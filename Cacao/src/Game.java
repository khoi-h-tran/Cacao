import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/* 
	File Name: Game.java
	   Author: Khoi Tran
		 Date: Dec. 26, 2019 9:35:11 p.m.
  Description: Game Loop Class
*/

//extend canvas to be displayed on GUI
//implement runnable to allow thread to run
//requires importing Canvas library
public class Game extends Canvas implements Runnable
{
	//Serial versions are to ensure that data sent is the correct version
	//Eclipse auto generates this for any class that extends canvas (cntrl +shift+o)
	private static final long serialVersionUID = 1L;
	
	//sets width
	public static final int WIDTH = 1400;
	
	//sets aspect ratio
	public static final int HEIGHT = WIDTH / 4*3;
	
	//side length of tile (they are squares)
	protected final int TILE_DIM = 123;
	//side length of ressource image
	protected final int RES_DIM = 31;
	
	//offset from title bar
	protected final int TITLE_BAR = 40;
	
	//offset for each ressource icon
	protected final int iconOffset = 42;
	
	//total turn counter
	protected int turnCounter = 0;
	
	//player turn tracker
	protected int playerTracker = 0;
	
	//creating instance of thread class
	private Thread thread;
	
	//initializing variable used in game loop
	private boolean running = false;
	
	//creating buffered image
	private BufferedImage spriteSheetTiles = null;
	private BufferedImage spriteSheetRes = null;
	private BufferedImage spriteSheetWorkerTiles = null;
	
	//creating objects to store sprite sheets
	public SpriteSheet sst;
	public SpriteSheet ssr;
	public SpriteSheet sswt;
	
	//initializing the number of players
	protected int numPlayers = 0;
	
	//create instance of handler class
	private Handler handler;
	
	//create instance of heads up display class
	private HUD hud;
	
	//create instance of grid class
	private Grid grid;
	
	//create instance of select class
	private Select select;
	
	//create instance of end class for game over
	private End end = new End();
	
	//create instance of mouse event class
	private MouseEvent e;
	
	//create instance of ID enumeration
	private ID id;
	private IDPlayer idplayer;
	
	// 0 means no state
	// 1 means jungle type was employed and you need to clear the list for worker tiles
	// 2 means worker type was employed and you need to clear the list for jungle tiles
	public int tileState = 0;
	
	//create states for the game
	public enum STATE {Player1, Player2, Player3, Player4, Select, Play, Pause, End};
	public enum TYPESTATE{Worker, Jungle};
	public enum TURNSTATE {Draw, Move, End};
	
	public STATE gameState = STATE.Select;
	public TYPESTATE typeState = TYPESTATE.Jungle;
	public TURNSTATE turnState = TURNSTATE.Draw;
	
	//create constant for draw location
	
	//where jungle deck is drawn to
	public final int draw1LocX = (HEIGHT + TILE_DIM/4);
	public final int draw1LocY = (HEIGHT - 2*TILE_DIM - TITLE_BAR*2);
	
	//Second place where jungle deck is drawn to
	public final int draw2LocX = (HEIGHT + TILE_DIM/2 + TILE_DIM);
	public final int draw2LocY = (HEIGHT - 2*TILE_DIM - TITLE_BAR*2);
	
	//First place where worker deck is drawn to
	public final int draw1WorkerLocX = (HEIGHT + TILE_DIM/4);
	public final int draw1WorkerLocY = (HEIGHT - 4*TILE_DIM - TITLE_BAR*5/2 - (TILE_DIM/3)); // - (draw1LocY - TILE_DIM/3) is to line it up to the "drawn jungle tiles" label
	
	//Second place where worker deck is drawn to
	public final int draw2WorkerLocX = (HEIGHT + TILE_DIM/2 + TILE_DIM);
	public final int draw2WorkerLocY = (HEIGHT - 4*TILE_DIM - TITLE_BAR*5/2 - (TILE_DIM/3));
	
	//Third place where worker deck is drawn to
	public final int draw3WorkerLocX = (HEIGHT + (WIDTH - HEIGHT)/2 - TILE_DIM/2);
	public final int draw3WorkerLocY = (HEIGHT - 3*TILE_DIM - TITLE_BAR*2 - (TILE_DIM/3));
	
	//Where the jungle tile deck location is stored
	public final int deckLocJungleX = (HEIGHT + TILE_DIM/4);
	public final int deckLocJungleY = (HEIGHT -  TILE_DIM - TITLE_BAR);
	
	//Where the worker tile deck location is stored
	public final int deckLocWorkerX = (HEIGHT + TILE_DIM*6/4);
	public final int deckLocWorkerY = (HEIGHT -  TILE_DIM - TITLE_BAR);
	
	//create variable to determine if a jungle tile should be drawn
	public Boolean turnEnd = false;
	public Boolean drawJungle1 = false;
	public Boolean drawJungle2 = false;
	public Boolean drawWorker1 = false;
	public Boolean drawWorker2 = false;
	public Boolean drawWorker3 = false;
	
	
	//create variable to skip the jungle tiles on the board, when creating the deck
	String remove1 = " ";
	String remove2 = " ";
	boolean nextPlayer = false; // used in turn rotation to see if we should change to the next player
	boolean jungleValidChecked = false; // allows the the tick and render in the Grid class to determine if there are any valid jungle tiles
	boolean placedWorkerSimulated = false;//This is for when the game is over, to simulate the worker turn without doing anything (to progress in turn rotation)
	
	public int [] scoreScheme = {0,0,0,0};
	
