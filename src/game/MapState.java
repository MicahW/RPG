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
	
	
	protected void removeBlock(int type,Point point) {
		Block block = level.getBlock(type,point);
		
		if(block == null) {
			return;
		}
		
		for(int x = 0; x < block.width; x++) {
			for(int y = 0; y < block.height; y++) {
				level.putBlock(type,new Point(x+point.x,y+point.y), null);
			}
		}
	}
	
	//add a block to the levelMap, or if one avalible then change it
	protected void addBlock(int type, String src, Point point) {
		Block block = new Block();
		block.setSrc(src, loader);
		
		for(int x = 0; x < block.width; x++) {
			for(int y = 0; y < block.height; y++) {
				Point p = new Point(x+point.x,y+point.y);
				if(level.getBlock(type, p) != null) {
					return;
				}
			}
		}
				
		level.putBlock(type, point,block);
	
		for(int x = 0; x < block.width; x++) {
			for(int y = 0; y < block.height; y++) {
				if(!(x == 0 && y == 0)) {
					level.putBlock(type,new Point(x+point.x,y+point.y), new Block(point));
				}
			}
		}
		
	}
	
}

