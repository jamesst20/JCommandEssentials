package com.jamesst20.jcommandessentials.Objects;

import java.io.File;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import com.jamesst20.jcommandessentials.Methods.Config;

public class JOfflinePlayerConfig {
	OfflinePlayer player = null;
	File playerConfigFile = null;
	YamlConfiguration playerConfig = null;

	public JOfflinePlayerConfig(OfflinePlayer p) {
		player = p;
		File playersDir = new File(Config.plugin.getDataFolder(), "players");
		// We do not make files if the player isn't found. We leave stuff to
		// null
		if (playersDir.exists()) {
			playerConfigFile = Config.getConfigFile("players/"
					+ player.getName());
			playerConfig = Config.getCustomConfig(playerConfigFile);
		}
	}

	public void setBanReason(String reason) {
		if (playerConfig != null) {
			if (reason.isEmpty()) {
				playerConfig.set("ban.reason", "You are banned.");
				Config.saveConfig(playerConfigFile, playerConfig);
			} else {
				playerConfig.set("ban.reason", "Banned: " + reason);
				Config.saveConfig(playerConfigFile, playerConfig);
			}
		}
	}

	public String getBanReason() {
		if (playerConfig != null) {
			return playerConfig.getString("ban.reason", "You are banned.");
		}else{
			return null;
		}
		
	}

	public void removeBanReason() {
		if (playerConfig != null) {
			playerConfig.set("ban", null);
			Config.saveConfig(playerConfigFile, playerConfig);
		}
	}

}
