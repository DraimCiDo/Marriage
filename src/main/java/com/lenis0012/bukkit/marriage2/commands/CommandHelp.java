package com.lenis0012.bukkit.marriage2.commands;

import com.lenis0012.bukkit.marriage2.Marriage;
import com.lenis0012.bukkit.marriage2.config.Message;
import com.lenis0012.bukkit.marriage2.internal.MarriageBase;
import com.lenis0012.bukkit.marriage2.internal.MarriageCommandExecutor;
import com.lenis0012.bukkit.marriage2.utils.ColorUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;

public class CommandHelp extends Command {

    public CommandHelp(Marriage marriage) {
        super(marriage, "help");
        setMinArgs(-1);
        setHidden(true);
        setPermission(null);
        setAllowConsole(true);
    }

    @Override
    public void execute() {
        MarriageCommandExecutor commandExecutor = ((MarriageBase) marriage).getCommandExecutor();
//        reply(ColorUtils.colorMessage("<$#0C9C29>Версия плагина:<$#0ED42C> ") + marriage.getPlugin().getDescription().getVersion());
        reply(ColorUtils.colorMessage("{#f12711>}&m---------{#f5af19<} {#FDC830>}Команды{#F37335<} {#f12711>}&m---------{#f5af19<}")); // Play around with the amount of dashes later
        for(Command command : commandExecutor.getSubCommands()) {
            if(command.isHidden()) {
                continue;
            }

            String alias = command instanceof CommandMarry ? "" : command.getAliases()[0] + " ";
            String text = "&a/marry " + alias + command.getUsage() + " &f- &7" + command.getDescription();
            if(command.getExecutionFee() == 0.0 || !Bukkit.getVersion().contains("Spigot") || !marriage.dependencies().isEconomyEnabled() || player == null) {
                reply(text);
                continue;
            }
            if(player.spigot() == null) {
                reply(text);
                continue;
            }
            ComponentBuilder builder = new ComponentBuilder("/marry " + alias + command.getUsage()).color(ChatColor.GREEN)
                    .event(new HoverEvent(Action.SHOW_TEXT, new Text("Стоимость: "
                            + marriage.dependencies().getEconomyService().format(command.getExecutionFee()))))
                    .append(" - ").color(ChatColor.WHITE)
                    .event(new HoverEvent(Action.SHOW_TEXT, new Text("Стоимость: "
                            + marriage.dependencies().getEconomyService().format(command.getExecutionFee()))))
                    .append(command.getDescription()).color(ChatColor.GRAY)
                     .event(new HoverEvent(Action.SHOW_TEXT, new Text("Стоимость: "
                            + marriage.dependencies().getEconomyService().format(command.getExecutionFee()))));
            player.spigot().sendMessage(builder.create());
        }

        String status = Message.SINGLE.toString();
        if(player != null && player.hasMetadata("marriedTo")) {
            String partner = player.getMetadata("marriedTo").get(0).asString();
            status = String.format(Message.MARRIED_TO.toString(), partner);
        }
        reply(Message.STATUS, status);
        reply(ColorUtils.colorMessage("{#f12711>}&m--------------------------------------------{#f5af19<}")); // Play around with the amount of dashes later
    }
}
