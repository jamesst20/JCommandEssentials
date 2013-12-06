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

public class SetItemDescriptionCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (args.length < 1 || args.length > 3) {
            return false;
        }

        if (Methods.isConsole(cs)) {
            Methods.sendPlayerMessage(cs, ChatColor.RED + "Console can't change its own item description.");
            return true;
        }
        if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.setitemdescription")){
            return true;
        }
        Player p = (Player) cs;
        ItemStack item = p.getItemInHand();
        ItemMeta itemMeta = item.getItemMeta();
        List<String> descriptionLines = (args.length == 1) ? Arrays.asList(args[0]) : (((args.length >= 3) ? Arrays.asList(args[0], args[1], args[2])
                : ((args.length >= 2) ? Arrays.asList(args[0], args[1]) : Arrays.asList("This will never happen.")))); // Had fun making something hard to read 
        itemMeta.setLore(descriptionLines);
        item.setItemMeta(itemMeta);
        Methods.sendPlayerMessage(cs, "Item description has been set.");
        return true;
    }

}
