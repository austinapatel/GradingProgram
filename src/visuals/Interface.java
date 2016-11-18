
// Austin Patel & Jason Morris
// APCS
// Redwood High School
// 10/13/16
// Interface.java

package visuals;

import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")
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

		// Add elements to the screen
		add(new JButton("View Grades")
		{
			{
				setLocation(0, 0);
				setSize(150, 50);
				setVisible(true);
			}
		});

		add(new JButton("Add Assignment")
		{
			{
				setLocation(150, 0);
				setSize(150, 50);
				setVisible(true);
			}
		});
	}
	
}
