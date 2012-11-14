package com.jamesst20.jcommandessentials.Commands;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jamesst20.jcommandessentials.Utils.Methods;

public class VanishCommand implements CommandExecutor {
	public static HashSet<String> vanishedPlayers = new HashSet<String>();

	@Override
	public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
		if (args.length == 0) {
			if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.vanish.self")) {
				return true;
			}
			if (Methods.isConsole(cs)) {
				Methods.sendPlayerMessage(cs, ChatColor.RED + "The console can only vanish other players.");
				return true;
			}
			if (!vanishedPlayers.contains(cs.getName())) {
				vanishedPlayers.add(cs.getName());
				Methods.sendPlayerMessage(cs, "You are now " + Methods.red("invisible") + " from all players!");
				Methods.hidePlayer(((Player) cs));
			} else {
				vanishedPlayers.remove(cs.getName());
				Methods.sendPlayerMessage(cs, "You are now " + Methods.red("visible") + " to all players!");
				Methods.showPlayer(((Player) cs));
			}
			return true;
		} else if (args.length == 1) {
			if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.vanish.others")) {
				return true;
			}
			Player player = Bukkit.getServer().getPlayer(args[0]);
			if (player != null) {
				if (!vanishedPlayers.contains(player.getName())) {
					vanishedPlayers.add(player.getName());
					Methods.sendPlayerMessage(cs, "The player " + Methods.red(player.getDisplayName()) + " is now "
							+ Methods.red("invisible") + " from all players.");
					Methods.sendPlayerMessage(player, "You are now " + Methods.red("invisible") + " from all players!");
					Methods.hidePlayer(player);
				} else {
					vanishedPlayers.remove(player.getName());
					Methods.sendPlayerMessage(cs, "The player " + Methods.red(player.getDisplayName()) + " is now "
							+ Methods.red("visible") + " to all players.");
					Methods.sendPlayerMessage(player, "You are now " + Methods.red("visible") + " to all players!");
					Methods.showPlayer(player);
				}
			} else {
				Methods.playerNotFound(cs, args[0]);
				return true;
			}
			return true;
		}
		return true;
	}

}
