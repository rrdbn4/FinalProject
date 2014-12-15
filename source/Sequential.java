package code;

import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.*;

import javax.sound.sampled.*;
import javax.swing.*;

public class Sequential extends JInternalFrame implements Runnable, LineListener
{
	/**
	 * Manages sorting thread.
	 */
	public ExecutorService executor;
	/**
	 * Array containing the filenames (minus extensions) 
	 */
	String[] fileNames ={"bird","cat","cricket","dolphin","donkey","elephant","hawk","monkey","pig","rooster"};
	/**
	 * Contains the pathname of each image in the images directory.
	 */
	private File[] images;
	/**
	 * Contains the pathname of each audio file in the audio directory.
	 */
	private File[] audio;
	/**
	 * Once an audio clip is loaded, used to play each clip.
	 */
	private Clip clip;
	/**
	 * Contains the image that will be displayed every time a new audio clip is played.
	 */
	private Image image;
	/**
	 * Used to load each audio clip.
	 */
	private AudioInputStream audioIn;
	/**
	 * Indicates which element of the images and audio array we are currently playing/displaying.
	 */
	int index = 0;
	/**
	 * The flag to throw if there was a problem getting the images.
	 */
	private boolean imagesErrorState = false;
	/**
	 * The flag to throw if there was a problem getting the audio.
	 */
	private boolean audioErrorState = false;
	/**
	 * Flag used to prevent audio from playing after JFrame has been closed.
	 */
	private boolean isRunning = true;
	
	/**
	 * Constructor for the Sequential Class. Creates new JInternal Frame, attempts to load
	 * images and audio files, and starts a thread to display each image with an audio file.
	 * If loading either of the directories fails, fail flags are set.
	 * @param x_loc X-coordinate where JInternal Frame will be drawn.
	 * @param y_loc Y-coordinate where JInternal Frame will be drawn.
	 * @param width Width of the JInternalFrame
	 * @param height Height of the JInternalFrame
	 */
	public Sequential(final int x_loc, final int y_loc, final int width, final int height)
	{
		super("Sequential", true, true, true, true);
		setBounds(x_loc, y_loc, width, height);
		
		/**
		 * The thread pool only contains 1 thread, the one doing the image/audio work.
		 */
		executor = Executors.newFixedThreadPool(1);
		
    	/**
    	 * Create the file arrays we will be saving our images and audio files in.
    	 */
    	images = new File[fileNames.length];
    	audio = new File[fileNames.length];
    	
    	for (int i = 0; i < fileNames.length; i++)
    	{
    		/**
    		 * Get all the audio files.
    		 */
			audio[i] = new File((getClass().getResource("/audio/"+fileNames[i]+".wav").toString().replace("file:/", "")));
    		/**
    		 * Get all the image files.
    		 */
			images[i] = new File((getClass().getResource("/img/"+fileNames[i]+".jpg").toString().replace("file:/", "")));
    	} 	
    	
    	/**
    	 * Ensure that each File array is in the same order. This way, we ensure that the correct image is displayed for each audio clip.
    	 */
    	Arrays.sort(images);
    	Arrays.sort(audio);

	    setVisible(true);
  	  	executor.execute(this);
	    repaint();
	}
	
	/**
	 * The function called when execute() is called.
	 * If the user selected image and audio directories, the first audio and image files are loaded, and displayed.
	 * A line listener is added to the audio clip playing. Once it is done playing, the update() function is called, 
	 * and the next set of images and audio files will be loaded, and played.
	 */
	public void run()
	{
		/**
		 * Using the default images and audio files provided, for demonstration purposes,
		 * the correct image file will be displayed for each song since they are at the same
		 * index in their respective arrays.
		 */
		
		if (!imagesErrorState && !audioErrorState)
		{
			try
			{
				audioIn = AudioSystem.getAudioInputStream(audio[index].toURI().toURL());
				clip = AudioSystem.getClip();
				clip.addLineListener(this);
				
				clip.open(audioIn);
				clip.start();
			} catch (UnsupportedAudioFileException e3){}
			catch(IOException e2){}
			catch(LineUnavailableException e1) {}
					
			image = new ImageIcon(images[index].toString()).getImage();
		}
		
		repaint();
	}
	
	/**
	 * Detects when each audio file has finished playing.
	 * Loads the next audio/image set, and plays/displays them.
	 */
	public void update(LineEvent le)
	{
		/**
		 * The infinite loop for playing the audio directory only continues as long as the
		 * JInternalFrame is open (!isClosed()).
		 */
		if (le.getType() == LineEvent.Type.STOP && !isClosed() && isRunning)
		{
			clip.close();
			index = (index < audio.length - 1)? index + 1: 0;
			
			try {
				audioIn = AudioSystem.getAudioInputStream(audio[index].toURI().toURL());
				clip = AudioSystem.getClip();
				clip.addLineListener(this);
				
				clip.open(audioIn);
				clip.start();
			} catch (UnsupportedAudioFileException e3){}
			catch(IOException e2){}
			catch(LineUnavailableException e1) {}
			
			image = new ImageIcon(images[index].toString()).getImage();
			
			repaint();
		}
	}
	
	/**
	 * Draws the current image, utilizing the entire JInternalFrame space.
	 * If there was an error loading either the audio or images directory,
	 * an error message will be displayed instead.
	 */
	public void paint(Graphics g)
	{
		super.paint(g);
		
		int left = getInsets().left;
		int top = getInsets().top + 23;
		int width = getWidth() - getInsets().left - getInsets().right;
		int height = getHeight() - getInsets().top - getInsets().bottom - 23;
		
		if (audioErrorState || imagesErrorState)
		{
			String errorMsg = "Error loading " + ((imagesErrorState)?("Images directory"):("")) + ((imagesErrorState && audioErrorState)?(" and "):("")) + ((audioErrorState)?("Audio directory"):(""));
			g.drawString(errorMsg, 20, 50);
		}
		else
		{
			g.drawImage(image, left, top, width, height, this);
		}
	}
	
	public void stop()
	{
		isRunning = false;
	}
}
