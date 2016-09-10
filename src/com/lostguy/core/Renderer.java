package com.lostguy.core;

import java.awt.image.DataBufferInt;
import java.util.ArrayList;

import com.lostguy.core.fx.Font;
import com.lostguy.core.fx.Image;
import com.lostguy.core.fx.ImageTile;
import com.lostguy.core.fx.Light;
import com.lostguy.core.fx.LightRequest;
import com.lostguy.core.fx.Pixel;
import com.lostguy.core.fx.ShadowType;

public class Renderer
{
	private GameContainer gc;
	
	private int width, height;
	private int[] pixels;
	
	//For lighting
	private int[] lightMap;
	private ShadowType[] lightBlock;
	
	private Font font = Font.STANDARD;
	
	private int ambientColor = Pixel.getColor(1, 0.1f, 0.1f, 0.1f);
	
	private ArrayList<LightRequest> lightRequests = new ArrayList<LightRequest>();
	
	private int clearColor = 0xff000000;
	
	//Translates everything on screen by these coordinates
	private int transX, transY;
	
	public Renderer(GameContainer gameContainer)
	{
		this.gc = gameContainer;
		
		width = gameContainer.getWidth();
		height = gameContainer.getHeight();
		
		pixels = ((DataBufferInt)gameContainer.getWindow().getImage().getRaster().getDataBuffer()).getData();
		
		lightMap = new int[pixels.length];
		lightBlock = new ShadowType[pixels.length];
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
	public void setPixel(int x, int y, int color, ShadowType lightBlock)
	{
		x -= transX;
		y -= transY;
		
		if((x < 0 || x >= width || y < 0 || y >= height) || color == 0xffff00ff)
		{
			return;
		}
		
		//Math to get the 2 dimensional location of the pixel from the 1 dimensional array
		int index = (x + y * width);
		
		pixels[index] = color;
		this.lightBlock[index] = lightBlock;
	}
	
	public ShadowType getLightBlock(int x, int y)
	{
		x -= transX;
		y -= transY;
		
		//Test for out of bounds
		if(x < 0 || x >= width || y < 0 || y >= height)
		{
			return ShadowType.TOTAL;
		}
		
		return lightBlock[x + y * width];
	}
	
	public void setLightMap(int x, int y, int color)
	{
		x -= transX;
		y -= transY;
		
		if(x < 0 || x >= width || y < 0 || y >= height)
		{
			return;
		}
		
		//Math to get the 2 dimensional location of the pixel from the 1 dimensional array
		int index = (x + y * width);
		
		lightMap[index] = Pixel.getMax(color, lightMap[index]);
	}
	
	/**
	 * Combines the color maps
	 */
	public void flushMaps()
	{
		for(int x = 0; x < width; x++)
		{
			for(int y = 0; y < height; y++)
			{
				int index = x + y * width;
					
				setPixel(x, y, Pixel.getLightBlend(pixels[index], lightMap[index], ambientColor), lightBlock[index]);
					
				lightMap[index] = ambientColor;
					
					
			}
		}
	}
	
	public void clear()
	{
		for(int x = 0; x < width; x++)
		{
			for(int y = 0; y < height; y++)
			{
				int index = x + y * width;
				
				if(gc.isClearScreen())
				{
					pixels[index] = clearColor;
				}
			}	
		}
	}
	
	public void drawLightArray()
	{
		for(LightRequest lightRequest : lightRequests)
		{
			drawLightRequest(lightRequest.light, lightRequest.x, lightRequest.y);
		}
		
		lightRequests.clear();
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
				int index = x + y * image.width;
				setPixel(x + offX, y + offY, image.pixels[index], image.shadowType);
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
						setPixel(x + offX + offset - 1, y + offY - 1, color, ShadowType.NONE);
					}
				}
			}
			
			//Increase the offset for the next letter
			offset += font.widths[unicode];
		}
	}
	
	/**
	 * Draws the tile
	 * @param image - image to draw
	 * @param offX - offset x
	 * @param offY - offset y
	 * @param tileX - x pos
	 * @param tileY - y pos
	 */
	public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY)
	{
		for(int x = 0; x < image.tileWidth; x++)
		{
			for(int y = 0; y < image.tileHeight; y++)
			{
				setPixel(x + offX, y + offY, image.pixels[(x + (tileX * image.tileWidth)) 
				                                          + (y + (tileY * image.tileHeight)) * image.width],
				                                          image.shadowType);
			}
		}
	}
	
	public void drawLight(Light light, int offX, int offY)
	{
		if(gc.drawLights() || gc.isLightingEnabled())
		{
			lightRequests.add(new LightRequest(light, offX, offY));
		}
		else
		{
			for(int x = 0; x < light.diameter; x++)
			{
				for(int y = 0; y < light.diameter; y++)
				{
					setLightMap(x + offX, y + offY, light.getLightValue(x, y));
				}
			}
		}
	}
	
	/**
	 * Draws light
	 * @param light
	 * @param offX
	 * @param offY
	 */
	private void drawLightRequest(Light light, int offX, int offY)
	{
		for(int i = 0; i <= light.diameter; i++)
		{
			//Scan across top edge
			drawLightLine(light.radius, light.radius, i, 0, light, offX, offY);
			
			//Scan across the bottom
			drawLightLine(light.radius, light.radius, i, light.diameter, light, offX, offY);
			
			//Scan across left edge
			drawLightLine(light.radius, light.radius, 0, i, light, offX, offY);
			
			//Scan across right edge
			drawLightLine(light.radius, light.radius, light.diameter, i, light, offX, offY);
		}
	}
	
	/**
	 * Draws a light line
	 * @param x0 - x start
	 * @param y0 - y start
	 * @param x1 - x end
	 * @param y1 - y end
	 * @param light - Light
	 * @param offX - offset x
	 * @param offY - offset y
	 */
	private void drawLightLine(int x0, int y0, int x1, int y1, Light light, int offX, int offY)
	{
		//Difference between x's and y's
		int dx = Math.abs(x1 - x0);
		int dy = Math.abs(y1 - y0);
		
		int sx = x0 < x1 ? 1 : -1;
		int sy = y0 < y1 ? 1 : -1;
		
		int err = dx - dy;
		int err2;
		
		float power = 1.0f;
		
		boolean hit = false;
		
		while(true)
		{
			//Break if the light value is black
			if(light.getLightValue(x0, y0) == 0xff000000)
			{
				break;
			}
			
			
			int screenX = x0 - light.radius + offX;
			int screenY = y0 - light.radius + offY;
			
			if(power == 1)
			{
				setLightMap(screenX, screenY, light.getLightValue(x0, y0));
			}
			else
			{
				setLightMap(screenX, screenY, Pixel.getColorPower(light.getLightValue(x0, y0), power));
			}
			
			if(x0 == x1 && y0 == y1)
			{
				break;
			}
			
			if(getLightBlock(screenX, screenY) == ShadowType.TOTAL)
			{
				break;
			}
			
			if(getLightBlock(screenX, screenY) == ShadowType.FADE)
			{
				power -= 0.1f;
			}
			
			if(getLightBlock(screenX, screenY) == ShadowType.HALF && !hit)
			{
				power /= 2;
				hit = true;
			}
			
			if(getLightBlock(screenX, screenY) == ShadowType.NONE && hit)
			{
				hit = false;
			}
			
			if(power <= 0.1f)
			{
				break;
			}
			
			err2 = 2 * err;
			
			//Move on x-axis
			if(err2 > -1 * dy)
			{
				err -= dy;
				x0 += sx;
			}
			
			//Move on y-axis
			if(err2 < dx)
			{
				err += dx;
				y0 += sy;
			}
		}
	}
	
	public void drawRect(int offX, int offY, int width, int height, int color, ShadowType sType)
	{
		for(int x = 0; x < width; x++)
		{
			for(int y = 0; y < height; y++)
			{
				setPixel(x + offX, y + offY, color, sType);
			}
		}
	}

	public int getAmbientColor() {
		return ambientColor;
	}

	public void setAmbientColor(int ambientColor) {
		this.ambientColor = ambientColor;
	}

	public int getClearColor() {
		return clearColor;
	}

	public void setClearColor(int clearColor) {
		this.clearColor = clearColor;
	}

	public int getTransX() {
		return transX;
	}

	public void setTransX(int transX) {
		this.transX = transX;
	}

	public int getTransY() {
		return transY;
	}

	public void setTransY(int transY) {
		this.transY = transY;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}
	
	//Wrappers
	public void drawImage(Image image)
	{
		drawImage(image, 0, 0);
	}
	
	public void drawImageTile(ImageTile image, int tileX, int tileY)
	{
		drawImageTile(image, 0, 0, tileX, tileY);
	}
	
	public void drawLight(Light light)
	{
		drawLight(light, 0, 0);
	}
	
	public void drawRect(int offX, int offY, int width, int height, int color)
	{
		drawRect(offX, offY, width, height, color, ShadowType.NONE);
	}
}
