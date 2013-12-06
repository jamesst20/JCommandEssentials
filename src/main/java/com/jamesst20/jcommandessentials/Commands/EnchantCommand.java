/* 
 * Copyright (C) 2013 James
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
            StringBuilder enchantList = new StringBuilder();
            for (Enchantment enchant : Enchantment.values()) {
                enchantList.append("&c").append(formatEnchantment(enchant.getName())).append("&3, ");
            }
            enchantList = Methods.replaceLast(enchantList, ", ", "");
            Methods.sendPlayerMessage(cs, "/enchant <enchantment> <level>");
            Methods.sendPlayerMessage(cs, Methods.coloring("You can apply those enchantments : " + "&cAll&3, " + enchantList + "&3."));
            return true;
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("all")) {
                enchantAll(cs, ((Player) cs));
                return true;
            } else {
                return false;
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("all")) {
                Methods.sendPlayerMessage(cs, Methods.red("Maximum level is used with keyword all."));
                enchantAll(cs, ((Player) cs));
            } else {
                enchant(cs, ((Player) cs), args);
            }
            return true;
        } else {
            return false;
        }
    }

    private String formatEnchantment(String str) {
        if (str.contains("_")) {
            String[] splittedStr = str.split("_");
            StringBuilder formattedStr = new StringBuilder();
            for (String part : splittedStr) {
                formattedStr.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1).toLowerCase()).append("_");
            }
            return Methods.replaceLast(formattedStr, "_", "").toString();
        } else {
            return Character.toUpperCase(str.charAt(0)) + str.substring(1).toLowerCase();
        }
    }

    private void enchant(CommandSender cs, Player player, String[] args) {
        try {
            Enchantment ench = Enchantment.getByName(args[0].toUpperCase());
            int enchantLevel = Integer.parseInt(args[1]);
            if (ench != null) {
                if (ench.canEnchantItem(player.getItemInHand())) {
                    if (ench.getMaxLevel() >= enchantLevel && ench.getStartLevel() <= enchantLevel) {
                        player.getItemInHand().addEnchantment(ench, enchantLevel);
                        Methods.sendPlayerMessage(cs, "You applied " + Methods.red(formatEnchantment(ench.getName())) + " to the item you're holding.");
                    } else {
                        Methods.sendPlayerMessage(cs, "Minimum for this enchantment is " + Methods.red(String.valueOf(ench.getStartLevel())) + " and maximum is " + Methods.red(String.valueOf(ench.getMaxLevel()) + "."));
                    }
                } else {
                    Methods.sendPlayerMessage(cs, "This enchantment can't be applied to this item.");
                }
            } else {
                Methods.sendPlayerMessage(cs, ChatColor.RED + "Enchantment not found.");
            }
        } catch (NumberFormatException e) {
            Methods.sendPlayerMessage(cs, ChatColor.RED + "Failed to get enchantment level. Did you give a number?");
        }
    }

    private void enchantAll(CommandSender cs, Player player) {
        StringBuilder enchantments = new StringBuilder();
        for (Enchantment ench : Enchantment.values()) {
            if (ench.canEnchantItem(player.getItemInHand())) {
                player.getItemInHand().addEnchantment(ench, ench.getMaxLevel());
                enchantments.append("&4").append(formatEnchantment(ench.getName())).append("&3, ");
            }
        }
        enchantments = Methods.replaceLast(enchantments, ", ", "");
        Methods.sendPlayerMessage(cs, "You applied " + Methods.coloring(enchantments.toString()) + ChatColor.DARK_AQUA + " to the item you're holding.");
    }
}
