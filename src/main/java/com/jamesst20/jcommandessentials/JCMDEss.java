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

package com.jamesst20.jcommandessentials;

import com.google.inject.Inject;

import com.jamesst20.jcommandessentials.commands.ArmorCommand;
import com.jamesst20.jcommandessentials.commands.ClearInventory;
import com.jamesst20.jcommandessentials.commands.ColorsCommand;
import com.jamesst20.jcommandessentials.commands.TimeCommand;
import com.jamesst20.jcommandessentials.commands.TimeCommand.Time;
import com.jamesst20.jcommandessentials.commands.TpAllCommand;
import com.jamesst20.jcommandessentials.commands.TpBackCommand;
import com.jamesst20.jcommandessentials.commands.TpHereCommand;
import com.jamesst20.jcommandessentials.commands.TpResponseCommand;
import com.jamesst20.jcommandessentials.commands.TpRequestCommand;
import com.jamesst20.jcommandessentials.commands.TpThereCommand;
import com.jamesst20.jcommandessentials.commands.TpcCommand;
import com.jamesst20.jcommandessentials.commands.WalkSpeedCommand;
import com.jamesst20.jcommandessentials.commands.WaterWalkCommand;
import com.jamesst20.jcommandessentials.commands.WeatherCommand;
import com.jamesst20.jcommandessentials.commands.WhatIsItCommand;
import com.jamesst20.jcommandessentials.commands.WorkbenchCommand;
import com.jamesst20.jcommandessentials.listerners.PlayerListener;
import com.jamesst20.jcommandessentials.utils.Methods;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.world.weather.Weathers;

import java.io.IOException;
import java.nio.file.Path;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.EventManager;

@Plugin(id = "jcommandessentials", name = "JCommandEssentials", version = "1.0", description = "Description")
public class JCMDEss {

    @Inject
    private Game game;

    @Inject
    private Logger logger;

    public static JCMDEss plugin;

    @Inject
    @DefaultConfig(sharedRoot = false)
    private Path defaultConfig;

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path configDir;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        plugin = this;
        
        registerAllListeners();
        registerAllCommands();
        
        logger.info("JCommandEssentials initialized");
    }

    @Listener
    public void onServerStop(GameStoppingServerEvent event) {
        logger.info("JCommandEssentials stopped");
    }

    private void registerAllCommands() {
        try {
            HoconConfigurationLoader loader = HoconConfigurationLoader.builder().setPath(defaultConfig).build();
            ConfigurationNode rootNode = loader.load().getNode("commands");

            Methods.regC(this, game, new ArmorCommand(), rootNode);
            Methods.regC(this, game, new ClearInventory(), rootNode);
            Methods.regC(this, game, new ColorsCommand(), rootNode);
            
            Methods.regC(this, game, new TimeCommand(), rootNode);
            Methods.regC(this, game, new TimeCommand(Time.DAY), rootNode);
            Methods.regC(this, game, new TimeCommand(Time.NIGHT), rootNode);
            
            Methods.regC(this, game, new TpAllCommand(), rootNode);
            Methods.regC(this, game, new TpBackCommand(), rootNode);
            Methods.regC(this, game, new TpcCommand(), rootNode);
            Methods.regC(this, game, new TpHereCommand(), rootNode);
            Methods.regC(this, game, new TpRequestCommand(true), rootNode);
            Methods.regC(this, game, new TpRequestCommand(false), rootNode);
            Methods.regC(this, game, new TpResponseCommand(true), rootNode);
            Methods.regC(this, game, new TpResponseCommand(false), rootNode);
            Methods.regC(this, game, new TpThereCommand(), rootNode);
            
            Methods.regC(this, game, new WalkSpeedCommand(), rootNode);
            Methods.regC(this, game, new WaterWalkCommand(), rootNode);

            Methods.regC(this, game, new WeatherCommand(), rootNode);
            Methods.regC(this, game, new WeatherCommand(Weathers.CLEAR), rootNode);
            Methods.regC(this, game, new WeatherCommand(Weathers.RAIN), rootNode);
            Methods.regC(this, game, new WeatherCommand(Weathers.THUNDER_STORM), rootNode);

            Methods.regC(this, game, new WhatIsItCommand(), rootNode);
            Methods.regC(this, game, new WorkbenchCommand(), rootNode);

            loader.save(rootNode.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void registerAllListeners(){
        EventManager manager = Sponge.getEventManager();

        manager.registerListeners(plugin, new PlayerListener());
    }
}
