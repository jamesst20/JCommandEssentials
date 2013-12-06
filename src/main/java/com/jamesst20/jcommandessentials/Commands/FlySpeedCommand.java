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

public class FlySpeedCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (args.length < 1 || args.length > 3) {
            return false;
        }
        if (args[0].equalsIgnoreCase("get")) {
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.flyspeed.get.self")) {
                return true;
            }
            if (args.length == 1) {
                if (Methods.isConsole(cs)) {
                    Methods.sendPlayerMessage(cs, ChatColor.RED + "The console can only get fly speed of other player.");
                    return true;
                }
                Player player = ((Player) cs);
                Methods.sendPlayerMessage(cs, "Your fly speed is " + Methods.red(player.getFlySpeed() * 10F + "."));
                return true;
            } else if (args.length == 2) {
                if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.flyspeed.get.others")) {
                    return true;
                }
                Player player = Bukkit.getServer().getPlayer(args[1]);
                if (player != null) {
                    Methods.sendPlayerMessage(
                            cs,
                            "Fly speed of " + Methods.red(player.getDisplayName()) + " is "
                                    + Methods.red(String.valueOf(player.getFlySpeed() * 10F)) + ".");
                } else {
                    Methods.playerNotFound(cs, args[0]);
                    return true;
                }
                return true;
            } else {
                return false;
            }
        } else if (args[0].equalsIgnoreCase("set")) {
            if (args.length == 2) {
                if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.flyspeed.set.self")) {
                    return true;
                }
                if (Methods.isConsole(cs)) {
                    Methods.sendPlayerMessage(cs, ChatColor.RED + "The console can only change speed of other players.");
                    return true;
                }
                Player player = ((Player) cs);
                try {
                    float flySpeed = Float.parseFloat(args[1]) / 10F;
                    player.setFlySpeed(flySpeed);
                    Methods.sendPlayerMessage(cs, "Your flying speed has been set to " + Methods.red(args[1]) + ".");
                    return true;
                } catch (NumberFormatException e) {
                    Methods.sendPlayerMessage(cs, ChatColor.RED + "Error getting the speed. Did you enter numbers?");
                    return true;
                } catch (IllegalArgumentException e) {
                    Methods.sendPlayerMessage(cs, "That number is too high. Maximum is " + Methods.red("10") + ".");
                    return true;
                }
            } else if (args.length == 3) {
                if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.flyspeed.set.others")) {
                    return true;
                }
                Player player = Bukkit.getServer().getPlayer(args[2]);
                if (player != null) {
                    try {
                        float flySpeed = Float.parseFloat(args[1]) / 10F;
                        player.setFlySpeed(flySpeed);
                        Methods.sendPlayerMessage(cs, "Flying speed of " + Methods.red(player.getDisplayName()) + " set to "
                                + Methods.red(args[1]) + ".");
                        Methods.sendPlayerMessage(player, "Your flying speed has been set to " + Methods.red(args[1]) + ".");
                        return true;
                    } catch (NumberFormatException e) {
                        Methods.sendPlayerMessage(cs, ChatColor.RED + "Error getting the speed. Did you enter numbers?");
                        return true;
                    } catch (IllegalArgumentException e) {
                        Methods.sendPlayerMessage(cs, "That number is too high. Maximum is " + Methods.red("10") + ".");
                        return true;
                    }
                } else {
                    Methods.playerNotFound(cs, args[2]);
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
