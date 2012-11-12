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
	public boolean onCommand(CommandSender cs, Command cmnd, String cmd, String[] args) {
		if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.afk")) {
			return true;
		}
		if (Methods.isConsole(cs)){
			Methods.sendPlayerMessage(cs, ChatColor.RED + "The console can't be afk.");
			return true;
		}
		if (args.length != 0){
			return false;
		}
		Player player = ((Player)cs);
		if (!AfkUtils.isPlayerAfk(player)){
			AfkUtils.setPlayerState(player, true);
		}else{
			AfkUtils.setPlayerState(player, false);
		}
		return true;
	}

}
