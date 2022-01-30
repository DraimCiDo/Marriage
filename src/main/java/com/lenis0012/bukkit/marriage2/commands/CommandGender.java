package com.lenis0012.bukkit.marriage2.commands;

import com.lenis0012.bukkit.marriage2.Gender;
import com.lenis0012.bukkit.marriage2.MPlayer;
import com.lenis0012.bukkit.marriage2.Marriage;
import com.lenis0012.bukkit.marriage2.config.Message;
import com.lenis0012.bukkit.marriage2.config.Settings;
import com.lenis0012.bukkit.marriage2.utils.MessageUtils;
import org.bukkit.entity.Player;

public class CommandGender extends Command {
    final Player p = (Player)sender;

    public CommandGender(Marriage marriage) {
        super(marriage, "gender");
        setDescription(MessageUtils.config("message", "Messages.Gender", p, 0));
        setUsage("<gender>");
        setMinArgs(1);
    }

    @Override
    public void execute() {
        Gender gender;
        try {
            gender = Gender.valueOf(getArg(0).toUpperCase());
        } catch(Exception e) {
            reply(Message.INVALID_GENDER);
            return;
        }

        MPlayer mPlayer = marriage.getMPlayer(player);
        if(mPlayer.getGender() != Gender.UNKNOWN && !Settings.ALLOW_GENDER_CHANGE.value()) {
            reply(Message.GENDER_ALREADY_CHANGED);
        }

        mPlayer.setGender(gender);
        reply(Message.GENDER_SET, gender.toString().toLowerCase());
    }
}