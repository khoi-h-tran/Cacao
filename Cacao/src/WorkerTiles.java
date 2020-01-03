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
	protected int [] scoreScheme = new int [4];
	
	BufferedImage tile_image0; //0 rotation
	BufferedImage tile_image1; //90 deg rotation
	BufferedImage tile_image2; //90 deg x2 rotation
	BufferedImage tile_image3; //90 deg x3 rotation
	
	//constructor method for jungle tiles
	public WorkerTiles(int x, int y, ID id, IDWorker idworker, int TILE_DIM, SpriteSheet ss, IDPlayer idplayer)
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
		int rowNum = 0;
		
		switch(idplayer)
		{
			case Player1:
				idplayer = IDPlayer.Player1;
				rowNum = 1;
				break;
			case Player2:
				idplayer = IDPlayer.Player2;
				rowNum = 2;
				break;
			case Player3:
				idplayer = IDPlayer.Player3;
				rowNum = 3;
				break;
			case Player4:
				idplayer = IDPlayer.Player4;
				rowNum = 4;
				break;
		}
		
		switch(idworker)
		{
			//total 1
			case OneOneOneOne:
				tile_image0 = ss.grabImage(1, rowNum, TILE_DIM, TILE_DIM, TILE_DIM);
				tile_image1 = ss.grabImage(1, rowNum + 4, TILE_DIM, TILE_DIM, TILE_DIM);
				tile_image2 = ss.grabImage(1, rowNum + 8, TILE_DIM, TILE_DIM, TILE_DIM);
				tile_image3 = ss.grabImage(1 + 4, rowNum, TILE_DIM, TILE_DIM, TILE_DIM);
				scoreScheme[0] = 1;
				scoreScheme[1] = 1;
				scoreScheme[2] = 1;
				scoreScheme[3] = 1;
				break;
			//total 2
			case TwoOneZeroOne:
				tile_image0 = ss.grabImage(2, rowNum, TILE_DIM, TILE_DIM, TILE_DIM);
				tile_image1 = ss.grabImage(2, rowNum + 4, TILE_DIM, TILE_DIM, TILE_DIM);
				tile_image2 = ss.grabImage(2, rowNum + 8, TILE_DIM, TILE_DIM, TILE_DIM);
				tile_image3 = ss.grabImage(2 + 4, rowNum, TILE_DIM, TILE_DIM, TILE_DIM);
				scoreScheme[0] = 1;
				scoreScheme[1] = 2;
				scoreScheme[2] = 1;
				scoreScheme[3] = 0;
				break;
			case ThreeZeroZeroOne:
				tile_image0 = ss.grabImage(3, rowNum, TILE_DIM, TILE_DIM, TILE_DIM);
				tile_image1 = ss.grabImage(3, rowNum + 4, TILE_DIM, TILE_DIM, TILE_DIM);
				tile_image2 = ss.grabImage(3, rowNum + 8, TILE_DIM, TILE_DIM, TILE_DIM);
				tile_image3 = ss.grabImage(3 + 4, rowNum, TILE_DIM, TILE_DIM, TILE_DIM);
				scoreScheme[0] = 1;
				scoreScheme[1] = 3;
				scoreScheme[2] = 0;
				scoreScheme[3] = 0;
				break;
			case ThreeOneZeroZero:
				tile_image0 = ss.grabImage(4, rowNum, TILE_DIM, TILE_DIM, TILE_DIM);
				tile_image1 = ss.grabImage(4, rowNum + 4, TILE_DIM, TILE_DIM, TILE_DIM);
				tile_image2 = ss.grabImage(4, rowNum + 8, TILE_DIM, TILE_DIM, TILE_DIM);
				tile_image3 = ss.grabImage(4 + 4, rowNum, TILE_DIM, TILE_DIM, TILE_DIM);
				scoreScheme[0] = 0;
				scoreScheme[1] = 3;
				scoreScheme[2] = 1;
				scoreScheme[3] = 0;
				break;
		}
		
	}
	
	//rotates the score scheme to match the rotation of the tile
	public void rotateScoreScheme(int [] scoreScheme)
	{
		int temp0 = scoreScheme[0];
		scoreScheme[0] = scoreScheme[3];
		scoreScheme[3] = scoreScheme[2];
		scoreScheme[2] = scoreScheme[1];
		scoreScheme[1] = temp0;
	}

	//code for worker tiles move
	public void tick()
	{
		if(changed == true)
		{
			if(rotation == 0)
			{
				rotateScoreScheme(scoreScheme);
				setScoreScheme(scoreScheme[0], scoreScheme[1], scoreScheme[2], scoreScheme[3]);
			}
			else if(rotation == 1)
			{
				rotateScoreScheme(scoreScheme);
				setScoreScheme(scoreScheme[0], scoreScheme[1], scoreScheme[2], scoreScheme[3]);
			}
			else if(rotation == 2)
			{
				rotateScoreScheme(scoreScheme);
				setScoreScheme(scoreScheme[0], scoreScheme[1], scoreScheme[2], scoreScheme[3]);
			}
			else if(rotation == 3)
			{
				rotateScoreScheme(scoreScheme);
				setScoreScheme(scoreScheme[0], scoreScheme[1], scoreScheme[2], scoreScheme[3]);
			}
			changed = false;
		}

	}

	//renders the jungle tiles
	public void render(Graphics g)
	{
		if(rotation == 0)
		{
			g.drawImage(tile_image0, x, y, null);
		}
		else if(rotation == 1)
		{
			g.drawImage(tile_image1, x, y, null);
		}
		else if(rotation == 2)
		{
			g.drawImage(tile_image2, x, y, null);
		}
		else if(rotation == 3)
		{
			g.drawImage(tile_image3, x, y, null);
		}
		
		g.setColor(Color.black);
		g.drawRect(x, y, TILE_DIM, TILE_DIM);
		//g.setColor(Color.green);
		//g.fillRect(x, y, TILE_DIM, TILE_DIM);
	}

}
