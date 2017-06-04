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

import com.jamesst20.jcommandessentials.JCMDEss;
import com.jamesst20.jcommandessentials.interfaces.SpongeCommand;
import com.jamesst20.jcommandessentials.utils.Methods;
import com.jamesst20.jcommandessentials.utils.StyledText;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityArchetype;
import org.spongepowered.api.entity.EntitySnapshot;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.weather.Lightning;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.Text;

/**
 *
 * @author charl
 */
public class StrikeCommand implements SpongeCommand{

    @Override
    public String getCommandUsage() {
        return "/strike <player>";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("strike", "lightning", "zeus");
    }

    @Override
    public SpongeCommandResult executeCommand(CommandSource src, String[] args) {
        
        if(args.length != 1) return SpongeCommandResult.INVALID_SYNTHAX;
        
        if(!Methods.hasPermission(src, "JCMDEss.commands.strike")) return SpongeCommandResult.NO_PERMISSION;
        
        Player player = Sponge.getServer().getPlayer(args[0]).orElse(null);
        
        if(player == null){
            Methods.sendPlayerNotFound(src, args[0]);
        
        } else {
            Entity lightning = player.getWorld().createEntity(EntityTypes.LIGHTNING, player.getLocation().getPosition());            
            player.getWorld().spawnEntity(lightning, Cause.source(Sponge.getPluginManager().fromInstance(JCMDEss.plugin).get()).build());
            
            Methods.sendPlayerMessage(src, StyledText.parseString("You struck &a" + player.getName() + "&f."));
            Methods.sendPlayerMessage(player, Text.of("You've got struck by lightning."));
        }
        
        return SpongeCommandResult.SUCCESS;
    }

    @Override
    public Optional<Text> getShortDescription(CommandSource source) {
        return Optional.of(Text.of("Strike lightning a player"));
    }
    
}
