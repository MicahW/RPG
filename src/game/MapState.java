package game;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;


//holds info for wall enemys and other enties but provides no means for
//game playing or interaction
public abstract class MapState {
	
	public Graphics graphics;
	
	public EntityLoader loader;
	
	public Level level;
	
	public MapState(EntityLoader loader) {
		this.loader = loader;
		level = new Level();
		graphics = new Graphics();
	}
	
	public abstract void Draw(Graphics2D g2d,int width, int height);
	
	public abstract void Update(long gameTime, Point mousePosition,boolean[] mouseState,boolean[] keyboardState);
	
	
	protected void removeBlock(Point point) {
		level.putBlock(point, null);
	}
	
	//add a block to the levelMap, or if one avalible then change it
	protected void addBlock(String solid, String tile , Point point) {
		Block block = level.getBlock(point);
		if(block == null) {
			block = new Block();
			level.putBlock(point,block);
		}
		block.setSrc(solid, tile, loader);
	}
	
}

