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
            String itemHolding = player.getItemInHand().getType().toString();
            int itemID = player.getItemInHand().getTypeId();
            int data = player.getItemInHand().getData().getData();
            StringBuilder strToSend = new StringBuilder();
            strToSend.append("You are holding : ");
            strToSend.append(Methods.red(itemHolding));
            strToSend.append(" ID : ");
            strToSend.append(Methods.red(String.valueOf(itemID)));
            if (data > 0) {
                strToSend.append(":");
                strToSend.append(Methods.red(String.valueOf(data)));
            }
            Methods.sendPlayerMessage(cs, strToSend.toString());
        }
        return true;
    }
}