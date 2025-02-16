package com.lenis0012.bukkit.marriage2.internal;

import com.lenis0012.bukkit.marriage2.config.Settings;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.logging.Level;

public class Dependencies {
    private static Economy economyService;

    public Dependencies(MarriageCore core) {
        boolean useEconomy = Settings.ECONOMY_ENABLED.value();
        if(!useEconomy) {
            return;
        }

        if(!Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            core.getLogger().log(Level.WARNING, "Не удалось обнаружить Vault, настройки экономии не будут работать");
            return;
        }

        RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServicesManager().getRegistration(Economy.class);
        if(economyProvider != null) {
            economyService = economyProvider.getProvider();
            core.getLogger().log(Level.INFO, "Подключен к " + economyService.getName() + " используя Vault");
        } else {
            core.getLogger().log(Level.WARNING, "Vault присутствует, но поставщик услуг экономики не найден");
        }
    }

    public boolean isEconomyEnabled() {
        return economyService != null;
    }

    public Economy getEconomyService() {
        return economyService;
    }
}
