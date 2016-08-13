package com.lostguy.core;

public class GameContainer implements Runnable
{
	private Thread thread;
	private AbstractGame game;
	
	private Window window;
	
	private Renderer renderer;
	
	private Input input;
	
	private boolean running = false;
	
	//60 updates per second
	private double frameCap = 1.0/60;
	
	//Dimensions
	private int width = 640, height = 320;
	private float scale = 2.0f;
	private String title = "Found Engine v1.0 by Lost Guy";
	
	public GameContainer(AbstractGame game)
	{
		this.game = game;
	}
	
	/**
	 * Starts the game
	 */
	public void start()
	{
		if(running)
		{
			return;
		}
		
		//Initialize engine components
		window = new Window(this);
		renderer = new Renderer(this);
		input = new Input(this);
		thread = new Thread(this);
		thread.run();
	}
	
	/**
	 * Stops the game
	 */
	public void stop()
	{
		if(!running)
		{
			return;
		}
		
		running = false;
	}
	
	public void run()
	{
		//We're running
		running = true;
		
		//Time in seconds
		double firstTime = 0,
				lastTime = System.nanoTime() / 1000000000.0,
				passedTime = 0,
				unprocessedTime = 0,
				frameTime = 0;
		int frames = 0;
		int fps = 0;
		
		//Game loop
		while(running)
		{
			boolean render = false;
			
			//Records the time it takes to loop once
			firstTime = System.nanoTime() / 1000000000.0;
			passedTime = firstTime - lastTime;
			lastTime = firstTime;
			
			unprocessedTime += passedTime;
			frameTime += passedTime;
			
			while(unprocessedTime >= frameCap)
			{
				game.update(this, (float)frameCap);
				input.update();
				unprocessedTime -= frameCap;
				render = true;
				
				if(frameTime >= 1)
				{
					frameTime = 0;
					fps = frames;;
					frames = 0;
				}
			}
			
			if(render)
			{
				renderer.clear();
				game.render(this, renderer);
				renderer.drawString("FPS- " + fps, 0xffffffff, 0, 0);
				window.update();
				frames++;
			}
			else
			{
				//Not updating anything so take some load off of the engine
				try
				{
					Thread.sleep(1);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
		
		cleanup();
	}
	
	/**
	 * Dispose of used objects
	 */
	private void cleanup()
	{
		window.cleanup();
	}
	
	//Getters and Setters
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Window getWindow() {
		return window;
	}
}
