package com.jamesst20.jcommandessentials.Utils;

import com.jamesst20.jcommandessentials.JCMDEssentials.JCMDEss;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class TeleportDelay {

    private static HashMap<String, Integer> playerTasks = new HashMap<String, Integer>();
    private static JCMDEss plugin = JCMDEss.plugin;

    public static void schedulePlayer(Player player, Location loc) {
        playerTasks.put(player.getName(), scheduleTask(player, loc));
    }

    public static void removeScheduledPlayer(Player player) {
        plugin.getServer().getScheduler().cancelTask(playerTasks.get(player.getName()));
        playerTasks.remove(player.getName());
        Methods.sendPlayerMessage(player, "Teleportation " + Methods.red("canceled") + ".");
    }

    public static boolean isPlayerQueued(Player player) {
        return playerTasks.containsKey(player.getName());
    }

    private static int scheduleTask(final Player p, final Location loc) {
        return plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                if (p != null) {
                    p.teleport(loc);
                    playerTasks.remove(p.getName());
                }
            }
        }, getDelay() * 20);
    }

    public static int getDelay() {
        return plugin.getConfig().getInt("teleport.delay", 5);
    }

    public static void setDefaultDelay() {
        if (plugin.getConfig().get("teleport.delay") == null) {
            plugin.getConfig().set("teleport.delay", 5);
        }
    }
}
