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
import com.jamesst20.jcommandessentials.utils.StyledText;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

/**
 *
 * @author charl
 */
public class ColorsCommand implements SpongeCommand{
    
    public static HashMap<String, Boolean> xmlFormatUsers = new HashMap<String, Boolean>();
    
    private static final String FORMAT_HELP = "How to use the hex format: \n" +
            "Use the '&' character followed by the code related to the style you want to apply. \n" +
            "&a- Color codes &r\n" +
            "&0'0' for the color Black &r\n" +
            "&1'1' for the color Dark Blue &r\n" +
            "&2'2' for the color Dark Green &r\n" +
            "&3'3' for the color Dark Aqua &r\n" +
            "&4'4' for the color Dark Red &r\n" +
            "&5'5' for the color Dark Purple &r\n" +
            "&6'6' for the color Gold &r\n" +
            "&7'7' for the color Gray &r\n" +
            "&8'8' for the color Dark Gray &r\n" +
            "&9'9' for the color Blue &r\n" +
            "&a'a' for the color Green &r\n" +
            "&b'b' for the color Aqua &r\n" +
            "&c'c' for the color Red &r\n" +
            "&d'd' for the color Light Purple &r\n" +
            "&e'e' for the color Yellow &r\n" +
            "&f'f' for the color White &r\n" +
            "&a- Style codes &r\n" +
            "&k'k' for the style Obfuscated &r\n" +
            "&l'l' for the style Bold &r\n" +
            "&m'm' for the style Strikethrough &r\n" +
            "&n'n' for the style Underline &r\n" +
            "&o'o' for the style Italic &r\n" +
            "&r'r' to reset all styles &r\n";

    @Override
    public String getCommandUsage() {
        return "/colors <get|help> OR /colors set <hex|xml>";
    }

    @Override
    public List<String> getAliases() {        
        return Arrays.asList("colors", "clrs", "chatcolors");
    }

    @Override
    public SpongeCommandResult executeCommand(CommandSource src, String[] args) {
        Methods.sendPlayerMessage(src, StyledText.parseString(FORMAT_HELP));
        return SpongeCommandResult.SUCCESS;
    }

    @Override
    public Optional<Text> getShortDescription(CommandSource source) {
        return Optional.of(Text.of("Display the colors chart."));
    }

}
