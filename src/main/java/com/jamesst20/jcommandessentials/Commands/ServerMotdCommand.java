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
import com.jamesst20.jcommandessentials.Utils.ServerMotd;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ServerMotdCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.servermotd")) {
            return true;
        }
        if (args.length < 1) {
            return false;
        }
        if (args[0].equalsIgnoreCase("set")) {
            StringBuilder motd = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                motd.append(args[i]).append(" ");
            }
            ServerMotd.setServerMotd(motd.toString());
            Methods.sendPlayerMessage(cs, "Server motd set!");
        } else if (args[0].equalsIgnoreCase("enable")) {
            ServerMotd.enableServerMotd();
            Methods.sendPlayerMessage(cs, "Server list motd is now " + Methods.red("enabled") + "!");
        } else if (args[0].equalsIgnoreCase("disable")) {
            ServerMotd.disableServerMotd();
            Methods.sendPlayerMessage(cs, "Server list motd is now " + Methods.red("disabled") + "!");
        } else {
            return false;
        }
        return true;
    }
}
