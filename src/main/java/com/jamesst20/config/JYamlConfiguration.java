package com.jamesst20.config;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class JYamlConfiguration {

    private File configFile;
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
    
    public String getFileName(){
        return configFile.getName();
    }

    private void loadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }
    
}
