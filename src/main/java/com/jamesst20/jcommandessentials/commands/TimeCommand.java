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
import com.jamesst20.jcommandessentials.utils.StyledText;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.storage.WorldProperties;

public class TimeCommand implements SpongeCommand{

    public final class Time {
        public static final int DEFAULT = -1;
        public static final long DAY = 1000;
        public static final long NIGHT = 13000;
    } 
    
    private final long time;

    public TimeCommand() {
        this.time = -1;
    }

    public TimeCommand(long time) {
        this.time = time;
    }
    
    @Override
    public String getCommandUsage() {
        if(time == Time.DAY){
            return "/day [world]";
        } else if (time == Time.NIGHT){
            return "/night [world]";
        } else if (time == Time.DEFAULT){
            return "/time <day|night> [world] OR /time <time> [world]";
        }
        return "Internal error";
    }

    @Override
    public List<String> getAliases() {
        if(time == Time.DAY){
            return Arrays.asList("day");
        } else if (time == Time.NIGHT){
            return Arrays.asList("night");
        } else if (time == Time.DEFAULT){
            return Arrays.asList("time");
        }
        return Arrays.asList("Internal error");
    }

    @Override
    public SpongeCommandResult executeCommand(CommandSource src, String[] args) {
        
        if(isSynthaxInvalid(args)) return SpongeCommandResult.INVALID_SYNTHAX;
        
        if(!Methods.hasPermission(src, "JCMDEss.commands.time")) return SpongeCommandResult.NO_PERMISSION;        
        
        if(src instanceof ConsoleSource && ((args.length != 1 && time != Time.DEFAULT) || (args.length != 2 && time == Time.DEFAULT))){
            Methods.sendPlayerMessage(src, Text.of(TextColors.RED, "The console must specify a world."));
        
        } else {
            String worldName = getWorldName(args, src);              
            World world = Sponge.getServer().getWorld(worldName).orElse(null);
            
            if(world != null){
                String timeText = (time == Time.DAY) 
                        ? "day" : (time == Time.NIGHT) 
                        ? "night" : args[0];                
                
                long timeToSet = (time != Time.DEFAULT) 
                        ? time : (args[0].equals("day")) 
                        ? Time.DAY : Time.NIGHT;   
                
                Text msg = StyledText.parseString("The time was set to &a" + timeText);
                
                if(!timeText.equals("day") && !timeText.equals("night")){
                    try{
                        timeToSet = Long.parseLong(timeText);   
                        world.getProperties().setWorldTime(timeToSet);
                        Methods.sendPlayerMessage(src, msg);                     
                        
                    } catch (NumberFormatException ex){
                        Methods.sendPlayerMessage(src, Text.of(TextColors.RED, "Invalid value for the 'time' parameter."));                        
                    }
                
                } else {
                    world.getProperties().setWorldTime(timeToSet);
                    Methods.sendPlayerMessage(src, msg);                    
                }                
                
            } else {                
                Methods.sendPlayerMessage(src, StyledText.parseString("The wolrd &c" + worldName + "&f could not be found."));
            }
        }        
        
        return SpongeCommandResult.SUCCESS;
    }

    @Override
    public Optional<Text> getShortDescription(CommandSource source) {
        return Optional.of(Text.of("Set the time of a world"));
    }
    
    private boolean isSynthaxInvalid(String[] args){
        return ((time == Time.DAY || time == Time.NIGHT) && args.length > 1) ||
               (time == Time.DEFAULT && (args.length < 1 || args.length > 2));
    }
    
    private String getWorldName(String[] args, CommandSource src){        
        if((args.length == 1 && time == Time.DEFAULT) || (args.length == 0) ) {
            return ((Player)src).getWorld().getName();
            
        } else if (args.length == 1 && time != Time.DEFAULT) {
            return args[0];
            
        } else if (args.length == 2) {
            return args[1];            
        }  
        return "";
    }
}
