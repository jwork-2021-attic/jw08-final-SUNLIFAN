package cn.edu.nju.net.protocol;

import cn.edu.nju.GameLogic.GameControl;
import cn.edu.nju.entity.Monster;
import cn.edu.nju.entity.Monster.Type;
import cn.edu.nju.net.Client;
import cn.edu.nju.utils.Direction;

public class MonsterNewMsg implements Message {
    //format : ClientId_msgType_x_y_dir_typeIndex_monsterId
    private String msg;
    public MonsterNewMsg(String msg){
        this.msg = msg.trim();
    }
    @Override
    public void decode() {
        String[] tokens = msg.split("_");
        int id = Integer.parseInt(tokens[0]);
        if(id == Client.getInstance().clientID)return;
        int xPos = Integer.parseInt(tokens[2]);
        int yPos = Integer.parseInt(tokens[3]);
        Direction dir = Direction.values()[Integer.parseInt(tokens[4])];
        Type type = Type.values()[Integer.parseInt(tokens[5])];
        Monster m = new Monster(type, xPos, yPos, GameControl.getMap(), GameControl.getBullets(), Integer.parseInt(tokens[6]));
        m.setDirection(dir);
        GameControl.monsters.add(m);
    }
    
}
