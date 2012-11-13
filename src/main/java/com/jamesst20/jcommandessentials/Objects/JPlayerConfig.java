package com.jamesst20.jcommandessentials.Objects;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.jamesst20.jcommandessentials.Utils.Config;

public class JPlayerConfig {
	Player player = null;
	File playerConfigFile = null;
	YamlConfiguration playerConfig = null;

	public JPlayerConfig(Player a) {
		File playersDir = new File(Config.plugin.getDataFolder(), "players");
		if (!playersDir.exists()) {
			playersDir.mkdir();
		}
		player = a;
		playerConfigFile = Config.getConfigFile("players/" + player.getName());
		playerConfig = Config.getCustomConfig(playerConfigFile);
	}

	public void setNewbieConfig() {
		Date date = new Date();
		Timestamp time = new Timestamp(date.getTime());
		playerConfig.set("timestamps.joindate", time.toString());
		Config.saveConfig(playerConfigFile, playerConfig);
	}

	public void onLogin() {
		if (playerConfig.getValues(true).isEmpty()) {
			setNewbieConfig();
		}
		Date date = new Date();
		Timestamp time = new Timestamp(date.getTime());
		playerConfig.set("timestamps.login", time.toString());
		playerConfig.set("ipAddress", player.getAddress().getAddress().toString().replaceAll("/", ""));
		Config.saveConfig(playerConfigFile, playerConfig);
	}

	public void onDisconnect() {
		Date date = new Date();
		Timestamp time = new Timestamp(date.getTime());
		playerConfig.set("timestamps.logout", time.toString());
		Config.saveConfig(playerConfigFile, playerConfig);
	}

	public void setHome() {
		playerConfig.set("homes.home.world", player.getWorld().getName());
		playerConfig.set("homes.home.x", player.getLocation().getX());
		playerConfig.set("homes.home.y", player.getLocation().getY());
		playerConfig.set("homes.home.z", player.getLocation().getZ());
		playerConfig.set("homes.home.yaw", player.getLocation().getYaw());
		playerConfig.set("homes.home.pitch", player.getLocation().getPitch());
		Config.saveConfig(playerConfigFile, playerConfig);
	}

	public void setHome(String name) {
		playerConfig.set("homes." + name + ".world", player.getWorld().getName());
		playerConfig.set("homes." + name + ".x", player.getLocation().getX());
		playerConfig.set("homes." + name + ".y", player.getLocation().getY());
		playerConfig.set("homes." + name + ".z", player.getLocation().getZ());
		playerConfig.set("homes." + name + ".yaw", player.getLocation().getYaw());
		playerConfig.set("homes." + name + ".pitch", player.getLocation().getPitch());
		Config.saveConfig(playerConfigFile, playerConfig);
	}

	public Location getHome() {
		if (playerConfig.get("homes.home") == null) {
			return null;
		}
		return new Location(Bukkit.getServer().getWorld(playerConfig.getString("homes.home.world")),
				playerConfig.getDouble("homes.home.x"), playerConfig.getDouble("homes.home.y"),
				playerConfig.getDouble("homes.home.z"), (float) playerConfig.getDouble("homes.home.yaw"),
				(float) playerConfig.getDouble("homes.home.pitch"));
	}

	public Location getHome(String name) {
		if (playerConfig.get("homes." + name) == null) {
			return null;
		}
		return new Location(Bukkit.getServer().getWorld(playerConfig.getString("homes." + name + ".world")),
				playerConfig.getDouble("homes." + name + ".x"), playerConfig.getDouble("homes." + name + ".y"),
				playerConfig.getDouble("homes." + name + ".z"), (float) playerConfig.getDouble("homes." + name + ".yaw"),
				(float) playerConfig.getDouble("homes." + name + ".pitch"));
	}

	public void setBanReason(String reason) {
		if (reason.isEmpty()) {
			playerConfig.set("ban.reason", "You are banned.");
			Config.saveConfig(playerConfigFile, playerConfig);
		} else {
			playerConfig.set("ban.reason", "Banned: " + reason);
			Config.saveConfig(playerConfigFile, playerConfig);
		}
	}

	public String getBanReason() {
		return playerConfig.getString("ban.reason", "You are banned.");
	}

	public void removeBanReason() {
		playerConfig.set("ban", null);
		Config.saveConfig(playerConfigFile, playerConfig);
	}
}