	//Game constructor class
	public Game()
	{

		//sets the Window constructor for the window sizing
		//note "this" keyword refers to the Game classes game instance
		new Window(WIDTH, HEIGHT, "Cacao", this);
		
		//ensure that the game starts off with the jungle type state so grid will work (it doesn't work on the first run if jungle isn't run first)
		typeState = TYPESTATE.Jungle;
		
		grid = new Grid();
		hud = new HUD();
		select = new Select(this,handler);
		
		//initialize sprite sheets
		init();
	
		//initializing constructor
		handler = new Handler();
		
		//this tells game to listen to the KeyInput class
		this.addKeyListener(new KeyInput(handler));
		
		//this tells game to listen to the mouse clicking class
		this.addMouseListener(select);

		//this tells game to listen to the mouse movements class
		this.addMouseMotionListener(select);
	}
	
	//alternate through turns
	/*
	1. Initialize game
	2. Player 1
		- Worker Tile
		- Jungle Tile(s)
		- Draw
	3. Repeat with all other players until game over
	*/
	public void turnRotation()
	{
		if(turnCounter == 0 && numPlayers > 0)
		{
			initGame();
			incrementTurn();
		}
		
		if(playerTracker == 1)
		{
			//check if there are any playable grids left, if not, the game is over
			if(!grid.gridUsed.containsValue(0))
			{
				gameState = STATE.End;
				nextPlayer = true;
			}
			
			if(handler.drawLocWorker1.isEmpty())
			{
				int outOfCards = 1;
				
				//nextPlayer = true;
				//turnState = TURNSTATE.End;
				
				if(numPlayers >= 2 && handler.drawLocWorker2.isEmpty())
				{
					outOfCards++;
					if(outOfCards == 2 && numPlayers == 2)
					{
						gameState = STATE.End;
						nextPlayer = true;
					}
				}
				if(numPlayers >= 3 && handler.drawLocWorker3.isEmpty())
				{
					outOfCards++;
					if(outOfCards == 3 && numPlayers == 3)
					{
						gameState = STATE.End;
						nextPlayer = true;
					}
				}
				if(numPlayers >= 4 && handler.drawLocWorker4.isEmpty())
				{
					outOfCards++;
					if(outOfCards == 4 && numPlayers == 4)
					{
						gameState = STATE.End;
						nextPlayer = true;
					}
				}
				
				outOfCards = 1;
			}
			
			if(gameState != STATE.End && ((handler.placedWorker1 != true && handler.placedWorker2 != true && handler.placedWorker3 != true)  || (handler.drawLocWorker1.isEmpty() && placedWorkerSimulated == false)))
			{
				/*
				for(String key: grid.listOfKeys)
				{
					System.out.println("jungle key: " + key);
				}
				*/
				nextPlayer = false;
				gameState = STATE.Player1;
				typeState = TYPESTATE.Worker;
				turnState = TURNSTATE.Move;
				
				if(handler.drawLocWorker1.isEmpty())
				{
					placedWorkerSimulated = true;
				}
			}
			else if((handler.placedWorker1 == true || handler.placedWorker2 == true || handler.placedWorker3 == true || placedWorkerSimulated == true) && turnState != TURNSTATE.Draw && jungleValidChecked == false )
			{
				typeState = TYPESTATE.Jungle;
				jungleValidChecked = true;
			}
			else if((handler.placedWorker1 == true || handler.placedWorker2 == true || handler.placedWorker3 == true || placedWorkerSimulated == true) && turnState != TURNSTATE.Draw && jungleValidChecked == true)
			{
				/*
				for(String key: grid.listOfKeys)
				{
					System.out.println("worker key: " + key);
				}
				*/
				//grid.validJungleTile == true && 
				//System.out.println(grid.validJungleTileLoc.size());
				if(grid.validJungleTileLoc.size() != 0 && grid.colorYellow && !handler.drawLocJungle.isEmpty())
				{
					if(!handler.drawLocJungle.isEmpty())
					{
						turnState = TURNSTATE.Move;
					}
				}
				//grid.validJungleTile == false  && 
				else if( (grid.validJungleTileLoc.size() == 0 && !grid.colorYellow) || handler.drawLocJungle.isEmpty())
				{
					//System.out.println("Change state to draw");
					turnState = TURNSTATE.Draw;
					jungleValidChecked = false;
					/*
					if(handler.drawLocWorker1.isEmpty())
					{
						nextPlayer = false;
						System.out.println("stuck in jungle move for player 1");
						turnState = TURNSTATE.Move;
					}
					*/
				}
			}
			
			if(turnState == TURNSTATE.Draw && nextPlayer == false)
			{				
				gameState = STATE.Play;
				typeState = TYPESTATE.Jungle;
				handler.endTurnTrue(this);
				handler.drawCard(this);

				gameState = STATE.Player1;
				typeState = TYPESTATE.Worker;
				handler.endTurnTrue(this);
				handler.drawCard(this);
				
				turnState = TURNSTATE.End;
				nextPlayer = true;
				//System.out.println("Player 1 draw sequence");
			}
			
			if(turnState == TURNSTATE.End || nextPlayer == true)
			{
				if(gameState != STATE.End)
				{
					grid.validJungleTile = true;
					handler.placedJungle1 = false;
					handler.placedJungle2 = false;
					handler.placedWorker1 = false;
					handler.placedWorker2 = false;
					handler.placedWorker3 = false;
					gameState = STATE.Player2;
					typeState = TYPESTATE.Worker;
					turnState = TURNSTATE.Move;
					incrementTurn();
					//System.out.println("Player 1 end sequence");
					handler.deckKeysEmpty = false;
					placedWorkerSimulated = false;
				}
			}
		}
		
		if(playerTracker == 2)
		{
			//check if there are any playable grids left, if not, the game is over
			if(!grid.gridUsed.containsValue(0))
			{
				gameState = STATE.End;
				nextPlayer = true;
			}
			if(handler.drawLocWorker2.isEmpty())
			{
				int outOfCards = 1;
				
				nextPlayer = true;
				turnState = TURNSTATE.End;
				
				if(numPlayers >= 2 && handler.drawLocWorker1.isEmpty())
				{
					outOfCards++;
					if(outOfCards == 2 && numPlayers == 2)
					{
						gameState = STATE.End;
						nextPlayer = true;
					}
				}
				if(numPlayers >= 3 && handler.drawLocWorker3.isEmpty())
				{
					outOfCards++;
					if(outOfCards == 3 && numPlayers == 3)
					{
						gameState = STATE.End;
						nextPlayer = true;
					}
				}
				if(numPlayers >= 4 && handler.drawLocWorker4.isEmpty())
				{
					outOfCards++;
					if(outOfCards == 4 && numPlayers == 4)
					{
						gameState = STATE.End;
						nextPlayer = true;
					}
				}
				
				outOfCards = 1;
			
			}
			if(gameState != STATE.End && ((handler.placedWorker1 != true && handler.placedWorker2 != true && handler.placedWorker3 != true) || (handler.drawLocWorker2.isEmpty() && placedWorkerSimulated == false)))
			{
				nextPlayer = false;
				gameState = STATE.Player2;
				typeState = TYPESTATE.Worker;
				turnState = TURNSTATE.Move;
				
				if(handler.drawLocWorker1.isEmpty())
				{
					placedWorkerSimulated = true;
				}
			}
			else if((handler.placedWorker1 == true || handler.placedWorker2 == true || handler.placedWorker3 == true || placedWorkerSimulated == true) && turnState != TURNSTATE.Draw && jungleValidChecked == false)
			{
				typeState = TYPESTATE.Jungle;
				jungleValidChecked = true;
			}
			else if((handler.placedWorker1 == true || handler.placedWorker2 == true || handler.placedWorker3 == true || placedWorkerSimulated == true) && turnState != TURNSTATE.Draw && jungleValidChecked == true)
			{
				if(grid.validJungleTileLoc.size() != 0 && grid.colorYellow && !handler.drawLocJungle.isEmpty())
				{

					if(!handler.drawLocJungle.isEmpty())
					{
						turnState = TURNSTATE.Move;
					}
				}
				else if( (grid.validJungleTileLoc.size() == 0 && !grid.colorYellow) || handler.drawLocJungle.isEmpty())
				{
					turnState = TURNSTATE.Draw;
					jungleValidChecked = false;
				}
			}
			if(turnState == TURNSTATE.Draw && nextPlayer == false)
			{				
				gameState = STATE.Play;
				typeState = TYPESTATE.Jungle;
				turnState = TURNSTATE.Draw;
				handler.endTurnTrue(this);
				handler.drawCard(this);

				gameState = STATE.Player2;
				turnState = TURNSTATE.Draw;
				typeState = TYPESTATE.Worker;
				handler.endTurnTrue(this);
				handler.drawCard(this);
				
				turnState = TURNSTATE.End;
				nextPlayer = true;
			}
			if(turnState == TURNSTATE.End || nextPlayer == true)
			{
				if(gameState != STATE.End)
				{
					grid.validJungleTile = true;
					handler.placedJungle1 = false;
					handler.placedJungle2 = false;
					handler.placedWorker1 = false;
					handler.placedWorker2 = false;
					handler.placedWorker3 = false;
					typeState = TYPESTATE.Worker;
					turnState = TURNSTATE.Move;
					if(numPlayers <= 2)
					{
						gameState = STATE.Player1;
						incrementTurn();
						playerTracker = 1;
					}
					else
					{
						gameState = STATE.Player3;
						incrementTurn();
					}
					handler.deckKeysEmpty = false;
					placedWorkerSimulated = false;
				}
			}
		}

		if(playerTracker == 3)
		{
			//check if there are any playable grids left, if not, the game is over
			if(!grid.gridUsed.containsValue(0))
			{
				gameState = STATE.End;
				nextPlayer = true;
			}
			if(handler.drawLocWorker3.isEmpty())
			{
				int outOfCards = 1;
				
				nextPlayer = true;
				turnState = TURNSTATE.End;
				
				if(numPlayers >= 2 && handler.drawLocWorker1.isEmpty())
				{
					outOfCards++;
					if(outOfCards == 2 && numPlayers == 2)
					{
						gameState = STATE.End;
						nextPlayer = true;
					}
				}
				if(numPlayers >= 2 && handler.drawLocWorker1.isEmpty())
				{
					outOfCards++;
					if(outOfCards == 3 && numPlayers == 3)
					{
						gameState = STATE.End;
						nextPlayer = true;
					}
				}
				if(numPlayers >= 4 && handler.drawLocWorker4.isEmpty())
				{
					outOfCards++;
					if(outOfCards == 4 && numPlayers == 4)
					{
						gameState = STATE.End;
						nextPlayer = true;
					}
				}
				
				outOfCards = 1;
			
			}
			if(gameState != STATE.End && ((handler.placedWorker1 != true && handler.placedWorker2 != true && handler.placedWorker3 != true) || (handler.drawLocWorker3.isEmpty() && placedWorkerSimulated == false)))
			{
				nextPlayer = false;
				gameState = STATE.Player3;
				typeState = TYPESTATE.Worker;
				turnState = TURNSTATE.Move;
				
				if(handler.drawLocWorker3.isEmpty())
				{
					placedWorkerSimulated = true;
				}
			}
			else if((handler.placedWorker1 == true || handler.placedWorker2 == true || handler.placedWorker3 == true || placedWorkerSimulated == true) && turnState != TURNSTATE.Draw && jungleValidChecked == false)
			{
				typeState = TYPESTATE.Jungle;
				jungleValidChecked = true;
			}
			else if((handler.placedWorker1 == true || handler.placedWorker2 == true || handler.placedWorker3 == true || placedWorkerSimulated == true) && turnState != TURNSTATE.Draw && jungleValidChecked == true)
			{
				if(grid.validJungleTileLoc.size() != 0 && grid.colorYellow && !handler.drawLocJungle.isEmpty())
				{

					if(!handler.drawLocJungle.isEmpty())
					{
						turnState = TURNSTATE.Move;
					}
				}
				else if( (grid.validJungleTileLoc.size() == 0 && !grid.colorYellow) || handler.drawLocJungle.isEmpty())
				{
					turnState = TURNSTATE.Draw;
					jungleValidChecked = false;
				}
			}
			if(turnState == TURNSTATE.Draw && nextPlayer == false)
			{				
				gameState = STATE.Play;
				typeState = TYPESTATE.Jungle;
				turnState = TURNSTATE.Draw;
				handler.endTurnTrue(this);
				handler.drawCard(this);

				gameState = STATE.Player3;
				turnState = TURNSTATE.Draw;
				typeState = TYPESTATE.Worker;
				handler.endTurnTrue(this);
				handler.drawCard(this);
				
				turnState = TURNSTATE.End;
				nextPlayer = true;
			}
			if(turnState == TURNSTATE.End || nextPlayer == true)
			{
				if(gameState != STATE.End)
				{
					grid.validJungleTile = true;
					handler.placedJungle1 = false;
					handler.placedJungle2 = false;
					handler.placedWorker1 = false;
					handler.placedWorker2 = false;
					handler.placedWorker3 = false;
					typeState = TYPESTATE.Worker;
					turnState = TURNSTATE.Move;
					if(numPlayers <= 3)
					{
						gameState = STATE.Player1;
						incrementTurn();
						playerTracker = 1;
					}
					else
					{
						gameState = STATE.Player4;
						incrementTurn();
					}
					handler.deckKeysEmpty = false;
					placedWorkerSimulated = false;
				}
			}
		}

		if(playerTracker == 4)
		{
			//check if there are any playable grids left, if not, the game is over
			if(!grid.gridUsed.containsValue(0))
			{
				gameState = STATE.End;
				nextPlayer = true;
			}
			if(handler.drawLocWorker4.isEmpty())
			{
				int outOfCards = 1;
				
				nextPlayer = true;
				turnState = TURNSTATE.End;
				
				if(numPlayers >= 2 && handler.drawLocWorker1.isEmpty())
				{
					outOfCards++;
					if(outOfCards == 2 && numPlayers == 2)
					{
						gameState = STATE.End;
						nextPlayer = true;
					}
				}
				if(numPlayers >= 2 && handler.drawLocWorker2.isEmpty())
				{
					outOfCards++;
					if(outOfCards == 3 && numPlayers == 3)
					{
						gameState = STATE.End;
						nextPlayer = true;
					}
				}
				if(numPlayers >= 4 && handler.drawLocWorker4.isEmpty())
				{
					outOfCards++;
					if(outOfCards == 4 && numPlayers == 4)
					{
						gameState = STATE.End;
						nextPlayer = true;
					}
				}
				
				outOfCards = 1;
			
			}
			if(gameState != STATE.End && ((handler.placedWorker1 != true && handler.placedWorker2 != true && handler.placedWorker3 != true) || (handler.drawLocWorker4.isEmpty() && placedWorkerSimulated == false)))
			{
				nextPlayer = false;
				gameState = STATE.Player4;
				typeState = TYPESTATE.Worker;
				turnState = TURNSTATE.Move;
				
				if(handler.drawLocWorker4.isEmpty())
				{
					placedWorkerSimulated = true;
				}
			}
			else if((handler.placedWorker1 == true || handler.placedWorker2 == true || handler.placedWorker3 == true || placedWorkerSimulated == true) && turnState != TURNSTATE.Draw && jungleValidChecked == false)
			{
				typeState = TYPESTATE.Jungle;
				jungleValidChecked = true;
			}
			else if((handler.placedWorker1 == true || handler.placedWorker2 == true || handler.placedWorker3 == true || placedWorkerSimulated == true) && turnState != TURNSTATE.Draw && jungleValidChecked == true)
			{
				if(grid.validJungleTileLoc.size() != 0 && grid.colorYellow && !handler.drawLocJungle.isEmpty())
				{

					if(!handler.drawLocJungle.isEmpty())
					{
						turnState = TURNSTATE.Move;
					}
				}
				else if( (grid.validJungleTileLoc.size() == 0 && !grid.colorYellow) || handler.drawLocJungle.isEmpty())
				{
					turnState = TURNSTATE.Draw;
					jungleValidChecked = false;
				}
			}
			if(turnState == TURNSTATE.Draw && nextPlayer == false)
			{				
				gameState = STATE.Play;
				typeState = TYPESTATE.Jungle;
				turnState = TURNSTATE.Draw;
				handler.endTurnTrue(this);
				handler.drawCard(this);

				gameState = STATE.Player4;
				turnState = TURNSTATE.Draw;
				typeState = TYPESTATE.Worker;
				handler.endTurnTrue(this);
				handler.drawCard(this);
				
				turnState = TURNSTATE.End;
				nextPlayer = true;
			}
			if(turnState == TURNSTATE.End || nextPlayer == true)
			{
				if(gameState != STATE.End)
				{
					grid.validJungleTile = true;
					handler.placedJungle1 = false;
					handler.placedJungle2 = false;
					handler.placedWorker1 = false;
					handler.placedWorker2 = false;
					handler.placedWorker3 = false;
					typeState = TYPESTATE.Worker;
					turnState = TURNSTATE.Move;
					if(numPlayers <= 4)
					{
						gameState = STATE.Player1;
						incrementTurn();
						playerTracker = 1;
					}
					handler.deckKeysEmpty = false;
					placedWorkerSimulated = false;
				}
			}
		}
		
	}
	
