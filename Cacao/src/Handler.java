import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

/* 
	File Name: handler.java
	   Author: Khoi Tran
		 Date: Dec. 27, 2019 11:43:24 a.m.
  Description: MAintains, updates, renders all objects (i.e. players, game pieces, etc) in game
*/
public class Handler
{
	
	//create a linked list for all objects
	HashMap<String, GameObject> object = new HashMap<String, GameObject>();

	//create array list to hold keys
	ArrayList<String> hashMapKeys = new ArrayList<String>();
	
	//create game loop for objects
	
	public void tick()
	{
		//loops through each object in hash map
		for(int i = 0; i < object.size(); i++) 
		{
			GameObject tempObject = object.get(hashMapKeys.get(i));
		
			//this is an abstract class that is called in the player or tile classes
			tempObject.tick();
		}
	}
	
	public void render(Graphics g)
	{
		for(int i = 0; i < object.size(); i++)
		{
			GameObject tempObject = object.get(hashMapKeys.get(i));
			
			//Code to check if keys and hash map are generating properly
			//System.out.println(hashMapKeys.get(i));
			//System.out.println(object.get(hashMapKeys.get(i)));
			
			//this is an abstract class that is called in the player or tile classes
			tempObject.render(g);
		}
	}
	
	//create method to add objects
	public void addObject(String key, GameObject object)
	{
		//adds an object to the linked list
		this.object.put(key, object);
	}
	
	public void addKey(String key)
	{
		this.hashMapKeys.add(key);
	}

	//create method to remove objects
	public void removeObject(String key, GameObject object)
	{
		//adds an object to the linked list
		this.object.remove(key, object);
	}
	
}
