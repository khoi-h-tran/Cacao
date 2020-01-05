import java.awt.Graphics;
import java.awt.image.BufferedImage;

/* 
	File Name: GameObject.java
	   Author: Khoi Tran
		 Date: Dec. 27, 2019 11:44:20 a.m.
  Description: This class defines characteristics of all game objects
*/

//abstract classes cannot be instantiated
//abstract class is a bunch of variables and methods that can be used to create another class
//You can use it to extend other classes
//i.e. you can use GameObject methods through any other class that you extend GameObject onto
public abstract class GameObject
{
	//protected means only objects that inherit game object (through extending) can use it
	protected int x, y;
	
	//create instance of enumeration
	protected ID id;
	protected IDJungle idjungle;
	
	//rotation variable for worker tile
	//0 = none, 1 = 90 deg, 2 = 90deg x2, 3 = 90 deg x 3, reset to 0 after 3
	protected int rotation = 0;
	
	//this is a boolean variable to determine if the rotation was changed and if the rotation ont he score scheme has been done (avoids indefinite rotating of score scheme)
	protected boolean changed = true;
	
	protected int velX, velY;
	
	protected int [] scoreScheme = new int [4];
	
	//to store and get the tile image stored in the subclass of worker tiles
	//we need this to be able to rotate properly in the select class
	protected BufferedImage tile_image;
	
	private WorkerTiles workertiles;
	
	private Game game;
	private Select select;
	private Handler handler;
	
	//create variables to pass to handler to indicate that we have left clicked on the worker tiles and held to move the tile
	protected boolean worker1Hold = false;
	protected boolean worker2Hold = false;
	protected boolean worker3Hold = false;
	//create variable so so that once the worker tile is left clicked, an action only happens once (i.e. 1 action between mouse click and release)
	protected boolean worker1HoldComplete = false;
	protected boolean worker2HoldComplete = false;
	protected boolean worker3HoldComplete = false;
	
	//Tile constructor class
	//constructor for GameObject class
	//initializes where the game object will start on window
	//gives the ID of the game object (i.e. player, tile, etc.)
	
	//jungle tile constructor class
	public GameObject(int x, int y, ID id, int TILE_DIM)
	{
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	//worker tile constructor class
	public GameObject(int x, int y, ID id, int TILE_DIM, int rotation)
	{
		this.x = x;
		this.y = y;
		this.id = id;
		this.rotation = rotation;
	}
	
	//empty constructor to create the player score
	public GameObject()
	{
		
	}
	
	//abstract methods cannot have code in it
	//they are here so that every other class that uses the GameObject abstract class has to have some code for the tick and render abstrac methods
	//used in player or tile classes
	public abstract void tick();
	//graphics requires import lib
	//used in player or tile classes
	public abstract void render(Graphics g);
	
	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public ID getId()
	{
		return id;
	}

	public void setId(ID id)
	{
		this.id = id;
	}

	public int getVelX()
	{
		return velX;
	}

	public void setVelX(int velX)
	{
		this.velX = velX;
	}

	public int getVelY()
	{
		return velY;
	}

	public void setVelY(int velY)
	{
		this.velY = velY;
	}

	public BufferedImage getTile_image()
	{
		return tile_image;
	}
	
	public void setTile_image(BufferedImage tile_image)
	{
		 this.tile_image = tile_image;
	}
	
	public boolean isWorker1Hold()
	{
		return worker1Hold;
	}

	public void setWorker1Hold(boolean worker1Hold)
	{
		this.worker1Hold = worker1Hold;
	}

	public boolean isWorker2Hold()
	{
		return worker2Hold;
	}

	public void setWorker2Hold(boolean worker2Hold)
	{
		this.worker2Hold = worker2Hold;
	}

	public boolean isWorker3Hold()
	{
		return worker3Hold;
	}

	public void setWorker3Hold(boolean worker3Hold)
	{
		this.worker3Hold = worker2Hold;
	}

	public boolean isWorker1HoldComplete()
	{
		return worker1HoldComplete;
	}

	public void setWorker1HoldComplete(boolean worker1HoldComplete)
	{
		this.worker1HoldComplete = worker1HoldComplete;
	}

	public boolean isWorker2HoldComplete()
	{
		return worker2HoldComplete;
	}

	public void setWorker2HoldComplete(boolean worker2HoldComplete)
	{
		this.worker2HoldComplete = worker2HoldComplete;
	}

	public boolean isWorker3HoldComplete()
	{
		return worker3HoldComplete;
	}

	public void setWorker3HoldComplete(boolean worker3HoldComplete)
	{
		this.worker3HoldComplete = worker3HoldComplete;
	}

	public void incrementRotation()
	{
		if(rotation < 3)
		{
			rotation++;
			changed = true;
		}
		else if(rotation == 3)
		{
			rotation = 0;
			changed = true;
		}
	}
	
	public int [] getScoreScheme()
	{
		return scoreScheme;
	}
	
	public void setScoreScheme(int a, int b, int c, int d)
	{
		this.scoreScheme[0] = a;
		this.scoreScheme[1] = b;
		this.scoreScheme[2] = c;
		this.scoreScheme[3] = d;
	}
}