	public void incrementTurn()
	{
		turnCounter++;
		if(playerTracker <= numPlayers)
		{
			playerTracker++;
		}
		else if(playerTracker == (numPlayers + 1))
		{
			playerTracker = 1;
		}
	}
	
	//initialize the game
	public void initGame()
	{
		//populate the coordinate of the game grid
		grid.popGridCoord(this, handler);
		
		//populate score cards for all players
		for(int i = 1; i <= numPlayers; i++)
		{
			switch(i)
			{
				case 1:
					idplayer = IDPlayer.Player1;
					break;
				case 2:
					idplayer = IDPlayer.Player2;
					break;
				case 3:
					idplayer = IDPlayer.Player3;
					break;
				case 4:
					idplayer = IDPlayer.Player4;
					break;
			}
			
			//creating a score card for each player
			handler.addObject("Player" + String.valueOf(i), new playerScore(), ID.ScoreCard, idplayer);
			
			if(idplayer == IDPlayer.Player1)
			{
				handler.popScoresToZero(handler.scoreCountP1);
			}
			else if(idplayer == IDPlayer.Player2)
			{
				handler.popScoresToZero(handler.scoreCountP2);
			}
			else if(idplayer == IDPlayer.Player3)
			{
				handler.popScoresToZero(handler.scoreCountP3);
			}
			else if(idplayer == IDPlayer.Player4)
			{
				handler.popScoresToZero(handler.scoreCountP4);
			}
			
			//creating the resource icon for each player
			handler.addObject("Cacao", new Ressources((Game.HEIGHT + 5), ((TITLE_BAR)*((i-1)*2 + 1) + RES_DIM/5), ID.Ressource, IDRessources.Cacao, idplayer, RES_DIM, ssr), ID.Ressource, idplayer);
			handler.addObject("SunToken", new Ressources((Game.HEIGHT + 5) + iconOffset * 2, ((TITLE_BAR)*((i-1)*2 + 1) + RES_DIM/5), ID.Ressource, IDRessources.SunToken, idplayer, RES_DIM, ssr), ID.Ressource, idplayer);
			handler.addObject("Water", new Ressources((Game.HEIGHT + 5) + iconOffset * 4, ((TITLE_BAR)*((i-1)*2 + 1) + RES_DIM/5), ID.Ressource, IDRessources.Water, idplayer, RES_DIM, ssr), ID.Ressource, idplayer);
			handler.addObject("Temple", new Ressources((Game.HEIGHT + 5) + iconOffset * 6, ((TITLE_BAR)*((i-1)*2 + 1) + RES_DIM/5), ID.Ressource, IDRessources.Temple, idplayer, RES_DIM, ssr), ID.Ressource, idplayer);
			handler.addObject("Meeple", new Ressources((Game.HEIGHT + RES_DIM * 4), ((TITLE_BAR)*((i-1)*2 + 1) - RES_DIM - 5), ID.Ressource, IDRessources.Meeple, idplayer, RES_DIM, ssr), ID.Ressource, idplayer);
			handler.addObject("Gold", new Ressources((Game.HEIGHT + RES_DIM * 5), ((TITLE_BAR)*((i-1)*2 + 1) - RES_DIM - 5), ID.Ressource, IDRessources.Gold, idplayer, RES_DIM, ssr), ID.Ressource, idplayer);
		}

		//populates hash map for all jungle tiles and worker tiles for the game
		/*
		 						***************Jungle Tiles***********
							   GoldMinex2(),//total: 1
							   SellingPricex4(),//total: 1
							  
								 Plantationx2(),//total: 2 
								 SellingPricex2(),//total: 2 (one is placed to start game)
								 (1 drawn) SunWorshippingSite(),//total: 2, if 2 players - 1
								 GoldMinex1(),//total: 2, if 2 players - 1
								
								
								 (1 drawn) Water(),//total: 3, if 2 players - 1 ******************
								
								 SellingPricex3(),//total: 4, if 2 players - 1
							
								 4 Temple(),//total: 5, if 2 players - 1
								
								 Plantationx1(),//total: 6, if 2 players - 2 (one is placed to start game)
								
								***************Worker Tiles***********
								
								ThreeZeroZeroOne(),//total: 1
								ThreeOneZeroZero();//total: 1
								OneOneOneOne(),//total: 4, if 3 or 4 players minus 1 tile
								TwoOneZeroOne(),//total: 5,  if 4 players minus 1 tile
								
		 */
		
		//populating Gold Mine, SellingPricex4 jungle tiles
		//populating ThreeZeroZeroOne, ThreeOneZeroZero worker tiles
		//tiles with only 1 count
		
		int max = 1;
		
		for(int i = 0; i < max; i++)
		{
			handler.addObject(IDJungle.GoldMinex2 + String.valueOf(i),new JungleTiles(deckLocJungleX, deckLocJungleY, ID.JungleTile, IDJungle.GoldMinex2, TILE_DIM, sst), ID.JungleTile, null);
			handler.addKey(IDJungle.GoldMinex2 + String.valueOf(i), ID.JungleTile, null);
			
			handler.addObject(IDJungle.SellingPricex4 + String.valueOf(i),new JungleTiles(deckLocJungleX, deckLocJungleY, ID.JungleTile, IDJungle.SellingPricex4, TILE_DIM, sst), ID.JungleTile, null);
			handler.addKey(IDJungle.SellingPricex4 + String.valueOf(i), ID.JungleTile, null);
			
			for(int k = 1; k <= numPlayers; k++)
			{
				switch(k)
				{
					case 1:
						idplayer = IDPlayer.Player1;
						break;
					case 2:
						idplayer = IDPlayer.Player2;
						break;
					case 3:
						idplayer = IDPlayer.Player3;
						break;
					case 4:
						idplayer = IDPlayer.Player4;
						break;
				}
				handler.addObject(IDWorker.ThreeZeroZeroOne + String.valueOf(i),new WorkerTiles(deckLocWorkerX, deckLocWorkerY, ID.WorkerTile, IDWorker.ThreeZeroZeroOne, TILE_DIM, sswt, idplayer), ID.WorkerTile, idplayer);
				handler.addKey(IDWorker.ThreeZeroZeroOne + String.valueOf(i), ID.WorkerTile, idplayer);
				
				handler.addObject(IDWorker.ThreeOneZeroZero + String.valueOf(i),new WorkerTiles(deckLocWorkerX, deckLocWorkerY, ID.WorkerTile, IDWorker.ThreeOneZeroZero, TILE_DIM, sswt, idplayer), ID.WorkerTile, idplayer);
				handler.addKey(IDWorker.ThreeOneZeroZero + String.valueOf(i), ID.WorkerTile, idplayer);
			}
				
		}
		
		max++;
		
		//populating Plantationx2, SellingPricex2, SunWorshippingSite, GoldMinex1()
		//tiles with 2 count
		for(int i = 0; i < max; i++)
		{
			
			handler.addObject(IDJungle.Plantationx2 + String.valueOf(i),new JungleTiles(deckLocJungleX, deckLocJungleY, ID.JungleTile, IDJungle.Plantationx2, TILE_DIM, sst), ID.JungleTile, null);
			handler.addKey(IDJungle.Plantationx2 + String.valueOf(i), ID.JungleTile, null);
			
			//place the first one in the middle
			if(i == 0)
			{
				handler.addObject(IDJungle.SellingPricex2 + String.valueOf(i),new JungleTiles(grid.gridCoord.get("E"), grid.gridCoord.get("5"), ID.JungleTile, IDJungle.SellingPricex2, TILE_DIM, sst), ID.JungleTile, null);
				handler.addKey(IDJungle.SellingPricex2 + String.valueOf(i), ID.JungleTile, null);
				remove1 = IDJungle.SellingPricex2 + String.valueOf(i);
				handler.jungleTileInPlay.put("E5", remove1);//add the key for the removed tile to the tiles in play at the coorect coordinate
			}
			
			else
			{
				handler.addObject(IDJungle.SellingPricex2 + String.valueOf(i),new JungleTiles(deckLocJungleX, deckLocJungleY, ID.JungleTile, IDJungle.SellingPricex2, TILE_DIM, sst), ID.JungleTile, null);
				handler.addKey(IDJungle.SellingPricex2 + String.valueOf(i), ID.JungleTile, null);
			}
			
			//takes out 1 of each jungle tile below if only 2 players (as per the instructions)
			if(numPlayers == 2 && i == (max - 1))
			{
				
			}
			else 
			{
				handler.addObject(IDJungle.SunWorshippingSite + String.valueOf(i),new JungleTiles(deckLocJungleX, deckLocJungleY, ID.JungleTile, IDJungle.SunWorshippingSite, TILE_DIM, sst), ID.JungleTile, null);
				handler.addKey(IDJungle.SunWorshippingSite + String.valueOf(i), ID.JungleTile, null);
				
				handler.addObject(IDJungle.GoldMinex1 + String.valueOf(i),new JungleTiles(deckLocJungleX, deckLocJungleY, ID.JungleTile, IDJungle.GoldMinex1, TILE_DIM, sst), ID.JungleTile, null);
				handler.addKey(IDJungle.GoldMinex1 + String.valueOf(i), ID.JungleTile, null);
			}
			
		}
		
		max++;
		
		//populating  Water() 
		//tiles with 3 count
		for(int i = 0; i < max; i++)
		{
			handler.addObject(IDJungle.Water + String.valueOf(i),new JungleTiles(deckLocJungleX, deckLocJungleY, ID.JungleTile, IDJungle.Water, TILE_DIM, sst), ID.JungleTile, null);
			handler.addKey(IDJungle.Water + String.valueOf(i), ID.JungleTile, null);
		}
		
		max++;
		
		//populating SellingPricex3
		//tiles with 4 count
		for(int i = 0; i < max; i++)
		{
			//takes out 1 of each jungle tile below if only 2 players (as per the instructions)
			if(numPlayers == 2 && i == (max - 1))
			{
			
			}
			else
			{
				handler.addObject(IDJungle.SellingPricex3 + String.valueOf(i),new JungleTiles(deckLocJungleX, deckLocJungleY, ID.JungleTile, IDJungle.SellingPricex3, TILE_DIM, sst), ID.JungleTile, null);
				handler.addKey(IDJungle.SellingPricex3 + String.valueOf(i), ID.JungleTile, null);
			}
			
			//takes out 1 of each worker tile below if 3 or 4 players (as per the instructions)
			if((numPlayers == 3 || numPlayers == 4) && i == (max - 1))
			{
				
			}
			else 
			{
				for(int k = 1; k <= numPlayers; k++)
				{
					switch(k)
					{
						case 1:
							idplayer = IDPlayer.Player1;
							break;
						case 2:
							idplayer = IDPlayer.Player2;
							break;
						case 3:
							idplayer = IDPlayer.Player3;
							break;
						case 4:
							idplayer = IDPlayer.Player4;
							break;
					}
					
					handler.addObject(IDWorker.OneOneOneOne + String.valueOf(i),new WorkerTiles(deckLocWorkerX, deckLocWorkerY, ID.WorkerTile, IDWorker.OneOneOneOne, TILE_DIM, sswt, idplayer), ID.WorkerTile, idplayer);
					handler.addKey(IDWorker.OneOneOneOne + String.valueOf(i), ID.WorkerTile, idplayer);
				}
			}
		}
		
		max++;
		
		//populating Temple
		//Populating TwoOneZeroOne Worker Tile
		//tiles with 5 count
		for(int i = 0; i < max; i++)
		{
			//takes out 1 of each jungle tile below if only 2 players (as per the instructions)
			if(numPlayers == 2 && i == (max - 1))
			{
				
			}
			else
			{
				handler.addObject(IDJungle.Temple + String.valueOf(i),new JungleTiles(deckLocJungleX, deckLocJungleY, ID.JungleTile, IDJungle.Temple, TILE_DIM, sst), ID.JungleTile, null);
				handler.addKey(IDJungle.Temple + String.valueOf(i), ID.JungleTile, null);
			}
			
			//takes out 1 of each worker tile below if 3 or 4 players (as per the instructions)
			if((numPlayers == 4) && i == (max - 1))
			{
				
			}
			else 
			{
				for(int k = 1; k <= numPlayers; k++)
				{
					switch(k)
					{
						case 1:
							idplayer = IDPlayer.Player1;
							break;
						case 2:
							idplayer = IDPlayer.Player2;
							break;
						case 3:
							idplayer = IDPlayer.Player3;
							break;
						case 4:
							idplayer = IDPlayer.Player4;
							break;
					}
					handler.addObject(IDWorker.TwoOneZeroOne + String.valueOf(i),new WorkerTiles(deckLocWorkerX, deckLocWorkerY, ID.WorkerTile, IDWorker.TwoOneZeroOne, TILE_DIM, sswt, idplayer), ID.WorkerTile, idplayer);
					handler.addKey(IDWorker.TwoOneZeroOne + String.valueOf(i), ID.WorkerTile, idplayer);
				}
			}
		}
		
		max++;
		
		//populating Plantationx1
		//tiles with 6 count
		for(int i = 0; i < max; i++)
		{
			//takes out 2 of 1x plantation jungle tile below if only 2 players (as per the instructions)
			if(numPlayers == 2 && i > (max - 2))
			{
				
			}
			else
			{
				//place the first one in the middle
				if(i == 0)
				{
					//
					handler.addObject(IDJungle.Plantationx1 + String.valueOf(i),new JungleTiles(grid.gridCoord.get("D"), grid.gridCoord.get("4"), ID.JungleTile, IDJungle.Plantationx1, TILE_DIM, sst), ID.JungleTile, null);
					handler.addKey(IDJungle.Plantationx1 + String.valueOf(i), ID.JungleTile, null);
					remove2 = IDJungle.Plantationx1 + String.valueOf(i);
					handler.jungleTileInPlay.put("D4", remove2);//add the key for the removed tile to the tiles in play at the coorect coordinate
				}
				
				else
				{
					handler.addObject(IDJungle.Plantationx1 + String.valueOf(i),new JungleTiles(deckLocJungleX, deckLocJungleY, ID.JungleTile, IDJungle.Plantationx1, TILE_DIM, sst), ID.JungleTile, null);
					handler.addKey(IDJungle.Plantationx1 + String.valueOf(i), ID.JungleTile, null);
				}
			}
		}
	
		//creates an arrayList of the keys for all the jungle tiles in the deck
		handler.cloneKey(handler.hashMapKeysJungle, handler.deckKeysJungle, remove1, remove2);
		
		//creates an arrayList of the keys for all the worker tiles in the deck
		handler.cloneKey(handler.hashMapKeysWorkerP1, handler.deckKeysWorkerP1);
		handler.cloneKey(handler.hashMapKeysWorkerP2, handler.deckKeysWorkerP2);
		handler.cloneKey(handler.hashMapKeysWorkerP3, handler.deckKeysWorkerP3);
		handler.cloneKey(handler.hashMapKeysWorkerP4, handler.deckKeysWorkerP4);
		
		//shuffles the jungle deck
		handler.shuffleDeck(handler.deckKeysJungle);
		
		//shuffles the worker decks for all players
		handler.shuffleDeck(handler.deckKeysWorkerP1);
		handler.shuffleDeck(handler.deckKeysWorkerP2);
		handler.shuffleDeck(handler.deckKeysWorkerP3);
		handler.shuffleDeck(handler.deckKeysWorkerP4);
	
		typeState = TYPESTATE.Jungle;
		gameState = STATE.Play;
		turnState = TURNSTATE.Draw;

		handler.endTurnTrue(this);
		handler.drawJungleTrue(this);
		//handler.drawWorkerTrue(this);

		//draw jungle cards
		handler.drawCard(this);
		
		//handler.endTurnTrue(this);
		//handler.drawJungleTrue(this);
		//handler.drawWorkerTrue(this);
		
		for(int i = 1; i <= numPlayers; i++)
		{
			switch(i)
			{
				case 1:
					gameState = STATE.Player1;
					turnState = TURNSTATE.Draw;
					typeState = TYPESTATE.Worker;
					handler.endTurnTrue(this);
					handler.drawWorkerTrue(this);
					handler.drawCard(this);
					break;
				case 2:
					gameState = STATE.Player2;
					turnState = TURNSTATE.Draw;
					typeState = TYPESTATE.Worker;
					handler.endTurnTrue(this);
					handler.drawWorkerTrue(this);
					handler.drawCard(this);
					break;
				case 3:
					gameState = STATE.Player3;
					turnState = TURNSTATE.Draw;
					typeState = TYPESTATE.Worker;
					handler.endTurnTrue(this);
					handler.drawWorkerTrue(this);
					handler.drawCard(this);
					break;
				case 4:
					gameState = STATE.Player4;
					turnState = TURNSTATE.Draw;
					typeState = TYPESTATE.Worker;
					handler.endTurnTrue(this);
					handler.drawWorkerTrue(this);
					handler.drawCard(this);
					break;
			}
		}
	
		turnState = TURNSTATE.Move;
		typeState = TYPESTATE.Jungle;
		
		//typeState = TYPESTATE.Worker;
		gameState = STATE.Player1;
	}
	
