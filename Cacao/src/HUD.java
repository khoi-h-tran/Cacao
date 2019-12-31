import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

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
		//drawing the work "Jungle"
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
		g.setFont(f1);
		g.setColor(Color.white);
		g.drawString("Jungle Tiles Drawn", Game.HEIGHT + game.TILE_DIM/3, game.draw1LocY - game.TILE_DIM/7);
    
		//Separating worker tile cards in display
		
		//drawing line at bottom of drawn jungle tiles
    g2.setColor(Color.black);
    Line2D lin2 = new Line2D.Float(Game.HEIGHT, game.draw1LocY + game.TILE_DIM, Game.WIDTH, game.draw1LocY + game.TILE_DIM);
    g2.draw(lin2);
		//Labelling the drawn jungle tiles section
    //creating filled rectangle for the section separation
    g.setColor(Color.gray);
		g.fillRect(Game.HEIGHT, game.draw1WorkerLocY - game.TILE_DIM/3, Game.WIDTH - Game.HEIGHT, game.TILE_DIM/4);
		g.setColor(Color.black);
		g.drawRect(Game.HEIGHT, game.draw1WorkerLocY - game.TILE_DIM/3, Game.WIDTH - Game.HEIGHT, game.TILE_DIM/4);
		//creating text for the section
		g.setFont(f1);
		g.setColor(Color.white);
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
			g.setColor(Color.black);
			g.drawRect(Game.HEIGHT, game.TITLE_BAR*i, Game.WIDTH - Game.HEIGHT, game.TITLE_BAR);
			
			g.setColor(Color.black);
			g.drawString("Player " + (i+1) + ":", (Game.HEIGHT + 10), ((game.TITLE_BAR)*(i + 1) - 10));
		}
		
	}

}
