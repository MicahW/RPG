package game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Graphics {
	
	int scale;
	
	int camara_x;
	int camara_y;
	
	int screen_width;
	int screen_height;
		
	MapState mapState;
	
	HashMap<Point,Boolean> renderedMap;
	
	public Graphics() {
		
	}
	
	private void drawSolid(Graphics2D g2d,int type,int block_x,int block_y) {
		//TODO set up hashmap to store rendred blocks to only render a image once
		//right now rendering a image for every block it has
		int x_pos =  (block_x  * Constants.BLOCK_SIZE * scale) - camara_x; 
		int y_pos =  (block_y  * Constants.BLOCK_SIZE * scale) - camara_y;
		
		Point point = new Point(block_x,block_y);
		Block block = mapState.level.getBlock(type,point);
		
		if(block != null) {
			if(block.used == false) {
				block.img.Draw(g2d, x_pos, y_pos, scale);
				
				renderedMap.put(point,true);
				
				if(block.fade) {
					for(int x = 0; x < block.width; x++) {
						for(int y = 0; y < block.height; y++) {
							drawFilledBlock(g2d,block_x + x,
									block_y + y,Color.GREEN);
						}
					}
					
				}
			} else {
				
				
				
				Point ownerPoint = block.getOwnerPoint();
				Block owner = mapState.level.getBlock(type,ownerPoint);
				
				//if this was already drawn to the screen
				if(renderedMap.containsKey(ownerPoint)) {
					return;
				}
				
				int owner_x_pos =  (ownerPoint.x  * Constants.BLOCK_SIZE * scale) - camara_x; 
				int ownder_y_pos =  (ownerPoint.y  * Constants.BLOCK_SIZE * scale) - camara_y;
				
				
				if(owner != null && owner.img != null) { //:) fixed
				assert(owner != null);
				assert(owner.img != null);
				owner.img.Draw(g2d, owner_x_pos, ownder_y_pos, scale);
				}
			}
		}
	}
	
	private void drawBlocks(Graphics2D g2d,int type) {

		renderedMap = new HashMap<Point,Boolean>();
		
		int start_x = ((camara_x) / (Constants.BLOCK_SIZE * scale))-1;
		int start_y = ((camara_y) / (Constants.BLOCK_SIZE * scale))-1;
		int end_x = ((camara_x+screen_width) / (Constants.BLOCK_SIZE * scale))+1;
		int end_y = ((camara_y+screen_height) / (Constants.BLOCK_SIZE * scale))+1;
		
		int x,y;
		
		for(x = start_x; x < end_x; x++) {
			for(y = start_y; y < end_y; y++) {
				drawSolid(g2d,type,x,y);
			}
		}
		
		
	}
	
	private void drawFilledBlock(Graphics2D g2d,int block_x, int block_y, Color color) {
		int x_pos =  (block_x  * Constants.BLOCK_SIZE * scale) - camara_x; 
		int y_pos =  (block_y  * Constants.BLOCK_SIZE * scale) - camara_y;
		
		g2d.setPaint(color);
		
		g2d.draw(new Rectangle(x_pos,y_pos,Constants.BLOCK_SIZE*scale, Constants.BLOCK_SIZE*scale));
		g2d.drawLine(x_pos,y_pos,x_pos + Constants.BLOCK_SIZE * scale, y_pos + Constants.BLOCK_SIZE * scale);
		g2d.drawLine(x_pos + Constants.BLOCK_SIZE * scale,y_pos,x_pos, y_pos + Constants.BLOCK_SIZE * scale);
	}
		
	
	private void drawGrid(Graphics2D g2d) {
		int block_size = Constants.BLOCK_SIZE * scale;
		
		int start_x = 0 - (camara_x % block_size) - block_size; 
		int start_y = 0 - (camara_y % block_size) - block_size;
		
		int end_x = screen_width + block_size;
		int end_y = screen_height + block_size;
		
		g2d.setColor(Color.GRAY);
		
		for(int i = start_x; i < end_x; i += block_size) {
			g2d.drawLine(i, start_y,i,end_y );
		}
		
		for(int i = start_y; i < end_y; i += block_size) {
			g2d.drawLine(start_x,i,end_x,i );
		}
		
		
	}
	
	private void drawRightDrag(EditorState state, Graphics2D g2d) {
		if(!state.rightClickHeld) {
			return;
		}
		
		int x = state.startOfRightClick.x;
		int y = state.startOfRightClick.y;
		int width = state.currentMousePosition.x - x;
		int height = state.currentMousePosition.y - y;
		
		if(width < 0) {
			x += width;
			width *= -1;
		}
		
		if(height < 0) {
			y += height;
			height *= -1;
		}
		
		g2d.setPaint(Color.BLUE);
		g2d.draw(new Rectangle(x,y,width,height));
	}
	
	private void drawInfo(Graphics2D g2d, EditorState state) {
		int width = 120;
		int height = 120;
		int string_pos = 100;
		
		g2d.setColor(Color.BLACK);
		g2d.fill(new Rectangle(screen_width - width,0,width,height));
		g2d.setColor(Color.WHITE);
		g2d.drawString("Slt: " + state.selection,screen_width-string_pos,10);
		g2d.drawString("Fade: " + Boolean.toString(state.set_fade),screen_width - string_pos,30);
		g2d.drawString("Draw Solids: " + Boolean.toString(state.render_solids),screen_width - string_pos,50);
		g2d.drawString("Draw Tiles: " + Boolean.toString(state.render_tiles),screen_width - string_pos,70);

		

	}
	
	public void renderEditor (EditorState state, Graphics2D g2d,int width, int height) {
		camara_x = state.camara_x;
		camara_y = state.camara_y;
		
		screen_width = width;
		screen_height = height;
		
		scale = state.scale;
		
		mapState = state;
		
		drawGrid(g2d);
		
		//draw the solids
		
		if(state.render_tiles) {
			drawBlocks(g2d,Level.TILES);
		}
		
		if(state.render_solids) {
			drawBlocks(g2d,Level.SOLIDS);
		}
		
		if(state.start_set) 
		{
			drawFilledBlock(g2d, state.level.start_x, state.level.start_y, Color.PINK);
		}
		
		drawRightDrag(state,g2d);
		drawInfo(g2d,state);
				
		
		
		
	}
	
}
