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
import org.bukkit.util.Vector;

/**
 *
 * @author James
 */
public class ShootCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (args.length == 0) {
            if (Methods.isConsole(cs)) {
                Methods.sendPlayerMessage(cs, ChatColor.RED + "The console cannot shoot itself.");
                return true;
            }
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.shoot.self")) {
                return true;
            }
            Player player = (Player) cs;
            player.setVelocity(new Vector(0, 10, 0)); //Sadly, bukkit doesn't allow more than 10.

            Methods.sendPlayerMessage(cs, "You have been shot in the air.");
        } else if (args.length == 1) {
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.shoot.others")) {
                return true;
            }
            Player player = Bukkit.getServer().getPlayer(args[0]);
            if (player != null) {
                player.setVelocity(new Vector(0, 10, 0)); //Sadly, bukkit doesn't allow more than 10.

                Methods.sendPlayerMessage(cs, "You have shot in the air " + Methods.red(args[0]) + ".");
                Methods.sendPlayerMessage(player, "You have been shot in the air by " + Methods.red(cs.getName()) + ".");
            } else {
                Methods.playerNotFound(cs, args[0]);
            }
        } else {
            return false;
        }
        return true;
    }

}
