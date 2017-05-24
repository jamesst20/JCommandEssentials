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

public class TpRequestCommand implements SpongeCommand{
    
    private final boolean isHere;
            
    public TpRequestCommand(boolean isHere){
        this.isHere = isHere;
    }
    
    @Override
    public String getCommandUsage() {
        return (isHere) ? "/tpahere <player>" : "/tpa <player>";
    }

    @Override
    public List<String> getAliases() {
        if(isHere){
            return Arrays.asList("tpahere", "tpherea", "atphere");             
        } else {
            return Arrays.asList("tpa", "tpask");            
        }
    }

    @Override
    public SpongeCommandResult executeCommand(CommandSource src, String[] args) {
        
        if(!doesSourceHavePermission(src, args)) return SpongeCommandResult.NO_PERMISSION;
        
        if(args.length != 1) return SpongeCommandResult.INVALID_SYNTHAX;
        
        if(src instanceof ConsoleSource){
            Methods.sendPlayerMessage(src, Text.of(TextColors.RED, "Console can ask to teleport to a player"));
        
        } else {            
            Player source = (Player)src;
            Player target = Sponge.getServer().getPlayer(args[0]).orElse(null);
            
            if(target == null){
                Methods.sendPlayerNotFound(src, args[0]);
            
            } else if (target.getName().equals(source.getName())){
                Methods.sendPlayerMessage(src, Text.of(TextColors.RED, "You can't ask yourself to teleport to yourself"));                
                
            } else {
                String destinationText = (isHere) ? "wants you to teleport to him" : "wants to teleport to you";              
                TpManager.request(source, target, !isHere);
                Methods.sendPlayerMessage(source, StyledText.parseString("A request has been sent to &a" + args[0]));                
                Methods.sendPlayerMessage(target, StyledText.parseString("&a" + source.getName() + "&f " + destinationText + ". &a/tpAccept&f or &c/tpDeny"));
            }            
        }    
        
        return SpongeCommandResult.SUCCESS;
    }

    @Override
    public Optional<Text> getShortDescription(CommandSource source) {
        String text = (isHere) ? "Ask a player to teleport to you" : "Ask a player to teleport to him";
        
        return Optional.of(Text.of(text));
    }
    
    private boolean doesSourceHavePermission(CommandSource src, String[] args){
        return (isHere && Methods.hasPermission(src, "JCMDEss.commands.tpahere")) ||
               (!isHere && Methods.hasPermission(src, "JCMDEss.commands.tpa"));
    }
}
