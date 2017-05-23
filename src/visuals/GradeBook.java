package visuals;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;

import javax.swing.*;

import database.DatabaseCellEditor;
import database.DatabaseManager;
import database.SqlBuilder;
import database.Table;
import database.TableProperties;

public class GradeBook extends InterfacePanel
{	
	private JList classList;
	private DefaultListModel listModel;
	private DatabaseJTable table;
	private JTable gradeTable = null;

	
	public static void main(String[] args) 
	{
		String test = SqlBuilder.Selection(new String[][] {{TableProperties.STUDENTS_TABLE_NAME, TableProperties.FIRST_NAME, TableProperties.LAST_NAME, TableProperties.STUDENT_ID}, {TableProperties.ENROLLMENTS_TABLE_NAME, TableProperties.ENROLLMENT_ID}, {TableProperties.ASSIGNMENTS_TABLE_NAME, TableProperties.ASSIGNMENTS_VALUE}, {TableProperties.GRADES_TABLE_NAME, TableProperties.GRADE_VALUE}}, new String[] {TableProperties.STUDENTS_TABLE_NAME});
		System.out.println(test);
	}
	
	
	public GradeBook ()
	{		
		initClassTable();
		add(table.getTableHeader());
		add(table, BorderLayout.NORTH);
	}
	
	public void initClassTable()
	{
		GradeBook thisInterface = this;

		table = new DatabaseJTable(TableProperties.COURSES_TABLE_NAME);
		table.setCellEditor(new DatabaseCellEditor());
		 table.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent me) {
		    	DatabaseJTable table2 =(DatabaseJTable) me.getSource();
		        Point p = me.getPoint();
		        int row = table2.rowAtPoint(p);
		        if (me.getClickCount() == 2) {
					String value = (table.getValueAt(table.getSelectedRow(), 0).toString());
					//String value = "1";
					//SELECT * FROM Students JOIN Enrollments ON Students.studentId = Enrollments.studentId WHERE Enrollments.courseId = "1"
					//ResultSet set = DatabaseManager.getJoinedTable(TableProperties.STUDENTS_TABLE_NAME, TableProperties.ENROLLMENTS_TABLE_NAME, new String[]{TableProperties.STUDENTS_TABLE_NAME + "." + TableProperties.FIRST_NAME, TableProperties.STUDENTS_TABLE_NAME + "." + TableProperties.LAST_NAME}, TableProperties.STUDENT_ID, TableProperties.STUDENT_ID, TableProperties.ENROLLMENTS_TABLE_NAME + "." + TableProperties.COURSE_ID, value);
					//Table joinedTable = new Table("StudentsTable", set);
					
					
					String select = SqlBuilder.Selection(new String[][] {{TableProperties.STUDENTS_TABLE_NAME, TableProperties.FIRST_NAME, TableProperties.LAST_NAME, TableProperties.STUDENT_ID}, {TableProperties.ENROLLMENTS_TABLE_NAME, TableProperties.ENROLLMENT_ID}, {TableProperties.ASSIGNMENTS_TABLE_NAME, TableProperties.ASSIGNMENTS_VALUE}, {TableProperties.GRADES_TABLE_NAME, TableProperties.GRADE_VALUE}}, new String[] {TableProperties.STUDENTS_TABLE_NAME});
					String join1 = SqlBuilder.getJoinString(SqlBuilder.JoinType.JOIN, TableProperties.ENROLLMENTS_TABLE_NAME, TableProperties.STUDENTS_TABLE_NAME, TableProperties.STUDENTS_TABLE_NAME);
					String join2 = SqlBuilder.getJoinString(SqlBuilder.JoinType.JOIN, TableProperties.ASSIGNMENTS_TABLE_NAME, TableProperties.ENROLLMENTS_TABLE_NAME, TableProperties.COURSE_ID);
					String join3 = SqlBuilder.getJoinString(SqlBuilder.JoinType.JOIN, TableProperties.GRADES_TABLE_NAME, TableProperties.STUDENTS_TABLE_NAME, TableProperties.STUDENT_ID);
					String join4 = SqlBuilder.getOperatorJoin(SqlBuilder.Operator.AND, SqlBuilder.JoinType.JOIN, TableProperties.ASSIGNMENTS_TABLE_NAME, TableProperties.GRADES_TABLE_NAME, TableProperties.ASSIGNMENT_ID);
					String filter = SqlBuilder.Filter(TableProperties.ENROLLMENTS_TABLE_NAME, TableProperties.COUNSELOR_ID, value);
					String sql = select + join1 + join2 + join3 + join4 + filter;
					
					// Remove the previous gradeTable if it exists
					if (gradeTable != null) {
						System.out.println("Removing");
						thisInterface.remove(gradeTable);
						thisInterface.remove(gradeTable.getTableHeader());
					}

					//gradeTable = new DatabaseJTable(joinedTable);

					thisInterface.add(gradeTable.getTableHeader());
					thisInterface.add(gradeTable);

					validate();
					repaint();
		        }
		    }
		});

		add(table.getTableHeader());
		add(table);
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