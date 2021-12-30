package cn.edu.nju.net.protocol;

import cn.edu.nju.GameLogic.GameControl;
import cn.edu.nju.net.Client;

public class CreatureDamagedMsg implements Message{

    private String msg;
    public CreatureDamagedMsg(String msg){
        this.msg = msg;
    }
    @Override
    public void decode() {
        String[] tokens = msg.split("_");
        int id = Integer.parseInt(tokens[0]);
        if(id == Client.getInstance().clientID)return;
        int xPos = Integer.parseInt(tokens[2]);
        int yPos = Integer.parseInt(tokens[3]);
        GameControl.getMap().getTile(xPos, yPos).getCreature().damage(Integer.parseInt(tokens[5].trim()));
    } 
    
}
