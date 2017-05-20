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
import com.jamesst20.jcommandessentials.utils.TpManager;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class TpResponseCommand implements SpongeCommand{

    private final boolean accept;
    
    public TpResponseCommand(boolean accept){
        this.accept = accept;
    }
    
    @Override
    public String getCommandUsage() {
        return (accept) ? "/tpaccept" : "/tpdeny";
    }

    @Override
    public List<String> getAliases() {        
        if(accept){
            return Arrays.asList("tpaccept", "tpaaccept");
        
        } else {
            return Arrays.asList("tpdeny", "tpadeny");            
        }        
    }

    @Override
    public SpongeCommandResult executeCommand(CommandSource src, String[] args) {
        
        if(!doesSourceHavePermission(src)) return SpongeCommandResult.NO_PERMISSION;
        
        if(args.length > 0) return SpongeCommandResult.INVALID_SYNTHAX;
        
        if(src instanceof ConsoleSource){
            Methods.sendPlayerMessage(src, Text.of(TextColors.RED, "Console can't accept or deny teleportation requests."));
        }
        
        Player player = (Player)src;        
        boolean result;
        
        if(accept){
            result = TpManager.accept(player);
        } else {
            result = TpManager.deny(player);
        }
        
        if(!result){
            Methods.sendPlayerMessage(src, Text.of(TextColors.RED, "No request to accept or deny. They may be expired."));
        }
        
        return SpongeCommandResult.SUCCESS;
    }

    @Override
    public Optional<Text> getShortDescription(CommandSource source) {
        String description = (accept) ? "Accept a teleport request" : "Deny a teleport request";
        
        return Optional.of(Text.of(description));
    }
    
    private boolean doesSourceHavePermission(CommandSource src){
        return (accept && Methods.hasPermission(src, "JCMDEss.commands.tpaccept")) ||
               (!accept && Methods.hasPermission(src, "JCMDEss.commands.tpdeny"));
    }    
}
