import java.awt.image.BufferedImage;

/* 
	File Name: SpriteSheet.java
	   Author: Khoi Tran
		 Date: Dec. 28, 2019 11:45:05 a.m.
  Description: This class pulls in our sprite sheet
*/
public class SpriteSheet
{
	//import buffered image library
	//create instance of class
	private BufferedImage image;
	
	//constructor class
	public SpriteSheet(BufferedImage image)
	{
		this.image = image;
	}
	
	//pulls the correct sprite from spire sheet
	public BufferedImage grabImage(int col, int row, int tileDim, int width, int height)
	{
		BufferedImage img = image.getSubimage(col * tileDim - tileDim, row * tileDim - tileDim, width, height);
		return img;
	}
}
