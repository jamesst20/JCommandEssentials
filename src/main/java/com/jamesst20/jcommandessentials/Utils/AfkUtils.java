package com.jamesst20.jcommandessentials.Utils;

import com.jamesst20.jcommandessentials.JCMDEssentials.JCMDEss;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.swing.event.EventListenerList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;

public class AfkUtils {

    public static int taskID = 0;
    public static HashMap<String, Long> idleTimeList = new HashMap<String, Long>();
    public static HashSet<String> afkList = new HashSet<String>();
    private final static EventListenerList listeners = new EventListenerList();

    public interface AfkListener extends EventListener {

        void playerAfkStateChanged(Player player, boolean afk);
    }

    public static void startTask() {
        if (isAutoAfkEnabled()) {
            if (taskID == 0) {
                taskID = JCMDEss.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(JCMDEss.plugin, new Runnable() {
                    @Override
                    public void run() {
                        for (Player players : Bukkit.getServer().getOnlinePlayers()) {
                            if (!afkList.contains(players.getName())) {
                                if (idleTimeList.containsKey(players.getName())) {
                                    long idleTimeoutSec = (System.currentTimeMillis() - idleTimeList.get(players.getName())) / 1000L;
                                    if (idleTimeoutSec > getTimeoutDelay()) {
                                        setPlayerState(players, true);
                                    }
                                } else {
                                    addPlayer(players);
                                }
                            }
                        }
                    }
                }, 20L, 60L);
            }
        }
    }

    public static void stopTask() {
        if (taskID != 0) {
            JCMDEss.plugin.getServer().getScheduler().cancelTask(taskID);
            taskID = 0;
        }
    }

    private static long getTimeoutDelay() {
        if (JCMDEss.plugin.getConfig().getLong("autoafk.delay") != 0) {
            return JCMDEss.plugin.getConfig().getLong("autoafk.delay", 60L);
        } else {
            JCMDEss.plugin.getConfig().set("autoafk.delay", 60L);
            JCMDEss.plugin.saveConfig();
            return 60L;
        }
    }

    public static void setDelay(long delay) {
        JCMDEss.plugin.getConfig().set("autoafk.delay", delay);
        JCMDEss.plugin.saveConfig();
    }

    public static void toggleAutoAfk(boolean enable) {
        JCMDEss.plugin.getConfig().set("enable.autoafk", Boolean.valueOf(enable));
        JCMDEss.plugin.saveConfig();
        if (enable) {
            startTask();
        } else {
            stopTask();
            afkList.clear();
            idleTimeList.clear();
        }
    }

    public static boolean isAutoAfkEnabled() {
        if (JCMDEss.plugin.getConfig().get("enable.autoafk") == null) {
            toggleAutoAfk(true);
            return true;
        } else {
            return JCMDEss.plugin.getConfig().getBoolean("enable.autoafk", true);
        }
    }

    public static void addPlayer(Player player) {
        if (isAutoAfkEnabled()) {
            if (!idleTimeList.containsKey(player.getName())) {
                idleTimeList.put(player.getName(), System.currentTimeMillis());
            }
        }
    }

    public static void removePlayer(Player player) {
        if (idleTimeList.containsKey(player.getName())) {
            idleTimeList.remove(player.getName());
        }
        if (afkList.contains(player.getName())) {
            afkList.remove(player.getName());
        }
    }

    public static void updatePlayerActivity(Player player) {
        if (player != null) {
            idleTimeList.put(player.getName(), System.currentTimeMillis());
            if (afkList.contains(player.getName())) {
                setPlayerState(player, false);
            }
        }
    }

    public static void setPlayerState(Player player, boolean afk) {
        if (player != null) {
            if (afk) {
                if (!afkList.contains(player.getName())) {
                    afkList.add(player.getName());
                    firePlayerAfkStateChanged(player, afk);
                }
            } else {
                if (afkList.contains(player.getName())) {
                    idleTimeList.put(player.getName(), System.currentTimeMillis());
                    afkList.remove(player.getName());
                    firePlayerAfkStateChanged(player, afk);
                }
            }
        }
    }

    public static boolean isPlayerAfk(Player player) {
        if (afkList.contains(player.getName())) {
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
