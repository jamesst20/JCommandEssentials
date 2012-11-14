package com.jamesst20.jcommandessentials.Commands;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.jamesst20.jcommandessentials.Utils.Config;
import com.jamesst20.jcommandessentials.Utils.Methods;

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
			setDefaultSpawn(((Player) cs).getLocation());
			Methods.sendPlayerMessage(cs, "Default spawn set to your location.");
			return true;
		} else {
			return false;
		}
	}

	private void setDefaultSpawn(Location loc) {
		File spawnConfigFile = Config.getConfigFile("spawns.yml");
		YamlConfiguration spawnConfig = Config.getCustomConfig(spawnConfigFile);
		spawnConfig.set("spawns.default.world", loc.getWorld().getName());
		spawnConfig.set("spawns.default.x", loc.getX());
		spawnConfig.set("spawns.default.y", loc.getY());
		spawnConfig.set("spawns.default.z", loc.getZ());
		spawnConfig.set("spawns.default.yaw", loc.getYaw());
		spawnConfig.set("spawns.default.pitch", loc.getPitch());
		Config.saveConfig(spawnConfigFile, spawnConfig);
	}

}