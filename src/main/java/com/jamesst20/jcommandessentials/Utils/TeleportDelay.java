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
