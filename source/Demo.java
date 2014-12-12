package code;
import javax.swing.JApplet;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
Demo is the driver class for the project
@author Robert Dunn, Holly Busken, Matt Lindner
*/
public class Demo extends JApplet
{ 
  /**
  The starting method for the applet, similar to main()
  Everything is setup and initialized here
  */
  public void init()
  {
	new MainFrame();
  }

  /**
	Open the applet in a frame is the need arises 
  */
  public static void main(String[] args)
  {
    new MainFrame();
  }

}
