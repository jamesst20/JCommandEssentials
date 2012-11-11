package com.jamesst20.jcommandessentials.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jamesst20.jcommandessentials.Methods.Methods;

public class ClearInventoryCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender cs, Command cmnd, String cmd, String[] args) {
		if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.clearinventory")) {
			return true;
		}
		if (args.length == 0) {
			if(Methods.isConsole(cs)){
				Methods.sendPlayerMessage(cs, ChatColor.RED + "The console can't clean its own inventory.");
				return true;
			}
			Player player = ((Player)cs);
			player.getInventory().clear();
			Methods.sendPlayerMessage(cs, "You cleared your inventory.");
			return true;
		} else if (args.length == 1) {
			Player player = Bukkit.getServer().getPlayer(args[0]);
			if (player != null){
				player.getInventory().clear();
				Methods.sendPlayerMessage(cs, "You cleared the inventory of " + Methods.red(player.getDisplayName()) + ".");
				Methods.sendPlayerMessage(player, "Your inventory has been cleaned.");
				return true;
			}else{
				Methods.sendPlayerMessage(cs, "The player " + Methods.red(args[0]) + " couldn't be found.");
				return true;
			}
		} else {
			return false;
		}
	}

}
