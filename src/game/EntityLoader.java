package game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class EntityLoader {
	//responsieble for loading game entitys and providing quick acess to them
	HashMap<String,Object> primaryMap;
	
	ArrayList<String> solids;
	
	public EntityLoader() {
		primaryMap = new HashMap<String,Object>();
		solids = new ArrayList<String>();
		
		loadImages("solids",solids);
		
	}
	
	public String[] getSolidsArray() {
		return solids.toArray(new String[solids.size()]);
	}
	
	private void loadImages(String path, ArrayList<String> images) {
		File[] files = new File("recourses/imgs/" + path).listFiles();
		if(files == null) {
			System.out.println("failed to open: " + path);
			return;
		}
	    for(File file : files) {
	    	if(!file.isDirectory()) {
	    		try {
					BufferedImage img = ImageIO.read(file);
					Image image = new Image(img);
					String str = file.getName().replaceAll(".png", "");
					solids.add(str);
					primaryMap.put(str,image);
				} catch (IOException e) {
					System.out.println("Failed to load image");
				}
	    		
	    	}
	    }
	}
	
	
	

}
