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

public class TpAcceptCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (Methods.isConsole(cs)) {
            Methods.sendPlayerMessage(cs, ChatColor.RED + "Console can't accept a tp.");
            return true;
        } else if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.tpaaccept")) {
            return true;
        } else if (args.length == 0) {
            Player playerWhoAccept = ((Player) cs);
            Player secondPlayer;
            if (TpaCommand.tpaPlayers.containsKey(playerWhoAccept.getName())) {
                //Dealing with /tpa
                secondPlayer = Bukkit.getServer().getPlayer(TpaCommand.tpaPlayers.get(playerWhoAccept.getName()));
                if (secondPlayer != null) {
                    Methods.sendPlayerMessage(secondPlayer, Methods.red(playerWhoAccept.getDisplayName()) + " has accepted your teleport request.");
                    Methods.sendPlayerMessage(playerWhoAccept, "You accepted " + Methods.red(secondPlayer.getDisplayName()) + "'s teleport request.");
                    secondPlayer.teleport(playerWhoAccept);
                    TpaCommand.tpaPlayers.remove(playerWhoAccept.getName());
                    return true;
                } else {
                    Methods.sendPlayerMessage(playerWhoAccept, ChatColor.RED + "Player offline.");
                    TpaCommand.tpaPlayers.remove(playerWhoAccept.getName());
                    return true;
                }
            } else if (TpaHereCommand.tpaPlayers.containsKey(playerWhoAccept.getName())) {
                //Dealing with /tpahere
                secondPlayer = Bukkit.getServer().getPlayer(TpaHereCommand.tpaPlayers.get(playerWhoAccept.getName()));
                if (secondPlayer != null) {
                    Methods.sendPlayerMessage(secondPlayer, Methods.red(playerWhoAccept.getDisplayName()) + " has accepted your teleport request.");
                    Methods.sendPlayerMessage(playerWhoAccept, "You accepted " + Methods.red(secondPlayer.getDisplayName()) + "'s teleport request.");
                    playerWhoAccept.teleport(secondPlayer);
                    TpaHereCommand.tpaPlayers.remove(playerWhoAccept.getName());
                    return true;
                } else {
                    Methods.sendPlayerMessage(playerWhoAccept, ChatColor.RED + "Player offline.");
                    TpaHereCommand.tpaPlayers.remove(playerWhoAccept.getName());
                    return true;
                }
            } else {
                Methods.sendPlayerMessage(cs, ChatColor.RED + "You have no pending teleport request.");
                return true;
            }
        } else {
            return false;
        }
    }
}
