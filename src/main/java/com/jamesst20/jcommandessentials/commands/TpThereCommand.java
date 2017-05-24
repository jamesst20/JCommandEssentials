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

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import com.jamesst20.jcommandessentials.JCMDEss;
import com.jamesst20.jcommandessentials.interfaces.SpongeCommand;
import com.jamesst20.jcommandessentials.utils.Methods;
import com.jamesst20.jcommandessentials.utils.StyledText;
import com.jamesst20.jcommandessentials.utils.TpManager;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.world.World;

public class TpThereCommand implements SpongeCommand{

    @Override
    public String getCommandUsage() {
        return "/tpthere [player]";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("tpthere", "put");
    }

    @Override
    public SpongeCommandResult executeCommand(CommandSource src, String[] args) {
        if(!Methods.hasPermission(src, "JCMDEss.commands.tpthere")) return SpongeCommandResult.NO_PERMISSION;
        
        if(args.length > 1) return SpongeCommandResult.INVALID_SYNTHAX;
        
        if(src instanceof ConsoleSource){
            Methods.sendPlayerMessage(src, Text.of(TextColors.RED, "The console can't teleport itself or players to looked position."));
        
        } else {
            Player player = (args.length == 1) ? Sponge.getServer().getPlayer(args[0]).orElse(null) : (Player)src;     
            
            if(player == null){
                Methods.sendPlayerNotFound(src, args[0]);
            
            } else {
                BlockRay ray = BlockRay.from((Player)src).build();
                World world = player.getWorld();
                int range = 1000;
                BlockType block;
                Vector3i location;
                
                do {
                    location = ray.next().getBlockPosition();                    
                    block = world.getBlock(location).getType();
                    range--;
                    
                } while(ray.hasNext() && block == BlockTypes.AIR && range != 0);
                
                if (range == 0) {
                    Methods.sendPlayerMessage(src, Text.of(TextColors.RED, "Block is out of range."));           
                    
                } else {  
                    TpManager.teleport(player, getSafeLocation(location, world).toDouble());
                    String teleportedTo = " have been teleported to &cx: " + location.getX() + " &ay: " + location.getY() + " &9z: " + location.getZ();
                    Methods.sendPlayerMessage(player, StyledText.parseString("You" + teleportedTo));
                    
                    if(args.length == 1){                        
                        Methods.sendPlayerMessage(src, StyledText.parseString("The player &a" + player.getName() + "&f" + teleportedTo));
                    }
                }               
            }
        }
        
        return SpongeCommandResult.SUCCESS;
    }

    @Override
    public Optional<Text> getShortDescription(CommandSource source) {
        return Optional.of(Text.of("Teleport yourself or a player to your target block"));
    }
    
    private Vector3d getSafeLocation(Vector3i location, World world){         
        Cause cause = Cause.source(Sponge.getPluginManager().fromInstance(JCMDEss.plugin).get()).build();
        Vector3d fixedLocation = location.add(0, 1, 0).toDouble().add(0.5, 0, 0.5);
        BlockState block = BlockState.builder().blockType(BlockTypes.AIR).build();
        
        if(location.getY() < 254){
            world.setBlock(location.add(0, 1, 0), block, cause);        
            world.setBlock(location.add(0, 2, 0), block, cause);            
        }      
        
        return fixedLocation;
    }
    
}
