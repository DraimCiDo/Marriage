package com.lenis0012.bukkit.marriage2.commands;

import com.lenis0012.bukkit.marriage2.MPlayer;
import com.lenis0012.bukkit.marriage2.Marriage;
import com.lenis0012.bukkit.marriage2.config.Message;
import com.lenis0012.bukkit.marriage2.config.Permissions;
import com.lenis0012.bukkit.marriage2.internal.MarriagePlugin;
import com.lenis0012.bukkit.marriage2.utils.ColorUtils;

public class CommandChatSpy extends Command {
    private MarriagePlugin plugin;

    public CommandChatSpy(Marriage marriage) {
        super(marriage, "chatspy");
        setDescription(ColorUtils.colorMessage("{#43C6AC>}Включить шпионский чат администратора.{#191654<}"));
        setPermission(Permissions.CHAT_SPY);
        setHidden(true);
    }

    @Override
    public void execute() {
        MPlayer mPlayer = marriage.getMPlayer(player);
        boolean mode = !mPlayer.isChatSpy();
        mPlayer.setChatSpy(mode);
        reply(mode ? Message.CHAT_SPY_ENABLED : Message.CHAT_SPY_DISABLED);
    }
}
