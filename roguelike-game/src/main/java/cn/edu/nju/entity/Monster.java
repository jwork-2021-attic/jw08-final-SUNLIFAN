package cn.edu.nju.entity;

import java.util.List;

import cn.edu.nju.scene.Map;
import cn.edu.nju.scene.Tile;
import cn.edu.nju.utils.Direction;

public class Monster extends Creature{

    private Type type;
    public Monster(Type type, int x, int y, Map map, List<Bullet> bullets, int id) {
        super(type.getName(), x, y, map, bullets, -1);
        this.type = type;
        this.strength = type.getStr();
        this.defence = type.getDef();
        this.maxHealth = type.getHp();
        this.health = type.getHp();
        this.id = id;
    }

    public Type getType(){return this.type;}

    public enum Type {
        BAT("bat", 20, 2, 0),
        RAT("rat", 25, 2, 0),
        GHOST("ghost", 30, 3, 1);
        
        private String name;
        private int hp;
        private int str;
        private int def;
        
        Type(String name, int hp, int str, int def) {
            this.name = name;
            this.hp = hp;
            this.str = str;
            this.def = def;
        }

        public String getName() {
            return name;
        }

        public int getHp() {
            return hp;
        }

        public int getStr() {
            return str;
        }

        public int getDef() {
            return def;
        }
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
        }else if(neighborTile != null && !neighborTile.isAvailable()){
            dir = Direction.values()[(dir.ordinal()+1)%Direction.values().length];
        }
        
    }
    
}
