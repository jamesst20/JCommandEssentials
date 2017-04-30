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
package com.jamesst20.jcommandessentials.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Stack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Text.Builder;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.text.chat.ChatType;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyle;
import org.spongepowered.api.text.format.TextStyles;

public class StyledText implements MessageChannel{    
    
    private static class Node{
        public TextStyle Style;
        public Node Next;
    }
    
    private static class StyleStack{
        private Node firstNode = null;
        private int size = 0;
        
        public void push(TextStyle style){
            Node node = new Node();
            node.Style = style;  
            
            if(style != TextStyles.RESET){
                node.Next = firstNode;                 
            }           
            
            firstNode = node;
            size++;
        }
                
        public void pop(){            
            if(firstNode != null){
                firstNode = firstNode.Next;
                size--;     
            }                 
        }
        
        public TextStyle[] getAll(){            
            if(size <= 1) return new TextStyle[] {TextStyles.RESET};
            
            int index = 0;
            Node node = firstNode;
            TextStyle[] styles = new TextStyle[size - 1]; 
            
            while (node.Next != null){
                styles[index] = node.Style;
                node = node.Next; 
                index++;
            }
            
            return styles;
        }
    }
        
    @Override
    public Optional<Text> transformMessage(Object sender, MessageReceiver recipient, Text original, ChatType type) {
        String text = original.toPlain();
        Text newText = parseStringOld(text);
        return Optional.of(newText);
    }

    @Override
    public Collection<MessageReceiver> getMembers() {
        return Collections.emptyList();
    }
    
    public static Text parseString(String text){     
        Builder textParts = Text.builder();   
        Stack<TextColor> colors = new Stack<>();
        StyleStack styles= new StyleStack();
        String[] parts = text.split("<");
        
        colors.push(TextColors.NONE);
        styles.push(TextStyles.RESET);
        
        boolean firstPart = true;
        
        for(String part : parts){
             char c = (part.length() > 0) ? part.charAt(0) : '?';
             char c2 = (part.length() > 1) ? part.charAt(1) : '?';
             
             TextStyle style = (Character.isUpperCase(c)) ? tagToStyle(String.valueOf(c)) : null;
             TextColor color = (c2 != '?') ? tagToColor(part.substring(0, 2)) : null;
            
             if(style != null){
                 part = part.replaceFirst(String.valueOf(c), "");
                 styles.push(style);
                
             } else if(color != null) {   
                 part = part.replaceFirst(String.valueOf(c) + String.valueOf(c2), "");
                 colors.add(color);
                 
             } else {
                 part = (firstPart) ? part : "<" + part;
                 firstPart = false;       
             }
             
             String[] innerParts = part.split(">");
             boolean firstInnerPart = true;
             
             for(String innerPart : innerParts){
             	char innerC = (innerPart.length() > 0) ? innerPart.charAt(innerPart.length() - 1) : '?';
             	
                 if(innerC == '/'){  
                     textParts.append(Text.builder(innerPart.replace("/", "")).color(colors.peek()).style(styles.getAll()).build());
                     if(colors.size() > 1) colors.pop();
             		 
                 } else if(innerC == '\\'){
                     textParts.append(Text.builder(innerPart.replace("\\", "")).color(colors.peek()).style(styles.getAll()).build());              
                     styles.pop();
                 
                 } else {
                     innerPart = (firstInnerPart) ? innerPart : ">" + innerPart;
                     firstInnerPart = false;        
                     textParts.append(Text.builder(innerPart).color(colors.peek()).style(styles.getAll()).build()); 
                 }
             }
        }
        
        return textParts.build();
    }
    
    public static Text parseStringOld(String text){
        Builder textParts = Text.builder();
        TextStyle styles = TextStyles.RESET;
        TextColor currentColor = TextColors.NONE;
        
        //styles.push(TextStyles.RESET);
        
        String[] parts = text.split("&");
        boolean firstPart = true;
        
        for(String part : parts){
            String c = (part.length() != 0) ? String.valueOf(part.charAt(0)) : "?";
            
             TextStyle style = tagToStyle(c);
             TextColor color = tagToColor(c);            
            
            if(style != null){
                if(style == TextStyles.BOLD){
                    styles = styles.bold(true);
                } else if(style == TextStyles.ITALIC){
                    styles = styles.italic(true);
                } else if(style == TextStyles.UNDERLINE){
                    styles = styles.italic(true);
                } else if(style == TextStyles.STRIKETHROUGH){
                    styles = styles.italic(true);
                } else if(style == TextStyles.OBFUSCATED){
                    styles = styles.italic(true);
                } else if(style == TextStyles.RESET){
                    styles = style;
                }
                textParts.append(Text.builder(part.replaceFirst(String.valueOf(c), "")).color(currentColor).style(styles).build());     
                
            } else if (color != null){
                currentColor = color;        
                textParts.append(Text.builder(part.replaceFirst(String.valueOf(c), "")).color(currentColor).style(styles).build()); 
                
            } else {
                textParts.append(Text.builder((firstPart ? "" : "&") + part).color(currentColor).style(styles).build());
                firstPart = false;
            }
        }
        return textParts.build();
    }
    
    private static TextColor tagToColor(String tag){
        switch (tag){
            case "0":
            case "bk": return TextColors.BLACK;
            case "1":
            case "db": return TextColors.DARK_BLUE;
            case "2":
            case "dg": return TextColors.DARK_GREEN;
            case "3":
            case "da": return TextColors.DARK_AQUA;
            case "4":
            case "dr": return TextColors.DARK_RED;
            case "5":
            case "dp": return TextColors.DARK_PURPLE;
            case "6":
            case "gd": return TextColors.GOLD;
            case "7":
            case "gr": return TextColors.GRAY;
            case "8":
            case "dk": return TextColors.DARK_GRAY;
            case "9":
            case "be": return TextColors.BLUE;
            case "a":
            case "gn": return TextColors.GREEN;
            case "b":
            case "aq": return TextColors.AQUA;
            case "c":
            case "rd": return TextColors.RED;
            case "d":
            case "lp": return TextColors.LIGHT_PURPLE;
            case "e":
            case "yl": return TextColors.YELLOW;
            case "f":
            case "wh": return TextColors.WHITE;
            case "g":
            case "nn": return TextColors.NONE;
            default: return null;
        }
    }
    private static TextStyle tagToStyle(String tag){
        switch (tag){
            case "k":
            case "O": return TextStyles.OBFUSCATED;
            case "l":
            case "B": return TextStyles.BOLD;
            case "m":
            case "S": return TextStyles.STRIKETHROUGH;
            case "n":
            case "U": return TextStyles.UNDERLINE;
            case "o":
            case "I": return TextStyles.ITALIC;
            case "r":
            case "R": return TextStyles.RESET;
            default: return null;
        }
    }
    
    private static boolean equalsAny(char subject, char... values){
        for(char value : values){
            if(subject == value) return true;
        }        
        return false;
    }
}
