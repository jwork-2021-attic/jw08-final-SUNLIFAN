package cn.edu.nju.entity;

import java.util.List;

import cn.edu.nju.net.Client;
import cn.edu.nju.scene.Map;
import cn.edu.nju.scene.Tile;

public class Player extends Creature{
    public int id;
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
        }
        
    }
}
