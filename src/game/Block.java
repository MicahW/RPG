package game;

import java.awt.Point;



public class Block implements java.io.Serializable{
	private static final long serialVersionUID = 24L;
	
	
	transient Image img;
	String src;
	//solid width and height, others to not matter
	int width;
	int height;
	
	//used to indicate that this is a usedblock
	//if this is the case then width and height will be block cords for block
	// this is a part of.
	boolean used;
	
	
	public static final Block USED = new Block();
	
	public Block() {
	}
	
	//constructer used to partials
	public Block(Point owner) {
		used = true;
		width = owner.x;
		height = owner.y;
	}
	
	public Point getOwnerPoint() {
		if(used == false) {
			return null;
		}
		
		return new Point(width,height);
	}
	
	
	//set the source and load the image as necisary
	public void setSrc(String src, EntityLoader loader) {
		used = false;
		this.src = src;
		img = loader.getImage(src);
		Point dims = img.getDimentions();
		width = dims.x;
		height = dims.y;
	}
	
	//used opun deserilzing a level
	public void loadImage(EntityLoader loader) {
		if(used == true) {
			return;
		}
		assert(src != null);
		img = loader.getImage(src);
	}
	
	
	
	
}
