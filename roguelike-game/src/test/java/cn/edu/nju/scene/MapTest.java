package cn.edu.nju.scene;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import cn.edu.nju.resources.MapData;
import cn.edu.nju.utils.Direction;

public class MapTest {
    private Map map;

    @Before
    public void init(){
        this.map = new Map(MapData.MAP_DATA);
    }

    @Test
    public void sizeGetterTest(){
        assert(map.getWidth() == MapData.MAP_DATA[0].length());
        assert(map.getHeight() == MapData.MAP_DATA.length);
    }

    @Test
    public void neighborTileGetterTest(){
        //get tile via getter
        Tile upwardTile = map.getNeighborTile(1, 1, Direction.UP);
        Tile downwardTile = map.getNeighborTile(1, 1, Direction.DOWN);
        Tile leftwardTile = map.getNeighborTile(1, 1, Direction.LEFT);
        Tile rightwardTile = map.getNeighborTile(1, 1, Direction.RIGHT);
        //generate simulated expected tile
        Tile upExpected = new Tile("wall", 0, 1, null);
        Tile downExpected = new Tile("floor", 2, 1, null);
        Tile leftExpected = new Tile("wall", 1, 0, null);
        Tile rightExpected = new Tile("floor", 1, 2, null);
        //perform testing
        assertEquals(upExpected, upwardTile);
        assertEquals(downExpected, downwardTile);
        assertEquals(leftExpected, leftwardTile);
        assertEquals(rightExpected, rightwardTile);
    }
}
