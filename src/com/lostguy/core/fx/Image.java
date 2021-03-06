package com.lostguy.core.fx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image
{
	public int width, height;
	public int[] pixels;
	
	public ShadowType shadowType = ShadowType.NONE;
	
	public Image(String path)
	{
		BufferedImage image = null;
		
		try 
		{
			image = ImageIO.read(Image.class.getResourceAsStream(path));
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		width = image.getWidth();
		height = image.getHeight();
		
		pixels = image.getRGB(0, 0, width, height, null, 0, width);
		
		//Make sure the image is out of the RAM
		image.flush();
	}
}
