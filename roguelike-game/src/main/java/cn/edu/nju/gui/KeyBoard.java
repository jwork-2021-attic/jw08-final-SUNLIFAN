package cn.edu.nju.gui;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoard implements KeyListener{

    public volatile boolean enter_be_pressed = false;
    @Override
    public void keyTyped(KeyEvent e) {
        
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER)enter_be_pressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
}
