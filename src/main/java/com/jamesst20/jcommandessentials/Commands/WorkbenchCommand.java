package com.jamesst20.jcommandessentials.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jamesst20.jcommandessentials.Methods.Methods;

public class WorkbenchCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender cs, Command cmnd, String cmd, String[] args) {
		if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.workbench")) {
			return true;
		}
		if (args.length == 0){
			if (Methods.isConsole(cs)) {
				Methods.sendPlayerMessage(cs, ChatColor.RED
						+ "The console can't open a workbench for itself.");
				return true;
			}			
			Player player = ((Player)cs);
			player.openWorkbench(player.getLocation(), true);
			Methods.sendPlayerMessage(cs, "You forced yourself to open a workbench.");
			return true;
		}else if(args.length == 1){
			Player player = Bukkit.getServer().getPlayer(args[0]);
			if (player != null){
				player.openWorkbench(player.getLocation(), true);
				Methods.sendPlayerMessage(cs, "You forced " + Methods.red(player.getDisplayName()) +
						" to open a workbench.");
				return true;
			}else{
				Methods.sendPlayerMessage(cs, "The player " + Methods.red(args[0]) + " couldn't be found.");
				return true;
			}
		}else{
			return false;
		}
	}

}
