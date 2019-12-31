import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/* 
	File Name: Tiles.java
	   Author: Khoi Tran
		 Date: Dec. 27, 2019 1:06:49 p.m.
  Description: This class creates the tiles
*/
public class Ressources extends GameObject
{
	//storing the tile dimension for the render
	//pulls from main game method in constructor
	private final int RES_DIM;
	private IDRessources idressources;

	private BufferedImage tile_image;
	
	//constructor method for ressource tiles
	public Ressources(int x, int y, ID id, IDRessources idressources, IDPlayer idplayer , int RES_DIM, SpriteSheet ss)
	{
		super(x, y, id,RES_DIM);
		this.RES_DIM = RES_DIM;
		this.idressources = idressources;
		
		/*
		 	Cacao(),//total: 20
			SunToken(),//total: 12
			Water(),
			Gold(),
			Temple(),
		 */
		
		switch(idressources)
		{
			case Cacao:
				tile_image = ss.grabImage(1, 2, RES_DIM, RES_DIM, RES_DIM);
				break;
			case SunToken:
				tile_image = ss.grabImage(2, 2, RES_DIM, RES_DIM, RES_DIM);
				break;
			case Temple:
				tile_image = ss.grabImage(3, 2, RES_DIM, RES_DIM, RES_DIM);
				break;
			case Gold:
				tile_image = ss.grabImage(4, 2, RES_DIM, RES_DIM, RES_DIM);
				break;
			case Water:
				tile_image = ss.grabImage(5, 2, RES_DIM, RES_DIM, RES_DIM);
				break;
			case Meeple:
				
				int meepleLoc = 0;
				
				if(idplayer == IDPlayer.Player1)
				{
					meepleLoc = 1;
				}
				else if(idplayer == IDPlayer.Player2)
				{
					meepleLoc = 2;
				}
				else if(idplayer == IDPlayer.Player3)
				{
					meepleLoc = 3;
				}
				else if(idplayer == IDPlayer.Player4)
				{
					meepleLoc = 4;
				}
				tile_image = ss.grabImage(meepleLoc, 1, RES_DIM, RES_DIM, RES_DIM);
				break;
		}
	}
	
	//code for how ressource tiles move
	public void tick()
	{
		//velX of tile
		//velY of tile
	}

	//renders the ressource tiles
	public void render(Graphics g)
	{
		g.drawImage(tile_image, x, y, null);
		g.setColor(Color.black);
		g.drawRect(x, y, RES_DIM, RES_DIM);
		//g.setColor(Color.green);
		//g.fillRect(x, y, TILE_DIM, TILE_DIM);
	}
}
