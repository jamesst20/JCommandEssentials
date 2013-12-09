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
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 *
 * @author James
 */
public class FreeMeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (args.length == 0) {
            if (Methods.isConsole(cs)) {
                Methods.sendPlayerMessage(cs, ChatColor.RED + "The console can't use this command.");
                return true;
            }
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.freeme.self")) {
                return true;
            }
            Player player = (Player) cs;
            for (Entity e : player.getNearbyEntities(10.0D, 10.0D, 10.0D)) {
                e.setVelocity(new Vector(0, 2, 0));
                e.setFireTicks(200);
            }
            Methods.sendPlayerMessage(cs, "You have thrown and set on fire enemies");
        } else if (args.length == 1) {
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.freeme.others")) {
                return true;
            }
            Player player = Bukkit.getServer().getPlayer(args[0]);
            if (player != null) {
                for (Entity e : player.getNearbyEntities(10.0D, 10.0D, 10.0D)) {
                    e.setVelocity(new Vector(0, 2, 0));
                    e.setFireTicks(200);
                }
                Methods.sendPlayerMessage(cs, "You have thrown and set on fire enemies around " + Methods.red(player.getDisplayName()) + ".");
                Methods.sendPlayerMessage(player, "You have thrown and set on fire enemies.");
            } else {
                Methods.playerNotFound(cs, args[0]);
                return true;
            }
        } else {

        }
        return true;
    }

}
