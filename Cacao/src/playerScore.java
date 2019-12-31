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
	//Score tracker
	// Gold, cacao beans, sun tokens, temples, water
	HashMap<String, Integer> score = new HashMap<String, Integer>();
	
	//constructor class for player
	public playerScore()
	{
		super();
		score.put("Gold", 0);
		score.put("Cacao Beans", 0);
		score.put("Sun Tokens", 0);
		score.put("Water", 0);
		
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
