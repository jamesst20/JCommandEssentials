package com.jamesst20.jcommandessentials.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.jamesst20.jcommandessentials.Utils.Methods;

public class SpawnMobCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
		if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.spawnmob")) {
			return true;
		}
		if (Methods.isConsole(cs)) {
			Methods.sendPlayerMessage(cs, "The console can't spawn mobs.");
			return true;
		}
		if (args.length == 0) {
			StringBuilder x = new StringBuilder();
			for (EntityType e : EntityType.values()) {
				x.append(e.toString()).append(", ");
			}
			Methods.sendPlayerMessage(cs, "You can spawn the following mobs/entities: ");
			Methods.sendPlayerMessage(cs, x.toString().trim());
			return true;
		} else if (args.length == 2) {
			int count = 0;
			Player player = ((Player) cs);
			try {
				count = Integer.parseInt(args[1]);
				for (int i = 0; i < count; i++) {
					player.getWorld().spawnEntity(
							player.getTargetBlock(null, 0).getLocation().getBlock().getRelative(0, 1, 0).getLocation(),
							EntityType.valueOf(args[0].toUpperCase()));
				}
				Methods.sendPlayerMessage(
						cs,
						"You spawned " + Methods.red(String.valueOf(count)) + " "
								+ Methods.red(EntityType.valueOf(args[0].toUpperCase()).getName()) + ".");
				return true;
			} catch (NumberFormatException e) {
				Methods.sendPlayerMessage(cs, ChatColor.RED + "You must enter numbers for quantity.");
				return true;
			} catch (IllegalArgumentException e) {
				Methods.sendPlayerMessage(cs, ChatColor.RED + "Invalid entity.");
				return true;
			}
		} else {
			return false;
		}
	}
}
