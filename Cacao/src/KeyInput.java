import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/* 
	File Name: KeyInput.java
	   Author: Khoi Tran
		 Date: Jan. 1, 2020 11:57:47 a.m.
  Description: This class monitors and acts on keyboard inputs
*/

//import the key adapter class to extend
public class KeyInput extends KeyAdapter
{
	private Handler handler;
	
	public KeyInput(Handler handler)
	{
		this.handler = handler;
	}
	
	//monitors if key is pressed
	//from the KeyAdapter class we extended
	public void keyPressed(KeyEvent e)
	{
		//variable to hold what key we pressed
		int key = e.getKeyCode();
	}
	
	//monitors if key is released
	//from the KeyAdapter class we extended
	public void keyReleased(KeyEvent e)
	{
		
	}
}
