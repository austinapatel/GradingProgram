package visuals;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import database.DatabaseManager;
import database.Table;
import database.TableManager;
import database.TableProperties;
import grading.GradeCalculator;


public class ClassInterface extends JPanel implements Tab
{

	private JButton backButton;
	private DatabaseJTable table;
	private JScrollPane tablePane, tablePane2;
	private int currentCourse = 0;
	
	
	public ClassInterface() 
	{
		initButton();
		initTable();
		initPanel();
	}

	private void initButton()
	{
		backButton = new JButton("Back");
		backButton.setFont(new Font("Helvetica", Font.BOLD, 14));
		// backButton.setForeground(Color.BLUE);
		backButton.setFocusable(false);
		backButton.setVisible(true);
		
		backButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				
			remove(tablePane2);
			add(tablePane);
			remove(backButton);
			}
			
		});
	}
	
	
	private void initTable()
	{
		
	 table = new DatabaseJTable(TableProperties.COURSES_TABLE_NAME);
	 
	 tablePane = new JScrollPane(table);
	 table.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent me) {
		    	DatabaseJTable table2 =(DatabaseJTable) me.getSource();
		        Point p = me.getPoint();
		        int row = table2.rowAtPoint(p);
		        if (me.getClickCount() == 2)
		        {
		        	{	        	
		        		currentCourse = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
		        		displayClass(currentCourse);
		        	
		        	}
		        }
		    }
		});

	}
	
	
	private void displayClass(int id)
	{
		
		//ResultSet joinedResultSet = DatabaseManager.getJoinedTable(TableProperties.STUDENTS_TABLE_NAME, TableProperties.ENROLLMENTS_TABLE_NAME, new String[] {TableProperties.STUDENTS_TABLE_NAME +  "." + TableProperties.STUDENT_ID, TableProperties.STUDENTS_TABLE_NAME + "." + TableProperties.FIRST_NAME, TableProperties.STUDENTS_TABLE_NAME + "." + TableProperties.LAST_NAME}, TableProperties.STUDENT_ID, TableProperties.STUDENT_ID, TableProperties.ENROLLMENTS_TABLE_NAME + "." + TableProperties.COURSE_ID, "1");
		Table table2 = new Table("test join table", GradeCalculator.getGrades(1, TableProperties.STUDENTS_TABLE_NAME + "." + TableProperties.STUDENT_ID));
		DatabaseJTable jTable = new DatabaseJTable(table2);
		jTable.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent me) {
		    	DatabaseJTable table2 =(DatabaseJTable) me.getSource();
		        Point p = me.getPoint();
		        int row = table2.rowAtPoint(p);
		        if (me.getClickCount() == 2)
		        {
		        	{	        	
		        		//get all grads
		        	}
		        }
		    }
		});
		
		
		remove(tablePane);
		this.revalidate();
		add(backButton);
		tablePane2  = new JScrollPane(jTable);
		add(tablePane2);
		this.repaint();
		
		
		///SELECT * FROM Students JOIN Enrollments ON Students.studentId = Enrollments.studentId WHERE Enrollments.courseId = "1"
		
		//SELECT * FROM Enrollments JOIN Students ON Enrollments.studentId = Students.StudentId WHERE Enrollments.courseId = "1"

		
		}
	
	private void initPanel() 
	{
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(tablePane);
		this.setVisible(true);

	}

	@Override
	public String getTabName() {
		// TODO Auto-generated method stub
		return "Classes";
	}

	@Override
	public String getTabImage() {
		// TODO Auto-generated method stub
		return "grading.png";
	}

	@Override
	public void onTabSelected() {
	}


}
