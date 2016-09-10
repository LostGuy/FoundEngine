package com.lostguy.core.components;

import com.lostguy.core.GameContainer;
import com.lostguy.core.Renderer;

public class Collider extends Component
{
	private GameObject gameObject;
	private float x, y, halfWidth, halfHeight;
	
	public Collider()
	{
		setTag("collider");
	}
	
	@Override
	public void update(GameContainer gc, GameObject object, float dt) 
	{
		if(this.gameObject == null)
		{
			this.gameObject = object;
		}
		halfWidth = object.getWidth() / 2;
		halfHeight = object.getHeight() / 2;
		x = object.getX() + halfWidth;
		y = object.getY() + halfHeight;
		gc.getPhysics().addCollider(this);
	}

	@Override
	public void render(GameContainer gc, Renderer r) 
	{
		
	}
	
	public void collision(GameObject g)
	{
		this.gameObject.componentEvent(tag, g);
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

	public float getHalfWidth() {
		return halfWidth;
	}

	public void setHalfWidth(float halfWidth) {
		this.halfWidth = halfWidth;
	}

	public float getHalfHeight() {
		return halfHeight;
	}

	public void setHalfHeight(float halfHeight) {
		this.halfHeight = halfHeight;
	}

	public GameObject getGameObject() {
		return gameObject;
	}

	public void setGameObject(GameObject g) {
		this.gameObject = g;
	}
}
