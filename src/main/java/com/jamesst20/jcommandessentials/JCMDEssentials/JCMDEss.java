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
package com.jamesst20.jcommandessentials.JCMDEssentials;

import com.jamesst20.jcommandessentials.Commands.*;
import com.jamesst20.jcommandessentials.Listener.ServerListener;
import com.jamesst20.jcommandessentials.Listener.ThePlayerListener;
import com.jamesst20.jcommandessentials.Utils.AfkUtils;
import com.jamesst20.jcommandessentials.Utils.Methods;
import com.jamesst20.jcommandessentials.Utils.Motd;
import com.jamesst20.jcommandessentials.Utils.ServerMotd;
import com.jamesst20.jcommandessentials.Utils.TeleportDelay;
import com.jamesst20.jcommandessentials.Utils.WarpConfig;
import com.jamesst20.jcommandessentials.mcstats.Metrics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class JCMDEss extends JavaPlugin {

    public static JCMDEss plugin;

    @Override
    public void onEnable() {
        plugin = this;
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            Methods.log(ChatColor.RED + "Failed to send MCStats!");
        }
        writeDefaultSettings();
        setCmdsConfig();
        checkForUpdate();
        WarpConfig.reloadWarps();
        registerEvents();
        registerCommands();
        getLogger().info("Successfully enabled!");
    }

    @Override
    public void onDisable() {
        this.saveConfig();
        getLogger().info("Disabled successfully!");
    }

    private void registerEvents() {
        Bukkit.getServer().getPluginManager().registerEvents(new ThePlayerListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ServerListener(), this);
        AfkUtils.addAfkListener(new ThePlayerListener());
        AfkUtils.startTask();
    }

    private void writeDefaultSettings() {
        Methods.writeConfigDefaultValues("enable.updatechecker", true);
        Methods.writeConfigDefaultValues("enable.motd", true);
        Motd.writeDefaultMotd();
        ServerMotd.setDefaultConfig();
        TeleportDelay.setDefaultDelay();
    }

    private void registerCommands() {
        Methods.regC("jcommandessentials", new JCommandEssentialsCommand());
        Methods.regC("enablecmd", new EnableCmdCommand());
        Methods.regC("disablecmd", new DisableCmdCommand());
        Methods.regC("home", new HomeCommand());
        Methods.regC("sethome", new SetHomeCommand());
        Methods.regC("weather", new WeatherCommand());
        Methods.regC("tpa", new TpaCommand());
        Methods.regC("tpaccept", new TpAcceptCommand());
        Methods.regC("tpdeny", new TpDenyCommand());
        Methods.regC("tp", new TpCommand());
        Methods.regC("tphere", new TpHereCommand());
        Methods.regC("time", new TimeCommand());
        Methods.regC("broadcast", new BroadcastCommand());
        Methods.regC("spawn", new SpawnCommand());
        Methods.regC("setspawn", new SetSpawnCommand());
        Methods.regC("fly", new FlyCommand());
        Methods.regC("god", new GodCommand());
        Methods.regC("motd", new MotdCommand());
        Methods.regC("tpall", new TpAllCommand());
        Methods.regC("freeze", new FreezeCommand());
        Methods.regC("lock", new LockCommand());
        Methods.regC("servermotd", new ServerMotdCommand());
        Methods.regC("sudo", new SudoCommand());
        Methods.regC("gamemode", new GameModeCommand());
        Methods.regC("heal", new HealCommand());
        Methods.regC("msg", new MsgCommand());
        Methods.regC("staff", new StaffCommand());
        Methods.regC("tpc", new TpcCommand());
        Methods.regC("kick", new KickCommand());
        Methods.regC("ban", new BanCommand());
        Methods.regC("unban", new UnbanCommand());
        Methods.regC("clearinventory", new ClearInventoryCommand());
        Methods.regC("kill", new KillCommand());
        Methods.regC("workbench", new WorkbenchCommand());
        Methods.regC("flyspeed", new FlySpeedCommand());
        Methods.regC("openinventory", new OpenInventoryCommand());
        Methods.regC("vanish", new VanishCommand());
        Methods.regC("afk", new AfkCommand());
        Methods.regC("killmobs", new KillMobsCommand());
        Methods.regC("spawnmob", new SpawnMobCommand());
        Methods.regC("handicap", new HandicapCommand());
        Methods.regC("ip", new IpCommand());
        Methods.regC("mute", new MuteCommand());
        Methods.regC("tpthere", new TpThereCommand());
        Methods.regC("setexp", new SetExpCommand());
        Methods.regC("enchant", new EnchantCommand());
        Methods.regC("walkspeed", new WalkSpeedCommand());
        Methods.regC("tpback", new TpBackCommand());
        Methods.regC("whois", new WhoisCommand());
        Methods.regC("warp", new WarpCommand());
        Methods.regC("whatisit", new WhatIsItCommand());
        Methods.regC("tpahere", new TpaHereCommand());
        Methods.regC("waterwalk", new WaterWalkCommand());
        Methods.regC("armor", new ArmorCommand());
        Methods.regC("shoot", new ShootCommand());
        Methods.regC("setitemdescription", new SetItemDescriptionCommand());
        Methods.regC("setitemname", new SetItemNameCommand());
        Methods.regC("freeme", new FreeMeCommand());
    }

    public void setCmdsConfig() {
        Methods.registerCommandConfigDefaultValue("enablecmd");
        Methods.registerCommandConfigDefaultValue("disablecmd");
        Methods.registerCommandConfigDefaultValue("home");
        Methods.registerCommandConfigDefaultValue("sethome");
        Methods.registerCommandConfigDefaultValue("weather");
        Methods.registerCommandConfigDefaultValue("tpa");
        Methods.registerCommandConfigDefaultValue("tpaccept");
        Methods.registerCommandConfigDefaultValue("tpdeny");
        Methods.registerCommandConfigDefaultValue("tp");
        Methods.registerCommandConfigDefaultValue("tphere");
        Methods.registerCommandConfigDefaultValue("time");
        Methods.registerCommandConfigDefaultValue("broadcast");
        Methods.registerCommandConfigDefaultValue("spawn");
        Methods.registerCommandConfigDefaultValue("setspawn");
        Methods.registerCommandConfigDefaultValue("fly");
        Methods.registerCommandConfigDefaultValue("god");
        Methods.registerCommandConfigDefaultValue("motd");
        Methods.registerCommandConfigDefaultValue("tpall");
        Methods.registerCommandConfigDefaultValue("freeze");
        Methods.registerCommandConfigDefaultValue("lock");
        Methods.registerCommandConfigDefaultValue("servermotd");
        Methods.registerCommandConfigDefaultValue("sudo");
        Methods.registerCommandConfigDefaultValue("gamemode");
        Methods.registerCommandConfigDefaultValue("heal");
        Methods.registerCommandConfigDefaultValue("msg");
        Methods.registerCommandConfigDefaultValue("staff");
        Methods.registerCommandConfigDefaultValue("tpc");
        Methods.registerCommandConfigDefaultValue("kick");
        Methods.registerCommandConfigDefaultValue("ban");
        Methods.registerCommandConfigDefaultValue("unban");
        Methods.registerCommandConfigDefaultValue("clearinventory");
        Methods.registerCommandConfigDefaultValue("kill");
        Methods.registerCommandConfigDefaultValue("workbench");
        Methods.registerCommandConfigDefaultValue("flyspeed");
        Methods.registerCommandConfigDefaultValue("openinventory");
        Methods.registerCommandConfigDefaultValue("vanish");
        Methods.registerCommandConfigDefaultValue("afk");
        Methods.registerCommandConfigDefaultValue("killmobs");
        Methods.registerCommandConfigDefaultValue("spawnmob");
        Methods.registerCommandConfigDefaultValue("handicap");
        Methods.registerCommandConfigDefaultValue("ip");
        Methods.registerCommandConfigDefaultValue("mute");
        Methods.registerCommandConfigDefaultValue("tpthere");
        Methods.registerCommandConfigDefaultValue("setexp");
        Methods.registerCommandConfigDefaultValue("enchant");
        Methods.registerCommandConfigDefaultValue("walkspeed");
        Methods.registerCommandConfigDefaultValue("tpback");
        Methods.registerCommandConfigDefaultValue("whois");
        Methods.registerCommandConfigDefaultValue("warp");
        Methods.registerCommandConfigDefaultValue("whatisit");
        Methods.registerCommandConfigDefaultValue("tpahere");
        Methods.registerCommandConfigDefaultValue("waterwalk");
        Methods.registerCommandConfigDefaultValue("armor");
        Methods.registerCommandConfigDefaultValue("shoot");
        Methods.registerCommandConfigDefaultValue("setitemname");
        Methods.registerCommandConfigDefaultValue("setitemdescription");
        Methods.registerCommandConfigDefaultValue("freeme");
    }

    private void checkForUpdate() {
        if (plugin.getConfig().getBoolean("enable.updatechecker", true)) {
            try {
                URL url = new URL("http://pastebin.com/raw.php?i=wZr5D85x");
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                float currentVersion = Float.parseFloat(plugin.getDescription().getVersion());
                float newestVersion = Float.parseFloat(in.readLine());
                if (currentVersion < newestVersion) {
                    Methods.log(Methods.prefix + ChatColor.RED + "New update available! Current is " + currentVersion + " Newest is " + newestVersion);
                } else {
                    Methods.log(Methods.prefix + ChatColor.GREEN + "JCommandEssentials is up to date!");
                }
                in.close();
            } catch (MalformedURLException e) {
                Methods.log(Methods.prefix + ChatColor.RED + "Failed to check for update.");
            } catch (IOException e) {
                Methods.log(Methods.prefix + ChatColor.RED + "Failed to check for update.");
            } catch (NumberFormatException e) {
                Methods.log(Methods.prefix + ChatColor.RED + "Failed to check for update.");
            } catch (NullPointerException e) {
                Methods.log(Methods.prefix + ChatColor.RED + "Failed to check for update.");
            }
        }
    }
}
