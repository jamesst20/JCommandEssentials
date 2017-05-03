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

import com.flowpowered.math.vector.Vector3i;
import com.jamesst20.jcommandessentials.JCMDEss;
import com.jamesst20.jcommandessentials.interfaces.SpongeCommand;
import com.jamesst20.jcommandessentials.utils.Methods;

import java.util.*;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.Sponge;

public class WaterWalkCommand implements SpongeCommand {
    private static final HashMap<String, HashMap<Vector3i, BlockState>> waterWalkers = new HashMap<>();

    @Override
    public String getCommandUsage() {
        return "/waterwalk [player]";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("waterwalk", "wwalk");
    }

    @Override
    public SpongeCommandResult executeCommand(CommandSource src, String[] args) {
        if (src instanceof ConsoleSource && args.length == 0) {
            Methods.sendPlayerMessage(src, Text.of(TextColors.RED, "The console can't walk on water."));
        } else if (args.length == 0) {
            if (!Methods.hasPermission(src, "JCMDEss.commands.waterwalk.self")) {
                return SpongeCommandResult.NO_PERMISSION;
            }
            Player player = (Player) src;
            if (waterWalkers.containsKey(player.getName())) {
                restoreBlocks(player, null);
                waterWalkers.remove(player.getName());
                Methods.sendPlayerMessage(player, Text.of(TextColors.RED, "You can no longer walk on water."));
            } else {
                waterWalkers.put(player.getName(), new HashMap<>());
                Methods.sendPlayerMessage(player, Text.of(TextColors.RED, "You can now walk on water."));
            }
        } else if (args.length == 1) {
            if (!Methods.hasPermission(src, "JCMDEss.commands.waterwalk.others")) {
                return SpongeCommandResult.NO_PERMISSION;
            }
            Player player = Sponge.getServer().getPlayer(args[0]).orElse(null);
            if (player == null) {
                Methods.sendPlayerNotFound(src, args[0]);
            } else if (waterWalkers.containsKey(player.getName())) {
                restoreBlocks(player, null);
                waterWalkers.remove(player.getName());
                Methods.sendPlayerMessage(player, Text.of(TextColors.RED, "You can no longer walk on water."));
                Methods.sendPlayerMessage(src, Text.builder().append(Text.of("The player ")).append(Text.of(TextColors.RED, player.getName())).append(Text.of(" can no longer walk on water.")).build());
            } else {
                waterWalkers.put(player.getName(), new HashMap<>());
                Methods.sendPlayerMessage(player, Text.of(TextColors.RED, "You can now walk on water."));
                Methods.sendPlayerMessage(src, Text.builder().append(Text.of("The player ")).append(Text.of(TextColors.RED, player.getName())).append(Text.of(" can now walk on water.")).build());
            }
        } else {
            return SpongeCommandResult.INVALID_SYNTHAX;
        }
        return SpongeCommandResult.SUCCESS;
    }

    @Override
    public Optional<Text> getShortDescription(CommandSource source) {
        return Optional.of(Text.of("Allow yourself or a player to walk on water"));
    }

    public static void processWaterWalk(Player player) {
        if (!waterWalkers.containsKey(player.getName())) return;

        Vector3i playerPosition = player.getLocation().getBlockPosition().add(0, -1, 0);

        List<Vector3i> blockLocations = new ArrayList<>();
        for (int x = -1; x < 2; x++) {
            for (int z = -1; z < 2; z++) {
                Vector3i blockLocation = playerPosition.add(x, 0, z);
                if (player.getWorld().getBlock(blockLocation).getType() == BlockTypes.WATER || player.getWorld().getBlock(blockLocation).getType() == BlockTypes.FLOWING_WATER || player.getWorld().getBlock(blockLocation).getType() == BlockTypes.ICE) {
                    blockLocations.add(blockLocation);
                }
            }
        }

        restoreBlocks(player, blockLocations);

        for (Vector3i blockLocation : blockLocations) {
            if (!waterWalkers.get(player.getName()).containsKey(blockLocation)) {
                waterWalkers.get(player.getName()).put(blockLocation, player.getWorld().getBlock(blockLocation));
                player.getWorld().getLocation(blockLocation).setBlockType(BlockTypes.ICE, Cause.source(Sponge.getPluginManager().fromInstance(JCMDEss.plugin).get()).build());
            }
        }
    }

    private static void restoreBlocks(Player player, List<Vector3i> exceptions) {
        Iterator<Map.Entry<Vector3i, BlockState>> it = waterWalkers.get(player.getName()).entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Vector3i, BlockState> pair = it.next();
            if (exceptions == null || !exceptions.contains(pair.getKey())) {
                player.getWorld().getLocation(pair.getKey()).setBlock(pair.getValue(), Cause.source(Sponge.getPluginManager().fromInstance(JCMDEss.plugin).get()).build());
                it.remove();
            }
        }
    }
}
