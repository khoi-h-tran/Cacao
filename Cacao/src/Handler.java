import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.SwingUtilities;

/* 
	File Name: handler.java
	   Author: Khoi Tran
		 Date: Dec. 27, 2019 11:43:24 a.m.
  Description: Maintains, updates, renders all objects (i.e. players, game pieces, etc) in game
*/
public class Handler extends MouseAdapter
{
	private Game game;
	private Select select;
	
	//create a hash map for all jungle tiles
	HashMap<String, GameObject> objectJungle = new HashMap<String, GameObject>();

	//create array list to hold keys for jungle tiles
	ArrayList<String> hashMapKeysJungle = new ArrayList<String>();
	
	//create array list to hold key for jungle tiles in deck
	ArrayList<String> deckKeysJungle = new ArrayList<String>();
	
	//create hash map to hold which jungle tiles (by key) is in which draw deck
	//String = value (unique id of the jungle tile)
	//key (draw location):
	//Integer = 1 means loc 1 (on left)
	//Integer = 2 means loc 2 (on right)
	HashMap<Integer, String> drawLocJungle = new HashMap<Integer, String>();
	
	//create hash map to hold which worker tiles (by key) is in which draw deck
	//String = key for worker tile in specific location
	//Integer = 1 means loc 1 (top left)
	//Integer = 2 means loc 2 (top right)
	//Integer = 3 means loc 2 (bottom middle)
	HashMap<Integer, String> drawLocWorker1 = new HashMap<Integer, String>();
	HashMap<Integer, String> drawLocWorker2 = new HashMap<Integer, String>();
	HashMap<Integer, String> drawLocWorker3 = new HashMap<Integer, String>();
	HashMap<Integer, String> drawLocWorker4 = new HashMap<Integer, String>();
	
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
	
	//create a hash map for to correspond each player to each score hash map
	//One for each player
	HashMap<String, GameObject> scoreP1 = new HashMap<String, GameObject>();
	HashMap<String, GameObject> scoreP2 = new HashMap<String, GameObject>();
	HashMap<String, GameObject> scoreP3 = new HashMap<String, GameObject>();
	HashMap<String, GameObject> scoreP4 = new HashMap<String, GameObject>();
	
	//Holds hash maps for the scores of each player
	HashMap<String, Integer> scoreCountP1 = new HashMap<String, Integer>();
	HashMap<String, Integer> scoreCountP2 = new HashMap<String, Integer>();
	HashMap<String, Integer> scoreCountP3 = new HashMap<String, Integer>();
	HashMap<String, Integer> scoreCountP4 = new HashMap<String, Integer>();
	
	//create a hash map for all player ressource icons
	//One for each player
	HashMap<String, GameObject> ressourceP1 = new HashMap<String, GameObject>();
	HashMap<String, GameObject>  ressourceP2 = new HashMap<String, GameObject>();
	HashMap<String, GameObject>  ressourceP3 = new HashMap<String, GameObject>();
	HashMap<String, GameObject>  ressourceP4 = new HashMap<String, GameObject>();

	/*
	Cacao(),//total: 20
	SunToken(),//total: 12
	Water(),
	Temple(),
	Gold(),
	*/
	//create an array to store names of ressource so we can refer to the array in a loop for easy access
	String [] ressourceNames = {"Cacao", "SunToken", "Water", "Temple", "Gold", "Meeple"};
	
	//create game loop for objects
	
	public void tick(Game game, Select select)
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
		
