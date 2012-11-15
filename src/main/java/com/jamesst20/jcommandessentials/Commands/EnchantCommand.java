package com.jamesst20.jcommandessentials.Commands;

import com.jamesst20.jcommandessentials.Utils.Methods;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class EnchantCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.enchant")) {
            return true;
        }
        if (Methods.isConsole(cs)) {
            Methods.sendPlayerMessage(cs, ChatColor.RED + "The console can't enchant items.");
            return true;
        }
        if (args.length == 0) {
            String enchantList = "";
            for (Enchantment enchant : Enchantment.values()) {
                enchantList += formatEnchantment(enchant.getName()) + ", ";
            }
            Methods.replaceLast(enchantList, ", ", "");
            Methods.sendPlayerMessage(cs, "/enchant <enchantment> <level>");
            Methods.sendPlayerMessage(cs, "You can apply those enchantments : " + Methods.red(enchantList) + ".");
            return true;
        } else if (args.length == 2) {
            try {
                Player player = ((Player) cs);
                Enchantment ench = Enchantment.getByName(args[0].toUpperCase());
                int enchantLevel = Integer.parseInt(args[1]);
                if (ench != null) {
                    if (ench.canEnchantItem(player.getItemInHand())) {
                        if (ench.getMaxLevel() >= enchantLevel && ench.getStartLevel() <= enchantLevel) {
                            player.getItemInHand().addEnchantment(ench, enchantLevel);
                            Methods.sendPlayerMessage(cs, "You applied " + Methods.red(formatEnchantment(ench.getName())) + " to the item you're holding.");
                        }else{
                            Methods.sendPlayerMessage(cs, "Minimum for this enchantment is " + Methods.red(String.valueOf(ench.getStartLevel())) + " and maximum is " + Methods.red(String.valueOf(ench.getMaxLevel()) + "."));
                        }
                        return true;
                    } else {
                        Methods.sendPlayerMessage(cs, "This enchantment can't be applied to this item.");
                        return true;
                    }
                } else {
                    Methods.sendPlayerMessage(cs, ChatColor.RED + "Enchantment not found.");
                    return true;
                }
            } catch (NumberFormatException e) {
                Methods.sendPlayerMessage(cs, ChatColor.RED + "Failed to get enchantment level. Did you give a number?");
                return true;
            }
        } else {
            return false;
        }
    }

    private String formatEnchantment(String str) {
        if (str.contains("_")) {
            String[] splittedStr = str.split("_");
            String formattedStr = "";
            for (String part : splittedStr) {
                formattedStr += Character.toUpperCase(part.charAt(0)) + part.substring(1).toLowerCase() + "_";
            }
            return Methods.replaceLast(formattedStr, "_", "");
        } else {
            return Character.toUpperCase(str.charAt(0)) + str.substring(1).toLowerCase();
        }
    }
}
