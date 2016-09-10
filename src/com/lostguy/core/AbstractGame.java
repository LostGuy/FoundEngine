package com.lostguy.core;

import java.util.Stack;

import com.lostguy.core.components.State;

public abstract class AbstractGame 
{
	private Stack<State> states = new Stack<State>();
	
	public abstract void update(GameContainer gameContainer, float dt);
	
	public abstract void render(GameContainer gameContainer, Renderer renderer);
	
	public State peek()
	{
		return states.peek();
	}

	public void push(State s)
	{
		states.push(s);
	}
	
	public State pop()
	{
		states.peek().dispose();
		return states.pop();
	}
	
	public void setState(State s)
	{
		states.pop();
		states.push(s);
	}
}
