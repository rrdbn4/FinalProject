package code;
import javax.swing.JApplet;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
Demo is the driver class for the 
@author Robert Dunn, Holly Busken, Matt Linder
*/
public class Demo extends JApplet implements ActionListener
{ 
  JDesktopPane desktop;

  public void init()
  {
    setSize(800, 600);

    desktop = new JDesktopPane();
    getContentPane().add(desktop);
    repaint();
  }


  public void actionPerformed(ActionEvent e)
  {

  }

  /**
	Open the applet in a frame is called as such
  */
  public static void main(String[] args)
  {
    new MainFrame(new Demo());
  }

}