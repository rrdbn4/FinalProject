package code;
import javax.swing.JApplet;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class SlideShow extends JInternalFrame implements ActionListener
{
  public SlideShow()
  {
    super("Slide Show", true, true, true, true);
    setBounds(0, 0, 500, 400);
    setLayout(new BorderLayout());
  }

  public void actionPerformed(ActionEvent e)
  {

  }

  public void paint(Graphics g)
  {
    super.paint(g);
  }
}