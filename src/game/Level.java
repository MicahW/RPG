package game;

import java.awt.Point;
import java.util.HashMap;

public class Level {
	int start_x;
	int start_y;
	
	//variables used to store map
	HashMap<Point,Block> levelMap;
	
	public Level() {
		levelMap = new HashMap<Point,Block>();
	}
	
	public void putBlock(Point p, Block b) {
		levelMap.put(p, b);
	}
	
	public Block getBlock(Point p) {
		return levelMap.get(p);
	}
	
	
	
}
