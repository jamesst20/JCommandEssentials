package com.jamesst20.jcommandessentials.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jamesst20.jcommandessentials.Utils.Methods;

public class KickCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender cs, Command cmnd, String cmd, String[] args) {
		if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.kick")) {
			return true;
		}
		if (args.length == 1) {
			Player player = Bukkit.getServer().getPlayer(args[0]);
			if (player != null) {
				Methods.sendPlayerMessage(cs, "You kicked " + Methods.red(player.getDisplayName()) + ".");
				player.kickPlayer("You have been kicked.");
				Methods.broadcastMessage(Methods.prefix + ChatColor.DARK_AQUA + ChatColor.ITALIC
						+ Methods.red(player.getDisplayName() + " have been kicked."));
			} else {
				Methods.playerNotFound(cs, args[0]);
				return true;
			}
		} else if (args.length > 1) {
			Player player = Bukkit.getServer().getPlayer(args[0]);
			if (player != null) {
				String reason = "";
				for (int i = 1; i < args.length; i++) {
					reason += args[i] + " ";
				}
				Methods.replaceLast(reason, " ", "");
				Methods.sendPlayerMessage(cs,
						"You kicked " + Methods.red(player.getDisplayName()) + ". Reason: " + Methods.red(reason));
				player.kickPlayer(reason);
				Methods.broadcastMessage(Methods.prefix + ChatColor.DARK_AQUA + ChatColor.ITALIC
						+ Methods.red(player.getDisplayName() + " have been kicked. Reason: " + Methods.red(reason)));
			} else {
				Methods.playerNotFound(cs, args[0]);
				return true;
			}
		} else {
			return false;
		}
		return true;
	}

}
