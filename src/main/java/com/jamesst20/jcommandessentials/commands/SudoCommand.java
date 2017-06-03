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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;


public class SudoCommand implements SpongeCommand{

    @Override
    public String getCommandUsage() {
        return "/sudo <player> <command [args]>";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("sudo", "cmd");
    }

    @Override
    public SpongeCommandResult executeCommand(CommandSource src, String[] args) {
        if(args.length < 2) return SpongeCommandResult.INVALID_SYNTHAX;
        
        if(!Methods.hasPermission(src, "JCMDEss.commands.sudo")) return SpongeCommandResult.NO_PERMISSION;
        
        Player player = Sponge.getServer().getPlayer(args[0]).orElse(null);
        
        if(player == null){
            Methods.sendPlayerNotFound(src, args[0]);
        
        } else {
            StringBuilder command = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                command.append(args[i]).append(" ");
            }
            
            Sponge.getCommandManager().process(player, command.toString());
            Methods.sendPlayerMessage(src, StyledText.parseString("&a" + args[0] + "&f was forced to run: &6" + command.toString()));
            Methods.sendPlayerMessage(player, Text.of("You've been forced to run a command"));
        }       
        
        return SpongeCommandResult.SUCCESS;
    }

    @Override
    public Optional<Text> getShortDescription(CommandSource source) {
        return Optional.of(Text.of("Run a command as another player"));
    }
    
}
