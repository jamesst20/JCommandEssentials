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

import java.util.HashSet;

public class VanishCommand implements CommandExecutor {

    public static HashSet<String> vanishedPlayers = new HashSet<String>();

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (args.length == 0) {
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.vanish.self")) {
                return true;
            }
            if (Methods.isConsole(cs)) {
                Methods.sendPlayerMessage(cs, ChatColor.RED + "The console can only vanish other players.");
                return true;
            }
            if (!vanishedPlayers.contains(cs.getName())) {
                vanishedPlayers.add(cs.getName());
                Methods.sendPlayerMessage(cs, "You are now " + Methods.red("invisible") + " from all players!");
                Methods.hidePlayer(((Player) cs));
            } else {
                vanishedPlayers.remove(cs.getName());
                Methods.sendPlayerMessage(cs, "You are now " + Methods.red("visible") + " to all players!");
                Methods.showPlayer(((Player) cs));
            }
            return true;
        } else if (args.length == 1) {
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.vanish.others")) {
                return true;
            }
            Player player = Bukkit.getServer().getPlayer(args[0]);
            if (player != null) {
                if (!vanishedPlayers.contains(player.getName())) {
                    vanishedPlayers.add(player.getName());
                    Methods.sendPlayerMessage(cs, "The player " + Methods.red(player.getDisplayName()) + " is now "
                            + Methods.red("invisible") + " from all players.");
                    Methods.sendPlayerMessage(player, "You are now " + Methods.red("invisible") + " from all players!");
                    Methods.hidePlayer(player);
                } else {
                    vanishedPlayers.remove(player.getName());
                    Methods.sendPlayerMessage(cs, "The player " + Methods.red(player.getDisplayName()) + " is now "
                            + Methods.red("visible") + " to all players.");
                    Methods.sendPlayerMessage(player, "You are now " + Methods.red("visible") + " to all players!");
                    Methods.showPlayer(player);
                }
            } else {
                Methods.playerNotFound(cs, args[0]);
                return true;
            }
            return true;
        }
        return true;
    }
}
