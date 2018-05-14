package game;

import java.awt.image.BufferedImage;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.omg.CORBA.portable.InputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
		loadAnimations();
		
	}
	
	public String[] getSolidsArray() {
		return solids.toArray(new String[solids.size()]);
	}
	
	
	public Image getImage(String id) {
		Image img = ((Image) (primaryMap.get(id)));
		//assert(img != null);
		return img;
	}
	
	//used for entities with their won animations
	public Image getImageCopy(String id) {
		Image img = ((Image) (primaryMap.get(id))).Copy();
		//assert(img != null);
		return img;
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
	
	
	private void loadAnimations() {
		try {
			FileInputStream stream = new FileInputStream("recourses/entities/animations.json");
			String jsonTxt = IOUtils.toString(stream,"UTF-8");
			JSONObject obj = new JSONObject(jsonTxt);   
			 
			JSONArray sheets = obj.getJSONArray("sheets");
			
			//iterate thtough each spreite sheet animations
			for(int sheetNumber = 0 ; sheetNumber < sheets.length(); sheetNumber++) {
				JSONObject sheet = sheets.getJSONObject(sheetNumber);
				String imgStr = sheet.getString("image");
				int width = sheet.getInt("width");
				int height = sheet.getInt("height");
				
				JSONArray animations = sheet.getJSONArray("animations");
				
				//array for numbers of frames per animation
				int[] numbers = new int[animations.length()];
				long[] frameTimes = new long[animations.length()];
				
				//iterate through each animation in that sprite sheet
				for(int animNumber = 0 ; animNumber < animations.length(); animNumber++) {
					JSONObject animation = animations.getJSONObject(sheetNumber);
					numbers[animNumber] = animation.getInt("number");
					frameTimes[animNumber] = animation.getLong("time");
				}
				
				//TODO make this for all numbers, for now just the first one
				Image oldImage = getImage(imgStr);
				if(oldImage == null) {
					System.out.println("failed to get the image for animation: " + imgStr);
				} else {
					Animation animation = new Animation(oldImage.animImage,width,height,0,numbers,frameTimes,true,0);
					primaryMap.put(imgStr, animation);
				}
				
				
				
			}
			
		} catch (JSONException e) {
			System.out.println("could not create animations");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println("could not find animations");
		} catch (IOException e) {
			System.out.println("could not load animations");
		}
	}
	
	

}
