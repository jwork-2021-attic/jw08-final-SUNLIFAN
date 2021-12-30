package cn.edu.nju.net.protocol;

import cn.edu.nju.GameLogic.GameControl;
import cn.edu.nju.entity.Player;
import cn.edu.nju.net.Client;
import cn.edu.nju.utils.Direction;

public class CreatureNewMsg implements Message{
    private String msg;
    public CreatureNewMsg(String msg){
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
        System.out.print(tokens[4]);
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
        Player player = new Player(xPos, yPos, GameControl.getMap(), GameControl.getBullets(), id);
        player.setDirection(dir);
        GameControl.getOtherPlayers().put(id, player);
        Client.getInstance().sendMsg(Client.getInstance().clientID + "_" + Message.CREATURE_ALREADY_EXISTS_MSG + "_" + GameControl.getPlayer().getXPos() +
        "_" + GameControl.getPlayer().getYPos() + "_" + GameControl.getPlayer().dir.ordinal() +"_" + GameControl.getPlayer().getHealth());
    }
    
}
