import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/* 
	File Name: Player.java
	   Author: Khoi Tran
		 Date: Dec. 27, 2019 12:14:50 p.m.
  Description: This class creates the player objects
*/

//extending makes Player a sub class and Game Object a super class
public class playerScore extends GameObject
{
	

	
	//constructor class for player
	public playerScore()
	{
		super();
		//Score tracker
		
		//for (Entry<String, Integer> entry : score.entrySet()) {
	  //System.out.println(entry.getKey() + ":" + entry.getValue().toString());
	}

	//these are the unimplemented abstract methods in the GAmeObject class
	@Override//this is generated automatically when adding abstract methods from extended class
	public void tick()
	{
		
	}

	//these are the unimplemented abstract methods in the GAmeObject class
	@Override//this is generated automatically when adding abstract methods from extended class
	public void render(Graphics g)
	{
		
	}
	
}
