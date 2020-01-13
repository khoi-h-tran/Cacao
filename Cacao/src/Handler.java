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
	private WorkerTiles workertiles;
	private MouseEvent e;
	private Grid grid;
	
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

	//create a hash map for all the worker tiles in play (so that it renders regardsless of which player's turn it is)
	ArrayList<GameObject> workerTilesInPlay = new ArrayList<GameObject>();
	
	/*
	Cacao(),//total: 20
	SunToken(),//total: 12
	Water(),
	Temple(),
	Gold(),
	*/
	//create an array to store names of ressource so we can refer to the array in a loop for easy access
	String [] ressourceNames = {"Cacao", "SunToken", "Water", "Temple", "Gold", "Meeple"};
	
	//create variable to indicate if tile was successfully placed on the grid
	public boolean placedWorker1 = false;
	public boolean placedWorker2 = false;
	public boolean placedWorker3 = false;
	public boolean placedJungle1 = false;
	public boolean placedJungle2 = false;
	
	//create game loop for objects
	
	public void tick(Game game, Select select, Grid grid)
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
		
		if(game.typeState == Game.TYPESTATE.Worker)
		{
		//check if the worker tiles are right clicked
			if(select.worker1Clicked == true || select.worker2Clicked == true || select.worker3Clicked == true)
			{
				chooseImageRotate(game, this, select);
			}
			
			//check if worker tiles are left clicked and move to follow cursor if it is held
			if(select.worker1Hold == true)
			{
				if(game.gameState == Game.STATE.Player1)
				{
					objectWorkerP1.get(drawLocWorker1.get(1)).setX(select.mxH - game.TILE_DIM/2);
					objectWorkerP1.get(drawLocWorker1.get(1)).setY(select.myH - game.TILE_DIM/2);
				}
				else if(game.gameState == Game.STATE.Player2)
				{
					objectWorkerP2.get(drawLocWorker2.get(1)).setX(select.mxH - game.TILE_DIM/2);
					objectWorkerP2.get(drawLocWorker2.get(1)).setY(select.myH - game.TILE_DIM/2);
				}
				else if(game.gameState == Game.STATE.Player3)
				{
					objectWorkerP3.get(drawLocWorker3.get(1)).setX(select.mxH - game.TILE_DIM/2);
					objectWorkerP3.get(drawLocWorker3.get(1)).setY(select.myH - game.TILE_DIM/2);
				}
				else if(game.gameState == Game.STATE.Player4)
				{
					objectWorkerP4.get(drawLocWorker4.get(1)).setX(select.mxH - game.TILE_DIM/2);
					objectWorkerP4.get(drawLocWorker4.get(1)).setY(select.myH - game.TILE_DIM/2);
				}

			}
			else if(select.worker2Hold == true)
			{
				if(game.gameState == Game.STATE.Player1)
				{
					objectWorkerP1.get(drawLocWorker1.get(2)).setX(select.mxH - game.TILE_DIM/2);
					objectWorkerP1.get(drawLocWorker1.get(2)).setY(select.myH - game.TILE_DIM/2);
				}
				else if(game.gameState == Game.STATE.Player2)
				{
					objectWorkerP2.get(drawLocWorker2.get(2)).setX(select.mxH - game.TILE_DIM/2);
					objectWorkerP2.get(drawLocWorker2.get(2)).setY(select.myH - game.TILE_DIM/2);
				}
				else if(game.gameState == Game.STATE.Player3)
				{
					objectWorkerP3.get(drawLocWorker3.get(2)).setX(select.mxH - game.TILE_DIM/2);
					objectWorkerP3.get(drawLocWorker3.get(2)).setY(select.myH - game.TILE_DIM/2);
				}
				else if(game.gameState == Game.STATE.Player4)
				{
					objectWorkerP4.get(drawLocWorker4.get(2)).setX(select.mxH - game.TILE_DIM/2);
					objectWorkerP4.get(drawLocWorker4.get(2)).setY(select.myH - game.TILE_DIM/2);
				}
			}
			else if(select.worker3Hold == true )
			{
				if(game.gameState == Game.STATE.Player1)
				{
					objectWorkerP1.get(drawLocWorker1.get(3)).setX(select.mxH - game.TILE_DIM/2);
					objectWorkerP1.get(drawLocWorker1.get(3)).setY(select.myH - game.TILE_DIM/2);
				}
				else if(game.gameState == Game.STATE.Player2)
				{
					objectWorkerP2.get(drawLocWorker2.get(3)).setX(select.mxH - game.TILE_DIM/2);
					objectWorkerP2.get(drawLocWorker2.get(3)).setY(select.myH - game.TILE_DIM/2);
				}
				else if(game.gameState == Game.STATE.Player3)
				{
					objectWorkerP3.get(drawLocWorker3.get(3)).setX(select.mxH - game.TILE_DIM/2);
					objectWorkerP3.get(drawLocWorker3.get(3)).setY(select.myH - game.TILE_DIM/2);
				}
				else if(game.gameState == Game.STATE.Player4)
				{
					objectWorkerP4.get(drawLocWorker4.get(3)).setX(select.mxH - game.TILE_DIM/2);
					objectWorkerP4.get(drawLocWorker4.get(3)).setY(select.myH - game.TILE_DIM/2);
				}
			}
		}
		
		if(game.typeState == Game.TYPESTATE.Jungle)
		{
			//check if jungle tiles are left clicked and move to follow cursor if it is held
			if(select.jungle1Hold == true)
			{
				objectJungle.get(drawLocJungle.get(1)).setX(select.mxH - game.TILE_DIM/2);
				objectJungle.get(drawLocJungle.get(1)).setY(select.myH - game.TILE_DIM/2);
			}
			else if(select.jungle2Hold == true)
			{
				objectJungle.get(drawLocJungle.get(2)).setX(select.mxH - game.TILE_DIM/2);
				objectJungle.get(drawLocJungle.get(2)).setY(select.myH - game.TILE_DIM/2);
			}
		}

		
		if(select.mouse1Released == true || select.mouse2Released == true || select.mouse3Released == true || select.mouseJungle1Released == true || select.mouseJungle2Released == true)
		{
			if(game.typeState == Game.TYPESTATE.Worker)
			{
				for(String coordinate: grid.validWorkerTileLoc)
				{
					//check if the released point is within the x-range of any valid worker tile location
					if(select.mxR > ((int)(coordinate.charAt(0)) - 65) * game.TILE_DIM  && select.mxR < (((int)(coordinate.charAt(0)) - 65) * game.TILE_DIM) + game.TILE_DIM)
					{
						//check if the released point is within the y-range of any valid worker tile location
						//you have to subtract an additional 1 sometimes because it will let you start at the correct location
						if(select.myR > ((int)(coordinate.charAt(1)) - 48 - 1) * game.TILE_DIM  && select.myR < ((int)(coordinate.charAt(1)) - 48 - 1) * game.TILE_DIM + game.TILE_DIM)
						{
							int xStart = ((int)(coordinate.charAt(0)) - 65) * game.TILE_DIM;
							int yStart = ((int)(coordinate.charAt(1)) - 48 - 1) * game.TILE_DIM;
							
							//create a string to store the new coordinate/grid location that is used
							String newGridUsed = String.valueOf((char)((int)(coordinate.charAt(0)))) + String.valueOf(((int)(coordinate.charAt(1)) - 48));
							
							//update the grid used hash map
							grid.gridUsed.replace(newGridUsed, 2);
							
							//if it is an acceptable square, place the correct object at the new location
					    if(select.mouse1Released == true)
					    {
								//System.out.println("worker 1 returned");
								if(game.gameState == Game.STATE.Player1)
								{
					  			objectWorkerP1.get(drawLocWorker1.get(1)).setX(xStart);
					  			objectWorkerP1.get(drawLocWorker1.get(1)).setY(yStart);
					  			workerTilesInPlay.add(objectWorkerP1.get(drawLocWorker1.get(1)));
					  			drawLocWorker1.remove(1);
					  			drawLocWorker1.remove(1);

					  			//hashMapKeysWorkerP1.remove(drawLocWorker1.get(1));
					  			//objectWorkerP1.remove(drawLocWorker1.get(1));
					  			placedWorker1 = true;
									game.drawWorker1 = true;
								}
								else if(game.gameState == Game.STATE.Player2)
								{
					  			objectWorkerP2.get(drawLocWorker2.get(1)).setX(xStart);
					  			objectWorkerP2.get(drawLocWorker2.get(1)).setY(yStart);
					  			workerTilesInPlay.add(objectWorkerP2.get(drawLocWorker2.get(1)));
					  			drawLocWorker2.remove(1);
					  			drawLocWorker2.remove(1);

					  			//hashMapKeysWorkerP2.remove(drawLocWorker2.get(1));
					  			//objectWorkerP2.remove(drawLocWorker2.get(1));
					  			placedWorker1 = true;
									game.drawWorker1 = true;
								}
								else if(game.gameState == Game.STATE.Player3)
								{
					  			objectWorkerP3.get(drawLocWorker3.get(1)).setX(xStart);
					  			objectWorkerP3.get(drawLocWorker3.get(1)).setY(yStart);
					  			workerTilesInPlay.add(objectWorkerP3.get(drawLocWorker3.get(1)));
					  			drawLocWorker3.remove(1);
					  			drawLocWorker3.remove(1);

					  			//hashMapKeysWorkerP3.remove(drawLocWorker3.get(1));
					  			//objectWorkerP3.remove(drawLocWorker3.get(1));
					  			placedWorker1 = true;
									game.drawWorker1 = true;
								}
								else if(game.gameState == Game.STATE.Player4)
								{
					  			objectWorkerP4.get(drawLocWorker4.get(1)).setX(xStart);
					  			objectWorkerP4.get(drawLocWorker4.get(1)).setY(yStart);
					  			workerTilesInPlay.add(objectWorkerP4.get(drawLocWorker4.get(1)));
					  			drawLocWorker4.remove(1);
					  			drawLocWorker4.remove(1);

					  			//hashMapKeysWorkerP4.remove(drawLocWorker4.get(1));
					  			//objectWorkerP4.remove(drawLocWorker4.get(1));
					  			placedWorker1 = true;
									game.drawWorker1 = true;
								}
					    }
					    if(select.mouse2Released == true)
					    {
								//System.out.println("worker 2 returned");
								if(game.gameState == Game.STATE.Player1)
								{
					  			objectWorkerP1.get(drawLocWorker1.get(2)).setX(xStart);
					  			objectWorkerP1.get(drawLocWorker1.get(2)).setY(yStart);
					  			workerTilesInPlay.add(objectWorkerP1.get(drawLocWorker1.get(2)));
					  			drawLocWorker1.remove(2);
					  			drawLocWorker1.remove(2);

					  			//hashMapKeysWorkerP1.remove(drawLocWorker1.get(2));
					  			//objectWorkerP1.remove(drawLocWorker1.get(2));
					  			placedWorker2 = true;
									game.drawWorker2 = true;
								}
								else if(game.gameState == Game.STATE.Player2)
								{
					  			objectWorkerP2.get(drawLocWorker2.get(2)).setX(xStart);
					  			objectWorkerP2.get(drawLocWorker2.get(2)).setY(yStart);
					  			workerTilesInPlay.add(objectWorkerP2.get(drawLocWorker2.get(2)));
					  			drawLocWorker2.remove(2);
					  			drawLocWorker2.remove(2);

					  			//hashMapKeysWorkerP2.remove(drawLocWorker2.get(2));
					  			//objectWorkerP2.remove(drawLocWorker2.get(2));
					  			placedWorker2 = true;
									game.drawWorker2 = true;
								}
								else if(game.gameState == Game.STATE.Player3)
								{
					  			objectWorkerP3.get(drawLocWorker3.get(2)).setX(xStart);
					  			objectWorkerP3.get(drawLocWorker3.get(2)).setY(yStart);
					  			workerTilesInPlay.add(objectWorkerP3.get(drawLocWorker3.get(2)));
					  			drawLocWorker3.remove(2);
					  			drawLocWorker3.remove(2);

					  			//hashMapKeysWorkerP3.remove(drawLocWorker3.get(2));
					  			//objectWorkerP3.remove(drawLocWorker3.get(2));
					  			placedWorker2 = true;
									game.drawWorker2 = true;
								}
								else if(game.gameState == Game.STATE.Player4)
								{
					  			objectWorkerP4.get(drawLocWorker4.get(2)).setX(xStart);
					  			objectWorkerP4.get(drawLocWorker4.get(2)).setY(yStart);
					  			workerTilesInPlay.add(objectWorkerP4.get(drawLocWorker4.get(2)));
					  			drawLocWorker4.remove(2);
					  			drawLocWorker4.remove(2);

					  			//hashMapKeysWorkerP4.remove(drawLocWorker4.get(2));
					  			//objectWorkerP4.remove(drawLocWorker4.get(2));
					  			placedWorker2 = true;
									game.drawWorker2 = true;
								}

					    }
					    if(select.mouse3Released == true)
					    {
								//System.out.println("worker 3 returned");
								if(game.gameState == Game.STATE.Player1)
								{
					  			objectWorkerP1.get(drawLocWorker1.get(3)).setX(xStart);
					  			objectWorkerP1.get(drawLocWorker1.get(3)).setY(yStart);
					  			workerTilesInPlay.add(objectWorkerP1.get(drawLocWorker1.get(3)));
					  			drawLocWorker1.remove(3);
					  			drawLocWorker1.remove(3);

					  			//hashMapKeysWorkerP1.remove(drawLocWorker1.get(3));
					  			//objectWorkerP1.remove(drawLocWorker1.get(3));
					  			placedWorker3 = true;
									game.drawWorker3 = true;
								}
								else if(game.gameState == Game.STATE.Player2)
								{
					  			objectWorkerP2.get(drawLocWorker2.get(3)).setX(xStart);
					  			objectWorkerP2.get(drawLocWorker2.get(3)).setY(yStart);
					  			workerTilesInPlay.add(objectWorkerP2.get(drawLocWorker2.get(3)));
					  			drawLocWorker2.remove(3);
					  			drawLocWorker2.remove(3);

					  			//hashMapKeysWorkerP2.remove(drawLocWorker2.get(3));
					  			//objectWorkerP2.remove(drawLocWorker2.get(3));
					  			placedWorker3 = true;
									game.drawWorker3 = true;
								}
								else if(game.gameState == Game.STATE.Player3)
								{
					  			objectWorkerP3.get(drawLocWorker3.get(3)).setX(xStart);
					  			objectWorkerP3.get(drawLocWorker3.get(3)).setY(yStart);
					  			workerTilesInPlay.add(objectWorkerP3.get(drawLocWorker3.get(3)));
					  			drawLocWorker3.remove(3);
					  			drawLocWorker3.remove(3);

					  			//hashMapKeysWorkerP3.remove(drawLocWorker3.get(3));
					  			//objectWorkerP3.remove(drawLocWorker3.get(3));
					  			placedWorker3 = true;
									game.drawWorker3 = true;
								}
								else if(game.gameState == Game.STATE.Player4)
								{
					  			objectWorkerP4.get(drawLocWorker4.get(3)).setX(xStart);
					  			objectWorkerP4.get(drawLocWorker4.get(3)).setY(yStart);
					  			workerTilesInPlay.add(objectWorkerP4.get(drawLocWorker4.get(3)));
					  			drawLocWorker4.remove(3);
					  			drawLocWorker4.remove(3);

					  			//hashMapKeysWorkerP4.remove(drawLocWorker4.get(3));
					  			//objectWorkerP4.remove(drawLocWorker4.get(3));
					  			placedWorker3 = true;
									game.drawWorker3 = true;
								}

					    }
					    
					    break;//break out if we have found the correct row and column that is acceptable
						}
						}
					}

				}
				
				
				//return the worker tile back if it is not placed in the correct location
		    if(placedWorker1 == false)
		    {
					//System.out.println("worker 1 returned");
					if(game.gameState == Game.STATE.Player1)
					{
		  			objectWorkerP1.get(drawLocWorker1.get(1)).setX(game.draw1WorkerLocX);
		  			objectWorkerP1.get(drawLocWorker1.get(1)).setY(game.draw1WorkerLocY);
					}
					else if(game.gameState == Game.STATE.Player2)
					{
		  			objectWorkerP2.get(drawLocWorker2.get(1)).setX(game.draw1WorkerLocX);
		  			objectWorkerP2.get(drawLocWorker2.get(1)).setY(game.draw1WorkerLocY);
					}
					else if(game.gameState == Game.STATE.Player3)
					{
		  			objectWorkerP3.get(drawLocWorker3.get(1)).setX(game.draw1WorkerLocX);
		  			objectWorkerP3.get(drawLocWorker3.get(1)).setY(game.draw1WorkerLocY);
					}
					else if(game.gameState == Game.STATE.Player4)
					{
		  			objectWorkerP4.get(drawLocWorker4.get(1)).setX(game.draw1WorkerLocX);
		  			objectWorkerP4.get(drawLocWorker4.get(1)).setY(game.draw1WorkerLocY);
					}
		    }
		    if(placedWorker2 == false)
		    {
					//System.out.println("worker 2 returned");
					if(game.gameState == Game.STATE.Player1)
					{
		  			objectWorkerP1.get(drawLocWorker1.get(2)).setX(game.draw2WorkerLocX);
		  			objectWorkerP1.get(drawLocWorker1.get(2)).setY(game.draw2WorkerLocY);
					}
					else if(game.gameState == Game.STATE.Player2)
					{
		  			objectWorkerP2.get(drawLocWorker2.get(2)).setX(game.draw2WorkerLocX);
		  			objectWorkerP2.get(drawLocWorker2.get(2)).setY(game.draw2WorkerLocY);
					}
					else if(game.gameState == Game.STATE.Player3)
					{
		  			objectWorkerP3.get(drawLocWorker3.get(2)).setX(game.draw2WorkerLocX);
		  			objectWorkerP3.get(drawLocWorker3.get(2)).setY(game.draw2WorkerLocY);
					}
					else if(game.gameState == Game.STATE.Player4)
					{
		  			objectWorkerP4.get(drawLocWorker4.get(2)).setX(game.draw2WorkerLocX);
		  			objectWorkerP4.get(drawLocWorker4.get(2)).setY(game.draw2WorkerLocY);
					}

		    }
		    if(placedWorker3 == false)
		    {
					//System.out.println("worker 3 returned");
					if(game.gameState == Game.STATE.Player1)
					{
		  			objectWorkerP1.get(drawLocWorker1.get(3)).setX(game.draw3WorkerLocX);
		  			objectWorkerP1.get(drawLocWorker1.get(3)).setY(game.draw3WorkerLocY);
					}
					else if(game.gameState == Game.STATE.Player2)
					{
		  			objectWorkerP2.get(drawLocWorker2.get(3)).setX(game.draw3WorkerLocX);
		  			objectWorkerP2.get(drawLocWorker2.get(3)).setY(game.draw3WorkerLocY);
					}
					else if(game.gameState == Game.STATE.Player3)
					{
		  			objectWorkerP3.get(drawLocWorker3.get(3)).setX(game.draw3WorkerLocX);
		  			objectWorkerP3.get(drawLocWorker3.get(3)).setY(game.draw3WorkerLocY);
					}
					else if(game.gameState == Game.STATE.Player4)
					{
		  			objectWorkerP4.get(drawLocWorker4.get(3)).setX(game.draw3WorkerLocX);
		  			objectWorkerP4.get(drawLocWorker4.get(3)).setY(game.draw3WorkerLocY);
					}

		    }
		    
				if(game.typeState == Game.TYPESTATE.Jungle)
				{
					for(String coordinate: grid.validJungleTileLoc)
					{
						/*
						System.out.println("xstart " + ((int)(coordinate.charAt(0)) - 65) * game.TILE_DIM);
						System.out.println("mxR " + select.mxR);
						System.out.println("xend " + (((int)(coordinate.charAt(0)) - 65) * game.TILE_DIM + game.TILE_DIM));

						System.out.println("ystart " + ((int)(coordinate.charAt(1)) - 48 - 1) * game.TILE_DIM);
						System.out.println("myR " + select.myR);
						System.out.println("yend " + (((int)(coordinate.charAt(1)) - 48 - 1) * game.TILE_DIM + game.TILE_DIM));
						*/
						
						//check if the released point is within the x-range of any valid worker tile location
						if(select.mxR > ((int)(coordinate.charAt(0)) - 65) * game.TILE_DIM  && select.mxR < (((int)(coordinate.charAt(0)) - 65) * game.TILE_DIM) + game.TILE_DIM)
						{
							//System.out.println("correct row: " + String.valueOf(coordinate.charAt(0) - 65));
							//check if the released point is within the y-range of any valid worker tile location
							//you have to subtract an additional 1 sometimes because it will let you start at the correct location
							if(select.myR > ((int)(coordinate.charAt(1)) - 48 - 1) * game.TILE_DIM  && select.myR < ((int)(coordinate.charAt(1)) - 48 - 1) * game.TILE_DIM + game.TILE_DIM)
							{
								//System.out.println("correct col: " + ((int)(coordinate.charAt(1)) - 48));
								
								//System.out.println("mxR" + select.mxR);
								//System.out.println("myR" + select.myR);
								
								int xStart = ((int)(coordinate.charAt(0)) - 65) * game.TILE_DIM;
								int yStart = ((int)(coordinate.charAt(1)) - 48 - 1) * game.TILE_DIM;
								
								String newGridUsed = String.valueOf((char)((int)(coordinate.charAt(0)))) + String.valueOf(((int)(coordinate.charAt(1)) - 48));
								
								grid.gridUsed.replace(newGridUsed, 1);
								
								//if it is an acceptable square, place the correct object at the new location
						    if(select.mouseJungle1Released == true)
						    {
									//System.out.println("worker 1 returned");
					  			objectJungle.get(drawLocJungle.get(1)).setX(xStart);
					  			objectJungle.get(drawLocJungle.get(1)).setY(yStart);
					  			drawLocJungle.remove(1);
					  			drawLocJungle.remove(1);
					  			placedJungle1 = true;
									game.drawJungle1 = true;
						    }
						    if(select.mouseJungle2Released == true)
						    {
									//System.out.println("worker 2 returned");
									objectJungle.get(drawLocJungle.get(2)).setX(xStart);
									objectJungle.get(drawLocJungle.get(2)).setY(yStart);
					  			drawLocJungle.remove(2);
					  			drawLocJungle.remove(2);
					  			placedJungle2 = true;
									game.drawJungle2 = true;
						    }
						    break;//break out if we have found the correct row and column that is acceptable
						  }
						}
						
					}
			
			    if(placedJungle1 == false && select.mouseJungle1Released == true)
			    {
						System.out.println("jungle 1 returned");
		  			objectJungle.get(drawLocJungle.get(1)).setX(game.draw1LocX);
		  			objectJungle.get(drawLocJungle.get(1)).setY(game.draw1LocY);
			    }
			    if(placedJungle2 == false && select.mouseJungle2Released == true)
			    {
						System.out.println("jungle 2 returned");
		  			objectJungle.get(drawLocJungle.get(2)).setX(game.draw2LocX);
		  			objectJungle.get(drawLocJungle.get(2)).setY(game.draw2LocY);
			    }
				}
				
			}//if mouse released = true

				select.mouseJungle1Released = false;
				select.mouseJungle2Released = false;
		    select.mouse1Released = false;
		    select.mouse2Released = false;
		    select.mouse3Released = false;
		    
		    
		}//end tick
			

		//}
	//}
	
	public void render(Graphics g, Game game)
	{
		if(game.gameState != Game.STATE.Select)
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
			}
			
			else if(game.gameState == Game.STATE.Player2)
			{
				if(game.gameState == Game.STATE.Player2)
				{
					for(int i = 0; i < objectWorkerP2.size(); i++) 
					{
						GameObject tempObject = objectWorkerP2.get(hashMapKeysWorkerP2.get(i));
					
						//this is an abstract class that is called in the player or tile classes
						tempObject.render(g);
					}
				}
			}
			
			else if(game.gameState == Game.STATE.Player3)
			{
				if(game.gameState == Game.STATE.Player3)
				{
					for(int i = 0; i < objectWorkerP3.size(); i++) 
					{
						GameObject tempObject = objectWorkerP3.get(hashMapKeysWorkerP3.get(i));
					
						//this is an abstract class that is called in the player or tile classes
						tempObject.render(g);
					}
				}
			}
			
			else if(game.gameState == Game.STATE.Player4)
			{
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
			
			//render all worker tiles in play regardless of player turn
			for(int i = 0; i < workerTilesInPlay.size(); i++) 
			{
				GameObject tempObject = workerTilesInPlay.get(i);
			
				//this is an abstract class that is called in the player or tile classes
				tempObject.render(g);
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
			//System.out.println("game.turnEnd == true");
			if(game.gameState == Game.STATE.Play && game.typeState == Game.TYPESTATE.Jungle && game.turnState == Game.TURNSTATE.Draw)
			{
				System.out.println("proper game state, type, and turn");
				if(game.drawJungle1 == true)
				{
					drawFromDeck(deckKeysJungle, objectJungle, drawLocJungle, null, game.draw1LocX, game.draw1LocY, game);
					removeFromDeck(deckKeysJungle);
					game.drawJungle1 = false;
					System.out.println("tried to draw to location 1");
				}
				if(game.drawJungle2 == true)
				{
					drawFromDeck(deckKeysJungle, objectJungle, drawLocJungle, null,  game.draw2LocX, game.draw2LocY, game);
					removeFromDeck(deckKeysJungle);
					game.drawJungle2 = false;
					System.out.println("tried to draw to location 2");
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
		int [] test = new int [4];
		
		//select the worker tiles to move and rotate
		if(game.typeState == Game.TYPESTATE.Worker && game.turnState == Game.TURNSTATE.Move)
		{
			if(game.gameState == Game.STATE.Player1)
			{
				//Performing rotations
				
				if(select.worker1Clicked == true && select.worker1ActionComplete == false)
				{
					//increment the rotation counter to determine which rotation sprite to be loaded
	        handler.objectWorkerP1.get(handler.drawLocWorker1.get(1)).incrementRotation();
	        //complete the action to prevent indefinite spinning/rotating of the tile
	        select.worker1ActionComplete = true;
				}
				else if(select.worker2Clicked == true && select.worker2ActionComplete == false)
				{
					//increment the rotation counter to determine which rotation sprite to be loaded
	        handler.objectWorkerP1.get(handler.drawLocWorker1.get(2)).incrementRotation();
	        //complete the action to prevent indefinite spinning/rotating of the tile
	        select.worker2ActionComplete = true;
				}
				else if(select.worker3Clicked == true && select.worker3ActionComplete == false)
				{
					//increment the rotation counter to determine which rotation sprite to be loaded
	        handler.objectWorkerP1.get(handler.drawLocWorker1.get(3)).incrementRotation();
	        //complete the action to prevent indefinite spinning/rotating of the tile
	        select.worker3ActionComplete = true;
				}

			}
			else if(game.gameState == Game.STATE.Player2)
			{
				//Performing rotations
				
				if(select.worker1Clicked == true && select.worker1ActionComplete == false)
				{
					//increment the rotation counter to determine which rotation sprite to be loaded
	        handler.objectWorkerP2.get(handler.drawLocWorker2.get(1)).incrementRotation();
	        //complete the action to prevent indefinite spinning/rotating of the tile
	        select.worker1ActionComplete = true;
				}
				else if(select.worker2Clicked == true && select.worker2ActionComplete == false)
				{
					//increment the rotation counter to determine which rotation sprite to be loaded
	        handler.objectWorkerP2.get(handler.drawLocWorker2.get(2)).incrementRotation();
	        //complete the action to prevent indefinite spinning/rotating of the tile
	        select.worker2ActionComplete = true;
				}
				else if(select.worker3Clicked == true && select.worker3ActionComplete == false)
				{
					//increment the rotation counter to determine which rotation sprite to be loaded
	        handler.objectWorkerP2.get(handler.drawLocWorker2.get(3)).incrementRotation();
	        //complete the action to prevent indefinite spinning/rotating of the tile
	        select.worker3ActionComplete = true;
				}
			}
			else if(game.gameState == Game.STATE.Player3)
			{
				//Performing rotations
				
				if(select.worker1Clicked == true && select.worker1ActionComplete == false)
				{
					//increment the rotation counter to determine which rotation sprite to be loaded
	        handler.objectWorkerP3.get(handler.drawLocWorker3.get(1)).incrementRotation();  
	        //complete the action to prevent indefinite spinning/rotating of the tile
	        select.worker1ActionComplete = true;
				}
				else if(select.worker2Clicked == true && select.worker2ActionComplete == false)
				{
					//increment the rotation counter to determine which rotation sprite to be loaded
	        handler.objectWorkerP3.get(handler.drawLocWorker3.get(2)).incrementRotation();
	        //complete the action to prevent indefinite spinning/rotating of the tile
	        select.worker2ActionComplete = true;
				}
				else if(select.worker3Clicked == true && select.worker3ActionComplete == false)
				{
					//increment the rotation counter to determine which rotation sprite to be loaded
	        handler.objectWorkerP3.get(handler.drawLocWorker3.get(3)).incrementRotation();
	        //complete the action to prevent indefinite spinning/rotating of the tile
	        select.worker3ActionComplete = true;
				}
			}
			else if(game.gameState == Game.STATE.Player4)
			{
				//Performing rotations
				
				if(select.worker1Clicked == true && select.worker1ActionComplete == false)
				{
					//increment the rotation counter to determine which rotation sprite to be loaded
	        handler.objectWorkerP4.get(handler.drawLocWorker4.get(1)).incrementRotation();
	        //complete the action to prevent indefinite spinning/rotating of the tile
	        select.worker1ActionComplete = true;
				}
				else if(select.worker2Clicked == true && select.worker2ActionComplete == false)
				{
					//increment the rotation counter to determine which rotation sprite to be loaded
	        handler.objectWorkerP4.get(handler.drawLocWorker4.get(2)).incrementRotation();
	        //complete the action to prevent indefinite spinning/rotating of the tile
	        select.worker2ActionComplete = true;
				}
				else if(select.worker3Clicked == true && select.worker3ActionComplete == false)
				{
					//increment the rotation counter to determine which rotation sprite to be loaded
	        handler.objectWorkerP4.get(handler.drawLocWorker4.get(3)).incrementRotation();
	        //complete the action to prevent indefinite spinning/rotating of the tile
	        select.worker3ActionComplete = true;
				}
			}
		}
	}
	
}
