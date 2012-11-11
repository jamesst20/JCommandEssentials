package com.jamesst20.jcommandessentials.Commands;


import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jamesst20.jcommandessentials.Methods.Methods;
import com.jamesst20.jcommandessentials.Objects.JPlayerConfig;

public class SetHomeCommand implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String cmd, String[] args) {
        	if (Methods.isConsole(cs)){
        		Methods.sendPlayerMessage(cs, ChatColor.RED + "The console can't set home.");
        		return true;
        	}
        	if (args.length == 0){
        		if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.setHome.default")){
                    return true;
                }
        		JPlayerConfig playerConf = new JPlayerConfig((Player)cs);
        		playerConf.setHome();
        		Methods.sendPlayerMessage(cs, "Home set.");
        	}else if (args.length == 1){
        		if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.setHome.custom")){
                    return true;
                }
        		JPlayerConfig playerConf = new JPlayerConfig((Player)cs);
        		playerConf.setHome(args[0]);
        		Methods.sendPlayerMessage(cs, "Home " + Methods.red(args[0]) + " set.");
        	}else{
        		return false;
        	}
        return true;
    }
    
}