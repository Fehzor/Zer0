/*
 * Copyright (C) 2017 FF6EB4
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package Bot.Commands;


import Bot.Commands.Implementation.*;
import Bot.Launcher;
import java.util.ArrayList;
import java.util.HashMap;
import sx.blah.discord.handle.obj.IChannel;

/**
 *
 * @author FF6EB4
 */
public class CommandParser {
    public static HashMap<String,Command> commandList = loadCommands();
    public static IChannel channel;
    
    public static void parseCommand(String S, long ID){
        String[] split = S.split(" ",2);
        String signature = split[0];
        
        String args = "";
        try{
            args = split[1];
        } catch (Exception E){}
        
        if(commandList.containsKey(signature)){
            Command C = commandList.get(signature);
            try{
                C.execute(args,ID);
            } catch (Exception E){
                //System.err.println(UD.name+": "+signature+" "+args);
                //LOL.PM("Something went wrong with your command! Use !commands or ask for formatting help!",UD);
            }
        }
    }
    
    private static HashMap<String,Command> loadCommands(){
        HashMap<String,Command> ret = new HashMap<>();
        
        addCommand(ret, new SwitchFaction());
        
        return ret;
    }
    
    private static void addCommand(HashMap<String,Command> ret,Command com){
        for(String s : com.signature){
            ret.put(s,com);
        }
    }
    
    
}
