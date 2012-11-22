package com.jamesst20.jcommandessentials.Objects;

import com.jamesst20.config.JYamlConfiguration;

import org.bukkit.Location;
import org.bukkit.World;

import com.jamesst20.jcommandessentials.JCMDEssentials.JCMDEss;

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
