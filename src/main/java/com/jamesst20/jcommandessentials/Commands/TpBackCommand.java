package com.jamesst20.jcommandessentials.Commands;

import com.jamesst20.jcommandessentials.Utils.Methods;
import com.jamesst20.jcommandessentials.Utils.TeleportDelay;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpBackCommand implements CommandExecutor {

    public static HashMap<String, Location> playersLastTeleport = new HashMap<String, Location>();
    public static String permPrefix = "JCMDEss.commands.tpback";

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (args.length == 0) {
            if (Methods.isConsole(cs)) {
                Methods.sendPlayerMessage(cs, ChatColor.RED + "The console has no previous location.");
                return true;
            }
            if (!Methods.hasPermissionTell(cs, permPrefix + ".self")){
                return true;
            }
            if (playersLastTeleport.containsKey(cs.getName())) {
                Player player = ((Player) cs);
                if (TeleportDelay.getDelay() < 1) {
                    player.teleport(playersLastTeleport.get(cs.getName()));
                    Methods.sendPlayerMessage(cs, "You teleported to your last location.");
                } else {
                    Methods.sendPlayerMessage(cs, "Don't move! You will be teleported in " + Methods.red(String.valueOf(TeleportDelay.getDelay())) + " seconds.");
                    TeleportDelay.schedulePlayer(player, playersLastTeleport.get(cs.getName()));
                }
            } else {
                Methods.sendPlayerMessage(cs, ChatColor.RED + "You have no previous location.");
            }
        } else if (args.length == 1) {
            if (!Methods.hasPermissionTell(cs, permPrefix + ".others")){
                return true;
            }
            Player player = Bukkit.getServer().getPlayer(args[0]);
            if (player != null) {
                if (playersLastTeleport.containsKey(player.getName())) {
                    player.teleport(playersLastTeleport.get(player.getName()));
                    Methods.sendPlayerMessage(cs, "You teleported " + Methods.red(player.getDisplayName()) + " to his previous location.");
                    Methods.sendPlayerMessage(player, "You have been teleported to your last location.");
                } else {
                    Methods.sendPlayerMessage(cs, "The player " + Methods.red(player.getDisplayName()) + " has no previous location.");
                }
            } else {
                Methods.playerNotFound(cs, args[0]);
            }
        } else {
            return false;
        }
        return true;
    }
    
    public static void removePlayer(String name){
        if (playersLastTeleport.containsKey(name)){
            playersLastTeleport.remove(name);
        }
    }
}
