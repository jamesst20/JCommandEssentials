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
package com.jamesst20.jcommandessentials.utils;

import com.flowpowered.math.vector.Vector3d;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Stack;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;


public class TpManager {
     
    private class TpRequest{
        private final Player source;
        private final Player target;
        private final Timer timer;
        private final boolean sourceToTarget;
        
        public TpRequest(Player source, Player target, boolean sourceToTarget){
            this.source = source;
            this.target = target; 
            this.sourceToTarget = sourceToTarget;
            this.timer = new Timer();
        }
        
        public void start(){
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                  TpManager.expire(TpRequest.this);
                }
            }, 30000);
        }
        
        public Player getTarget(){
            return this.target;
        }
        
        public Player getSource(){
            return this.source;
        }
        
        public boolean isSourceToTarget(){
            return sourceToTarget;
        }
        
        public void execute(){
            if(sourceToTarget){
                TpManager.teleport(source, target.getLocation().getPosition());                
            
            } else {
                TpManager.teleport(target, source.getLocation().getPosition());      
            }
            cancel();
        }
        
        public void cancel(){
            this.timer.cancel();
        }
    }
    
    private static final HashSet<TpRequest> REQUESTS = new HashSet<>();
    
    private static HashMap<String, Stack<Location>> playersPastLocations = new HashMap<String, Stack<Location>>();
    
    public static void request(Player source, Player target, boolean isSourceToTarget){   
        if(source == null || target == null) return;
        
        TpRequest request = new TpManager().new TpRequest(source, target, isSourceToTarget);
        request.start();
        REQUESTS.add(request);
    }
    
    public static boolean accept(Player target){
        TpRequest request = getRequestFromTarget(target);
        
        if(request == null) return false;
        
        request.execute();    
        Player source = request.getSource();
        Methods.sendPlayerMessage(target, StyledText.parseString("You accepted the request of &a" + source.getName() + "&f."));    
        
        if(request.isSourceToTarget()){
            Methods.sendPlayerMessage(source, StyledText.parseString("You have been teleported to &a" + target.getName() + "&f."));            
        
        } else {
            Methods.sendPlayerMessage(target, StyledText.parseString("You have been teleported to &a" + source.getName() + "&f."));             
        }
        
        REQUESTS.remove(request);  
        
        return true;
    }
    
    public static boolean deny(Player target){
        TpRequest request = getRequestFromTarget(target);
        
        if(request == null) return false;
        
        request.cancel(); 
        REQUESTS.remove(request);
        Methods.sendPlayerMessage(target, Text.of(TextColors.RED, "Request of &a" + request.getSource().getName() + "&f denied."));
        Methods.sendPlayerMessage(request.getSource(), Text.of(TextColors.RED, "Your request has been denied."));
        
        return true;
    }
    
    public static boolean teleport(Player player, Vector3d position){        
        if(player == null || position == null) return false;
                
        if(!playersPastLocations.containsKey(player.getName())){
            playersPastLocations.put(player.getName(), new Stack<>());        
        }
        playersPastLocations.get(player.getName()).push(player.getLocation());
        
        player.setLocation(new Location<>(player.getWorld(), position));
        
        return true;
    }
    
    public static boolean teleportBack(Player player){        
        if(player != null){
            if(playersPastLocations.containsKey(player.getName())){                
                Stack<Location> pastLocations = playersPastLocations.get(player.getName());
                
                if(!pastLocations.empty()) {
                    player.setLocation(pastLocations.pop());
                    
                    return true;
                }
            }
        }
        
        return false;
    }
    
    private static void expire(TpRequest request){        
        REQUESTS.remove(request);
        Methods.sendPlayerMessage(request.getSource(), Text.of(TextColors.RED, "Your request expired."));
    }
    
    private static TpRequest getRequestFromTarget(Player target){        
        if(target != null){
            for(TpRequest request : REQUESTS){
                if(request.getTarget().getName().equals(target.getName())){                
                    return request;
                }
            }                
        }            
        return null;
    }
    
}
