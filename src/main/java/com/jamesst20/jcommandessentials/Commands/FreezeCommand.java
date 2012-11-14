package com.jamesst20.jcommandessentials.Commands;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jamesst20.jcommandessentials.Utils.Methods;

public class FreezeCommand implements CommandExecutor {
	public static HashSet<String> frozenPlayers = new HashSet<String>();

	@Override
	public boolean onCommand(CommandSender cs, Command cmnd, String cmd, String[] args) {
		if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.freeze")) {
			return true;
		}
		if (args.length != 1) {
			return false;
		}
		Player player = Bukkit.getServer().getPlayer(args[0]);
		if (player != null) {
			if (!frozenPlayers.contains(player.getName())) {
				frozenPlayers.add(player.getName());
				Methods.sendPlayerMessage(player, "You are now " + Methods.red("frozen") + "!");
				Methods.sendPlayerMessage(cs, Methods.red(player.getDisplayName()) + " is now frozen.");
			} else {
				frozenPlayers.remove(player.getName());
				Methods.sendPlayerMessage(player, "You are now " + Methods.red("unfrozen") + "!");
				Methods.sendPlayerMessage(cs, Methods.red(player.getDisplayName()) + " is now unfrozen.");
			}
			return true;
		} else {
			Methods.playerNotFound(cs, args[0]);
			return true;
		}
	}

}
