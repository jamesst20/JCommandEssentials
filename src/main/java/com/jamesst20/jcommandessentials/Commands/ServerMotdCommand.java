package com.jamesst20.jcommandessentials.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.jamesst20.jcommandessentials.Utils.Methods;
import com.jamesst20.jcommandessentials.Utils.ServerMotd;

public class ServerMotdCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
		if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.servermotd")) {
			return true;
		}
		if (args.length < 1) {
			return false;
		}
		if (args[0].equalsIgnoreCase("set")) {
			StringBuilder motd = new StringBuilder();
			for (int i = 1; i < args.length; i++) {
				motd.append(args[i]).append(" ");
			}
			ServerMotd.setServerMotd(motd.toString());
			Methods.sendPlayerMessage(cs, "Server motd set!");
		} else if (args[0].equalsIgnoreCase("enable")) {
			ServerMotd.enableServerMotd();
			Methods.sendPlayerMessage(cs, "Server list motd is now " + Methods.red("enabled") + "!");
		} else if (args[0].equalsIgnoreCase("disable")) {
			ServerMotd.disableServerMotd();
			Methods.sendPlayerMessage(cs, "Server list motd is now " + Methods.red("disabled") + "!");
		} else {
			return false;
		}
		return true;
	}

}
