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

import com.jamesst20.jcommandessentials.Objects.Warp;
import com.jamesst20.jcommandessentials.Utils.Methods;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommand implements CommandExecutor {

    public static String PERMISSIONS_EDIT_WARP = "JCMDEss.commands.warp.edit";
    public static String PERMISSIONS_LIST_WARP = "JCMDEss.commands.warp.list";
    public static String PERMISSIONS_TP_WARP = "JCMDEss.commands.warp.tp.";
    public static String PERMISSIONS_WARPSIGNS_CREATE = "JCMDEss.commands.warp.signs";

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("list")) {
                if (!Methods.hasPermissionTell(cs, PERMISSIONS_LIST_WARP)) {
                    return true;
                }
                List<String> warpsName = Warp.getWarpsName();
                if (warpsName.size() < 1) {
                    Methods.sendPlayerMessage(cs, ChatColor.RED + "No warps have been created yet.");
                    return true;
                }
                StringBuilder msgToSend = new StringBuilder("Available Warps (" + Methods.red(String.valueOf(warpsName.size())) + ") : ");
                for (String name : warpsName) {
                    msgToSend.append(Methods.red(name)).append(", ");
                }
                msgToSend = Methods.replaceLast(msgToSend, ", ", ".");
                Methods.sendPlayerMessage(cs, msgToSend.toString());
                return true;
            } else {
                if (Methods.isConsole(cs)) {
                    Methods.sendPlayerMessage(cs, ChatColor.RED + "The console can't teleport to warps.");
                    return true;
                }
                if (!Methods.hasPermission(cs, PERMISSIONS_TP_WARP + args[0])) {
                    Methods.sendPlayerMessage(cs, ChatColor.RED + "You are not allowed to teleport to this warp.");
                    return true;
                }
                Warp warp = Warp.getWarpByName(args[0]);
                if (warp != null) {
                    Location location = warp.getLocation();
                    if (location != null) {
                        Player player = (Player) cs;
                        player.teleport(location);
                        Methods.sendPlayerMessage(player, "You have been teleported to " + args[0] + ".");
                    } else {
                        Methods.sendPlayerMessage(cs, ChatColor.RED + "This warp location is invalid.");
                        return true;
                    }
                } else {
                    Methods.sendPlayerMessage(cs, ChatColor.RED + "The warp " + args[0] + " doesn't exist.");
                    return true;
                }
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("add")) {
                if (Methods.isConsole(cs)) {
                    Methods.sendPlayerMessage(cs, ChatColor.RED + "The console can't add warps.");
                    return true;
                }
                if (!Methods.hasPermissionTell(cs, PERMISSIONS_EDIT_WARP)) {
                    return true;
                }
                Player p = (Player) cs;
                if (new Warp(args[1], p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch()).save()) {
                    Methods.sendPlayerMessage(p, "You have created a new warp named " + Methods.red(args[1]) + ".");
                } else {
                    Methods.sendPlayerMessage(p, ChatColor.RED + "Sorry, this warp already exists.");
                }
            } else if (args[0].equalsIgnoreCase("delete")) {
                if (!Methods.hasPermissionTell(cs, PERMISSIONS_EDIT_WARP)) {
                    return true;
                }
                Warp warp = Warp.getWarpByName(args[1]);
                if (warp != null) {
                    warp.remove();
                    Methods.sendPlayerMessage(cs, "You have deleted a warp named " + Methods.red(args[1]) + ".");
                } else {
                    Methods.sendPlayerMessage(cs, ChatColor.RED + "Sorry, this warp doesn't exist.");
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
}
