package com.jerry.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class KeyInputs implements KeyListener{
	
	public KeyInputs(Snake snake){
		
        snake.requestFocus();
        snake.addKeyListener(this);
        
	}

	public class Key{
		
        private boolean pressed=false;
		
        public boolean isPressed(){
        	
                return pressed;
                
        }

        public void toggle(boolean isPressed){
        	
                pressed = isPressed;
                
        }
      
	}

	public List<Key> keys=new ArrayList<Key>();

	public Key up=new Key();
	public Key down=new Key();
	public Key left=new Key();
	public Key right=new Key();
	//sets pressed to true and false when released
	public void keyPressed(KeyEvent e){
		
        toggleKey(e.getKeyCode(), true);
	}

	public void keyReleased(KeyEvent e){
		
        toggleKey(e.getKeyCode(), false);
	}

	public void keyTyped(KeyEvent e){

	}
	//changes pressed when using wasd or directional keys
	public void toggleKey(int KeyCode, boolean isPressed){
		
        if (KeyCode == KeyEvent.VK_UP||KeyCode == KeyEvent.VK_W){
        	
                up.toggle(isPressed);
                
        }
        
        if (KeyCode == KeyEvent.VK_DOWN||KeyCode == KeyEvent.VK_S){
        	
                down.toggle(isPressed);
        }
        
        if (KeyCode == KeyEvent.VK_LEFT||KeyCode == KeyEvent.VK_A){
        	
                left.toggle(isPressed);
                
        }
        
        if (KeyCode == KeyEvent.VK_RIGHT||KeyCode == KeyEvent.VK_D){
        	
                right.toggle(isPressed);
                
        }
        
	}

}
