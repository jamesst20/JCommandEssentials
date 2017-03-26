/* 
 * Copyright (C) 2017 James St-Pierre
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
package com.jamesst20.jcommandessentials.commands;

import com.jamesst20.jcommandessentials.interfaces.SpongeCommand;
import com.jamesst20.jcommandessentials.utils.Methods;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ArmorCommand implements SpongeCommand {

    @Override
    public String getCommandUsage() {
        return "/armor [player] <leather|iron|gold|diamond>";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("armor");
    }

    @Override
    public SpongeCommandResult executeCommand(CommandSource src, String[] args) {
        if (args.length == 1) {
            if (src instanceof ConsoleSource) {
                Methods.sendPlayerMessage(src, Text.of("The console can't have an armor."));
                return SpongeCommandResult.SUCCESS;
            }
            if (Methods.hasPermission(src, "JCMDEss.commands.armor.self")) {
                Player player = (Player) src;
                if (setArmor(player, args[0])) {
                    Methods.sendPlayerMessage(src, Text.of("You now have " + (args[0].equalsIgnoreCase("iron") ? "an " : "a ") + args[0] + " armor."));
                } else {
                    Methods.sendPlayerMessage(src, Text.of("No such armor " + args[0] + "."));
                }
            } else {
                return SpongeCommandResult.NO_PERMISSION;
            }
        } else if (args.length == 2) {
            if (Methods.hasPermission(src, "JCMDEss.commands.armor.others")) {
                Player player = Sponge.getServer().getPlayer(args[0]).orElse(null);
                if (player != null) {
                    if (setArmor(player, args[1])) {
                        Methods.sendPlayerMessage(src, Text.builder().append(Text.of("You gave " + (args[1].equalsIgnoreCase("iron") ? "an " : "a ") + args[1] + " armor to ")).append(Text.of(TextColors.RED, player.getName())).build());
                        Methods.sendPlayerMessage(player, Text.of("You now have " + (args[1].equalsIgnoreCase("iron") ? "an " : "a ") + args[1] + " armor."));
                    } else {
                        Methods.sendPlayerMessage(src, Text.of("No such armor " + args[1] + "."));
                    }
                } else {
                    Methods.sendPlayerNotFound(src, args[0]);
                }
            } else {
                return SpongeCommandResult.NO_PERMISSION;
            }
        } else {
            return SpongeCommandResult.INVALID_SYNTHAX;
        }
        return SpongeCommandResult.SUCCESS;
    }

    @Override
    public Optional<Text> getShortDescription(CommandSource src) {
        return Optional.of(Text.of("Give someone or yourself an armor. Leather, Iron, Gold, Diamond."));
    }

    private boolean setArmor(Player p, String armor) {
        if (armor.equalsIgnoreCase("Leather")) {
            p.setHelmet(ItemStack.of(ItemTypes.LEATHER_HELMET, 1));
            p.setBoots(ItemStack.of(ItemTypes.LEATHER_BOOTS, 1));
            p.setChestplate(ItemStack.of(ItemTypes.LEATHER_CHESTPLATE, 1));
            p.setLeggings(ItemStack.of(ItemTypes.LEATHER_LEGGINGS, 1));
        } else if (armor.equalsIgnoreCase("Iron")) {
            p.setHelmet(ItemStack.of(ItemTypes.IRON_HELMET, 1));
            p.setBoots(ItemStack.of(ItemTypes.IRON_BOOTS, 1));
            p.setChestplate(ItemStack.of(ItemTypes.IRON_CHESTPLATE, 1));
            p.setLeggings(ItemStack.of(ItemTypes.IRON_LEGGINGS, 1));
        } else if (armor.equalsIgnoreCase("Gold")) {
            p.setHelmet(ItemStack.of(ItemTypes.GOLDEN_HELMET, 1));
            p.setBoots(ItemStack.of(ItemTypes.GOLDEN_BOOTS, 1));
            p.setChestplate(ItemStack.of(ItemTypes.GOLDEN_CHESTPLATE, 1));
            p.setLeggings(ItemStack.of(ItemTypes.GOLDEN_LEGGINGS, 1));
        } else if (armor.equalsIgnoreCase("Diamond")) {
            p.setHelmet(ItemStack.of(ItemTypes.DIAMOND_HELMET, 1));
            p.setBoots(ItemStack.of(ItemTypes.DIAMOND_BOOTS, 1));
            p.setChestplate(ItemStack.of(ItemTypes.DIAMOND_CHESTPLATE, 1));
            p.setLeggings(ItemStack.of(ItemTypes.DIAMOND_LEGGINGS, 1));
        } else {
            return false;
        }
        return true;
    }
}
