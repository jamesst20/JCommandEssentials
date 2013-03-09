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

public class TpaHereCommand implements CommandExecutor {
    public static Map<String, String> tpaPlayers = new HashMap<String, String>();

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (Methods.isConsole(cs)) {
            Methods.sendPlayerMessage(cs, ChatColor.RED + "The console can't tpa.");
            return true;
        } else if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.tpahere")) {
            return true;
        } else if (args.length != 1) {
            return false;
        }
        final Player playerAsked = ((Player) cs); //Destination Player
        final Player playerToTeleport = Bukkit.getServer().getPlayer(args[0]);
        if (playerAsked != null && playerToTeleport != null) {
            if(tpaPlayers.containsKey(playerToTeleport.getName())||tpaPlayers.containsKey(playerAsked.getName())||tpaPlayers.containsValue(playerToTeleport.getName())||tpaPlayers.containsValue(playerAsked.getName())||TpaCommand.tpaPlayers.containsKey(playerToTeleport.getName())||TpaCommand.tpaPlayers.containsKey(playerAsked.getName())||TpaCommand.tpaPlayers.containsValue(playerToTeleport.getName())||TpaCommand.tpaPlayers.containsValue(playerAsked.getName())){ 
                Methods.sendPlayerMessage(cs, ChatColor.RED + "Error : You or " + playerToTeleport.getDisplayName() + " has already a pending request.");
                return true;
            }
            tpaPlayers.put(playerToTeleport.getName(), playerAsked.getName()); // Save first the one who will type /tpaccept
            Methods.sendPlayerMessage(playerAsked, "You asked to tp to you " + Methods.red(playerToTeleport.getDisplayName()) + ". Waiting for an answer.");
            Methods.sendPlayerMessage(playerToTeleport, Methods.red(playerAsked.getDisplayName()) + " wants to teleport you to him.");
            Methods.sendPlayerMessage(playerToTeleport, "Agree : " + Methods.red("/tpaccept"));
            Methods.sendPlayerMessage(playerToTeleport, "Disagree : " + Methods.red("/tpdeny"));
            JCMDEss.plugin.getServer().getScheduler().scheduleSyncDelayedTask(JCMDEss.plugin, new Runnable() {
                @Override
                public void run() {
                    if (tpaPlayers.containsKey(playerToTeleport.getName())) {
                        tpaPlayers.remove(playerToTeleport.getName());
                        Methods.sendPlayerMessage(playerAsked, Methods.red(playerToTeleport.getDisplayName()) + " didn't answer your teleport request.");
                        Methods.sendPlayerMessage(playerToTeleport, "You didn't answer " + Methods.red(playerAsked.getDisplayName()) + " teleport request.");
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