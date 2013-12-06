/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jamesst20.jcommandessentials.Commands;

import com.jamesst20.jcommandessentials.Utils.Methods;
import java.util.Arrays;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author James
 */
public class SetItemNameCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (args.length != 1) {
            return false;
        }
        if (Methods.isConsole(cs)) {
            Methods.sendPlayerMessage(cs, ChatColor.RED + "Console can't change its own item name.");
            return true;
        }
        if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.setitemname")){
            return true;
        }
        Player p = (Player) cs;
        ItemStack item = p.getItemInHand();
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(args[0]);
        item.setItemMeta(itemMeta);
        Methods.sendPlayerMessage(cs, "Item name has been set to " + Methods.red(args[0]) + ".");
        return true;
    }
    
}
