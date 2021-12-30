package cn.edu.nju.entity;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import cn.edu.nju.GameLogic.GameControl;
import cn.edu.nju.utils.Direction;

public class BulletAITest {
    private BulletAI ai;
    @Before 
    public void setUp(){
        GameControl.initGame();
        GameControl.gameState = true;
        ai = new BulletAI(GameControl.getBullets());
    }

    private class task implements Runnable {

        @Override
        public void run() {
            try{
                TimeUnit.MILLISECONDS.sleep(5000);
                GameControl.gameState = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
        
    }
    @Test
    public void bulletAITest(){
        for(int i = 0; i < 20; i ++)
            GameControl.getPlayer().fire(Direction.DOWN);
        new Thread(ai).start();
        new Thread(new task()).start();
    }
}
