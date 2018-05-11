package gameframework;
import game.*;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;


/**
 * Actual game.
 * 
 * @author www.gametutorial.net
 */

public class Game {


	PlayingState gameState;
	Graphics graphics;
	
	
	
    public Game()
    {
        Framework.gameState = Framework.GameState.GAME_CONTENT_LOADING;
        
        Thread threadForInitGame = new Thread() {
            @Override
            public void run(){
                // Sets variables and objects for the game.
                Initialize();
                // Load game files (images, sounds, ...)
                LoadContent();
                
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
    	gameState = new PlayingState();
    	graphics = new Graphics();
    }
    
    /**
     * Load game files - images, sounds, ...
     */
    private void LoadContent()
    {
    	
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
    public void UpdateGame(long gameTime, Point mousePosition,boolean[] keyboardState)
    {
    	double x =0;
    	
    	if(keyboardState[KeyEvent.VK_LEFT]) {
    		x = -.5;
    	}
    	
    	if(keyboardState[KeyEvent.VK_RIGHT]) {
    		x = .5;
    	}
    	
        gameState.Update(x,0);
    }
    
    /**
     * Draw the game to the screen.
     * 
     * @param g2d Graphics2D
     * @param mousePosition current mouse position.
     */
    public void Draw(Graphics2D g2d, Point mousePosition)
    {
    	assert(gameState != null);
    	assert(graphics != null);
    	assert(g2d != null);
        graphics.Render(gameState,g2d);
    }
}
