package com.jamesst20.jcommandessentials.Commands;

import com.jamesst20.jcommandessentials.Utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NickCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (args.length == 1) {
            if (Methods.isConsole(cs)) {
                Methods.sendPlayerMessage(cs, ChatColor.RED + "Console can't change its own nickname.");
                return true;
            }
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.nick.self")) {
                return true;
            }
            Player player = ((Player) cs);
            Methods.sendPlayerMessage(cs, "Your nickname has been changed to " + Methods.red(Methods.coloring(args[0])) + ".");
            player.setDisplayName("~" + Methods.coloring(args[0] + "&f"));
            return true;
        } else if (args.length == 2) {
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.nick.others")) {
                return true;
            }
            Player player = Bukkit.getServer().getPlayer(args[0]);
            if (player != null){
                Methods.sendPlayerMessage(cs, "You changed nickname of " + Methods.red(player.getDisplayName()) + " to " + Methods.red(Methods.coloring(args[1])) + ".");
                Methods.sendPlayerMessage(player, "Your nickname has been changed to " + Methods.red(Methods.coloring(args[1])) +".");
                player.setDisplayName("~" + Methods.coloring(args[1] + "&f"));
            }else{
                Methods.playerNotFound(cs, args[0]);
            }
            return true;
        } else {
            return false;
        }
    }
}
