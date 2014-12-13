package code;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import javax.sound.sampled.*;
import javax.swing.*;

public class Sequential extends JInternalFrame implements Runnable, LineListener
{
	private ExecutorService executor;
	
	private File imagesDir, audioDir;
	private File[] images, audio;
	
	Clip clip;
	Image image;
	AudioInputStream audioIn;
	int index;
	
	public Sequential(final int x_loc, final int y_loc, final int width, final int height)
	{
		super("Sequential", true, true, true, true);
		setBounds(x_loc, y_loc, width, height);
		
		executor = Executors.newFixedThreadPool(1);
		
		imagesDir = new File("E:/School/Java/Final/bin/img/");
		audioDir = new File("E:/School/Java/Final/bin/audio/");
				
		images = imagesDir.listFiles();
		Arrays.sort(images);
		audio = audioDir.listFiles();
		Arrays.sort(audio);

		index = 0;
		
		setVisible(true);
		
		executor.execute(this);
	}
	
	public void run()
	{
		try
		{
			audioIn = AudioSystem.getAudioInputStream(audio[index].toURI().toURL());
			clip = AudioSystem.getClip();
			clip.addLineListener(this);
			
			clip.open(audioIn);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {}
				
		image = new ImageIcon(images[index].toString()).getImage();
		
		repaint();
	}
	
	public void update(LineEvent le)
	{
		if (le.getType() == LineEvent.Type.STOP)
		{
			clip.close();
			index = (index < audio.length - 1)? index + 1: 0;
			
			try {
				audioIn = AudioSystem.getAudioInputStream(audio[index].toURI().toURL());
				clip = AudioSystem.getClip();
				clip.addLineListener(this);
				
				clip.open(audioIn);
				clip.start();
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) { System.out.println("asd");}
			
			image = new ImageIcon(images[index].toString()).getImage();
			
			repaint();
		}
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		
		int left = getInsets().left;
		int top = getInsets().top + 23;
		int width = getWidth() - getInsets().left - getInsets().right;
		int height = getHeight() - getInsets().top - getInsets().bottom - 23;
		
		g.drawImage(image, left, top, width, height, this);
	}
}
