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
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LockCommand implements CommandExecutor {

    public static boolean serverLocked = false;
    public static ArrayList<String> lockedPlayersName = new ArrayList<String>();

    public static boolean byPass(Player player) {
        if(serverLocked || lockedPlayersName.contains(player.getName())){
            if(Methods.hasPermission(player, "JCMDEss.bypass.lock")) return true;
            return false;
        }
        return true;
    }
    
    public static void sendErrorMessage(Player p){
        if(serverLocked){
            Methods.sendPlayerMessage(p, ChatColor.RED + "The server is locked.");
        }else{
            Methods.sendPlayerMessage(p, ChatColor.RED + "You are locked.");
        }
    }

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (args.length == 0) {
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.lock.server")) {
                return true;
            }
            if (!serverLocked) {
                serverLocked = true;
                Methods.broadcastMessage("The server is now locked!");
            } else {
                serverLocked = false;
                Methods.broadcastMessage("The server is now unlocked!");
            }
        } else if (args.length == 1) {
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.lock.players")) {
                return true;
            }
            Player p = Bukkit.getServer().getPlayer(args[0]);
            if(p != null){
                if(lockedPlayersName.contains(p.getName())){
                    lockedPlayersName.remove(p.getName());
                    Methods.sendPlayerMessage(cs, "The player " + Methods.red(p.getName()) + " is now " + Methods.red("unlocked") + ".");
                    Methods.sendPlayerMessage(p, "You are now " + Methods.red("unlocked") + ".");
                }else{
                    lockedPlayersName.add(p.getName());
                    Methods.sendPlayerMessage(cs, "The player " + Methods.red(p.getName()) + " is now " + Methods.red("locked") + ".");
                    Methods.sendPlayerMessage(p, "You are now " + Methods.red("locked") + ".");
                }
            }else{
                Methods.playerNotFound(cs, args[0]);
            }
        } else {
            return false;
        }
        return true;
    }
}
