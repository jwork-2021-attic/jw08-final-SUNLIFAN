package cn.edu.nju.entity;
import java.util.concurrent.TimeUnit;

import cn.edu.nju.GameLogic.GameControl;
import cn.edu.nju.net.Client;
import cn.edu.nju.net.protocol.Message;
import cn.edu.nju.utils.Direction;
public class MonsterGenerator implements Runnable {
    private static final int INTERVAL = 5000;
    private static final int START_TIME = 20000;
    private int typeIndex = 0;
    private int xPos;
    private int yPos;
    private static int id = -1;
    public MonsterGenerator(int x, int y){
        this.xPos = x;
        this.yPos = y;
    }
    @Override
    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(START_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while(true){
            if(GameControl.monsters.size() >= 20)continue;
            try {
                TimeUnit.MILLISECONDS.sleep(INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Monster m = new Monster(Monster.Type.values()[typeIndex], xPos, yPos, GameControl.getMap(), GameControl.getBullets(), id);
            m.setDirection(Direction.UP);
            GameControl.monsters.add(m);
            String msg = Client.getInstance().clientID + "_" + Message.MONSTER_NEW_MSG + "_" + xPos + "_" +
            yPos + "_" + m.dir.ordinal() +"_" + typeIndex + "_" + id;
            Client.getInstance().sendMsg(msg);
            typeIndex++;
            typeIndex %= 3;
            id--;
        }
    }
    
}
