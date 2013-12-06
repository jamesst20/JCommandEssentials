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

public class StaffCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.staff.send")) {
            return true;
        }
        if (args.length < 1) {
            return false;
        }
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            message.append(args[i]).append(" ");
        }
        if (Methods.isConsole(cs)) {
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                if (Methods.hasPermission(player, "JCMDEss.commands.staff.see")) {
                    player.sendMessage(ChatColor.GOLD + "[Staff]" + ChatColor.DARK_AQUA + "Console" + ChatColor.WHITE + ": "
                            + ChatColor.LIGHT_PURPLE + message);
                }
            }
            Methods.log(ChatColor.GOLD + "[Staff]" + ChatColor.DARK_AQUA + "Console" + ChatColor.WHITE + ": "
                    + ChatColor.LIGHT_PURPLE + message);
            return true;
        } else {
            Player from = ((Player) cs);
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                if (Methods.hasPermission(player, "JCMDEss.commands.staff.see")) {
                    player.sendMessage(ChatColor.GOLD + "[Staff]" + ChatColor.DARK_AQUA + from.getDisplayName()
                            + ChatColor.WHITE + ": " + ChatColor.LIGHT_PURPLE + message);
                }
            }
            Methods.log(ChatColor.GOLD + "[Staff]" + ChatColor.DARK_AQUA + from.getDisplayName() + ChatColor.WHITE + ": "
                    + ChatColor.LIGHT_PURPLE + message);
            return true;
        }
    }
}
