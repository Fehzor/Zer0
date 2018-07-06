/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bot.Commands.Implementation;

import Bot.Commands.Command;
import Bot.Commands.CommandParser;
import Bot.Fields.Faction;
import Bot.Fields.UserData;
import Bot.Launcher;
import static Bot.SuperRandom.oRan;
import java.util.ArrayList;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;

/**
 *
 * @author FF6EB4
 */
public class SwitchFaction extends Command{
    
    public SwitchFaction(){
        this.category = 1;
        this.signature = new String[]{"!faction"};
        this.description = "Chooses a faction";
    }
    
    public void execute(String params, long ID){
        try{
        Faction F = Faction.getFaction(params.toLowerCase());
        
        UserData UD = UserData.getUD(ID);
        UD.role.writeData(F.role.getLongID());
        
        IUser IU = Launcher.client.fetchUser(ID);
        
        
        ArrayList<IRole> factions = new ArrayList<>();
        for(IRole IR : IU.getRolesForGuild(Launcher.client.getGuilds().get(0))){
            if(Faction.isFaction(IR.getLongID())){
                factions.add(IR);
            }
        }
        while(factions.size() > 0){
            IU.removeRole(factions.remove(0));
        }
        
        IU.addRole(Launcher.client.getRoleByID(UD.role.getData()));
        } catch (NullPointerException NPE){
            Launcher.PM("Faction not found!", ID);
        }
    }
}