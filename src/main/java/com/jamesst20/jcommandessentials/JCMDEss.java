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

import com.jamesst20.jcommandessentials.commands.WhatIsItCommand;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;

@Plugin(id = "jcommandesstials", name = "JCommandEssentials", version = "1.0", description = "Description")
public class JCMDEss {

    @Inject
    private Game game;

    @Inject
    private Logger logger;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        registerAllCommands();
        logger.info("JCommandEssentials initialized");
    }

    @Listener
    public void onServerStop(GameStoppingServerEvent event) {
        logger.info("JCommandEssentials stopped");
    }

    private void registerAllCommands() {
        game.getCommandManager().register(this, new WhatIsItCommand(), "whatisit", "wii");
    }
}
