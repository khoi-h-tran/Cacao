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
	
	//create a hash map for all jungle tiles
	HashMap<String, GameObject> objectJungle = new HashMap<String, GameObject>();
	
	//create a hash map for all worker tiles
	HashMap<String, GameObject> objectWorker = new HashMap<String, GameObject>();

	//create array list to hold keys for jungle tiles
	ArrayList<String> hashMapKeysJungle = new ArrayList<String>();
	
	//create array list to hold keys for worker tiles
	ArrayList<String> hashMapKeysWorker = new ArrayList<String>();
	
	//create array list to hold key for jungle tiles in deck
	ArrayList<String> deckKeysJungle = new ArrayList<String>();
	
	//create array list to hold key for worker tiles in deck
	ArrayList<String> deckKeysWorker = new ArrayList<String>();
	
	//create game loop for objects
	
	public void tick()
	{
		//loops through each object in jungle tile hash map
		for(int i = 0; i < objectJungle.size(); i++) 
		{
			GameObject tempObject = objectJungle.get(hashMapKeysJungle.get(i));
		
			//this is an abstract class that is called in the player or tile classes
			tempObject.tick();
		}
		
		//loops through each object in jungle tile hash map
		for(int i = 0; i < objectWorker.size(); i++) 
		{
			GameObject tempObject = objectWorker.get(hashMapKeysWorker.get(i));
		
			//this is an abstract class that is called in the player or tile classes
			tempObject.tick();
		}
	}
	
	public void render(Graphics g)
	{
		for(int i = 0; i < objectJungle.size(); i++)
		{
			GameObject tempObject = objectJungle.get(hashMapKeysJungle.get(i));
			
			//Code to check if keys and hash map are generating properly
			//System.out.println(hashMapKeys.get(i));
			//System.out.println(object.get(hashMapKeys.get(i)));
			
			//this is an abstract class that is called in the player or tile classes
			tempObject.render(g);
		}
		
		for(int i = 0; i < objectWorker.size(); i++)
		{
			GameObject tempObject = objectWorker.get(hashMapKeysWorker.get(i));
			
			//Code to check if keys and hash map are generating properly
			//System.out.println(hashMapKeys.get(i));
			//System.out.println(object.get(hashMapKeys.get(i)));
			
			//this is an abstract class that is called in the player or tile classes
			tempObject.render(g);
		}
	}
	
	//create method to add objects
	public void addObject(String key, GameObject object, ID id)
	{
		if(id == ID.JungleTile)
		{
			//adds an object to the jungle tile hash map
			this.objectJungle.put(key, object);
		}
		else if(id == ID.WorkerTile)
		{
			//adds an object to the jungle tile hash map
			this.objectWorker.put(key, object);
		}
		
	}
	
	public void addKey(String key, ID id)
	{
		if(id == ID.JungleTile)
		{
			//adds an object to the jungle tile hash map
			this.hashMapKeysJungle.add(key);
		}
		else if(id == ID.WorkerTile)
		{
			//adds an object to the jungle tile hash map
			this.hashMapKeysWorker.add(key);
		}
		
	}

	//create method to remove jungle tiles from game
	public void removeObject(String key, GameObject object, ID id)
	{
		if(id == ID.JungleTile)
		{
			//removes an object from the jungle tile hash map
			this.objectJungle.remove(key, object);
		}
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
	
	//clones the Jungle key array list. This will be used to shuffle the deck.
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
	
	//clones the Worker key array list. This will be used to shuffle the deck.
	public void cloneKey(ArrayList<String> hashMapKeys, ArrayList<String> deckKeys)
	{
		 
		 for(String key: hashMapKeys)
		 {
			 //(used for debugging only)
			 //System.out.println(key);
			 deckKeys.add(key);
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

	
}
