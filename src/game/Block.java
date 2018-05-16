package game;

import java.awt.Point;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.HashMap;



public class Block implements Externalizable, java.io.Serializable{
	private static final long serialVersionUID = 24L;
	
	
	transient Image img;
	String src;
	//solid width and height, others to not matter
	int width;
	int height;
	
	//used to indicate that this is a usedblock
	//if this is the case then width and height will be block cords for block
	// this is a part of.
	boolean used;
	
	
	public static final Block USED = new Block();
	
	public Block() {
	}
	
	//constructer used to partials
	public Block(Point owner) {
		used = true;
		width = owner.x;
		height = owner.y;
	}
	
	public Point getOwnerPoint() {
		if(used == false) {
			return null;
		}
		
		return new Point(width,height);
	}
	
	
	//set the source and load the image as necisary
	public void setSrc(String src, EntityLoader loader) {
		used = false;
		this.src = src;
		img = loader.getImage(src);
		Point dims = img.getDimentions();
		width = dims.x;
		height = dims.y;
	}
	
	//used opun deserilzing a level
	public void loadImage(EntityLoader loader) {
		if(used == true) {
			return;
		}
		assert(src != null);
		img = loader.getImage(src);
	}
	
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		String checkPoint = (String)in.readObject();
		switch (checkPoint) {
		//switch through each checkpoint, creat less blank object as version increases
		//last case should have a break, all others should not
		case "base":
			src = (String) in.readObject();
			width = (int) in.readObject();
			height = (int) in.readObject();
			used = (boolean) in.readObject();
			break;
		default:
			System.out.println("ERROR: something has gone wronge with level object loading in checkpoint in Block Object");
			assert(false);
		}
		
		
	}

	//write the checkpoint first, then write things in reverse order that they where added to this class
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(new String("base"));
		out.writeObject(src);
		out.writeObject(width);
		out.writeObject(height);
		out.writeObject(used);
	}
	
	
	
	
	
}
