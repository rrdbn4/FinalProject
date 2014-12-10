package code;
import javax.swing.JApplet;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
Demo is the driver class for the 
@author Robert Dunn, Holly Busken, Matt Lindner
*/
public class Demo extends JApplet implements ActionListener
{ 
  JDesktopPane desktop;  
  JMenuBar menuBar;
  JMenuItem author, problemDescription, references, help;
  JMenuItem interactive, sequential, slideshow, zoomshow;

  public void init()
  {
    setSize(800, 600);

    desktop = new JDesktopPane();
    getContentPane().add(desktop);


    menuBar = new JMenuBar();
    setJMenuBar(menuBar);
    JMenu about = new JMenu("About");
    menuBar.add(about);
    JMenu demos = new JMenu("Demos");
    menuBar.add(demos);
    JMenu audio = new JMenu("Audio");
    demos.add(audio);
    JMenu images = new JMenu("Images");
    demos.add(images);

    interactive = new JMenuItem("Interactive");
    interactive.addActionListener(this);
    audio.add(interactive);
    sequential = new JMenuItem("Sequential");
    sequential.addActionListener(this);
    audio.add(sequential);

    slideshow = new JMenuItem("Slide Show");
    slideshow.addActionListener(this);
    images.add(slideshow);
    zoomshow = new JMenuItem("Zoom Show");
    zoomshow.addActionListener(this);
    images.add(zoomshow);

    repaint();
  }


  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource() == slideshow)
    {
      System.out.println("slide");
    }
    if(e.getSource() == zoomshow)
    {
      System.out.println("zoom");
    }
  }

  /**
	Open the applet in a frame is called as such
  */
  public static void main(String[] args)
  {
    new MainFrame(new Demo());
  }

}
