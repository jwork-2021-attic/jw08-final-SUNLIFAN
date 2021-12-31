package cn.edu.nju.entity;


import java.util.Iterator;

import cn.edu.nju.GameLogic.GameControl;

import java.util.concurrent.TimeUnit;

public class MonsterAI implements Runnable {
    @Override
    public void run() {
        while(true){
        synchronized(GameControl.monsters){
        Iterator<Monster> iter = GameControl.monsters.iterator();
        while(iter.hasNext()){
            Monster m = iter.next();
            if(!m.isAlive())iter.remove();
            m.move();
        }
    }
        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
    
}
