package game;

import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class EditorState extends MapState implements ActionListener {
	
	//camara and scale state
	public int camara_x = 0;
	public int camara_y = 0;
	
	public int scale = 2;
	
	private static int CAMARA_SPEED = 10;
	
	//graphcis options
	boolean draw_grid = true;
	
	boolean render_solids = true;
	boolean render_tiles = true;
	
	
	//swing elements / ui
	JPanel panle;
	JList blockList;
	JList tilesList;
	JTextField fileName;
	
	ArrayList<JButton> buttons;
	
	//editor info
	String selection;
	boolean start_set = false;
	
	//right click info
	boolean rightClickHeld = false;
	Point startOfRightClick;
	Point currentMousePosition;
	
	public EditorState(EntityLoader loader,JPanel panle) {	
		super(loader);
		buttons = new ArrayList<JButton>();
		this.panle = panle;
		selection = "nothing";
		
		int button_size = 120;
		int button_start = 150;
		
		addButton("Start Block",0+button_start,0,button_size,20);
		addButton("B",0+button_start,20,40,20);
		addButton("S",40+button_start,20,40,20);
		addButton("T",80+button_start,20,40,20);
		
		addButton("Place Block",button_size+button_start,0,button_size,20);
		addButton("Remove Block",button_size+button_start,20,button_size,20);
		
		
		addButton("Place Tile",2*button_size+button_start,0,button_size,20);
		addButton("Remove Tile",2*button_size+button_start,20,button_size,20);
				
		addButton("Save",3*button_size+button_start,0,button_size/2,20);
		addButton("Load",(int)(3.5*button_size)+button_start,0,button_size/2,20);
		
		fileName = new JTextField();
		fileName.setBounds(3*button_size+button_start,20,button_size,20);
		panle.add(fileName);
		
		
		blockList = addList(loader.getSolidsArray(),0,0,150,364);
		tilesList = addList(loader.getTilesArray(),0,364,150,364);
		

		
		
		
		
	}
	
	private JList addList(String[] arr, int x, int y, int width, int height) {
		JList list = new JList<String>(arr);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroller = new JScrollPane(list,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.setBounds(x,y,width,height);
		panle.add(scroller);
		scroller.validate();
		list.setFocusable(false);
		scroller.setFocusable(false);
		return list;

	}
	
	private JButton addButton(String name,int x,int y, int width, int height) {
		JButton botton = new JButton(name);
		botton.setBounds(x,y,width,height);
		botton.setFocusable(false);
		botton.addActionListener(this);
		botton.setMargin(new Insets(1,1,1,1));
		buttons.add(botton);
		panle.add(botton);
		return botton;
	}
	
	private Point getClickedBlock(Point mousePosition) {
		int block_x = (mousePosition.x + camara_x) / (Constants.BLOCK_SIZE * scale);
		int block_y = (mousePosition.y + camara_y) / (Constants.BLOCK_SIZE * scale);
		
		if(mousePosition.x + camara_x < 0) {
			block_x -= 1;
		}
		
		if(mousePosition.y + camara_y < 0) {
			block_y -= 1;
		}
		
		return new Point(block_x,block_y);
	}
	
	private void processRightClick(Point mousePosition, boolean mouseState) {
		//if just started the right click drag
		if(mouseState == true && rightClickHeld == false) {
			rightClickHeld = true;
			startOfRightClick = mousePosition;
		}
		
		//if still dragin
		if(rightClickHeld == true) {
			currentMousePosition = mousePosition;
		}
		
		//if realesing the right click after draging it
		if(mouseState == false && rightClickHeld == true) {
			rightClickHeld = false;
			//TODO process the blocks in the area
			
		}
	}
	
	
	private void processClick(Point mousePosition) {
		Point block = getClickedBlock(mousePosition);
		
		switch (selection) {
		
		case "Start Block":
			level.start_x = block.x;
			level.start_y = block.y;
			start_set = true;
			break;
			
		case "Place Block":
			String id = (String)blockList.getSelectedValue();
			if(id == null) {
				System.out.println("no selected value");
			} else {
				addBlock(Level.SOLIDS,id,block);
			}
			break;
		
		case "Remove Block":
			removeBlock(Level.SOLIDS,block);
			break;
		
		case "Place Tile":
			id = (String)tilesList.getSelectedValue();
			if(id == null) {
				System.out.println("no selected value");
			} else {
				addBlock(Level.TILES,id,block);
			}
			break;
		
		case "Remove Tile":
			removeBlock(Level.TILES,block);
			break;

			
		}
			
	}
	
	private void processKeys(boolean[] keyboardState) {
		int speed = CAMARA_SPEED;
		
		if(keyboardState[KeyEvent.VK_LEFT]) {
    		camara_x += -1*speed;
    	} else if (keyboardState[KeyEvent.VK_RIGHT]) {
    		camara_x += speed;
    	}
    	
		if(keyboardState[KeyEvent.VK_UP]) {
    		camara_y += -1*speed;
    	} else if (keyboardState[KeyEvent.VK_DOWN]) {
    		camara_y += speed;
    	}
		
		if(keyboardState[KeyEvent.VK_MINUS] && scale != 1) {
			int old_scale = scale;
			scale--;
			
			camara_x = ((scale * (camara_x + (panle.getWidth()/2))) / old_scale) - (panle.getWidth()/2);
			camara_y = ((scale * (camara_y + (panle.getHeight()/2))) / old_scale) - (panle.getHeight()/2);

		} else if(keyboardState[KeyEvent.VK_EQUALS]) {
			int old_scale = scale;
			scale++;
			camara_x = ((scale * (camara_x + (panle.getWidth()/2))) / old_scale) - (panle.getWidth()/2);
			camara_y = ((scale * (camara_y + (panle.getHeight()/2))) / old_scale) - (panle.getHeight()/2);
		}
		
		if(keyboardState[KeyEvent.VK_F]) {
			selection = "Place Block";
		}
		
		if(keyboardState[KeyEvent.VK_D]) {
			selection = "Remove Block";
		}
		
		if(keyboardState[KeyEvent.VK_R]) {
			selection = "Place Tile";
		}
		
		if(keyboardState[KeyEvent.VK_E]) {
			selection = "Remove Tile";
		}
	}
	
	@Override
	public void Update(long gameTime, Point mousePosition, boolean[] mouseState, boolean[] keyboardState) {
		
		processKeys(keyboardState);		
		
		if(mouseState[0]) {
			//mouseState[0] = false;
			processClick(mousePosition);
		}
		
		//System.out.println("(" + Integer.toString(mousePosition.x) + "," + Integer.toString(mousePosition.y) + ") " + mouseState[2]);
		processRightClick(mousePosition,mouseState[2]);
		
	}


	@Override
	public void Draw(Graphics2D g2d, int width, int height) {
		graphics.renderEditor(this, g2d, width, height);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String command = arg0.getActionCommand();
		
		switch(command) {
		case "Save":
			//System.out.println("Saving");
			boolean saved = level.saveLevel(fileName.getText());
			if(saved) {
				selection = "Saved: " + fileName.getText();
			} else {
				selection = "Failed to Save: " + fileName.getText() + "!!!";
			}
		break;
		
		case "Load":
			//System.out.println("Loading");
			Level newLevel = level.loadLevel(fileName.getText(),loader);
			if(newLevel != null) {
				selection = "Loaded: " + fileName.getText();
				level = newLevel;
			} else {
				selection = "Failed to Load: " + fileName.getText() + "!!!";
			}
			break;
			
		case "B":
			render_solids = true;
			render_tiles = true;
			break;
		
		case "S":
			render_solids = true;
			render_tiles = false;
			break;
		
		case "T":
			render_solids = false;
			render_tiles = true;
			break;
		
		
			
		default:
			selection = command;
		}
				
	}
}