		//check if the worker tiles are clicked
		if(select.worker1Clicked == true || select.worker2Clicked == true || select.worker3Clicked == true)
		{
			chooseImageRotate(game, this, select);
		}
	}
	
	public void render(Graphics g, Game game)
	{
		if(game.gameState == Game.STATE.Player1 || game.gameState == Game.STATE.Player2 || game.gameState == Game.STATE.Player3 || game.gameState == Game.STATE.Player4)
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
			
			//Render the resource icons
			
			if(game.numPlayers >= 1)
			{
				//render the resource tiles
				for(int i = 0; i < ressourceP1.size(); i++) 
				{
					GameObject tempObject = ressourceP1.get(ressourceNames[i]);
				
					//this is an abstract class that is called in the player or tile classes
					tempObject.render(g);
				}
			}
		
			if(game.numPlayers >= 2)
			{
				//render the resource tiles
				for(int i = 0; i < ressourceP2.size(); i++) 
				{
					GameObject tempObject = ressourceP2.get(ressourceNames[i]);
				
					//this is an abstract class that is called in the player or tile classes
					tempObject.render(g);
				}
			}
	
			if(game.numPlayers >= 3)
			{
				//render the resource tiles
				for(int i = 0; i < ressourceP3.size(); i++) 
				{
					GameObject tempObject = ressourceP3.get(ressourceNames[i]);
				
					//this is an abstract class that is called in the player or tile classes
					tempObject.render(g);
				}
			}
			
			if(game.numPlayers >= 4)
			{
				//render the resource tiles
				for(int i = 0; i < ressourceP4.size(); i++) 
				{
					GameObject tempObject = ressourceP4.get(ressourceNames[i]);
				
					//this is an abstract class that is called in the player or tile classes
					tempObject.render(g);
				}
			}
		}

	}
	
	//populates the entire hash map to zero
	public void popScoresToZero(HashMap<String,Integer> score)
	{
		// Gold, cacao beans, sun tokens, temples, water
		score.put("Gold", 0);
		score.put("Cacao", 0);
		score.put("Sun Tokens", 0);
		score.put("Water", 0);
		score.put("Temple", 0);
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
		else if(id == ID.Ressource)
		{
			//adds an object to the resource icon hash map for all players
			if(idplayer == IDPlayer.Player1)
			{
				this.ressourceP1.put(key, object);
			}
			else if(idplayer == IDPlayer.Player2)
			{
				this.ressourceP2.put(key, object);
			}
			else if(idplayer == IDPlayer.Player3)
			{
				this.ressourceP3.put(key, object);
			}
			else if(idplayer == IDPlayer.Player4)
			{
				this.ressourceP4.put(key, object);
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
	public void drawFromDeck(ArrayList<String> deckKeys, HashMap<String, GameObject> object, HashMap<Integer, String> drawLocJungle, HashMap<Integer, String> drawLocWorker, int x, int y, Game game)
	{
		//pulls the object from the hash map because this is where the object is actually stored and moved around
		//the deckKeys at 0 index is just the top most card in the deck
		GameObject tempObject = object.get(deckKeys.get(0));
		//sets the tile's location to the draw location
		tempObject.setX(x);
		tempObject.setY(y);
		
		//storing which tile was put in which draw location
		if(x == game.draw1LocX && y == game.draw1LocY)
		{
			drawLocJungle.put(1, deckKeys.get(0));
		}
		else if(x == game.draw2LocX && y == game.draw2LocY)
		{
			drawLocJungle.put(2, deckKeys.get(0));
		}
		else if(x == game.draw1WorkerLocX && y == game.draw1WorkerLocY)
		{
			drawLocWorker.put(1, deckKeys.get(0));
		}
		else if(x == game.draw2WorkerLocX && y == game.draw2WorkerLocY)
		{
			drawLocWorker.put(2, deckKeys.get(0));
		}
		else if(x == game.draw3WorkerLocX && y == game.draw3WorkerLocY)
		{
			drawLocWorker.put(3, deckKeys.get(0));
		}
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
					drawFromDeck(deckKeysJungle, objectJungle, drawLocJungle, null, game.draw1LocX, game.draw1LocY, game);
					removeFromDeck(deckKeysJungle);
					game.drawJungle1 = false;
				}
				if(game.drawJungle2 == true)
				{
					drawFromDeck(deckKeysJungle, objectJungle, drawLocJungle, null,  game.draw2LocX, game.draw2LocY, game);
					removeFromDeck(deckKeysJungle);
					game.drawJungle2 = false;
				}
			}
			
			if(game.drawWorker1 == true && game.typeState == Game.TYPESTATE.Worker && game.turnState == Game.TURNSTATE.Draw)
			{
				if(game.gameState == Game.STATE.Player1)
				{
					drawFromDeck(deckKeysWorkerP1, objectWorkerP1, null, drawLocWorker1, game.draw1WorkerLocX, game.draw1WorkerLocY, game);
					removeFromDeck(deckKeysWorkerP1);
				}
				else if(game.gameState == Game.STATE.Player2)
				{
					drawFromDeck(deckKeysWorkerP2, objectWorkerP2, null, drawLocWorker2, game.draw1WorkerLocX, game.draw1WorkerLocY, game);
					removeFromDeck(deckKeysWorkerP2);
				}
				else if(game.gameState == Game.STATE.Player3)
				{
					drawFromDeck(deckKeysWorkerP3, objectWorkerP3, null, drawLocWorker3, game.draw1WorkerLocX, game.draw1WorkerLocY, game);
					removeFromDeck(deckKeysWorkerP3);
				}
				else if(game.gameState == Game.STATE.Player4)
				{
					drawFromDeck(deckKeysWorkerP4, objectWorkerP4, null, drawLocWorker4, game.draw1WorkerLocX, game.draw1WorkerLocY, game);
					removeFromDeck(deckKeysWorkerP4);
				}

				game.drawWorker1 = false;
			}
			
			if(game.drawWorker2 == true && game.typeState == Game.TYPESTATE.Worker && game.turnState == Game.TURNSTATE.Draw)
			{
				if(game.gameState == Game.STATE.Player1)
				{
					drawFromDeck(deckKeysWorkerP1, objectWorkerP1, null, drawLocWorker1, game.draw2WorkerLocX, game.draw2WorkerLocY, game);
					removeFromDeck(deckKeysWorkerP1);
				}
				else if(game.gameState == Game.STATE.Player2)
				{
					drawFromDeck(deckKeysWorkerP2, objectWorkerP2, null, drawLocWorker2, game.draw2WorkerLocX, game.draw2WorkerLocY, game);
					removeFromDeck(deckKeysWorkerP2);
				}
				else if(game.gameState == Game.STATE.Player3)
				{
					drawFromDeck(deckKeysWorkerP3, objectWorkerP3, null, drawLocWorker3, game.draw2WorkerLocX, game.draw2WorkerLocY, game);
					removeFromDeck(deckKeysWorkerP3);
				}
				else if(game.gameState == Game.STATE.Player4)
				{
					drawFromDeck(deckKeysWorkerP4, objectWorkerP4, null, drawLocWorker4, game.draw2WorkerLocX, game.draw2WorkerLocY, game);
					removeFromDeck(deckKeysWorkerP4);
				}
				
				game.drawWorker2 = false;
			}
			if(game.drawWorker3 == true  && game.typeState == Game.TYPESTATE.Worker && game.turnState == Game.TURNSTATE.Draw)
			{
				if(game.gameState == Game.STATE.Player1)
				{
					drawFromDeck(deckKeysWorkerP1, objectWorkerP1, null, drawLocWorker1, game.draw3WorkerLocX, game.draw3WorkerLocY, game);
					removeFromDeck(deckKeysWorkerP1);
				}
				else if(game.gameState == Game.STATE.Player2)
				{
					drawFromDeck(deckKeysWorkerP2, objectWorkerP2, null, drawLocWorker2, game.draw3WorkerLocX, game.draw3WorkerLocY, game);
					removeFromDeck(deckKeysWorkerP2);
				}
				else if(game.gameState == Game.STATE.Player3)
				{
					drawFromDeck(deckKeysWorkerP3, objectWorkerP3, null, drawLocWorker3, game.draw3WorkerLocX, game.draw3WorkerLocY, game);
					removeFromDeck(deckKeysWorkerP3);
				}
				else if(game.gameState == Game.STATE.Player4)
				{
					drawFromDeck(deckKeysWorkerP4, objectWorkerP4, null, drawLocWorker4, game.draw3WorkerLocX, game.draw3WorkerLocY, game);
					removeFromDeck(deckKeysWorkerP4);
				}

				game.drawWorker3 = false;
			}
			
			game.turnEnd = false;
		}
	}
	
	//chooses the image to rotate
	public void chooseImageRotate(Game game, Handler handler, Select select)
	{
		//select the worker tiles to move and rotate
		if(game.typeState == Game.TYPESTATE.Worker && game.turnState == Game.TURNSTATE.Move)
		{
			if(game.gameState == Game.STATE.Player1)
			{
				if(select.worker1Clicked == true && select.worker1ActionComplete == false)
				{
	        //System.out.println("Right button pressed. On 1");
					System.out.println(handler.objectWorkerP1.get(handler.drawLocWorker1.get(1)).getTile_image());
	        //rotateImage(handler.objectWorkerP1.get(handler.drawLocWorker1.get(1)).getTile_image());
	        select.worker1ActionComplete = true;
				}
				if(select.worker2Clicked == true && select.worker2ActionComplete == false)
				{
	        System.out.println("Right button pressed. On 2");
	        select.worker2ActionComplete = true;
				}
				if(select.worker3Clicked == true && select.worker3ActionComplete == false)
				{
	        System.out.println("Right button pressed. On 3");
	        select.worker3ActionComplete = true;
				}
			}
			else if(game.gameState == Game.STATE.Player2)
			{
				
			}
			else if(game.gameState == Game.STATE.Player3)
			{
				
			}
			else if(game.gameState == Game.STATE.Player4)
			{
				
			}
		}
	}
	
	//rotates the worker tiles
	public void rotateImage(BufferedImage originalImage)
	{
		AffineTransform tx = new AffineTransform();

		// last, width = height and height = width :)
		tx.translate(originalImage.getHeight() / 2,originalImage.getWidth() / 2);
		tx.rotate(Math.PI / 2);
		// first - center image at the origin so rotate works OK
		tx.translate(-originalImage.getWidth() / 2,-originalImage.getHeight() / 2);
	
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
	
		// new destination image where height = width and width = height.
		BufferedImage newImage =new BufferedImage(originalImage.getHeight(), originalImage.getWidth(), originalImage.getType());
		op.filter(originalImage, newImage);
	}
	
}
