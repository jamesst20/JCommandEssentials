package com.jamesst20.jcommandessentials.Commands;

import com.jamesst20.jcommandessentials.Utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WalkSpeedCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (args.length < 1) {
            return false;
        }
        if (args[0].equalsIgnoreCase("get")) {
            if (args.length == 1) {
                if (Methods.isConsole(cs)) {
                    Methods.sendPlayerMessage(cs, ChatColor.RED + "The console can't get its own walk speed.");
                    return true;
                }
                if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.walkspeed.get.self")) {
                    return true;
                }
                Methods.sendPlayerMessage(cs, "Your walk speed is " + Methods.red(String.valueOf((((Player) cs).getWalkSpeed() * 10F))) + ".");
            } else if (args.length == 2) {
                if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.walkspeed.get.others")) {
                    return true;
                }
                Player player = Bukkit.getServer().getPlayer(args[1]);
                if (player != null) {
                    Methods.sendPlayerMessage(cs, Methods.red(player.getDisplayName() + "'s") + " walk speed is " + Methods.red(String.valueOf((player.getWalkSpeed() * 10F))) + ".");
                } else {
                    Methods.playerNotFound(cs, args[1]);
                }
            } else {
                return false;
            }
        } else if (args[0].equalsIgnoreCase("set")) {
            if (args.length == 2) {
                if (Methods.isConsole(cs)) {
                    Methods.sendPlayerMessage(cs, ChatColor.RED + "Console can only change walk speed of other players.");
                    return true;
                }
                if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.walkspeed.set.self")) {
                    return true;
                }
                Player player = ((Player) cs);
                try {
                    float speed = Float.parseFloat(args[1]) / 10F;
                    player.setWalkSpeed(speed);
                    Methods.sendPlayerMessage(cs, "You have set your walk speed to " + Methods.red(String.valueOf(speed * 10F)) + ".");
                    if (speed > 6) {
                        Methods.sendPlayerMessage(cs, ChatColor.RED + "Speed higher then 6 isn't recommended.");
                    }
                } catch (NumberFormatException e) {
                    Methods.sendPlayerMessage(cs, ChatColor.RED + "Speed must be given as a number.");
                    return true;
                } catch (IllegalArgumentException e) {
                    Methods.sendPlayerMessage(cs, ChatColor.RED + "Speed must be between 1 and 10.");
                    return true;
                }
            } else if (args.length == 3) {
                if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.walkspeed.set.others")) {
                    return true;
                }
                Player player = Bukkit.getServer().getPlayer(args[2]);
                if (player != null) {
                    try {
                        float speed = Float.parseFloat(args[1]) / 10F;
                        player.setWalkSpeed(speed);
                        Methods.sendPlayerMessage(cs, "You have set walk speed of " + Methods.red(player.getDisplayName()) + " to " + Methods.red(String.valueOf(speed * 10F)) + ".");
                        Methods.sendPlayerMessage(player, "Your walk speed have been set to " + Methods.red(String.valueOf(speed * 10F)) + ".");
                        if (speed > 6) {
                            Methods.sendPlayerMessage(cs, ChatColor.RED + "Speed higher then 6 isn't recommended.");
                        }
                    } catch (NumberFormatException e) {
                        Methods.sendPlayerMessage(cs, ChatColor.RED + "Speed must be given as a number.");
                        return true;
                    } catch (IllegalArgumentException e) {
                        Methods.sendPlayerMessage(cs, ChatColor.RED + "Speed must be between 1 and 10.");
                        return true;
                    }
                } else {
                    Methods.playerNotFound(cs, args[2]);
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
}
