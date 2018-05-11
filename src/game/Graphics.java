package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gameframework.Animation;

public class Graphics {
	
	int scale = 1;
	
	int camara_x;
	int camara_y;
	
	int screen_width;
	int screen_height;
	
	public Graphics() {
		
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
	
	public void renderEditor (EditorState state, Graphics2D g2d,int width, int height) {
		camara_x = state.camara_x;
		camara_y = state.camara_y;
		
		screen_width = width;
		screen_height = height;
		
		scale = state.scale;
		
		drawGrid(g2d);
		
	}
}
