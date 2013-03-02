package com.jamesst20.jcommandessentials.Commands;

import com.jamesst20.jcommandessentials.Utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

public class KillMobsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.killmobs")) {
            return true;
        }
        if (args.length == 0) {
            for (World world : Bukkit.getServer().getWorlds()) {
                for (Entity e : world.getEntities()) {
                    if (!(e instanceof Player)) {
                        e.remove();
                    }
                }
            }
            Methods.sendPlayerMessage(cs, "Killed all mobs on the server!");
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("animal") || args[0].equalsIgnoreCase("animals")) {
                for (World world : Bukkit.getServer().getWorlds()) {
                    for (Entity e : world.getEntitiesByClass(Animals.class)) {
                        e.remove();
                    }
                }
                Methods.sendPlayerMessage(cs, "Killed all " + Methods.red("animals") + " on the server!");
            } else if (args[0].equalsIgnoreCase("monster") || args[0].equalsIgnoreCase("monsters")) {
                for (World world : Bukkit.getServer().getWorlds()) {
                    for (Entity e : world.getEntitiesByClass(Monster.class)) {
                        e.remove();
                    }
                }
                Methods.sendPlayerMessage(cs, "Killed all " + Methods.red("monsters") + " on the server!");
            } else if (args[0].equalsIgnoreCase("all")) {
                for (World world : Bukkit.getServer().getWorlds()) {
                    for (Entity e : world.getEntities()) {
                        if (!(e instanceof Player)) {
                            e.remove();
                        }
                    }
                }
                Methods.sendPlayerMessage(cs, "Killed all mobs on the server!");
            } else {
                String mobName = args[0];
                boolean found = false;
                for (World world : Bukkit.getServer().getWorlds()) {
                    for (Entity e : world.getEntities()) {
                        if (!(e instanceof Player)) {
                            if (e != null) {
                                if (mobName.equalsIgnoreCase(e.getType().getName())) {
                                    e.remove();
                                    found = true;
                                }
                            }
                        }
                    }
                }
                if (found) {
                    Methods.sendPlayerMessage(cs, "Killed all " + Methods.red(mobName + "s") + " on the server!");
                } else {
                    Methods.sendPlayerMessage(cs, Methods.red(mobName) + " isn't a valid entity or couldn't be found.");
                    StringBuilder entitiesList = new StringBuilder("&cAll&b, ");
                    for (EntityType e : EntityType.values()) {
                        if (e.getName() != null) {
                            entitiesList.append("&c").append(e.getName()).append("&b").append(", ");
                        }
                    }
                    entitiesList = Methods.replaceLast(entitiesList, ", ", "");
                    Methods.sendPlayerMessage(cs, "Valid entities are : " + Methods.coloring(entitiesList.toString()));
                }
            }
        } else {
            return false;
        }
        return true;
    }
}
