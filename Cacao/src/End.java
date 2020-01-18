import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/* 
	File Name: Menu.java
	   Author: Khoi Tran
		 Date: Jan. 1, 2020 12:23:01 p.m.
  Description: Handles the select screen and any mouse choices
*/

//extend mouse adapter to this class can react to mouse clicks
public class End 
{
	
	public void tick()
	{

	}
	
	public void render(Graphics g)
	{
		//setting font
		Font f1 = new Font(Font.SERIF, Font.PLAIN, 50);
		g.setFont(f1);
		
		//writing instructions to pick amount of players
		g.setColor(Color.black);
		g.drawString("Game Over", Game.HEIGHT/(3) + 100, Game.HEIGHT/3);
		
	}

}
