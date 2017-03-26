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

import com.jamesst20.jcommandessentials.interfaces.SpongeCommand;
import com.jamesst20.jcommandessentials.utils.Methods;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import java.util.Optional;


public class WhatIsItCommand implements SpongeCommand {
    @Override
    public String getCommandUsage() {
        return "/whatisit";
    }

    @Override
    public SpongeCommandResult executeCommand(CommandSource src, String[] args) {
        if (Methods.hasPermission(src, "JCMDEss.commands.whatisit")) {
            if(src instanceof Player) {
                Player player = (Player) src;
                ItemStack mainItem = player.getItemInHand(HandTypes.MAIN_HAND).orElse(null);
                ItemStack offItem = player.getItemInHand(HandTypes.OFF_HAND).orElse(null);
                if (mainItem != null) {
                    Methods.sendPlayerMessage(player, Text.of("Holding in left hand : " + mainItem.getItem().getId()));
                }
                if (offItem != null) {
                    Methods.sendPlayerMessage(player, Text.of("Holding in right hand : " + offItem.getItem().getId()));
                }
            }
            else if(src instanceof ConsoleSource) {
                Methods.sendPlayerMessage(src, Text.of("You must be a player to use this command."));
            }
        } else {
            return SpongeCommandResult.NO_PERMISSION;
        }
        return SpongeCommandResult.SUCCESS;
    }

    @Override
    public Optional<Text> getShortDescription(CommandSource src) {
        return Optional.of(Text.of("Describe item you are holding"));
    }
}
