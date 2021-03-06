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
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TimeCommand implements CommandExecutor {

    int DAY_TIME = 200;
    int NIGHT_TIME = 14400;

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.time")) {
            return true;
        }
        if (args.length == 0) {
            if (cmd.equalsIgnoreCase("day")) {
                setDay(cs);
                return true;
            } else if (cmd.equals("night")) {
                setNight(cs);
                return true;
            } else {
                return false;
            }
        } else if (args.length == 1) {
            if (cmd.equalsIgnoreCase("day")) {
                setDay(cs, args[0]);
                return true;
            } else if (cmd.equals("night")) {
                setNight(cs, args[0]);
                return true;
            } else if (cmd.equalsIgnoreCase("time")) {
                if (args[0].equalsIgnoreCase("day")) {
                    setDay(cs);
                    return true;
                } else if (args[0].equalsIgnoreCase("night")) {
                    setNight(cs);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else if (args.length == 2) {
            if (cmd.equalsIgnoreCase("time")) {
                if (args[0].equalsIgnoreCase("day")) {
                    setDay(cs, args[1]);
                    return true;
                } else if (args[0].equalsIgnoreCase("night")) {
                    setNight(cs, args[1]);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void setDay(CommandSender cs) {
        if (Methods.isConsole(cs)) {
            Methods.sendPlayerMessage(cs, ChatColor.RED + "The console must specify a world.");
            return;
        }
        ((Player) cs).getWorld().setTime(DAY_TIME);
        Methods.sendPlayerMessage(cs, "Current world time have been set to " + Methods.red("day"));
    }

    private void setNight(CommandSender cs) {
        if (Methods.isConsole(cs)) {
            Methods.sendPlayerMessage(cs, ChatColor.RED + "The console must specify a world.");
            return;
        }
        ((Player) cs).getWorld().setTime(NIGHT_TIME);
        Methods.sendPlayerMessage(cs, "Current world time have been set to " + Methods.red("night"));
    }

    private void setDay(CommandSender cs, String worldStr) {
        World world = Bukkit.getServer().getWorld(worldStr);
        if (world != null) {
            world.setTime(DAY_TIME);
            Methods.sendPlayerMessage(cs, world.getName() + " time have been set to " + Methods.red("day"));
        } else {
            Methods.sendPlayerMessage(cs, "The world " + Methods.red(worldStr) + " couldn't be found.");
        }
    }

    private void setNight(CommandSender cs, String worldStr) {
        World world = Bukkit.getServer().getWorld(worldStr);
        if (world != null) {
            world.setTime(NIGHT_TIME);
            Methods.sendPlayerMessage(cs, world.getName() + " time have been set to " + Methods.red("night."));
        } else {
            Methods.sendPlayerMessage(cs, "The world " + Methods.red(worldStr) + " couldn't be found.");
        }
    }
}
