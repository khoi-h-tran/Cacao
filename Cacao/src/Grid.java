import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/* 
	File Name: HUD.java
	   Author: Khoi Tran
		 Date: Dec. 28, 2019 3:23:11 p.m.
  Description: This class is the layout
*/
public class Grid
{
	private Game game;
	
	//create a hash map for grid coordinates
	HashMap<String, Integer> gridCoord = new HashMap<String, Integer>();
	
	//create a hash map to determine if a grid has a tile in it
	HashMap<String, Integer> gridUsed = new HashMap<String, Integer>();
	
	//Get the list of keys whose value indicates the grid is being used (indicated by value = 1 for jungle tiles, value = 2 for worker tiles)
	ArrayList<String> listOfKeys = new ArrayList<String>();;
	
	//get the hash map of all keys with column and row indicator separated
	HashMap<Integer, Integer> yellowCoords = new HashMap<Integer, Integer>();;
	
	//populate the coordinates of the grid
	public void popGridCoord(Game game)
	{
		//populates the rows (numerical)
		for(int i = 0; i < Game.HEIGHT/game.TILE_DIM; i++)
		{
			gridCoord.put(String.valueOf(i+1), game.TILE_DIM*i);
		}
		
		//populates the columns (alphabetical)
		for(int i = 0; i < Game.HEIGHT/game.TILE_DIM; i++)
		{
			gridCoord.put(String.valueOf((char)(i+65)), game.TILE_DIM*i);
		}
		
		//Populates a hash map to state that each coordinate is unused (indicated by 0)
		//e.g. A1: 0, meaning A1 is empty
		for(int i = 0; i < Game.HEIGHT/game.TILE_DIM; i++)
		{
			for(int j = 0; j < Game.HEIGHT/game.TILE_DIM; j++)
			{
				gridUsed.put(String.valueOf((char)(i+65)) + String.valueOf(j), 0);
			}
		}
		
		//indicate that these 2 cells are used, as that is how the game starts
		gridUsed.replace("D4", 1);
		gridUsed.replace("E5", 1);
		//******************DELLETE********************************TEMPORARY*******************
		gridUsed.replace("F5", 2);
		//prints out grid coordinates
		//used for debugging
		/*
		for (Entry<String, Integer> entry : gridCoord.entrySet()) 
		{
	    System.out.println(entry.getKey() + ":" + entry.getValue().toString());
		}
		*/
		
		//prints out grid usage
		//used for debugging
		/*
		for (Entry<String, Integer> entry : gridUsed.entrySet()) 
		{
	    System.out.println(entry.getKey() + ":" + entry.getValue().toString());
		}
		*/
	}
	
	//searches for all grid locations that have a tile on it
	public static  ArrayList<String> getAllKeysForValue(HashMap<String, Integer> gridUsed, Integer value) 
	{
		ArrayList<String> listOfKeys = null;
		 
		//Check if Map contains the given value
		if(gridUsed.containsValue(value))
		{
			// Create an Empty List
			listOfKeys = new ArrayList<>();
					
			// Iterate over each entry of map using entrySet
			for (Map.Entry<String, Integer> entry : gridUsed.entrySet()) 
			{
				// Check if value matches with given value
				if (entry.getValue().equals(value))
				{
					// Store the key from entry to the list
					listOfKeys.add(entry.getKey());
				}
			}
		}
		
		//prints out grid usage
		//used for debugging
		/*
		for(String key: listOfKeys)
		{
			System.out.println(key);
		}
		*/
		
		return listOfKeys;
	}
	
	//splits up each coordinate that is occupied by a tile
	public HashMap <Integer, Integer> tileUsedCoordSplit(ArrayList<String> listOfKeys, HashMap<Integer, Integer> yellowCoords)
	{
		//split up the column and row  value and store it in a hash map
		for(String key: listOfKeys)
		{
			yellowCoords.put(((int)(key.charAt(0))-64), Character.getNumericValue(key.charAt(1)));
		}

		//prints out grid usage
		//used for debugging
		/*
		System.out.println(yellowCoords.size());
		
		for (HashMap.Entry<Integer, Integer> entry : yellowCoords.entrySet()) 
		{
	    System.out.println(entry.getKey() + ":" + entry.getValue());
		}
		*/
		
		return yellowCoords;
	}

	public void tick(Game game)
	{
		// 0 means no state
		// 1 means jungle type was employed and you need to clear the list for worker tiles
		// 2 means worker type was employed and you need to clear the list for jungle tiles
		
		
		if(game.typeState == Game.TYPESTATE.Jungle)
		{
			//clear the list if it was previously used to keep track of worker tiles
			if(game.jungleState == 2)
			{
				listOfKeys.clear();
				yellowCoords.clear();
			}
			//indicate that the jungle tiles were used at least once
			game.jungleState = 1;
			//Get the list of keys whose value indicates the grid is being used (indicated by value = 1)
			listOfKeys = getAllKeysForValue(gridUsed, 1);
			
			//get the hash map of all keys with column and row indicator separated
			yellowCoords = tileUsedCoordSplit(listOfKeys, yellowCoords);
		}
		
		if(game.typeState == Game.TYPESTATE.Worker)
		{
		//clear the list if it was previously used to keep track of jungle tiles
			if(game.jungleState == 1)
			{
				listOfKeys.clear();
				yellowCoords.clear();
			}
			//indicate that the worker tiles were used at least once
			game.jungleState = 2;
			//Get the list of keys whose value indicates the grid is being used (indicated by value = 2), 2 meaning worker tiles
			/*
			listOfKeys = getAllKeysForValue(gridUsed, 2);
			
			//get the hash map of all keys with column and row indicator separated
			yellowCoords = tileUsedCoordSplit(listOfKeys, yellowCoords);
			*/
		}
	}
	
	public void render(Graphics g, Game game)
	{
		boolean colorYellow = false;
		//Font f1 = new Font(Font.SERIF, Font.PLAIN, 30);
		//populates the grid display in the game
		for(int i = 0; i < Game.HEIGHT/game.TILE_DIM; i++)
		{
			for(int j = 0; j < Game.HEIGHT/game.TILE_DIM; j++)
			{
				colorYellow = false;
				
				for (HashMap.Entry<Integer, Integer> entry : yellowCoords.entrySet()) 
				{
			    if(((i+1) == entry.getKey() && ((j+1) == entry.getValue() - 1 || (j+1) == entry.getValue() +1)) || ((j+1) == entry.getKey() && ((i+1) == entry.getValue() - 1 || (i+1) == entry.getValue() +1)))
			    {
			    	colorYellow = true;
			    }
				}
				
				if(colorYellow == true)
				{
					g.setColor(Color.yellow);
					g.fillRect((game.TILE_DIM*i), (game.TILE_DIM*j), game.TILE_DIM, game.TILE_DIM);
				}
				else
				{
					g.setColor(Color.black);
					g.drawRect((game.TILE_DIM*i), (game.TILE_DIM*j), game.TILE_DIM, game.TILE_DIM);
				}
	    	
	    	
	    	
			}
		}
	}

}
