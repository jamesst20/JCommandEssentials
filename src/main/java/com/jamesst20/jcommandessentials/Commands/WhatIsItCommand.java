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

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class WhatIsItCommand implements CommandCallable {
    @Inject
    private Logger logger;

    @Override
    public CommandResult process(CommandSource commandSource, String s) throws CommandException {
        commandSource.sendMessage(Text.of("WHAT IS IT :) !"));
        return CommandResult.success();
    }

    @Override
    public List<String> getSuggestions(CommandSource commandSource, String s, @Nullable Location<World> location) throws CommandException {
        return Collections.emptyList();
    }

    @Override
    public boolean testPermission(CommandSource commandSource) {
        return true;
    }

    @Override
    public Optional<Text> getShortDescription(CommandSource commandSource) {
        return Optional.of(Text.of("Describe item you are holding"));
    }

    @Override
    public Optional<Text> getHelp(CommandSource commandSource) {
        return Optional.of(Text.of("Describe item you are holding"));
    }

    @Override
    public Text getUsage(CommandSource commandSource) {
        return Text.of("");
    }
}
