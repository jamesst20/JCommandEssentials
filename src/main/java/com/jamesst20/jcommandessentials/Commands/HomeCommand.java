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

import com.jamesst20.jcommandessentials.Objects.JPlayerConfig;
import com.jamesst20.jcommandessentials.Utils.Methods;
import com.jamesst20.jcommandessentials.Utils.TeleportDelay;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (Methods.isConsole(cs)) {
            Methods.sendPlayerMessage(cs, ChatColor.RED + "The console can't tp home.");
            return true;
        }
        if (args.length == 0) {
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.home.default")) {
                return true;
            }
            JPlayerConfig playerConf = new JPlayerConfig((Player) cs);
            if (playerConf.getHome() != null) {
                if (TeleportDelay.getDelay() < 1) {
                    ((Player) cs).teleport(playerConf.getHome());
                    Methods.sendPlayerMessage(cs, "You teleported to home.");
                } else {
                    TeleportDelay.schedulePlayer(((Player) cs), playerConf.getHome());
                    Methods.sendPlayerMessage(cs, "Don't move! You will be teleported in " + Methods.red(String.valueOf(TeleportDelay.getDelay())) + " seconds.");
                }
            } else {
                Methods.sendPlayerMessage(cs, ChatColor.RED + "Home not set.");
            }
        } else if (args.length == 1) {
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.home.custom")) {
                return true;
            }
            JPlayerConfig playerConf = new JPlayerConfig((Player) cs);
            if (playerConf.getHome(args[0]) != null) {
                if (TeleportDelay.getDelay() < 1) {
                    ((Player) cs).teleport(playerConf.getHome(args[0]));
                    Methods.sendPlayerMessage(cs, "You teleported to " + Methods.red(args[0]) + ".");
                } else {
                    TeleportDelay.schedulePlayer(((Player) cs), playerConf.getHome(args[0]));
                    Methods.sendPlayerMessage(cs, "Don't move! You will be teleported in " + Methods.red(String.valueOf(TeleportDelay.getDelay())) + " seconds.");
                }
            } else {
                Methods.sendPlayerMessage(cs, "Your home " + Methods.red(args[0]) + " is not set.");
            }
        } else {

            return false;
        }
        return true;
    }
}