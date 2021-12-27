package com.lenis0012.bukkit.marriage2.internal;

import com.google.common.collect.Lists;
import com.lenis0012.bukkit.marriage2.Marriage;
import com.lenis0012.bukkit.marriage2.utils.ColorUtils;
import com.lenis0012.pluginutils.PluginHolder;
import com.lenis0012.pluginutils.modules.configuration.ConfigurationModule;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;

public class MarriagePlugin extends PluginHolder {
    private static MarriageCore core;

    private File langFile;
    private FileConfiguration langConfig;

    public static Marriage getCore() {
        return core;
    }

    @SuppressWarnings("unchecked")
    private final List<Method>[] methods = new List[Register.Type.values().length];

    @SuppressWarnings("unchecked")
	public MarriagePlugin() {
        super(ConfigurationModule.class);
        core = new MarriageCore(this);

        //Scan methods
        for(int i = 0; i < methods.length; i++) {
            methods[i] = Lists.newArrayList();
        }
        scanMethods(core.getClass());
    }

    private void scanMethods(Class<?> clazz) {
        if(clazz == null) {
            return;
        }

        // Loop through all methods in class
        for(Method method : clazz.getMethods()) {
            Register register = method.getAnnotation(Register.class);
            if(register != null) {
                methods[register.type().ordinal()].add(method);
            }
        }

        // Scan methods in super class
        scanMethods(clazz.getSuperclass());
    }

    protected File getPluginFile() {
        return getFile();
    }

    @Override
    public void onLoad() {
        executeMethods(Register.Type.LOAD);
    }

    @Override
    public void enable() {
        printASCII();
        executeMethods(Register.Type.ENABLE);
        this.createLangFile("ru_RU");
        this.loadLangConfig();
    }

    @Override
    public void disable() {
        executeMethods(Register.Type.DISABLE);
    }

    private void createLangFile(String... names) {
        for (String name : names) {
            if (!new File(getDataFolder(), "lang" + File.separator + name + ".yml").exists()) {
                saveResource("lang" + File.separator + name + ".yml", false);
            }
        }
    }

    public void loadLangConfig() {
        langFile = new File(getDataFolder(), "lang" + File.separator + getConfig().getString("lang") + ".yml");
        if (!langFile.exists()) {
            langFile = new File(getDataFolder(), "lang" + File.separator + "ru_RU.yml");
        }
        langConfig = YamlConfiguration.loadConfiguration(langFile);
    }

    private void executeMethods(Register.Type type) {
        List<Method> list = Lists.newArrayList(methods[type.ordinal()]);
        while (!list.isEmpty()) {
            Method method = null;
            int lowestPriority = Integer.MAX_VALUE;
            for (Method m : list) {
                Register register = m.getAnnotation(Register.class);
                if (register.priority() < lowestPriority) {
                    method = m;
                    lowestPriority = register.priority();
                }
            }

            if (method != null) {
                list.remove(method);
                Register register = method.getAnnotation(Register.class);
                getLogger().log(Level.INFO, "Загрузка " + register.name() + "...");
                try {
                    method.invoke(core);
                } catch (Exception e) {
                    getLogger().log(Level.SEVERE, "Не удалось загрузить " + register.name(), e);
                }
            } else {
                list.clear();
            }
        }

        getLogger().log(Level.INFO, type.getCompletionMessage());
    }

    public void printASCII() {
        getLogger().info(ColorUtils.colorMessage(" &6 /$$$$$$$  /$$$$$$$   /$$$$$$  /$$$$$$ /$$      /$$ /$$      /$$  /$$$$$$  /$$$$$$$  /$$$$$$$  /$$     /$$"));
        getLogger().info(ColorUtils.colorMessage(" &6| $$__  $$| $$__  $$ /$$__  $$|_  $$_/| $$$    /$$$| $$$    /$$$ /$$__  $$| $$__  $$| $$__  $$|  $$   /$$/"));
        getLogger().info(ColorUtils.colorMessage(" &6| $$  \\ $$| $$  \\ $$| $$  \\ $$  | $$  | $$$$  /$$$$| $$$$  /$$$$| $$  \\ $$| $$  \\ $$| $$  \\ $$ \\  $$ /$$/ "));
        getLogger().info(ColorUtils.colorMessage(" &6| $$  | $$| $$$$$$$/| $$$$$$$$  | $$  | $$ $$/$$ $$| $$ $$/$$ $$| $$$$$$$$| $$$$$$$/| $$$$$$$/  \\  $$$$/  "));
        getLogger().info(ColorUtils.colorMessage(" &6| $$  | $$| $$__  $$| $$__  $$  | $$  | $$  $$$| $$| $$  $$$| $$| $$__  $$| $$__  $$| $$__  $$   \\  $$/   "));
        getLogger().info(ColorUtils.colorMessage(" &6| $$  | $$| $$  \\ $$| $$  | $$  | $$  | $$\\  $ | $$| $$\\  $ | $$| $$  | $$| $$  \\ $$| $$  \\ $$    | $$    "));
        getLogger().info(ColorUtils.colorMessage(" &6| $$$$$$$/| $$  | $$| $$  | $$ /$$$$$$| $$ \\/  | $$| $$ \\/  | $$| $$  | $$| $$  | $$| $$  | $$    | $$    "));
        getLogger().info(ColorUtils.colorMessage(" &6|_______/ |__/  |__/|__/  |__/|______/|__/     |__/|__/     |__/|__/  |__/|__/  |__/|__/  |__/    |__/    "));
        getLogger().info(ColorUtils.colorMessage("                                                                                  "));
        getLogger().info(ColorUtils.colorMessage("                                                                &6by DraimGooSe        "));
        getLogger().info(ColorUtils.colorMessage("                                                                                  "));
    }
}