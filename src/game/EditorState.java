package game;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;

public class EditorState extends MapState {
	public int camara_x = 0;
	public int camara_y = 0;
	
	boolean draw_grid = true;
	
	
	private static int  CAMARA_SPEED = 5;
	
	
	@Override
	public void Update(long gameTime, Point mousePosition, boolean[] mouseState, boolean[] keyboardState) {
		
		if(keyboardState[KeyEvent.VK_LEFT]) {
    		camara_x += -1*CAMARA_SPEED;
    	} else if (keyboardState[KeyEvent.VK_RIGHT]) {
    		camara_x += CAMARA_SPEED;
    	}
    	
		if(keyboardState[KeyEvent.VK_UP]) {
    		camara_y += -1*CAMARA_SPEED;
    	} else if (keyboardState[KeyEvent.VK_DOWN]) {
    		camara_y += CAMARA_SPEED;
    	}		
		
		System.out.println("x:" + Integer.toString(camara_x) + " y:" + Integer.toString(camara_y));
	}


	@Override
	public void Draw(Graphics2D g2d, int width, int height) {
		graphics.renderEditor(this, g2d, width, height);
	}
}
