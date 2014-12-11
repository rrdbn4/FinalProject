package code;
import javax.swing.JApplet;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.util.LinkedList;
import java.util.*;
import java.util.concurrent.*;

public class ZoomShow extends JInternalFrame implements Runnable
{
  ExecutorService executor;
  String anImagePath;
  Image[] images;
  int nextImageIndex;
  Image currImage;
  boolean errorState = false;
  float margin;

  public ZoomShow()
  {
    super("Zoom Show", true, true, true, true);
    setBounds(0, 0, 500, 400);
    setLayout(new BorderLayout());

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
        final float maxMargin = 0.4f;
        for(float i = maxMargin; i > 0; i -= 0.01f) //enlarge
        {
          margin = i;
          repaint();
          try
          {
            Thread.sleep(10);
          }
          catch(InterruptedException e){}
        }
        for(float i = 0; i < maxMargin; i += 0.01f) //shrink
        {
          margin = i;
          repaint();
          try
          {
            Thread.sleep(10);
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
      int height = getHeight() - getInsets().top - getInsets().bottom;
      int width = getWidth() - getInsets().left - getInsets().right;
      int imgWidth = (int)(width * margin);
      int imgHeight = (int)(height * margin);
      int index = 0;

      g.drawImage(currImage, getInsets().left + (int)(imgWidth * margin), getInsets().top + (int)(height * margin), imgWidth, imgHeight, this);
    }
  }
}