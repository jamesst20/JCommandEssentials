package com.jamesst20.jcommandessentials.Listener;

import com.jamesst20.jcommandessentials.Commands.*;
import com.jamesst20.jcommandessentials.JCMDEssentials.JCMDEss;
import com.jamesst20.jcommandessentials.Objects.JPlayerConfig;
import com.jamesst20.jcommandessentials.Utils.AfkUtils;
import com.jamesst20.jcommandessentials.Utils.AfkUtils.AfkListener;
import com.jamesst20.jcommandessentials.Utils.Methods;
import com.jamesst20.jcommandessentials.Utils.Motd;
import com.jamesst20.jcommandessentials.Utils.TeleportDelay;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

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
        TpBackCommand.removePlayer(e.getPlayer().getName());
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
        if (TeleportDelay.isPlayerQueued(e.getPlayer())) {
            if (e.getFrom().getX() != e.getTo().getX() || e.getFrom().getY() != e.getTo().getY() || e.getFrom().getZ() != e.getTo().getZ()) {
                TeleportDelay.removeScheduledPlayer(e.getPlayer());
            }
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
        if (MuteCommand.mutedPlayersList.contains(e.getPlayer().getName())) {
            Methods.sendPlayerMessage(e.getPlayer(), ChatColor.RED + "You are muted!");
            e.setCancelled(true);
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
        if (HandicapCommand.handicappedList.contains(e.getPlayer().getName())) {
            if (!Methods.hasPermission(e.getPlayer(), "JCMDEss.commands.handicap.exempt")) {
                Methods.sendPlayerMessage(e.getPlayer(), ChatColor.RED + "You're not allowed to use command.");
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        TpBackCommand.playersLastTeleport.put(e.getPlayer().getName(), e.getFrom());
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerDeath(PlayerDeathEvent e) {
        TpBackCommand.playersLastTeleport.put(e.getEntity().getName(), e.getEntity().getLocation());
        if (Methods.hasPermission(e.getEntity(), TpBackCommand.permPrefix + ".self")) {
            /* We want this message to display after the death message*/
            final Player deathPlayer = e.getEntity();
            Bukkit.getServer().getScheduler().runTaskLater(JCMDEss.plugin, new Runnable() {
                @Override
                public void run() {
                    deathPlayer.sendMessage(ChatColor.GOLD + "Use /back command to return to your death point.");
                }
            }, 1);
        }
    }
}
