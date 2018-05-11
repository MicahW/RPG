package game;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

public class EditorState extends MapState {
	public int camara_x = 0;
	public int camara_y = 0;
	
	public int scale = 3;
	
	boolean draw_grid = true;

	//this is just a test array

	private static int  CAMARA_SPEED = 5;
	
	public EditorState(JPanel panle) {
		JButton editor = new JButton("test");
		editor.setBounds(0,0,100,40);
		editor.setFocusable(false);
		//editor.addActionListener(this);
		
		panle.add(editor);
	}
	
	private void getClickedBlock(Point mousePosition) {
		int block_x = (mousePosition.x + camara_x) / (Constants.BLOCK_SIZE * scale);
		int block_y = (mousePosition.y + camara_y) / (Constants.BLOCK_SIZE * scale);
		System.out.println("Clicked block (" + Integer.toString(block_x) + "," + Integer.toString(block_y) +")");
	}
	
	
	@Override
	public void Update(long gameTime, Point mousePosition, boolean[] mouseState, boolean[] keyboardState) {
		
		int speed = CAMARA_SPEED;
		
		if(keyboardState[KeyEvent.VK_LEFT]) {
    		camara_x += -1*speed;
    	} else if (keyboardState[KeyEvent.VK_RIGHT]) {
    		camara_x += speed;
    	}
    	
		if(keyboardState[KeyEvent.VK_UP]) {
    		camara_y += -1*speed;
    	} else if (keyboardState[KeyEvent.VK_DOWN]) {
    		camara_y += speed;
    	}		
		
		if(mouseState[0]) {
			mouseState[0] = false;
			getClickedBlock(mousePosition);
		}
		
	}


	@Override
	public void Draw(Graphics2D g2d, int width, int height) {
		graphics.renderEditor(this, g2d, width, height);
	}
}
