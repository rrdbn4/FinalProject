package code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/** 
MainFrame is purely used for the ability to run an applet as an application
@author Robert Dunn, Holly Busken, Matt Lindner
*/
public class MainFrame extends JFrame
{
  /**
  1 Parameter Constructor
  @param demo   The Applet to be displyed in the application frame
  */
  public MainFrame(JApplet demo) 
  {
    super("Audio and Images");
    add(demo);
    demo.init();
    setSize(800,600); 
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  } 
}
