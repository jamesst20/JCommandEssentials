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

public class HealCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (args.length == 0) {
            if (Methods.isConsole(cs)) {
                Methods.sendPlayerMessage(cs, ChatColor.RED + "The console can't heal itself.");
                return true;
            }
            if (!Methods.hasPermission(cs, "JCMDEss.commands.heal.self")) {
                return true;
            }
            ((Player) cs).setFoodLevel(20);
            ((Player) cs).setHealth(20);
            Methods.sendPlayerMessage(cs, "You healed yourself!");
            return true;
        } else if (args.length == 1) {
            if (!Methods.hasPermission(cs, "JCMDEss.commands.heal.others")) {
                return true;
            }
            Player player = Bukkit.getServer().getPlayer(args[0]);
            if (player != null) {
                player.setFoodLevel(20);
                player.setHealth(20);
                Methods.sendPlayerMessage(cs, "You healed " + Methods.red(player.getDisplayName()) + ".");
                Methods.sendPlayerMessage(player, "You have been healed.");
                return true;
            } else {
                Methods.playerNotFound(cs, args[0]);
                return true;
            }
        } else {
            return false;
        }
    }
}
