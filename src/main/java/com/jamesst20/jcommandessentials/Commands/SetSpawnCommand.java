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

import com.jamesst20.config.JYamlConfiguration;
import com.jamesst20.jcommandessentials.JCMDEssentials.JCMDEss;
import com.jamesst20.jcommandessentials.Utils.Methods;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.setspawn")) {
            return true;
        }
        if (Methods.isConsole(cs)) {
            Methods.sendPlayerMessage(cs, ChatColor.RED + "The console can't use /setspawn.");
            return true;
        }
        if (args.length == 0) {
            Location location = ((Player) cs).getLocation();
            setDefaultSpawn(location);
            ((Player) cs).getWorld().setSpawnLocation((int) location.getX(), (int) location.getY(), (int) location.getZ());
            Methods.sendPlayerMessage(cs, "Default spawn set to your location.");
            return true;
        } else {
            return false;
        }
    }

    private void setDefaultSpawn(Location loc) {
        JYamlConfiguration spawnConfig = new JYamlConfiguration(JCMDEss.plugin, "spawns.yml");
        spawnConfig.set("spawns.default.world", loc.getWorld().getName());
        spawnConfig.set("spawns.default.x", loc.getX());
        spawnConfig.set("spawns.default.y", loc.getY());
        spawnConfig.set("spawns.default.z", loc.getZ());
        spawnConfig.set("spawns.default.yaw", loc.getYaw());
        spawnConfig.set("spawns.default.pitch", loc.getPitch());
        spawnConfig.saveConfig();
    }
}