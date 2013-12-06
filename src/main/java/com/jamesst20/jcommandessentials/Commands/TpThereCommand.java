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
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpThereCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (Methods.isConsole(cs)) {
            Methods.sendPlayerMessage(cs, ChatColor.RED + "The console has no target block.");
            return true;
        }
        if (args.length == 0) {
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.tpthere.self")) {
                return true;
            }
            Player player = ((Player) cs);
            player.teleport(player.getTargetBlock(null, 0).getRelative(BlockFace.UP, 1).getLocation());
            Methods.sendPlayerMessage(player, "You teleported to the target block!");
            return true;
        } else if (args.length == 1) {
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.tpthere.others")) {
                return true;
            }
            Player player = Bukkit.getServer().getPlayer(args[0]);
            if (player != null) {
                player.teleport(((Player) cs).getTargetBlock(null, 0).getRelative(BlockFace.UP, 1).getLocation());
                Methods.sendPlayerMessage(cs, "You teleported " + Methods.red(player.getDisplayName()) + " to your target block.");
                Methods.sendPlayerMessage(player, "You have been teleported to " + Methods.red(cs.getName() + "'s") + " target block!");
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
