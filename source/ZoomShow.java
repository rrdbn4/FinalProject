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

/** 
The internal frame used to show a zoomshow of all the images in a specified directory
@author Robert Dunn, Holly Busken, Matt Lindner
*/
public class ZoomShow extends JInternalFrame implements Runnable, ChangeListener
{
  /**
  The service to manage the tread
  */
  ExecutorService executor;
  /**
  The class' constants for the sleep times used in the animation thread
  */
  final int SLEEP_MAX = 110, SLEEP_MIN = 13, SLEEP_DEFAULT = 45; 
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
  The current image being displayed
  */
  Image currImage;
  /**
  The flag to throw if there was a problem getting the images
  */
  boolean errorState = false;
  /**
  The margin from the top of the image to the top of the frame measured in percentage of total frame height
  */
  float margin;

  /**
  The default constructor where everything is initialized
  */
  public ZoomShow()
  {
    super("Zoom Show", true, true, true, true);
    setBounds(0, 0, 600, 400);
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
    String[] imageNames ={"bird","cat","cricket","dolphin","donkey","elephant","hawk","monkey","pig","rooster"};

      images = new Image[imageNames.length];
      for(int i = 0; i < imageNames.length; i++)
      {
        images[i] = new ImageIcon(getClass().getResource("/img/"+imageNames[i]+".jpg")).getImage();
      }
      currImage = images[0];
      nextImageIndex = 1;
  }

  /**
  Start the zooming animation thread
  */
  public void start()
  {
    executor.execute(this);
  }

  /**
  The thread's run method where the zooming animation is performed
  */
  public void run()
  {
    if(!errorState)
    {
      while(true)
      {
        final float maxMargin = 0.48f;
        final float delta = 0.005f;
        for(float i = maxMargin; i > 0; i -= delta) //enlarge
        {
          margin = i;
          repaint();
          try
          {
            Thread.sleep(sleepTime);
          }
          catch(InterruptedException e){}
        }
        for(float i = 0; i < maxMargin; i += delta) //shrink
        {
          margin = i;
          repaint();
          try
          {
            Thread.sleep(sleepTime);
          }
          catch(InterruptedException e){}
        }

        currImage = images[nextImageIndex];

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
      int imgHeight = height - (int)(height * margin * 2);
      int imgWidth = (int)(imgHeight * 1.33f); //4:3 ratio
      int startx = (int)((float)(width - imgWidth) / 2f);

      g.drawImage(currImage, startx + getInsets().left, getInsets().top + (int)(height * margin), imgWidth, imgHeight, this);
    }
  }
}