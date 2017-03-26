package com.jamesst20.jcommandessentials.interfaces;/* 
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

import com.jamesst20.jcommandessentials.utils.Methods;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface SpongeCommand extends CommandCallable {

    enum SpongeCommandResult {
        SUCCESS,
        INVALID_SYNTHAX,
        NO_PERMISSION
    }

    String getCommandUsage();

    SpongeCommandResult executeCommand(CommandSource src, String[] args);

    @Override
    default CommandResult process(CommandSource src, String arguments) throws CommandException {
        SpongeCommandResult result = executeCommand(src, Methods.strSplit(arguments, " "));

        if(result == SpongeCommandResult.SUCCESS) return CommandResult.success();

        if (result == SpongeCommandResult.INVALID_SYNTHAX) {
            Methods.sendPlayerMessage(src, Text.builder().append(Text.of(TextColors.RED, "Invalid usage : ")).append(Text.of(getCommandUsage())).build());
        }

        if (result == SpongeCommandResult.NO_PERMISSION) {
            Methods.sendPlayerMessage(src, Text.of(TextColors.RED, "You do not have permission to run this command"));
        }
        return CommandResult.empty();
    }

    @Override
    default List<String> getSuggestions(CommandSource source, String arguments, @Nullable Location<World> location) throws CommandException {
        return Collections.emptyList();
    }

    @Override
    default boolean testPermission(CommandSource source) {
        return true;
    }

    @Override
    default Optional<Text> getHelp(CommandSource source) {
        return getShortDescription(source);
    }

    @Override
    default Text getUsage(CommandSource src){
        return Text.of("");
    }
}
