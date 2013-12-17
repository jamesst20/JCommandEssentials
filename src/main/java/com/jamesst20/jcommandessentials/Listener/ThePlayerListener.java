/* 
 * Copyright (C) 2013 James
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
package com.jamesst20.jcommandessentials.Listener;

import com.jamesst20.jcommandessentials.Commands.FreezeCommand;
import com.jamesst20.jcommandessentials.Commands.GodCommand;
import com.jamesst20.jcommandessentials.Commands.HandicapCommand;
import com.jamesst20.jcommandessentials.Commands.LockCommand;
import com.jamesst20.jcommandessentials.Commands.MuteCommand;
import com.jamesst20.jcommandessentials.Commands.TpBackCommand;
import com.jamesst20.jcommandessentials.Commands.VanishCommand;
import com.jamesst20.jcommandessentials.Commands.WarpCommand;
import com.jamesst20.jcommandessentials.Commands.WaterWalkCommand;
import com.jamesst20.jcommandessentials.JCMDEssentials.JCMDEss;
import com.jamesst20.jcommandessentials.Objects.JPlayer;
import com.jamesst20.jcommandessentials.Objects.Warp;
import com.jamesst20.jcommandessentials.Utils.AfkUtils;
import com.jamesst20.jcommandessentials.Utils.AfkUtils.AfkListener;
import com.jamesst20.jcommandessentials.Utils.Methods;
import com.jamesst20.jcommandessentials.Utils.Motd;
import com.jamesst20.jcommandessentials.Utils.TeleportDelay;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class ThePlayerListener implements Listener, AfkListener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(final PlayerJoinEvent e) {
        Player player = e.getPlayer();
        new JPlayer(player).onLogin();
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
            e.setKickMessage(new JPlayer(player).getBanReason());
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerQuit(PlayerQuitEvent e) {
        if (GodCommand.godPlayers.contains(e.getPlayer().getName())) {
            GodCommand.godPlayers.remove(e.getPlayer().getName());
        }
        new JPlayer(e.getPlayer()).onDisconnect();// Save Player Config
        AfkUtils.removePlayer(e.getPlayer());
        TpBackCommand.removePlayer(e.getPlayer().getName());
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onSignChange(SignChangeEvent e) {
        if (e.getLine(0).equalsIgnoreCase("[warp]")) {
            if (Methods.hasPermission(e.getPlayer(), WarpCommand.PERMISSIONS_WARPSIGNS_CREATE)) {
                if (!e.getLine(1).isEmpty()) {
                    e.setLine(0, ChatColor.BLUE + "[Warp]");
                    Methods.sendPlayerMessage(e.getPlayer(), "You have created a new warp sign.");
                } else {
                    Methods.sendPlayerMessage(e.getPlayer(), ChatColor.RED + "Please include the warp name on the 2nd line.");
                    e.setCancelled(true);
                }
            } else {
                Methods.sendPlayerMessage(e.getPlayer(), ChatColor.RED + "You are not allowed to create warp signs.");
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteract(PlayerInteractEvent e) {
        Block block = e.getClickedBlock();
        if (block != null && ((block.getType() == Material.SIGN) || (block.getType() == Material.SIGN_POST) || (block.getType() == Material.WALL_SIGN)) && ((block.getState() instanceof Sign))) {
            Sign sign = (Sign) block.getState();
            if (ChatColor.stripColor(sign.getLine(0)).equalsIgnoreCase("[Warp]")) {
                String warpName = sign.getLine(1);
                if (Methods.hasPermission(e.getPlayer(), WarpCommand.PERMISSIONS_TP_WARP + warpName)) {
                    Warp warp = Warp.getWarpByName(warpName);
                    if (warp != null) {
                        Location location = warp.getLocation();
                        if (location != null) {
                            e.getPlayer().teleport(location);
                            Methods.sendPlayerMessage(e.getPlayer(), "You have been teleported to " + Methods.red(warpName) + ".");
                        } else {
                            Methods.sendPlayerMessage(e.getPlayer(), ChatColor.RED + "This warp location is invalid.");
                        }
                    }else{
                        Methods.sendPlayerMessage(e.getPlayer(), ChatColor.RED + "The warp " + warpName + " doesn't exist.");
                    }
                } else {
                    Methods.sendPlayerMessage(e.getPlayer(), ChatColor.RED + "You are not allowed to teleport to this warp.");
                }
            }
        }
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
        Player player = e.getPlayer();
        if (FreezeCommand.frozenPlayers.contains(player.getName())) {
            e.setCancelled(true);
        }
        if (TeleportDelay.isPlayerQueued(player)) {
            if (e.getFrom().getX() != e.getTo().getX() || e.getFrom().getY() != e.getTo().getY() || e.getFrom().getZ() != e.getTo().getZ()) {
                TeleportDelay.removeScheduledPlayer(player);
            }
        }
        if (WaterWalkCommand.playersWalkingWaterList.containsKey(player.getName())) {
            ArrayList<Location> blocksAroundLocation = WaterWalkCommand.getBlocksLocationAroundPlayer(player);
            for (Location loc : WaterWalkCommand.playersWalkingWaterList.get(player.getName())) {
                if (!blocksAroundLocation.contains(loc)) {
                    loc.getBlock().setType(Material.STATIONARY_WATER);  //Restore from ice to old block
                }
            }
            for (Location loc : blocksAroundLocation) {
                if (loc.getBlock().getType() == Material.WATER || loc.getBlock().getType() == Material.STATIONARY_WATER) {
                    if (!WaterWalkCommand.playersWalkingWaterList.get(player.getName()).contains(loc)) {
                        WaterWalkCommand.playersWalkingWaterList.get(player.getName()).add(loc);  //Backup Block
                    }
                    loc.getBlock().setType(Material.ICE);  //Set block to ice
                }
            }
        }
        AfkUtils.updatePlayerActivity(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        if (!LockCommand.byPass(player)) {
            LockCommand.sendErrorMessage(player);
            e.setCancelled(true);
        }

        if (MuteCommand.mutedPlayersList.contains(e.getPlayer().getName())) {
            Methods.sendPlayerMessage(e.getPlayer(), ChatColor.RED + "You are muted!");
            e.setCancelled(true);
        }
        AfkUtils.updatePlayerActivity(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        if (!LockCommand.byPass(player)) {
            LockCommand.sendErrorMessage(player);
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if (!LockCommand.byPass(player)) {
            LockCommand.sendErrorMessage(player);
            e.setCancelled(true);
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
