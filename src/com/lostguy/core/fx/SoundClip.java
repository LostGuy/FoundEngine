package com.lostguy.core.fx;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundClip 
{
	private Clip clip;
	
	private FloatControl gainControl;
	
	public SoundClip(String path)
	{
		try
		{
			//Setting up the Audio File
			InputStream audioSource = getClass().getResourceAsStream(path);
			InputStream bufferedInputStream = new BufferedInputStream(audioSource);
			
			AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedInputStream);
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 
					baseFormat.getSampleRate(), 
					16, 
					baseFormat.getChannels(), 
					baseFormat.getChannels() * 2, 
					baseFormat.getSampleRate(), 
					false);
			
			AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
			
			clip = AudioSystem.getClip();
			clip.open(dais);
			
			//For controlling volume
			gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Plays the sound
	 */
	public void play()
	{
		//No clip means no sound
		if(clip == null)
		{
			return;
		}
		stop();
		clip.setFramePosition(0);
		
		//Loop through the channels until it finds one that can play the sound
		while(!clip.isRunning())
		{
			clip.start();
		}
	}
	
	/**
	 * Stops the sound
	 */
	public void stop()
	{
		if(clip.isRunning())
		{
			clip.stop();
		}
	}
	
	/**
	 * Closes the clip and removes it from memory
	 */
	public void close()
	{
		stop();
		clip.drain();
		clip.close();
	}
	
	/**
	 * Loops over the clip continuously
	 */
	public void loop()
	{
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		
		//Loop through the channels until it finds one that can play the sound
		while(!clip.isRunning())
		{
			clip.start();
		}
	}
	
	/**
	 * Control the volume
	 * @param volume - value for the volume
	 */
	public void setVolume(float volume)
	{
		gainControl.setValue(volume);
	}
	
	public boolean isRunning()
	{
		return clip.isRunning();
	}
}














