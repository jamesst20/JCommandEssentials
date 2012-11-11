package com.jamesst20.jcommandessentials.JCMDEssentials;

import java.io.IOException;

import com.jamesst20.jcommandessentials.Commands.BanCommand;
import com.jamesst20.jcommandessentials.Commands.BroadcastCommand;
import com.jamesst20.jcommandessentials.Commands.ClearInventoryCommand;
import com.jamesst20.jcommandessentials.Commands.DisableCmdCommand;
import com.jamesst20.jcommandessentials.Commands.EnableCmdCommand;
import com.jamesst20.jcommandessentials.Commands.FlyCommand;
import com.jamesst20.jcommandessentials.Commands.FlySpeedCommand;
import com.jamesst20.jcommandessentials.Commands.FreezeCommand;
import com.jamesst20.jcommandessentials.Commands.GameModeCommand;
import com.jamesst20.jcommandessentials.Commands.GodCommand;
import com.jamesst20.jcommandessentials.Commands.HealCommand;
import com.jamesst20.jcommandessentials.Commands.HomeCommand;
import com.jamesst20.jcommandessentials.Commands.JCommandEssentialsCommand;
import com.jamesst20.jcommandessentials.Commands.KickCommand;
import com.jamesst20.jcommandessentials.Commands.KillCommand;
import com.jamesst20.jcommandessentials.Commands.LockCommand;
import com.jamesst20.jcommandessentials.Commands.MotdCommand;
import com.jamesst20.jcommandessentials.Commands.MsgCommand;
import com.jamesst20.jcommandessentials.Commands.OpenInventoryCommand;
import com.jamesst20.jcommandessentials.Commands.SetHomeCommand;
import com.jamesst20.jcommandessentials.Commands.ServerMotdCommand;
import com.jamesst20.jcommandessentials.Commands.SetSpawnCommand;
import com.jamesst20.jcommandessentials.Commands.SpawnCommand;
import com.jamesst20.jcommandessentials.Commands.StaffCommand;
import com.jamesst20.jcommandessentials.Commands.SudoCommand;
import com.jamesst20.jcommandessentials.Commands.TimeCommand;
import com.jamesst20.jcommandessentials.Commands.TpAcceptCommand;
import com.jamesst20.jcommandessentials.Commands.TpAllCommand;
import com.jamesst20.jcommandessentials.Commands.TpCommand;
import com.jamesst20.jcommandessentials.Commands.TpDenyCommand;
import com.jamesst20.jcommandessentials.Commands.TpHereCommand;
import com.jamesst20.jcommandessentials.Commands.TpaCommand;
import com.jamesst20.jcommandessentials.Commands.TpcCommand;
import com.jamesst20.jcommandessentials.Commands.UnbanCommand;
import com.jamesst20.jcommandessentials.Commands.WeatherCommand;
import com.jamesst20.jcommandessentials.Commands.WorkbenchCommand;
import com.jamesst20.jcommandessentials.Listener.ThePlayerListener;
import com.jamesst20.jcommandessentials.Listener.ServerListener;
import com.jamesst20.jcommandessentials.Methods.Methods;
import com.jamesst20.jcommandessentials.Methods.Motd;
import com.jamesst20.jcommandessentials.Methods.ServerMotd;

import com.jamesst20.jcommandessentials.mcstats.Metrics;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class JCMDEss extends JavaPlugin{
    public static JCMDEss plugin;
    @Override
    public void onEnable(){
        plugin = this;
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            Methods.log(ChatColor.RED + "Failed to send MCStats!");
        }
        setCmdsConfig();
        writeDefaultSettings();
        registerEvents();
        registerCommands();
        getLogger().info("Successfully enabled!");
    }
    @Override
    public void onDisable(){
    	this.saveConfig();
        getLogger().info("Disabled successfully!");
    }
   
    private void registerEvents(){
    	Bukkit.getServer().getPluginManager().registerEvents(new ThePlayerListener(), this);
    	Bukkit.getServer().getPluginManager().registerEvents(new ServerListener(), this);
    }
    
    private void writeDefaultSettings(){
    	Methods.writeConfigDefaultValues("enable.motd", true);
    	Motd.writeDefaultMotd();
    	ServerMotd.setDefaultConfig();
    }
    private void registerCommands(){
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
    }
}
