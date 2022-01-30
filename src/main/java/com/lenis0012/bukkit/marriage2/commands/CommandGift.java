package com.lenis0012.bukkit.marriage2.commands;

import com.lenis0012.bukkit.marriage2.MData;
import com.lenis0012.bukkit.marriage2.MPlayer;
import com.lenis0012.bukkit.marriage2.Marriage;
import com.lenis0012.bukkit.marriage2.config.Message;
import com.lenis0012.bukkit.marriage2.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandGift extends Command {
    final Player p = (Player)sender;

    public CommandGift(Marriage marriage) {
        super(marriage, "gift");
        setDescription(MessageUtils.config("message", "Messages.Gift", p, 0));
    }

    @Override
    public void execute() {
        MPlayer mPlayer = marriage.getMPlayer(player);
        MData marriage = mPlayer.getMarriage();
        if(marriage == null) {
            reply(MessageUtils.config("message", "Messages.Not_Marry", p, 0));
            return;
        }

        Player partner = Bukkit.getPlayer(marriage.getOtherPlayer(player.getUniqueId()));
        if(partner == null) {
            reply(MessageUtils.config("message", "Messages.Player_Not_Online", p, 0));
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        if(item == null || item.getType() == Material.AIR) {
            reply(MessageUtils.config("message", "Messages.NO_ITEM", p, 0));
            return;
        }
        
        if(partner.getInventory().firstEmpty() == -1) {
            reply(MessageUtils.config("message", "Messages.PARTNER_INVENTORY_FULL", p, 0));
            return;
        }

        partner.getInventory().addItem(item.clone());
        player.getInventory().setItemInMainHand(null);
        reply(Message.ITEM_GIFTED, item.getAmount(), item.getType().toString().toLowerCase());
        reply(partner, Message.GIFT_RECEIVED, item.getAmount(), item.getType().toString().toLowerCase());
    }
}
