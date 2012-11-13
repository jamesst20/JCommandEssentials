package com.jamesst20.jcommandessentials.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import com.jamesst20.jcommandessentials.Utils.ServerMotd;

public class ServerListener implements Listener {

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onServerListPing(ServerListPingEvent event) {
		if (ServerMotd.isEnabled() && !ServerMotd.getServerMotd().isEmpty()) {
			event.setMotd(ServerMotd.getServerMotd());
		}
	}

}
