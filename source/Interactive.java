package code;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import java.net.URL;
import java.io.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.event.ListSelectionListener;

public class Interactive extends JInternalFrame implements ActionListener, ListSelectionListener
{
  
  JButton playButton, stopButton, loopButton;
  JList sounds;
  String sound;
  String action;
  Clip clip;
  
  
  public Interactive()
  {
    super("Interactive Audio",true,true,true,true);
	
	File audioDir = new File((getClass().getResource("../audio/").toString()).replace("file:/", ""));
	if(audioDir == null)
	  audioDir = new File((getClass().getResource("..\audio\").toString()).replace("file:/", ""));
	String[] soundNames = audioDir.list();
	for(int i=0;i<soundNames.length;i++)
	  soundNames[i]=soundNames[i].replace(".wav","");
	//String[] soundNames ={"bird","cat","cricket","dolphin","donkey","elephant","hawk","monkey","pig","rooster"};
	sounds = new JList(soundNames);
	sounds.setVisibleRowCount(10);
	sounds.addListSelectionListener(this);
	Border border = new LineBorder(Color.BLACK,2);
	sounds.setSize(new Dimension(100,100));

	JPanel listPane = new JPanel();

	JLabel label = new JLabel("Select an animal");
	listPane.add(label);
	sounds.setBorder(border);
	listPane.add(sounds);

	JPanel buttonPane=new JPanel();
	
	playButton = new JButton("Play");
	playButton.addActionListener(this);
	buttonPane.add(playButton);
	
	stopButton = new JButton("Stop");
	stopButton.addActionListener(this);
	buttonPane.add(stopButton);
	
	loopButton = new JButton("Loop");
	loopButton.addActionListener(this);	
	buttonPane.add(loopButton);
	
	BoxLayout layout = new BoxLayout(listPane, BoxLayout.PAGE_AXIS);
	listPane.setLayout(layout);
	add(listPane);
	
	add(buttonPane);
  
    setSize(500,300); 
	setLayout(new FlowLayout());
    setVisible(true);
	action=" ";
  }

  public void valueChanged(ListSelectionEvent event)
  {
    sound=(String)sounds.getSelectedValue();
	if(action.equals(" "));
	  action="Selected";
	repaint();
  }
  
  public void actionPerformed(ActionEvent event)
  {
    if(clip == null && sound!=null)
	{
      createClip();
	}
  
    if(clip != null)
	{
      if( event.getSource()==playButton)
	  {
  	    if(clip.isActive())
	      clip.stop();
	    action="Playing";
        createClip();
	    clip.setFramePosition(0);
	    clip.start();
	    }
	
	    else if(event.getSource()==stopButton)
	    {
		  action="Stopped";
  	      clip.stop();
		  repaint();
  	    }
	
	  else if(event.getSource()==loopButton)
	  {
	    
		action="Looping";
  	    if(clip.isActive())
	      clip.stop();
	    action="Playing";
        createClip();
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	    clip.setFramePosition(0);		
		repaint();
	  }
	}
  }
  
  public void createClip()
  {
	    try{
          URL url = this.getClass().getResource("../audio/"+sound+".wav");
		  AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
		  clip = AudioSystem.getClip();
		  clip.open(audioIn);
	    }
	    catch(UnsupportedAudioFileException e) 
		{ System.out.println("Unsupported Audio");}
	    catch(IOException e) 
		{ System.out.println("Cannot find File");}
	    catch(LineUnavailableException e) 
		{ System.out.println("Line Unavailable");} 
		repaint();
		
  }
  
  public void paint(Graphics g)
  {
    super.paint(g);
	if(sound != null)
	{
	  int x = getWidth()/2;
	  g.drawString(action+": " + sound, getWidth()/2,170);
	  Image image = new ImageIcon(getClass().getResource("../img/"+sound+".jpg")).getImage();
	  g.drawImage(image,x,175, 100,100,this);
	}
  }
}
