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

import com.jamesst20.jcommandessentials.commands.ArmorCommand;
import com.jamesst20.jcommandessentials.interfaces.SpongeCommand;
import org.spongepowered.api.Game;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.List;

public class Methods {
    public static Text prefix = Text.builder().append(Text.of(TextColors.WHITE, "[")).append(Text.of(TextColors.GREEN, "JCMD")).append(Text.of(TextColors.DARK_GREEN, "Ess")).append(Text.of(TextColors.WHITE, Text.builder("] "))).build();

    public static void sendPlayerMessage(Player player, Text msg) {
        player.sendMessage(Text.builder().append(prefix).append(msg).build());
    }

    public static void sendPlayerMessage(CommandSource src, Text msg) {
        src.sendMessage(Text.builder().append(prefix).append(msg).build());
    }

    public static boolean hasPermission(CommandSource src, String permission) {
        if(src instanceof ConsoleSource) {
            return true;
        }
        return src.hasPermission(permission);
    }

    public static void sendPlayerNotFound(CommandSource src, String playerName) {
        src.sendMessage(Text.builder().append(prefix).append(Text.of("The player ")).append(Text.of(TextColors.RED, playerName)).append(Text.of(" couldn't be found.")).build());
    }

    public static void regC(Game game, SpongeCommand command){
        game.getCommandManager().register(game, command, command.getAliases());
    }

    public static String[] strSplit(String str, String pat) {
        if (str.length() == 0 || str.replaceAll(pat, "").length() == 0) {
            return new String[0];
        } else {
            return str.split(pat);
        }
    }
}
