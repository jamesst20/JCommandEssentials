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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.plugin.PluginContainer;

/**
 *
 * @author charl
 */
public class WaterWalkCommand implements SpongeCommand {

    public class WaterWalker{
        public ArrayList<Vector3i> Surrounding;
        public String LastKnownLocation;
        
        public WaterWalker(String lastKnownLocation){
            Surrounding = new ArrayList<>();
            LastKnownLocation = lastKnownLocation;
        }
    }
    
    public static HashMap<String, WaterWalker> WaterWalkers = new HashMap<String, WaterWalker>();
    
    public static void restoreBlocks(Player player) {
        for (Vector3i loc : WaterWalkers.get(player.getName()).Surrounding) {
            PluginContainer pc = Sponge.getPluginManager().fromInstance(JCMDEss.plugin).orElse(null);
            Cause cause = Cause.of(NamedCause.of("PluginContainer", pc));
            player.getWorld().setBlockType(loc, BlockTypes.WATER, cause);  //Restore old block
        }
    }
    
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
        if (!Methods.hasPermission(src, "JCMDEss.commands.waterwalk.self")) {
            return SpongeCommandResult.NO_PERMISSION;
        }
        
        if (src instanceof ConsoleSource && args.length == 0) {
            Methods.sendPlayerMessage(src, Text.of(TextColors.RED, "The console can't walk on water."));
            
        } else if (args.length == 0) {               
            Player player = (Player) src;
            if (WaterWalkers.containsKey(player.getName())) {
               disableWalk(args, player, src, true);
            } else {
               enableWalk(args, player, src, true);
            }    
            
        } else if (args.length == 1) {
            Player player = Sponge.getServer().getPlayer(args[0]).orElse(null);
            
            if (player == null) {
                Methods.sendPlayerNotFound(src, args[0]);
            } else if (WaterWalkers.containsKey(player.getName())) {
                disableWalk(args, player, src, false);
            } else {
               enableWalk(args, player, src, false);
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
    
    private void enableWalk(String[] args, Player player, CommandSource src, boolean isSelf){
        String lastKnownLocation = player.getLocation().getBlockPosition().toString();
        WaterWalkers.put(player.getName(), new WaterWalker(lastKnownLocation));
        Methods.sendPlayerMessage(player, Text.of(TextColors.RED, "You can now walk on water."));
        if(!isSelf){
            Methods.sendPlayerMessage(src, Text.builder().append(Text.of("The player "))
                .append(Text.of(TextColors.RED, args[0]))
                .append(Text.of(" can now walk on water.")).build());            
        }
    }
    
    private void disableWalk(String[] args, Player player, CommandSource src, boolean isSelf){
        restoreBlocks(player);
        WaterWalkers.remove(player.getName());
        Methods.sendPlayerMessage(player, Text.of(TextColors.RED, "You can no longer walk on water."));
        if(!isSelf){
            Methods.sendPlayerMessage(src, Text.builder().append(Text.of("The player "))
                .append(Text.of(TextColors.RED, args[0]))
                .append(Text.of(" can no longer walk on water.")).build());
        }
    }
    
}
