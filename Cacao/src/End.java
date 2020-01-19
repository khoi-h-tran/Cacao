import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map.Entry;

/* 
	File Name: Menu.java
	   Author: Khoi Tran
		 Date: Jan. 1, 2020 12:23:01 p.m.
  Description: Handles the select screen and any mouse choices
*/

//extend mouse adapter to this class can react to mouse clicks
public class End 
{
	private int offSetX = 10;//offsets between each line of text
	private int offSetY = 40;// offsets between each line of text
	private int offSetPlayerX = 100;//difference between each player in the X
	private int offSetPlayerY = 200;//difference between each player in the y
	private int offSetPlayer12 = 200;//shifts the player 1 and 2 boxes left, and 3 and 4 players right
	private int offSetPlayer34 = 200;
	private int topMargin = 300;//offsets from the top of the page
	private int offSetText = 200;//offset between the text  like "gold" and the value of how much gold the player has
	int [] templeRanks = {0,0,0,0,0}; //stores which player came in first or second to calc the temples given, // index 1 = plyaer1, index 2 = player 2, etc.
	//int [] templeScores = {0,0,0,0};//stores the players scores
	
	//Hashmap to store the temple scores and help sort them
	//first ingeter is player id (e.g. 1 for player 1), the second is the temple score
	HashMap <Integer, Integer> templeSort= new HashMap <Integer, Integer>();
	
	public void tick()
	{

	}
	
