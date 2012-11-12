package com.jamesst20.jcommandessentials.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jamesst20.jcommandessentials.Utils.Methods;

public class LockCommand implements CommandExecutor {
	public static boolean serverLocked = false;
	@Override
	public boolean onCommand(CommandSender cs, Command cmnd, String cmd, String[] args) {
		if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.lock")){
			return true;
		}
		if (args.length != 0){
			return false;
		}
		if (!serverLocked){
			serverLocked = true;
			Methods.broadcastMessage("The server is now locked!");
		}else{
			serverLocked = false;
			Methods.broadcastMessage("The server is now unlocked!");
		}
		return true;
	}
	
	public static boolean byPass(Player player){
		return Methods.hasPermission(player, "JCMDEss.bypass.lock");
	}

}
