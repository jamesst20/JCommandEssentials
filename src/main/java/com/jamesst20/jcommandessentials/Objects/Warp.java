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
package com.jamesst20.jcommandessentials.Objects;

import com.jamesst20.config.JYamlConfiguration;
import com.jamesst20.jcommandessentials.JCMDEssentials.JCMDEss;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 *
 * @author James
 */
public class Warp {

    public World world;
    public double x, y, z;
    public float yaw, pitch;
    public String warpName;

    public Warp(String warpName, World world, double x, double y, double z, float yaw, float pitch) {
        this.warpName = warpName;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    /**
     * Saves the warp into a config file.
     *
     * @return false if warp already exists, otherwise true.
     */
    public boolean save() {
        JYamlConfiguration config = new JYamlConfiguration(JCMDEss.plugin, "warps.yml");
        if (config.getConfig().get("warps." + warpName) == null) {
            config.getConfig().set("warps." + warpName + ".world", world.getName());
            config.getConfig().set("warps." + warpName + ".x", x);
            config.getConfig().set("warps." + warpName + ".y", y);
            config.getConfig().set("warps." + warpName + ".z", z);
            config.getConfig().set("warps." + warpName + ".yaw", yaw);
            config.getConfig().set("warps." + warpName + ".pitch", pitch);
            config.saveConfig();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Saves and overrides the warp into a config file.
     */
    public void saveOverride() {
        JYamlConfiguration config = new JYamlConfiguration(JCMDEss.plugin, "warps.yml");
        config.getConfig().set("warps." + warpName + ".world", world.getName());
        config.getConfig().set("warps." + warpName + ".x", x);
        config.getConfig().set("warps." + warpName + ".y", y);
        config.getConfig().set("warps." + warpName + ".z", z);
        config.getConfig().set("warps." + warpName + ".yaw", yaw);
        config.getConfig().set("warps." + warpName + ".pitch", pitch);
        config.saveConfig();
    }

    /**
     * Removes this warp from config file.
     * 
     * @return true if the warp existed, false if the warp didn't exist.
     */
    public boolean remove() {
        JYamlConfiguration config = new JYamlConfiguration(JCMDEss.plugin, "warps.yml");
        if (config.getConfig().getConfigurationSection("warps." + warpName) != null) {
            config.getConfig().set("warps." + warpName, null);
            config.saveConfig();
            return true;
        }
        return false;
    }

    /**
     * Get the location of this warp
     *
     * @return a location object for this warp, null if this warp doesn't exist.
     */
    public Location getLocation() {
        if (world != null) {
            return new Location(world, x, y, z, yaw, pitch);
        }
        return null;
    }

    /**
     * Load a warp from the config file by name.
     *
     * @param warpName The warp name.
     * @return a warp instance, null if the warp is not found.
     */
    public static Warp getWarpByName(String warpName) {
        JYamlConfiguration config = new JYamlConfiguration(JCMDEss.plugin, "warps.yml");
        World world = Bukkit.getServer().getWorld(config.getConfig().getString("warps." + warpName + ".world", ""));
        double x = config.getConfig().getDouble("warps." + warpName + ".x");
        double y = config.getConfig().getDouble("warps." + warpName + ".y");
        double z = config.getConfig().getDouble("warps." + warpName + ".z");
        float yaw = (float) config.getConfig().getDouble("warps." + warpName + ".yaw");
        float pitch = (float) config.getConfig().getDouble("warps." + warpName + ".pitch");

        if ((!warpName.isEmpty()) && world != null) {
            return new Warp(warpName, world, x, y, z, yaw, pitch);
        }
        return null;
    }
    
    public static List<String> getWarpsName(){
        JYamlConfiguration config = new JYamlConfiguration(JCMDEss.plugin, "warps.yml");
        List<String> warpsName = new ArrayList<String>();
        if (config.getConfig().getConfigurationSection("warps") != null) {
            for (String warpName : config.getConfig().getConfigurationSection("warps").getKeys(false)) {
                warpsName.add(warpName);
            }
        }
        return warpsName;
    }

}
