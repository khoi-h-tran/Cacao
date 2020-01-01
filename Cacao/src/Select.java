import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/* 
	File Name: Menu.java
	   Author: Khoi Tran
		 Date: Jan. 1, 2020 12:23:01 p.m.
  Description: Handles the select screen
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
	
	public Select(Game game, Handler handler)
	{
		this.game = game;
		this.handler = handler;
	}
	 
	public void mousePressed(MouseEvent e)
	{
		//stores x position of mouse
		int mx = e.getX();
		
		//stores y position of mouse
		int my = e.getY();
		
		if(game.gameState == Game.STATE.Select)
		{
			if(mouseOver(mx, my, 550, 400, 275, 75))
			{
				game.numPlayers = 2;
				game.gameState = Game.STATE.Player1;
				game.initGame();
			}
			else if(mouseOver(mx, my, 550, 500, 275, 75))
			{
				game.numPlayers = 3;
				game.gameState = Game.STATE.Player1;
				game.initGame();
			}
			else if(mouseOver(mx, my, 550, 600, 275, 75))
			{
				game.numPlayers = 4;
				game.gameState = Game.STATE.Player1;
				game.initGame();
			}
		}
		
		if(game.typeState == Game.TYPESTATE.Jungle && game.turnState == Game.TURNSTATE.Move)
		{
			if(game.gameState == Game.STATE.Player1)
			{
				if(mouseOver(mx, my, game.deckLocWorkerX, game.deckLocWorkerY, game.TILE_DIM, game.TILE_DIM))
				{
					
				}
			}
			else if(game.gameState == Game.STATE.Player2)
			{
				
			}
			else if(game.gameState == Game.STATE.Player3)
			{
				
			}
			else if(game.gameState == Game.STATE.Player4)
			{
				
			}
		}

	}
	
	public void mouseReleased(MouseEvent e)
	{
		
	}
	
	//checks if mouse is over our target
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height)
	{
		//if statement to check if we are in x range of a box
		if(mx > x && mx < x + boxWidth)
		{
			//if statement to check if we are in y range of a box
			if(my > y && my < y + boxHeight)
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
