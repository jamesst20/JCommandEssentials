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

package com.jamesst20.jcommandessentials.Commands;

import com.jamesst20.jcommandessentials.Utils.Methods;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpyCommand implements CommandExecutor{
    private static HashMap<String, List<String>>  playersSpiedByAdmin = new HashMap<String, List<String>>(); // First = Admin, Second = Players spied list
    
    public static void SpyPlayer(String sender, String receiver, String msg){
        for(String admin:playersSpiedByAdmin.keySet()){
            if(playersSpiedByAdmin.get(admin).contains(sender) || playersSpiedByAdmin.get(admin).contains(receiver)){
                if(admin.equalsIgnoreCase("console")){
                    Bukkit.getConsoleSender().sendMessage(msg);
                }else{
                    Player pAdmin = Bukkit.getServer().getPlayer(admin);
                    if(pAdmin != null){
                        pAdmin.sendMessage(msg);
                    }
                }                
            }
        }
    }
    
    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String cmd, String[] args) {
        if(args.length != 1){
            return false;
        }
        if(!Methods.hasPermissionTell(cs, "JCMDEss.commands.spy")){
            return true;
        }
        if(args[0].equalsIgnoreCase("clear")){
            if(playersSpiedByAdmin.get(cs.getName()) != null){
                playersSpiedByAdmin.get(cs.getName()).clear();
            }            
            Methods.sendPlayerMessage(cs, "You cleared your spying list.");
            return true;
        }
        Player p = Bukkit.getServer().getPlayer(args[0]);
        if(p != null){
            if(!playersSpiedByAdmin.containsKey(cs.getName())){
                List<String> spiedPlayers = new ArrayList<String>();
                spiedPlayers.add(p.getName());
                playersSpiedByAdmin.put(cs.getName(), spiedPlayers);
                Methods.sendPlayerMessage(cs, "You are now spying " + Methods.red(p.getName()) + " private messages.");
            }else{
                List<String> spiedPlayers = playersSpiedByAdmin.get(cs.getName());
                if(spiedPlayers.contains(p.getName())){
                    spiedPlayers.remove(p.getName());
                    Methods.sendPlayerMessage(cs, "You stopped spying " + Methods.red(p.getName()) + " private messages.");
                }else{
                    spiedPlayers.add(p.getName());     
                    Methods.sendPlayerMessage(cs, "You are now spying " + Methods.red(p.getName()) + " private messages.");
                }
                playersSpiedByAdmin.put(cs.getName(), spiedPlayers);
            }
        }else{
            Methods.playerNotFound(cs, args[0]);
        }
        return true;
    }
    
}
