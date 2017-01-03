
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 10/13/16
// Main.java

package main;

import database.DatabaseManager;
import database.TableProperties;
import database.ValueParameter;
//import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import database.Table;
import database.TableColumn;
import database.TableManager;
import visuals.PasswordField;
import visuals.TableInterface;

/**Driver class for entire program.*/
public class Main
{

	public static void main(String[] args)
	{
		DatabaseManager.init();
		Main.setUpTables();
//		Main.showPasswordField();
		new TableInterface();
	}
	
	public static void setUpTables() {		
		TableColumn[] studentsTableColumns = new TableColumn[] {
				new TableColumn(TableProperties.ID, "INT NOT NULL UNIQUE", null),
				new TableColumn(TableProperties.STUDENT_ID, "INT NOT NULL UNIQUE", new ValueParameter(){{
					setValueRange(1, Integer.MAX_VALUE);
					setValueLengthRange(6, 6);
				}}),
				new TableColumn(TableProperties.FIRST_NAME, "VARCHAR(50) NOT NULL", null),
				new TableColumn(TableProperties.LAST_NAME, "VARCHAR(50) NOT NULL", null),
				new TableColumn(TableProperties.NOTES, "VARCHAR(255)", null),
				new TableColumn(TableProperties.GENDER, "CHAR(1)", new ValueParameter() {{
					setValueLengthRange(1, 1);
				}}),
				new TableColumn(TableProperties.GRADE_LEVEL, "INT NOT NULL", new ValueParameter() {{
					setValueRange(1, 12);
				}}) };
		
		TableColumn[] coursesTableColumns = new TableColumn[] {
				new TableColumn(TableProperties.ID, "INT NOT NULL UNIQUE", null),
				new TableColumn(TableProperties.NAME, "VARCHAR(50) NOT NULL", null),
				new TableColumn(TableProperties.PERIOD, "INT NOT NULL UNIQUE", null)};
		
		TableColumn[] assignmentsTableColumns = new TableColumn[] {
				new TableColumn(TableProperties.ID, "INT NOT NULL UNIQUE", null),
				
				new TableColumn(TableProperties.COURSE_ID, "INT NOT NULL", new ValueParameter() {{
					addSelector(TableProperties.COURSES_TABLE_NAME, TableProperties.NAME, TableProperties.ID);
				}}),
				
				new TableColumn(TableProperties.CATEGORY_ID, "INT NOT NULL", new ValueParameter() {{
					setValueRange(0, Integer.MAX_VALUE);
				}}),
				
				new TableColumn(TableProperties.VALUE, "INT NOT NULL", new ValueParameter() {{
					setValueRange(0, Integer.MAX_VALUE);
				}}),
				
				new TableColumn(TableProperties.NAME, "VARCHAR(255) NOT NULL", null)};

		TableColumn[] categoriesTableColumns = new TableColumn[] {
				new TableColumn(TableProperties.ID, "INT NOT NULL UNIQUE", null),
				new TableColumn(TableProperties.NAME, "VARCHAR(50) NOT NULL", null), 
				new TableColumn(TableProperties.WEIGHT, "INT NOT NULL", new ValueParameter() {{
					setValueRange(0, 100);
				}}),
				new TableColumn(TableProperties.COURSE_ID, "INT NOT NULL", new ValueParameter() {{
					addSelector(TableProperties.COURSES_TABLE_NAME, TableProperties.NAME, TableProperties.ID);
				}}),		
				};
		
		TableColumn[] enrollmentsTableColumns = new TableColumn[] {
				new TableColumn(TableProperties.ID, "INT NOT NULL UNIQUE", null),
				new TableColumn(TableProperties.YEAR, "INT NOT NULL UNIQUE", null), 
				new TableColumn(TableProperties.COURSE_ID, "INT NOT NULL", new ValueParameter() {{
					addSelector(TableProperties.COURSES_TABLE_NAME, TableProperties.NAME, TableProperties.ID);
				}}),
				new TableColumn(TableProperties.STUDENTS_ID, "INT NOT NULL UNIQUE", null)};
		
		TableManager.addTable(new Table(database.TableProperties.STUDENTS_TABLE_NAME, studentsTableColumns));
		TableManager.addTable(new Table(database.TableProperties.COURSES_TABLE_NAME, coursesTableColumns));
		TableManager.addTable(new Table(database.TableProperties.ASSIGNMENTS_TABLE_NAME, assignmentsTableColumns));
		TableManager.addTable(new Table(database.TableProperties.CATEGORIES_TABLE_NAME, categoriesTableColumns));
		TableManager.addTable(new Table(database.TableProperties.ENROLLMENTS_TABLE_NAME, enrollmentsTableColumns));
	}
	
	private static void showPasswordField() {
		PasswordField passTest = new PasswordField();
		//passTest.setToOpen();
	}

}
