package com.jamesst20.jcommandessentials.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jamesst20.jcommandessentials.Utils.Methods;

public class SudoCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
		if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.sudo")) {
			return true;
		}
		if (args.length < 2) {
			return false;
		}
		StringBuilder theCommand = new StringBuilder();
		for (int i = 1; i < args.length; i++) {
			theCommand.append(args[i]).append(" ");
		}
		theCommand = Methods.replaceLast(theCommand, " ", "");
		Player player = Bukkit.getServer().getPlayer(args[0]);
		if (player != null) {
			player.performCommand(theCommand.toString());
			Methods.sendPlayerMessage(cs,
					"Forced " + Methods.red(player.getDisplayName()) + " to run: '" + Methods.red(theCommand.toString()) + "'");
			return true;
		} else {
			Methods.playerNotFound(cs, args[0]);
			return true;
		}
	}

}
