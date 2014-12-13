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
  The internal frame holding author information 
  */
  Authors authorsFrame;
  /**
  The internal frame holding the problem description
  */
  ProblemDescription descFrame;
  /**
  The internal Frame holding the interactive frame
  */
  Interactive interFrame;

  /**
  1 Parameter Constructor
  @param demo   The Applet to be displyed in the application frame
  */
  public MainFrame() 
  {
    super("Audio and Images");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(800,600); 
    setVisible(true);

    desktop = new JDesktopPane();
    add(desktop);
    

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
	
  	author = new JMenuItem("Authors");
  	author.addActionListener(this);
  	about.add(author);

  	problemDescription = new JMenuItem("Problem Description");
  	problemDescription.addActionListener(this);
  	about.add(problemDescription);	


  	validate();
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
    else if(e.getSource() == zoomshow)
    {
      if(zoomFrame == null || zoomFrame.isClosed())
      {
        zoomFrame = new ZoomShow();
        desktop.add(zoomFrame);
      }
      zoomFrame.setVisible(true);
      zoomFrame.toFront();
    }
    else if(e.getSource() == author)
    {
      if(authorsFrame == null || authorsFrame.isClosed())
      {
        authorsFrame = new Authors();
        desktop.add(authorsFrame);
      }
      authorsFrame.setVisible(true);
      authorsFrame.toFront();
    }	
    else if(e.getSource() == problemDescription)
    {
      if(descFrame == null || descFrame.isClosed())
      {
        descFrame = new ProblemDescription();
        desktop.add(descFrame);
      }
      descFrame.setVisible(true);
      descFrame.toFront();
    }	
    else if(e.getSource() == interactive)
    {
      if(interFrame == null || interFrame.isClosed())
      {
        interFrame = new Interactive();
        desktop.add(interFrame);
      }
      interFrame.setVisible(true);
      interFrame.toFront();
    }    
	
  }
}
