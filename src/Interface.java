
// Austin Patel
// APCS
// Redwood High School
// 10/13/16
// Main.java

import java.awt.Toolkit;
import javax.swing.JFrame;

public class Interface extends JFrame
{

	private static final int WIDTH = 800, HEIGHT = 600;

	public Interface()
	{
		// Set up the frame's preferences
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png")));
		setSize(WIDTH, HEIGHT);
		setTitle("Grading Program");
		setVisible(true);
	}

}
