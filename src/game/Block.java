package game;

import java.awt.Point;



public class Block implements java.io.Serializable{
	transient Image tile;
	transient Image solid;
	String tileSrc;
	String solidSrc;
	//solid width and height, others to not matter
	int width;
	int height;
	
	public static Block usedBlock = new Block();
	
	public Block() {
	}
	
	
	
	//set the source and load the image as necisary
	public void setSrc(String solidSrc, String tileSrc,EntityLoader loader) {
		if(tileSrc != null) {
			this.tileSrc = tileSrc;
			tile = loader.getImage(tileSrc);
		} 
		
		if(solidSrc != null) {
			this.solidSrc = solidSrc;
			solid = loader.getImage(solidSrc);
			Point dims = solid.getDimentions();
			width = dims.x;
			height = dims.y;
		}
	}
	
	
	
	
}
