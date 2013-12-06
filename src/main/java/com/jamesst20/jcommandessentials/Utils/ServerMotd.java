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
package com.jamesst20.jcommandessentials.Utils;

import com.jamesst20.jcommandessentials.JCMDEssentials.JCMDEss;

public class ServerMotd {

    static JCMDEss plugin = JCMDEss.plugin;
    private static String defaultMotd = "&f[&aJCMD&2Ess&f] &cMotd not set!";

    public static void setServerMotd(String message) {
        plugin.getConfig().set("server.motd", message);
        plugin.saveConfig();
    }

    public static String getServerMotd() {
        String motd = plugin.getConfig().getString("server.motd");
        if (motd != null) {
            return Methods.coloring(plugin.getConfig().getString("server.motd"));
        } else {
            return "";
        }
    }

    public static void disableServerMotd() {
        plugin.getConfig().set("enable.servermotd", Boolean.valueOf(false));
        plugin.saveConfig();
    }

    public static void enableServerMotd() {
        plugin.getConfig().set("enable.servermotd", Boolean.valueOf(true));
        plugin.saveConfig();
    }

    public static void setDefaultConfig() {
        if (plugin.getConfig().get("enable.servermotd") == null) {
            plugin.getConfig().set("enable.servermotd", Boolean.valueOf(true));
        }
        if (plugin.getConfig().get("server.motd") == null) {
            plugin.getConfig().set("server.motd", defaultMotd);
        }
        plugin.saveConfig();
    }

    public static boolean isEnabled() {
        return plugin.getConfig().getBoolean("enable.servermotd");
    }
}
