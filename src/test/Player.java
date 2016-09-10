package test;

import java.awt.event.KeyEvent;

import com.lostguy.core.GameContainer;
import com.lostguy.core.Renderer;
import com.lostguy.core.components.Collider;
import com.lostguy.core.components.GameObject;

public class Player extends GameObject
{

	public Player(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.width = 16;
		this.height = 64;
		setTag("player");
		addComponent(new Collider());
	}
	
	@Override
	public void render(GameContainer gc, Renderer r) 
	{
		r.drawRect((int)x, (int)y, (int)width, (int)height, 0xffffffff);
	}

	@Override
	public void update(GameContainer gc, float dt) 
	{
		if(gc.getInput().isKey(KeyEvent.VK_S))
		{
			y += 75 * dt;
			
			if(y + height > gc.getHeight())
			{
				y = gc.getHeight() - height;
			}
		}
		
		if(gc.getInput().isKey(KeyEvent.VK_W))
		{
			y -= 75 * dt;
			
			if(y < 0)
			{
				y = 0;
			}
		}
		
		updateComponents(gc, dt);
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void componentEvent(String name, GameObject g) 
	{
	}

}
