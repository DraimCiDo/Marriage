package com.lenis0012.bukkit.marriage2.internal.data;

import com.google.common.collect.Maps;
import com.lenis0012.bukkit.marriage2.MData;
import com.lenis0012.bukkit.marriage2.internal.MarriageCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class DataConverter {
    private final MarriageCore core;
    private File dir;

    public DataConverter(MarriageCore core) {
        this.core = core;
    }

    public boolean isOutdated() {
        this.dir = new File(core.getPlugin().getDataFolder(), "playerdata");
        return dir.exists();
    }

    public void convert() {
        long lastMessage = 0;
        File[] files = dir.listFiles();
        int totalFiles = files.length;
        core.getLogger().log(Level.INFO, "Преобразование " + totalFiles + " в старые записи базы данных...");

        // Retrieve UUIDs from mojang
        Map<String, UUID> uuidMap = Maps.newHashMap();
        //UUIDFetcher uuidFetcher = new UUIDFetcher(new ArrayList<String>());
        int failed = 0;
        for(int completed = 0; completed < totalFiles; completed++) {
            File file = files[completed];
            String name = file.getName().replace(".yml", "");

            // status report
            double progress = (completed + 1.0) / (double) totalFiles;
            if(System.currentTimeMillis() >= lastMessage) {
                lastMessage = System.currentTimeMillis() + 2500; // Update every 2.5 seconds
                reportStatus(progress);
            }

            // Pull from cache
            @SuppressWarnings("deprecation")
			OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(name);
            if(offlinePlayer != null) {
                UUID userId = offlinePlayer.getUniqueId();
                if(userId != null) {
                    uuidMap.put(name, userId);
                    continue;
                }
            }

            //Not stored on server? Failed then.
            failed += 1;

            // // Pull from mojang
            // if(ranThroughMojang >= 50000) { // Max 500 requests
            //     failed += 1;
            //     continue;
            // }

            // uuidFetcher.addName(name);
            // ranThroughMojang += 1;
            // if(uuidFetcher.size() == 100) {
            //     try {
            //         uuidMap.putAll(uuidFetcher.call());
            //     } catch(Exception e) {
            //         core.getLogger().log(Level.WARNING, "Не удалось получить UUID для 100 игроков!");
            //     }
            //     uuidFetcher = new UUIDFetcher(new ArrayList<String>());
            // }
        }

        core.getLogger().log(Level.INFO, String.format("Преобразованные записи %s. %s локально, %s сбой.",
            totalFiles, totalFiles - failed, failed));
        core.getLogger().log(Level.INFO, "Неудачные записи, скорее всего, сделаны неактивными игроками.");

//        for(int completed = 0; completed < totalFiles; completed++) {
//            File file = files[completed];
//            String name = file.getName().replace(".yml", "");
//            if(files.length > 50000) {
//                // Over 500 requests, check for marriage
//                try {
//                    FileConfiguration cnf = YamlConfiguration.loadConfiguration(file);
//                    cnf.load(file);
//                    String partner = cnf.getString("partner");
//                    if(partner == null) continue;
//                } catch(Exception e) {
//                    continue; // skip
//                }
//            }
//
//            uuidFetcher.addName(name);
//            if(uuidFetcher.size() >= 100 || completed >= totalFiles - 1) {
//                try {
//                    uuidMap.putAll(uuidFetcher.call());
//                    uuidFetcher = new UUIDFetcher(new ArrayList<String>());
//                } catch(Exception e) {
//                    core.getLogger().log(Level.WARNING, "Failed to retrieve UUID for 100 players!");
//                }
//            }
//
//            double progress = (completed + 1.0) / (double) totalFiles;
//            if(System.currentTimeMillis() >= lastMessage) {
//                lastMessage = System.currentTimeMillis() + 2500; // Update every 2.5 seconds
//                reportStatus(progress);
//            }
//        }

        // Insert data into new DB...
        core.getLogger().log(Level.INFO, "Вставка пользовательских данных в новую базу данных...");
        int completed = 0;
        for(Map.Entry<String, UUID> entry : uuidMap.entrySet()) {
            try {
                MarriagePlayer mp = core.getDataManager().loadPlayer(entry.getValue());
                String name = entry.getKey();
                mp.setLastName(name);
                File file = new File(dir, name + ".yml");
                FileConfiguration cnf = YamlConfiguration.loadConfiguration(file);
                cnf.load(file);

                if(cnf.contains("partner") && !mp.isMarried()) {
                    UUID uuid = uuidMap.get(cnf.getString("partner"));
                    if(uuid != null) {
                        MarriagePlayer mp2 = core.getDataManager().loadPlayer(uuid);
                        MData mdata = core.marry(mp, mp2);

                        if(cnf.contains("home")) {
                            World world = Bukkit.getWorld(cnf.getString("home.world"));
                            if(world != null) {
                                double x = cnf.getDouble("home.x", 0.0);
                                double y = cnf.getDouble("home.y", 0.0);
                                double z = cnf.getDouble("home.z", 0.0);
                                float yaw = (float) cnf.getDouble("home.yaw", 0.0);
                                float pitch = (float) cnf.getDouble("home.pitch", 0.0);
                                Location location = new Location(world, x, y, z, yaw, pitch);
                                mdata.setHome(location);
                            }
                        }
                        // Only save if players are married, otherwise we really don't care.
                        core.getDataManager().savePlayer(mp);
                        core.getDataManager().savePlayer(mp2);
                    }
                }
            } catch(Exception e) {
                core.getLogger().log(Level.WARNING, "Не удалось преобразовать данные для игрока " + entry.getKey(), e);
            }

            double progress = ++completed / (double) uuidMap.size();
            if(System.currentTimeMillis() >= lastMessage) {
                lastMessage = System.currentTimeMillis() + 2500; // Update every 2.5 seconds
                reportStatus(progress);
            }
        }

        // Reset old data
        core.getLogger().log(Level.INFO, "Переименование файла playerdata...");
        int remainingTries = 60; // Try 60 times
        while(!dir.renameTo(new File(core.getPlugin().getDataFolder(), "playerdata_backup"))) {
            long sleepTime = 500L;

            // Limit to take 30 seconds max
            if(remainingTries-- <= 0) {
                core.getLogger().log(Level.WARNING, "Не удалось переименовать старый файл playerdata, пожалуйста, сделайте это вручную!");
                core.getLogger().log(Level.INFO, "Сервер запустится нормально через 10 секунд.");
                sleepTime = 10000L;
            }

            // Wait
            try {
                Thread.sleep(sleepTime);
            } catch(InterruptedException e) {
            }
        }
    }

    private void reportStatus(double progress) {
        int percent = (int) Math.floor(progress * 100);
        StringBuilder bar = new StringBuilder("[");
        for(int i = 0; i < percent; i += 5) {
            bar.append('=');
        }
        for(int i = percent; i < 100; i += 5) {
            bar.append('_');
        }
        bar.append("] (").append(percent).append("%)");
        core.getLogger().log(Level.INFO, bar.toString());
    }
}
