package com.lostguy.core.components;

import java.util.ArrayList;

public class Physics
{
	private ArrayList<Collider> colliders = new ArrayList<Collider>();
	
	public void update()
	{
		for(int i = 0; i < colliders.size(); i++)
		{
			for(int j = i + 1; j < colliders.size(); j++)
			{
				Collider c1 = colliders.get(i);
				Collider c2 = colliders.get(j);
				
				//Colliding on the x-axis
				if(Math.abs(c1.getX() - c2.getX()) < c1.getHalfWidth() + c2.getHalfWidth())
				{
					if(Math.abs(c1.getY() - c2.getY()) < c1.getHalfHeight() + c2.getHalfHeight())
					{
						c1.collision(c2.getGameObject());
						c2.collision(c1.getGameObject());
					}
				}
			}
		}
		
		colliders.clear();
	}
	
	public void addCollider(Collider c)
	{
		colliders.add(c);
	}
}
