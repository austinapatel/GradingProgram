
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 10/13/16
// Main.java

package main;

import database.DatabaseManager;
import database.TableProperties;
import database.ValueParameter;
import grading.GradingScale;
//import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import database.Table;
import database.TableColumn;
import database.TableManager;
import visuals.PasswordField;
import visuals.TableInterface;

/** Driver class for entire program. */
public class Main {

	static PasswordField login;

	public static void main(String[] args) {

		if (args.length == 1)
			launchGUI(args[0]);
		else if (args.length > 1)
			launchGUI(args[0], args[1], args[2]);
		else
			login = new PasswordField();
	}

	// note that field calls launchGUI
	public static void launchGUI(String secretKey) {
		DatabaseManager.init(secretKey);
		Main.setUpTables();
		new TableInterface();
	}

	// note that field calls launchGUI
	public static void launchGUI(String url, String username, String password) {
		System.out.println(url + " " + username + " " + password);
		DatabaseManager.init(url, username, password);
		Main.setUpTables();
		new TableInterface();

		double value[] = {98, 93 ,90, 87, 83, 80, 77, 73, 70, 67, 63, 60, 0};

		GradingScale test = new GradingScale(value);
	}

	private static void setUpTables() {
		TableColumn[] studentsTableColumns = new TableColumn[] {
				new TableColumn(TableProperties.STUDENT_ID, "INT NOT NULL UNIQUE",
						null),
				new TableColumn(TableProperties.STUDENT_REDWOOD_ID,
						"INT NOT NULL", new ValueParameter() {
							{
								setValueRange(1, Integer.MAX_VALUE);
								setValueLengthRange(6, 6);
							}
						}),
				new TableColumn(TableProperties.FIRST_NAME,
						"VARCHAR(50) NOT NULL", null),
				new TableColumn(TableProperties.LAST_NAME,
						"VARCHAR(50) NOT NULL", null),
				new TableColumn(TableProperties.NOTES, "VARCHAR(255)", null),
				new TableColumn(TableProperties.GENDER, "CHAR(1)",
						new ValueParameter() {
							{
								setValueLengthRange(1, 1);
							}
						}),
				new TableColumn(TableProperties.GRADE_LEVEL, "INT NOT NULL",
						new ValueParameter() {
							{
								setValueRange(1, 12);
							}
						}) };

		TableColumn[] coursesTableColumns = new TableColumn[] {
				new TableColumn(TableProperties.COURSE_ID, "INT NOT NULL UNIQUE",
						null),
				new TableColumn(TableProperties.NAME, "VARCHAR(50) NOT NULL",
						null),
				new TableColumn(TableProperties.PERIOD, "INT NOT NULL",
						null),
				new TableColumn(TableProperties.START_YEAR, "INT NOT NULL", null),
				new TableColumn(TableProperties.END_YEAR, "INT NOT NULL", null)};

		TableColumn[] assignmentsTableColumns = new TableColumn[] {
				new TableColumn(TableProperties.ASSIGNMENT_ID, "INT NOT NULL UNIQUE",
						null),

				new TableColumn(TableProperties.COURSE_ID, "INT NOT NULL",
						new ValueParameter() {
							{
								addSelector(TableProperties.COURSES_TABLE_NAME,
										TableProperties.NAME);
							}
						}),

				new TableColumn(TableProperties.CATEGORY_ID, "INT NOT NULL",
						new ValueParameter() {
							{
								setValueRange(0, Integer.MAX_VALUE);
							}
						}),

				new TableColumn(TableProperties.VALUE, "INT NOT NULL",
						new ValueParameter() {
							{
								setValueRange(0, Integer.MAX_VALUE);
							}
						}),

				new TableColumn(TableProperties.NAME, "VARCHAR(255) NOT NULL",
						null) };

		TableColumn[] categoriesTableColumns = new TableColumn[] {
				new TableColumn(TableProperties.CATEGORY_ID, "INT NOT NULL UNIQUE",
						null),
				new TableColumn(TableProperties.NAME, "VARCHAR(50) NOT NULL",
						null),
				new TableColumn(TableProperties.WEIGHT, "INT NOT NULL",
						new ValueParameter() {
							{
								setValueRange(0, 100);
							}
						}),
				new TableColumn(TableProperties.COURSE_ID, "INT NOT NULL",
						new ValueParameter() {
							{
								addSelector(TableProperties.COURSES_TABLE_NAME,
										TableProperties.NAME);
							}
						}), };

		TableColumn[] enrollmentsTableColumns = new TableColumn[] {
				new TableColumn(TableProperties.ENTROLLMENT_ID, "INT NOT NULL UNIQUE",
						null),
				new TableColumn(TableProperties.COURSE_ID, "INT NOT NULL",
						new ValueParameter() {
							{
								addSelector(TableProperties.COURSES_TABLE_NAME, true,
										TableProperties.PERIOD,
										" peroid",
										TableProperties.NAME,
										TableProperties.START_YEAR,
										"-",
										TableProperties.END_YEAR);
							}
						}),
				new TableColumn(TableProperties.STUDENT_ID, "INT NOT NULL",
						new ValueParameter() {
							{
								addSelector(TableProperties.STUDENTS_TABLE_NAME,
										TableProperties.FIRST_NAME,
										TableProperties.LAST_NAME);
							}
						}) };
		
		
		TableColumn[] gradesTableColumns = new TableColumn[] {
				new TableColumn(TableProperties.GRADE_ID, "INT NOT NULL UNIQUE", null),	
				new TableColumn(TableProperties.STUDENT_ID, "INT NOT NULL", null),
				new TableColumn(TableProperties.GRADE_VALUE, "DOUBLE NOT NULL", null),
				new TableColumn(TableProperties.ASSIGNMENT_ID, "INT NOT NULL", null)};

		TableColumn[] gradingScales = new TableColumn[] {
				new TableColumn(TableProperties.SCALE_ID, "INT NOT NULL UNIQUE", null),	
				new TableColumn(TableProperties.SCALE_DATA, "VARCHAR(500) NOT NULL", null)};
		
		TableManager.addTable(new Table(database.TableProperties.SCALE_TABLE_NAME, gradingScales));
						

		TableManager.addTable(
				new Table(database.TableProperties.STUDENTS_TABLE_NAME,
						studentsTableColumns));
		TableManager
				.addTable(new Table(database.TableProperties.COURSES_TABLE_NAME,
						coursesTableColumns));
		TableManager.addTable(
				new Table(database.TableProperties.ASSIGNMENTS_TABLE_NAME,
						assignmentsTableColumns));
		TableManager.addTable(
				new Table(database.TableProperties.CATEGORIES_TABLE_NAME,
						categoriesTableColumns));
		TableManager.addTable(
				new Table(database.TableProperties.ENROLLMENTS_TABLE_NAME,
						enrollmentsTableColumns));
		TableManager.addTable(new Table(database.TableProperties.GRADES_TABLE_NAME, gradesTableColumns));
	}
}
