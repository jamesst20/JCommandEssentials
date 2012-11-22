package com.jamesst20.jcommandessentials.Objects;

import com.jamesst20.config.JYamlConfiguration;
import com.jamesst20.jcommandessentials.JCMDEssentials.JCMDEss;
import java.io.File;

import org.bukkit.OfflinePlayer;

public class JOfflinePlayerConfig {

    OfflinePlayer player;
    JYamlConfiguration playerConfig;

    public JOfflinePlayerConfig(OfflinePlayer p) {
        player = p;
        File playersDir = new File(JCMDEss.plugin.getDataFolder(), "players");
        // We do not make file if the player isn't found. We leave stuff to null
        if (playersDir.exists()) {
            playerConfig = new JYamlConfiguration(JCMDEss.plugin, "players/" + player.getName());
        }
    }

    public void setBanReason(String reason) {
        if (playerConfig != null) {
            if (reason.isEmpty()) {
                playerConfig.set("ban.reason", "You are banned.");
                playerConfig.saveConfig();
            } else {
                playerConfig.set("ban.reason", "Banned: " + reason);
                playerConfig.saveConfig();
            }
        }
    }

    public String getBanReason() {
        if (playerConfig != null) {
            return playerConfig.getConfig().getString("ban.reason", "You are banned.");
        } else {
            return null;
        }

    }

    public void removeBanReason() {
        if (playerConfig != null) {
            playerConfig.set("ban", null);
            playerConfig.saveConfig();
        }
    }
}
