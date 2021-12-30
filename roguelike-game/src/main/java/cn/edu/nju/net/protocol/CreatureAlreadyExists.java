package cn.edu.nju.net.protocol;

import cn.edu.nju.GameLogic.GameControl;
import cn.edu.nju.entity.Player;
import cn.edu.nju.net.Client;
import cn.edu.nju.utils.Direction;

public class CreatureAlreadyExists implements Message{
    private String msg;
    public CreatureAlreadyExists(String msg){
        this.msg = msg;
    }
    @Override
    public void decode() {
        String[] tokens = msg.split("_");
        int id = Integer.parseInt(tokens[0]);
        if(id == Client.getInstance().clientID)return;
        if(GameControl.getOtherPlayers().get(id) != null)return;
        int xPos = Integer.parseInt(tokens[2]);
        int yPos = Integer.parseInt(tokens[3]);
        int health = Integer.parseInt(tokens[5].trim());
        Direction dir = Direction.LEFT;
        switch(Integer.parseInt(tokens[4])){
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
        Player player = new Player(xPos, yPos, GameControl.getMap(), GameControl.getBullets(), id);
        player.setDirection(dir);
        player.damage(player.getMaxHealth() - health);
        GameControl.getOtherPlayers().put(id, player);
    }
    
}
