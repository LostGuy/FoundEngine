package test;

import java.awt.event.KeyEvent;

import com.lostguy.core.AbstractGame;
import com.lostguy.core.GameContainer;
import com.lostguy.core.Input;
import com.lostguy.core.Renderer;
import com.lostguy.core.graphics.Image;

public class Game extends AbstractGame
{
	private Image image = new Image("/test.png");
	
	float x = 0;
	
	
	public static void main(String args[])
	{
		GameContainer gc = new GameContainer(new Game());
		gc.start();
	}

	@Override
	public void update(GameContainer gameContainer, float dt)
	{
		if(Input.isKey(KeyEvent.VK_A))
		{
			x += dt* 50;
		}
	}

	@Override
	public void render(GameContainer gameContainer, Renderer renderer) 
	{
		renderer.drawImage(image, (int)x, 50);
	}
	
}
