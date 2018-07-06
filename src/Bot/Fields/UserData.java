package Bot.Fields;

import Bot.Fields.Field;
import Bot.Launcher;
import static Bot.SuperRandom.oRan;
import com.vdurmont.emoji.Emoji;
import java.awt.Color;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.RoleBuilder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author FF6EB4
 */
public class UserData {
    public static Field<ArrayList<String>> IDList = new Field<>("USERDATA","IDLIST",new ArrayList<>());
    private static HashMap<String,UserData> UserList = new HashMap<>();
    
    public static UserData getUD(IUser user){
        if(!UserList.containsKey(user.getStringID())){
            if(!IDList.getData().contains(user.getStringID())){
                IDList.getData().add(user.getStringID());
            }
            UserList.put(user.getStringID(),new UserData(user));
        }
        
        IDList.writeData(IDList.getData());
        
        //if(UserList.get(user.getID()).name.equals("Clint Eastwood's Character")){
        //    UserList.get(user.getID()).name = user.getName();
        //}
        
        return UserList.get(user.getStringID());
    }
    
    public static UserData getUD(long ID){
        return getUD(Launcher.client.getUserByID(ID));
    }
    
    
    public String name = "Nameless Hero Of Legend";
    public String ID = "00002";
    
    public Field<Long> role;
    public Field<Long> lastMessage;
    
    
    public UserData(IUser user){
        instantiateFields(user);
    }
    
    public String toString(){
        String s = "**"+name+"**\n\n";
        
        //s+="Faction: "+faction;
        
        
        
        return s;
    }
    
    private void instantiateFields(IUser user){
        
        this.name = user.getName();
        this.ID = user.getStringID();
        
        IUser IU = Launcher.client.getUserByID(user.getLongID());
        ArrayList<IRole> factions = new ArrayList<>();
        for(IRole IR : IU.getRolesForGuild(Launcher.client.getGuilds().get(0))){
            if(Faction.isFaction(IR.getLongID())){
                factions.add(IR);
            }
        }
        while(factions.size() > 0){
            IU.removeRole(factions.remove(0));
        }
        
        
        role = new Field<>(this.ID,"role",Faction.getFaction("factionless").role.getLongID());
        
        IRole theRole = Launcher.client.getRoleByID(role.getData());
        user.addRole(theRole);
        
        this.lastMessage = new Field<>(this.ID,"lastMessage",0L);
    }
}
