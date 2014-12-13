package code;

import javax.swing.*;

public class Help extends JInternalFrame
{
	/**
	 * Text box that will contain the help information.
	 */
	private JTextArea textArea;
	/**
	 * Scroll Pane for the text area.
	 */
	JScrollPane scrollPane;
	/**
	 * The default width and height of the JInternalFrame
	 */
	private int width = 600;
	private int height = 500;
	
	public Help()
	{
		super("Help", true, true, true, true);
		setBounds(0, 10, width, height);
		
		textArea = new JTextArea("About Menu:\n"
				+ "\tAuthors - The names of each group member, their emails, and instructions on how to execute the application.\n"
				+ "\tProblem Description - Condensed text description of the assignment.\n"
				+ "\tHelp - This window, explains the functionality of this program.\n"
				+ "\tReferences - Citations of any outside help any group memeber may have used to complete this project.\n\n"
				+ "Demos Menu:\n"
				+ "\tSelect the Audio or Images menu\n\n"
				+ "\tAudio Menu:\n"
				+ "\t\tSelect the Interative option to play, stop or loop animal sounds.\n Select the Sequential option to hear animal sounds in a loop.\n"
				+ "\n\tImages Menu:\n"
				+ "\t\tSelect the SlideShow option to see animal pictures in a slideshow\nSelect the ZoomShow option to see an animal slideshow where the pictures zoom in and out.\n");
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
