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
import java.io.File;
import org.bukkit.OfflinePlayer;

public class JOfflinePlayerConfig {

    OfflinePlayer player;
    JYamlConfiguration playerConfig;

    public JOfflinePlayerConfig(OfflinePlayer p) {
        player = p;
        File playersDir = new File(JCMDEss.plugin.getDataFolder(), "players");
        // We do not make file if the player isn't found. We leave stuff to null
        // We also make sure it is case insensitive
        if (playersDir.exists()) {
            for (File file : playersDir.listFiles()) {
                if (file != null) {
                    if (file.getName().equalsIgnoreCase(player.getName())) {
                        playerConfig = new JYamlConfiguration(JCMDEss.plugin, "players/" + file.getName());
                    }
                }
            }
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

    public String getJoinDate() {
        if (playerConfig != null) {
            return playerConfig.getConfig().getString("timestamps.joindate");
        }
        return null;
    }

    public String getLastLogin() {
        if (playerConfig != null) {
            return playerConfig.getConfig().getString("timestamps.login");
        }
        return null;
    }

    public String getLastLogout() {
        if (playerConfig != null) {
            return playerConfig.getConfig().getString("timestamps.logout");
        }
        return null;
    }

    public String getLastIP() {
        if (playerConfig != null) {
            return playerConfig.getConfig().getString("ipAddress");
        }
        return null;
    }

    public String getName() {
        return player.getName();
    }

    public boolean isBanned() {
        return player.isBanned();
    }

    public boolean playerExist() {
        return playerConfig != null;
    }
}
