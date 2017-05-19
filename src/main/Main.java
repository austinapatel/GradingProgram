
// Austin Patel & Jason Morris
// APCS
// Redwood High School
// 10/13/16
// Main.java

package main;


import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import database.DatabaseManager;
import database.Table;
import database.TableColumn;
import database.TableManager;
import database.TableProperties;
import database.ValueParameter;
import grading.GradeCalculator;
import visuals.BaseInterface;
import visuals.PasswordField;


/**
 * Driver class for entire program.
 */
public class Main {

	static PasswordField login;

	public static void main(String[] args) 
	{

      try
		{
			UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
			//com.jtattoo.plaf.acryl.AcrylLookAndFeel
			//com.jtattoo.plaf.smart.SmartLookAndFeel
		}
		catch (Exception error)
		{
			// TODO Auto-generated catch block
			error.printStackTrace();
		}



		new Main(args);
	}

	// note that field calls launchGUI
	public static void launchGUI(String secretKey) {
		DatabaseManager.init(secretKey);
		startInterface();
	}

	// note that field calls launchGUI
	public static void launchGUI(String url, String username, String password) {
		DatabaseManager.init(url, username, password);
		startInterface();
	}

	public Main(String[] args) {
		System.setProperty("awt.useSystemAAFontSettings", "on");
		System.setProperty("swing.aatext", "true");
		if (args.length == 1)
			launchGUI(args[0]);
		else if (args.length == 4) {
			TableManager.createTable = Boolean.parseBoolean(args[3]);
			launchGUI(args[0], args[1], args[2]);
		}
		else
			login = new PasswordField();	
	}

	private static void startInterface() {
		Main.setUpTables();
		new BaseInterface();

		GradeCalculator.getStudentGrades(1);
	}

	private static void setUpTables() {
		TableColumn[] studentsTableColumns = new TableColumn[] {
				new TableColumn(TableProperties.STUDENT_ID, "INT NOT NULL UNIQUE", null),
				new TableColumn(TableProperties.STUDENT_REDWOOD_ID, "INT NOT NULL UNIQUE", new ValueParameter() {
					{
						setValueRange(1, Integer.MAX_VALUE);
						setValueLengthRange(6, 6);
					}
				}), new TableColumn(TableProperties.FIRST_NAME, "VARCHAR(50) NOT NULL", null),
				new TableColumn(TableProperties.LAST_NAME, "VARCHAR(50) NOT NULL", null),
				new TableColumn(TableProperties.BIRTH_MONTH, "INT", null),
				new TableColumn(TableProperties.BIRTH_DAY, "INT", null),
				new TableColumn(TableProperties.BIRTH_YEAR, "INT", null),
				new TableColumn(TableProperties.GENDER, "CHAR(1)", new ValueParameter() {
					{
						setValueLengthRange(1, 1);
					}
				}), new TableColumn(TableProperties.GRADUATION_YEAR, "INT NOT NULL", null),
				new TableColumn(TableProperties.COUNSELOR_ID, "INT", null) };

		TableColumn[] coursesTableColumns = new TableColumn[] {
				new TableColumn(TableProperties.COURSE_ID, "INT NOT NULL UNIQUE", null),
				new TableColumn(TableProperties.NAME, "VARCHAR(50) NOT NULL", null),
				new TableColumn(TableProperties.PERIOD, "INT NOT NULL", null),
				new TableColumn(TableProperties.START_YEAR, "INT NOT NULL", null),
				new TableColumn(TableProperties.END_YEAR, "INT NOT NULL", null) };

		TableColumn[] assignmentsTableColumns = new TableColumn[] {
				new TableColumn(TableProperties.ASSIGNMENT_ID, "INT NOT NULL UNIQUE", null),

				new TableColumn(TableProperties.COURSE_ID, "INT NOT NULL", null
//						new ValueParameter() {
//					{
//						addSelector(TableProperties.COURSES_TABLE_NAME, TableProperties.NAME);
//					}
//				}
				),

				new TableColumn(TableProperties.ASSIGNMENT_DATE, "DATE NOT NULL", null),

				new TableColumn(TableProperties.ASSIGNMENTS_VALUE, "INT NOT NULL", new ValueParameter() {
					{
						setValueRange(0, Integer.MAX_VALUE);
					}
				}),

				new TableColumn(TableProperties.NAME, "VARCHAR(255) NOT NULL", null) };

		TableColumn[] enrollmentsTableColumns = new TableColumn[] {
				new TableColumn(TableProperties.ENROLLMENT_ID, "INT NOT NULL UNIQUE", null),
				new TableColumn(TableProperties.COURSE_ID, "INT NOT NULL", null),
				new TableColumn(TableProperties.STUDENT_ID, "INT NOT NULL", null
//						new ValueParameter() {
//					{
//						addSelector(TableProperties.STUDENTS_TABLE_NAME, TableProperties.FIRST_NAME,
//								TableProperties.LAST_NAME);
//					}
//				}
				) };

		TableColumn[] gradesTableColumns = new TableColumn[] {
				new TableColumn(TableProperties.GRADE_ID, "INT NOT NULL UNIQUE", null),
				new TableColumn(TableProperties.STUDENT_ID, "INT NOT NULL", null),
				new TableColumn(TableProperties.GRADE_VALUE, "DOUBLE NOT NULL", null),
				new TableColumn(TableProperties.ASSIGNMENT_ID, "INT NOT NULL", null) };

		TableColumn[] gradingScaleTableColumns = new TableColumn[] {
				new TableColumn(TableProperties.SCALE_ID, "INT NOT NULL UNIQUE", null),
				new TableColumn(TableProperties.SCALE_DATA, "VARCHAR(500) NOT NULL", null),
				new TableColumn(TableProperties.SCALE_DESCRIPTION, "VARCHAR(50) NOT NULL UNIQUE", null) };

		TableColumn[] counselorTableColumns = new TableColumn[] {
				new TableColumn(TableProperties.COUNSELOR_ID, "INT NOT NULL UNIQUE", null),
				new TableColumn(TableProperties.NAME, "VARCHAR(500) NOT NULL", null) };

		TableManager.addTable(new Table(database.TableProperties.SCALE_TABLE_NAME, gradingScaleTableColumns));

		TableManager.addTable(new Table(TableProperties.STUDENTS_TABLE_NAME, studentsTableColumns));
		TableManager.addTable(new Table(TableProperties.COURSES_TABLE_NAME, coursesTableColumns));
		TableManager.addTable(new Table(TableProperties.ASSIGNMENTS_TABLE_NAME, assignmentsTableColumns));
		TableManager.addTable(new Table(database.TableProperties.ENROLLMENTS_TABLE_NAME, enrollmentsTableColumns));
		TableManager.addTable(new Table(TableProperties.GRADES_TABLE_NAME, gradesTableColumns));
		TableManager.addTable(new Table(TableProperties.COUNSELORS_TABLE_NAME, counselorTableColumns));
	}

}