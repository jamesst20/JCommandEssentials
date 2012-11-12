package com.jamesst20.jcommandessentials.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jamesst20.jcommandessentials.Utils.Methods;

public class TpAcceptCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command cmnd, String cmd,
			String[] args) {
		if (Methods.isConsole(cs)) {
			Methods.sendPlayerMessage(cs, ChatColor.RED
					+ "Console can't accept a tp.");
			return true;
		} else if (!Methods
				.hasPermissionTell(cs, "JCMDEss.commands.tpa.accept")) {
			return true;
		} else if (args.length == 0) {
			Player to = ((Player) cs);
			Player from = null;
			if (TpaCommand.tpaPlayers.containsKey(to.getName())) {
				from = Bukkit.getServer().getPlayer(
						TpaCommand.tpaPlayers.get(to.getName()).toString());
			}
			if (from != null) {
				Methods.sendPlayerMessage(from,
						Methods.red(to.getDisplayName())
								+ " has accepted your teleport request.");
				Methods.sendPlayerMessage(to,
						"You accepted " + Methods.red(from.getDisplayName())
								+ "'s teleport request.");
				from.teleport(to);
				TpaCommand.tpaPlayers.remove(to.getName());
				return true;
			} else {
				Methods.sendPlayerMessage(to, ChatColor.RED
						+ "You have no pending teleport request.");
				return true;
			}
		} else {
			return false;
		}
	}

}
