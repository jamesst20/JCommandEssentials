package com.jamesst20.jcommandessentials.Commands;

import com.jamesst20.jcommandessentials.Utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetExpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (args.length == 1) {
            if (Methods.isConsole(cs)) {
                Methods.sendPlayerMessage(cs, ChatColor.RED + "The console can't set its own experience.");
                return true;
            }
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.setxp.self")) {
                return true;
            }
            try {
                int exp = Integer.parseInt(args[0]);
                ((Player) cs).setLevel(exp);
                Methods.sendPlayerMessage(cs, "Experience level has been set to " + Methods.red(args[0]) + ".");
                return true;
            } catch (NumberFormatException e) {
                Methods.sendPlayerMessage(cs, ChatColor.RED + "Experience level must be given as a number.");
                return true;
            }
        } else if (args.length == 2) {
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.setxp.others")) {
                return true;
            }
            Player player = Bukkit.getServer().getPlayer(args[0]);
            if (player != null) {
                try {
                    int exp = Integer.parseInt(args[1]);
                    player.setLevel(exp);
                    Methods.sendPlayerMessage(cs, Methods.red(player.getDisplayName() + "'s") + " experience level has been set to " + Methods.red(args[1]) + ".");
                    Methods.sendPlayerMessage(player, "Your experience level has been set to " + Methods.red(args[1]) + ".");
                    return true;
                } catch (NumberFormatException e) {
                    Methods.sendPlayerMessage(cs, ChatColor.RED + "Experience must be given as a number.");
                    return true;
                }
            } else {
                Methods.playerNotFound(cs, args[0]);
                return true;
            }
        } else {
            return false;
        }
    }
}
