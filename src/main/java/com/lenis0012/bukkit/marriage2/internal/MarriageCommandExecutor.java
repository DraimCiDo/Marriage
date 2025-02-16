package com.lenis0012.bukkit.marriage2.internal;

import com.google.common.collect.Maps;
import com.lenis0012.bukkit.marriage2.Marriage;
import com.lenis0012.bukkit.marriage2.commands.Command;
import com.lenis0012.bukkit.marriage2.config.Settings;
import com.lenis0012.bukkit.marriage2.utils.ColorUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;

public class MarriageCommandExecutor implements CommandExecutor {
    private final MarriageCore core;
    private final Map<String, Command> commands = Maps.newHashMap();

    public MarriageCommandExecutor(MarriageBase core) {
        this.core = (MarriageCore) core;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        String subCommand = args.length > 0 ? args[0] : "help";
        Command command = commands.get(subCommand.toLowerCase());
        if(command == null) {
            command = commands.get("marry");
        }

        // Assuming that the command is not null now, if it is, then that is a mistake on my side.
        if(args.length > command.getMinArgs()) {
            if(command.getPermission() == null || command.getPermission().has(sender)) {
                if(command.isAllowConsole() || sender instanceof Player) {
                    command.prepare(sender, args);
                    command.execute();
                } else {
                    sender.sendMessage(ColorUtils.colorMessage("<$#FF512F>Вы должны быть игроком, чтобы выполнить эту команду.<$#F09819>"));
                }
            } else {
                sender.sendMessage(ColorUtils.colorMessage("<$#FF512F>Вам не разрешается использовать эту команду.<$#F09819>"));
            }
        } else {
            sender.sendMessage(ColorUtils.colorMessage("<$#FF512F>Вы не указали достаточно аргументов для этой команды.<$#F09819>"));
        }

        return true;
    }

    public void register(Class<? extends Command> commandClass) {
        try {
            Command command = commandClass.getConstructor(Marriage.class).newInstance(core);
            if(Settings.DISABLED_COMMANDS.value().contains(command.getAliases()[0])) {
                // Command was disabled in config...
                return;
            }

            for(String alias : command.getAliases()) {
                commands.put(alias.toLowerCase(), command);
            }
        } catch(Exception e) {
            core.getLogger().log(Level.SEVERE, "Не удалось зарегистрировать подкоманду", e);
        }
    }

    public Collection<Command> getSubCommands() {
        return commands.values();
    }
}