/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jamesst20.jcommandessentials.Commands;

import com.jamesst20.jcommandessentials.Utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author James
 */
public class ArmorCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command command, String cmd, String[] args) {
        if (args.length == 1){
            if(Methods.isConsole(cs)){
                Methods.sendPlayerMessage(cs, ChatColor.RED + "The console can't have an armor.");
                return true;
            }
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.armor.self")){
                return true;
            }
            Player p = Bukkit.getServer().getPlayer(cs.getName());
            if(args[0].equalsIgnoreCase("Leather")){
                p.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
                p.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
                p.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
                p.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));                
            }else if(args[0].equalsIgnoreCase("Iron")){
                p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
                p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
                p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
            }else if(args[0].equalsIgnoreCase("Gold")){
                p.getInventory().setHelmet(new ItemStack(Material.GOLD_HELMET));
                p.getInventory().setBoots(new ItemStack(Material.GOLD_BOOTS));
                p.getInventory().setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
                p.getInventory().setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
            }else if(args[0].equalsIgnoreCase("Diamond")){
                p.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                p.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                p.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                p.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
            }else{
                Methods.sendPlayerMessage(cs, "Unknown armor type : " + Methods.red(args[0]) + ".");
                return true;
            }
            Methods.sendPlayerMessage(cs, "You now have " + (args[0].equalsIgnoreCase("iron") ? "an " : "a ") +  Methods.red(args[0]) + " armor.");
        }else if(args.length == 2){
            if (!Methods.hasPermissionTell(cs, "JCMDEss.commands.armor.others")){
                return true;
            }
            Player p = Bukkit.getServer().getPlayer(args[0]);
            if(p != null){
                if(args[1].equalsIgnoreCase("Leather")){
                p.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
                p.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
                p.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
                p.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
            }else if(args[1].equalsIgnoreCase("Iron")){
                p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
                p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
                p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
            }else if(args[1].equalsIgnoreCase("Gold")){
                p.getInventory().setHelmet(new ItemStack(Material.GOLD_HELMET));
                p.getInventory().setBoots(new ItemStack(Material.GOLD_BOOTS));
                p.getInventory().setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
                p.getInventory().setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
            }else if(args[1].equalsIgnoreCase("Diamond")){
                p.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                p.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                p.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                p.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
            }else{
                Methods.sendPlayerMessage(cs, "Unknown armor type : " + Methods.red(args[1]) + ".");
                return true;
            }
            }else{
                Methods.playerNotFound(cs, args[0]);
                return true;
            }
            Methods.sendPlayerMessage(cs, "The player " + Methods.red(p.getDisplayName()) + " now have " + (args[1].equalsIgnoreCase("iron") ? "an " : "a ") + Methods.red(args[1]) + " armor.");
            Methods.sendPlayerMessage(p, "You now have " + (args[1].equalsIgnoreCase("iron") ? "an " : "a ") +  Methods.red(args[1]) + " armor.");
        }else{
            return false;
        }
        return true;
    }
    
}
