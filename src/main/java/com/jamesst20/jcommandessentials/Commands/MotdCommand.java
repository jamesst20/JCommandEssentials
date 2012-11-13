package com.jamesst20.jcommandessentials.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jamesst20.jcommandessentials.Utils.Methods;
import com.jamesst20.jcommandessentials.Utils.Motd;

public class MotdCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender cs, Command cmnd, String cmd, String[] args) {

		if (args.length == 0) {
			if (Methods.isConsole(cs)) {
				Methods.sendPlayerMessage(cs, ChatColor.RED + "Sorry, only in-game players can use /motd.");
				Methods.sendPlayerMessage(cs, ChatColor.RED + "You can only use : /motd set/enable/disable");
				return true;
			}
			if (Motd.isEnable()) {
				if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.motd.read")) {
					return true;
				}
				Motd.sendMotd(((Player) cs));
				return true;
			} else {
				Motd.motdDisabled(cs);
				return true;
			}
		} else if (args.length == 1) {
			if (!Methods.hasPermissionTell(cs, "JCMDEss.motd.toggle")) {
				return true;
			}
			if (args[0].equalsIgnoreCase("enable")) {
				Motd.enableMotd();
				Methods.sendPlayerMessage(cs, "Motd is now " + Methods.red("enabled") + "!");
				return true;
			} else if (args[0].equalsIgnoreCase("disable")) {
				Motd.disableMotd();
				Methods.sendPlayerMessage(cs, "Motd is now " + Methods.red("disabled") + "!");
				return true;
			} else {
				return false;
			}
		} else if (args.length > 1) {
			if (args[0].equalsIgnoreCase("set")) {
				if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.motd.set")) {
					return true;
				}
				Motd.setMotd("", cs);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

}
