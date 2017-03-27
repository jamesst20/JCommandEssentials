/*
 * Copyright (C) 2017 James St-Pierre
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
package com.jamesst20.jcommandessentials.commands;

import com.jamesst20.jcommandessentials.JCMDEss;
import com.jamesst20.jcommandessentials.interfaces.SpongeCommand;
import org.spongepowered.api.command.CommandSource;
import com.jamesst20.jcommandessentials.utils.Methods;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.Sponge;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.text.format.TextColors;

public class WorkbenchCommand implements SpongeCommand{

    @Override
    public String getCommandUsage() {
        return "/workbench [player]";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("workbench", "craft", "crafting", "craftingtable", "crafttable", "wb", "ct");
    }

    @Override
    public SpongeCommandResult executeCommand(CommandSource src, String[] args) {
        if (!Methods.hasPermission(src, "JCMDEss.commands.workbench")) {
            return SpongeCommandResult.NO_PERMISSION;        
        }
        
        if (args.length == 0) {
            if (src instanceof ConsoleSource) {
                Methods.sendPlayerMessage(src, Text.of(TextColors.RED, "The console can't open a workbench for itself."));
                return SpongeCommandResult.SUCCESS;
            }
            //TODO: Fix workbench inventory
            Inventory workbench = Inventory.builder().of(InventoryArchetypes.WORKBENCH).build(JCMDEss.plugin);
            ((Player) src).openInventory(workbench, Cause.of(NamedCause.of("Plugin", JCMDEss.plugin)));

            Methods.sendPlayerMessage(src, Text.of("You forced yourself to open a workbench."));
        } else if (args.length == 1) {
            Player player = Sponge.getServer().getPlayer(args[0]).orElse(null);
            if (player != null) {
                //TODO: Fix workbench inventory
                Inventory workbench = Inventory.builder().of(InventoryArchetypes.WORKBENCH).build(JCMDEss.plugin);
                player.openInventory(workbench, Cause.of(NamedCause.of("Plugin", JCMDEss.plugin)));
                
                Methods.sendPlayerMessage(src, Text.of(TextColors.RED, "You forced " + args[0] + " to open a workbench."));
            } else {
                Methods.sendPlayerNotFound(src, args[0]);
            }
        } else {
            return SpongeCommandResult.INVALID_SYNTHAX;
        }
        return SpongeCommandResult.SUCCESS;
    }

    @Override
    public Optional<Text> getShortDescription(CommandSource source) {
        return Optional.of(Text.of("Open a workbench anywhere"));
    }
    
}
