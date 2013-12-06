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
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class JCommandEssentialsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (args.length == 0) {
            if (Methods.isConsole(cs)) {
                Methods.sendPlayerMessage(cs, "==========================================");
                Methods.sendPlayerMessage(cs, "=    JCommandEssentials by Jamesst20     =");
                Methods.sendPlayerMessage(cs, "=     Get Version : /JCMDEss version     =");
                Methods.sendPlayerMessage(cs, "=    Reloads Config : /JCMDEss reload    =");
                Methods.sendPlayerMessage(cs, "==========================================");
            } else {
                Methods.sendPlayerMessage(cs, "==========================================");
                Methods.sendPlayerMessage(cs, "=        JCommandEssentials by Jamesst20        =");
                Methods.sendPlayerMessage(cs, "=          Get Version : /JCMDEss version          =");
                Methods.sendPlayerMessage(cs, "=         Reloads Config : /JCMDEss reload        =");
                Methods.sendPlayerMessage(cs, "==========================================");
            }
        } else if (args[0].equalsIgnoreCase("reload") && args.length == 1) {
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.reload")) {
                return true;
            }
            JCMDEss.plugin.reloadConfig();
            Methods.sendPlayerMessage(cs, "Config has been reloaded!");
        } else if (args[0].equalsIgnoreCase("version") && args.length == 1) {
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.version")) {
                return true;
            }
            Methods.sendPlayerMessage(cs, "Version : " + ChatColor.RED + JCMDEss.plugin.getDescription().getVersion());
        } else {
            return false;
        }
        return true;
    }
}