	public void render(Graphics g, Game game, Handler handler)
	{
		//setting font
		Font f1 = new Font(Font.SERIF, Font.PLAIN, 50);
		Font f2 = new Font(Font.SERIF, Font.PLAIN, 30);
		g.setFont(f1);
		
		//writing instructions to pick amount of players
		g.setColor(Color.black);
		g.drawString("Game Over", Game.HEIGHT/2 + 50, 200);
		
		//check who won the temple
		templeSort.put(1, handler.scoreCountP1.get("Temple"));
		if(game.numPlayers >= 2)
		{
			templeSort.put(2, handler.scoreCountP2.get("Temple"));
		}
		if(game.numPlayers >= 3)
		{
			templeSort.put(3, handler.scoreCountP3.get("Temple"));
		}
		if(game.numPlayers >= 4)
		{
			templeSort.put(4, handler.scoreCountP4.get("Temple"));
		}
		
		//sort through to find max value
		int maxEntryKey = 0;
		int maxEntryValue = 0;
		
		//finding the largest temple value by sorting through array and storing the max value
		for (Entry<Integer, Integer> entry : templeSort.entrySet())
		{
		    if (maxEntryKey == 0)
		    {
		        maxEntryKey = entry.getKey();
		        maxEntryValue = entry.getValue();
		    }
		    if(maxEntryKey != 0)
		    {
		    	if(maxEntryValue < entry.getValue())
		    	{
		        maxEntryKey = entry.getKey();
		        maxEntryValue = entry.getValue();
		    	}
		    }
		}
		
		//setting the ranking of that player's temple value to 1
		templeRanks[maxEntryKey] = 1;
		
		//finding if there are any ties for largest value
		for (Entry<Integer, Integer> entry : templeSort.entrySet())
		{
				if(entry.getKey() != maxEntryKey)
				{
					if(entry.getValue() == maxEntryValue)
					{
						templeRanks[entry.getKey()] = 1;
					}
				}
		}
		
		//sort through to find second highest value
		int secondEntryKey = 0;
		int secondEntryValue = 0;
		
		//finding the largest temple value by sorting through array and storing the max value
		for (Entry<Integer, Integer> entry : templeSort.entrySet())
		{
		    if (secondEntryKey == 0 && entry.getValue() != maxEntryValue)
		    {
		    	secondEntryKey = entry.getKey();
		    	secondEntryValue = entry.getValue();
		    }
		    if(secondEntryKey != 0 && entry.getValue() != maxEntryValue)
		    {
		    	if(secondEntryValue < entry.getValue())
		    	{
		        secondEntryKey = entry.getKey();
		        secondEntryValue = entry.getValue();
		    	}
		    }
		}
		
		//setting the ranking of that player's temple value to 2
		templeRanks[secondEntryKey] = 2;
		
		//finding if there are any ties for second largest value
		for (Entry<Integer, Integer> entry : templeSort.entrySet())
		{
				if(entry.getKey() != secondEntryKey && entry.getValue() != maxEntryValue)
				{
					if(entry.getValue() == secondEntryValue && entry.getValue() != maxEntryValue)
					{
						templeRanks[entry.getKey()] = 2;
					}
				}
		}
		
		//Drawing the Player info section
		for(int i = 0; i < game.getNumPlayers(); i++)
		{
			//drawing the rectangle for each player
			if(i == 0 || i == 1)
			{
				g.setColor(Color.black);
				g.drawRect(Game.HEIGHT/2 - game.TITLE_BAR - offSetPlayer12, game.TITLE_BAR*7*i  + topMargin, Game.WIDTH - Game.HEIGHT, game.TITLE_BAR*7);
			}
			else
			{
				int j = i - 2;
				g.setColor(Color.black);
				g.drawRect(Game.HEIGHT/2 - game.TITLE_BAR + offSetPlayer34, game.TITLE_BAR*7*j  + topMargin, Game.WIDTH - Game.HEIGHT, game.TITLE_BAR*7);
			}
			
			g.setFont(f2);
			//Writing out the player number
			
			g.setColor(Color.black);
			if(i == 0 || i == 1)
			{
				g.drawString("Player " + (i+1) + ":", (Game.HEIGHT/2) - offSetPlayer12, (game.TITLE_BAR)*(i*2 + 1) - 10 + offSetPlayerY*i + topMargin);
			}
			else
			{
				int j = i - 2;
				g.drawString("Player " + (i+1) + ":", (Game.HEIGHT/2) + offSetPlayer34, (game.TITLE_BAR)*(j*2 + 1) - 10 + offSetPlayerY*j + topMargin);
			}
			
			//Writing out the score count
			
			if(i == 0)
			{
				int templeScore = 0;
				//Gold Score
				g.drawString("Gold:", Game.HEIGHT/2 - offSetPlayer12, game.TITLE_BAR*(i*2 + 1) - 10 + offSetY + topMargin);
				g.drawString(String.valueOf(handler.scoreCountP1.get("Gold")), Game.HEIGHT/2 - offSetPlayer12 + offSetText, game.TITLE_BAR*(i*2 + 1) - 10 + offSetY + topMargin);
				
				//Cacao count
				g.drawString("Cacao:", Game.HEIGHT/2 - offSetPlayer12, game.TITLE_BAR*(i*2 + 1) - 10 + offSetY*2 + topMargin);
				g.drawString(String.valueOf(handler.scoreCountP1.get("Cacao")), Game.HEIGHT/2 - offSetPlayer12 + offSetText, game.TITLE_BAR*(i*2 + 1) - 10 + offSetY*2 + topMargin);
				//Sun worshiping token count
				g.drawString("Sun Tokens:", Game.HEIGHT/2 - offSetPlayer12, game.TITLE_BAR*(i*2 + 1) - 10 + offSetY*3 + topMargin);
				g.drawString(String.valueOf(handler.scoreCountP1.get("Sun Tokens")), Game.HEIGHT/2 - offSetPlayer12 + offSetText, game.TITLE_BAR*(i*2 + 1) - 10 + offSetY*3 + topMargin);
				//Water count
				g.drawString("Water:", Game.HEIGHT/2 - offSetPlayer12, game.TITLE_BAR*(i*2 + 1) - 10 + offSetY*4 + topMargin);
				g.drawString(String.valueOf(handler.scoreCountP1.get("Water")), Game.HEIGHT/2 - offSetPlayer12 + offSetText, game.TITLE_BAR*(i*2 + 1) - 10 + offSetY*4 + topMargin);
				//Temple Count
				g.drawString("Temple:", Game.HEIGHT/2 - offSetPlayer12, (game.TITLE_BAR)*(i*2 + 1) - 10 + offSetY*5 + offSetPlayerY*i + topMargin);
				g.drawString(String.valueOf(handler.scoreCountP1.get("Temple")), Game.HEIGHT/2 - offSetPlayer12 + offSetText, (game.TITLE_BAR)*(i*2 + 1) - 10 + offSetY*5 + offSetPlayerY*i + topMargin);
				g.drawString(" + ", Game.HEIGHT/2 - offSetPlayer12  + offSetText + 30, (game.TITLE_BAR)*(i*2 + 1) - 10 + offSetY*5 + offSetPlayerY*i + topMargin);
				if(templeRanks[i+1] == 1)
				{
					templeScore = 30;
					g.drawString(String.valueOf(templeScore), Game.HEIGHT/2 - offSetPlayer12  + offSetText + 30*2, (game.TITLE_BAR)*(i*2 + 1) - 10 + offSetY*5 + offSetPlayerY*i + topMargin);
				}
				else if(templeRanks[i+1] == 2)
				{
					templeScore = 15;
					g.drawString(String.valueOf(templeScore), Game.HEIGHT/2 - offSetPlayer12  + offSetText + 30*2, (game.TITLE_BAR)*(i*2 + 1) - 10 + offSetY*5 + offSetPlayerY*i + topMargin);
				}
				//output the total score
				g.drawString("Total:", Game.HEIGHT/2 - offSetPlayer12, (game.TITLE_BAR)*(i*2 + 1) - 10 + offSetY*6 + offSetPlayerY*i + topMargin);
				g.drawString(String.valueOf(handler.scoreCountP1.get("Gold") + handler.scoreCountP1.get("Sun Tokens") + handler.scoreCountP1.get("Water") + templeScore), Game.HEIGHT/2 - offSetPlayer12 + offSetText, (game.TITLE_BAR)*(i*2 + 1) - 10 + offSetY*6 + offSetPlayerY*i + topMargin);
			}

			else if(i == 1)
			{
				int templeScore = 0;
				//Gold Score
				g.drawString("Gold:", (Game.HEIGHT/2) - offSetPlayer12, (game.TITLE_BAR)*(i*2 + 1) - 10 + offSetY + offSetPlayerY*i + topMargin);
				g.drawString(String.valueOf(handler.scoreCountP2.get("Gold")), (Game.HEIGHT/2) - offSetPlayer12 + offSetText, (game.TITLE_BAR)*(i*2 + 1) - 10 + offSetY + offSetPlayerY*i + topMargin);
				
				//Cacao count
				g.drawString("Cacao:", Game.HEIGHT/2 - offSetPlayer12, (game.TITLE_BAR)*(i*2 + 1) - 10 + offSetY*2 + offSetPlayerY*i + topMargin);
				g.drawString(String.valueOf(handler.scoreCountP2.get("Cacao")), Game.HEIGHT/2 - offSetPlayer12 + offSetText, (game.TITLE_BAR)*(i*2 + 1) - 10 + offSetY*2 + offSetPlayerY*i + topMargin);
				//Sun worshiping token count
				g.drawString("Sun Tokens:", Game.HEIGHT/2 - offSetPlayer12, (game.TITLE_BAR)*(i*2 + 1) - 10 + offSetY*3 + offSetPlayerY*i + topMargin);
				g.drawString(String.valueOf(handler.scoreCountP2.get("Sun Tokens")), Game.HEIGHT/2 - offSetPlayer12 + offSetText, (game.TITLE_BAR)*(i*2 + 1) - 10 + offSetY*3 + offSetPlayerY*i + topMargin);
				//Water count
				g.drawString("Water:", Game.HEIGHT/2 - offSetPlayer12, (game.TITLE_BAR)*(i*2 + 1) - 10 + offSetY*4 + offSetPlayerY*i + topMargin);
				g.drawString(String.valueOf(handler.scoreCountP2.get("Water")), Game.HEIGHT/2 - offSetPlayer12 + offSetText, (game.TITLE_BAR)*(i*2 + 1) - 10 + offSetY*4 + offSetPlayerY*i + topMargin);
				//Temple Count
				g.drawString("Temple:", Game.HEIGHT/2 - offSetPlayer12, (game.TITLE_BAR)*(i*2 + 1) - 10 + offSetY*5 + offSetPlayerY*i + topMargin);
				g.drawString(String.valueOf(handler.scoreCountP2.get("Temple")), Game.HEIGHT/2 - offSetPlayer12 + offSetText, (game.TITLE_BAR)*(i*2 + 1) - 10 + offSetY*5 + offSetPlayerY*i + topMargin);
				g.drawString(" + ", Game.HEIGHT/2 - offSetPlayer12  + offSetText + 30, (game.TITLE_BAR)*(i*2 + 1) - 10 + offSetY*5 + offSetPlayerY*i + topMargin);
				if(templeRanks[i+1] == 1)
				{
					templeScore = 30;
					g.drawString(String.valueOf(templeScore), Game.HEIGHT/2 - offSetPlayer12  + offSetText + 30*2, (game.TITLE_BAR)*(i*2 + 1) - 10 + offSetY*5 + offSetPlayerY*i + topMargin);
				}
				else if(templeRanks[i+1] == 2)
				{
					templeScore = 15;
					g.drawString(String.valueOf(templeScore), Game.HEIGHT/2 - offSetPlayer12  + offSetText + 30*2, (game.TITLE_BAR)*(i*2 + 1) - 10 + offSetY*5 + offSetPlayerY*i + topMargin);
				}
				//Total Score
				g.drawString("Total:", Game.HEIGHT/2 - offSetPlayer12, (game.TITLE_BAR)*(i*2 + 1) - 10 + offSetY*6 + offSetPlayerY*i + topMargin);
				g.drawString(String.valueOf(handler.scoreCountP2.get("Gold") + handler.scoreCountP2.get("Sun Tokens") + handler.scoreCountP2.get("Water") + templeScore), Game.HEIGHT/2 - offSetPlayer12 + offSetText, (game.TITLE_BAR)*(i*2 + 1) - 10 + offSetY*6 + offSetPlayerY*i + topMargin);
			}
			
			else if(i == 2)
			{
				int templeScore = 0;
				int j = 0;
				//Gold Score
				g.drawString("Gold:", Game.HEIGHT/2 + offSetPlayer34, game.TITLE_BAR*(j*2 + 1) - 10 + offSetY + topMargin);
				g.drawString(String.valueOf(handler.scoreCountP3.get("Gold")), Game.HEIGHT/2 + offSetPlayer34 + offSetText, game.TITLE_BAR*(j*2 + 1) - 10 + offSetY + topMargin);
				
				//Cacao count
				g.drawString("Cacao:", Game.HEIGHT/2 + offSetPlayer34, (game.TITLE_BAR)*(j*2 + 1) - 10 + offSetY*2 + offSetPlayerY*j + topMargin);
				g.drawString(String.valueOf(handler.scoreCountP3.get("Cacao")), Game.HEIGHT/2 + offSetPlayer34 + offSetText, (game.TITLE_BAR)*(j*2 + 1) - 10 + offSetY*2 + offSetPlayerY*j + topMargin);
				//Sun worshiping token count
				g.drawString("Sun Tokens:", Game.HEIGHT/2 + offSetPlayer34, (game.TITLE_BAR)*(j*2 + 1) - 10 + offSetY*3 + offSetPlayerY*j + topMargin);
				g.drawString(String.valueOf(handler.scoreCountP3.get("Sun Tokens")), Game.HEIGHT/2 + offSetPlayer34 + offSetText, (game.TITLE_BAR)*(j*2 + 1) - 10 + offSetY*3 + offSetPlayerY*j + topMargin);
				//Water count
				g.drawString("Water:", Game.HEIGHT/2 + offSetPlayer34, (game.TITLE_BAR)*(j*2 + 1) - 10 + offSetY*4 + offSetPlayerY*j + topMargin);
				g.drawString(String.valueOf(handler.scoreCountP3.get("Water")), Game.HEIGHT/2 + offSetPlayer34 + offSetText, (game.TITLE_BAR)*(j*2 + 1) - 10 + offSetY*4 + offSetPlayerY*j + topMargin);
				//Temple Count
				g.drawString("Temple:", Game.HEIGHT/2 + offSetPlayer34, (game.TITLE_BAR)*(j*2 + 1) - 10 + offSetY*5 + offSetPlayerY*j + topMargin);
				g.drawString(String.valueOf(handler.scoreCountP3.get("Temple")), Game.HEIGHT/2 + offSetPlayer34 + offSetText, (game.TITLE_BAR)*(j*2 + 1) - 10 + offSetY*5 + offSetPlayerY*j + topMargin);
				g.drawString(" + ", Game.HEIGHT/2 + offSetPlayer34  + offSetText + 30, (game.TITLE_BAR)*(j*2 + 1) - 10 + offSetY*5 + offSetPlayerY*j + topMargin);
				if(templeRanks[i+1] == 1)
				{
					templeScore = 30;
					g.drawString(String.valueOf(templeScore), Game.HEIGHT/2 + offSetPlayer34  + offSetText + 30*2, (game.TITLE_BAR)*(j*2 + 1) - 10 + offSetY*5 + offSetPlayerY*j + topMargin);
				}
				else if(templeRanks[i+1] == 2)
				{
					templeScore = 15;
					g.drawString(String.valueOf(templeScore), Game.HEIGHT/2 + offSetPlayer34  + offSetText + 30*2, (game.TITLE_BAR)*(j*2 + 1) - 10 + offSetY*5 + offSetPlayerY*j + topMargin);
				}
				//Total Score
				g.drawString("Total:", Game.HEIGHT/2 + offSetPlayer34, (game.TITLE_BAR)*(j*2 + 1) - 10 + offSetY*6 + offSetPlayerY*j + topMargin);
				g.drawString(String.valueOf(handler.scoreCountP3.get("Gold") + handler.scoreCountP3.get("Sun Tokens") + handler.scoreCountP3.get("Water") + templeScore), Game.HEIGHT/2 + offSetPlayer34 + offSetText, (game.TITLE_BAR)*(j*2 + 1) - 10 + offSetY*6 + offSetPlayerY*j + topMargin);
			}
			
			else if(i == 3)
			{
				int templeScore = 0;
				int j = 1;
				//Gold Score
				g.drawString("Gold:", Game.HEIGHT/2 + offSetPlayer34, game.TITLE_BAR*(j*2 + 1) - 10 + offSetY + offSetPlayerY*j + topMargin);
				g.drawString(String.valueOf(handler.scoreCountP4.get("Gold")), Game.HEIGHT/2 + offSetPlayer34 + offSetText, game.TITLE_BAR*(j*2 + 1) - 10 + offSetY + offSetPlayerY*j + topMargin);
				
				//Cacao count
				g.drawString("Cacao:", Game.HEIGHT/2 + offSetPlayer34, (game.TITLE_BAR)*(j*2 + 1) - 10 + offSetY*2 + offSetPlayerY*j + topMargin);
				g.drawString(String.valueOf(handler.scoreCountP4.get("Cacao")), Game.HEIGHT/2 + offSetPlayer34 + offSetText, (game.TITLE_BAR)*(j*2 + 1) - 10 + offSetY*2 + offSetPlayerY*j + topMargin);
				//Sun worshiping token count
				g.drawString("Sun Tokens:", Game.HEIGHT/2 + offSetPlayer34, (game.TITLE_BAR)*(j*2 + 1) - 10 + offSetY*3 + offSetPlayerY*j + topMargin);
				g.drawString(String.valueOf(handler.scoreCountP4.get("Sun Tokens")), Game.HEIGHT/2 + offSetPlayer34 + offSetText, (game.TITLE_BAR)*(j*2 + 1) - 10 + offSetY*3 + offSetPlayerY*j + topMargin);
				//Water count
				g.drawString("Water:", Game.HEIGHT/2 + offSetPlayer34, (game.TITLE_BAR)*(j*2 + 1) - 10 + offSetY*4 + offSetPlayerY*j + topMargin);
				g.drawString(String.valueOf(handler.scoreCountP4.get("Water")), Game.HEIGHT/2 + offSetPlayer34 + offSetText, (game.TITLE_BAR)*(j*2 + 1) - 10 + offSetY*4 + offSetPlayerY*j + topMargin);
				//Temple Count
				g.drawString("Temple:", Game.HEIGHT/2 + offSetPlayer34, (game.TITLE_BAR)*(j*2 + 1) - 10 + offSetY*5 + offSetPlayerY*j + topMargin);
				g.drawString(String.valueOf(handler.scoreCountP4.get("Temple")), Game.HEIGHT/2 + offSetPlayer34 + offSetText, (game.TITLE_BAR)*(j*2 + 1) - 10 + offSetY*5 + offSetPlayerY*j + topMargin);
				g.drawString(" + ", Game.HEIGHT/2 + offSetPlayer34  + offSetText + 30, (game.TITLE_BAR)*(j*2 + 1) - 10 + offSetY*5 + offSetPlayerY*j + topMargin);
				if(templeRanks[i+1] == 1)
				{
					templeScore = 30;
					g.drawString(String.valueOf(templeScore), Game.HEIGHT/2 + offSetPlayer34  + offSetText + 30*2, (game.TITLE_BAR)*(j*2 + 1) - 10 + offSetY*5 + offSetPlayerY*j + topMargin);
				}
				else if(templeRanks[i+1] == 2)
				{
					templeScore = 15;
					g.drawString(String.valueOf(templeScore), Game.HEIGHT/2 + offSetPlayer34  + offSetText + 30*2, (game.TITLE_BAR)*(j*2 + 1) - 10 + offSetY*5 + offSetPlayerY*j + topMargin);
				}
				//Temple Count
				g.drawString("Total:", Game.HEIGHT/2 + offSetPlayer34, (game.TITLE_BAR)*(j*2 + 1) - 10 + offSetY*6 + offSetPlayerY*j + topMargin);
				g.drawString(String.valueOf(handler.scoreCountP4.get("Gold") + handler.scoreCountP4.get("Sun Tokens") + handler.scoreCountP4.get("Water") + templeScore), Game.HEIGHT/2 + offSetPlayer34 + offSetText, (game.TITLE_BAR)*(j*2 + 1) - 10 + offSetY*6 + offSetPlayerY*j + topMargin);
			}
			
			
		}
	}

}
