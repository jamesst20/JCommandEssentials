package com.jamesst20.jcommandessentials.Commands;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jamesst20.jcommandessentials.Utils.Methods;

public class FlyCommand implements CommandExecutor {
	public static HashSet<String> flyingPlayers = new HashSet<String>();

	@Override
	public boolean onCommand(CommandSender cs, Command cmnd, String cmd,
			String[] args) {
		if (args.length == 0) {
			if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.fly.self")) {
				return true;
			}
			if (Methods.isConsole(cs)) {
				Methods.sendPlayerMessage(cs, ChatColor.RED
						+ "You can only enable fly for others from console.");
				return true;
			}
			if (!flyingPlayers.contains(cs.getName())) {
				flyingPlayers.add(cs.getName());
				((Player) cs).setAllowFlight(true);
				((Player) cs).setFlying(true);
				Methods.sendPlayerMessage(cs,
						"Flying is now " + Methods.red("enabled") + ".");
			} else {
				flyingPlayers.remove(cs.getName());
				((Player) cs).setAllowFlight(false);
				((Player) cs).setFlying(false);
				Methods.sendPlayerMessage(cs,
						"Flying is now " + Methods.red("disabled") + ".");
			}
		} else if (args.length == 1) {
			if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.fly.others")) {
				return true;
			}
			Player target = Bukkit.getServer().getPlayer(args[0]);
			if (target != null) {
				if (!flyingPlayers.contains(target.getName())) {
					flyingPlayers.add(target.getName());
					target.setAllowFlight(true);
					target.setFlying(true);
					Methods.sendPlayerMessage(cs,
							"Flying is now " + Methods.red("enabled") + " for " + Methods.red(target.getDisplayName()) + ".");
					Methods.sendPlayerMessage(target, "Flying " + Methods.red("enabled") + ".");
				} else {
					flyingPlayers.remove(target.getName());
					target.setAllowFlight(false);
					target.setFlying(false);
					Methods.sendPlayerMessage(cs,
							"Flying is now " + Methods.red("disabled") + " for " + Methods.red(target.getDisplayName()) + ".");
					Methods.sendPlayerMessage(target, "Flying " + Methods.red("disabled") + ".");
				}
			} else {
				Methods.sendPlayerMessage(cs,
						"The player " + Methods.red(args[0])
								+ " couldn't be found.");
				return true;
			}

		} else {
			return false;
		}
		return true;
	}

}
