package com.jamesst20.jcommandessentials.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jamesst20.jcommandessentials.Utils.Methods;

public class SudoCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender cs, Command cmnd, String cmd, String[] args) {
		if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.sudo")){
			return true;
		}
		if (args.length < 2){
			return false;
		}
		String playerStr = args[0];
		String theCommand = "";
		for (int i = 1; i < args.length; i++){
			theCommand += args[i] + " ";
		}
		theCommand = Methods.replaceLast(theCommand, " ", "");
		Player player = Bukkit.getServer().getPlayer(playerStr);
		if (player != null){
			player.performCommand(theCommand);
			Methods.sendPlayerMessage(cs, "Forced " + Methods.red(player.getDisplayName()) + " to run: '"
					+ Methods.red(theCommand) + "'");
			return true;
		}else{
			Methods.sendPlayerMessage(cs, "The player " + Methods.red(playerStr) + " couldn't be found.");
			return true;
		}
	}

}
