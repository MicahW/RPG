package game;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;


//holds info for wall enemys and other enties but provides no means for
//game playing or interaction
public abstract class MapState {
	
	public transient Graphics graphics;
	
	public transient EntityLoader loader;
	
	//variables used to store map
	HashMap<Point,Block> levelMap;
	
	public MapState(EntityLoader loader) {
		this.loader = loader;
		levelMap = new HashMap<Point,Block>();
		graphics = new Graphics();
	}
	
	public abstract void Draw(Graphics2D g2d,int width, int height);
	
	public abstract void Update(long gameTime, Point mousePosition,boolean[] mouseState,boolean[] keyboardState);
	
	
	protected void removeBlock(Point point) {
		levelMap.put(point, null);
	}
	
	//add a block to the levelMap, or if one avalible then change it
	protected void addBlock(String solid, String tile , Point point) {
		Block block = levelMap.get(point);
		if(block == null) {
			block = new Block();
			levelMap.put(point,block);
		}
		block.setSrc(solid, tile, loader);
	}
	
}

