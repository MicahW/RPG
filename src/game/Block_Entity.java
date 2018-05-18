package game;

import java.awt.Point;

public class Block_Entity {
	//
	public final static Block_Entity USED = new Block_Entity();
	
	
	transient Image img;
	String src;
	int width;
	int height;
	
	protected void setSrc(String src, EntityLoader loader) {
		this.src = src;
		img = loader.getImage(src);
		Point dims = img.getDimentions();
		width = dims.x;
		height = dims.y;
	}
	
}