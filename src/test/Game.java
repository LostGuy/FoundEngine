package test;

import java.awt.event.KeyEvent;

import com.lostguy.core.AbstractGame;
import com.lostguy.core.GameContainer;
import com.lostguy.core.Input;
import com.lostguy.core.Renderer;
import com.lostguy.core.fx.Image;
import com.lostguy.core.fx.Light;
import com.lostguy.core.fx.ShadowType;
import com.lostguy.core.fx.SoundClip;

public class Game extends AbstractGame
{
	private Image image = new Image("/test.png");
	private Image image2 = new Image("/test2.png");
	
	private Light light = new Light(0xffff0000, 200);
	private Light light1 = new Light(0xffffff, 200);
	private Light light2 = new Light(0xff0000ff, 200);
	
	private SoundClip clip = new SoundClip("/DoomDoorSoundTest.wav");
	
	float x = 0;
	
	
	public static void main(String args[])
	{
		GameContainer gc = new GameContainer(new Game());
		gc.setWidth(320);
		gc.setHeight(240);
		gc.setScale(3);
		gc.start();
	}
	
	public Game()
	{
		image2.shadowType = ShadowType.FADE;
	}

	@Override
	public void update(GameContainer gameContainer, float dt)
	{
		if(Input.isKeyPressed(KeyEvent.VK_A))
		{
			x += dt* 50;
			
			clip.play();
		}
	}

	@Override
	public void render(GameContainer gameContainer, Renderer renderer) 
	{
		renderer.drawImage(image, 0, 0);
		renderer.drawImage(image2, 0, 0);
		renderer.drawLight(light1, Input.getMouseX(), Input.getMouseY());
	}
	
}
