package com.jamesst20.jcommandessentials.Commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.jamesst20.jcommandessentials.Utils.Methods;

public class KillMobsCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
		if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.killmobs")) {
			return true;
		}
		if (args.length != 0) {
			return false;
		}
		for (World world : Bukkit.getServer().getWorlds()) {
			for (Entity e : world.getEntities()) {
				if (!(e instanceof Player)) {
					e.remove();
				}
			}
		}
		Methods.sendPlayerMessage(cs, "Killed all mobs on the server!");
		return true;
	}

}
