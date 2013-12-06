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
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;

public class HandicapCommand implements CommandExecutor {

    public static HashSet<String> handicappedList = new HashSet<String>();

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.handicap")) {
            return true;
        }
        if (args.length != 1) {
            return false;
        }
        Player player = Bukkit.getServer().getPlayer(args[0]);
        if (player != null) {
            if (!handicappedList.contains(player.getName())) {
                handicappedList.add(player.getName());
                Methods.sendPlayerMessage(cs, "You handicaped " + Methods.red(player.getDisplayName()) + ".");
                Methods.sendPlayerMessage(player, "You're now " + Methods.red("blocked") + " from using commands.");
                return true;
            } else {
                handicappedList.remove(player.getName());
                Methods.sendPlayerMessage(cs, "You unhandicaped " + Methods.red(player.getDisplayName()) + ".");
                Methods.sendPlayerMessage(player, "You're now " + Methods.red("allowed") + " to use commands.");
                return true;
            }
        } else {
            Methods.playerNotFound(cs, args[0]);
            return true;
        }
    }
}
