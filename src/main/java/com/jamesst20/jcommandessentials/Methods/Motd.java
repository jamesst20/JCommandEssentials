package com.jamesst20.jcommandessentials.Methods;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jamesst20.jcommandessentials.JCMDEssentials.JCMDEss;

public class Motd {
	static JCMDEss plugin = JCMDEss.plugin;
	
	public static void sendMotd(Player player) {
		Scanner scanner = new Scanner(Methods.readFile("motd.txt"));
		while (scanner.hasNextLine()) {
			player.sendMessage(setMotdFeatures(
					ChatColor.translateAlternateColorCodes('&',
							scanner.nextLine()), player));
		}
		scanner.close();
	}

	public static void setMotd(String msg, CommandSender player) {
		if (player != null) {
			Methods.sendPlayerMessage(player,
					"Please, edit the file using a text editor.");
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
	public static void enableMotd(){
		plugin.getConfig().set("enable.motd", Boolean.valueOf(true));
		plugin.saveConfig();
	}
	public static void disableMotd(){
		plugin.getConfig().set("enable.motd", Boolean.valueOf(false));
		plugin.saveConfig();
	}
	public static void motdDisabled(CommandSender cs){
		Methods.sendPlayerMessage(cs, ChatColor.RED + "Motd is disabled.");
	}
	public static boolean isEnable() {
		return JCMDEss.plugin.getConfig().getBoolean("enable.motd", true);
	}
}
