package com.jamesst20.jcommandessentials.Commands;

import com.jamesst20.config.JYamlConfiguration;
import com.jamesst20.jcommandessentials.JCMDEssentials.JCMDEss;
import com.jamesst20.jcommandessentials.Utils.Methods;
import com.jamesst20.jcommandessentials.Utils.TeleportDelay;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
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
                if (TeleportDelay.getDelay() < 1) {
                    Methods.sendPlayerMessage(cs, "You teleported to spawn.");
                    ((Player) cs).teleport(spawn);
                } else {
                    TeleportDelay.schedulePlayer(((Player) cs), spawn);
                    Methods.sendPlayerMessage(cs, "Don't move! You will be teleported in " + Methods.red(String.valueOf(TeleportDelay.getDelay())) + " seconds.");
                }
                return true;
            } else {
                if (TeleportDelay.getDelay() < 1) {
                    Methods.sendPlayerMessage(cs, ChatColor.RED + "JCMDEss spawn not set !");
                    Methods.sendPlayerMessage(cs, "Teleporting to the world spawn instead.");
                    ((Player) cs).teleport(((Player) cs).getWorld().getSpawnLocation());
                } else {
                    TeleportDelay.schedulePlayer(((Player) cs), ((Player) cs).getWorld().getSpawnLocation());
                    Methods.sendPlayerMessage(cs, ChatColor.RED + "JCMDEss spawn not set !");
                    Methods.sendPlayerMessage(cs, "Teleporting to the world spawn instead.");
                    Methods.sendPlayerMessage(cs, "Don't move! You will be teleported in " + Methods.red(String.valueOf(TeleportDelay.getDelay())) + " seconds.");
                }
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
        JYamlConfiguration spawnConfig = new JYamlConfiguration(JCMDEss.plugin, "spawns.yml");
        if (spawnConfig.getConfig().getString("spawns.default.world") == null) {
            return null; // Missing settings.
        }
        World world = Bukkit.getServer().getWorld(spawnConfig.getConfig().getString("spawns.default.world"));
        if (world == null) {
            return null; // World doesn't exist
        }
        return new Location(world, spawnConfig.getConfig().getDouble("spawns.default.x"), spawnConfig.getConfig().getDouble("spawns.default.y"),
                spawnConfig.getConfig().getDouble("spawns.default.z"), (float) spawnConfig.getConfig().getDouble("spawns.default.yaw"),
                (float) spawnConfig.getConfig().getDouble("spawns.default.pitch"));
    }
}