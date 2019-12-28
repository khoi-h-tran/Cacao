import java.awt.Color;
import java.awt.Graphics;

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
	
	//constructor method for jungle tiles
	public JungleTiles(int x, int y, ID id, IDJungle idjungle, int TILE_DIM)
	{
		super(x, y, id, TILE_DIM);
		this.TILE_DIM = TILE_DIM;
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
		g.setColor(Color.green);
		g.fillRect(x, y, TILE_DIM, TILE_DIM);
	}
}
