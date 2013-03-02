package com.jamesst20.jcommandessentials.Commands;

import com.jamesst20.jcommandessentials.JCMDEssentials.JCMDEss;
import com.jamesst20.jcommandessentials.Utils.Methods;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpaCommand implements CommandExecutor {

    public static Map<String, String> tpaPlayers = new HashMap<String, String>();

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (Methods.isConsole(cs)) {
            Methods.sendPlayerMessage(cs, ChatColor.RED + "The console can't tpa.");
            return true;
        } else if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.tpa.tp")) {
            return true;
        } else if (args.length != 1) {
            return false;
        }
        final Player from = ((Player) cs);
        final Player to = Bukkit.getServer().getPlayer(args[0]);
        if (from != null && to != null) {
            tpaPlayers.put(to.getName(), from.getName()); // Destination, sender
            Methods.sendPlayerMessage(from, "You asked to tp to " + Methods.red(to.getDisplayName())
                    + ". Waiting for an answer.");
            Methods.sendPlayerMessage(to, Methods.red(from.getDisplayName()) + " has asked to teleport to you.");
            Methods.sendPlayerMessage(to, "Agree : " + Methods.red("/tpaccept"));
            Methods.sendPlayerMessage(to, "Disagree : " + Methods.red("/tpdeny"));
            JCMDEss.plugin.getServer().getScheduler().scheduleSyncDelayedTask(JCMDEss.plugin, new Runnable() {
                @Override
                public void run() {
                    if (tpaPlayers.containsKey(to.getName())) {
                        tpaPlayers.remove(to.getName());
                        Methods.sendPlayerMessage(from, Methods.red(to.getDisplayName())
                                + " didn't answer your teleport request.");
                        Methods.sendPlayerMessage(to, "You didn't answer " + Methods.red(from.getDisplayName())
                                + " teleport request.");
                    }
                }
            }, 35L * 20L);
            return true;
        } else {
            Methods.playerNotFound(cs, args[0]);
            return true;
        }
    }
}
