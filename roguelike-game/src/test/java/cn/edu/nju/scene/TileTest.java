package cn.edu.nju.scene;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


import org.junit.Before;
import org.junit.Test;

import cn.edu.nju.entity.Creature;
import cn.edu.nju.resources.MapData;

public class TileTest {
    public Tile tile;
    public Map map;

    @Before
    public void init(){
        map = new Map(MapData.MAP_DATA);
        tile = map.getTile(2, 2);
    }

    @Test
    public void setCreatureTest(){
        assertNull(tile.getCreature());
        assert(tile.isAvailable());
        Creature c = new Creature("player", 2, 2, map, null, 0);
        assertNotNull(tile.getCreature());
        assert(!tile.isAvailable());
        tile.setCreature(null);
        assertNull(tile.getCreature());
        assert(tile.isAvailable());
        tile.setCreature(c);
        assertEquals(c, tile.getCreature());
        assert(!tile.isAvailable());
    }

    @Test
    public void positionGetterTest(){
        assert(tile.getXPos() == 2 && tile.getYPos() == 2);
    }

    @Test
    public void nameGetterTest(){
        assertEquals("floor", tile.getName());
    }
}
