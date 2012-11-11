package com.jamesst20.jcommandessentials.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jamesst20.jcommandessentials.Methods.Methods;

public class KillCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender cs, Command cmnd, String cmd, String[] args) {
		if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.kill")){
			return true;
		}
		if (args.length == 0){
			if(Methods.isConsole(cs)){
				Methods.sendPlayerMessage(cs, ChatColor.RED +"The console can't kill itself.");
				return true;
			}
			Player player = ((Player)cs);
			player.setHealth(0);
			player.getWorld().strikeLightningEffect(player.getLocation());
			Methods.sendPlayerMessage(cs, "You killed yourself.");
			return true;
		}else if(args.length == 1){
			Player player = Bukkit.getServer().getPlayer(args[0]);
			if (player != null){
				player.setHealth(0);
				player.getWorld().strikeLightningEffect(player.getLocation());
				Methods.sendPlayerMessage(cs, "You killed " + Methods.red(player.getDisplayName()) + ".");
			}else{
				Methods.sendPlayerMessage(cs, "The player " + Methods.red(args[0])+ " couldn't be found.");
			}
			return true;
		}else{
			return false;
		}	
	}

}
