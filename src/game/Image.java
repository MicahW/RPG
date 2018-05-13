package game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Image {
	// Image of animation.
    protected BufferedImage animImage;
    
 // Width of one frame in animated image.
    protected int frameWidth;

    // Height of the frame(image).
    protected int frameHeight;
    
    public Image(BufferedImage img) {
    	animImage = img;
    	frameWidth = img.getWidth();
    	frameHeight = img.getHeight();
    }
    
    public void Draw(Graphics2D g2d,int x, int y, int scale)   {
    	g2d.drawImage(animImage, x, y, x + frameWidth, y + frameHeight, 0, 
            		frameHeight, 0 , frameHeight, null);
    }
}
