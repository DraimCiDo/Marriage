package com.lenis0012.bukkit.marriage2.config;

import com.lenis0012.bukkit.marriage2.internal.MarriagePlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class MessageConfig
{
    private static MessageConfig mc;
    private File f;
    private FileConfiguration fc;
    public void setUp() {
        if(this.f == null) {
            this.f = new File(MarriagePlugin.getInstance().getDataFolder(), "message.yml");
        }
        this.fc = YamlConfiguration.loadConfiguration(this.f);
        if(!this.f.exists()) {
            try (final InputStream in = MarriagePlugin.getInstance().getResource("message.yml")) {
                Files.copy(in, this.f.toPath());
                this.fc = YamlConfiguration.loadConfiguration(this.f);
                Bukkit.getServer().getConsoleSender().sendMessage("[DraimMarriage] Файл локализации успешно создан.");
            }
            catch (IOException e) {
                Bukkit.getServer().getConsoleSender().sendMessage("[DraimMarriage] Произошла ошибка при создании файла локализации");
            }
        }
    }

    public void reloadCFG() {
        this.f = new File(MarriagePlugin.getInstance().getDataFolder(), "message.yml");
        this.fc = YamlConfiguration.loadConfiguration(this.f);
    }

    public FileConfiguration getCFG() {
        if (this.fc == null) {
            this.reloadCFG();
        }
        return this.fc;
    }

    public void saveCFG() {
        try {
            this.getCFG().save(this.f);
        }
        catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("Ошибка в сохранении файла локализации: " + this.f);
        }
    }

    public static MessageConfig getMSG() {
        return MessageConfig.mc;
    }

    static {
        MessageConfig.mc = new MessageConfig();
    }
}
