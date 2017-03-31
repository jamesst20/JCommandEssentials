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
import org.spongepowered.api.world.weather.Weather;
import org.spongepowered.api.world.weather.Weathers;


public class WeatherCommand implements SpongeCommand {


    private Weather weather;

    public WeatherCommand() {
        this.weather = null;
    }

    public WeatherCommand(Weather weather) {
        this.weather = weather;
    }

    @Override
    public String getCommandUsage() {
        if(this.weather == null) {
            return "/weather <sun,rain,storm,thunderstorm> [world]";
        } else if (this.weather == Weathers.CLEAR) {
            return "/sun [world]";
        } else if (this.weather == Weathers.RAIN) {
            return "/rain [world]";
        } else if (this.weather == Weathers.THUNDER_STORM) {
            return "/thunderstorm [world]";
        }
        return "Internal error";
    }

    @Override
    public List<String> getAliases() {
        if(this.weather == null) {
            return Arrays.asList("weather");
        } else if (this.weather == Weathers.CLEAR) {
            return Arrays.asList("sun");
        } else if (this.weather == Weathers.RAIN) {
            return Arrays.asList("rain", "storm");
        } else if (this.weather == Weathers.THUNDER_STORM) {
            return Arrays.asList("thunderstorm", "thunder");
        }
        return Arrays.asList("Internal error");
    }

    @Override
    public SpongeCommandResult executeCommand(CommandSource src, String[] args) {
        if (!Methods.hasPermission(src, "JCMDEss.commands.weather")) {
            return SpongeCommandResult.NO_PERMISSION;
        }

        if (args.length > 2 || (weather != null && args.length > 1) || (weather == null && args.length == 0)) return SpongeCommandResult.INVALID_SYNTHAX;

        if (src instanceof ConsoleSource && (args.length == 0 || (weather == null && args.length == 1))) {
            Methods.sendPlayerMessage(src, Text.of(TextColors.RED, "The console must specify a world."));
            return SpongeCommandResult.SUCCESS;
        }

        World world;
        if (args.length == 2) {
            world = Sponge.getServer().getWorld(args[1]).orElse(null);
        } else if (weather != null && args.length == 1) {
            world = Sponge.getServer().getWorld(args[0]).orElse(null);
        } else {
            world = ((Player) src).getWorld();
        }

        if (world != null) {
            Weather weatherToSet = weather;
            if (weatherToSet == null) {
                if (args[0].equalsIgnoreCase("sun")) {
                    weatherToSet = Weathers.CLEAR;
                } else if (args[0].equalsIgnoreCase("rain") || args[0].equalsIgnoreCase("storm")) {
                    weatherToSet = Weathers.RAIN;
                } else if (args[0].equalsIgnoreCase("thunderstorm")) {
                    weatherToSet = Weathers.THUNDER_STORM;
                }
            }

            if (weatherToSet != null) {
                world.setWeather(weatherToSet);
                Methods.sendPlayerMessage(src, Text.builder().append(Text.of("The weather has been changed to ")).append(Text.of(TextColors.RED, weatherToSet.getName())).build());
            } else {
                Methods.sendPlayerMessage(src, Text.of(TextColors.RED, "Unknown weather \"" + args[0] + "\"."));
            }
        } else {
            Methods.sendPlayerMessage(src, Text.of(TextColors.RED, "Unknown world \"" + args[args.length - 1] + "\"."));
        }
        return SpongeCommandResult.SUCCESS;
    }

    @Override
    public Optional<Text> getShortDescription(CommandSource source) {
        return Optional.of(Text.of("Set the weather of the current or other worlds"));
    }
}
