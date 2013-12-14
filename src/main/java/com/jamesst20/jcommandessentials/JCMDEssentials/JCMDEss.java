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

import com.jamesst20.jcommandessentials.Listener.ServerListener;
import com.jamesst20.jcommandessentials.Listener.ThePlayerListener;
import com.jamesst20.jcommandessentials.Utils.AfkUtils;
import com.jamesst20.jcommandessentials.Utils.Methods;
import com.jamesst20.jcommandessentials.Utils.Motd;
import com.jamesst20.jcommandessentials.Utils.ServerMotd;
import com.jamesst20.jcommandessentials.Utils.TeleportDelay;
import com.jamesst20.jcommandessentials.Utils.Updater;
import com.jamesst20.jcommandessentials.Utils.WarpConfig;
import com.jamesst20.jcommandessentials.mcstats.Metrics;
import java.io.IOException;
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
        Methods.writeConfigDefaultValues("enable.update.enable", true);
        Methods.writeConfigDefaultValues("enable.update.autodownload", true);
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
        if (plugin.getConfig().getBoolean("enable.update.enable", true)) {
            if(plugin.getConfig().getBoolean("enable.update.autodownload", true)){
                Updater updater = new Updater(this, 47075, this.getFile(), Updater.UpdateType.DEFAULT, true);
                if(updater.getResult() == Updater.UpdateResult.SUCCESS){
                    Methods.log("[JCommandEssentials] " + ChatColor.RED + "An update is available.");
                    Methods.log("[JCommandEssentials] " + ChatColor.GREEN + "The update has been downloaded.");
                }
            }else{
                Updater updater = new Updater(this, 47075, this.getFile(), Updater.UpdateType.NO_DOWNLOAD, true);
                if(updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE){
                    Methods.log("[JCommandEssentials] " + ChatColor.RED + "An update is available.");
                }
            }            
        }
    }
}
