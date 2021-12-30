package cn.edu.nju.net.protocol;

import cn.edu.nju.GameLogic.GameControl;
import cn.edu.nju.entity.Bullet;
import cn.edu.nju.net.Client;
import cn.edu.nju.utils.Direction;

public class BulletNewMsg implements Message{
    private String msg;
    public BulletNewMsg(String msg){
        this.msg = msg;
    }
    @Override
    public void decode() {
        String[] tokens = msg.split("_");
        int id = Integer.parseInt(tokens[0]);
        if(id == Client.getInstance().clientID)return;
        int xPos = Integer.parseInt(tokens[2]);
        int yPos = Integer.parseInt(tokens[3]);
        Direction dir = Direction.LEFT;
        switch(Integer.parseInt(tokens[4].trim())){
            case 0:
                dir = Direction.LEFT;
                break;
            case 1:
                dir = Direction.RIGHT;
                break;
            case 2:
                dir = Direction.UP;
                break;
            case 3:
                dir = Direction.DOWN;
                break;
        }
        Bullet b = new Bullet(Integer.parseInt(tokens[5].trim()), dir, xPos, yPos, id+"", GameControl.getMap());
        GameControl.getBullets().add(b);
    }
    
}
