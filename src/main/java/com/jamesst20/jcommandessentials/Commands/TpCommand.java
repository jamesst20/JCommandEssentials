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
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (args.length == 0) {
            return false;
        } else if (args.length == 1) {
            if (Methods.isConsole(cs)) {
                Methods.sendPlayerMessage(cs, ChatColor.RED + "Console can only tp a player to another player.");
                return true;
            }
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.tp.self")) {
                return true;
            }
            Player from = ((Player) cs);
            Player to = Bukkit.getServer().getPlayer(args[0]);
            if (to != null) {
                Methods.sendPlayerMessage(from, "You teleported to " + Methods.red(to.getDisplayName()));
                from.teleport(to);
                return true;
            } else {
                Methods.playerNotFound(cs, args[0]);
                return true;
            }
        } else if (args.length == 2) {
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.tp.others")) {
                return true;
            }
            Player from = Bukkit.getServer().getPlayer(args[0]);
            Player to = Bukkit.getServer().getPlayer(args[1]);
            if (from != null && to != null) {
                Methods.sendPlayerMessage(cs,
                        "You teleported " + Methods.red(from.getDisplayName()) + " to " + Methods.red(to.getDisplayName())
                                + ".");
                Methods.sendPlayerMessage(from, "You have been teleported to " + Methods.red(to.getDisplayName()) + ".");
                Methods.sendPlayerMessage(to, Methods.red(from.getDisplayName()) + " have been teleported to you.");
                from.teleport(to);
            } else if (from == null) {
                Methods.playerNotFound(cs, args[0]);
            } else if (to == null) {
                Methods.playerNotFound(cs, args[1]);
            }
            return true;
        } else {
            return false;
        }
    }
}
