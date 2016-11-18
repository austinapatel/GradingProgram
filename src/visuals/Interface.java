
// Austin Patel & Jason Morris
// APCS
// Redwood High School
// 10/13/16
// Interface.java

package visuals;


import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class Interface extends JFrame
{

	private static final int WIDTH = 800, HEIGHT = 600;
	ArrayList<Integer> averageYears = new ArrayList<>();

	public Interface()
	{
		// Set up the frame's preferences
		setIconImage(new ImageIcon("icon.png").getImage());
		setSize(WIDTH, HEIGHT);
		setTitle("Grading Program");

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
		
		
		JScrollPane scrollPane = new JScrollPane(new JButton("Apply Changes")
		{
			{
				setLocation(150, 0);
				setSize(150, 50);
				setVisible(true);
				
			}
		});
		
		add(scrollPane, BorderLayout.SOUTH);

		addTable();
		setVisible(true);
	}

	private void addTable()
	{
		String[] columnNames = {"First Name", "Last Name", "Sport", "# of Years", "Vegetarian"};

		Object[][] data = {{"Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false)},
					{"John", "Doe", "Rowing", new Integer(3), new Boolean(true)},
					{"Sue", "Black", "Knitting", new Integer(2), new Boolean(false)},
					{"Jane", "White", "Speed reading", new Integer(20), new Boolean(true)},
					{"Austin", "Patel", "Being Trash", new Integer(100), new Boolean(false)},
					{"Daniel", "Winston", "None", new Integer(0), new Boolean(false)},
					{"Jack", "IDK", "Club Penguin", new Integer(15), new Boolean(false)},
					{"Joe", "Brown", "Pool", new Integer(10), new Boolean(false)}};
		
		JTable table = new JTable(data, columnNames);

		JScrollPane scrollPane = new JScrollPane(table); 
			scrollPane.setSize(500, 500);
			scrollPane.setLocation(0, 0);
			scrollPane.setVisible(true);
			
		
		for (int count = 0; count < table.getRowCount(); count++){
			  averageYears.add((int)table.getValueAt(count, 0));
			}
		
		add(scrollPane, BorderLayout.CENTER);
	}

}
