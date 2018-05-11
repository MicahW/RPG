package game;

import java.awt.Graphics2D;
import java.awt.Point;


//holds info for wall enemys and other enties but provides no means for
//game playing or interaction
public abstract class MapState {
	
	public Graphics graphics;
	
	public abstract void Draw(Graphics2D g2d,int width, int height);
	
	public abstract void Update(long gameTime, Point mousePosition,boolean[] mouseState,boolean[] keyboardState);
	
	public MapState() {
		graphics = new Graphics();
	}
}

