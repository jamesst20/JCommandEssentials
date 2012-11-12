package com.jamesst20.jcommandessentials.Commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import com.jamesst20.jcommandessentials.Objects.JOfflinePlayerConfig;
import com.jamesst20.jcommandessentials.Utils.Methods;

public class UnbanCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender cs, Command cmnd, String cmd, String[] args){
		if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.unban")){
			return true;
		}
		if (args.length == 1){
			OfflinePlayer player = Bukkit.getServer().getOfflinePlayer(args[0]);
			if (player.isBanned()){
				player.setBanned(false);
				JOfflinePlayerConfig pConfig = new JOfflinePlayerConfig(player);
				pConfig.removeBanReason();
				Methods.sendPlayerMessage(cs,
						"The player " + Methods.red(args[0]) + " is now " + Methods.red("forgiven") + ".");
				Methods.broadcastMessage("The player " + Methods.red(args[0]) + " is now " + Methods.red("forgiven") + ".");
			}else{
				Methods.sendPlayerMessage(cs, Methods.red(player.getName()) + " isn't banned.");
			}
			return true;
		}else{
			return false;
		}
	}

}
