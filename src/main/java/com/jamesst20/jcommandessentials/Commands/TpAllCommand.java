package com.jamesst20.jcommandessentials.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jamesst20.jcommandessentials.Utils.Methods;

public class TpAllCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command cmnd, String cmd, String[] args) {
		if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.tpall")){
			return true;
		}
		if (args.length != 0){
			return false;
		}
		if (Methods.isConsole(cs)){
			Methods.sendPlayerMessage(cs, ChatColor.RED + "The console can't tp player to itself.");
			return true;
		}
		Player me = ((Player)cs);
		for (Player player:Bukkit.getServer().getOnlinePlayers()){
			player.teleport(me);
			Methods.sendPlayerMessage(player, "You have been teleported to " + Methods.red(me.getDisplayName())+ ".");
		}
		return true;
	}

}
