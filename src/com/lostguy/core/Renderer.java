package com.lostguy.core;

import java.awt.image.DataBufferInt;

import com.lostguy.core.graphics.Font;
import com.lostguy.core.graphics.Pixel;
import com.lostguy.core.graphics.Image;

public class Renderer
{
	private int width, height;
	private int[] pixels;
	
	private Font font = Font.STANDARD;
	
	public Renderer(GameContainer gameContainer)
	{
		width = gameContainer.getWidth();
		height = gameContainer.getHeight();
		
		pixels = ((DataBufferInt)gameContainer.getWindow().getImage().getRaster().getDataBuffer()).getData();
	}
	
	/**
	 * Set the pixel color
	 * @param x - x pos
	 * @param y - y pos
	 * @param a - alpha
	 * @param r - red
	 * @param g - green
	 * @param b = blue
	 */
	public void setPixel(int x, int y, int color)
	{
		if((x < 0 || x > width || y < 0 || y > height) || color == 0xffff00ff)
		{
			return;
		}
		
		//Math to get the 2 dimensional location of the pixel from the 1 dimensional array
		int index = (x + y * width);
		
		//Force the cast to round for more accurate colors
		pixels[index] = color;
	}
	
	/**
	 * Clear the screen
	 */
	public void clear()
	{
		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < height; j++)
			{
				setPixel(i, j, 0xff000000);
			}
		}
	}
	
	/**
	 * Draw the image
	 * @param image - image to draw
	 * @param offX - offset x
	 * @param offY - offset y
	 */
	public void drawImage(Image image, int offX, int offY)
	{
		for(int x = 0; x < image.width; x++)
		{
			for(int y = 0; y < image.height; y++)
			{
				setPixel(x + offX, y + offY, image.pixels[x + y * image.width]);
			}
		}
	}
	
	/**
	 * Draws a string
	 * @param text - text to draw
	 * @param color - color of the text
	 * @param offX - offset x
	 * @param offY - offset y
	 */
	public void drawString(String text, int color, int offX, int offY)
	{
		//Upper case the text since currently the image only has uppercase letters
		text = text.toUpperCase();
		
		//The offset to move after each letter
		int offset = 0;
		
		//Loop through the string
		for(int i = 0; i < text.length(); i++)
		{
			//Get the unicode
			int unicode = text.codePointAt(i) - 32;
			
			for(int x = 0; x < font.widths[unicode]; x++)
			{
				for(int y = 1; y < font.image.height; y++)
				{
					//Color the pixel in the right location
					if(font.image.pixels[(x + font.offsets[unicode]) + y * font.image.width] == 0xffffffff)
					{
						setPixel(x + offX + offset - 1, y + offY - 1, color);
					}
				}
			}
			
			//Increase the offset for the next letter
			offset += font.widths[unicode];
		}
	}
}
