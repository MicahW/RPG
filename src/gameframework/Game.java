package gameframework;
import game.*;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;

import javax.swing.JButton;


/**
 * Actual game.
 * 
 * @author www.gametutorial.net
 */

public class Game {


	MapState gameState;
	EntityLoader loader;
	Graphics graphics;
	String type;
	
	Framework frame;
	
	
	
    public Game(String type, Framework frame)
    {
    	this.type = type;
    	this.frame = frame;
        Framework.gameState = Framework.GameState.GAME_CONTENT_LOADING;
        
        Thread threadForInitGame = new Thread() {
            @Override
            public void run(){
            	
            	// Load game files (images, sounds, ...)
                LoadContent();
                // Sets variables and objects for the game.
                Initialize();
                
                
                Framework.gameState = Framework.GameState.PLAYING;
            }
        };
        threadForInitGame.start();
    }
    
    
   /**
     * Set variables and objects for the game.
     */
    private void Initialize()
    {
    	if(type == "game") {
    		gameState = new PlayingState(loader);
    	} else if(type == "editor") {
    		gameState = new EditorState(loader,frame);
    	}
    	
    	graphics = new Graphics();
    }
    
    /**
     * Load game files - images, sounds, ...
     */
    private void LoadContent()
    {

    	loader = new EntityLoader();
    }    
    
    
    /**
     * Restart game - reset some variables.
     */
    public void RestartGame()
    {
        
    }
    
    
    /**
     * Update game logic.
     * 
     * @param gameTime gameTime of the game.
     * @param mousePosition current mouse position.
     */
    public void UpdateGame(long gameTime, Point mousePosition, boolean[] mouseState, boolean[] keyboardState) {
    	gameState.Update(gameTime, mousePosition,mouseState, keyboardState);
    }
    
    /**
     * Draw the game to the screen.
     * 
     * @param g2d Graphics2D
     * @param mousePosition current mouse position.
     */
    public void Draw(Graphics2D g2d, Point mousePosition, int width, int heigth)
    {
    	assert(gameState != null);
    	assert(graphics != null);
    	assert(g2d != null);
        gameState.Draw(g2d,width,heigth);
    }
}
