package com.lostguy.core.fx;

public class Light
{
	public int color, radius, diameter;
	
	public int[] lightMap;
	
	public Light(int color, int radius)
	{
		this.color = color;
		this.radius = radius;
		this.diameter = radius * 2;
		
		lightMap = new int[diameter * diameter];
		
		for(int x = 0; x < diameter; x++)
		{
			for(int y = 0; y < diameter; y++)
			{
				float distance = (float)Math.sqrt((x - radius) * (x - radius) + (y - radius) * (y - radius));
				
				if(distance < radius)
				{
					lightMap[x + y * diameter] = Pixel.getColorPower(color, 1 - distance/radius);
				}
				else
				{
					lightMap[x + y * diameter] = 0;
				}
			}
		}
	}
	
	/**
	 * Returns the color of a pixel
	 * @param x - x pos
	 * @param y - y pos
	 * @return - color
	 */
	public int getLightValue(int x, int y)
	{
		//Out of bounds return black
		if(x < 0 || x >= diameter || y < 0 || y >= diameter)
		{
			return 0xff000000;
		}
		else
		{
			return lightMap[x + y * diameter];
		}
	}
}
