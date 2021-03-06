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

public class KickCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.kick")) {
            return true;
        }
        if (args.length == 1) {
            Player player = Bukkit.getServer().getPlayer(args[0]);
            if (player != null) {
                if (Methods.hasPermission(player, "JCMDEss.commands.kick.exempt")) {
                    Methods.sendPlayerMessage(cs, "You are not allowed to kick " + Methods.red(player.getName()) + ".");
                    return true;
                }
                Methods.sendPlayerMessage(cs, "You kicked " + Methods.red(player.getDisplayName()) + ".");
                player.kickPlayer("You have been kicked.");
                Methods.broadcastMessage(Methods.prefix + ChatColor.DARK_AQUA + ChatColor.ITALIC
                        + Methods.red(player.getDisplayName() + " have been kicked."));
            } else {
                Methods.playerNotFound(cs, args[0]);
                return true;
            }
        } else if (args.length > 1) {
            Player player = Bukkit.getServer().getPlayer(args[0]);
            if (player != null) {
                if (Methods.hasPermission(player, "JCMDEss.commands.kick.exempt")) {
                    Methods.sendPlayerMessage(cs, "You are not allowed to kick " + Methods.red(player.getName()) + ".");
                    return true;
                }
                StringBuilder reason = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    reason.append(args[i]).append(" ");
                }
                Methods.replaceLast(reason, " ", "");
                Methods.sendPlayerMessage(cs,
                        "You kicked " + Methods.red(player.getDisplayName()) + ". Reason: " + Methods.red(reason.toString()));
                player.kickPlayer(reason.toString());
                Methods.broadcastMessage(Methods.prefix + ChatColor.DARK_AQUA + ChatColor.ITALIC
                        + Methods.red(player.getDisplayName() + " have been kicked. Reason: " + Methods.red(reason.toString())));
            } else {
                Methods.playerNotFound(cs, args[0]);
                return true;
            }
        } else {
            return false;
        }
        return true;
    }
}
