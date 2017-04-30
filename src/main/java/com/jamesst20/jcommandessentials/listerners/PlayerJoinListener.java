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
package com.jamesst20.jcommandessentials.listerners;

import com.jamesst20.jcommandessentials.utils.StyledText;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.EventListener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.channel.MessageChannel;

public class PlayerJoinListener implements EventListener<ClientConnectionEvent.Join>{
    private MessageChannel StyledChannel = new StyledText();
    
    @Override
    public void handle(ClientConnectionEvent.Join event) throws Exception {
        Player player = event.getTargetEntity();
        MessageChannel originalChannel = event.getOriginalChannel();
        MessageChannel newChannel = MessageChannel.combined(originalChannel, StyledChannel);
        player.setMessageChannel(newChannel);
    }
    
}
