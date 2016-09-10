package test;

import java.awt.event.KeyEvent;
import java.util.Stack;

import com.lostguy.core.AbstractGame;
import com.lostguy.core.GameContainer;
import com.lostguy.core.Input;
import com.lostguy.core.Renderer;
import com.lostguy.core.components.State;
import com.lostguy.core.fx.Image;
import com.lostguy.core.fx.Light;
import com.lostguy.core.fx.ShadowType;
import com.lostguy.core.fx.SoundClip;

public class GameManager extends AbstractGame
{	
	public static void main(String args[])
	{
		GameContainer gc = new GameContainer(new GameManager());
		gc.setWidth(320);
		gc.setHeight(240);
		gc.setScale(3);
		gc.setClearScreen(true);
		gc.setDrawLights(false);
		gc.setLightingEnabled(false);
		gc.start();
	}
	
	public GameManager()
	{
		push(new PlayState());
	}

	@Override
	public void update(GameContainer gameContainer, float dt)
	{
		peek().update(gameContainer, dt);
	}

	@Override
	public void render(GameContainer gameContainer, Renderer renderer) 
	{
		peek().render(gameContainer, renderer);
	}	
}
