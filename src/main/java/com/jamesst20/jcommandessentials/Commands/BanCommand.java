package com.jamesst20.jcommandessentials.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jamesst20.jcommandessentials.Objects.JOfflinePlayerConfig;
import com.jamesst20.jcommandessentials.Objects.JPlayerConfig;
import com.jamesst20.jcommandessentials.Utils.Methods;

public class BanCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.ban")) {
            return true;
        }
        if (args.length == 1) {
            Player player = Bukkit.getServer().getPlayerExact(args[0]);
            if (player != null) {
                if (Methods.hasPermission(player, "JCMDEss.commands.ban.exempt")) {
                    Methods.sendPlayerMessage(cs, "You are not allowed to ban " + Methods.red(player.getName()) + ".");
                    return true;
                }
                player.setBanned(true);
                JPlayerConfig jConfig = new JPlayerConfig(player);
                jConfig.setBanReason("");
                player.kickPlayer(jConfig.getBanReason());
                Methods.sendPlayerMessage(cs, "You banned " + Methods.red(player.getDisplayName()) + ".");
                Methods.broadcastMessage(Methods.prefix + ChatColor.DARK_AQUA + ChatColor.ITALIC
                        + Methods.red(player.getDisplayName() + " have been banned."));
                return true;
            } else {
                OfflinePlayer oPlayer = Bukkit.getServer().getOfflinePlayer(args[0]);
                oPlayer.setBanned(true);
                JOfflinePlayerConfig jConfig = new JOfflinePlayerConfig(oPlayer);
                jConfig.setBanReason("");
                Methods.sendPlayerMessage(cs, "You banned an offline player: " + Methods.red(oPlayer.getName()) + ".");
                Methods.broadcastMessage(Methods.prefix + ChatColor.DARK_AQUA + ChatColor.ITALIC
                        + Methods.red(oPlayer.getName() + " have been banned."));
                return true;
            }
        } else if (args.length > 1) {
            Player player = Bukkit.getServer().getPlayer(args[0]);
            if (player != null) {
                if (Methods.hasPermission(player, "JCMDEss.commands.ban.exempt")) {
                    Methods.sendPlayerMessage(cs, "You are not allowed to ban " + Methods.red(player.getName()) + ".");
                    return true;
                }
                StringBuilder reason = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    reason.append(args[i]).append(" ");
                }
                Methods.replaceLast(reason, " ", "");
                player.setBanned(true);
                JPlayerConfig jConfig = new JPlayerConfig(player);
                jConfig.setBanReason(reason.toString());
                player.kickPlayer(reason.toString());
                Methods.sendPlayerMessage(cs,
                        "You banned " + Methods.red(player.getDisplayName()) + ". Reason: " + Methods.red(reason.toString()));
                Methods.broadcastMessage(Methods.prefix + ChatColor.DARK_AQUA + ChatColor.ITALIC
                        + Methods.red(player.getDisplayName() + " have been banned. Reason: " + Methods.red(reason.toString())));
                return true;
            } else {
                StringBuilder reason = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    reason.append(args[i]).append(" ");
                }
                Methods.replaceLast(reason, " ", "");
                OfflinePlayer oPlayer = Bukkit.getServer().getOfflinePlayer(args[0]);
                oPlayer.setBanned(true);
                JOfflinePlayerConfig jConfig = new JOfflinePlayerConfig(oPlayer);
                jConfig.setBanReason(reason.toString());
                Methods.sendPlayerMessage(cs, "You banned an offline player: " + Methods.red(oPlayer.getName())
                        + " Reason: " + Methods.red(reason.toString()));
                Methods.broadcastMessage(Methods.prefix + ChatColor.DARK_AQUA + ChatColor.ITALIC
                        + Methods.red(oPlayer.getName() + " have been banned. Reason: " + Methods.red(reason.toString())));
                return true;
            }
        } else {
            return false;
        }
    }
}
