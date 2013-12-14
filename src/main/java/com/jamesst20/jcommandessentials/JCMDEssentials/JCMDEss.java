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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
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
        registerCommandsAndSetCmdsConfig();
        checkForUpdate();
        WarpConfig.reloadWarps();
        registerEvents();
        
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

    private void registerCommandsAndSetCmdsConfig() {
        List<String> cmdsList = new ArrayList<String>(this.getDescription().getCommands().keySet());
        Collections.sort(cmdsList);
        for(String cmd : cmdsList){
            try {
                Methods.regC(cmd, (CommandExecutor)Class.forName("com.jamesst20.jcommandessentials.Commands." + cmd + "Command").newInstance());
                Methods.registerCommandConfigDefaultValue(cmd);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(JCMDEss.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(JCMDEss.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(JCMDEss.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
