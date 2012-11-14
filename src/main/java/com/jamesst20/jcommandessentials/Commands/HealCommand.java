package com.jamesst20.jcommandessentials.Commands;

import com.jamesst20.jcommandessentials.Utils.Methods;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command cmnd, String cmd, String[] args) {
		if (args.length == 0) {
			if (Methods.isConsole(cs)) {
				Methods.sendPlayerMessage(cs, ChatColor.RED + "The console can't heal itself.");
				return true;
			}
			if (!Methods.hasPermission(cs, "JCMDEss.commands.heal.self")) {
				return true;
			}
			((Player) cs).setFoodLevel(20);
			((Player) cs).setHealth(20);
			Methods.sendPlayerMessage(cs, "You healed yourself!");
			return true;
		} else if (args.length == 1) {
			if (!Methods.hasPermission(cs, "JCMDEss.commands.heal.others")) {
				return true;
			}
			Player player = Bukkit.getServer().getPlayer(args[0]);
			if (player != null) {
				player.setFoodLevel(20);
				player.setHealth(20);
				Methods.sendPlayerMessage(cs, "You healed " + Methods.red(player.getDisplayName()) + ".");
				Methods.sendPlayerMessage(player, "You have been healed.");
				return true;
			} else {
				Methods.playerNotFound(cs, args[0]);
				return true;
			}
		} else {
			return false;
		}
	}

}
