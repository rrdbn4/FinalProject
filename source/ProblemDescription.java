package code;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class ProblemDescription extends JInternalFrame
{
	/**
	 * Text box that will contain group project problem description.
	 */
	private JTextArea textArea;
	/**
	 * Scroll Pane for the text area.
	 */
	JScrollPane scrollPane;
	/**
	 * The default width and height of the JInternalFrame
	 */
	private int width = 700;
	private int height = 500;
	
	public ProblemDescription()
	{
		super("Problem Description", true, true, true, true);
		setBounds(20, 20, width, height);
		
		String output="\tWrite a program using new threads";
		output+="(Do not use old threads to get credit for the project)";
		output+="as discussed in the class for Algorithm Visualization. ";
		output+="Your program should be menu driven as seen in the demos and previous";
		output+="assignments. Your program uses JInternalFrames (full functionality)";
        output+="and JPanels for proper display of the program components to do all the work."; 
		output+=" The JInternalFrames and JPanels may be placed in JApplet and/or JFrame.";
		output+=" The program should be executable as an applet or an application.";
		output+=" Use package command for packaging the appropriate files.";
		output+=" Jar the whole package. Document the code using java style code.";
		output+=" Every class, function and variable has javadoc descriptive comments.";
		output+=" Make your program as fancy as you can.";	
		output+="\n\nSelect your favorite 10 images and 10 audioclips whose sound matched the image";
		output+="\nMenus\nProject: Combine Functionality images and audios:\n";
		output+="\nMenus:\n\tAbout\n\t\tAuthor\n\t\tProblem\n\t\tReferences\n\t\tHelp";
		output+="\n\n\tDemos\n\t\tAudio\n\t\t\tInteractive\n";
		output+="allow user to select a clip. Use buttons to control play, stop, loop.";
		output+="\n\n\t\t\tSequential\nCreate an internal frame for automatically selecting clips";
        output+="sequentially from the audio directory and playing them in that order in an";
        output+="infinite loop. While the clip is playing, display an image related to the clip";
		output+="\n\n\tImages\n\t\tSlideShow\nDisplay images all scaled";
		output+="to same dimensions and one after the other from right to left.";
		output+="\n\n\t\tZoomShow\nDisplay images all scaled from min (say 10x10) to maximum";
		output+=" (windowsize) dimension, then back to min before starting the next image.";
		textArea = new JTextArea(output);
		
		/**
		 * The problem description is read only.
		 */
		textArea.setEditable(false);
		/**
		 * If the width of the text area is not big enough to accommodate each sentence, the words will wrap to the next line.
		 */
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		
		/**
		 * Create the scroll pane object, add the text area
		 */
		scrollPane = new JScrollPane(textArea);
		
		/**
		 * Add the scroll pane to the JPanel
		 */
		add(scrollPane);
	}
}
