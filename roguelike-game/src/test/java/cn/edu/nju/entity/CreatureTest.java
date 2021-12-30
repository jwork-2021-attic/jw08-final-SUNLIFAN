package cn.edu.nju.entity;

import static org.junit.Assert.assertEquals;

import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import cn.edu.nju.resources.MapData;
import cn.edu.nju.scene.Map;
import cn.edu.nju.utils.Direction;

public class CreatureTest {
    private Creature creature;
    private Map map;

    @Before
    public void init(){
        map = new Map(MapData.MAP_DATA);
        creature = new Creature("creature4test", 2, 1, map, new Vector<Bullet>(), 0);
    }

    @Test
    public void getterAndSetterTest(){
        assertEquals("creature4test", creature.getName());
        assertEquals( 10 ,creature.getMaxHealth());
        assertEquals(10 , creature.getHealth());
        assertEquals(3 , creature.getStrength());
        assertEquals(true, creature.isAlive());
        assertEquals(Direction.LEFT, creature.dir);
        creature.setDirection(Direction.DOWN);
        assertEquals(Direction.DOWN, creature.dir);
        assertEquals(2, creature.getXPos());
        assertEquals(1, creature.getYPos());
    }

    @Test
    public void damageTest(){
        creature.damage(5);
        assertEquals(7 , creature.getHealth());
        assertEquals(10 , creature.getMaxHealth());
        assert(creature.isAlive());
        creature.damage(10);
        assertEquals(0, creature.getHealth());
        assert(!creature.isAlive());
    }

    @Test
    public void moveTest(){
        creature.move();
        assertEquals(2 , creature.getXPos());
        assertEquals(1 , creature.getYPos());
        creature.setDirection(Direction.RIGHT);
        creature.move();
        assertEquals(2 , creature.getXPos());
        assertEquals(2 , creature.getYPos());
        creature.setDirection(Direction.UP);
        creature.move();
        assertEquals(1 , creature.getXPos());
        assertEquals(2 , creature.getYPos());
        creature.setDirection(Direction.DOWN);
        creature.move();
        assertEquals(2 , creature.getXPos());
        assertEquals(2 , creature.getYPos());
    }

    @Test
    public void fireTest(){
        assertEquals(0, creature.bullets.size());
        creature.fire(creature.dir);
        assertEquals(1, creature.bullets.size());
        for(int i = 0; i < 200; i ++)creature.fire(creature.dir);
        assertEquals(201, creature.bullets.size());
    }
}
