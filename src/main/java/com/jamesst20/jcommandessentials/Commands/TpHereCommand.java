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

public class TpHereCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.tphere")) {
            return true;
        } else if (args.length != 1) {
            return false;
        }
        if (Methods.isConsole(cs)) {
            Methods.sendPlayerMessage(cs, ChatColor.RED + "The console can't use tphere.");
            return true;
        }
        Player player = Bukkit.getServer().getPlayer(args[0]);
        if (player != null) {
            Methods.sendPlayerMessage(cs, "You have teleported " + Methods.red(player.getDisplayName()) + " to you.");
            Methods.sendPlayerMessage(player, "You have been teleported to " + Methods.red(((Player) cs).getDisplayName()));
            player.teleport(((Player) cs).getLocation());
            return true;
        } else {
            Methods.sendPlayerMessage(cs, ChatColor.RED + "The player " + args[0] + " couldn't be found.");
            return true;
        }
    }
}
