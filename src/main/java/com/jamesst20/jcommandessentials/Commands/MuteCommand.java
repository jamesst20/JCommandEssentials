package com.jamesst20.jcommandessentials.Commands;

import com.jamesst20.jcommandessentials.Utils.Methods;
import java.util.HashSet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MuteCommand implements CommandExecutor {

    public static HashSet<String> mutedPlayersList = new HashSet<String>();

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.mute")) {
            return true;
        }
        if (args.length != 1) {
            return false;
        }
        Player player = Bukkit.getServer().getPlayer(args[0]);
        if (player != null) {
            if (player.getName().equalsIgnoreCase(cs.getName())) {
                Methods.sendPlayerMessage(cs, ChatColor.RED + "You can't mute or unmute yourself.");
                return true;
            }
            if (!mutedPlayersList.contains(player.getName())) {
                if (Methods.hasPermission(player, "JCMDEss.commands.mute.exempt")) {
                    Methods.sendPlayerMessage(cs, ChatColor.RED + "You can't mute " + Methods.red(player.getDisplayName()) + ".");
                    return true;
                }
                mutedPlayersList.add(player.getName());
                Methods.sendPlayerMessage(cs, "The player " + Methods.red(player.getDisplayName()) + " is now " + Methods.red("muted") + ".");
                Methods.sendPlayerMessage(player, "You are now " + Methods.red("muted") + ".");
                return true;
            } else {
                mutedPlayersList.remove(player.getName());
                Methods.sendPlayerMessage(cs, "The player " + Methods.red(player.getDisplayName()) + " is now " + Methods.red("unmuted") + ".");
                Methods.sendPlayerMessage(player, "You are now " + Methods.green("unmuted") + ".");
                return true;
            }
        } else {
            Methods.playerNotFound(cs, args[0]);
            return true;
        }
    }
}
