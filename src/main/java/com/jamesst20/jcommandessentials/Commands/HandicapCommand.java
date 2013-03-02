package com.jamesst20.jcommandessentials.Commands;

import com.jamesst20.jcommandessentials.Utils.Methods;
import java.util.HashSet;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HandicapCommand implements CommandExecutor {

    public static HashSet<String> handicappedList = new HashSet<String>();

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.handicap")) {
            return true;
        }
        if (args.length != 1) {
            return false;
        }
        Player player = Bukkit.getServer().getPlayer(args[0]);
        if (player != null) {
            if (!handicappedList.contains(player.getName())) {
                handicappedList.add(player.getName());
                Methods.sendPlayerMessage(cs, "You handicaped " + Methods.red(player.getDisplayName()) + ".");
                Methods.sendPlayerMessage(player, "You're now " + Methods.red("blocked") + " from using commands.");
                return true;
            } else {
                handicappedList.remove(player.getName());
                Methods.sendPlayerMessage(cs, "You unhandicaped " + Methods.red(player.getDisplayName()) + ".");
                Methods.sendPlayerMessage(player, "You're now " + Methods.red("allowed") + " to use commands.");
                return true;
            }
        } else {
            Methods.playerNotFound(cs, args[0]);
            return true;
        }
    }
}
