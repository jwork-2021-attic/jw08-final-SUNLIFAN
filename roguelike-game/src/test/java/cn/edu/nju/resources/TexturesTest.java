package cn.edu.nju.resources;

import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;


public class TexturesTest {
    @Before
    public void init(){
        Textures.init();
    }

    @Test
    public void initTest() throws FileNotFoundException{
        assertNotNull(Textures.getSprite("bat"));
        assertNotNull(Textures.getSprite("bullet"));
        assertNotNull(Textures.getSprite("player"));
        assertNotNull(Textures.getSprite("torch"));
    }

    @Test(expected = FileNotFoundException.class)
    public void exceptionInInit() throws FileNotFoundException {
        Textures.getSprite("creature");
    }
}
