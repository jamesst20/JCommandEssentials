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
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ClearInventory implements SpongeCommand {

    @Override
    public String getCommandUsage() {
        return "/clearinv [player]";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("clearinventory", "clearinv", "cinv");
    }

    @Override
    public SpongeCommandResult executeCommand(CommandSource src, String[] args) {
        if (args.length == 0) {
            if (src instanceof ConsoleSource) {
                Methods.sendPlayerMessage(src, Text.of("The console can't clear it's own inventory"));
                return SpongeCommandResult.SUCCESS;
            }
            if (!Methods.hasPermission(src, "JCMDEss.commands.clearinventory.self")) {
                return SpongeCommandResult.NO_PERMISSION;
            }
            ((Player)src).getInventory().clear();
            Methods.sendPlayerMessage(src, Text.of("You cleared your inventory."));
        } else if (args.length == 1) {
            if (!Methods.hasPermission(src, "JCMDEss.commands.clearinventory.others")) {
                return SpongeCommandResult.NO_PERMISSION;
            }
            Player player = Sponge.getServer().getPlayer(args[0]).orElse(null);
            if (player != null) {
                player.getInventory().clear();

                Methods.sendPlayerMessage(src, Text.builder().append(Text.of("You cleared the inventory of ")).append(Text.of(TextColors.RED, player.getName())).build());
                Methods.sendPlayerMessage(player, Text.of("Your inventory was cleared."));
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
        return Optional.of(Text.of("Clear someone or self inventory"));
    }

}
