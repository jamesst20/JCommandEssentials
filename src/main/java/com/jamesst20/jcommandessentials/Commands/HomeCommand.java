package com.jamesst20.jcommandessentials.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jamesst20.jcommandessentials.Objects.JPlayerConfig;
import com.jamesst20.jcommandessentials.Utils.Methods;

public class HomeCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
		if (Methods.isConsole(cs)) {
			Methods.sendPlayerMessage(cs, ChatColor.RED + "The console can't tp home.");
			return true;
		}
		if (args.length == 0) {
			if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.home.default")) {
				return true;
			}
			JPlayerConfig playerConf = new JPlayerConfig((Player) cs);
			if (playerConf.getHome() != null) {
				((Player) cs).teleport(playerConf.getHome());
				Methods.sendPlayerMessage(cs, "You teleported to home.");
			} else {
				Methods.sendPlayerMessage(cs, ChatColor.RED + "Home not set.");
			}
		} else if (args.length == 1) {
			if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.home.custom")) {
				return true;
			}
			JPlayerConfig playerConf = new JPlayerConfig((Player) cs);
			if (playerConf.getHome(args[0]) != null) {
				((Player) cs).teleport(playerConf.getHome(args[0]));
				Methods.sendPlayerMessage(cs, "You teleported to " + Methods.red(args[0]) + ".");
			} else {
				Methods.sendPlayerMessage(cs, "Your home " + Methods.red(args[0]) + " is not set.");
			}
		} else {

			return false;
		}
		return true;
	}
}