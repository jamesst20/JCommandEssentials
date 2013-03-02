package com.jamesst20.jcommandessentials.Commands;

import com.jamesst20.jcommandessentials.JCMDEssentials.JCMDEss;
import com.jamesst20.jcommandessentials.Utils.Methods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DisableCmdCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.DisableCMD")) {
            return true;
        }
        if (args.length != 1) {
            return false;
        }
        if (JCMDEss.plugin.getConfig().get("commands." + args[0]) != null) {
            JCMDEss.plugin.getConfig().set("commands." + args[0], Boolean.valueOf(false));
            JCMDEss.plugin.saveConfig();
            Methods.sendPlayerMessage(cs, "The Command " + Methods.red(args[0])
                    + " is now disabled. Reload/Restart is needed.");
        } else {
            Methods.sendPlayerMessage(cs, "The Command " + Methods.red(args[0]) + " doesn't exist.");
        }
        return true;
    }
}
