package game;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;

public class PlayingState extends MapState {

	//testing the framework
	double x;
	double y;
	
	public PlayingState(EntityLoader loader) {
		super(loader);
		x = 0;
		y = 0;
	}
	
	public void Update(double dx, double dy) {
		x+=dx;
		y+=dy;
	}

	@Override
	public void Update(long gameTime, Point mousePosition, boolean[] mouseState, boolean[] keyboardState) {
		// TODO Auto-generated method stub
double x =0;
    	
    	if(keyboardState[KeyEvent.VK_LEFT]) {
    		x = -.5;
    	}
    	
    	if(keyboardState[KeyEvent.VK_RIGHT]) {
    		x = .5;
    	}
    	
        Update(x,0);
		
	}

	@Override
	public void Draw(Graphics2D g2d, int width, int heigth) {
		// TODO Auto-generated method stub
		
	}


}
