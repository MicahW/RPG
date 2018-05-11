package gameframework;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class MainMenu implements ActionListener {
	
	Framework framework;
	JButton newGame;
	
	public MainMenu(Framework framework) {
		this.framework = framework;
		
		int width = framework.getWidth();
		
		newGame = new JButton("New Game");
		
		newGame.setBounds((width-100)/2,100,100,40);
		
		newGame.addActionListener(this);
		
		framework.add(newGame);	
		
	}
	
	private void cleanUp() {
		framework.remove(newGame);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if("New Game".equals(arg0.getActionCommand())) {
			cleanUp();
			framework.newGame();
		}
		
	}
	
	
	
	


}
