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

public class ZoomShow extends JInternalFrame implements Runnable, ChangeListener
{
  ExecutorService executor;
  final int SLEEP_MAX = 110, SLEEP_MIN = 13, SLEEP_DEFAULT = 45; 
  int sleepTime = SLEEP_DEFAULT;
  JPanel container;
  JSlider speedSlider;
  Image[] images;
  int nextImageIndex;
  Image currImage;
  boolean errorState = false;
  float margin;

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
      currImage = images[0];
      nextImageIndex = 1;
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
      int imgHeight = height - (int)(height * margin * 2);
      int imgWidth = (int)(imgHeight * 1.33f); //4:3 ratio
      int startx = (int)((float)(width - imgWidth) / 2f);

      g.drawImage(currImage, startx + getInsets().left, getInsets().top + (int)(height * margin), imgWidth, imgHeight, this);
    }
  }
}