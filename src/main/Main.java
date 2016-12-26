
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
				new TableColumn(TableProperties.STUDENT_ID, "INT NOT NULL UNIQUE", new ValueParameter(){{
					setValueRange(1, Integer.MAX_VALUE);
					setValueLengthRange(6, 6);
				}}),
				new TableColumn(TableProperties.FIRST_NAME, "VARCHAR(50) NOT NULL", new ValueParameter() {{
					setValueLengthRange(0, 50);
				}}),
				new TableColumn(TableProperties.LAST_NAME, "VARCHAR(50) NOT NULL", new ValueParameter() {{
					setValueLengthRange(0, 50);
				}}),
				new TableColumn(TableProperties.NOTES, "VARCHAR(255)", new ValueParameter() {{
					setValueLengthRange(0, 255);
				}}),
				new TableColumn(TableProperties.GENDER, "CHAR(1)", new ValueParameter() {{
					setValueLengthRange(1, 1);
				}}),
				new TableColumn(TableProperties.GRADE_LEVEL, "INT NOT NULL", new ValueParameter() {{
					setValueRange(1, 12);
				}}) };
		
		TableColumn[] coursesTableColumns = new TableColumn[] {
				new TableColumn(TableProperties.PERIOD, "INT NOT NULL UNIQUE", new ValueParameter() {{
					setValueRange(1, 7);
				}}),
				new TableColumn(TableProperties.NAME, "VARCHAR(50) NOT NULL", new ValueParameter() {{
					setValueLengthRange(0, 50);
				}})};

		TableManager.addTable(new Table(database.TableProperties.STUDENTS_TABLE_NAME, studentsTableColumns));
		TableManager.addTable(new Table(database.TableProperties.COURSES_TABLE_NAME, coursesTableColumns));
	}
	
	private static void showPasswordField() {
		PasswordField passTest = new PasswordField();
		//passTest.setToOpen();
	}

}
