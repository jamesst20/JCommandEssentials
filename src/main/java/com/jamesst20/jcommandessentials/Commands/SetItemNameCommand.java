/* 
 * Copyright (C) 2013 James
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.jamesst20.jcommandessentials.Commands;

import com.jamesst20.jcommandessentials.Utils.Methods;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author James
 */
public class SetItemNameCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (args.length != 1) {
            return false;
        }
        if (Methods.isConsole(cs)) {
            Methods.sendPlayerMessage(cs, ChatColor.RED + "Console can't change its own item name.");
            return true;
        }
        if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.setitemname")){
            return true;
        }
        Player p = (Player) cs;
        ItemStack item = p.getItemInHand();
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(args[0]);
        item.setItemMeta(itemMeta);
        Methods.sendPlayerMessage(cs, "Item name has been set to " + Methods.red(args[0]) + ".");
        return true;
    }
    
}
