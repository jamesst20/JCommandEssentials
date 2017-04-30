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
    
    private final String XML_FORMAT_HELP = new StringBuilder("&a&nHow to use the xml format \n")
            .append("&r&gUse the '<' character followed by the code related to the style you want to apply and ")
            .append("use '/>' to go back to the last used color and '\\>' for the last used style.\n")
            .append("&a- Color codes &g&r \n")
            .append("'bk' for the color Black : \nA <bkgreat/> exemple gives A &0great&g exemple. \n")            
            .append("'db' for the color Dark Blue : \nA <dbgreat/> exemple gives A &1great&g exemple. \n")
            .append("'dg' for the color Dark Green : \nA <dggreat/> exemple gives A &2great&g exemple. \n")
            .append("'da' for the color Dark Aqua : \nA <dagreat/> exemple gives A &3great&g exemple. \n")
            .append("'dr' for the color Dark Red : \nA <drgreat/> exemple gives A &4great&g exemple. \n")
            .append("'dp' for the color Dark Purple : \nA <rdgreat/> exemple gives A &5great&g exemple. \n")
            .append("'gd' for the color Gold : \nA <rdgreat/> exemple gives A &6great&g exemple. \n")
            .append("'gr' for the color Gray : \nA <rdgreat/> exemple gives A &7great&g exemple. \n")
            .append("'dk' for the color Dark Gray : \nA <rdgreat/> exemple gives A &8great&g exemple. \n")
            .append("'bl' for the color Blue : \nA <rdgreat/> exemple gives A &9great&g exemple. \n")
            .append("'gn' for the color Green : \nA <rdgreat/> exemple gives A &agreat&g exemple. \n")
            .append("'aq' for the color Aqua : \nA <rdgreat/> exemple gives A &bgreat&g exemple. \n")
            .append("'rd' for the color Red : \nA <rdgreat/> exemple gives A &cgreat&g exemple. \n")
            .append("'lp' for the color Light Purple : \nA <rdgreat/> exemple gives A &dgreat&g exemple. \n")
            .append("'yl' for the color Yellow : \nA <rdgreat/> exemple gives A &egreat&g exemple. \n")
            .append("'wh' for the color White : \nA <rdgreat/> exemple gives A &fgreat&g exemple. \n")
            .append("'nn' for the color None : \n<rdA <nngreat/> exemple gives &cA &ggreat&c exemple. \n")
            .append("&a&l- Style codes &g&r\n")
            .append("'O' for the style Obfuscated : \nA <Ogreat\\> exemple gives A &kgreat&r exemple. \n")
            .append("'B' for the style Bold : \nA <Bgreat\\> exemple gives A &lgreat&r exemple. \n")
            .append("'S' for the style Strikethrough : \nA <Sgreat\\> exemple gives A &mgreat&r exemple. \n")
            .append("'U' for the style Underline : \nA <Ugreat\\> exemple gives A &ngreat&r exemple. \n")
            .append("'I' for the style Italic : \nA <Igreat\\> exemple gives A &ogreat&r exemple. \n")
            .append("'R' to reset all styles : \n<UA <Rgreat<U exemple gives &nA &rgreat&n exemple. \n").toString();
    
    private final String HEX_FORMAT_HELP = new StringBuilder("<gn<BHow to use the hex format\\>/> \n")
            .append("Use the '&' character followed by the code related to the style you want to apply. \n")
            .append("<gn- Color codes /> \n")
            .append("'0' for the color Black : \n&0A great exemple gives <bkA great exemple./> \n")            
            .append("'1' for the color Dark Blue : \n&1A great exemple gives <dbA great exemple./> \n")
            .append("'2' for the color Dark Green : \n&2A great exemple gives <dgA great exemple./> \n")
            .append("'3' for the color Dark Aqua : \n&3A great exemple gives <daA great exemple./> \n")
            .append("'4' for the color Dark Red : \n&4A great exemple gives <drA great exemple./> \n")
            .append("'5' for the color Dark Purple : \n&5A great exemple gives <dpA great exemple./> \n")
            .append("'6' for the color Gold : \n&6A great exemple gives <gdA great exemple./> \n")
            .append("'7' for the color Gray : \n&7A great exemple gives <grA great exemple./> \n")
            .append("'8' for the color Dark Gray : \n&8A great exemple gives <dkA great exemple./> \n")
            .append("'9' for the color Blue : \n&9A great exemple gives <blA great exemple./> \n")
            .append("'a' for the color Green : \n&aA great exemple gives <gnA great exemple./> \n")
            .append("'b' for the color Aqua : \n&bA great exemple gives <aqA great exemple./> \n")
            .append("'c' for the color Red : \n&cA great exemple gives <rdA great exemple./> \n")
            .append("'d' for the color Light Purple : \n&dA great exemple gives <lpA great exemple./> \n")
            .append("'e' for the color Yellow : \n&eA great exemple gives <ylA great exemple./> \n")
            .append("'f' for the color White : \n&fA great exemple gives <whA great exemple./> \n")
            .append("'g' for the color None : \n&cA &ggreat&c exemple gives <rdA <nngreat<rd exemple./> \n")
            .append("<gn- Style codes />\n")
            .append("'k' for the style Obfuscated : \n&kA great exemple gives <OA great exemple.\\> \n")
            .append("'l' for the style Bold : \n&lA great exemple gives <BA great exemple.\\> \n")
            .append("'m' for the style Strikethrough : \n&mA great exemple gives <SA great exemple.\\> \n")
            .append("'n' for the style Underline : \n&nA great exemple gives <UA great exemple.\\> \n")
            .append("'o' for the style Italic : \n&oA great exemple gives <IA great exemple.\\> \n")
            .append("'r' to reset all styles : \n&lA &rgreat&l exemple gives <BA <Rgreat<B exemple.\\> \n").toString();


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
        
        if(isSynthaxInvalid(args)) return SpongeCommandResult.INVALID_SYNTHAX;
        
        if(!Methods.hasPermission(src, "JCMDEss.commands.colors")) return SpongeCommandResult.NO_PERMISSION;
        
        if(src instanceof ConsoleSource) {
            Methods.sendPlayerMessage(src, Text.of(TextColors.RED, "Console doesn't have color support."));
        
        } else if (args[0].equals("get")) {
            Player player = (Player)src;
            String usedFormat = (xmlFormatUsers.containsKey(player.getName()) && xmlFormatUsers.get(player.getName())) ? "xml" : "hex";
                       
            Methods.sendPlayerMessage(src, "You are currently using the <gn" + usedFormat + "/> format. To learn how to use it, use the <gd/colors help/> command"); 
        
        } else if (args[0].equals("set")){
            Player player = (Player)src;
            boolean isXml = args[1].equals("xml");
            
            if(!xmlFormatUsers.containsKey(player.getName())){
                xmlFormatUsers.put(player.getName(), isXml);
            } else {
                xmlFormatUsers.replace(player.getName(), isXml);
            }
            
            Methods.sendPlayerMessage(src, "You are now using the <gn" + args[1] + "/> format. To learn how to use it, use the <gd/colors help/> command"); 
        } else {
            Player player = (Player)src;
            Text helpMsg = (xmlFormatUsers.containsKey(player.getName()) && xmlFormatUsers.get(player.getName())) 
                    ? StyledText.parseStringOld(XML_FORMAT_HELP)
                    : StyledText.parseString(HEX_FORMAT_HELP);                       
                        
            Methods.sendPlayerMessage(src, helpMsg); 
        }      
        
        return SpongeCommandResult.SUCCESS;
    }

    @Override
    public Optional<Text> getShortDescription(CommandSource source) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private boolean isSynthaxInvalid(String[] args){
        return (args.length == 0) ||
               (args.length == 1 && !args[0].equals("get") && !args[0].equals("help")) ||
               (args.length == 2 && !args[0].equals("set") && (!args[1].equals("xml") && !args[1].equals("hex"))) ||
               (args.length > 2);
    }    
}
