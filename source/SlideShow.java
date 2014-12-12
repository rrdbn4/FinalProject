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

public class SlideShow extends JInternalFrame implements Runnable, ChangeListener
{
  ExecutorService executor;
  final int SLEEP_MAX = 50, SLEEP_MIN = 2, SLEEP_DEFAULT = 14; 
  int sleepTime = SLEEP_DEFAULT;
  JPanel container;
  JSlider speedSlider;
  Image[] images;
  int nextImageIndex;
  final int IMG_QUEUE_SIZE = 3;
  Queue<Image> currImages = new LinkedList<Image>();
  boolean errorState = false;
  int imgWidth;
  int startx = 0;

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

  public void getImages()
  {
    File dir = new File("./img/");
    File[] imagePaths = dir.listFiles();
    if(imagePaths != null)
    {
      images = new Image[imagePaths.length];
      for(int i = 0; i < imagePaths.length; i++)
      {
        images[i] = new ImageIcon(imagePaths[i].getAbsolutePath()).getImage();
      }
      for(int i = 0; i < IMG_QUEUE_SIZE; i++)  //populate starting queue
        currImages.add(images[i]);
      nextImageIndex = IMG_QUEUE_SIZE;
    }
    else
      errorState = true;
  }

  public void start()
  {
    executor.execute(this);
  }

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

  public void stateChanged(ChangeEvent e)
  {
    sleepTime = (SLEEP_MAX - speedSlider.getValue()) + SLEEP_MIN;
  }

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