package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Graphics {
	
	public void Render (PlayingState state, Graphics2D g2d) {
		g2d.setBackground(Color.white);
		g2d.draw(new Rectangle((int)(state.x),(int)(state.y),30,30));
	}
}
