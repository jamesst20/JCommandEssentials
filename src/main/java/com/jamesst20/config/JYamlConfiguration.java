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
package com.jamesst20.config;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class JYamlConfiguration {

    private final File configFile;
    private YamlConfiguration config;

    public JYamlConfiguration(String configFileName) {
        configFile = new File(configFileName);
        loadConfig();
    }

    public JYamlConfiguration(JavaPlugin plugin, String configFileName) {
        configFile = new File(plugin.getDataFolder(), configFileName);
        loadConfig();
    }

    public JYamlConfiguration(JavaPlugin plugin) {
        configFile = new File(plugin.getDataFolder(), "config.yml");
        loadConfig();
    }

    public JYamlConfiguration(File config) {
        configFile = config;
        loadConfig();
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException ex) {
            Logger.getLogger(JYamlConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setDefault(String node, Object value) {
        if (config.get(node) == null) {
            config.set(node, value);
        }
    }

    public void set(String path, Object value) {
        config.set(path, value);
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public String getFileName() {
        return configFile.getName();
    }

    private void loadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }

}