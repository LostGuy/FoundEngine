package com.lostguy.core.components;

import java.util.ArrayList;

import com.lostguy.core.GameContainer;
import com.lostguy.core.Renderer;

public class ObjectManager
{
	private ArrayList<GameObject> objects = new ArrayList<GameObject>();
	
	public void updateObjects(GameContainer gc, float dt)
	{
		for(int i = 0; i < objects.size(); i++)
		{
			objects.get(i).update(gc,  dt);
			
			if(objects.get(i).isDead())
			{
				objects.remove(i);
			}
		}
	}
	
	public void renderObjects(GameContainer gc, Renderer r)
	{
		for(int i = 0; i < objects.size(); i++)
		{
			objects.get(i).render(gc,  r);
		}
	}
	
	public GameObject findObject(String tag)
	{
		for(GameObject g : objects)
		{
			if(g.getTag().equalsIgnoreCase(tag))
			{
				return g;
			}
		}
		
		return null;
	}
	
	public void disposeObjects()
	{
		for(GameObject g : objects)
		{
			g.dispose();
		}
	}
	
	public void addObject(GameObject object)
	{
		objects.add(object);
	}
	
	
}
