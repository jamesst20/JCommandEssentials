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

public class TpBackCommand implements SpongeCommand {

    @Override
    public String getCommandUsage() {
        return "/tpback [player]";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("tpback", "teleportback", "tplastlocation", "back", "teleportlastlocation", "tplast", "teleportlast");
    }

    @Override
    public SpongeCommandResult executeCommand(CommandSource src, String[] args) {
        
        if(args.length > 1) return SpongeCommandResult.INVALID_SYNTHAX;
        
        if(!doesSourceHavePermission(src, args)) return SpongeCommandResult.NO_PERMISSION;
        
        if(src instanceof ConsoleSource && args.length == 0){
            Methods.sendPlayerMessage(src, Text.of(TextColors.RED, "Console can only teleport other players back."));
        
        } else {
            Player player = (args.length == 0) ? (Player)src : Sponge.getServer().getPlayer(args[0]).orElse(null);
            
            if(player == null){
                Methods.sendPlayerNotFound(src, args[0]);
            
            } else {
                if(TpManager.teleportBack(player)){
                    Methods.sendPlayerMessage(player, Text.of("You have been teleported back."));
                    
                    if(args.length == 1){
                        Methods.sendPlayerMessage(src, StyledText.parseString("&a" + player.getName() + "&f have been teleported back"));
                    }
                
                } else {
                    Methods.sendPlayerMessage(src, Text.of(TextColors.RED, "No location to teleport back."));
                }
            }
        }
        
        return SpongeCommandResult.SUCCESS;
    }
    
    private boolean doesSourceHavePermission(CommandSource src, String[] args){
        return (Methods.hasPermission(src, "JCMDEss.commands.tpback.self") && args.length == 0) ||
               (Methods.hasPermission(src, "JCMDEss.commands.tpback.others") && args.length == 1);
    }

    @Override
    public Optional<Text> getShortDescription(CommandSource source) {
        return Optional.of(Text.of("Teleport the player to its last location"));
    }
    
}
