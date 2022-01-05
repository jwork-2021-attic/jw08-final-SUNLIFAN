package cn.edu.nju.scene;

import java.io.Serializable;

import cn.edu.nju.utils.Direction;

public class Map implements Serializable{
    private int width;
    private int height;
    private Tile[][] map;

    public Map(String[] mapData){
        this.width = mapData[0].length();
        this.height = mapData.length;
        map = new Tile[this.height][this.width];

        for(int x = 0; x < height; x++)
            for(int y = 0; y < width; y ++){
                switch(mapData[x].charAt(y)){
                    case '#':
                        map[x][y] = new Tile("wall",x,y,null);
                        break;
                    case '.':
                        map[x][y] = new Tile("floor",x,y,null);
                        break;
                    case '^':
                        map[x][y] = new Tile("stairs",x,y,null);
                        break;
                    case 'c':
                        map[x][y] = new Tile("chest",x,y,null);
                        break;
                    case 'd':
                        map[x][y] = new Tile("drawer", x, y, null);
                        break;
                    case 'l':
                        map[x][y] = new Tile("locked_door", x, y, null);
                        break;
                    case 'k':
                        map[x][y] = new Tile("key", x, y, null);
                        break;
                }
            }
    }

    public int getWidth(){return width;}

    public int getHeight(){return height;}

    public Tile getTile(int x,int y){return map[x][y];}

    public Tile getNeighborTile(int x,int y, Direction dir){
        int resX = x;
        int resY = y;

        switch(dir){
            case LEFT:
                resY = y - 1;
                break;
            case RIGHT:
                resY = y + 1;
                break;
            case UP:
                resX = x - 1;
                break;
            case DOWN:
                resX = x + 1;
                break;
        }

        if((resX < 0) || (resX >= height) || (resY < 0) || (resY >= width))return null;
        return map[resX][resY];
    }
}
