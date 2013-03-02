package com.jamesst20.jcommandessentials.Commands;

import com.jamesst20.jcommandessentials.Utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.staff.send")) {
            return true;
        }
        if (args.length < 1) {
            return false;
        }
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            message.append(args[i]).append(" ");
        }
        if (Methods.isConsole(cs)) {
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                if (Methods.hasPermission(player, "JCMDEss.commands.staff.see")) {
                    player.sendMessage(ChatColor.GOLD + "[Staff]" + ChatColor.DARK_AQUA + "Console" + ChatColor.WHITE + ": "
                            + ChatColor.LIGHT_PURPLE + message);
                }
            }
            Methods.log(ChatColor.GOLD + "[Staff]" + ChatColor.DARK_AQUA + "Console" + ChatColor.WHITE + ": "
                    + ChatColor.LIGHT_PURPLE + message);
            return true;
        } else {
            Player from = ((Player) cs);
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                if (Methods.hasPermission(player, "JCMDEss.commands.staff.see")) {
                    player.sendMessage(ChatColor.GOLD + "[Staff]" + ChatColor.DARK_AQUA + from.getDisplayName()
                            + ChatColor.WHITE + ": " + ChatColor.LIGHT_PURPLE + message);
                }
            }
            Methods.log(ChatColor.GOLD + "[Staff]" + ChatColor.DARK_AQUA + from.getDisplayName() + ChatColor.WHITE + ": "
                    + ChatColor.LIGHT_PURPLE + message);
            return true;
        }
    }
}
