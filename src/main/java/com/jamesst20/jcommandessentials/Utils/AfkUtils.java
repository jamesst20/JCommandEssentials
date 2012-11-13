package com.jamesst20.jcommandessentials.Utils;

import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.event.EventListenerList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.jamesst20.jcommandessentials.JCMDEssentials.JCMDEss;

public class AfkUtils {
	public static int taskID = 0;
	public static HashMap<String, Long> idleTimeList = new HashMap<String, Long>();
	public static HashSet<String> afkList = new HashSet<String>();
	private final static EventListenerList listeners = new EventListenerList();
	public interface AfkListener extends EventListener{
	    void playerAfkStateChanged(Player player, boolean afk);
	}
	
	public static void startTask() {
		if (taskID == 0) {
			taskID = JCMDEss.plugin.getServer().getScheduler()
					.scheduleSyncRepeatingTask(JCMDEss.plugin, new Runnable() {
						public void run() {
							for (Player players : Bukkit.getServer().getOnlinePlayers()) {
								if (!afkList.contains(players.getName())) {
									if (idleTimeList.containsKey(players.getName())) {
										long idleTimeSec = (System.currentTimeMillis() - idleTimeList.get(players.getName())) / 1000L;
										if (idleTimeSec > getDelay()){
											setPlayerState(players, true);
										}
									} else {
										addPlayer(players);
									}
								}
							}
						}
					}, 20L, 100L);
		}
	}
	
	public static void stopTask() {
		if (taskID != 0) {
			JCMDEss.plugin.getServer().getScheduler().cancelTask(taskID);
			taskID = 0;
		}
	}
	
	private static long getDelay(){
		if (JCMDEss.plugin.getConfig().getLong("afk.timeout") != 0){
			return JCMDEss.plugin.getConfig().getLong("afk.timeout", 60L);
		}else{
			JCMDEss.plugin.getConfig().set("afk.timeout", 60L);
			JCMDEss.plugin.saveConfig();
			return 60L;
		}
	}
	public static void setDelay(long delay){
		JCMDEss.plugin.getConfig().set("afk.timeout", delay);
		JCMDEss.plugin.saveConfig();
	}
	
	public static void addPlayer(Player player) {
		if (!idleTimeList.containsKey(player.getName())) {
			idleTimeList.put(player.getName(), System.currentTimeMillis());
		}
	}

	public static void removePlayer(Player player) {
		if (idleTimeList.containsKey(player.getName())) {
			idleTimeList.remove(player.getName());
		}
		if (afkList.contains(player.getName())){
			afkList.remove(player.getName());
		}
	}
	
	public static void updatePlayerActivity(Player player){
		if (player != null){
			idleTimeList.put(player.getName(), System.currentTimeMillis());
			if (afkList.contains(player.getName())){
				setPlayerState(player, false);
			}			
		}
	}

	public static void setPlayerState(Player player, boolean afk){
		if (player != null){
			if (afk){
				if (!afkList.contains(player.getName())){
					afkList.add(player.getName());
					firePlayerAfkStateChanged(player, afk);
				}				
			}else{
				if (afkList.contains(player.getName())){
					idleTimeList.put(player.getName(), System.currentTimeMillis());
					afkList.remove(player.getName());
					firePlayerAfkStateChanged(player, afk);					
				}				
			}
		}
	}
	public static boolean isPlayerAfk(Player player){
		if (afkList.contains(player.getName())){
			return true;
		}
		return false;
	}
	
	/* Listener Region */
	protected static void firePlayerAfkStateChanged(Player player, boolean afk) {
		for (AfkListener listener : getAfkListeners()) {
			listener.playerAfkStateChanged(player, afk);
		}
	}

	public static void addAfkListener(AfkListener listener) {
		listeners.add(AfkListener.class, listener);
	}

	public static void removeAfkListener(AfkListener listener) {
		listeners.remove(AfkListener.class, listener);
	}

	public static AfkListener[] getAfkListeners() {
		return listeners.getListeners(AfkListener.class);
	}
}
