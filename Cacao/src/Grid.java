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
	
	//create a hash map for grid coordinates (populates the point at which a specific row starts and the point at which a specific columns)
	//String is the column or row indicated. E.g. either A to indicate column, or 1 to indicate row
	//Integer is the starting point of the column or row as a multiple of the tile dimension
	HashMap<String, Integer> gridCoord = new HashMap<String, Integer>();
	
	//create a hash map to determine if a grid has a tile in it
	//String is the combined coordinate (e.g. E4).
	//Integer = 1 means a jungle tile
	//Integer = 2 means a worker tile
	HashMap<String, Integer> gridUsed = new HashMap<String, Integer>();
	
	//Get the list of keys whose value indicates the grid is being used (indicated by value = 1 for jungle tiles, value = 2 for worker tiles)
	ArrayList<String> listOfKeys = new ArrayList<String>();;
	
	//get the hash map of all keys with column and row indicator separated
	ArrayList<Integer> yellowCoordsCol = new ArrayList<Integer>();
	ArrayList<Integer> yellowCoordsRow = new ArrayList<Integer>();
	
	//hash map to store all the grids a worker tile could be put on
	ArrayList<String> validWorkerTileLoc = new ArrayList<String>();
	
	//hash map to store all the grids a jungle tile could be put on
	ArrayList<String> validJungleTileLoc = new ArrayList<String>();
	
	//if there are no valid jungle tiles, set boolean to skip the jungle tile phase
	public boolean validJungleTile = true;
	
	//variable used in debugging to only output values once per turn
	int tempTurnCounter = 0;
	
	//boolean variable to determine if a box is to be colored yellow or not
	protected boolean colorYellow = false;
	
	//populate the coordinates of the grid
	public void popGridCoord(Game game, Handler handler)
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
	public void tileUsedCoordSplit(ArrayList<String> listOfKeys, ArrayList<Integer> yellowCoordsCol, ArrayList<Integer> yellowCoordsRow)
	{
		yellowCoordsCol.clear();
		yellowCoordsRow.clear();
		//split up the column and row  value and store it in a hash map
		if(listOfKeys.size() > 0)
		{
			for(String key: listOfKeys)
			{
				yellowCoordsCol.add( (int)(key.charAt(0))-64 );
				yellowCoordsRow.add( Character.getNumericValue(key.charAt(1)) );
			}
		}
	}

	public void tick(Game game)
	{
		popYellowCoords(game);
	}
	
	public void render(Graphics g, Game game)
	{
		//checks if the grid boxes should be yellow or not
		checkBoxIsYellow(g, game);
	}
	
	//populates where the yellow boxes should be according to coordinates
	public void popYellowCoords(Game game)
	{
		// 0 means no state
		// 1 means jungle type was employed and you need to clear the list for worker tiles
		// 2 means worker type was employed and you need to clear the list for jungle tiles
		if(game.typeState == Game.TYPESTATE.Worker)
		{
			//clear the list if it was previously used to keep track of worker tiles
			listOfKeys.clear();
			/*
			if(game.tileState == 2)
			{
				listOfKeys.clear();
			}
			*/
			//indicate that the jungle tiles were used at least once
			//game.tileState = 1;
			//Get the list of keys whose value indicates the grid is being used (indicated by value = 1), 1 meaning jungle tile
			listOfKeys = getAllKeysForValue(gridUsed, 1);
			
			//get the hash map of all keys with column and row indicator separated
			tileUsedCoordSplit(listOfKeys, yellowCoordsCol, yellowCoordsRow);
			
			/*
			for (HashMap.Entry<String, Integer> entry : gridUsed.entrySet()) 
			{
				System.out.println(entry.getKey() + ":" + entry.getValue());
			}
			*/
			
			/*
			for(String key: listOfKeys)
			{
				System.out.print(key + ", ");
			}
			System.out.println();
			*/
		}
		
		if(game.typeState == Game.TYPESTATE.Jungle)
		{
		//clear the list if it was previously used to keep track of jungle tiles
			listOfKeys.clear();
			/*
			if(game.tileState == 1)
			{
				listOfKeys.clear();
			}
			*/
			//indicate that the worker tiles were used at least once
			//game.tileState = 2;
			//Get the list of keys whose value indicates the grid is being used (indicated by value = 2), 2 meaning worker tiles
			listOfKeys = getAllKeysForValue(gridUsed, 2);
			//get the hash map of all keys with column and row indicator separated
			tileUsedCoordSplit(listOfKeys, yellowCoordsCol, yellowCoordsRow);
		}
	}

	
	//method to sort through grids and compare them to worker tiles or jungle tiles
	public void checkBoxIsYellow(Graphics g, Game game)
	{
		colorYellow = false;
		
		//empty the list so it can be populated each time as the game ticks
		//or else we will jus tkeep adding inifinite values
		//this will allow us to use the same array list for jungle tiles or worth tiles
		validWorkerTileLoc.clear();
		validJungleTileLoc.clear();
		
		/*
		for (HashMap.Entry<Integer, Integer> entry : yellowCoords.entrySet()) 
		{
			System.out.println((char)(entry.getKey() + 64) + ":" + entry.getValue());
		}
		 */
		
		/*
		if(tempTurnCounter == game.turnCounter)
		{
			tempTurnCounter++;
			
			for (HashMap.Entry<Integer, Integer> entry : yellowCoords.entrySet()) 
			{
				if(game.typeState == Game.TYPESTATE.Worker)
				{
					System.out.println("worker tiles " + (char)(entry.getKey() + 64) + ":" + entry.getValue());
				}
				else if(game.typeState == Game.TYPESTATE.Jungle)
				{
					System.out.println("jungle tiles " + (char)(entry.getKey() + 64) + ":" + entry.getValue());
				}
			}
		}
		*/
		
		//populates the grid display in the game
		for(int i = 0; i < Game.HEIGHT/game.TILE_DIM; i++)//populates rows
		{
			for(int j = 0; j < Game.HEIGHT/game.TILE_DIM; j++)//populates columns
			{
				//set the default colour of the square to not yellow
				colorYellow = false;
    		//int count = 0;
				//loop through the HashMap that contains all the coordinates (e.g. key = E, value = 4).
				for (int c = 0; c < yellowCoordsCol.size(); c++) 
				{
				  //System.out.println((char)(entry.getKey() + 64) + ":" + entry.getValue());

					//For each grid which is identified as used (e.g. E4)
					//check if in column E, if there is a row that is one above or one below 4. This is a potential option to place your next tile (i.e. adjacent to your tiles)
					//check if in row 4, if there is a column = C or F, which is one above or below 4.This is a potential option to place your next tile (i.e. adjacent to your tiles)
					//if it is above or below the already placed tiles, indicate it should be yellow
					
					//add 1 for i, because the for loop starts at 0, but our numbers start at 1
					//goes to each square, and flags as yellow if it checks above and below or left side and right side and finds a grid that is used
					
		    	//System.out.print((char)(entry.getKey() + 64));
		    	//System.out.println(entry.getValue());
					
		    	//System.out.print("key " + entry.getKey() + j);
		    	//System.out.println("value " + entry.getValue() + i);
		    	
		    	//how logic works
		    	//all the(i+1) or (j+1) are just because the for loops start at 0, but the column values start at 1 or A.
		    	//check if the row matches the row of a grid square that is used
		    	//if yes, check if it's value is either 1 above or below that used grid
		    	//if yes, fill it in yellow
		    	//vice versa for the columns
			    if( ( ((i+1) == yellowCoordsRow.get(c)) && ((j+1) == yellowCoordsCol.get(c) - 1 || (j+1) == yellowCoordsCol.get(c) +1) ) || ((j+1) == yellowCoordsCol.get(c) && ((i+1) == yellowCoordsRow.get(c) - 1 || (i+1) == yellowCoordsRow.get(c) +1)) )
			    {
			    	
			    	if(game.typeState == Game.TYPESTATE.Worker)
			    	{
				    	colorYellow = true;
			    	}
			    	else if(game.typeState == Game.TYPESTATE.Jungle)
			    	{			    		
							for (int k = 0; k < yellowCoordsCol.size(); k++)
							{
								if(yellowCoordsRow.get(c) != yellowCoordsRow.get(k) && yellowCoordsCol.get(c) != yellowCoordsCol.get(k) )
								{
					    		//System.out.print(yellowCoordsCol.get(c));
					    		//System.out.println(yellowCoordsRow.get(c));
					    		
					    		//System.out.print(yellowCoordsCol.get(k));
					    		//System.out.println(yellowCoordsRow.get(k));
									if( ( ((i+1) == yellowCoordsRow.get(k)) && ((j+1) == yellowCoordsCol.get(k) - 1 || (j+1) == yellowCoordsCol.get(k) +1) ) || ((j+1) == yellowCoordsCol.get(k) && ((i+1) == yellowCoordsRow.get(k) - 1 || (i+1) == yellowCoordsRow.get(k) +1)) )
							    {
							    	colorYellow = true;
							    	//System.out.println("yellow jungle true");
							    }
								}
							}
			    		
			    	}
			    }
				}
				
				//if it is a potential place to put the tile, color the box yellow
				if(colorYellow == true)
				{
					//colours the box yellow
					g.setColor(Color.yellow);
					g.fillRect((game.TILE_DIM*j), (game.TILE_DIM*i), game.TILE_DIM, game.TILE_DIM);
					
					
					//if it is yellow, check if there is already a tile on it or not. If there is, it is not a valid tile location.
					

					//if it is the worker turn, state which worker tiles could be used
					if(game.typeState == Game.TYPESTATE.Worker)
					{
						//boolean variable to check if the tile is not used (if it is used it cannot be a valid placement location)
						boolean notUsed = true;
						
						//find all jungle tiles used
						ArrayList<String> listOfKeysJungle = getAllKeysForValue(gridUsed, 1);
						//find all worker tiles used
						if(game.turnCounter > 1)
						{
							ArrayList<String> listOfKeysWorker = getAllKeysForValue(gridUsed, 2);
							
							//combine the lists for all tiles
							listOfKeysJungle.addAll(listOfKeysWorker);
						}
						
						//check all keys used (see above line, it is for both workers and jungle combined into one list)
						for ( String key : listOfKeysJungle ) 
						{
				    	//System.out.print("key used" + key);
				    	//System.out.println(" new grid " + (String.valueOf((char)(i + 65)) + String.valueOf(j + 1)));
					    if (key.equals(String.valueOf((char)(j + 65)) + String.valueOf(i + 1)))
					    {
					    	notUsed = false;
					    	//System.out.println("flagged as false");
					    }
						}
						
						//only add as a viable location if there is no existing tile in that grid location
						if(notUsed == true)
						{
							validWorkerTileLoc.add(String.valueOf((char)(j + 65)) + String.valueOf(i + 1));
						}
						
						//System.out.print(String.valueOf((char)(i + 65)));
						//System.out.println(String.valueOf(j + 1));
					}
					
					//if it is the jungle turn, state which worker tiles could be used
					if(game.typeState == Game.TYPESTATE.Jungle)
					{
						//boolean variable to check if the tile is not used (if it is used it cannot be a valid placement location)
						boolean notUsed = true;
						
						//find all jungle tiles used
						ArrayList<String> listOfKeysJungle = getAllKeysForValue(gridUsed, 1);
						//find all worker tiles used
						//ArrayList<String> listOfKeysWorker = getAllKeysForValue(gridUsed, 2);
						
						//combine the lists for all tiles
						//listOfKeysJungle.addAll(listOfKeysWorker);
						
						//check all keys used (see above line, it is for both workers and jungle combined into one list)
						for ( String key : listOfKeysJungle ) 
						{
				    	//System.out.print("key used" + key);
				    	//System.out.println(" new grid " + (String.valueOf((char)(i + 65)) + String.valueOf(j + 1)));
					    if (key.equals(String.valueOf((char)(j + 65)) + String.valueOf(i + 1)))
					    {
					    	notUsed = false;
					    	//System.out.println("flagged as false");
					    }
						}
						
						//only add as a viable location if there is no existing tile in that grid location
						if(notUsed == true)
						{
							validJungleTileLoc.add(String.valueOf((char)(j+65)) + String.valueOf(i + 1));
							//System.out.println(String.valueOf((char)(j+65)) + String.valueOf(i + 1));
						}
						//System.out.print(String.valueOf((char)(i + 65)));
						//System.out.println(String.valueOf(j + 1));
						

					}

				}
				
				//System.out.println(validJungleTileLoc.size());
				
				//if it is a potential place to put the tile, color the box black
				else
				{
					g.setColor(Color.black);
					g.drawRect((game.TILE_DIM*i), (game.TILE_DIM*j), game.TILE_DIM, game.TILE_DIM);
				}
			}
		}
		
		if(game.typeState == Game.TYPESTATE.Jungle)
		{
			//if there are no valid jungle tiles, set boolean to skip the jungle tile phase
			if(validJungleTileLoc.size() == 0)
			{
				validJungleTile = false;
				//System.out.println("amount of valid jungle tile locations: " + validJungleTileLoc.size());
			}
			else
			{
				validJungleTile = true;
				//System.out.println("amount of valid jungle tile locations: " + validJungleTileLoc.size());
			}
		}

	}

}
