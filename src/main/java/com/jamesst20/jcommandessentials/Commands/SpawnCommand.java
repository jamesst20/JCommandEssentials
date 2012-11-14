package com.jamesst20.jcommandessentials.Commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.jamesst20.jcommandessentials.Utils.Config;
import com.jamesst20.jcommandessentials.Utils.Methods;

public class SpawnCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command cmnd, String cmd, String[] args) {
		if (args.length == 0) {
			if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.spawn.self")) {
				return true;
			}
			if (Methods.isConsole(cs)) {
				Methods.sendPlayerMessage(cs, ChatColor.RED + "The console can only tp others to spawn.");
				return true;
			}
			Location spawn = getDefaultSpawn();
			if (spawn != null) {
				Methods.sendPlayerMessage(cs, "You teleported to the default spawn.");
				((Player) cs).teleport(spawn);
				return true;
			} else {
				Methods.sendPlayerMessage(cs, ChatColor.RED + "JCMDEss spawn not set !");
				Methods.sendPlayerMessage(cs, "Teleporting to the world spawn instead.");
				((Player) cs).teleport(((Player) cs).getWorld().getSpawnLocation());
				return true;
			}
		} else if (args.length == 1) {
			if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.spawn.others")) {
				return true;
			}
			Player player = Bukkit.getServer().getPlayer(args[0]);
			if (player != null) {
				Location spawn = getDefaultSpawn();
				if (spawn != null) {
					Methods.sendPlayerMessage(cs, "You teleported " + Methods.red(player.getDisplayName())
							+ " to the default spawn.");
					player.teleport(spawn);
				} else {
					Methods.sendPlayerMessage(cs, ChatColor.RED + "JCMDEss spawn not set !");
					Methods.sendPlayerMessage(cs, "Teleporting " + Methods.red(player.getDisplayName())
							+ " to the world spawn instead.");
					player.teleport(player.getWorld().getSpawnLocation());
				}
				Methods.sendPlayerMessage(player, "You have been teleported to spawn.");
				return true;
			} else {
				Methods.playerNotFound(cs, args[0]);
				return true;
			}
		} else {
			return false;
		}
	}

	private Location getDefaultSpawn() {
		File spawnConfigFile = Config.getConfigFile("spawns.yml");
		YamlConfiguration spawnConfig = Config.getCustomConfig(spawnConfigFile);
		if (spawnConfig.getString("spawns.default.world") == null) {
			return null; // Missing settings.
		}
		World world = Bukkit.getServer().getWorld(spawnConfig.getString("spawns.default.world"));
		if (world == null) {
			return null; // World doesn't exist
		}
		return new Location(world, spawnConfig.getDouble("spawns.default.x"), spawnConfig.getDouble("spawns.default.y"),
				spawnConfig.getDouble("spawns.default.z"), (float) spawnConfig.getDouble("spawns.default.yaw"),
				(float) spawnConfig.getDouble("spawns.default.pitch"));
	}
}