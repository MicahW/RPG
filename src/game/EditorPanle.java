package game;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import gameframework.Framework;

public class EditorPanle extends JPanel {
	
	private EditorState editor;
		
	public EditorPanle(EditorState state)
    {
		this.editor = state;
		
		this.setBackground(Color.WHITE);
        
        this.setVisible(true);
                            
        
    }
}
