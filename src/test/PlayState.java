package test;

import java.util.ArrayList;

import com.lostguy.core.GameContainer;
import com.lostguy.core.Renderer;
import com.lostguy.core.components.ObjectManager;
import com.lostguy.core.components.State;

public class PlayState extends State
{	
	public PlayState()
	{
		manager.addObject(new Player(0, 0));
		manager.addObject(new Ball(156, 116));
		manager.addObject(new Enemy(304, 0));
	}
	
	@Override
	public void update(GameContainer gc, float dt) 
	{
		manager.updateObjects(gc, dt);
	}

	@Override
	public void render(GameContainer gc, Renderer r) 
	{
		manager.renderObjects(gc, r);
	}

	@Override
	public void dispose()
	{
		manager.disposeObjects();
	}

	public ObjectManager getManager() {
		return manager;
	}

	public void setManager(ObjectManager manager) {
		this.manager = manager;
	}
	
}
