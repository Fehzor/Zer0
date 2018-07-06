/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bot.Fields;

import Bot.Launcher;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import org.json.JSONObject;
import sx.blah.discord.handle.obj.IRole;

/**
 *
 * @author FF6EB4
 */
public class Faction {
    public String name = "Some Faction";
    public String description = "No one really knows these guys...";
    public IRole role;
    
    public Faction(){}
    
    public static HashMap<String, Faction> factions = new HashMap<>();
    public static HashMap<String, Faction> loadFactions(){
        try{
            JSONObject formdex;
            File F = new File("resources/factions.json");

            Scanner oScan = new Scanner(F);

            String get = "";

            while(oScan.hasNextLine()){
                get+=oScan.nextLine()+"\n";
            }
            formdex = new JSONObject(get);
            Iterator<?> keys = formdex.keys();
            HashMap<String,Faction> ret = new HashMap<>();
            
            while( keys.hasNext() ) {
                String key = (String)keys.next();
                JSONObject entry = (JSONObject)formdex.get(key);
                ret.put(key,loadForm(entry));
            }
            
            return ret;
        } catch (Exception E){E.printStackTrace();}
        
        return null;
    }
    
    private static Faction loadForm(JSONObject formula){
        Faction ret = new Faction();
        /*
        for (Object o : formula.getJSONArray("input")){
            String s = (String)o;
            ret.in.add(s);
        }
        */

        
        ret.name = formula.getString("name");
        
        ret.description = formula.getString("description");
        
        ret.role = Launcher.client.getRoleByID(formula.getLong("role"));
        
        return ret;
    }
    
    public static Faction getFaction(String roleName){
        if(factions.isEmpty()){
            factions = loadFactions();
        }
        return factions.get(roleName);
    }
    
    public static boolean isFaction(long ID){
        for(String F : factions.keySet()){
            long get = factions.get(F).role.getLongID();
            if(get == ID){
                return true;
            }
        }
        return false;
    }
}
