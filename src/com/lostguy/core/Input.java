package com.lostguy.core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Input implements KeyListener, MouseListener, MouseMotionListener
{
	private GameContainer gameContainer;
	
	//Keys for current frame
	private boolean [] keys = new boolean[250];
	
	//Keys for last frame
	private boolean [] keysLast = new boolean[250];
	
	//Buttons for current frame
	private boolean [] buttons = new boolean[5];
	
	//Buttons for last frame
	private boolean [] buttonsLast = new boolean[5];
	
	private int mouseX, mouseY;
	
	public Input(GameContainer gameContainer)
	{
		this.gameContainer = gameContainer;
		
		//Add listeners to canvas
		gameContainer.getWindow().getCanvas().addKeyListener(this);
		gameContainer.getWindow().getCanvas().addMouseListener(this);
		gameContainer.getWindow().getCanvas().addMouseMotionListener(this);
	}
	
	public void update()
	{
		keysLast = keys.clone();
		buttonsLast = buttons.clone();
	}
	
	public boolean isKey(int keyCode)
	{
		return keys[keyCode];
	}
	
	/**
	 * Key pressed
	 * @param keyCode
	 * @return
	 */
	public boolean isKeyPressed(int keyCode)
	{
		return keys[keyCode] && !keysLast[keyCode];
	}
	
	/**
	 * Key released
	 * @param keyCode
	 * @return
	 */
	public boolean isKeyReleased(int keyCode)
	{
		return !keys[keyCode] && keysLast[keyCode];
	}
	
	public boolean isButton(int button)
	{
		return buttons[button];
	}
	
	/**
	 * Button pressed
	 * @param button
	 * @return
	 */
	public boolean isButtonPressed(int button)
	{
		return buttons[button] && !buttonsLast[button];
	}
	
	/**
	 * Button released
	 * @param button
	 * @return
	 */
	public boolean isButtonReleased(int button)
	{
		return !buttons[button] && buttonsLast[button];
	}
	
	@Override
	public void mouseDragged(MouseEvent e) 
	{
		mouseX = (int)(e.getX() / gameContainer.getScale());
		mouseY = (int)(e.getY() / gameContainer.getScale());
	}

	@Override
	public void mouseMoved(MouseEvent e) 
	{
		mouseX = (int)(e.getX() / gameContainer.getScale());
		mouseY = (int)(e.getY() / gameContainer.getScale());
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		buttons[e.getButton()] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		buttons[e.getButton()] = false;
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{
	}

	public int getMouseX() {
		return mouseX;
	}
	
	public void setMouseX(int mouseX) {
		this.mouseX = mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}
	
	public void setMouseY(int mouseY) {
		this.mouseY = mouseY;
	}	
}
