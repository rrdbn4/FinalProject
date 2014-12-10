package code;
import javax.swing.JApplet;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.concurrent.*;

public class SlideShow extends JInternalFrame implements Runnable
{
  ExecutorService executor;
  String anImagePath;
  public SlideShow()
  {
    super("Slide Show", true, true, true, true);
    setBounds(0, 0, 500, 400);
    setLayout(new BorderLayout());

    // executor = Executors.newFixedThreadPool(1);
    // repaint();
    getImages();
  }

  public void getImages()
  {
    File img = new File("./img/");
    File[] images = img.listFiles();
    if(images == null)
      System.out.println("NULL");
    else
      for(File image : images)
      {
        System.out.println(image.getAbsolutePath());
        anImagePath = image.getAbsolutePath();
      }
    repaint();
  }

  public void start()
  {
    executor.execute(this);
  }

  public void run()
  {

  }

  public void paint(Graphics g)
  {
    super.paint(g);
    if(anImagePath != null)
    {
      Image img = new ImageIcon(anImagePath).getImage();
      g.drawImage(img, 0, 0, this);
    }
  }
}