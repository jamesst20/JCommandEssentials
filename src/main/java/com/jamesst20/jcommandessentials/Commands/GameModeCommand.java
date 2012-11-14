package com.jamesst20.jcommandessentials.Commands;

import com.jamesst20.jcommandessentials.Utils.Methods;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameModeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
		if (args.length == 0) {
			if (Methods.isConsole(cs)) {
				Methods.sendPlayerMessage(cs, ChatColor.RED + "The console can't change its own gamemode.");
				return true;
			}
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("survival")
					|| args[0].equalsIgnoreCase("adventure")) {
				if (Methods.isConsole(cs)) {
					Methods.sendPlayerMessage(cs, ChatColor.RED + "The console can't change its own gamemode.");
					return true;
				}
			}
		}
		if (cmd.equalsIgnoreCase("creative")) {
			if (args.length == 0) {
				setCreative(cs.getName(), cs);
			} else if (args.length == 1) {
				if (setCreative(args[0], cs)) {
					Methods.sendPlayerMessage(cs, Methods.red(Bukkit.getServer().getPlayer(args[0]).getDisplayName())
							+ " is now in " + Methods.red("creative") + "!");
				}
			} else {
				return false;
			}
			return true;
		} else if (cmd.equalsIgnoreCase("survival")) {
			if (args.length == 0) {
				setSurvival(cs.getName(), cs);
			} else if (args.length == 1) {
				if (setSurvival(args[0], cs)) {
					Methods.sendPlayerMessage(cs, Methods.red(Bukkit.getServer().getPlayer(args[0]).getDisplayName())
							+ " is now in " + Methods.red("survival") + "!");
				}
			} else {
				return false;
			}
			return true;
		} else if (cmd.equalsIgnoreCase("adventure")) {
			if (args.length == 0) {
				setAdventure(cs.getName(), cs);
			} else if (args.length == 1) {
				if (setAdventure(args[0], cs)) {
					Methods.sendPlayerMessage(cs, Methods.red(Bukkit.getServer().getPlayer(args[0]).getDisplayName())
							+ " is now in " + Methods.red("adventure") + "!");
				}
			} else {
				return false;
			}
			return true;
		} else if (cmd.equalsIgnoreCase("gamemode") || cmd.equalsIgnoreCase("gm")) {
			if (args.length == 0) {
				return false;
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("creative")) {
					setCreative(cs.getName(), cs);
				} else if (args[0].equalsIgnoreCase("survival")) {
					setSurvival(cs.getName(), cs);
				} else if (args[0].equalsIgnoreCase("adventure")) {
					setAdventure(cs.getName(), cs);
				} else {
					return false;
				}
			} else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("creative")) {
					if (setCreative(args[1], cs)) {
						Methods.sendPlayerMessage(cs, Methods.red(Bukkit.getServer().getPlayer(args[1]).getDisplayName())
								+ " is now in " + Methods.red("creative") + "!");
					}
				} else if (args[0].equalsIgnoreCase("survival")) {
					if (setSurvival(args[1], cs)) {
						Methods.sendPlayerMessage(cs, Methods.red(Bukkit.getServer().getPlayer(args[1]).getDisplayName())
								+ " is now in " + Methods.red("survival") + "!");
					}
				} else if (args[0].equalsIgnoreCase("adventure")) {
					if (setAdventure(args[1], cs)) {
						Methods.sendPlayerMessage(cs, Methods.red(Bukkit.getServer().getPlayer(args[1]).getDisplayName())
								+ " is now in " + Methods.red("adventure") + "!");
					}
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	private boolean setCreative(String name, CommandSender cs) {
		if (name.equalsIgnoreCase(cs.getName())) {
			if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.gamemode.self")) {
				return true;
			}
		} else {
			if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.gamemode.others")) {
				return true;
			}
		}
		Player player = Bukkit.getServer().getPlayer(name);
		if (player != null) {
			player.setGameMode(GameMode.CREATIVE);
			Methods.sendPlayerMessage(player, "You are now in " + Methods.red("creative") + "!");
			return true;
		} else {
			Methods.playerNotFound(cs, name);
			return false;
		}
	}

	private boolean setSurvival(String name, CommandSender cs) {
		if (name.equalsIgnoreCase(cs.getName())) {
			if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.gamemode.self")) {
				return true;
			}
		} else {
			if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.gamemode.others")) {
				return true;
			}
		}
		Player player = Bukkit.getServer().getPlayer(name);
		if (player != null) {
			player.setGameMode(GameMode.SURVIVAL);
			Methods.sendPlayerMessage(player, "You are now in " + Methods.red("survival") + "!");
			return true;
		} else {
			Methods.playerNotFound(cs, name);
			return false;
		}
	}

	private boolean setAdventure(String name, CommandSender cs) {
		if (name.equalsIgnoreCase(cs.getName())) {
			if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.gamemode.self")) {
				return true;
			}
		} else {
			if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.gamemode.others")) {
				return true;
			}
		}
		Player player = Bukkit.getServer().getPlayer(name);
		if (player != null) {
			player.setGameMode(GameMode.ADVENTURE);
			Methods.sendPlayerMessage(player, "You are now in " + Methods.red("adventure") + "!");
			return true;
		} else {
			Methods.playerNotFound(cs, name);
			return false;
		}
	}

}
