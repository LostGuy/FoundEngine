package com.lostguy.core.components;

import java.util.ArrayList;

import com.lostguy.core.GameContainer;
import com.lostguy.core.Renderer;

public abstract class GameObject 
{
	protected float x, y, width, height;
	
	protected String tag = "null";
	protected boolean dead = false;
	protected ArrayList<Component> components = new ArrayList<Component>();
	
	public abstract void render(GameContainer gc, Renderer r);
	public abstract void update(GameContainer gc, float dt);
	public abstract void dispose();
	public abstract void componentEvent(String name, GameObject g);
	
	public void updateComponents(GameContainer gc, float dt)
	{
		for(Component c : components)
		{
			c.update(gc, this, dt);
		}
	}
	
	public void renderComponents(GameContainer gc, Renderer r)
	{
		for(Component c : components)
		{
			c.render(gc, r);
		}
	}
	
	public void addComponent(Component c)
	{
		components.add(c);
	}
	
	public void removeComponent(String tag)
	{
		for(int i = 0; i < components.size(); i++)
		{
			if(components.get(i).getTag().equalsIgnoreCase(tag))
			{
				components.remove(i);
			}
		}
	}
	
	public String getTag() {
		return tag;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public float getWidth() {
		return width;
	}
	
	public void setWidth(float width) {
		this.width = width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public void setHeight(float height) {
		this.height = height;
	}
	
	
}
