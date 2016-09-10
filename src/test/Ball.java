package test;

import com.lostguy.core.GameContainer;
import com.lostguy.core.Renderer;
import com.lostguy.core.components.Collider;
import com.lostguy.core.components.GameObject;

public class Ball extends GameObject
{
	//Direction left or right, -1 left, 1 right
	int direction = -1;
	
	float speedY = 0;
	
	public Ball(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.width = 16;
		this.height = 16;
		setTag("ball");
		addComponent(new Collider());
	}

	@Override
	public void render(GameContainer gc, Renderer r) 
	{
		r.drawRect((int)x, (int)y, (int)width, (int)height, 0xff00ff00);
	}

	@Override
	public void update(GameContainer gc, float dt) 
	{
		x += direction * (50 * dt);
		y += speedY;
		
		if(y < 0)
		{
			y = 0; 
			speedY *= -1;
		}
		if(y + height > gc.getHeight())
		{
			y = gc.getHeight() - height;
			speedY *= -1;
		}
		
		updateComponents(gc, dt);
	}

	@Override
	public void dispose() 
	{
		
	}

	@Override
	public void componentEvent(String name, GameObject g) 
	{
		if(name.equalsIgnoreCase("collider"))
		{
			if(g.getX() < x)
			{
				direction *= -1;
			}
		}
		
		speedY = -((g.getY() + (g.getHeight() / 2)) - (y + (height / 2))) / (g.getHeight() / 2);
	}

}
