package test;

import com.lostguy.core.GameContainer;
import com.lostguy.core.Renderer;
import com.lostguy.core.components.Collider;
import com.lostguy.core.components.GameObject;

public class Enemy extends GameObject
{
	private GameObject target = null;
	
	public Enemy(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.width = 16;
		this.height = 64;
		setTag("enemy");
		addComponent(new Collider());
	}
	
	@Override
	public void render(GameContainer gc, Renderer r) 
	{
		r.drawRect((int)x, (int)y, (int)width, (int)height, 0xffff0000);
	}

	@Override
	public void update(GameContainer gc, float dt) 
	{
		if(target == null)
		{
			target = gc.getGame().peek().getManager().findObject("ball");
		}
		
		if(target.getY() + target.getHeight() / 2 > y - 2)
		{
			y += dt * 75;
		}
		
		if(target.getY() + target.getHeight() / 2 < y + 2)
		{
			y -= dt * 75;
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
