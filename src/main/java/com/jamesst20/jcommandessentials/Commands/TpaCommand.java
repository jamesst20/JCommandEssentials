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

import com.jamesst20.jcommandessentials.JCMDEssentials.JCMDEss;
import com.jamesst20.jcommandessentials.Utils.Methods;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpaCommand implements CommandExecutor {

    public static Map<String, String> tpaPlayers = new HashMap<String, String>();

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (Methods.isConsole(cs)) {
            Methods.sendPlayerMessage(cs, ChatColor.RED + "The console can't use tpa.");
            return true;
        } else if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.tpa")) {
            return true;
        } else if (args.length != 1) {
            return false;
        }
        final Player from = ((Player) cs);
        final Player to = Bukkit.getServer().getPlayer(args[0]);
        if (from != null && to != null) {
            if (tpaPlayers.containsKey(from.getName()) || tpaPlayers.containsKey(to.getName()) || tpaPlayers.containsValue(from.getName()) || tpaPlayers.containsValue(to.getName()) || TpaHereCommand.tpaPlayers.containsKey(from.getName()) || TpaHereCommand.tpaPlayers.containsKey(to.getName()) || TpaHereCommand.tpaPlayers.containsValue(from.getName()) || TpaHereCommand.tpaPlayers.containsValue(to.getName())) {
                Methods.sendPlayerMessage(cs, ChatColor.RED + "Error : You or " + to.getDisplayName() + " has already a pending request.");
                return true;
            }
            tpaPlayers.put(to.getName(), from.getName()); // Save first the one who will type /tpaccept
            Methods.sendPlayerMessage(from, "You asked to tp to " + Methods.red(to.getDisplayName()) + ". Waiting for an answer.");
            Methods.sendPlayerMessage(to, Methods.red(from.getDisplayName()) + " has asked to teleport to you.");
            Methods.sendPlayerMessage(to, "Agree : " + Methods.red("/tpaccept"));
            Methods.sendPlayerMessage(to, "Disagree : " + Methods.red("/tpdeny"));
            JCMDEss.plugin.getServer().getScheduler().scheduleSyncDelayedTask(JCMDEss.plugin, new Runnable() {
                @Override
                public void run() {
                    if (tpaPlayers.containsKey(to.getName())) {
                        tpaPlayers.remove(to.getName());
                        Methods.sendPlayerMessage(from, Methods.red(to.getDisplayName()) + " didn't answer your teleport request.");
                        Methods.sendPlayerMessage(to, "You didn't answer " + Methods.red(from.getDisplayName()) + " teleport request.");
                    }
                }
            }, 35L * 20L);
            return true;
        } else {
            Methods.playerNotFound(cs, args[0]);
            return true;
        }
    }
}
