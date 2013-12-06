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

public class SetExpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (args.length == 1) {
            if (Methods.isConsole(cs)) {
                Methods.sendPlayerMessage(cs, ChatColor.RED + "The console can't set its own experience.");
                return true;
            }
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.setxp.self")) {
                return true;
            }
            try {
                int exp = Integer.parseInt(args[0]);
                ((Player) cs).setLevel(exp);
                Methods.sendPlayerMessage(cs, "Experience level has been set to " + Methods.red(args[0]) + ".");
                return true;
            } catch (NumberFormatException e) {
                Methods.sendPlayerMessage(cs, ChatColor.RED + "Experience level must be given as a number.");
                return true;
            }
        } else if (args.length == 2) {
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.setxp.others")) {
                return true;
            }
            Player player = Bukkit.getServer().getPlayer(args[0]);
            if (player != null) {
                try {
                    int exp = Integer.parseInt(args[1]);
                    player.setLevel(exp);
                    Methods.sendPlayerMessage(cs, Methods.red(player.getDisplayName() + "'s") + " experience level has been set to " + Methods.red(args[1]) + ".");
                    Methods.sendPlayerMessage(player, "Your experience level has been set to " + Methods.red(args[1]) + ".");
                    return true;
                } catch (NumberFormatException e) {
                    Methods.sendPlayerMessage(cs, ChatColor.RED + "Experience must be given as a number.");
                    return true;
                }
            } else {
                Methods.playerNotFound(cs, args[0]);
                return true;
            }
        } else {
            return false;
        }
    }
}
