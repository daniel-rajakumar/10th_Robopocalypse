package com.daniel.robopocalypse.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 * inputHandler
 */
public class inputHandler implements KeyListener {

    public inputHandler(robopocalypseApp game){//syncing the main file here
        game.addKeyListener(this);
    }

    public class Key {
        private int numTimesPressed = 0;
        private boolean pressed = false;//setting the default key as false

        public boolean isPressed(){//if the key is pressed then activate the stuffs we gave to java 
            return pressed;
        }
        
        public void toggle(boolean isPressed){//toggle is like on\off button
            pressed = isPressed;//whatever we put here automatically transfer to "pressed" value
            if(isPressed)numTimesPressed++;
        }
    }
    
   // public List<Key>keys = new ArrayList<Key>();
    //setting up the keys --> --V
    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();
    //public Key up = new Key();
    public Key up2 = new Key();
    public Key down2 = new Key();
    public Key left2 = new Key();
    public Key right2 = new Key();
    
    public Key space = new Key();
    
    public void keyTyped(KeyEvent e) {
    }
    
    public void keyPressed(KeyEvent e) {
        toggleKey(e.getKeyCode(), true);//if the key is pressed then activate
    }
    
    public void keyReleased(KeyEvent e) {
        toggleKey(e.getKeyCode(), false);//if the key released then disactivate(go to default function)
    }

    public void toggleKey(int keyCode, boolean isPressed){//setting up the key's method
        if ((keyCode == KeyEvent.VK_UP)){
            up.toggle(isPressed);
        }
        if ((keyCode == KeyEvent.VK_DOWN)){
            down.toggle(isPressed);
        }
        if ((keyCode == KeyEvent.VK_LEFT)){
            left.toggle(isPressed);
        }
        if ((keyCode == KeyEvent.VK_RIGHT)){
            right.toggle(isPressed);
        }
        
        if ((keyCode == KeyEvent.VK_SPACE)){
            space.toggle(isPressed);
        }

        if ((keyCode == KeyEvent.VK_W)){
            up2.toggle(isPressed);
        }
        if ((keyCode == KeyEvent.VK_S)){
            down2.toggle(isPressed);
        }
        if ((keyCode == KeyEvent.VK_A)){
            left2.toggle(isPressed);
        }
        if ((keyCode == KeyEvent.VK_D)){
            right2.toggle(isPressed);
        }
    }
}
