package game;

import java.awt.Point;



public class Block implements java.io.Serializable{
	transient Image img;
	String src;
	//solid width and height, others to not matter
	int width;
	int height;
	
	public static final Block USED = new Block();
	
	public Block() {
	}
	
	
	
	//set the source and load the image as necisary
	public void setSrc(String src, EntityLoader loader) {
		this.src = src;
		img = loader.getImage(src);
		Point dims = img.getDimentions();
		width = dims.x;
		height = dims.y;
	}
	
	
	
	
}
