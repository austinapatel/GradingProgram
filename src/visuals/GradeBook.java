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
	private Table studentsTable, assignmentsTable, enrollmentsTable, gradesTable;

	public GradeBook ()
	{		
		initClassTable();
		initClassPicker();
		loadTables();
	}

	private void initClassPicker() {
		add(table.getTableHeader());
		add(table, BorderLayout.NORTH);
	}

	private void loadTables() {
		studentsTable = TableManager.getTable(TableProperties.STUDENTS_TABLE_NAME);
		assignmentsTable = TableManager.getTable(TableProperties.ASSIGNMENTS_TABLE_NAME);
		enrollmentsTable = TableManager.getTable(TableProperties.ENROLLMENTS_TABLE_NAME);
		gradesTable = TableManager.getTable(TableProperties.GRADES_TABLE_NAME);
	}

	@Override
	public void onLayoutOpened() {

	}

	public void initClassTable()
	{
		table = new DatabaseJTable(TableProperties.COURSES_TABLE_NAME);
		table.setCellEditor(new DatabaseCellEditor());
		table.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent me) {
				if (me.getClickCount() == 2) {
					int courseID = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());

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

					// Get the data and put it into the row
					ArrayList<ArrayList<String>> rows = new ArrayList<>();

					for (int i = 0; i < studentIds.size(); i++) {
						int studentId = studentIds.get(i);

						ArrayList<String> currentRow = new ArrayList<>();
						// Convert a student id to a name
						String firstName = DataTypeManager.toStringArrayList(studentsTable.getSomeFromColumn(TableProperties.FIRST_NAME, TableProperties.STUDENT_ID, studentId + "")).get(0);
						String lastName = DataTypeManager.toStringArrayList(studentsTable.getSomeFromColumn(TableProperties.LAST_NAME, TableProperties.STUDENT_ID, studentId + "")).get(0);

						currentRow.add(firstName + " " + lastName);

						// Now add each of the student's scores to the row
						for (int j = 0; j < assignmentIds.size(); j++) {
							int assignmentId = assignmentIds.get(j);
							ArrayList<Integer> scores = DataTypeManager.toIntegerArrayList(gradesTable.getSomeFromColummn(TableProperties.GRADE_ID, new Search(TableProperties.STUDENT_ID, studentId + ""), new Search(TableProperties.ASSIGNMENT_ID, assignmentId + "")));

//							ArrayList<Integer> returnedScores = DataTypeManager.toIntegerArrayList(gradesTable.getSomeFromColumn(TableProperties.GRADE_VALUE, TableProperties.ASSIGNMENT_ID, assignmentIds.get(i) + ""));
						}

						rows.add(currentRow);
					}

					String[][] rowsFormatted = new String[rows.size()][rows.get(0).size()];

					for (int x = 0; x < rowsFormatted.length; x++) {
						for (int y = 0; y < rowsFormatted[x].length; y++) {
							rowsFormatted[x][y] = rows.get(x).get(y);
						}
					}

					ArrayList<String> columns = new ArrayList<>();
					columns.add("Student Name");

					for (int i = 0; i < assignmentIds.size(); i++) {
						String assignmentName = DataTypeManager.toStringArrayList(assignmentsTable.getSomeFromColumn(TableProperties.NAME, TableProperties.ASSIGNMENT_ID, assignmentIds.get(i) + "")).get(0);
						int assignmentValue = DataTypeManager.toIntegerArrayList(assignmentsTable.getSomeFromColumn(TableProperties.ASSIGNMENTS_VALUE, TableProperties.ASSIGNMENT_ID, assignmentIds.get(i) + "")).get(0);

						columns.add(assignmentName + " (" + assignmentValue + ")");
					}

					String columnsFormatted[] = new String[columns.size()];

					for (int i = 0; i < columnsFormatted.length; i++)
						columnsFormatted[i] = columns.get(i);

					DefaultTableModel model = new DefaultTableModel(rowsFormatted, columnsFormatted);

					JTable gradesTable = new JTable(model);

					add(gradesTable.getTableHeader());
					add(gradesTable);

					//String value = "1";
					//SELECT * FROM Students JOIN Enrollments ON Students.studentId = Enrollments.studentId WHERE Enrollments.courseId = "1"
					//ResultSet set = DatabaseManager.getJoinedTable(TableProperties.STUDENTS_TABLE_NAME, TableProperties.ENROLLMENTS_TABLE_NAME, new String[]{TableProperties.STUDENTS_TABLE_NAME + "." + TableProperties.FIRST_NAME, TableProperties.STUDENTS_TABLE_NAME + "." + TableProperties.LAST_NAME}, TableProperties.STUDENT_ID, TableProperties.STUDENT_ID, TableProperties.ENROLLMENTS_TABLE_NAME + "." + TableProperties.COURSE_ID, value);
					//Table joinedTable = new Table("StudentsTable", set);
					
					
//					String select = SqlBuilder.Selection(new String[][] {{TableProperties.STUDENTS_TABLE_NAME, TableProperties.FIRST_NAME, TableProperties.LAST_NAME, TableProperties.STUDENT_ID}, {TableProperties.ENROLLMENTS_TABLE_NAME, TableProperties.ENROLLMENT_ID}, {TableProperties.ASSIGNMENTS_TABLE_NAME, TableProperties.ASSIGNMENTS_VALUE}, {TableProperties.GRADES_TABLE_NAME, TableProperties.GRADE_VALUE}}, new String[] {TableProperties.STUDENTS_TABLE_NAME});
//					System.out.println("DASFASDSD SAFDASDFASF" + select);
//					String join1 = SqlBuilder.getJoinString(SqlBuilder.JoinType.JOIN, TableProperties.ENROLLMENTS_TABLE_NAME, TableProperties.STUDENTS_TABLE_NAME, TableProperties.STUDENT_ID);
//					String join2 = SqlBuilder.getJoinString(SqlBuilder.JoinType.JOIN, TableProperties.ASSIGNMENTS_TABLE_NAME, TableProperties.ENROLLMENTS_TABLE_NAME, TableProperties.COURSE_ID);
//					String join3 = SqlBuilder.getJoinString(SqlBuilder.JoinType.JOIN, TableProperties.GRADES_TABLE_NAME, TableProperties.STUDENTS_TABLE_NAME, TableProperties.STUDENT_ID);
//					String join4 = SqlBuilder.getOperatorJoin(SqlBuilder.Operator.AND, SqlBuilder.JoinType.JOIN, TableProperties.ASSIGNMENTS_TABLE_NAME, TableProperties.GRADES_TABLE_NAME, TableProperties.ASSIGNMENT_ID);
//					String filter = SqlBuilder.Filter(TableProperties.ENROLLMENTS_TABLE_NAME, TableProperties.COURSE_ID, value);
//					String sql = select + join1 + join2 + join3 + join4 + filter;
//					ResultSet set = DatabaseManager.executeSqlStatement(sql);
//
//					Table joinedTable = new Table("GradeBook", set);
//
//					// Remove the previous gradeTable if it exists
//					if (gradeTable != null) {
//						System.out.println("Removing");
//						thisInterface.remove(gradeTable);
//						thisInterface.remove(gradeTable.getTableHeader());
//					}
//
//
//					DatabaseTableModel databaseTableModel = new DatabaseTableModel();
//					databaseTableModel.setTable(joinedTable);
//
//					gradeTable = new JTable(databaseTableModel);
//
//
//					thisInterface.add(gradeTable.getTableHeader());
//					thisInterface.add(gradeTable);

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