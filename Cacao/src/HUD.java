import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.Timer;
import java.util.TimerTask;

/* 
	File Name: HUD.java
	   Author: Khoi Tran
		 Date: Dec. 28, 2019 3:23:11 p.m.
  Description: This class is the layout
*/
public class HUD
{
	private Game game;
	private Handler handler;
	
	//using timer to flash if jungle tiles are supposed to be moved
	//taking in the current time in miliseconds
	long timer = System.currentTimeMillis();
	
	public void tick()
	{
		
	}
	
	public void render(Graphics g, Game game, Handler handler)
	{
		Font f1 = new Font(Font.SERIF, Font.PLAIN, 30);
		
		//Covering the jungle deck
		
		//Adding square to cover deck
		g.setColor(Color.gray);
		g.fillRect(game.deckLocJungleX, game.deckLocJungleY, game.TILE_DIM, game.TILE_DIM);
		//Drawing rectangle for border around the deck cover
		g.setColor(Color.black);
		g.drawRect(game.deckLocJungleX, game.deckLocJungleY, game.TILE_DIM, game.TILE_DIM);
		//drawing the word "Jungle"
		g.setFont(f1);
		g.setColor(Color.white);
		g.drawString("Jungle", game.deckLocJungleX + game.TILE_DIM/5, game.deckLocJungleY + game.TILE_DIM*1/4);
		//drawing the work "Deck"
		g.setFont(f1);
		g.setColor(Color.white);
		g.drawString("Deck", game.deckLocJungleX + game.TILE_DIM/4, game.deckLocJungleY + game.TILE_DIM*2/4);
		g.drawString(String.valueOf(handler.deckKeysJungle.size()), game.deckLocJungleX + game.TILE_DIM/4, game.deckLocJungleY + game.TILE_DIM*3/4);
		
		//Covering the worker deck
		
		//Adding square to cover deck
		g.setColor(Color.gray);
		g.fillRect(game.deckLocWorkerX, game.deckLocWorkerY, game.TILE_DIM, game.TILE_DIM);
		//Drawing rectangle for border around the deck cover
		g.setColor(Color.black);
		g.drawRect(game.deckLocWorkerX, game.deckLocWorkerY, game.TILE_DIM, game.TILE_DIM);
		//drawing the work "Jungle"
		g.setFont(f1);
		g.setColor(Color.white);
		g.drawString("Worker", game.deckLocWorkerX + game.TILE_DIM/5, game.deckLocWorkerY + game.TILE_DIM*1/4);
		//drawing the work "Deck"
		g.setFont(f1);
		g.setColor(Color.white);
		g.drawString("Deck", game.deckLocWorkerX + game.TILE_DIM/4, game.deckLocWorkerY + game.TILE_DIM*2/4);
		
		if(game.gameState == Game.STATE.Player1)
		{
			g.drawString(String.valueOf(handler.deckKeysWorkerP1.size()), game.deckLocWorkerX + game.TILE_DIM/4, game.deckLocWorkerY + game.TILE_DIM*3/4);
		}
		else if(game.gameState == Game.STATE.Player2)
		{
			g.drawString(String.valueOf(handler.deckKeysWorkerP2.size()), game.deckLocWorkerX + game.TILE_DIM/4, game.deckLocWorkerY + game.TILE_DIM*3/4);
		}
		else if(game.gameState == Game.STATE.Player3)
		{
			g.drawString(String.valueOf(handler.deckKeysWorkerP3.size()), game.deckLocWorkerX + game.TILE_DIM/4, game.deckLocWorkerY + game.TILE_DIM*3/4);
		}
		else if(game.gameState == Game.STATE.Player4)
		{
			g.drawString(String.valueOf(handler.deckKeysWorkerP4.size()), game.deckLocWorkerX + game.TILE_DIM/4, game.deckLocWorkerY + game.TILE_DIM*3/4);
		}
		
		//Separating jungle tile cards in display
		
		//drawing line at bottom of drawn jungle tiles
    Graphics2D g2 = (Graphics2D) g;
    g2.setColor(Color.black);
    Line2D lin = new Line2D.Float(Game.HEIGHT, game.draw1LocY + game.TILE_DIM, Game.WIDTH, game.draw1LocY + game.TILE_DIM);
    g2.draw(lin);
		//Labelling the drawn jungle tiles section
    //creating filled rectangle for the section separation
    g.setColor(Color.gray);
		g.fillRect(Game.HEIGHT, game.draw1LocY - game.TILE_DIM/3, Game.WIDTH - Game.HEIGHT, game.TILE_DIM/4);
		g.setColor(Color.black);
		g.drawRect(Game.HEIGHT, game.draw1LocY - game.TILE_DIM/3, Game.WIDTH - Game.HEIGHT, game.TILE_DIM/4);
		//creating text for the section
		//allowing text to flash if it is the time to move the jungle tiles
		g.setFont(f1);
		g.setColor(Color.white);//sets white for the first run (or else it will read in the black above for the rectangle)
		if(game.typeState == Game.TYPESTATE.Jungle)
		{
			if(System.currentTimeMillis() - timer < 1000)
			{
	  		g.setFont(f1);
	  		g.setColor(Color.white);
    	}
    	else if (System.currentTimeMillis() - timer > 1000  && System.currentTimeMillis() - timer < 2000)
    	{
	  		g.setFont(f1);
	  		g.setColor(Color.yellow);
    	}
    	else if (System.currentTimeMillis() - timer > 2000)
    	{
				//set the timer to current time to put the delta back to zero
				timer = System.currentTimeMillis();
    	}
		}
		else
		{
  		g.setFont(f1);
  		g.setColor(Color.white);
		}
		g.drawString("Jungle Tiles Drawn", Game.HEIGHT + game.TILE_DIM/3, game.draw1LocY - game.TILE_DIM/7);
    
		//Separating worker tile cards in display
		
		//drawing line at bottom of drawn jungle tiles
    g2.setColor(Color.black);
    Line2D lin2 = new Line2D.Float(Game.HEIGHT, game.draw1LocY + game.TILE_DIM, Game.WIDTH, game.draw1LocY + game.TILE_DIM);
    g2.draw(lin2);
		//Labeling the drawn jungle tiles section
    //creating filled rectangle for the section separation
    g.setColor(Color.gray);
		g.fillRect(Game.HEIGHT, game.draw1WorkerLocY - game.TILE_DIM/3, Game.WIDTH - Game.HEIGHT, game.TILE_DIM/4);
		g.setColor(Color.black);
		g.drawRect(Game.HEIGHT, game.draw1WorkerLocY - game.TILE_DIM/3, Game.WIDTH - Game.HEIGHT, game.TILE_DIM/4);
		//creating text for the section
		//allowing text to flash if it is the time to move the jungle tiles
		g.setFont(f1);
		g.setColor(Color.white);//sets white for the first run (or else it will read in the black above for the rectangle)
		if(game.typeState == Game.TYPESTATE.Worker)
		{
			if(System.currentTimeMillis() - timer < 1000)
			{
	  		g.setFont(f1);
	  		g.setColor(Color.white);
    	}
    	else if (System.currentTimeMillis() - timer > 1000  && System.currentTimeMillis() - timer < 2000)
    	{
	  		g.setFont(f1);
	  		g.setColor(Color.yellow);
    	}
    	else if (System.currentTimeMillis() - timer > 2000)
    	{
				//increment the timer by 3 seconds to put the delta back to zero
				timer = System.currentTimeMillis();
    	}
		}
		else
		{
  		g.setFont(f1);
  		g.setColor(Color.white);
		}
		g.drawString("Worker Tiles Drawn", Game.HEIGHT + game.TILE_DIM/3, game.draw1WorkerLocY - game.TILE_DIM/7);
		
		//Separating deck tile cards in display
		
		//Labelling the deck section
    //creating filled rectangle for the section separation
    g.setColor(Color.gray);
		g.fillRect(Game.HEIGHT, game.deckLocJungleY - game.TILE_DIM/3, Game.WIDTH - Game.HEIGHT, game.TILE_DIM/4);
		g.setColor(Color.black);
		g.drawRect(Game.HEIGHT, game.deckLocJungleY - game.TILE_DIM/3, Game.WIDTH - Game.HEIGHT, game.TILE_DIM/4);
		//creating text for the section
		g.setFont(f1);
		g.setColor(Color.white);
		g.drawString("Deck(Jungle/Worker)", Game.HEIGHT + game.TILE_DIM/3, game.deckLocJungleY - game.TILE_DIM/7);
	
		//Drawing the rectangle to separate play space and hand space
		g.setColor(Color.black);
		g.drawRect(Game.HEIGHT, 0, Game.WIDTH - Game.HEIGHT, Game.HEIGHT - game.TITLE_BAR);
		
		//Drawing the Player info section
		for(int i = 0; i < game.getNumPlayers(); i++)
		{
			//drawing the rectangle for each player
			g.setColor(Color.black);
			g.drawRect(Game.HEIGHT, game.TITLE_BAR*i*2, Game.WIDTH - Game.HEIGHT, game.TITLE_BAR*2);
			
			//Writing out the player number
			//code if you want to change the "player#" text
			/*
			switch(i)
			{
				case 0:
					g.setColor(Color.red);
					break;
				case 1:
					g.setColor(Color.magenta);
					break;
				case 2:
					g.setColor(Color.white);
					break;
				case 3:
					g.setColor(Color.yellow);
					break;
			}
			*/
			
			//Writing out the player number
			g.setColor(Color.black);
			g.drawString("Player " + (i+1) + ":", (Game.HEIGHT + 10), ((game.TITLE_BAR)*(i*2 + 1) - 10));
			
			//Writing out the score count
			//Gold Score
			g.drawString(String.valueOf(handler.scoreCountP1.get("Gold")), (Game.HEIGHT + 10 + game.RES_DIM * 6), (game.TITLE_BAR)*(i*2 + 1) - 10);
			
			//Cacao count
			g.drawString(String.valueOf(handler.scoreCountP1.get("Cacao")), Game.HEIGHT + 3 + game.RES_DIM*3/2, ((game.TITLE_BAR)*((i)*2 + 1) + game.RES_DIM));
			//Sun worshiping token count
			g.drawString(String.valueOf(handler.scoreCountP1.get("Sun Tokens")), Game.HEIGHT + 3 + game.RES_DIM*3/2 + game.iconOffset * 2, ((game.TITLE_BAR)*((i)*2 + 1) + game.RES_DIM));
			//Water count
			g.drawString(String.valueOf(handler.scoreCountP1.get("Water")), Game.HEIGHT + 3 + game.RES_DIM*3/2 + game.iconOffset * 4, ((game.TITLE_BAR)*((i)*2 + 1) + game.RES_DIM));
			//Temple Count
			g.drawString(String.valueOf(handler.scoreCountP1.get("Temple")), Game.HEIGHT + 3 + game.RES_DIM*3/2 + game.iconOffset * 6, ((game.TITLE_BAR)*((i)*2 + 1) + game.RES_DIM));
			
			
		}
		
	}

}
