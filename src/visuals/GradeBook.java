package visuals;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import database.DatabaseCellEditor;
import database.DatabaseManager;
import database.Table;
import database.TableProperties;

public class GradeBook extends InterfacePanel
{	
	private JList classList;
	private DefaultListModel listModel;
	private DatabaseJTable table;
	private JScrollPane tablePane;

	public GradeBook ()
	{		
		initClassTable();
		add(table.getTableHeader());
		add(table, BorderLayout.NORTH);
	}
	
	public void initClassTable()
	{
		table = new DatabaseJTable(TableProperties.COURSES_TABLE_NAME);
		table.setCellEditor(new DatabaseCellEditor());
		 table.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent me) {
		    	DatabaseJTable table2 =(DatabaseJTable) me.getSource();
		        Point p = me.getPoint();
		        int row = table2.rowAtPoint(p);
		        if (me.getClickCount() == 2)
		        {
		        	{	
		        		String value = (table.getValueAt(table.getSelectedRow(), 0).toString());
		        		//String value = "1";
		        		//SELECT * FROM Students JOIN Enrollments ON Students.studentId = Enrollments.studentId WHERE Enrollments.courseId = "1"
		        		ResultSet set = DatabaseManager.getJoinedTable(TableProperties.STUDENTS_TABLE_NAME, TableProperties.ENROLLMENTS_TABLE_NAME, new String[]{TableProperties.STUDENTS_TABLE_NAME + "." + TableProperties.FIRST_NAME, TableProperties.STUDENTS_TABLE_NAME + "." + TableProperties.LAST_NAME}, TableProperties.STUDENT_ID, TableProperties.STUDENT_ID, TableProperties.ENROLLMENTS_TABLE_NAME + "." + TableProperties.COURSE_ID, value);
		        		Table studentTable = new Table("StudentsTable", set);
		        		DatabaseJTable table3 = new DatabaseJTable(studentTable);
		        		add(new JScrollPane(table3), BorderLayout.SOUTH);
		        		validate();
		        		repaint();
		        		//GradeCalculator.getGrades(1, "");
		        		
		        	}
		        }
		    }
		});
		tablePane = new JScrollPane(table);
	}
	private void initList() 
	{
		listModel = new DefaultListModel();
		listModel.addElement("hello");
		classList = new JList(listModel);
		classList.setBackground(getBackground());
		classList.setFont(new Font("Helvetica", Font.BOLD, 15));
		classList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JList list = (JList) evt.getSource();
				if (evt.getClickCount() == 2) 
				{
					System.out.println("Double Click ");
				} else if (evt.getClickCount() == 3)
				{

					int index = list.locationToIndex(evt.getPoint());
				}
			}
		});
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}