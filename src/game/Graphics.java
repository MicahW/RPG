package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gameframework.Animation;

public class Graphics {
	
	Animation animation = null;
	
	int scale = 1;
	
	public Graphics() {
		try {
			BufferedImage img = ImageIO.read(new File("recourses/imgs/hero_spritesheet.png"));
			animation = new Animation(img,80,100,1,6,120,true,25,25,0);
		} catch (IOException e) {
			assert(false);
		}
		
		
	}
	
	public void Render (PlayingState state, Graphics2D g2d) {
		animation.changeCoordinates((int)(state.x), (int)(state.y));
		animation.Draw(g2d);
	}
}
