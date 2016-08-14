package com.lostguy.core.fx;

public class ImageTile extends Image
{

	public int tileWidth, tileHeight;
	
	public ImageTile(String path, int tileWidth, int tileHeight) 
	{
		super(path);
		this.tileHeight = tileHeight;
		this.tileWidth = tileWidth;
	}
	
}
