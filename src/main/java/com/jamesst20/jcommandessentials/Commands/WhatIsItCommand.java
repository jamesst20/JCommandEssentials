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
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WhatIsItCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.whatisit")) {
            return true;
        }
        if (args.length == 0) {
            if (Methods.isConsole(cs)) {
                Methods.sendPlayerMessage(cs, ChatColor.RED + "The console can't hold an item.");
                return true;
            }
            Player player = (Player) cs;

            ItemStack itemMainHand = player.getInventory().getItemInMainHand();
            ItemStack itemOffHand = player.getInventory().getItemInOffHand();

            //Main hand
            StringBuilder strToSend = new StringBuilder();
            strToSend.append("You are holding : ").append(Methods.red(itemMainHand.getType().toString()));

            //Second off hand
            if(itemOffHand.getType() != Material.AIR) {
                strToSend.append(" and : ").append(Methods.red(itemOffHand.getType().toString()));
            }

            Methods.sendPlayerMessage(cs, strToSend.toString());
        }
        return true;
    }
}