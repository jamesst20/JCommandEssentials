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
import java.util.Arrays;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SetItemDescriptionCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (args.length < 1 || args.length > 3) {
            return false;
        }

        if (Methods.isConsole(cs)) {
            Methods.sendPlayerMessage(cs, ChatColor.RED + "Console can't change its own item description.");
            return true;
        }
        if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.setitemdescription")){
            return true;
        }
        Player p = (Player) cs;
        ItemStack item = p.getItemInHand();
        ItemMeta itemMeta = item.getItemMeta();
        List<String> descriptionLines = (args.length == 1) ? Arrays.asList(args[0]) : (((args.length >= 3) ? Arrays.asList(args[0], args[1], args[2])
                : ((args.length >= 2) ? Arrays.asList(args[0], args[1]) : Arrays.asList("This will never happen.")))); // Had fun making something hard to read 
        itemMeta.setLore(descriptionLines);
        item.setItemMeta(itemMeta);
        Methods.sendPlayerMessage(cs, "Item description has been set.");
        return true;
    }

}
