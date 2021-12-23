package com.lenis0012.bukkit.marriage2.commands;

import com.lenis0012.bukkit.marriage2.Marriage;
import com.lenis0012.bukkit.marriage2.config.Permissions;
import com.lenis0012.bukkit.marriage2.internal.MarriageCore;
import com.lenis0012.bukkit.marriage2.internal.data.DataManager;
import com.lenis0012.bukkit.marriage2.internal.data.Driver;
import com.lenis0012.bukkit.marriage2.utils.ColorUtils;
import org.bukkit.Bukkit;

public class CommandMigrate extends Command {
    public CommandMigrate(Marriage marriage) {
        super(marriage, "migrate");
        setMinArgs(2);
        setHidden(true);
        setPermission(Permissions.ADMIN);
    }

    @Override
    public void execute() {
        MarriageCore core = ((MarriageCore) marriage);
        Driver driver;
        final DataManager newDatabase = core.getDataManager();
        if(getArg(0).equalsIgnoreCase("sqlite") && getArg(1).equalsIgnoreCase("mysql")) {
            driver = Driver.SQLITE;
        } else if(getArg(0).equalsIgnoreCase("mysql") && getArg(1).equalsIgnoreCase("sqlite")) {
            driver = Driver.MYSQL;
        } else {
            reply(ColorUtils.colorMessage("<$#43C6AC>Используйте:<$#191654> <$#00F260>/marry migrate <old db> <new db><$#0575E6>"));
            return;
        }

        final boolean fastMode = getArgLength() <= 2 || !getArg(2).equalsIgnoreCase("false");
        final DataManager oldDatabase = new DataManager(core, driver);

        reply(ColorUtils.colorMessage("<$#e65c00>Запущен процесс миграции (это займёт не которое время)<$#F9D423>"));
        Bukkit.getScheduler().runTaskAsynchronously(marriage.getPlugin(), new Runnable() {
            @Override
            public void run() {
                boolean success = oldDatabase.migrateTo(newDatabase, !fastMode);
                oldDatabase.close(); // Disconnect from old db
                reply(ColorUtils.colorMessage(success ? "<$#e65c00>&aБД успешно перенесена!<$#F9D423>" : "<$#FF512F>Что-то пошло не так во время миграции, проверьте консоль.<$#F09819>"));
            }
        });
    }
}
