import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/* 
	File Name: handler.java
	   Author: Khoi Tran
		 Date: Dec. 27, 2019 11:43:24 a.m.
  Description: Maintains, updates, renders all objects (i.e. players, game pieces, etc) in game
*/
public class Handler
{
	
	//create a hash map for all objects
	HashMap<String, GameObject> object = new HashMap<String, GameObject>();

	//create array list to hold keys
	ArrayList<String> hashMapKeys = new ArrayList<String>();
	
	//create array list to hold key for jungle tiles in deck
	ArrayList<String> deckKeys = new ArrayList<String>();
	
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
		//adds an object to the hash map
		this.object.put(key, object);
	}
	
	public void addKey(String key)
	{
		this.hashMapKeys.add(key);
	}

	//create method to remove jungle tiles from game
	public void removeObject(String key, GameObject object)
	{
		//removes an object to the hash map
		this.object.remove(key, object);
	}
	
	//create method to remove jungle tiles from deck
	public void removeFromDeck(ArrayList<String> deckKeys)
	{
	//removes an object from the draw deck array list (to indicate it no longer can be drawn)
	deckKeys.remove(0);
	}
	
	//create method to draw jungle tiles from deck
	public void drawFromDeck(ArrayList<String> deckKeys, HashMap<String, GameObject> object, int x, int y)
	{
		//pulls the object from the hash map because this is where the object is actually stored and moved around
		//the deckKeys at 0 index is just the top most card in the deck
		object.get(deckKeys.get(0)).setX(x);
		object.get(deckKeys.get(0)).setY(y);
	}
	
	/*
	  public static List<Dog> cloneList(List<Dog> list) {
    List<Dog> clone = new ArrayList<Dog>(list.size());
    for (Dog item : list) clone.add(item.clone());
    return clone;
}
	 */
	
	//clones the key array list. This will be used to shuffle the deck.
	public void cloneKey(ArrayList<String> hashMapKeys, ArrayList<String> deckKeys, String remove1, String remove2)
	{
		 
		 for(String key: hashMapKeys)
		 {
			 //(used for debugging only)
			 //System.out.println(key);
			 if(key.equals(remove1) || key.equals(remove2))
			 {
				 continue;
			 }
			 else
			 {
				 deckKeys.add(key);
			 }
		 }
		 
		 //(used for debugging only)
		 //System.out.println(hashMapKeys.size());
		 //System.out.println(deckKeys.size());
		 
		 //prints out keys in deck (used for debugging only)
		 /*
		 for(String key: deckKeys)
		 {
			 System.out.println(key);
		 }
		 */
	}
	
	//shuffles the deck using the keys
	public void shuffleDeck(ArrayList<String> deckKeys)
	{
		Collections.shuffle(deckKeys);
		
		//prints out keys in deck (used for debugging only)
		 /*
		for(String key: deckKeys)
		 {
			 System.out.println(key);
		 }
		*/
	}
	
	//draw card from jungle tile deck
	public void drawDeck(ArrayList<String> deckKeys)
	{
		
	}
	
}
