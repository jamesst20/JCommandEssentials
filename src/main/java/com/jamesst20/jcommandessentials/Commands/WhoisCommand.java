/*
 * Copyright (C) 2012 Jamesst20

 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.jamesst20.jcommandessentials.Commands;

import com.jamesst20.jcommandessentials.Objects.JOfflinePlayerConfig;
import com.jamesst20.jcommandessentials.Utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WhoisCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.whois")) {
            return true;
        }
        if (args.length != 1) {
            return false;
        }
        JOfflinePlayerConfig oPlayer = new JOfflinePlayerConfig(Bukkit.getServer().getOfflinePlayer(args[0]));
        if (!oPlayer.playerExist()) {
            if (Methods.isConsole(cs)) {
                Methods.log(ChatColor.RED + "No config found for the player " + args[0] + ".");
            } else {
                Methods.sendPlayerMessage(cs, ChatColor.RED + "No config found for the player " + args[0] + ".");
            }
            return true;
        }
        String joinDate = ChatColor.AQUA + oPlayer.getJoinDate();
        String lastLogin = ChatColor.GREEN + oPlayer.getLastLogin();
        String lastLogout = ChatColor.RED + oPlayer.getLastLogout();
        String lastIP = ChatColor.AQUA + oPlayer.getLastIP();
        String isOnline = Bukkit.getServer().getPlayer(args[0]) != null ? ChatColor.GREEN + "YES" : ChatColor.RED + "NO";
        if (Methods.isConsole(cs)) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "==================================================");
            Methods.log(ChatColor.GOLD + "Player : " + ChatColor.AQUA + args[0] + ".");
            Methods.log(ChatColor.GOLD + "Online : " + isOnline + "." + "");
            Methods.log(ChatColor.GOLD + "Join Date : " + joinDate + ".");
            Methods.log(ChatColor.GOLD + "Last Login : " + lastLogin + ".");
            Methods.log(ChatColor.GOLD + "Last Logout : " + lastLogout + ".");
            Methods.log(ChatColor.GOLD + "Last IP : " + lastIP + ".");
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "==================================================");
        } else {
            Bukkit.getServer().getPlayer(cs.getName()).sendMessage(ChatColor.AQUA + "==================================================");
            Methods.sendPlayerMessage(cs, ChatColor.GOLD + "Player : " + ChatColor.AQUA + args[0] + ".");
            Methods.sendPlayerMessage(cs, ChatColor.GOLD + "Online : " + isOnline + "." + "");
            Methods.sendPlayerMessage(cs, ChatColor.GOLD + "Join Date : " + joinDate + ".");
            Methods.sendPlayerMessage(cs, ChatColor.GOLD + "Last Login : " + lastLogin + ".");
            Methods.sendPlayerMessage(cs, ChatColor.GOLD + "Last Logout : " + lastLogout + ".");
            Methods.sendPlayerMessage(cs, ChatColor.GOLD + "Last IP : " + lastIP + ".");
            Bukkit.getServer().getPlayer(cs.getName()).sendMessage(ChatColor.AQUA + "==================================================");
        }
        return true;
    }
}
