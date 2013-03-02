package com.jamesst20.jcommandessentials.Objects;

import com.jamesst20.config.JYamlConfiguration;
import com.jamesst20.jcommandessentials.JCMDEssentials.JCMDEss;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class JPlayerConfig {

    Player player;
    JYamlConfiguration playerConfig;

    public JPlayerConfig(Player p) {
        player = p;
        playerConfig = new JYamlConfiguration(JCMDEss.plugin, "players/" + player.getName());
    }

    public void setNewbieConfig() {
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        playerConfig.set("timestamps.joindate", time.format(new Date()));
        playerConfig.saveConfig();
    }

    public void onLogin() {
        if (playerConfig.getConfig().getValues(true).isEmpty()) {
            setNewbieConfig();
        }
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        playerConfig.set("timestamps.login", time.format(new Date()));
        playerConfig.set("ipAddress", player.getAddress().getAddress().toString().replaceAll("/", ""));
        playerConfig.saveConfig();
    }

    public void onDisconnect() {
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        playerConfig.set("timestamps.logout", time.format(new Date()));
        playerConfig.saveConfig();
    }

    public void setHome() {
        playerConfig.set("homes.home.world", player.getWorld().getName());
        playerConfig.set("homes.home.x", player.getLocation().getX());
        playerConfig.set("homes.home.y", player.getLocation().getY());
        playerConfig.set("homes.home.z", player.getLocation().getZ());
        playerConfig.set("homes.home.yaw", player.getLocation().getYaw());
        playerConfig.set("homes.home.pitch", player.getLocation().getPitch());
        playerConfig.saveConfig();
    }

    public void setHome(String name) {
        playerConfig.set("homes." + name + ".world", player.getWorld().getName());
        playerConfig.set("homes." + name + ".x", player.getLocation().getX());
        playerConfig.set("homes." + name + ".y", player.getLocation().getY());
        playerConfig.set("homes." + name + ".z", player.getLocation().getZ());
        playerConfig.set("homes." + name + ".yaw", player.getLocation().getYaw());
        playerConfig.set("homes." + name + ".pitch", player.getLocation().getPitch());
        playerConfig.saveConfig();
    }

    public Location getHome() {
        if (playerConfig.getConfig().get("homes.home") == null) {
            return null;
        }
        return new Location(Bukkit.getServer().getWorld(playerConfig.getConfig().getString("homes.home.world")),
                playerConfig.getConfig().getDouble("homes.home.x"), playerConfig.getConfig().getDouble("homes.home.y"),
                playerConfig.getConfig().getDouble("homes.home.z"), (float) playerConfig.getConfig().getDouble("homes.home.yaw"),
                (float) playerConfig.getConfig().getDouble("homes.home.pitch"));
    }

    public Location getHome(String name) {
        if (playerConfig.getConfig().get("homes." + name) == null) {
            return null;
        }
        return new Location(Bukkit.getServer().getWorld(playerConfig.getConfig().getString("homes." + name + ".world")),
                playerConfig.getConfig().getDouble("homes." + name + ".x"), playerConfig.getConfig().getDouble("homes." + name + ".y"),
                playerConfig.getConfig().getDouble("homes." + name + ".z"), (float) playerConfig.getConfig().getDouble("homes." + name + ".yaw"),
                (float) playerConfig.getConfig().getDouble("homes." + name + ".pitch"));
    }

    public void setBanReason(String reason) {
        if (reason.isEmpty()) {
            playerConfig.set("ban.reason", "You are banned.");
            playerConfig.saveConfig();
        } else {
            playerConfig.set("ban.reason", "Banned: " + reason);
            playerConfig.saveConfig();
        }
    }

    public String getBanReason() {
        return playerConfig.getConfig().getString("ban.reason", "You are banned.");
    }

    public void removeBanReason() {
        playerConfig.set("ban", null);
        playerConfig.saveConfig();
    }
}
