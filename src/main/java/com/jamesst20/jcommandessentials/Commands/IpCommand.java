package com.jamesst20.jcommandessentials.Commands;

import com.jamesst20.jcommandessentials.Utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class IpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (args.length == 0) {
            if (Methods.isConsole(cs)) {
                Methods.sendPlayerMessage(cs, ChatColor.RED + "The console can't check its own ip.");
                return true;
            }
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.ip.self")) {
                return true;
            }
            Methods.sendPlayerMessage(cs, "Your IP is : " + Methods.red(((Player) cs).getAddress().getAddress().toString().replaceAll("/", "")) + ".");
            return true;
        } else if (args.length == 1) {
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.ip.others")) {
                return true;
            }
            Player player = Bukkit.getServer().getPlayer(args[0]);
            if (player != null) {
                Methods.sendPlayerMessage(cs, "The IP of " + Methods.red(player.getDisplayName()) + " is " + Methods.red(player.getAddress().getAddress().toString().replaceAll("/", "")) + ".");
                return true;
            } else {
                Methods.playerNotFound(cs, args[0]);
                return true;
            }
        } else {
            return false;
        }
    }
}
