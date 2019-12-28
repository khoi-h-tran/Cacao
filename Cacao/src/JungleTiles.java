import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/* 
	File Name: Tiles.java
	   Author: Khoi Tran
		 Date: Dec. 27, 2019 1:06:49 p.m.
  Description: This class creates the tiles
*/
public class JungleTiles extends GameObject
{
	//storing the tile dimension for the render
	//pulls from main game method in constructor
	private final int TILE_DIM;
	private IDJungle idjungle;
	
	private BufferedImage tile_image;
	
	//constructor method for jungle tiles
	public JungleTiles(int x, int y, ID id, IDJungle idjungle, int TILE_DIM, SpriteSheet ss)
	{
		super(x, y, id, TILE_DIM);
		this.TILE_DIM = TILE_DIM;
		this.idjungle = idjungle;
		
		/*
	  GoldMinex2(),//total: 1
	  
		Plantationx2(),//total: 2
		SellingPricex2(),//total: 2
		SunWorshippingSite(),//total: 2, if 2 players - 1
		GoldMinex1(),//total: 2, if 2 players - 1
		
		SellingPricex3(),//total: 3, if 2 players - 1
		Water(),//total: 3, if 2 players - 1
		
		SellingPricex4(),//total: 4
		
		Temple(),//total: 5, if 2 players - 1
		
		Plantationx1(),//total: 6, if 2 players - 2
	 */
		switch(idjungle)
		{
			//total 1
			case GoldMinex2:
				tile_image = ss.grabImage(1, 1, TILE_DIM, TILE_DIM, TILE_DIM);
				break;
			//total 2
			case Plantationx2:
				tile_image = ss.grabImage(1, 2, TILE_DIM, TILE_DIM, TILE_DIM);
				break;
			case SellingPricex2:
				tile_image = ss.grabImage(2, 2, TILE_DIM, TILE_DIM, TILE_DIM);
				break;
			case SunWorshippingSite:
				tile_image = ss.grabImage(3, 2, TILE_DIM, TILE_DIM, TILE_DIM);
				break;
			case GoldMinex1:
				tile_image = ss.grabImage(4, 2, TILE_DIM, TILE_DIM, TILE_DIM);
				break;
			//total 3
			case SellingPricex3:
				tile_image = ss.grabImage(1, 3, TILE_DIM, TILE_DIM, TILE_DIM);
				break;
			case Water:
				tile_image = ss.grabImage(2, 3, TILE_DIM, TILE_DIM, TILE_DIM);
				break;
			//total 4
			case SellingPricex4:
				tile_image = ss.grabImage(1, 4, TILE_DIM, TILE_DIM, TILE_DIM);
				break;
			//total 5
			case Temple:
				tile_image = ss.grabImage(5, 1, TILE_DIM, TILE_DIM, TILE_DIM);
				break;
			//total 6
			case Plantationx1:
				tile_image = ss.grabImage(1, 6, TILE_DIM, TILE_DIM, TILE_DIM);
				break;
		}
		
	}
	
	//code for how jungle tiles move
	public void tick()
	{
		//velX of tile
		//velY of tile
	}

	//renders the jungle tiles
	public void render(Graphics g)
	{
		g.drawImage(tile_image, x, y, null);
		g.setColor(Color.black);
		g.drawRect(x, y, TILE_DIM, TILE_DIM);
		//g.setColor(Color.green);
		//g.fillRect(x, y, TILE_DIM, TILE_DIM);
	}
}
