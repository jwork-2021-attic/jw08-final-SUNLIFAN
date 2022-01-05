package cn.edu.nju.entity;

import java.io.Serializable;
import java.util.List;

import cn.edu.nju.scene.Map;
import cn.edu.nju.scene.Tile;
import cn.edu.nju.utils.Direction;
import cn.edu.nju.utils.Strengthen;

public class Creature implements Serializable{
    public Map map;
    protected List<Bullet> bullets;

    protected String name;

    public Direction dir;
    public Direction facing;

    protected int maxHealth;
    protected int health;

    protected int strength;
    protected int defence;

    protected boolean alive;

    protected int xPos;
    protected int yPos;

    protected int id;
    public int getXPos(){return xPos;}

    public int getYPos(){return yPos;}

    public Creature(String name, int x, int y, Map map, List<Bullet> bullets, int id){
        this.name = name;
        this.xPos = x;
        this.yPos = y;
        this.maxHealth = 10;
        this.health = 10;
        this.strength = 3;
        this.defence = 2;
        this.facing = Direction.LEFT;
        this.dir = Direction.LEFT;
        this.alive = true;
        this.map = map;
        map.getTile(xPos, yPos).setCreature(this);
        this.bullets = bullets;
        this.id = id;
    }

    public int getDefence(){
        return this.defence;
    }
    public String getName(){return this.name;}

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
        }
    }

    public void setDirection(Direction dir){
        this.dir = dir;
    }

    public int getHealth(){return health;}

    public int getStrength(){return strength;}

    public int getMaxHealth(){return maxHealth;}
    
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
        }
        
    }

    public boolean isAlive(){return this.alive;}

    public void fire(Direction dir){
        if(bullets.size() <= 1000){
            bullets.add(new Bullet(strength,dir, xPos, yPos,id+"",map));
        }
        else System.out.println("Bullet list is full !");
    }

    public void getStrengthened(Strengthen type){
        switch(type){
            case MAX_HEALTH:
                this.maxHealth += 3;
                break;
            case HEALTH:
                this.health += 4;
                if(health >= maxHealth)health = maxHealth;
                break;
            case STRENGTH:
                this.strength += 1;
                break;
            case DEFENCE:
                this.defence += 2;
                break;
        }
    }

}
