package visuals;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Array;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import database.*;

public class GradeBook extends InterfacePanel
{	
	private JList classList;
	private DefaultListModel listModel;
	private DatabaseJTable table;
	private JTable gradeTable = null;

	public GradeBook ()
	{		
		initClassTable();
		add(table.getTableHeader());
		add(table, BorderLayout.NORTH);
	}

	@Override
	public void onLayoutOpened() {

	}

	public void initClassTable()
	{
		GradeBook thisInterface = this;

		table = new DatabaseJTable(TableProperties.COURSES_TABLE_NAME);
		table.setCellEditor(new DatabaseCellEditor());
		 table.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent me) {
				if (me.getClickCount() == 2) {
					Point p = me.getPoint();
					DatabaseJTable table2 =(DatabaseJTable) me.getSource();

					int row = table2.rowAtPoint(p);

					int courseID = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
					String value = courseID + "";


					Table studentsTable = TableManager.getTable(TableProperties.STUDENTS_TABLE_NAME);
					Table assignmentsTable = TableManager.getTable(TableProperties.ASSIGNMENTS_TABLE_NAME);
					Table enrollmentsTable = TableManager.getTable(TableProperties.ENROLLMENTS_TABLE_NAME);

					ArrayList<Integer> studentIds = DataTypeManager.toIntegerArrayList(enrollmentsTable.getSomeFromColumn(TableProperties.STUDENT_ID, TableProperties.COURSE_ID, courseID + ""));
					ArrayList<Integer> assignmentIds = DataTypeManager.toIntegerArrayList(assignmentsTable.getSomeFromColumn(TableProperties.ASSIGNMENT_ID, TableProperties.COURSE_ID, courseID + ""));

					System.out.println("student ids:");
					for (int studentID : studentIds) {
						System.out.println(studentID);
					}

					System.out.println("assignment ids");
					for (int assignmentId : assignmentIds) {
						System.out.println(assignmentId);
					}

					Object rows[][] = { { "AMZN", "Amazon", "67 9/16" },
							{ "AOL", "America Online", "68 3/4" },
							{ "BOUT", "About.com", "56 3/8" },
							{ "CDNW", "CDnow", "4 7/16" },
							{ "DCLK", "DoubleClick", "87 3/16" },
							{ "EBAY", "eBay", "180 7/8" },
							{ "EWBX", "EarthWeb", "18 1/4" },
							{ "MKTW", "MarketWatch", "29" },
							{ "TGLO", "Theglobe.com", "4 15/16" },
							{ "YHOO", "Yahoo!", "151 1/8" } };
					Object columns[] = { "Symbol", "Name", "Price" };

					DefaultTableModel model = new DefaultTableModel(rows, columns);

					JTable gradesTable = new JTable(model);

					add(gradesTable);

					//String value = "1";
					//SELECT * FROM Students JOIN Enrollments ON Students.studentId = Enrollments.studentId WHERE Enrollments.courseId = "1"
					//ResultSet set = DatabaseManager.getJoinedTable(TableProperties.STUDENTS_TABLE_NAME, TableProperties.ENROLLMENTS_TABLE_NAME, new String[]{TableProperties.STUDENTS_TABLE_NAME + "." + TableProperties.FIRST_NAME, TableProperties.STUDENTS_TABLE_NAME + "." + TableProperties.LAST_NAME}, TableProperties.STUDENT_ID, TableProperties.STUDENT_ID, TableProperties.ENROLLMENTS_TABLE_NAME + "." + TableProperties.COURSE_ID, value);
					//Table joinedTable = new Table("StudentsTable", set);
					
					
					String select = SqlBuilder.Selection(new String[][] {{TableProperties.STUDENTS_TABLE_NAME, TableProperties.FIRST_NAME, TableProperties.LAST_NAME, TableProperties.STUDENT_ID}, {TableProperties.ENROLLMENTS_TABLE_NAME, TableProperties.ENROLLMENT_ID}, {TableProperties.ASSIGNMENTS_TABLE_NAME, TableProperties.ASSIGNMENTS_VALUE}, {TableProperties.GRADES_TABLE_NAME, TableProperties.GRADE_VALUE}}, new String[] {TableProperties.STUDENTS_TABLE_NAME});
					System.out.println("DASFASDSD SAFDASDFASF" + select);
					String join1 = SqlBuilder.getJoinString(SqlBuilder.JoinType.JOIN, TableProperties.ENROLLMENTS_TABLE_NAME, TableProperties.STUDENTS_TABLE_NAME, TableProperties.STUDENT_ID);
					String join2 = SqlBuilder.getJoinString(SqlBuilder.JoinType.JOIN, TableProperties.ASSIGNMENTS_TABLE_NAME, TableProperties.ENROLLMENTS_TABLE_NAME, TableProperties.COURSE_ID);
					String join3 = SqlBuilder.getJoinString(SqlBuilder.JoinType.JOIN, TableProperties.GRADES_TABLE_NAME, TableProperties.STUDENTS_TABLE_NAME, TableProperties.STUDENT_ID);
					String join4 = SqlBuilder.getOperatorJoin(SqlBuilder.Operator.AND, SqlBuilder.JoinType.JOIN, TableProperties.ASSIGNMENTS_TABLE_NAME, TableProperties.GRADES_TABLE_NAME, TableProperties.ASSIGNMENT_ID);
					String filter = SqlBuilder.Filter(TableProperties.ENROLLMENTS_TABLE_NAME, TableProperties.COURSE_ID, value);
					String sql = select + join1 + join2 + join3 + join4 + filter;
					ResultSet set = DatabaseManager.executeSqlStatement(sql);
					
					Table joinedTable = new Table("GradeBook", set);
					
					// Remove the previous gradeTable if it exists
					if (gradeTable != null) {
						System.out.println("Removing");
						thisInterface.remove(gradeTable);
						thisInterface.remove(gradeTable.getTableHeader());
					}

					gradeTable = new DatabaseJTable(joinedTable);

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