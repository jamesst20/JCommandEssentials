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
import com.jamesst20.jcommandessentials.utils.TpManager;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class TpHereCommand implements SpongeCommand{

    @Override
    public String getCommandUsage() {
        return "/tphere <player>";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("tphere");
    }

    @Override
    public SpongeCommandResult executeCommand(CommandSource src, String[] args) {        
        if(!Methods.hasPermission(src, "JCMDEss.commands.tphere")) return SpongeCommandResult.NO_PERMISSION;
        
        if(args.length != 1) return SpongeCommandResult.INVALID_SYNTHAX;
        
        if(src instanceof ConsoleSource){
            Methods.sendPlayerMessage(src, Text.of(TextColors.RED, "The console can't teleport players to its position."));
        
        } else {
            Player player = Sponge.getServer().getPlayer(args[0]).orElse(null);     
            
            if(player == null){
                Methods.sendPlayerNotFound(src, args[0]);
            
            } else {
                Player source = ((Player)src);
                TpManager.teleport(player, source.getLocation().getPosition());
                
                Methods.sendPlayerMessage(player, StyledText.parseString("You have been teleported to &a" + source.getName() + "&f."));                                         
                Methods.sendPlayerMessage(src, StyledText.parseString("&a" + player.getName() + "&f has been teleported to you."));                    
            }
        }
        
        return SpongeCommandResult.SUCCESS;
    }

    @Override
    public Optional<Text> getShortDescription(CommandSource source) {
        return Optional.of(Text.of("Teleport a player to your location"));
    }    
}
