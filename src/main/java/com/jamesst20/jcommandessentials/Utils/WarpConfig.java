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
package com.jamesst20.jcommandessentials.Utils;

import com.jamesst20.config.JYamlConfiguration;
import com.jamesst20.jcommandessentials.JCMDEssentials.JCMDEss;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpConfig {

    static JCMDEss plugin = JCMDEss.plugin;
    static JYamlConfiguration config;
    public static HashMap<String, Location> warps = new HashMap<String, Location>();

    public static void createWarp(Player player, String name) {
        if (config.getConfig().get("warps." + name) == null) {
            config.getConfig().set("warps." + name + ".world", player.getLocation().getWorld().getName());
            config.getConfig().set("warps." + name + ".x", player.getLocation().getX());
            config.getConfig().set("warps." + name + ".y", player.getLocation().getY());
            config.getConfig().set("warps." + name + ".z", player.getLocation().getZ());
            config.getConfig().set("warps." + name + ".yaw", player.getLocation().getYaw());
            config.getConfig().set("warps." + name + ".pitch", player.getLocation().getPitch());
            config.saveConfig();
            warps.put(name, player.getLocation());
            Methods.sendPlayerMessage(player, "You have created a new warp : " + Methods.red(name) + ".");
        } else {
            Methods.sendPlayerMessage(player, ChatColor.RED + "This warp already exist.");
        }
    }

    public static void deleteWarp(CommandSender player, String name) {
        if (warps.containsKey(name)) {
            config.getConfig().set("warps." + name, null);
            config.saveConfig();
            warps.remove(name);
            Methods.sendPlayerMessage(player, "You have removed a warp named " + Methods.red(name) + ".");
        } else {
            Methods.sendPlayerMessage(player, ChatColor.RED + "The warp " + name + " doesn't exist.");
        }
    }

    public static void reloadWarps() {
        config = new JYamlConfiguration(plugin, "warps.yml");
        warps.clear();
        if (config.getConfig().getConfigurationSection("warps") != null) {
            for (String keyName : config.getConfig().getConfigurationSection("warps").getKeys(false)) {
                World world = Bukkit.getServer().getWorld(config.getConfig().getString("warps." + keyName + ".world"));
                double x = config.getConfig().getDouble("warps." + keyName + ".x");
                double y = config.getConfig().getDouble("warps." + keyName + ".y");
                double z = config.getConfig().getDouble("warps." + keyName + ".z");
                float yaw = (float) config.getConfig().getDouble("warps." + keyName + ".yaw");
                float pitch = (float) config.getConfig().getDouble("warps." + keyName + ".pitch");
                warps.put(keyName, new Location(world, x, y, z, yaw, pitch));
            }
        }
        config.saveConfig();
    }

    public static Location getWarpLocation(String name) {
        if (warps.containsKey(name)) {
            return warps.get(name);
        } else {
            return null;
        }
    }

    public static ArrayList<String> getWarpsName() {
        ArrayList<String> warpsName = new ArrayList<String>();
        for (String name : warps.keySet()) {
            warpsName.add(name);
        }
        return warpsName;
    }
}
