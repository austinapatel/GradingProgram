
// Austin Patel & Jason Morris
// APCS
// Redwood High School
// 10/13/16
// Interface.java

package visuals;

import javax.swing.ImageIcon;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
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
	}

	private void addTable()
	{
		String[] columnNames = {"First Name", "Last Name", "Gender", "Student ID", "Notes"};

		Object[][] data = {{"Jason", "Morris", true, 110999, "No Note"}, {"Austin", "Patel", true, 110473, "No Note"},
					{"Achinthya", "Poduval", true, 110549, "No Note"}, {"Drew", "Carlisle", false, 110945, "No Note"}};

		JScrollPane scrollPane = new JScrollPane((new JTable(data, columnNames)
		{
			{
				setSize(500, 500);
				setLocation(0, 0);
				setVisible(true);
			}
		}));

		add(scrollPane, BorderLayout.CENTER);
		setSize(300, 150);
		setVisible(true);
	}

}
