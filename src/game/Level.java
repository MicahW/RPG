package game;

import java.awt.Point;
import java.util.HashMap;

public class Level {
	int start_x;
	int start_y;
	
	//variables used to store map
	HashMap<Point,Block> solidsMap;
	
	public Level() {
		solidsMap = new HashMap<Point,Block>();
	}
	
	public void putSolid(Point p, Block b) {
		solidsMap.put(p, b);
	}
	
	public Block getSolid(Point p) {
		return solidsMap.get(p);
	}
	
	
	
}
