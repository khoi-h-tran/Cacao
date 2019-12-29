import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/* 
	File Name: Tiles.java
	   Author: Khoi Tran
		 Date: Dec. 27, 2019 1:06:49 p.m.
  Description: This class creates the tiles
*/
public class WorkerTiles extends GameObject
{
	//storing the tile dimension for the render
	//pulls from main game method in constructor
	private final int TILE_DIM;
	private IDWorker idworker;
	
	private BufferedImage tile_image;
	
	//constructor method for jungle tiles
	public WorkerTiles(int x, int y, ID id, IDWorker idworker, int TILE_DIM, SpriteSheet ss)
	{
		super(x, y, id,TILE_DIM);
		this.TILE_DIM = TILE_DIM;
		this.idworker = idworker;
		
		/*
	  OneOneOneOne(),//total: 4, if 3 or 4 players minus 1 tile
		TwoOneZeroOne(),//total: 5,  if 4 players minus 1 tile
		ThreeZeroZeroOne(),//total: 1
		ThreeOneZeroZero();//total: 1
	 */
		switch(idworker)
		{
			//total 1
			case OneOneOneOne:
				tile_image = ss.grabImage(1, 7, TILE_DIM, TILE_DIM, TILE_DIM);
				break;
			//total 2
			case TwoOneZeroOne:
				tile_image = ss.grabImage(2, 7, TILE_DIM, TILE_DIM, TILE_DIM);
				break;
			case ThreeZeroZeroOne:
				tile_image = ss.grabImage(3, 7, TILE_DIM, TILE_DIM, TILE_DIM);
				break;
			case ThreeOneZeroZero:
				tile_image = ss.grabImage(4, 7, TILE_DIM, TILE_DIM, TILE_DIM);
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