	//clamp method to restrict movement to window of game
	public static int clamp(int var, int min, int max)
	{
		if(var >= max)
		{
			return var = max;
		}
		else if(var <= min)
		{
			return var = min;
		}
		else
		{
			return var;
		}
	}
	
	//initializing method for sprites
	public void init()
	{
		BufferedImageLoader loader = new BufferedImageLoader();
		
		File file = new File(".");
		
		try
		{
			spriteSheetTiles = loader.loadImage("res/Tiles.png");
			spriteSheetRes = loader.loadImage("res/Ressources.png");
			spriteSheetWorkerTiles = loader.loadImage("res/WorkerTiles.png");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		sst = new SpriteSheet(spriteSheetTiles);
		ssr = new SpriteSheet(spriteSheetRes);
		sswt = new SpriteSheet(spriteSheetWorkerTiles);
	}
	
	//synchronized key word is used for threads
	//It means only 1 thread can run the code at a time
	//It prevents code errors when 2 threads try to execute the same code
	public synchronized void start()
	{
		//initialize thread as new thread
		//pass in instance of Game class
		thread = new Thread(this);
		
		//starts thread
		thread.start();
		running = true;
	}
	
	//stops the thread
	public synchronized void stop()
	{
		try
		{
			//stops the thread
			thread.join();
			running = false;
		}
		catch(Exception e)
		{
			//tells us what error occurs
			e.printStackTrace();
		}
	}
	
	//game loop
	public void run()
	{
		//initializing sprites
		init();
		//don't have to click on window to start playing, it selects automatically
		this.requestFocus();
		//record time starting loop in nano seconds (most accurate we can have)
		long lastTime = System.nanoTime();
		//this is saying we want 1 "tick" or frame per second (i.e. 60 ticks or frames in 1 min)
		double amountOfTicks = 60.0;
		//1 nanosecond is 1,000,000,000 of a second. So if we want 60 ticks per second, this is how many nanoseconds we need per tick.
		double ns = 1000000000 / amountOfTicks;
		//initializing value to be used later to calc a delta
		double delta = 0;
		//taking in the current time in miliseconds
		long timer = System.currentTimeMillis();
		//initializing value to be used later to calc the frames per second
		int frames = 0;
		
		while(running)
		{
			//record time when while loop starts
			long now = System.nanoTime();
			//calculates how much time has passed and divides it by the amount of ns required for each frame in a 60 fps game
			delta += (now - lastTime) / ns;
			//records the now value
			lastTime = now;
			
			//when the delta = 1, that means we are due for an update to achieve 60 fps
			while(delta >= 1)
			{
				//run the tick method
				tick();
				//set the delta to 0
				delta--;
			}
			
			if(running)
			{
				//run the render method
				render();
			}
			
			//count the frames
			frames++;
			
			//check if 1 second has passed
			//if yes, output the frames per second
			if(System.currentTimeMillis() - timer > 1000)
			{
				//increment the timer by 1 second
				timer += 1000;
				//System.out.println("FPS: " + frames);
				//set frames back to 0 so we can count for the next second
				frames = 0;
			}
		}
		
		//if we are no longer running, stop the thread using the stop method
		stop();
	}
	
	//this is the code that should run every frame (i.e. 60x per second)
	private void tick()
	{
		if(gameState == STATE.Player1 || gameState == STATE.Player2 || gameState == STATE.Player3 || gameState == STATE.Player4)
		{
			grid.tick(this);
			handler.tick(this, select, grid);
		}

		select.tick();

		turnRotation();
	}
	
	//this code is basically the graphics creator
	private void render()
	{
		//Buffer strategy requires imported library
		//it makes sure we don't miss any buffers
		
		BufferStrategy bs = this.getBufferStrategy();
		
		//checks if we have created a buffer strategy already
		if(bs == null)
		{
			//3 has been determined to be a good value to use
			//3 means the amount of buffers that are created
			//triple buffer strategy allows the next 3 frames loaded into memory, ready to be deployed
			//adding more hogs too many resources
			//having 3 frames rendered allows for smoother game play
			//2 will be flickery
			//4 will be smoother, but may make some computers veery slow
			//triple is typically the best balance
			this.createBufferStrategy(3);
			return;
		}
		
		//Buffer Strategy is a way to organize memory
		//Graphics is how we create graphics
		//requires importing graphics lib
		
		//create graphics class instance g
		//use buffer strategy class to call draw graphics method
		//pass in the next render in the buffer into the graphics class instance g
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		if((gameState == STATE.Player1 || gameState == STATE.Player2 || gameState == STATE.Player3 || gameState == STATE.Player4) && gameState != STATE.End)
		{
			grid.render(g, this);
		}
		else if(gameState == STATE.Select)
		{
			select.render(g);
		}
		else if(gameState == STATE.End)
		{
			end.render(g, this, handler);
		}
		
		try
		{
			if(gameState != STATE.End)
			{
				handler.render(g, this);
			}
		}
		catch(Exception e)
		{
			
		}
		
		if(gameState == STATE.Player1 || gameState == STATE.Player2 || gameState == STATE.Player3 || gameState == STATE.Player4)
		{
			hud.render(g, this, handler);
		}
		
		//disposes of previously rendered graphics no longer needed
		//i.e. garbage collector
		g.dispose();
		//shows what we have rendered and stored in the buffer
		bs.show();
		
	}
	
	public int getNumPlayers()
	{
		return numPlayers;
	}

	public static void main(String[] args)
	{
		//create new instance of game class
		//calls our constructor method
		new Game();
	}

}
