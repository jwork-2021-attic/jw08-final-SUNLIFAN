package cn.edu.nju.scene;

import java.io.Serializable;

import cn.edu.nju.entity.Creature;
import cn.edu.nju.entity.Player;
import cn.edu.nju.net.Client;
import cn.edu.nju.utils.Strengthen;

public class Tile implements Serializable{
    public String name;
    private int xPos;
    private int yPos;
    private boolean isOpen;//valid when this tile is a chest

    private Creature creature;
    private boolean available;

    public Tile(String name, int x,int y, Creature c){
        this.xPos = x;
        this.yPos = y;
        this.name = name;
        this.creature = c;
        if(this.name.equals("floor") && (this.creature == null))this.available = true;
        else this.available = false;
    }
    public boolean isOpen(){return this.isOpen;}

    public String getName(){return this.name;}
    
    public boolean isAvailable(){return this.available;}
    
    public int getXPos(){return this.xPos;}
    
    public int getYPos(){return this.yPos;}

    public synchronized void setCreature(Creature c){
        if(c == null){
            this.available = true;
            creature = null;
        }
        else if(available){
            this.available = false;
            this.creature = c;
        }
        return;
    }

    public Creature getCreature(){return this.creature;}


    @Override
    public boolean equals(Object o){
        Tile t = (Tile) o;
        return t.xPos == xPos && t.yPos == yPos && t.creature == creature;
    }

    //valid only when this tile is a chest
    public void open(Creature c){
        if(!name.equals("chest") || isOpen)return;
        isOpen = true;
        this.name = "open_chest";
        Strengthen type = Strengthen.next();
        c.getStrengthened(type);
    }

    //valid only when this tile is a drawer
    public void openDrawer(){
        if(isOpen)return;
        isOpen = true;
        Client.gold += 2;
    }

    public void takeKey(Player player){
        if(!name.equals("key"))return;
        player.getKey = true;
        name = "table";
    }

    public void doorOpen(){
        if(!name.equals("locked_door"))return;
        name = "stairs";
    }
    
}
