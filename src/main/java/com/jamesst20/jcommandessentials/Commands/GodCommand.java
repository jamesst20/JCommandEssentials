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

public class GodCommand implements CommandExecutor {

    public final static HashSet<String> godPlayers = new HashSet<String>();

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (args.length == 0) {
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.god.self")) {
                return true;
            }
            if (Methods.isConsole(cs)) {
                Methods.sendPlayerMessage(cs, ChatColor.RED + "Console can only enable god mode on other players.");
                return true;
            }
            if (!godPlayers.contains(cs.getName())) {
                godPlayers.add(cs.getName());
                Methods.sendPlayerMessage(cs, "God mode " + Methods.red("enabled") + ".");
            } else {
                godPlayers.remove(cs.getName());
                Methods.sendPlayerMessage(cs, "God mode " + Methods.red("disabled") + ".");
            }
        } else if (args.length == 1) {
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.god.others")) {
                return true;
            }
            Player target = Bukkit.getServer().getPlayer(args[0]);
            if (target == null) {
                Methods.playerNotFound(cs, args[0]);
                return true;
            }
            if (!godPlayers.contains(target.getName())) {
                godPlayers.add(target.getName());
                Methods.sendPlayerMessage(cs,
                        "You " + Methods.red("enabled") + " god mode for " + Methods.red(target.getDisplayName()) + ".");
                Methods.sendPlayerMessage(target, "God mode " + Methods.red("enabled") + ".");
            } else {
                godPlayers.remove(target.getName());
                Methods.sendPlayerMessage(cs,
                        "You " + Methods.red("disabled") + " god mode for " + Methods.red(target.getDisplayName()) + ".");
                Methods.sendPlayerMessage(target, "God mode " + Methods.red("disabled") + ".");
            }
        } else {
            return false;
        }
        return true;
    }
}
