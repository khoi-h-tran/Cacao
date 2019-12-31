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
	private Game game;
	
	//create a hash map for all jungle tiles
	HashMap<String, GameObject> objectJungle = new HashMap<String, GameObject>();

	//create array list to hold keys for jungle tiles
	ArrayList<String> hashMapKeysJungle = new ArrayList<String>();
	
	//create array list to hold key for jungle tiles in deck
	ArrayList<String> deckKeysJungle = new ArrayList<String>();
	
	//create a hash map for all worker tiles
	//One for each player
	HashMap<String, GameObject> objectWorkerP1 = new HashMap<String, GameObject>();
	HashMap<String, GameObject> objectWorkerP2 = new HashMap<String, GameObject>();
	HashMap<String, GameObject> objectWorkerP3 = new HashMap<String, GameObject>();
	HashMap<String, GameObject> objectWorkerP4 = new HashMap<String, GameObject>();
	
	//create array list to hold keys for worker tiles
	//One for each player
	ArrayList<String> hashMapKeysWorkerP1 = new ArrayList<String>();
	ArrayList<String> hashMapKeysWorkerP2 = new ArrayList<String>();
	ArrayList<String> hashMapKeysWorkerP3 = new ArrayList<String>();
	ArrayList<String> hashMapKeysWorkerP4 = new ArrayList<String>();
	
	//create array list to hold key for worker tiles in deck
	//One for each player
	ArrayList<String> deckKeysWorkerP1 = new ArrayList<String>();
	ArrayList<String> deckKeysWorkerP2 = new ArrayList<String>();
	ArrayList<String> deckKeysWorkerP3 = new ArrayList<String>();
	ArrayList<String> deckKeysWorkerP4 = new ArrayList<String>();
	
	//create a hash map for all player scores
	//One for each player
	HashMap<String, GameObject> scoreP1 = new HashMap<String, GameObject>();
	HashMap<String, GameObject> scoreP2 = new HashMap<String, GameObject>();
	HashMap<String, GameObject> scoreP3 = new HashMap<String, GameObject>();
	HashMap<String, GameObject> scoreP4 = new HashMap<String, GameObject>();

	//create game loop for objects
	
	public void tick(Game game)
	{
		//loops through each object in jungle tile hash map
		for(int i = 0; i < objectJungle.size(); i++) 
		{
			GameObject tempObject = objectJungle.get(hashMapKeysJungle.get(i));
		
			//this is an abstract class that is called in the player or tile classes
			tempObject.tick();
		}
		
		if(game.numPlayers >= 1)
		{
			//loops through each object in worker tile hash map
			for(int i = 0; i < objectWorkerP1.size(); i++) 
			{
				GameObject tempObject = objectWorkerP1.get(hashMapKeysWorkerP1.get(i));
			
				//this is an abstract class that is called in the player or tile classes
				tempObject.tick();
			}
		}
		
		if(game.numPlayers >= 2)
		{
			for(int i = 0; i < objectWorkerP2.size(); i++) 
			{
				GameObject tempObject = objectWorkerP2.get(hashMapKeysWorkerP2.get(i));
			
				//this is an abstract class that is called in the player or tile classes
				tempObject.tick();
			}
		}
		
		if(game.numPlayers >= 3)
		{
			for(int i = 0; i < objectWorkerP3.size(); i++) 
			{
				GameObject tempObject = objectWorkerP3.get(hashMapKeysWorkerP3.get(i));
			
				//this is an abstract class that is called in the player or tile classes
				tempObject.tick();
			}
		}
		
		if(game.numPlayers >= 4)
		{
			for(int i = 0; i < objectWorkerP4.size(); i++) 
			{
				GameObject tempObject = objectWorkerP4.get(hashMapKeysWorkerP4.get(i));
			
				//this is an abstract class that is called in the player or tile classes
				tempObject.tick();
			}
		}
	
	}
	
	public void render(Graphics g, Game game)
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
		
		if(game.gameState == Game.STATE.Player1)
		{
			//loops through each object in worker tile hash map
			for(int i = 0; i < objectWorkerP1.size(); i++) 
			{
				GameObject tempObject = objectWorkerP1.get(hashMapKeysWorkerP1.get(i));
			
				//this is an abstract class that is called in the player or tile classes
				tempObject.render(g);
			}
		}
		
		if(game.gameState == Game.STATE.Player2)
		{
			for(int i = 0; i < objectWorkerP2.size(); i++) 
			{
				GameObject tempObject = objectWorkerP2.get(hashMapKeysWorkerP2.get(i));
			
				//this is an abstract class that is called in the player or tile classes
				tempObject.render(g);
			}
		}
		
		if(game.gameState == Game.STATE.Player3)
		{
			for(int i = 0; i < objectWorkerP3.size(); i++) 
			{
				GameObject tempObject = objectWorkerP3.get(hashMapKeysWorkerP3.get(i));
			
				//this is an abstract class that is called in the player or tile classes
				tempObject.render(g);
			}
		}
		
		if(game.gameState == Game.STATE.Player4)
		{
			for(int i = 0; i < objectWorkerP4.size(); i++) 
			{
				GameObject tempObject = objectWorkerP4.get(hashMapKeysWorkerP4.get(i));
			
				//this is an abstract class that is called in the player or tile classes
				tempObject.render(g);
			}
		}
	}
	
	//create method to add objects such as the tiles (worker and jungle)
	public void addObject(String key, GameObject object, ID id, IDPlayer idplayer)
	{
		if(id == ID.JungleTile)
		{
			//adds an object to the jungle tile hash map
			this.objectJungle.put(key, object);
		}
		else if(id == ID.WorkerTile)
		{
			//adds an object to the worker tile hash map for all players
			if(idplayer == IDPlayer.Player1)
			{
				this.objectWorkerP1.put(key, object);
			}
			else if(idplayer == IDPlayer.Player2)
			{
				this.objectWorkerP2.put(key, object);
			}
			else if(idplayer == IDPlayer.Player3)
			{
				this.objectWorkerP3.put(key, object);
			}
			else if(idplayer == IDPlayer.Player4)
			{
				this.objectWorkerP4.put(key, object);
			}
		}
		else if(id == ID.ScoreCard)
		{
			//adds an object to the worker tile hash map for all players
			if(idplayer == IDPlayer.Player1)
			{
				this.scoreP1.put(key, object);
			}
			else if(idplayer == IDPlayer.Player2)
			{
				this.scoreP2.put(key, object);
			}
			else if(idplayer == IDPlayer.Player3)
			{
				this.scoreP3.put(key, object);
			}
			else if(idplayer == IDPlayer.Player4)
			{
				this.scoreP4.put(key, object);
			}
			
		}
		
	}
	
	public void addKey(String key, ID id, IDPlayer idplayer)
	{
		if(id == ID.JungleTile)
		{
			//adds an object to the jungle tile hash map
			this.hashMapKeysJungle.add(key);
		}
		else if(id == ID.WorkerTile)
		{
			//adds an object to the worker tile hash map for all players
			if(idplayer == IDPlayer.Player1)
			{
				this.hashMapKeysWorkerP1.add(key);
			}
			else if(idplayer == IDPlayer.Player2)
			{
				this.hashMapKeysWorkerP2.add(key);
			}
			else if(idplayer == IDPlayer.Player3)
			{
				this.hashMapKeysWorkerP3.add(key);
			}
			else if(idplayer == IDPlayer.Player4)
			{
				this.hashMapKeysWorkerP4.add(key);
			}

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
		GameObject tempObject = object.get(deckKeys.get(0));
		tempObject.setX(x);
		tempObject.setY(y);
	}
	
	/*
	  public static List<Dog> cloneList(List<Dog> list) {
    List<Dog> clone = new ArrayList<Dog>(list.size());
    for (Dog item : list) clone.add(item.clone());
    return clone;
}
	 */
	
	//clones the Jungle key array list. This will be used to clone the deck.
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
	
	//clones the Worker key array list. This will be used to clone the deck.
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
	
	//rotate the tile and it's corresponding score
	public void rotateWorkerTile(int [] score)
	{
		int temp = 0;
		
		temp = score[0];
		score[0] = score[1];	
		score[1] = score[2];
		score[2] = score[3];
		score[3] = temp;
	}
	
	//reset end turn to true to initialize draw
	public void endTurnTrue(Game game)
	{
		game.turnEnd = true;
	}
	
	//reset drawing  to true to initialize draw
	public void drawWorkerTrue(Game game)
	{
		game.drawWorker1 = true;
		game.drawWorker2 = true;
		game.drawWorker3 = true;
	}
	
	//reset drawing  to true to initialize draw
	public void drawJungleTrue(Game game)
	{
		game.drawJungle1 = true;
		game.drawJungle2 = true;
	}

	//draws a card from the deck
	public void drawCard(Game game)
	{
		//System.out.println(handler.deckKeys.size());
		
		//Logic to determine if any tiles should be drawn from the decks
		if(game.turnEnd == true)
		{
			if(game.gameState == Game.STATE.Play && game.typeState == Game.TYPESTATE.Jungle && game.turnState == Game.TURNSTATE.Draw)
			{
				if(game.drawJungle1 == true)
				{
					drawFromDeck(deckKeysJungle, objectJungle, game.draw1LocX, game.draw1LocY);
					removeFromDeck(deckKeysJungle);
					game.drawJungle1 = false;
				}
				if(game.drawJungle2 == true)
				{
					drawFromDeck(deckKeysJungle, objectJungle, game.draw2LocX, game.draw2LocY);
					removeFromDeck(deckKeysJungle);
					game.drawJungle2 = false;
				}
			}
			
			if(game.drawWorker1 == true && game.typeState == Game.TYPESTATE.Worker && game.turnState == Game.TURNSTATE.Draw)
			{
				if(game.gameState == Game.STATE.Player1)
				{
					drawFromDeck(deckKeysWorkerP1, objectWorkerP1, game.draw1WorkerLocX, game.draw1WorkerLocY);
					removeFromDeck(deckKeysWorkerP1);
				}
				else if(game.gameState == Game.STATE.Player2)
				{
					drawFromDeck(deckKeysWorkerP2, objectWorkerP2, game.draw1WorkerLocX, game.draw1WorkerLocY);
					removeFromDeck(deckKeysWorkerP2);
				}
				else if(game.gameState == Game.STATE.Player3)
				{
					drawFromDeck(deckKeysWorkerP3, objectWorkerP3, game.draw1WorkerLocX, game.draw1WorkerLocY);
					removeFromDeck(deckKeysWorkerP3);
				}
				else if(game.gameState == Game.STATE.Player4)
				{
					drawFromDeck(deckKeysWorkerP4, objectWorkerP4, game.draw1WorkerLocX, game.draw1WorkerLocY);
					removeFromDeck(deckKeysWorkerP4);
				}

				game.drawWorker1 = false;
			}
			
			if(game.drawWorker2 == true && game.typeState == Game.TYPESTATE.Worker && game.turnState == Game.TURNSTATE.Draw)
			{
				if(game.gameState == Game.STATE.Player1)
				{
					drawFromDeck(deckKeysWorkerP1, objectWorkerP1, game.draw2WorkerLocX, game.draw2WorkerLocY);
					removeFromDeck(deckKeysWorkerP1);
				}
				else if(game.gameState == Game.STATE.Player2)
				{
					drawFromDeck(deckKeysWorkerP2, objectWorkerP2, game.draw2WorkerLocX, game.draw2WorkerLocY);
					removeFromDeck(deckKeysWorkerP2);
				}
				else if(game.gameState == Game.STATE.Player3)
				{
					drawFromDeck(deckKeysWorkerP3, objectWorkerP3, game.draw2WorkerLocX, game.draw2WorkerLocY);
					removeFromDeck(deckKeysWorkerP3);
				}
				else if(game.gameState == Game.STATE.Player4)
				{
					drawFromDeck(deckKeysWorkerP4, objectWorkerP4, game.draw2WorkerLocX, game.draw2WorkerLocY);
					removeFromDeck(deckKeysWorkerP4);
				}
				
				game.drawWorker2 = false;
			}
			if(game.drawWorker3 == true  && game.typeState == Game.TYPESTATE.Worker && game.turnState == Game.TURNSTATE.Draw)
			{
				if(game.gameState == Game.STATE.Player1)
				{
					drawFromDeck(deckKeysWorkerP1, objectWorkerP1, game.draw3WorkerLocX, game.draw3WorkerLocY);
					removeFromDeck(deckKeysWorkerP1);
				}
				else if(game.gameState == Game.STATE.Player2)
				{
					drawFromDeck(deckKeysWorkerP2, objectWorkerP2, game.draw3WorkerLocX, game.draw3WorkerLocY);
					removeFromDeck(deckKeysWorkerP2);
				}
				else if(game.gameState == Game.STATE.Player3)
				{
					drawFromDeck(deckKeysWorkerP3, objectWorkerP3, game.draw3WorkerLocX, game.draw3WorkerLocY);
					removeFromDeck(deckKeysWorkerP3);
				}
				else if(game.gameState == Game.STATE.Player4)
				{
					drawFromDeck(deckKeysWorkerP4, objectWorkerP4, game.draw3WorkerLocX, game.draw3WorkerLocY);
					removeFromDeck(deckKeysWorkerP4);
				}

				game.drawWorker3 = false;
			}
			
			game.turnEnd = false;
		}
		
		//System.out.println(handler.deckKeys.size());
	}
	
}
