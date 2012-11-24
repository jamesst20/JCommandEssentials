package com.jamesst20.jcommandessentials.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jamesst20.jcommandessentials.Utils.Methods;

public class MsgCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
		if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.msg")) {
			return true;
		}
		if (args.length < 2) {
			return false;
		}
		String from;
		if (Methods.isConsole(cs)) {
			from = "Console";
		} else {
			from = ((Player) cs).getDisplayName();
		}
		Player to = Bukkit.getServer().getPlayer(args[0]);
		if (to != null) {
			StringBuilder message = new StringBuilder();
			for (int i = 1; i < args.length; i++) {
				message.append(args[i]).append(" ");
			}
			cs.sendMessage(ChatColor.GOLD + "[me -> " + to.getDisplayName() + ChatColor.GOLD + "] " + ChatColor.WHITE
					+ message);
			to.sendMessage(ChatColor.GOLD + "[" + from + ChatColor.GOLD + " -> me] " + ChatColor.WHITE + message);
			return true;
		} else {
			Methods.playerNotFound(cs, args[0]);
			return true;
		}
	}

}
