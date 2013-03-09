package com.jamesst20.jcommandessentials.Commands;

import com.jamesst20.jcommandessentials.Utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpDenyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (Methods.isConsole(cs)) {
            Methods.sendPlayerMessage(cs, ChatColor.RED + "Console can't deny a tp.");
            return true;
        } else if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.tpadeny")) {
            return true;
        } else if (args.length == 0) {
            Player playerWhoAccept = ((Player) cs);
            Player secondPlayer;
            if (TpaCommand.tpaPlayers.containsKey(playerWhoAccept.getName())) {
                //Dealing with /tpa
                secondPlayer = Bukkit.getServer().getPlayer(TpaCommand.tpaPlayers.get(playerWhoAccept.getName()));
                TpaCommand.tpaPlayers.remove(playerWhoAccept.getName());
            } else if (TpaHereCommand.tpaPlayers.containsKey(playerWhoAccept.getName())) {
                //Dealing with /tpahere
                secondPlayer = Bukkit.getServer().getPlayer(TpaHereCommand.tpaPlayers.get(playerWhoAccept.getName()));
                TpaHereCommand.tpaPlayers.remove(playerWhoAccept.getName());
            } else {
                Methods.sendPlayerMessage(cs, ChatColor.RED + "You have no pending teleport request.");
                return true;
            }
            Methods.sendPlayerMessage(secondPlayer, Methods.red(playerWhoAccept.getDisplayName()) + " has denied your teleport request.");
            Methods.sendPlayerMessage(playerWhoAccept, "You denied " + Methods.red(secondPlayer.getDisplayName()) + "'s teleport request.");
        } else {
            return false;
        }
        return true;
    }
}