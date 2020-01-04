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
	
	//create variables to pass to handler to indicate that we have right clicked on the worker tiles to rotate
	protected boolean worker1Clicked = false;
	protected boolean worker2Clicked = false;
	protected boolean worker3Clicked = false;
	//create variable so so that once the worker tile is right clicked, an action only happens once (i.e. 1 action between mouse click and release)
	protected boolean worker1ActionComplete = false;
	protected boolean worker2ActionComplete = false;
	protected boolean worker3ActionComplete = false;
	
	//create variables to pass to handler to indicate that we have left clicked on the worker tiles and held to move the tile
	protected boolean worker1Hold = false;
	protected boolean worker2Hold = false;
	protected boolean worker3Hold = false;
	//create variable so so that once the worker tile is left clicked, an action only happens once (i.e. 1 action between mouse click and release)
	protected boolean worker1HoldComplete = false;
	protected boolean worker2HoldComplete = false;
	protected boolean worker3HoldComplete = false;
	
	//mouse pressed
	protected int mxP;
	protected int myP;
	
	//mouse held
	protected int mxH;
	protected int myH;
	
	//variable to check if mouse was released
	protected boolean mouseReleased = false;
	
	public Select(Game game, Handler handler)
	{
		this.game = game;
		this.handler = handler;
	}
	
	public void mouseDragged(MouseEvent e)
	{
		if(SwingUtilities.isLeftMouseButton(e) && (worker1Hold == true || worker2Hold == true  || worker3Hold == true ))
		{
			//stores x position of mouse
			mxH = e.getX();
			
			//stores y position of mouse
			myH = e.getY();
		}
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
		
		//notify handler class if worker tiles are clicked by right mouse button
		
		//worker tile 1
		if(mouseOver(mxP, myP, game.draw1WorkerLocX, game.draw1WorkerLocY, game.TILE_DIM, game.TILE_DIM) && (SwingUtilities.isRightMouseButton(e)))
		{
			worker1Clicked = true;
			worker1ActionComplete = false;
		}
		//worker tile 2
		else if(mouseOver(mxP, myP, game.draw2WorkerLocX, game.draw2WorkerLocY, game.TILE_DIM, game.TILE_DIM) && (SwingUtilities.isRightMouseButton(e)))
		{
			worker2Clicked = true;
			worker2ActionComplete = false;
		}
		//worker tile 3
		else if(mouseOver(mxP, myP, game.draw3WorkerLocX, game.draw3WorkerLocY, game.TILE_DIM, game.TILE_DIM) && (SwingUtilities.isRightMouseButton(e)))
		{
			worker3Clicked = true;
			worker3ActionComplete = false;
		}
		
		//notify handler class if worker tiles are clicked by left mouse button
		
		//worker tile 1
		if(mouseOver(mxP, myP, game.draw1WorkerLocX, game.draw1WorkerLocY, game.TILE_DIM, game.TILE_DIM) && (SwingUtilities.isLeftMouseButton(e)))
		{
			System.out.println("worker1 hold");
			worker1Hold = true;
			worker1HoldComplete = false;
			mxH = game.draw1WorkerLocX + game.TILE_DIM/2;
			myH = game.draw1WorkerLocY + game.TILE_DIM/2;
		}
		//worker tile 2
		else if(mouseOver(mxP, myP, game.draw2WorkerLocX, game.draw2WorkerLocY, game.TILE_DIM, game.TILE_DIM) && (SwingUtilities.isLeftMouseButton(e)))
		{
			System.out.println("worker2 hold");
			
			worker2Hold = true;
			worker2HoldComplete = false;
			mxH = game.draw2WorkerLocX + game.TILE_DIM/2;
			myH = game.draw2WorkerLocY + game.TILE_DIM/2;
			/*
			//set so that the worker tile doesn't move when you don't click and hold on it
			handler.objectWorkerP1.get(handler.drawLocWorker1.get(2)).setWorker2Hold(true);
			handler.objectWorkerP1.get(handler.drawLocWorker1.get(2)).setWorker2HoldComplete(false);
			*/
		}
		//worker tile 3
		else if(mouseOver(mxP, myP, game.draw3WorkerLocX, game.draw3WorkerLocY, game.TILE_DIM, game.TILE_DIM) && (SwingUtilities.isLeftMouseButton(e)))
		{
			System.out.println("worker3 hold");
			worker3Hold = true;
			worker3HoldComplete = false;
			mxH = game.draw3WorkerLocX + game.TILE_DIM/2;
			myH = game.draw3WorkerLocY + game.TILE_DIM/2;
			/*
			//set so that the worker tile doesn't move when you don't click and hold on it
			handler.objectWorkerP1.get(handler.drawLocWorker1.get(3)).setWorker3Hold(true);
			handler.objectWorkerP1.get(handler.drawLocWorker1.get(3)).setWorker3HoldComplete(false);
			*/
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
		else if(mouseOver(mxP, myP, game.draw2WorkerLocX, game.draw2WorkerLocY, game.TILE_DIM, game.TILE_DIM) && (SwingUtilities.isRightMouseButton(e)))
		{
			worker2Clicked = false;
		}
		else if(mouseOver(mxP, myP, game.draw3WorkerLocX, game.draw3WorkerLocY, game.TILE_DIM, game.TILE_DIM) && (SwingUtilities.isRightMouseButton(e)))
		{
			worker3Clicked = false;
		}
		
		if(mouseOver(mxP, myP, game.draw1WorkerLocX, game.draw1WorkerLocY, game.TILE_DIM, game.TILE_DIM) && (SwingUtilities.isLeftMouseButton(e)))
		{
      //complete the action to prevent indefinite spinning/rotating of the tile
			worker1Hold = false;
      worker1HoldComplete = true;
      mouseReleased = true;
		}
		else if(mouseOver(mxP, myP, game.draw2WorkerLocX, game.draw2WorkerLocY, game.TILE_DIM, game.TILE_DIM) && (SwingUtilities.isLeftMouseButton(e)))
		{
      //complete the action to prevent indefinite spinning/rotating of the tile
			worker2Hold = false;
      worker2HoldComplete = true;
      mouseReleased = true;
		}
		else if(mouseOver(mxP, myP, game.draw3WorkerLocX, game.draw3WorkerLocY, game.TILE_DIM, game.TILE_DIM) && (SwingUtilities.isLeftMouseButton(e)))
		{
      //complete the action to prevent indefinite spinning/rotating of the tile
			worker3Hold = false;
      worker3HoldComplete = true;
      mouseReleased = true;
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
