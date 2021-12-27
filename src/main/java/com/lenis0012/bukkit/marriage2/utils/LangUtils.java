package com.lenis0012.bukkit.marriage2.utils;

import com.lenis0012.bukkit.marriage2.internal.MarriagePlugin;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class LangUtils {
    private static String lang;
    private static File langFile;
    private static FileConfiguration langCFG;

    public static void loadLangConfig() {
        Plugin plugin = MarriagePlugin.getPlugin();
        lang = plugin.getConfig().getString("lang");

        File langFolder = new File(plugin.getDataFolder(), "lang");
        File tempRes = new File(plugin.getDataFolder(), lang + ".yml");

        langFile = new File(langFolder, lang + ".yml");
        langFile.getParentFile().mkdirs();

        if (plugin.getResource(lang + ".yml") != null) {
            plugin.saveResource(lang + ".yml", true);
            tempRes.renameTo(langFile);
        }

        langCFG = new YamlConfiguration();
        try {
            langCFG.load(langFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static String getString(String node) {
        return langCFG.getString(node);
    }
}
