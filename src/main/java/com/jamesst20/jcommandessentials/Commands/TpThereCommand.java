package com.jamesst20.jcommandessentials.Commands;

import com.jamesst20.jcommandessentials.Utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpThereCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (Methods.isConsole(cs)) {
            Methods.sendPlayerMessage(cs, ChatColor.RED + "The console has no target block.");
            return true;
        }
        if (args.length == 0) {
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.tpthere.self")) {
                return true;
            }
            Player player = ((Player) cs);
            player.teleport(player.getTargetBlock(null, 0).getRelative(BlockFace.UP, 1).getLocation());
            Methods.sendPlayerMessage(player, "You teleported to the target block!");
            return true;
        } else if (args.length == 1) {
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.tpthere.others")) {
                return true;
            }
            Player player = Bukkit.getServer().getPlayer(args[0]);
            if (player != null) {
                player.teleport(((Player) cs).getTargetBlock(null, 0).getRelative(BlockFace.UP, 1).getLocation());
                Methods.sendPlayerMessage(cs, "You teleported " + Methods.red(player.getDisplayName()) + " to your target block.");
                Methods.sendPlayerMessage(player, "You have been teleported to " + Methods.red(cs.getName() + "'s") + " target block!");
                return true;
            } else {
                Methods.playerNotFound(cs, args[0]);
                return true;
            }
        } else {
            return false;
        }
    }
}
