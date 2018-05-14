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
import javax.swing.ListSelectionModel;

public class EditorState extends MapState implements ActionListener {
	public int camara_x = 0;
	public int camara_y = 0;
	
	public int scale = 2;
	
	boolean draw_grid = true;
	
	private static int  CAMARA_SPEED = 5;
	
	JPanel panle;
	JList blockList;
	
	ArrayList<JButton> buttons;

	String selection;
	
	//starting block for the payer
	int start_x,start_y;
	boolean start_set = false;
	
	public EditorState(EntityLoader loader,JPanel panle) {	
		super(loader);
		buttons = new ArrayList<JButton>();
		this.panle = panle;
		selection = "nothing";
		
		addButton("Start Block",0,0,100,30);
		addButton("Place Block",0,30,100,30);
		
		String[] list = loader.getSolidsArray();
		blockList = new JList<String>(list);
		blockList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		blockList.setLayoutOrientation(JList.VERTICAL);
		blockList.setVisibleRowCount(-1);
		blockList.setBounds(0,90,150,400);
		JScrollPane blockScroller = new JScrollPane(blockList);
		blockScroller.setBounds(0,90,150,400);
		blockScroller.setFocusable(false);
		blockList.setFocusable(false);
		panle.add(blockList);
		panle.add(blockScroller);
		
		
		
		
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
			start_x = block.x;
			start_y = block.y;
			start_set = true;
			break;
			
		case "Place Block":
			String id = (String)blockList.getSelectedValue();
			if(id == null) {
				System.out.println("no selected value");
			} else {
				addBlock(id,null,block);
			}
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
			scale--;
			camara_x = ((camara_x + panle.getWidth())/ (2*Constants.BLOCK_SIZE)) * scale - (panle.getWidth() / 2);
			camara_y = ((camara_y + panle.getHeight())/ (2*Constants.BLOCK_SIZE)) * scale - (panle.getHeight() / 2);

		} else if(keyboardState[KeyEvent.VK_EQUALS]) {
			scale++;
			camara_x = ((camara_x + panle.getWidth())/ (2*Constants.BLOCK_SIZE)) * scale - (panle.getWidth() / 2);
			camara_y = ((camara_y + panle.getHeight())/ (2*Constants.BLOCK_SIZE)) * scale - (panle.getHeight() / 2);
		}
	}
	
	@Override
	public void Update(long gameTime, Point mousePosition, boolean[] mouseState, boolean[] keyboardState) {
		
		processKeys(keyboardState);		
		
		if(mouseState[0]) {
			mouseState[0] = false;
			processClick(mousePosition);
		}
		
	}


	@Override
	public void Draw(Graphics2D g2d, int width, int height) {
		graphics.renderEditor(this, g2d, width, height);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		selection = arg0.getActionCommand();
		
	}
}
