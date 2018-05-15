package game;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class Level implements java.io.Serializable{
	private static final long serialVersionUID = 23L;
	
	
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
	
	public boolean saveLevel(String name) {
		try {
			String path = "recourses/levels/" + name + ".map";
			FileOutputStream fileOut = new FileOutputStream(path);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("could not open file output stream for: " + name);
			return false;
		} catch (IOException e) {
			System.out.println("could not open object output stream for: " + name);
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//used to load immages,animations,ect.. this level has
	public void loadEntitys(EntityLoader loader) {
		for(Block block : solidsMap.values()) {
			assert(block != null);
			assert(loader != null);
			block.loadImage(loader);
		}
	}
	
	static public Level loadLevel(String name, EntityLoader loader) {
		Level level;
		String path = "recourses/levels/" + name + ".map";
		
		try {
			
			FileInputStream fileIn = new FileInputStream(path);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			level = (Level) in.readObject();
			in.close();
			fileIn.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("could not open file output stream for: " + path);
			return null;
		} catch (IOException e) {
			System.out.println("could not open object output stream for: " + path);
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		level.loadEntitys(loader);
		return level;
	}
	
	
	
}
