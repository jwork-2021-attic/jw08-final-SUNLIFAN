package cn.edu.nju.entity;

import java.util.List;

import cn.edu.nju.GameLogic.GameControl;
import cn.edu.nju.net.Client;
import cn.edu.nju.scene.Map;
import cn.edu.nju.scene.Tile;

public class Player extends Creature{
    public int id;
    public boolean getKey;
    public Player(int x, int y, Map map, List<Bullet> bullets, int id){
        super("player",x, y, map, bullets, id);
    }

    public synchronized void move(){
        Tile neighborTile = map.getNeighborTile(xPos, yPos, dir);
        int curX = xPos;
        int curY = yPos;
        switch(dir){
            case LEFT:   
                curY--;
                break;
            case RIGHT:   
                curY++;
                break;
            case UP:     
                curX--;
                break;
            case DOWN:    
                curX++;
                break;
        }

        if(neighborTile!=null && neighborTile.isAvailable()){
            Tile curTile = map.getTile(xPos, yPos);
            curTile.setCreature(null);
            xPos = curX;
            yPos = curY;
            neighborTile.setCreature(this);
        }else if(neighborTile != null && neighborTile.getName().equals("chest") && Client.gold >= 3){
            //open the chest to get some strengthen
            neighborTile.open(this);
            Client.gold -= 3;
        }else if(neighborTile != null && neighborTile.getName().equals("drawer")){
            neighborTile.openDrawer();
        }else if(neighborTile != null && neighborTile.getCreature()!= null && neighborTile.getCreature().id < 0){
            this.damage(neighborTile.getCreature().strength);
        }else if(neighborTile != null && neighborTile.getName().equals("key")){
            neighborTile.takeKey(this);
        }else if(neighborTile != null && neighborTile.getName().equals("locked_door")){
            if(getKey)neighborTile.doorOpen();
        }else if(neighborTile != null && neighborTile.getName().equals("stairs") && GameControl.getOtherPlayers().size() == 0){
            getKey = false;
            GameControl.playerWin = true;
            GameControl.gameState = false;
        }
        
    }


    public synchronized void damage(int amount){
        int leftDamage = amount - defence;
        if(leftDamage <= 0){
            defence -= amount;
            return;
        }else{
            defence = 0;
        }
        this.health -= leftDamage;
        if(this.health <= 0){
            health = 0;
            map.getTile(xPos, yPos).setCreature(null);
            this.alive = false;
            if(getKey)map.getTile(1, 49).name = "key";
        }
    }
}
