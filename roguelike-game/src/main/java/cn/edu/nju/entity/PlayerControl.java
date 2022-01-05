package cn.edu.nju.entity;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import cn.edu.nju.GameLogic.GameControl;
import cn.edu.nju.gui.Window;
import cn.edu.nju.net.Client;
import cn.edu.nju.net.protocol.Message;
import cn.edu.nju.utils.Direction;

public class PlayerControl implements Runnable, KeyListener{
    
    private Player player;
    public volatile boolean active;

    public PlayerControl(Player player){
        this.player = player;
        Window.screen.addKeyListener(this);
        active = true;
    }

    @Override
    public void run() {
        while(active){
            if(!GameControl.gameState)break;
            if(!player.isAlive()){
                System.out.println("[Player Control:]player died");
                active = false;
                GameControl.gameState = false;
                GameControl.playerWin = false;
                break;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        String msg = null;
        switch(e.getKeyCode()){
            case KeyEvent.VK_W:
                player.setDirection(Direction.UP);
                player.move();
                msg = Client.getInstance().clientID + "_" + Message.CREATURE_MOVE_MSG + "_" + 
            player.xPos + "_" + player.yPos + "_" + player.dir.ordinal();
                break;
            case KeyEvent.VK_S:    
                player.setDirection(Direction.DOWN);
                player.move();
                msg = Client.getInstance().clientID + "_" + Message.CREATURE_MOVE_MSG + "_" + 
            player.xPos + "_" + player.yPos + "_" + player.dir.ordinal();
                break;
            case KeyEvent.VK_A: 
                player.setDirection(Direction.LEFT);
                player.move();
                msg = Client.getInstance().clientID + "_" + Message.CREATURE_MOVE_MSG + "_" + 
            player.xPos + "_" + player.yPos + "_" + player.dir.ordinal();
                break;
            case KeyEvent.VK_D:
                player.setDirection(Direction.RIGHT);
                player.move();
                msg = Client.getInstance().clientID + "_" + Message.CREATURE_MOVE_MSG + "_" + 
            player.xPos + "_" + player.yPos + "_" + player.dir.ordinal();
                break;
            case KeyEvent.VK_SPACE:
                player.fire(player.dir);
                msg = Client.getInstance().clientID + "_" + Message.BULLET_NEW_MSG + "_" + player.xPos + "_" + 
            player.yPos + "_" + player.dir.ordinal() + "_" + player.getStrength();
                break;    
        }
        Client.getInstance().sendMsg(msg);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        String msg = null;
        switch(e.getKeyCode()){
            case KeyEvent.VK_W:
                player.setDirection(Direction.UP);
                player.move();
                msg = Client.getInstance().clientID + "_" + Message.CREATURE_MOVE_MSG + "_" + 
            player.xPos + "_" + player.yPos + "_" + player.dir.ordinal();
                break;
            case KeyEvent.VK_S:    
                player.setDirection(Direction.DOWN);
                player.move();
                msg = Client.getInstance().clientID + "_" + Message.CREATURE_MOVE_MSG + "_" + 
            player.xPos + "_" + player.yPos + "_" + player.dir.ordinal();
                break;
            case KeyEvent.VK_A: 
                player.setDirection(Direction.LEFT);
                player.move();
                msg = Client.getInstance().clientID + "_" + Message.CREATURE_MOVE_MSG + "_" + 
            player.xPos + "_" + player.yPos + "_" + player.dir.ordinal();
                break;
            case KeyEvent.VK_D:
                player.setDirection(Direction.RIGHT);
                player.move();
                msg = Client.getInstance().clientID + "_" + Message.CREATURE_MOVE_MSG + "_" + 
            player.xPos + "_" + player.yPos + "_" + player.dir.ordinal();
                break;
            case KeyEvent.VK_SPACE:
                player.fire(player.dir);
                msg = Client.getInstance().clientID + "_" + Message.BULLET_NEW_MSG + "_" + player.xPos + "_" + 
            player.yPos + "_" + player.dir.ordinal() + "_" + player.getStrength();
                break;    
        }
        Client.getInstance().sendMsg(msg);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
}
