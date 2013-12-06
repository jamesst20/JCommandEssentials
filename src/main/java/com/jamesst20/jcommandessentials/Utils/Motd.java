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
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Motd {

    static JCMDEss plugin = JCMDEss.plugin;

    public static void sendMotd(Player player) {
        Scanner scanner = new Scanner(Methods.readFile("motd.txt"));
        while (scanner.hasNextLine()) {
            player.sendMessage(setMotdFeatures(ChatColor.translateAlternateColorCodes('&', scanner.nextLine()), player));
        }
        scanner.close();
    }

    public static void setMotd(String msg, CommandSender player) {
        if (player != null) {
            Methods.sendPlayerMessage(player, "Please, edit the file using a text editor.");
        }
    }

    private static String setMotdFeatures(String message, Player player) {
        String msg = message;
        msg = msg.replace("{PLAYER}", player.getDisplayName());
        msg = msg.replace("{ONLINE}", Methods.getFormattedOnlinePlayers());
        return msg;
    }

    public static void writeDefaultMotd() {
        File file = new File(JCMDEss.plugin.getDataFolder(), "motd.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
                FileWriter outFile = new FileWriter(file);
                PrintWriter out = new PrintWriter(outFile);
                out.print("&2Welcome {PLAYER}! Currently Online: {ONLINE}");
                out.close();
            } catch (IOException e) {
                Methods.log(ChatColor.RED + "Failed to create motd.txt");
            }
        }
    }

    public static void enableMotd() {
        plugin.getConfig().set("enable.motd", Boolean.valueOf(true));
        plugin.saveConfig();
    }

    public static void disableMotd() {
        plugin.getConfig().set("enable.motd", Boolean.valueOf(false));
        plugin.saveConfig();
    }

    public static void motdDisabled(CommandSender cs) {
        Methods.sendPlayerMessage(cs, ChatColor.RED + "Motd is disabled.");
    }

    public static boolean isEnable() {
        return JCMDEss.plugin.getConfig().getBoolean("enable.motd", true);
    }
}
