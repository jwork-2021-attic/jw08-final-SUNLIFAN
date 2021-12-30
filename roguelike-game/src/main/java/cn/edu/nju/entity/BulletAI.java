package cn.edu.nju.entity;

import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.edu.nju.GameLogic.GameControl;


public class BulletAI implements Runnable{
    private List<Bullet> bullets;

    public BulletAI(List<Bullet> bullets){
        this.bullets = bullets;
    }

    @Override
    public void run() {
        while(true){
            if(!GameControl.gameState)break;
            for(int i = 0; i < bullets.size(); i ++){
                Bullet b = bullets.get(i);
                b.forward();
            }

            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                System.out.println("[BulletAI]:Exceptions in BulletAI!!!");
            }
        }    
    }

    
    
}
