package gameframework;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class MainMenu implements ActionListener {
	
	Framework framework;
	JButton newGame;
	JButton editor;
	
	public MainMenu(Framework framework) {
		this.framework = framework;
		
		int width = framework.getWidth();
		
		newGame = new JButton("New Game");
		newGame.setBounds((width-100)/2,100,100,40);
		newGame.addActionListener(this);
		
		editor = new JButton("Map Editor");
		editor.setBounds((width-100)/2,170,100,40);
		editor.addActionListener(this);
		
		framework.add(newGame);
		framework.add(editor);	
		
	}
	
	private void cleanUp() {
		framework.remove(newGame);
		framework.remove(editor);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if("New Game".equals(arg0.getActionCommand())) {
			cleanUp();
			framework.newGame();
		}
		
		if("Map Editor".equals(arg0.getActionCommand())) {
			cleanUp();
			framework.newEditor();
		}
		
	}
	
	
	
	


}
