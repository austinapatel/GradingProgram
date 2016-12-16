
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 10/13/16
// Interface.java

package visuals;

import javax.swing.ImageIcon;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import database.DatabaseManager;
import database.Students;
import net.proteanit.sql.DbUtils;

/**Interface for the program.*/
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
		String[] columnNames = {"First Name", "Last Name", "Student ID", "Grade", "Notes"};
		
		Object[][] data = {{"Jason", "Morris", 110999, "B-", "No Note"}, {"Austin", "Patel", 110473, "C+", "No Note"},
					{"Achinthya", "Poduval", 110549, "D-", "No Note"}, {"Drew", "Carlisle", 110945, "F", "No Note"}};

		JTable table = new JTable();
		table.setModel(DbUtils.resultSetToTableModel(DatabaseManager.getTable(new Students())));
		///DbUtils.resultSetToTableModel();
		
		
		
		JScrollPane scrollPane = new JScrollPane(table); 
			scrollPane.setSize(500, 500);
			scrollPane.setLocation(0, 0);
			scrollPane.setVisible(true);
			
		add(scrollPane, BorderLayout.CENTER);
	}

}
