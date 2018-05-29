package game;

import java.awt.Point;
import java.io.Externalizable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class Level implements Externalizable, java.io.Serializable{
	private static final long serialVersionUID = 23L;
	
	static final int SOLIDS = 1;
	static final int TILES = 2;
	
	int start_x;
	int start_y;
	boolean start_selected;
	
	//variables used to store map
	HashMap<Point,Block> solidsMap;
	HashMap<Point,Block> tilesMap;
	
	
	private HashMap<Point,Block> getHashMap(int id) {
		switch (id) {
		case SOLIDS:
			return solidsMap;
		case TILES:
			return tilesMap;
		}
		assert(false);
		return null;
	}
	
	public Level() {
		solidsMap = new HashMap<Point,Block>();
		tilesMap = new HashMap<Point,Block>();
	}
	
	public void putBlock(int id,Point p, Block b) {
		getHashMap(id).put(p, b);
	}
	
	public Block getBlockOrOwner(int id,Point p) {
		Block block = getBlock(id,p);
		
		if(block == null || block.used == false) {
			return block;
		}
		
		p = block.getOwnerPoint();
		block = getBlock(id,p);
		
		return block;
	}
	
	public Block getBlock(int id,Point p) {
		return getHashMap(id).get(p);
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
		assert(loader != null);
		
		//load tiles
		for(Block block : solidsMap.values()) {
			if(block != null && !block.used) {
				block.loadImage(loader);
				assert(block.img != null);
			}
			
		}
		
		//load blocks
		for(Block block : tilesMap.values()) {
			if(block != null && !block.used) {
				block.loadImage(loader);
				assert(block.img != null);
			}
			
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

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		String checkPoint = (String)in.readObject();
		switch (checkPoint) {
		//switch through each checkpoint, creat less blank object as version increases
		//last case should have a break, all others should not
		case "base":
			solidsMap = (HashMap<Point,Block>) in.readObject();
			tilesMap = (HashMap<Point,Block>) in.readObject();
			start_x = (int) in.readObject();
			start_y = (int) in.readObject();
			start_selected = (boolean) in.readObject();
			break;
		default:
			System.out.println("ERROR: something has gone wronge with level object loading in checkpoint");
			assert(false);
		}
		
		
	}

	//write the checkpoint first, then write things in reverse order that they where added to this class
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(new String("base"));
		out.writeObject(solidsMap);
		out.writeObject(tilesMap);
		out.writeObject(start_x);
		out.writeObject(start_y);
		out.writeObject(start_selected);
	}
	
	
	
	
}
