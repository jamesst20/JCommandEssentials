package com.jamesst20.jcommandessentials.Commands;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jamesst20.jcommandessentials.Utils.Methods;

public class GodCommand implements CommandExecutor {
	public static HashSet<String> godPlayers = new HashSet<String>();

	@Override
	public boolean onCommand(CommandSender cs, Command cmnd, String cmd, String[] args) {
		if (args.length == 0){
			if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.god.self")) {
				return true;
			}
			if(Methods.isConsole(cs)){
				Methods.sendPlayerMessage(cs, ChatColor.RED + "Console can only enable god mode on other players.");
				return true;
			}
			if (!godPlayers.contains(cs.getName())){
				godPlayers.add(cs.getName());
				Methods.sendPlayerMessage(cs, "God mode " + Methods.red("enabled") + ".");
			}else{
				godPlayers.remove(cs.getName());
				Methods.sendPlayerMessage(cs, "God mode " + Methods.red("disabled") + ".");
			}
		}else if (args.length == 1){
			if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.god.others")) {
				return true;
			}
			Player target = Bukkit.getServer().getPlayer(args[0]);
			if (target == null){
				Methods.sendPlayerMessage(cs, "The player " + Methods.red(args[0]) + " couldn't be found.");
				return true;
			}
			if (!godPlayers.contains(target.getName())){
				godPlayers.add(target.getName());
				Methods.sendPlayerMessage(cs, "You " +  Methods.red("enabled") + " god mode for " + Methods.red(target.getDisplayName()) + ".");
				Methods.sendPlayerMessage(target, "God mode " + Methods.red("enabled") + ".");
			}else{
				godPlayers.remove(target.getName());
				Methods.sendPlayerMessage(cs, "You " +  Methods.red("disabled") + " god mode for " + Methods.red(target.getDisplayName()) + ".");
				Methods.sendPlayerMessage(target, "God mode " + Methods.red("disabled") + ".");
			}
		}else{
			return false;
		}
		return true;
	}
}
