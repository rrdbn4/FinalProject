package code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/** 
MainFrame is purely used for the ability to run an applet as an application
@author Robert Dunn, Holly Busken, Matt Lindner
*/
public class MainFrame extends JFrame implements ActionListener
{

  /**
  The container for the internal frames
  */
  JDesktopPane desktop;  
  /**
  The applet's menu bar
  */
  JMenuBar menuBar;
  /**
  The menu items associated with the about section in the menu
  */
  JMenuItem author, problemDescription, references, help;
  /**
  The menu item associated with the demo section in the menu
  */
  JMenuItem interactive, sequential, slideshow, zoomshow;

  /**
  The internal frame holding the slideshow demo
  */
  SlideShow slideFrame;
  /**
  The internal frame holding the zoomshow demo
  */
  ZoomShow zoomFrame;


  /**
  1 Parameter Constructor
  @param demo   The Applet to be displyed in the application frame
  */
  public MainFrame() 
  {
    super("Audio and Images");
    setSize(800,600); 
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);

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
  
    /**
  The click listener for the menu items in the applet
  @param e The passed in action event
  */
  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource() == slideshow)
    {
      if(slideFrame == null || slideFrame.isClosed())
      {
        slideFrame = new SlideShow();
        desktop.add(slideFrame);
      }
      slideFrame.setVisible(true);
      slideFrame.toFront();
    }
    if(e.getSource() == zoomshow)
    {
      if(zoomFrame == null || zoomFrame.isClosed())
      {
        zoomFrame = new ZoomShow();
        desktop.add(zoomFrame);
      }
      zoomFrame.setVisible(true);
      zoomFrame.toFront();
    }
  }
}
