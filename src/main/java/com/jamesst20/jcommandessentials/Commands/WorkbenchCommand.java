/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamesst20.jcommandessentials.commands;

import com.google.inject.Inject;
import com.jamesst20.jcommandessentials.JCMDEss;
import com.jamesst20.jcommandessentials.interfaces.SpongeCommand;
import org.spongepowered.api.command.CommandSource;
import com.jamesst20.jcommandessentials.utils.Methods;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.Sponge;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.format.TextColors;

/**
 *
 * @author charl
 */
public class WorkbenchCommand implements SpongeCommand{

    @Override
    public String getCommandUsage() {
        return "/workbench [player]";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("workbench", "craft", "crafting", "craftingtable", "crafttable", "wb", "ct");
    }

    @Override
    public SpongeCommandResult executeCommand(CommandSource src, String[] args) {
        if (!Methods.hasPermission(src, "JCMDEss.commands.workbench")) {
            return SpongeCommandResult.NO_PERMISSION;        
        }
        
        if (args.length == 0) {
            if (src instanceof ConsoleSource) {
                Methods.sendPlayerMessage(src, Text.of(TextColors.RED, "The console can't open a workbench for itself."));
                return SpongeCommandResult.SUCCESS;
            }
            Player player = ((Player) src);
            Inventory workbench = Inventory.builder().of(InventoryArchetypes.WORKBENCH).build(JCMDEss.pluginContainer);
            player.openInventory(workbench, Cause.of(NamedCause.of("Plugin", JCMDEss.pluginContainer)));
            Methods.sendPlayerMessage(src, Text.of("You forced yourself to open a workbench."));
            return SpongeCommandResult.SUCCESS;
        } else if (args.length == 1) {
            Player player = Sponge.getServer().getPlayer(args[0]).orElse(null);;
            if (player != null) {
                Inventory workbench = Inventory.builder().of(InventoryArchetypes.WORKBENCH).build(JCMDEss.pluginContainer);
                Cause cause = Cause.of(NamedCause.source(src), NamedCause.of("Plugin", JCMDEss.pluginContainer));
                player.openInventory(workbench, cause);
                
                Methods.sendPlayerMessage(src, Text.of(TextColors.RED, "You forced " + args[0] + " to open a workbench."));
                return SpongeCommandResult.SUCCESS;
            } else {
                Methods.sendPlayerNotFound(src, args[0]);
                return SpongeCommandResult.SUCCESS;
            }
        } else {
            return SpongeCommandResult.INVALID_SYNTHAX;
        }
    }

    @Override
    public Optional<Text> getShortDescription(CommandSource source) {
        return Optional.of(Text.of("Force opening a workbench anywhere"));
    }
    
}
