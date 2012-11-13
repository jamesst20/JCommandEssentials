package com.jamesst20.jcommandessentials.Listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.jamesst20.jcommandessentials.Commands.FreezeCommand;
import com.jamesst20.jcommandessentials.Commands.GodCommand;
import com.jamesst20.jcommandessentials.Commands.LockCommand;
import com.jamesst20.jcommandessentials.Commands.VanishCommand;
import com.jamesst20.jcommandessentials.Objects.JPlayerConfig;
import com.jamesst20.jcommandessentials.Utils.AfkUtils;
import com.jamesst20.jcommandessentials.Utils.Methods;
import com.jamesst20.jcommandessentials.Utils.Motd;
import com.jamesst20.jcommandessentials.Utils.AfkUtils.AfkListener;

public class ThePlayerListener implements Listener, AfkListener {
	String serverLocked = "The server is currently locked!";

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(final PlayerJoinEvent e) {
		Player player = e.getPlayer();
		new JPlayerConfig(player).onLogin();
		if (Motd.isEnable()) {
			Motd.sendMotd(player);
		}
		for (String players : VanishCommand.vanishedPlayers) {
			Methods.hidePlayerFrom(player, Bukkit.getServer().getPlayer(players));
		}
		AfkUtils.addPlayer(e.getPlayer());
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerLogin(PlayerLoginEvent e) {
		/* This is not OnPlayerJoin!!! */
		Player player = e.getPlayer();
		if (player.isBanned()) {
			e.setKickMessage(new JPlayerConfig(player).getBanReason());
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerQuit(PlayerQuitEvent e) {
		if (GodCommand.godPlayers.contains(e.getPlayer().getName())) {
			GodCommand.godPlayers.remove(e.getPlayer().getName());
		}
		new JPlayerConfig(e.getPlayer()).onDisconnect();// Save Player Config
		AfkUtils.removePlayer(e.getPlayer());
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			if (GodCommand.godPlayers.contains(((Player) e.getEntity()).getName())) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerMove(PlayerMoveEvent e) {
		if (FreezeCommand.frozenPlayers.contains(e.getPlayer().getName())) {
			e.setCancelled(true);
		}
		AfkUtils.updatePlayerActivity(e.getPlayer());
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
		if (LockCommand.serverLocked) {
			Player player = e.getPlayer();
			if (!LockCommand.byPass(player)) {
				Methods.sendPlayerMessage(player, serverLocked);
				e.setCancelled(true);
			}
		}
		AfkUtils.updatePlayerActivity(e.getPlayer());
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockPlace(BlockPlaceEvent e) {
		if (LockCommand.serverLocked) {
			Player player = e.getPlayer();
			if (!LockCommand.byPass(player)) {
				Methods.sendPlayerMessage(player, serverLocked);
				e.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockBreak(BlockBreakEvent e) {
		if (LockCommand.serverLocked) {
			Player player = e.getPlayer();
			if (!LockCommand.byPass(player)) {
				Methods.sendPlayerMessage(player, serverLocked);
				e.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onCreatureSpawn(CreatureSpawnEvent e) {
		if (LockCommand.serverLocked) {
			e.setCancelled(true);
		}
	}

	@Override
	public void playerAfkStateChanged(Player player, boolean afk) {
		if (afk) {
			Methods.broadcastMessage(Methods.red(player.getDisplayName()) + " is now " + Methods.red("afk") + "!");
		} else {
			Methods.broadcastMessage(Methods.red(player.getDisplayName()) + " is now " + Methods.green("back") + "!");
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
		
	}
}
