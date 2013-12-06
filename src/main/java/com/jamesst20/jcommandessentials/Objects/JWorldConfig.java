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
import org.bukkit.Location;
import org.bukkit.World;

public class JWorldConfig {

    JCMDEss plugin = JCMDEss.plugin;
    World world = null;
    JYamlConfiguration worldConfig = null;

    public JWorldConfig(World world) {
        worldConfig = new JYamlConfiguration(plugin, "worlds/" + world.getName());
    }

    public void setSpawn(Location loc) {
        worldConfig.set("world.spawn.x", loc.getX());
        worldConfig.set("world.spawn.y", loc.getY());
        worldConfig.set("world.spawn.z", loc.getZ());
        worldConfig.set("world.spawn.yaw", loc.getPitch());
        worldConfig.set("world.spawn.pitch", loc.getYaw());
        worldConfig.saveConfig();
    }

    public Location getSpawn() {
        Location sLoc = new Location(world, worldConfig.getConfig().getDouble("spawns." + getName() + ".x"),
                worldConfig.getConfig().getDouble("spawns." + getName() + ".y"), worldConfig.getConfig().getDouble("spawns." + getName() + ".z"),
                (float) worldConfig.getConfig().getDouble("spawns." + getName() + ".yaw"), (float) worldConfig.getConfig().getDouble("spawns."
                + getName() + ".pitch"));
        return sLoc;
    }

    public World getWorld() {
        return world;
    }

    public String getName() {
        return world.getName();
    }

    public JYamlConfiguration getConfig() {
        return worldConfig;
    }
}
