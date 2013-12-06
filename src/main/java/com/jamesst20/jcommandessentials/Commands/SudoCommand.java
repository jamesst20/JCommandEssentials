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
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SudoCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.sudo")) {
            return true;
        }
        if (args.length < 2) {
            return false;
        }
        StringBuilder theCommand = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            theCommand.append(args[i]).append(" ");
        }
        theCommand = Methods.replaceLast(theCommand, " ", "");
        Player player = Bukkit.getServer().getPlayer(args[0]);
        if (player != null) {
            player.performCommand(theCommand.toString());
            Methods.sendPlayerMessage(cs,
                    "Forced " + Methods.red(player.getDisplayName()) + " to run: '" + Methods.red(theCommand.toString()) + "'");
            return true;
        } else {
            Methods.playerNotFound(cs, args[0]);
            return true;
        }
    }
}
