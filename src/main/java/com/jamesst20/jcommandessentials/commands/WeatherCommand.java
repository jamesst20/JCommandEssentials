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
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.weather.Weathers;

/**
 *
 * @author charl
 */
public class WeatherCommand implements SpongeCommand{
    
    public enum WeatherAlias{
        SUN,
        RAIN,
        STORM,
        THUNDER,
        NONE
    }
    
    private WeatherAlias weather;
    
    public WeatherCommand(){
        this.weather = WeatherAlias.NONE;
    }
    
    public WeatherCommand(WeatherAlias weather){
        this.weather = weather;
    }
    
    @Override
    public String getCommandUsage() {
        switch (this.weather){
            case SUN:
                return "/sun [world]"; 
            case RAIN:
                return "/rain [world]"; 
            case STORM:
                return "/storm [world]"; 
            case THUNDER:
                return "/thunder [world]"; 
            default:
                return "/weather <sun,rain,storm,thunder> [world]";                
        }
    }

    @Override
    public List<String> getAliases() {
        switch (this.weather){
            case SUN:
                return Arrays.asList("sun"); 
            case RAIN:
                return Arrays.asList("rain"); 
            case STORM:
                return Arrays.asList("storm"); 
            case THUNDER:
                return Arrays.asList("thunder"); 
            default:
                return Arrays.asList("weather");                
        }
    }

    @Override
    public SpongeCommandResult executeCommand(CommandSource src, String[] args) {
        if (!Methods.hasPermission(src, "JCMDEss.commands.weather")) {
            return SpongeCommandResult.NO_PERMISSION;
        }
        String weatherType = "";
        World world = null;
        Player player = null;
        
        switch (args.length) {
            case 0:
                if(this.weather == WeatherAlias.NONE){
                    return SpongeCommandResult.INVALID_SYNTHAX;
                } else if (src instanceof ConsoleSource){
                    Methods.sendPlayerMessage(src, Text.of(TextColors.RED, "The console must specify a world."));
                    return SpongeCommandResult.SUCCESS;                    
                } else {
                    player = (Player)src;
                    world = player.getWorld();
                    return setWeather(getAliases().get(0), world, src, player.getName(), true);
                }
                
            case 1:
                if(this.weather == WeatherAlias.NONE){
                    weatherType = args[0].toLowerCase();
                    if (src instanceof ConsoleSource) {
                        Methods.sendPlayerMessage(src, Text.of(TextColors.RED, "The console must specify a world."));
                        return SpongeCommandResult.SUCCESS;
                    }
                    player = (Player)src;
                    world = player.getWorld();

                    return setWeather(weatherType, world, src, player.getName(), true);                    
                } else {
                    weatherType = getAliases().get(0);
                    world = Sponge.getServer().getWorld(args[0]).orElse(null);
                    
                    return setWorldWeather(world, src, args[0], weatherType);
                }
                
            case 2:
                if(this.weather == WeatherAlias.NONE){
                    weatherType = args[0].toLowerCase();
                    world = Sponge.getServer().getWorld(args[1]).orElse(null);                    
                    return setWorldWeather(world, src, args[1], weatherType);  
                    
                } else {
                    return SpongeCommandResult.INVALID_SYNTHAX;
                }   
            default:
                return SpongeCommandResult.INVALID_SYNTHAX;
        }
    }

    @Override
    public Optional<Text> getShortDescription(CommandSource source) {  
          return Optional.of(Text.of("Set the weather of the current or other worlds"));
    }   
    private SpongeCommandResult setWorldWeather(World world, CommandSource src, String worldName, String weatherType){
        if(world == null){
            Methods.sendPlayerMessage(src, Text.of(TextColors.RED, "Unknown world \"" + worldName + "\"."));
            return SpongeCommandResult.SUCCESS;
        } else if (src instanceof ConsoleSource) {
            return setWeather(weatherType, world, src, "Console", false);
        } else {
            return setWeather(weatherType, world, src, ((Player)src).getName(), false);                        
        }      
    }
    
    private SpongeCommandResult setWeather(String weatherType, World world, CommandSource src, String sourceName, boolean currentWorld){
        String worldName = (currentWorld) ? "Current world " : "\"" + world.getName() + "\" world ";
        
        switch (weatherType){
            case "sun":
                world.setWeather(Weathers.CLEAR);
                Methods.sendPlayerMessage(src, Text.of(worldName + "weather set to sunny by " + sourceName + "!"));
                return SpongeCommandResult.SUCCESS;

            case "rain":
            case "storm":
                world.setWeather(Weathers.RAIN);
                Methods.sendPlayerMessage(src, Text.of(worldName + "weather set to rainning by " + sourceName + "!"));
                return SpongeCommandResult.SUCCESS;

            case "thunder":
                world.setWeather(Weathers.THUNDER_STORM);
                Methods.sendPlayerMessage(src, Text.of("The thunder is striking through the rain thanks to " + sourceName + " in the " + worldName + "!"));
                return SpongeCommandResult.SUCCESS;

            default:
                Methods.sendPlayerMessage(src, Text.of(TextColors.RED, "Unknown weather \"" + weatherType + "\"!"));
                return SpongeCommandResult.INVALID_SYNTHAX; 
        }           
    }
}
