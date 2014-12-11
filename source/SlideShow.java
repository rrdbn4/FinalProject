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

public class SlideShow extends JInternalFrame implements Runnable
{
  ExecutorService executor;
  String anImagePath;
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
            Thread.sleep(17);
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
      imgWidth = (int)(width * (0.67f));
      int imgHeight = (int)(height * (0.67f));
      int index = 0;
      for(Image im : currImages)
      {
        g.drawImage(im, startx + (int)(imgWidth * index), getInsets().top + (int)(height * (0.167f)), imgWidth, imgHeight, this);
        index++;
      }
    }
  }
}