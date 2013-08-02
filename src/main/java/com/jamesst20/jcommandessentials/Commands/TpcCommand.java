package com.jamesst20.jcommandessentials.Commands;

import com.jamesst20.jcommandessentials.Utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpcCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (args.length == 3) {
            if (Methods.isConsole(cs)) {
                Methods.sendPlayerMessage(cs, ChatColor.RED + "The console can only tp other player.");
                return true;
            }
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.tpc.self")) {
                return true;
            }
            Player player = ((Player) cs);
            Location loc = toLocation(player.getWorld(), args[0], args[1], args[2]);
            if (loc != null) {
                player.teleport(loc);
                Methods.sendPlayerMessage(
                        cs,
                        "You teleported to (" + Methods.red(String.valueOf((int) loc.getX())) + ", "
                                + Methods.red(String.valueOf((int) loc.getY())) + ", "
                                + Methods.red(String.valueOf((int) loc.getZ())) + ").");
            } else {
                Methods.sendPlayerMessage(cs, ChatColor.RED + "Invalid location.");
            }
        } else if (args.length == 4) {
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.tpc.others")) {
                return true;
            }
            Player player = Bukkit.getServer().getPlayer(args[0]);
            if (player != null) {
                Location loc = toLocation(player.getWorld(), args[1], args[2], args[3]);
                if (loc != null) {
                    player.teleport(loc);
                    Methods.sendPlayerMessage(
                            cs,
                            "You teleported " + Methods.red(player.getDisplayName()) + " to ("
                                    + Methods.red(String.valueOf((int) loc.getX())) + ", "
                                    + Methods.red(String.valueOf((int) loc.getY())) + ", "
                                    + Methods.red(String.valueOf((int) loc.getZ())) + ").");
                    Methods.sendPlayerMessage(
                            player,
                            "You have been teleported to (" + Methods.red(String.valueOf((int) loc.getX())) + ", "
                                    + Methods.red(String.valueOf((int) loc.getY())) + ", "
                                    + Methods.red(String.valueOf((int) loc.getZ())) + ").");
                } else {
                    Methods.sendPlayerMessage(cs, ChatColor.RED + "Invalid location.");
                }
            } else {
                Methods.playerNotFound(cs, args[0]);
                return true;
            }
        } else {
            return false;
        }
        return true;
    }

    private Location toLocation(World world, String x1, String y1, String z1) {
        double x;
        double y;
        double z;
        try {
            x = Double.parseDouble(x1);
            y = Double.parseDouble(y1);
            z = Double.parseDouble(z1);
        } catch (NullPointerException e) {
            return null;
        } catch (NumberFormatException e) {
            return null;
        }
        return new Location(world, x, y, z);
    }
}
