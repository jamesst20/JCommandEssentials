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

package com.jamesst20.jcommandessentials.listerners;

import com.flowpowered.math.vector.Vector3i;
import com.jamesst20.jcommandessentials.JCMDEss;
import com.jamesst20.jcommandessentials.commands.WaterWalkCommand;
import java.util.ArrayList;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.EventListener;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.world.World;

public class PlayerMovementListener implements EventListener<MoveEntityEvent>{
    
    @Override
    public void handle(MoveEntityEvent event) throws Exception {
        Entity entity = event.getTargetEntity();
        
        if(entity instanceof Player){
            Player player = (Player)entity;
            player.getLocation().getBlockPosition().
            if (WaterWalkCommand.WaterWalkers.containsKey(player.getName())) {
                manageWaterWalk(player);
            }
        }
    }
    
    private void manageWaterWalk(Player player){  
        String playerPosition = player.getLocation().getBlockPosition().toString();
        if(!playerPosition.equals(WaterWalkCommand.WaterWalkers.get(player.getName()).LastKnownLocation)){
            WaterWalkCommand.WaterWalkers.get(player.getName()).LastKnownLocation = playerPosition; 
            
            World world = player.getWorld();
            PluginContainer pc = Sponge.getPluginManager().fromInstance(JCMDEss.plugin).orElse(null);
            Cause cause = Cause.of(NamedCause.of("PluginContainer", pc));
            
            ArrayList<Vector3i> newSurrounding = getBlocksLocationAroundPlayer(player);
            ArrayList<Vector3i> currentSurrounding = WaterWalkCommand.WaterWalkers.get(player.getName()).Surrounding;
            ArrayList<Vector3i> blocksToMelt = new ArrayList<>();
            
            for (Vector3i pos : currentSurrounding) {
                if (!newSurrounding.contains(pos)) {
                    world.setBlockType(pos, BlockTypes.WATER, cause);
                    blocksToMelt.add(pos);
                    //Restore from ice to old block
                }
            }
   
            for (Vector3i pos : newSurrounding) {
                BlockType type = world.getBlock(pos).getType();
                if (type == BlockTypes.WATER || type == BlockTypes.FLOWING_WATER){
                    if (!currentSurrounding.contains(pos)) {
                        WaterWalkCommand.WaterWalkers.get(player.getName()).Surrounding.add(pos);  //Backup Block
                        world.setBlockType(pos, BlockTypes.ICE, cause);  //Set block to ice
                    }
                }                
            }
            
            for(int i = 0; i < blocksToMelt.size(); i++){
                WaterWalkCommand.WaterWalkers.get(player.getName()).Surrounding.remove(blocksToMelt.get(i));
            }
        }
         
    }

    private ArrayList<Vector3i> getBlocksLocationAroundPlayer(Player player) {
        ArrayList<Vector3i> blocksAroundLocation = new ArrayList<>();
        Vector3i base = player.getLocation().getBlockPosition().toInt();
        
        for(int x = -1; x < 2; x++){
            for(int z = -1; z < 2; z++){
                blocksAroundLocation.add(base.add(x, -1, z).toInt());
            }
        }
        
        return blocksAroundLocation;
    } 
}
