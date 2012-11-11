package com.jamesst20.jcommandessentials.Objects;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import com.jamesst20.jcommandessentials.JCMDEssentials.JCMDEss;
import com.jamesst20.jcommandessentials.Methods.Config;

public class JWorldConfig {
	JCMDEss plugin = JCMDEss.plugin;
	World world = null;
	File worldConfigFile = null;
	YamlConfiguration worldConfig = null;
	
	public JWorldConfig(World world){
		Config.dirCreate("worlds");
		worldConfigFile = Config.getConfigFile("worlds/" + world.getName());
	}
	
	public void setSpawn(Location loc){
		//Bukkit.getServer().getWorld(getName()).setSpawnLocation((int)loc.getX(), (int)loc.getY(), (int)loc.getZ());
		worldConfig.set("world.spawn.x", loc.getX());
		worldConfig.set("world.spawn.y", loc.getY());
		worldConfig.set("world.spawn.z", loc.getZ());
		worldConfig.set("world.spawn.yaw", loc.getPitch());
		worldConfig.set("world.spawn.pitch", loc.getYaw());
		Config.saveConfig(worldConfigFile, worldConfig);
	}
	
	public Location getSpawn(){
		Location sLoc = new Location(world, worldConfig.getDouble("spawns." + getName() + ".x"), 
				worldConfig.getDouble("spawns." + getName() + ".y"), 
				worldConfig.getDouble("spawns." + getName() + ".z"), 
				(float)worldConfig.getDouble("spawns." + getName() + ".yaw"), 
				(float)worldConfig.getDouble("spawns." + getName() + ".pitch"));
		return sLoc;
	}
	
	public World getWorld(){
		return world;
	}
	public String getName(){
		return world.getName();
	}
	public YamlConfiguration getConfig(){
		return worldConfig;
	}
	public File getConfigFile(){
		return worldConfigFile;
	}
}
