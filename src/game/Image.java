package game;

import java.awt.Graphics2D;
import java.awt.Point;
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
    
    public Point getDimentions() {
    	return new Point(frameWidth / Constants.BLOCK_SIZE, frameHeight / Constants.BLOCK_SIZE);
    }
    
    //images can just pass pointers to the one image, this will be used for animations
    public Image Copy() {
    	return this;
    }
    
    public void Draw(Graphics2D g2d,int x, int y, int scale)   {
    	g2d.drawImage(animImage, 
    			x, y, x + (scale *frameWidth), y + (scale * frameHeight), 
    			0,0, frameWidth , frameHeight, null);
    }
}
