package com.jamesst20.jcommandessentials.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jamesst20.jcommandessentials.Utils.Methods;

public class WeatherCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
		if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.weather")) {
			return true;
		}
		if (args.length == 0) {
			if (Methods.isConsole(cs)) {
				Methods.sendPlayerMessage(cs, ChatColor.RED + "The console must specify a world.");
				return true;
			}
			if (cmd.equalsIgnoreCase("sun")) {
				((Player) cs).getWorld().setStorm(false);
				Methods.sendPlayerMessage(cs, "Current world weather set to sunny!");
				return true;
			} else if (cmd.equalsIgnoreCase("rain") || cmd.equalsIgnoreCase("storm")) {
				((Player) cs).getWorld().setStorm(true);
				((Player) cs).getWorld().setThundering(false);
				Methods.sendPlayerMessage(cs, "Current world weather set to stormy!");
				return true;
			} else if (cmd.equalsIgnoreCase("thunder")) {
				((Player) cs).getWorld().setStorm(true);
				((Player) cs).getWorld().setThundering(true);
				Methods.sendPlayerMessage(cs, "The thunder is striking through the rain!");
				return true;
			} else {
				return false;
			}
		} else if (args.length == 1) {
			if (cmd.equalsIgnoreCase("weather")) {
				if (Methods.isConsole(cs)) {
					Methods.sendPlayerMessage(cs, ChatColor.RED + "The console must specify a world.");
					return true;
				}
				if (args[0].equalsIgnoreCase("sun")) {
					((Player) cs).getWorld().setStorm(false);
					Methods.sendPlayerMessage(cs, "Current world weather set to sunny!");
					return true;
				} else if (args[0].equalsIgnoreCase("rain") || args[0].equalsIgnoreCase("storm")) {
					((Player) cs).getWorld().setStorm(true);
					((Player) cs).getWorld().setThundering(false);
					Methods.sendPlayerMessage(cs, "Current world weather set to stormy!");
					return true;
				} else if (args[0].equalsIgnoreCase("thunder")) {
					((Player) cs).getWorld().setStorm(true);
					((Player) cs).getWorld().setThundering(true);
					Methods.sendPlayerMessage(cs, "The thunder is striking through the rain!");
					return true;
				} else {
					return false;
				}
			} else if (cmd.equalsIgnoreCase("sun")) {
				if (Bukkit.getServer().getWorld(args[0]) != null) {
					Bukkit.getServer().getWorld(args[0]).setStorm(false);
					Methods.sendPlayerMessage(cs, "Weather set to sunnyin " + Bukkit.getServer().getWorld(args[0]).getName()
							+ "!");
					return true;
				} else {
					Methods.sendPlayerMessage(cs, "The world " + Methods.red(args[0]) + " doesn't exist.");
					return true;
				}
			} else if (cmd.equalsIgnoreCase("rain") || cmd.equalsIgnoreCase("storm")) {
				if (Bukkit.getServer().getWorld(args[0]) != null) {
					Bukkit.getServer().getWorld(args[0]).setStorm(true);
					Bukkit.getServer().getWorld(args[0]).setThundering(false);
					Methods.sendPlayerMessage(cs, "Weather set to stormy in "
							+ Bukkit.getServer().getWorld(args[0]).getName() + "!");
					return true;
				} else {
					Methods.sendPlayerMessage(cs, "The world " + Methods.red(args[0]) + " doesn't exist.");
					return true;
				}
			} else if (cmd.equalsIgnoreCase("thunder")) {
				if (Bukkit.getServer().getWorld(args[0]) != null) {
					Bukkit.getServer().getWorld(args[0]).setStorm(true);
					Bukkit.getServer().getWorld(args[0]).setThundering(true);
					Methods.sendPlayerMessage(cs, "The thunder is striking through the rain in "
							+ Bukkit.getServer().getWorld(args[0]).getName() + "!");
					return true;
				} else {
					Methods.sendPlayerMessage(cs, "The world " + Methods.red(args[0]) + " doesn't exist.");
					return true;
				}
			} else {
				return false;
			}
		} else if (args.length == 2) {
			if (cmd.equalsIgnoreCase("weather")) {
				if (Bukkit.getServer().getWorld(args[1]) != null) {
					if (args[0].equalsIgnoreCase("sun")) {
						Bukkit.getWorld(args[1]).setStorm(false);
						Methods.sendPlayerMessage(cs, "The weather of " + Methods.red(args[1]) + " set to sunny!");
						return true;
					} else if (args[0].equalsIgnoreCase("rain") || args[0].equalsIgnoreCase("storm")) {
						Bukkit.getWorld(args[1]).setStorm(true);
						Bukkit.getWorld(args[1]).setThundering(false);
						Methods.sendPlayerMessage(cs, "The weather of " + Methods.red(args[1]) + " set to stormy!");
						return true;
					} else if (args[0].equalsIgnoreCase("thunder")) {
						Bukkit.getWorld(args[1]).setStorm(true);
						Bukkit.getWorld(args[1]).setThundering(true);
						Methods.sendPlayerMessage(cs, "The thunder is striking through the rain in " + Methods.red(args[1])
								+ "!");
						return true;
					} else {
						return false;
					}
				} else {
					Methods.sendPlayerMessage(cs, "The world " + Methods.red(args[1]) + " doesn't exist.");
					return true;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
