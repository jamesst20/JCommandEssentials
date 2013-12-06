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

public class FlyCommand implements CommandExecutor {

    public static HashSet<String> flyingPlayers = new HashSet<String>();

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (args.length == 0) {
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.fly.self")) {
                return true;
            }
            if (Methods.isConsole(cs)) {
                Methods.sendPlayerMessage(cs, ChatColor.RED + "You can only enable fly for others from console.");
                return true;
            }
            if (!flyingPlayers.contains(cs.getName())) {
                flyingPlayers.add(cs.getName());
                ((Player) cs).setAllowFlight(true);
                ((Player) cs).setFlying(true);
                Methods.sendPlayerMessage(cs, "Flying is now " + Methods.red("enabled") + ".");
            } else {
                flyingPlayers.remove(cs.getName());
                ((Player) cs).setAllowFlight(false);
                ((Player) cs).setFlying(false);
                Methods.sendPlayerMessage(cs, "Flying is now " + Methods.red("disabled") + ".");
            }
        } else if (args.length == 1) {
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.fly.others")) {
                return true;
            }
            Player target = Bukkit.getServer().getPlayer(args[0]);
            if (target != null) {
                if (!flyingPlayers.contains(target.getName())) {
                    flyingPlayers.add(target.getName());
                    target.setAllowFlight(true);
                    target.setFlying(true);
                    Methods.sendPlayerMessage(cs,
                            "Flying is now " + Methods.red("enabled") + " for " + Methods.red(target.getDisplayName()) + ".");
                    Methods.sendPlayerMessage(target, "Flying " + Methods.red("enabled") + ".");
                } else {
                    flyingPlayers.remove(target.getName());
                    target.setAllowFlight(false);
                    target.setFlying(false);
                    Methods.sendPlayerMessage(cs,
                            "Flying is now " + Methods.red("disabled") + " for " + Methods.red(target.getDisplayName())
                                    + ".");
                    Methods.sendPlayerMessage(target, "Flying " + Methods.red("disabled") + ".");
                }
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
