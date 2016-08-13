package com.lostguy.core;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Window 
{
	//The Window
	private JFrame frame;
	
	//Render the window to
	private Canvas canvas;
	
	//Image of the game
	private BufferedImage image;
	
	//Used to draw the image
	private Graphics graphics;
	
	private BufferStrategy bufferStrategy;
	
	public Window(GameContainer gameContainer)
	{
		image = new BufferedImage(gameContainer.getWidth(), gameContainer.getHeight(), BufferedImage.TYPE_INT_RGB);
		
		canvas = new Canvas();
		
		//Calculate dimensions and set them
		int dimensionWidth = (int)(gameContainer.getWidth() * gameContainer.getScale());
		int dimensionHeight = (int)(gameContainer.getHeight() * gameContainer.getScale());
		
		Dimension dimension = new Dimension(dimensionWidth, dimensionHeight);
		
		//Setup Canvas
		canvas.setPreferredSize(dimension);
		canvas.setMaximumSize(dimension);
		
		//Setup Window
		frame = new JFrame(gameContainer.getTitle());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(canvas, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		
	
		//Setup BufferStrategy and Graphics
		canvas.createBufferStrategy(1);
		bufferStrategy = canvas.getBufferStrategy();
		graphics = bufferStrategy.getDrawGraphics();
	}
	
	/**
	 * Draws the image
	 */
	public void update()
	{
		//Draw the image
		graphics.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
		bufferStrategy.show();
	}
	
	/**
	 * Disposes of objects after use
	 */
	public void cleanup()
	{
		//Dispose objects
		graphics.dispose();
		bufferStrategy.dispose();
		image.flush();
		frame.dispose();
	}

	//Getters
	public Canvas getCanvas() {
		return canvas;
	}

	public BufferedImage getImage() {
		return image;
	}
}
