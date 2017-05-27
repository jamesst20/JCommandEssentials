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

import com.flowpowered.math.vector.Vector3d;
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

public class TpcCommand implements SpongeCommand{

    @Override
    public String getCommandUsage() {
        return "/tpc <x> <y> <z> [player]";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("tpc", "tppos", "tpos", "tpcoord");
    }

    @Override
    public SpongeCommandResult executeCommand(CommandSource src, String[] args) {
        
        if (isSynthaxInvalid(args)) return SpongeCommandResult.INVALID_SYNTHAX;
        
        if (!doesSourceHavePermission(src, args)) return SpongeCommandResult.NO_PERMISSION;
        
        boolean teleportSelf = (args.length == 3);
        
        if(src instanceof ConsoleSource && teleportSelf){
            Methods.sendPlayerMessage(src, Text.of(TextColors.RED, "The console can only teleport other players."));
        
        } else {
            Player player = (teleportSelf) ? (Player)src : Sponge.getServer().getPlayer(args[3]).orElse(null);
            
            if(player != null){                
                try{
                    double x = Double.parseDouble(args[0]);
                    double y = Double.parseDouble(args[1]);
                    double z = Double.parseDouble(args[2]);                    
                    
                    String teleportedTo = " have been teleported to &cx: " + x + " &ay: " + y + " &9z: " + z;

                    TpManager.teleport(player, new Vector3d(x, y, z)); 
                    
                    Methods.sendPlayerMessage(player, StyledText.parseString("You" + teleportedTo));
                    
                    if(!teleportSelf){                        
                        Methods.sendPlayerMessage(src, StyledText.parseString("The player &a" + player.getName() + "&f" + teleportedTo));
                    }

                } catch (NumberFormatException ex) {
                    Methods.sendPlayerMessage(src, Text.of(TextColors.RED, "Invalid number format for one of the coordinates."));
                }                
            
            } else {
                Methods.sendPlayerNotFound(src, args[3]);
            }
        }
        
        return SpongeCommandResult.SUCCESS;
    }

    @Override
    public Optional<Text> getShortDescription(CommandSource source) {
        return Optional.of(Text.of("Teleport yourself or a player to coordinates"));
    }
    
    private boolean isSynthaxInvalid(String[] args){           
        return (args.length < 3 || args.length > 4);
    }
    
    private boolean doesSourceHavePermission(CommandSource src, String[] args){
        return (args.length == 3 && Methods.hasPermission(src, "JCMDEss.commands.tpc.self")) ||
               (args.length == 4 && Methods.hasPermission(src, "JCMDEss.commands.tpc.others"));
    }
    
}
