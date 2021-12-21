package com.lenis0012.bukkit.marriage2.internal.data;

import com.lenis0012.bukkit.marriage2.internal.MarriageCore;

import java.io.File;
import java.util.logging.Level;

public class DataConverter {
    private final MarriageCore core;

    public DataConverter(MarriageCore core) {
        this.core = core;
    }

    public boolean isOutdated() {
        File dir = new File(core.getPlugin().getDataFolder(), "playerdata");
        return dir.exists();
    }

    private void reportStatus(double progress) {
        int percent = (int) Math.floor(progress * 100);
        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < percent; i += 5) {
            bar.append('=');
        }
        for (int i = percent; i < 100; i += 5) {
            bar.append('_');
        }
        bar.append("] (").append(percent).append("%)");
        core.getLogger().log(Level.INFO, bar.toString());
    }
}
