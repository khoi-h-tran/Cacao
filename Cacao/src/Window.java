import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

/* 
	File Name: Window.java
	   Author: Khoi Tran
		 Date: Dec. 26, 2019 9:35:54 p.m.
  Description: Generates GUI
*/

//extends Canvas allows us to have a GUI
//requires importing Canvas library

public class Window extends Canvas
{
	
	//Serial versions are to ensure that data sent is the correct version
	//Eclipse auto generates this for any class that extends canvas (cntrl +shift+o)
	private static final long serialVersionUID = 1L;
	
	/*
	 * Method Name: Window
	 *     Purpose: Constructor for Window class
	 *     Accepts: int, string, instance of game class (basically a copy of the game class)
	 *     Returns: void
	 */
	public Window(int width, int height, String title, Game game)
	{
		//Instantiate object for our window
		//Jframe is the frame of our window for our game
		//the title input will be at the top of our window
		JFrame frame = new JFrame(title);
		
		//Note: frame. refers to the JFrame
		
		//setting the size of the window 
		//Dimension requires importing library (cntrl+shift+o)
		frame.setPreferredSize(new Dimension(width, height));
		
		//Don't allow resizing of window (it makes the programming more complicated)
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		
		//Allows the game to close
		//i.e. gives "x" button functionality
		//x button stops threads in game
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Don't allow resizing of window
		//Makes GUI more complicated if true
		frame.setResizable(false);
		//null will set it in the center of the screen
		//without null it starts in top left
		frame.setLocationRelativeTo(null);
		//add the game loop code to the frame
		frame.add(game);
		//setVisible to true so we can see the window
		frame.setVisible(true);
		//creates a thread for the game
		game.start();
	}
}
