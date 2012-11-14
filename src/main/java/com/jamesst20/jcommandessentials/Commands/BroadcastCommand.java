package com.jamesst20.jcommandessentials.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.jamesst20.jcommandessentials.Utils.Methods;

public class BroadcastCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
		if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.broadcast")) {
			return true;
		} else if (args.length < 1) {
			return false;
		}
		String message = "";
		for (String msg : args) {
			message += msg + " ";
		}
		Methods.broadcastMessage(message);
		return true;
	}

}
