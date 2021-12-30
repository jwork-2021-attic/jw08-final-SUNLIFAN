package cn.edu.nju.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import cn.edu.nju.net.Client;
import cn.edu.nju.resources.MapData;
import cn.edu.nju.scene.Map;

public class PlayerTest {
    private Player player;
    private Map map;
    @Before
    public void setUp(){
        map = new Map(MapData.MAP_DATA);
        player = new Player(1, 1, map, null, Client.getInstance().clientID);
    }

    @Test
    public void playerTest(){
        assertEquals("player", player.getName());
    }
}
