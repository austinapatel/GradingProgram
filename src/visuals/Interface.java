
// Austin Patel & Jason Morris
// APCS
// Redwood High School
// 10/13/16
// Interface.java

package visuals;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class Interface extends JFrame
{

	private static final int WIDTH = 800, HEIGHT = 600;

	public Interface()
	{
		// Set up the frame's preferences
		setIconImage(new ImageIcon("icon.png").getImage());
		setSize(WIDTH, HEIGHT);
		setTitle("Grading Program");
		setVisible(true);

		// Add elements to the screen
//		add(new JButton("View Grades")
//		{
//			{
//				setLocation(0, 0);
//				setSize(150, 50);
//				setVisible(true);
//			}
//		});
//
//		add(new JButton("Add Assignment")
//		{
//			{
//				setLocation(150, 0);
//				setSize(150, 50);
//				setVisible(true);
//			}
//		});

		addTable();
	}

	private void addTable()
	{
		String[] columnNames = {"First Name", "Last Name", "Sport", "# of Years", "Vegetarian"};

		Object[][] data = {{"Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false)},
					{"John", "Doe", "Rowing", new Integer(3), new Boolean(true)},
					{"Sue", "Black", "Knitting", new Integer(2), new Boolean(false)},
					{"Jane", "White", "Speed reading", new Integer(20), new Boolean(true)},
					{"Joe", "Brown", "Pool", new Integer(10), new Boolean(false)}};

		add(new JTable(data, columnNames)
		{
			{
				setSize(500,500);
				setLocation(0,0);
				setVisible(true);
			}
		});
	}

}
