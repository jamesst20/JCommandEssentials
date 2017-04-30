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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.math.NumberUtils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.entity.MovementSpeedData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class WalkSpeedCommand implements SpongeCommand {
    
    private final double BASE_WALK_SPEED = 0.05;
    
    @Override
    public String getCommandUsage() {
        return "/walkspeed get [player] OR /walkspeed set <speed> [player]";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("walkspeed", "wspeed", "speedwalk", "walk", "run");
    }

    @Override
    public SpongeCommandResult executeCommand(CommandSource src, String[] args) {
        
        if (isSynthaxInvalid(args)) return SpongeCommandResult.INVALID_SYNTHAX;
        
        if(!doesSourceHavePermission(src, args)) return SpongeCommandResult.NO_PERMISSION;        
    
        if (src instanceof ConsoleSource && args.length == 1 && args[0].equalsIgnoreCase("get")) {
            Methods.sendPlayerMessage(src, Text.of(TextColors.RED, "The console doesn't have a walk speed. Specify a player."));
            
        } else if (src instanceof ConsoleSource && args.length == 2 && args[0].equalsIgnoreCase("set")){
            Methods.sendPlayerMessage(src, Text.of(TextColors.RED, "Console can only change walk speed of other players."));    
            
        } else if (args[0].equalsIgnoreCase("get")){
            Player player = (args.length == 1) ? (Player)src : Sponge.getServer().getPlayer(args[1]).orElse(null);                  
            
            if(player == null){
                Methods.sendPlayerMessage(src, "<rdUnknown player <dr" + args[1] + "/>.");                 
            } else {
                Methods.sendPlayerMessage(src, displayPlayerSpeed(player, args));
            } 
            
        } else if (args[0].equalsIgnoreCase("set")) {
            Player player = (args.length == 2) ? (Player)src : Sponge.getServer().getPlayer(args[2]).orElse(null);                  
            
            if(player == null){
                Methods.sendPlayerMessage(src, "<rUnknown player <dr" + args[2] + "/>."); 
                                          
            } else {
                try{                
                    Methods.sendPlayerMessage(src, setPlayerSpeed(player, args));  
                    
                    if(src instanceof ConsoleSource){
                        String speed = String.format("%1$.2f", (Double.parseDouble(args[1])));
                        Methods.sendPlayerMessage(player, "Your walk speed has been set to <gn" + speed + "/>.");  
                    }
                    
                } catch (NumberFormatException ex) {
                    Methods.sendPlayerMessage(src, Text.of(TextColors.RED, "Invalid number format for speed."));
                }                
            }                       
        } 
        
        return SpongeCommandResult.SUCCESS;
    }

    @Override
    public Optional<Text> getShortDescription(CommandSource source) {
        return Optional.of(Text.of("Manage your or a player walking speed"));
    }
    
    private String displayPlayerSpeed(Player player, String[] args){
        MovementSpeedData speedData = player.getOrCreate(MovementSpeedData.class).orElse(null);
        
        if(speedData == null) return "<rdUnable to access player data.";
        
        String speed = String.format("%1$.2f", (speedData.getValue(Keys.WALKING_SPEED).get().get() * 10));
        String introText = (args.length == 1) ? "Your walk speed is ": "The walk speed of <g>" + player.getName() + "</> is ";
        
        return introText + "<gn" + speed + "/>.";
    }
    
    private Text setPlayerSpeed(Player player, String[] args) throws NumberFormatException{
        MovementSpeedData speedData = player.getOrCreate(MovementSpeedData.class).orElse(null);
        
        if(speedData == null) return Text.of(TextColors.RED, "Unable to access player data.");       
        
        Double speed = Double.parseDouble(args[1]) / 10;
        
        if(speed < 0) return Text.of(TextColors.RED, "The speed value must be positive.");

        speedData.set(Keys.WALKING_SPEED, speed);
        player.offer(speedData);
        
        String introText = (args.length == 2) ? "Your walk speed is now ": "The walk speed of <gn" + player.getName() + "/> is now ";
        String speedValue = String.format("%1$.2f", (speed * 10));
        
        return Text.builder().append(Text.of(introText), Text.of(TextColors.RED, speedValue), Text.of(".")).toText(); 
    }
    
    private boolean isSynthaxInvalid(String[] args){
        return (args.length == 0) || 
               (args.length == 1 && !args[0].equalsIgnoreCase("get")) ||
               (args.length == 1 && args[0].equalsIgnoreCase("set")) ||
               (args.length > 1 && args[0].equalsIgnoreCase("set") && !NumberUtils.isNumber(args[1])) ||
               (args.length > 2 && args[0].equalsIgnoreCase("get")) || 
               (args.length > 3 && args[0].equalsIgnoreCase("set"));
    }
    
    private boolean doesSourceHavePermission(CommandSource src, String[] args){
        return (args.length == 1 && args[0].equalsIgnoreCase("get") && Methods.hasPermission(src, "JCMDEss.commands.walkspeed.get.self")) ||
               (args.length == 2 && args[0].equalsIgnoreCase("get") && Methods.hasPermission(src, "JCMDEss.commands.walkspeed.get.others")) ||
               (args.length == 2 && args[0].equalsIgnoreCase("set") && Methods.hasPermission(src, "JCMDEss.commands.walkspeed.set.self")) ||
               (args.length == 3 && args[0].equalsIgnoreCase("set") && Methods.hasPermission(src, "JCMDEss.commands.walkspeed.set.others"));
    }    
}
