package com.jamesst20.jcommandessentials.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jamesst20.jcommandessentials.Utils.AfkUtils;
import com.jamesst20.jcommandessentials.Utils.Methods;

public class AfkCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
		if (args.length == 0) {
			if (Methods.isConsole(cs)) {
				Methods.sendPlayerMessage(cs, ChatColor.RED + "The console can't be afk.");
				return true;
			}
			if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.afk.cmd")) {
				return true;
			}
			Player player = ((Player) cs);
			if (!AfkUtils.isPlayerAfk(player)) {
				AfkUtils.setPlayerState(player, true);
			} else {
				AfkUtils.setPlayerState(player, false);
			}
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("enable")) {
				if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.afk.toggle")) {
					return true;
				}
				AfkUtils.toggleAutoAfk(true);
				Methods.sendPlayerMessage(cs, "Auto afk has been " + Methods.red("enabled") + ".");
				return true;
			} else if (args[0].equalsIgnoreCase("disable")) {
				if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.afk.toggle")) {
					return true;
				}
				AfkUtils.toggleAutoAfk(false);
				Methods.sendPlayerMessage(cs, "Auto afk has been " + Methods.red("disabled") + ".");
				return true;
			} else {
				return false;
			}
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("delay")) {
				if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.afk.setdelay")) {
					return true;
				}
				try {
					long delay = 0;
					delay = Long.parseLong(args[1]);
					AfkUtils.setDelay(delay);
					Methods.sendPlayerMessage(cs, "Auto AFK delay set to " + Methods.red(String.valueOf(delay))
							+ " seconds.");
					return true;
				} catch (NumberFormatException e) {
					Methods.sendPlayerMessage(cs, ChatColor.RED + "Invalid delay. It must be numbers in seconds.");
					return true;
				}
			} else {
				return false;
			}
		}
		return true;
	}

}
