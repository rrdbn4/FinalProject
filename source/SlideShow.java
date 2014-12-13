package code;
import javax.swing.JApplet;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.TitledBorder;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.util.LinkedList;
import java.util.*;
import java.util.concurrent.*;
import java.net.*;

/** 
The internal frame used to show a slideshow of all the images specified directory
@author Robert Dunn, Holly Busken, Matt Lindner
*/
public class SlideShow extends JInternalFrame implements Runnable, ChangeListener
{
  /**
  The service to manage the tread
  */
  ExecutorService executor;
  /**
  The class' constants for the sleep times used in the animation thread
  */
  final int SLEEP_MAX = 50, SLEEP_MIN = 2, SLEEP_DEFAULT = 14; 
  /**
  The sleep time used in the animation thread
  */
  int sleepTime = SLEEP_DEFAULT;
  /**
  The continer to hold the graphical elements
  */
  JPanel container;
  /**
  The slider used to adjust the speed of the animation
  */
  JSlider speedSlider;
  /**
  The locally stored images
  */
  Image[] images;
  /**
  The array index of the next image up for showing
  */
  int nextImageIndex;
  /**
  The size of the slideshow queue
  */
  final int IMG_QUEUE_SIZE = 3;
  /**
  The queue of images being displayed on the screen
  */
  Queue<Image> currImages = new LinkedList<Image>();
  /**
  The flag to throw if there was a problem getting the images
  */
  boolean errorState = false;
  /**
  The width of each image being displayed
  */
  int imgWidth;
  /**
  The starting x position of the first image in the queue to be drawn
  */
  int startx = 0;

  /**
  The default constructor where everything is initialized
  */
  public SlideShow()
  {
    super("Slide Show", true, true, true, true);
    setBounds(0, 0, 500, 400);
    setLayout(new BorderLayout());

    container = new JPanel();
    container.setLayout(new GridLayout(1, 1));
    speedSlider = new JSlider(SLEEP_MIN, SLEEP_MAX, SLEEP_DEFAULT);
    speedSlider.addChangeListener(this);
    speedSlider.setBorder(new TitledBorder("Speed"));
    container.add(speedSlider);
    add(container, BorderLayout.SOUTH);

    executor = Executors.newFixedThreadPool(1);
    getImages();
    start();
  }

  /**
  Pulls in all the images in a directory and stores them locally for use later
  Puts the internal frame into an error state if something went wrong
  */
  public void getImages()
  {
    // URI url = getClass().getResource("./img/");
    File dir = new File("./img/");
    File[] imagePaths = dir.listFiles();
    if(imagePaths != null)
    {
      images = new Image[imagePaths.length];
      for(int i = 0; i < imagePaths.length; i++)
      {
        images[i] = new ImageIcon(imagePaths[i].getAbsolutePath()).getImage();
        // System.out.println(imagePaths[i].getPath());
      }
      for(int i = 0; i < IMG_QUEUE_SIZE; i++)  //populate starting queue
        currImages.add(images[i]);
      nextImageIndex = IMG_QUEUE_SIZE;
    }
    else
      errorState = true;
  }

  /**
  Start the zooming animation thread
  */
  public void start()
  {
    executor.execute(this);
  }

  /**
  The run method for the animation thread
  Startx is decremented until the first image in the queue goes off screen, then that image
    is popped from the queue and a new image is added at the tail. This happens continuously
    for the slideshow animation 
  */
  public void run()
  {
    if(!errorState)
    {
      while(true)
      {
        while(startx >= -imgWidth)
        {
          startx -= 1;
          repaint();
          try
          {
            Thread.sleep(sleepTime);
          }
          catch(InterruptedException e){}
        }
        startx = 0;
        currImages.remove();
        currImages.add(images[nextImageIndex]);
        if(nextImageIndex + 1 >= images.length)
          nextImageIndex = 0;
        else
          nextImageIndex++;
      }
    }
    repaint();
  }

  /**
  The change listener for the speed slider
  */
  public void stateChanged(ChangeEvent e)
  {
    sleepTime = (SLEEP_MAX - speedSlider.getValue()) + SLEEP_MIN;
  }

  /**
  The paint method where the images are drawn
  */
  public void paint(Graphics g)
  {
    super.paint(g);
    if(errorState)
    {
      g.setColor(Color.RED);
      g.drawString("Error getting images", 20, getInsets().top + 10);
    }
    else
    {
      int height = getHeight() - getInsets().top - getInsets().bottom - container.getHeight();
      int width = getWidth() - getInsets().left - getInsets().right;
      imgWidth = (int)(width * (0.833f));
      int imgHeight = (int)(height * (0.833f));
      int index = 0;
      for(Image im : currImages)
      {
        g.drawImage(im, startx + (int)(imgWidth * index), getInsets().top + (int)(height * (0.0833f)), imgWidth, imgHeight, this);
        index++;
      }
    }
  }
}