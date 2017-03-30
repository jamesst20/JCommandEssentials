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
import com.jamesst20.jcommandessentials.commands.WeatherCommand;
import com.jamesst20.jcommandessentials.commands.WeatherCommand.WeatherAlias;
import com.jamesst20.jcommandessentials.commands.WhatIsItCommand;
import com.jamesst20.jcommandessentials.commands.WorkbenchCommand;
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

import java.io.IOException;
import java.nio.file.Path;

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
            Methods.regC(this, game, new WeatherCommand(), rootNode);
            Methods.regC(this, game, new WeatherCommand(WeatherAlias.SUN), rootNode);
            Methods.regC(this, game, new WeatherCommand(WeatherAlias.RAIN), rootNode);
            Methods.regC(this, game, new WeatherCommand(WeatherAlias.STORM), rootNode);
            Methods.regC(this, game, new WeatherCommand(WeatherAlias.THUNDER), rootNode);
            Methods.regC(this, game, new WhatIsItCommand(), rootNode);
            Methods.regC(this, game, new WorkbenchCommand(), rootNode);

            loader.save(rootNode.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
