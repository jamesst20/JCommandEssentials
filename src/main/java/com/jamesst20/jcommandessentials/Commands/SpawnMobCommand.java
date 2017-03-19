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
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SpawnMobCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.spawnmob")) {
            return true;
        }
        if (Methods.isConsole(cs)) {
            Methods.sendPlayerMessage(cs, "The console can't spawn mobs.");
            return true;
        }
        if (args.length == 0) {
            StringBuilder x = new StringBuilder();
            for (EntityType e : EntityType.values()) {
                x.append("&c").append(formatMobName(e.toString())).append("&b").append(", ");
            }
            Methods.sendPlayerMessage(cs, "You can spawn the following mobs/entities: ");
            Methods.sendPlayerMessage(cs, Methods.coloring(x.toString()));
            return true;
        } else if (args.length == 2) {
            int count;
            Player player = ((Player) cs);
            try {
                count = Integer.parseInt(args[1]);
                for (int i = 0; i < count; i++) {
                    Set<Material> transparents = new HashSet<Material>(Arrays.asList(Material.AIR));
                    player.getWorld().spawnEntity(
                            player.getTargetBlock(transparents, 10).getLocation().getBlock().getRelative(0, 1, 0).getLocation(),
                            EntityType.valueOf(args[0].toUpperCase()));
                }
                Methods.sendPlayerMessage(
                        cs,
                        "You spawned " + Methods.red(String.valueOf(count)) + " "
                                + Methods.red(EntityType.valueOf(args[0].toUpperCase()).getName()) + ".");
                return true;
            } catch (NumberFormatException e) {
                Methods.sendPlayerMessage(cs, ChatColor.RED + "You must enter numbers for quantity.");
                return true;
            } catch (IllegalArgumentException e) {
                Methods.sendPlayerMessage(cs, ChatColor.RED + "Invalid entity.");
                return true;
            }
        } else {
            return false;
        }
    }

    private String formatMobName(String str) {
        if (str.contains("_")) {
            String[] splittedStr = str.split("_");
            StringBuilder formattedStr = new StringBuilder();
            for (String part : splittedStr) {
                formattedStr.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1).toLowerCase()).append("_");
            }
            return Methods.replaceLast(formattedStr, "_", "").toString();
        } else {
            return Character.toUpperCase(str.charAt(0)) + str.substring(1).toLowerCase();
        }
    }
}
