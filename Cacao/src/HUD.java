import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/* 
	File Name: HUD.java
	   Author: Khoi Tran
		 Date: Dec. 28, 2019 3:23:11 p.m.
  Description: This class is the layout
*/
public class HUD
{
	private Game game;

	public void tick()
	{
		
	}
	
	public void render(Graphics g, Game game)
	{
		Font f1 = new Font(Font.SERIF, Font.PLAIN, 30);
		g.setColor(Color.gray);
		g.fillRect((Game.HEIGHT + game.TILE_DIM), (Game.HEIGHT - game.TILE_DIM - game.TITLE_BAR), game.TILE_DIM, game.TILE_DIM);
		g.setColor(Color.black);
		g.drawRect((Game.HEIGHT + game.TILE_DIM), (Game.HEIGHT - game.TILE_DIM - game.TITLE_BAR), game.TILE_DIM, game.TILE_DIM);
		g.setFont(f1);
		g.setColor(Color.white);
		g.drawString("Deck", (Game.HEIGHT + game.TILE_DIM + game.TILE_DIM/3), (Game.HEIGHT - game.TILE_DIM/2 - game.TITLE_BAR));
		g.setColor(Color.black);
		g.drawRect(Game.HEIGHT, 0, Game.WIDTH - Game.HEIGHT, Game.HEIGHT - game.TITLE_BAR);
		for(int i = 0; i < game.getNumPlayers(); i++)
		{
			g.setColor(Color.black);
			g.drawRect(Game.HEIGHT, game.TITLE_BAR*i, Game.WIDTH - Game.HEIGHT, game.TITLE_BAR);
			
			g.setColor(Color.black);
			g.drawString("Player " + (i+1) + ":", (Game.HEIGHT + 10), ((game.TITLE_BAR)*(i + 1) - 10));
		}
		
	}

}
