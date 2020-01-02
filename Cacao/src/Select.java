import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

/* 
	File Name: Menu.java
	   Author: Khoi Tran
		 Date: Jan. 1, 2020 12:23:01 p.m.
  Description: Handles the select screen and any mouse choices
*/

//extend mouse adapter to this class can react to mouse clicks
public class Select extends MouseAdapter
{
	private int heightOffset = 100;
	private int widthOffset = 150;
	private int boxWidth = 275;
	private int boxHeight = 75;
	private Game game;
	private Handler handler;
	public MouseEvent e;
	
	//create variables to pass to handler to indicate that we have clicked on the worker tiles to rotate
	protected boolean worker1Clicked = false;
	protected boolean worker2Clicked = false;
	protected boolean worker3Clicked = false;
	//create variable so so that once the worker tile is clicked, an action only happens once (i.e. 1 action between mouse click and release)
	protected boolean worker1ActionComplete = false;
	protected boolean worker2ActionComplete = false;
	protected boolean worker3ActionComplete = false;
	//mouse pressed
	protected int mxP = 0;
	protected int myP = 0;
	
	public Select(Game game, Handler handler)
	{
		this.game = game;
		this.handler = handler;
	}
	 
	//events if mouse button is pressed
	//the listener in the game means we don't have to tick this.
	public void mousePressed(MouseEvent e)
	{	
		//stores x position of mouse
		mxP = e.getX();
		
		//stores y position of mouse
		myP = e.getY();
		
		//select number of players screen
		if(game.gameState == Game.STATE.Select)
		{
			if(mouseOver(mxP, myP, 550, 400, 275, 75))
			{
				game.numPlayers = 2;
				game.gameState = Game.STATE.Player1;
				game.initGame();
			}
			else if(mouseOver(mxP, myP, 550, 500, 275, 75))
			{
				game.numPlayers = 3;
				game.gameState = Game.STATE.Player1;
				game.initGame();
			}
			else if(mouseOver(mxP, myP, 550, 600, 275, 75))
			{
				game.numPlayers = 4;
				game.gameState = Game.STATE.Player1;
				game.initGame();
			}
		}
		
		//notify handler class if worker tiles are clicked
		//worker tile 1
		if(mouseOver(mxP, myP, game.draw1WorkerLocX, game.draw1WorkerLocY, game.TILE_DIM, game.TILE_DIM) && (SwingUtilities.isRightMouseButton(e)))
		{
			worker1Clicked = true;
			worker1ActionComplete = false;
		}
		else if(mouseOver(mxP, myP, game.draw2WorkerLocX, game.draw2WorkerLocY, game.TILE_DIM, game.TILE_DIM) && (SwingUtilities.isRightMouseButton(e)))
		{
			worker2Clicked = true;
			worker2ActionComplete = false;
		}
		else if(mouseOver(mxP, myP, game.draw3WorkerLocX, game.draw3WorkerLocY, game.TILE_DIM, game.TILE_DIM) && (SwingUtilities.isRightMouseButton(e)))
		{
			worker3Clicked = true;
			worker3ActionComplete = false;
		}

	}
	
	//events if mouse button is released
	//the listener in the game means we don't have to tick this.
	public void mouseReleased(MouseEvent e)
	{
		
		if(mouseOver(mxP, myP, game.draw1WorkerLocX, game.draw1WorkerLocY, game.TILE_DIM, game.TILE_DIM) && (SwingUtilities.isRightMouseButton(e)))
		{
			worker1Clicked = false;
		}
		if(mouseOver(mxP, myP, game.draw2WorkerLocX, game.draw2WorkerLocY, game.TILE_DIM, game.TILE_DIM) && (SwingUtilities.isRightMouseButton(e)))
		{
			worker2Clicked = false;
		}
		if(mouseOver(mxP, myP, game.draw3WorkerLocX, game.draw3WorkerLocY, game.TILE_DIM, game.TILE_DIM) && (SwingUtilities.isRightMouseButton(e)))
		{
			worker3Clicked = false;
		}
	}
	
	//checks if mouse is over our target
	protected boolean mouseOver(int mx, int my, int x, int y, int width, int height)
	{
		//if statement to check if we are in x range of a box
		if(mxP > x && mxP < x + width)
		{
			//if statement to check if we are in y range of a box
			if(myP > y && myP < y + height)
			{
				return true;
			}
		}

		return false;

	}
	
	public void tick()
	{

	}
	
	public void render(Graphics g)
	{
		//setting font
		Font f1 = new Font(Font.SERIF, Font.PLAIN, 50);
		g.setFont(f1);
		
		//writing instructions to pick amount of players
		g.setColor(Color.black);
		g.drawString("Select Amount of Players", Game.HEIGHT/(3) + 100, Game.HEIGHT/3);
		
		//text for 2 players
		g.setColor(Color.black);
		g.drawString("2 Players", Game.HEIGHT/(3) + 100 + widthOffset, Game.HEIGHT/3 + heightOffset);
		
		//box for 2 players
		g.setColor(Color.black);
		g.drawRect(550, 400, boxWidth, boxHeight);
		
		//text for 3 players
		g.setColor(Color.black);
		g.drawString("3 Players", Game.HEIGHT/(3) + 100 + widthOffset, Game.HEIGHT/3 + heightOffset*2);
		
		//box for 3 players
		g.setColor(Color.black);
		g.drawRect(550, 500, boxWidth, boxHeight);
		
		//text for 4 players
		g.setColor(Color.black);
		g.drawString("4 Players", Game.HEIGHT/(3) + 100 + widthOffset, Game.HEIGHT/3 + heightOffset*3);
		
		//box for 4 players
		g.setColor(Color.black);
		g.drawRect(550, 600, boxWidth, boxHeight);
	}

}
