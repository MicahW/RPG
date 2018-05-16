package game;

import java.awt.Graphics2D;
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
	public int camara_x = 0;
	public int camara_y = 0;
	
	public int scale = 2;
	
	boolean draw_grid = true;
	
	private static int  CAMARA_SPEED = 10;
	
	JPanel panle;
	JList blockList;
	JTextField fileName;
	
	ArrayList<JButton> buttons;

	String selection;
	
	boolean start_set = false;
	
	public EditorState(EntityLoader loader,JPanel panle) {	
		super(loader);
		buttons = new ArrayList<JButton>();
		this.panle = panle;
		selection = "nothing";
		
		int button_size = 140;
		
		addButton("Start Block",0,0,button_size,20);
		
		addButton("Place Block",0,22,button_size,20);
		addButton("Remove Block",0,42,button_size,20);
		
		addButton("Place Tile",0,64,button_size,20);
		addButton("Remove Tile",0,84,button_size,20);
		
		addButton("Save",0,106,button_size/2,20);
		addButton("Load",button_size/2,106,button_size/2,20);
		
		fileName = new JTextField();
		fileName.setBounds(0,126,button_size,20);
		panle.add(fileName);
		
		
		String[] list = loader.getSolidsArray();
		blockList = new JList<String>(list);
		blockList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane blockScroller = new JScrollPane(blockList,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		blockScroller.setBounds(0,180,150,400);
		panle.add(blockScroller);
		blockScroller.validate();

		
		
		
		
	}
	
	private JButton addButton(String name,int x,int y, int width, int height) {
		JButton botton = new JButton(name);
		botton.setBounds(x,y,width,height);
		botton.setFocusable(false);
		botton.addActionListener(this);
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
	}
	
	@Override
	public void Update(long gameTime, Point mousePosition, boolean[] mouseState, boolean[] keyboardState) {
		
		processKeys(keyboardState);		
		
		if(mouseState[0]) {
			//mouseState[0] = false;
			processClick(mousePosition);
		}
		
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
			
		default:
			selection = command;
		}
				
	}
}
