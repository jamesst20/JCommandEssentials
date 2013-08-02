package com.jamesst20.jcommandessentials.Commands;

import com.jamesst20.jcommandessentials.Utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;


public class WaterWalkCommand implements CommandExecutor {
    public static HashMap<String, HashMap<Location, Material>> playersWalkingWaterList = new HashMap<String, HashMap<Location, Material>>();

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (args.length == 0) {
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.waterwalk.self")) {
                return true;
            }
            if (Methods.isConsole(cs)) {
                Methods.sendPlayerMessage(cs, ChatColor.RED + "The console can't walk on water.");
                return true;
            }
            Player player = (Player) cs;
            if (playersWalkingWaterList.containsKey(player.getName())) {
                restoreBlocks(player);
                playersWalkingWaterList.remove(player.getName());
                Methods.sendPlayerMessage(player, ChatColor.RED + "You can no longer walk on water.");
                return true;
            } else {
                playersWalkingWaterList.put(player.getName(), new HashMap<Location, Material>());
                Methods.sendPlayerMessage(player, "You can now walk on water.");
                return true;
            }
        } else if (args.length == 1) {
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.waterwalk.others")) {
                return true;
            }
            Player player = Bukkit.getServer().getPlayer(args[0]);
            if (player == null) {
                Methods.playerNotFound(cs, args[0]);
                return true;
            }
            if (playersWalkingWaterList.containsKey(player.getName())) {
                restoreBlocks(player);
                playersWalkingWaterList.remove(player.getName());
                Methods.sendPlayerMessage(player, ChatColor.RED + "You can no longer walk on water.");
                Methods.sendPlayerMessage(cs, "The player " + Methods.red(args[0]) + " can no longer walk on water.");
                return true;
            } else {
                playersWalkingWaterList.put(player.getName(), new HashMap<Location, Material>());
                Methods.sendPlayerMessage(player, "You can now walk on water.");
                Methods.sendPlayerMessage(cs, "The player " + Methods.red(args[0]) + " can now walk on water.");
                return true;
            }
        } else {
            return false;
        }
    }

    public static ArrayList<Location> getBlocksLocationAroundPlayer(Player player) {
        ArrayList<Location> blocksAroundLocation = new ArrayList<Location>();
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                Block block = player.getLocation().getBlock().getRelative(x, -1, y);
                blocksAroundLocation.add(block.getLocation());
            }
        }
        return blocksAroundLocation;
    }

    public static void restoreBlocks(Player player) {
        for (int i = 0; i < playersWalkingWaterList.get(player.getName()).size(); i++) {
            Location loc = (Location) playersWalkingWaterList.get(player.getName()).keySet().toArray()[i];
            loc.getBlock().setType((Material) playersWalkingWaterList.get(player.getName()).values().toArray()[i]);  //Restore old block
        }
    }
}
