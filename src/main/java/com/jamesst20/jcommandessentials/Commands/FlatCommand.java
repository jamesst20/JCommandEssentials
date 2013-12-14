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
import static java.lang.Math.pow;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author James
 */
public class FlatCommand implements CommandExecutor {

    private final int maxBlocksChangePerTick = 50000;
    private final int radiusLimit = 100;
    private int taskID;

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (Methods.isConsole(cs)) {
            Methods.sendPlayerMessage(cs, ChatColor.RED + "The console can't use this command.");
            return true;
        }
        if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.flat")) {
            return true;
        }
        if (taskID != 0) {
            Methods.sendPlayerMessage(cs, ChatColor.RED + "Please wait until previous request is over.");
            return true;
        }
        if (args.length == 1) {
            try {
                final Player p = (Player) cs;
                final World world = p.getWorld();
                final int radius = Integer.parseInt(args[0]);
                final Location pLocation = p.getLocation();
                final long blocksCountToLoopThrough = (long) pow(radius * 2 + 1, 2) * radius;

                if (radius > radiusLimit) {
                    Methods.sendPlayerMessage(cs, ChatColor.RED + "Sorry, the radius limit is " + Methods.red(String.valueOf(radiusLimit)) + ".");
                    return true;
                }

                Methods.sendPlayerMessage(cs, "Looping through " + Methods.red(String.valueOf(blocksCountToLoopThrough)) + " blocks.");
                Methods.sendPlayerMessage(cs, "Task divided in " + Methods.red(String.valueOf(Math.round(blocksCountToLoopThrough / maxBlocksChangePerTick + 0.5))) + " ticks.");

                taskID = Bukkit.getScheduler().runTaskTimer(JCMDEss.plugin, new Runnable() {
                    private int x = (int) pLocation.getX() - radius;
                    private int z = (int) pLocation.getZ() - radius;
                    private int y = (int) pLocation.getY();

                    @Override
                    public void run() {
                        long blocksChanged = 0L;
                        for (; x < pLocation.getX() + radius; x++) {
                            for (; z < pLocation.getZ() + radius; z++) {
                                for (; y < pLocation.getY() + radius; y++) {
                                    if (blocksChanged >= maxBlocksChangePerTick) {
                                        return;
                                    }
                                    new Location(world, x, y, z).getBlock().setType(Material.AIR);
                                    blocksChanged++;
                                }
                                y = (int) pLocation.getY();
                            }
                            z = (int) pLocation.getZ() - radius;
                        }
                        Methods.sendPlayerMessage(p, "Done flattening ground.");
                        Bukkit.getScheduler().cancelTask(taskID);
                        taskID = 0;
                    }
                }, 0, 1).getTaskId();
            } catch (NumberFormatException e) {
                Methods.sendPlayerMessage(cs, ChatColor.RED + "This is not a valid radius.");
            }
        } else {
            return false;
        }
        return true;
    }
}
