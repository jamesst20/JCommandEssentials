package com.jamesst20.jcommandessentials.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jamesst20.jcommandessentials.Utils.Methods;

public class OpenInventoryCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command cmnd, String cmd, String[] args) {
		if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.openinventory")) {
			return true;
		}
		if (Methods.isConsole(cs)) {
			Methods.sendPlayerMessage(cs, ChatColor.RED + "The console can't open an inventory.");
			return true;
		}
		if (args.length != 1) {
			return false;
		}
		Player player = Bukkit.getServer().getPlayer(args[0]);
		if (player != null) {
			Player me = ((Player) cs);
			me.openInventory(player.getInventory());
			Methods.sendPlayerMessage(cs, "You opened the inventory of " + Methods.red(player.getDisplayName()) + ".");
			return true;
		} else {
			Methods.playerNotFound(cs, args[0]);
			return true;
		}
	}

}
