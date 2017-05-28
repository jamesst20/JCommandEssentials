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
import org.spongepowered.api.world.Location;

public class TpAllCommand implements SpongeCommand{

    @Override
    public String getCommandUsage() {
        return "/tpall";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("tpall");
    }

    @Override
    public SpongeCommandResult executeCommand(CommandSource src, String[] args) {
        
        if(args.length != 0) return SpongeCommandResult.INVALID_SYNTHAX;
        
        if(!Methods.hasPermission(src, "JCMDEss.commands.tpall")) return SpongeCommandResult.INVALID_SYNTHAX;
        
        if(src instanceof ConsoleSource){
            Methods.sendPlayerMessage(src, Text.of(TextColors.RED, "The console can't teleport player to itself."));
        
        } else {  
            Player source = ((Player)src);
            Location location = source.getLocation();
            
            for(Player player : Sponge.getServer().getOnlinePlayers()){
                
                if(player.getName().equals(source.getName())) continue;
                
                Methods.sendPlayerMessage(player, StyledText.parseString("You have been teleported to &a" + source.getName() + "&f."));                 
                TpManager.teleport(player, location);
            }
            
            Methods.sendPlayerMessage(src, Text.of("All players have been teleported to you"));
        }
        
        return SpongeCommandResult.SUCCESS;
    }

    @Override
    public Optional<Text> getShortDescription(CommandSource source) {
        return Optional.of(Text.of("Teleport all players to you"));
    }    
}
