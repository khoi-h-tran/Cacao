import java.awt.Graphics;

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
	
	protected int velX, velY;
	
	//Tile constructor class
	//constructor for GameObject class
	//initializes where the game object will start on window
	//gives the ID of the game object (i.e. player, tile, etc.)
	public GameObject(int x, int y, ID id, int TILE_DIM)
	{
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	//player constructor class
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

	
}
