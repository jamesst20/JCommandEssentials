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

import com.jamesst20.jcommandessentials.Objects.JPlayer;
import com.jamesst20.jcommandessentials.Utils.Methods;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHomeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (Methods.isConsole(cs)) {
            Methods.sendPlayerMessage(cs, ChatColor.RED + "The console can't set home.");
            return true;
        }
        if (args.length == 0) {
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.setHome.default")) {
                return true;
            }
            JPlayer playerConf = new JPlayer((Player) cs);
            playerConf.setHome();
            Methods.sendPlayerMessage(cs, "Home set.");
        } else if (args.length == 1) {
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.setHome.custom")) {
                return true;
            }
            JPlayer playerConf = new JPlayer((Player) cs);
            playerConf.setHome(args[0]);
            Methods.sendPlayerMessage(cs, "Home " + Methods.red(args[0]) + " set.");
        } else {
            return false;
        }
        return true;
    }
}