package com.jamesst20.jcommandessentials.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jamesst20.jcommandessentials.Utils.Methods;

public class TimeCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender cs, Command cmnd, String cmd, String[] args) {
		if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.time")) {
			return true;
		}
		if (args.length == 0) {
			if (cmd.equalsIgnoreCase("day")) {
				setDay(cs);
				return true;
			} else if (cmd.equals("night")) {
				setNight(cs);
				return true;
			} else {
				return false;
			}
		} else if (args.length == 1) {
			if (cmd.equalsIgnoreCase("day")) {
				setDay(cs, args[0]);
				return true;
			} else if (cmd.equals("night")) {
				setNight(cs, args[0]);
				return true;
			} else if (cmd.equalsIgnoreCase("time")) {
				if (args[0].equalsIgnoreCase("day")) {
					setDay(cs);
					return true;
				} else if (args[0].equalsIgnoreCase("night")) {
					setNight(cs);
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else if (args.length == 2) {
			if (cmd.equalsIgnoreCase("time")) {
				if (args[0].equalsIgnoreCase("day")) {
					setDay(cs, args[1]);
					return true;
				} else if (args[0].equalsIgnoreCase("night")) {
					setNight(cs, args[1]);
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private void setDay(CommandSender cs) {
		if (Methods.isConsole(cs)) {
			Methods.sendPlayerMessage(cs, ChatColor.RED + "The console must specify a world.");
			return;
		}
		((Player) cs).getWorld().setTime(DAY_TIME);
		Methods.sendPlayerMessage(cs, "Current world time have been set to " + Methods.red("day"));
	}

	private void setNight(CommandSender cs) {
		if (Methods.isConsole(cs)) {
			Methods.sendPlayerMessage(cs, ChatColor.RED + "The console must specify a world.");
			return;
		}
		((Player) cs).getWorld().setTime(NIGHT_TIME);
		Methods.sendPlayerMessage(cs, "Current world time have been set to " + Methods.red("night"));
	}

	private void setDay(CommandSender cs, String worldStr) {
		World world = Bukkit.getServer().getWorld(worldStr);
		if (world != null) {
			world.setTime(DAY_TIME);
			Methods.sendPlayerMessage(cs, world.getName() + " time have been set to " + Methods.red("day"));
		} else {
			Methods.sendPlayerMessage(cs, "The world " + Methods.red(worldStr) + " couldn't be found.");
		}
	}

	private void setNight(CommandSender cs, String worldStr) {
		World world = Bukkit.getServer().getWorld(worldStr);
		if (world != null) {
			world.setTime(NIGHT_TIME);
			Methods.sendPlayerMessage(cs, world.getName() + " time have been set to " + Methods.red("night."));
		} else {
			Methods.sendPlayerMessage(cs, "The world " + Methods.red(worldStr) + " couldn't be found.");
		}
	}

	int DAY_TIME = 200;
	int NIGHT_TIME = 14400;
}
