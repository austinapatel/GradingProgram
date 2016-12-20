
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 10/13/16
// Main.java

package main;

import database.DatabaseManager;
import database.Properties;
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
				new TableColumn(Properties.ID, "INT NOT NULL UNIQUE"),
				new TableColumn(Properties.FIRST_NAME, "VARCHAR(20) NOT NULL"),
				new TableColumn(Properties.LAST_NAME, "VARCHAR(20) NOT NULL"),
				new TableColumn(Properties.NOTES, "VARCHAR(255)"),
				new TableColumn(Properties.GENDER, "CHAR(1)"),
				new TableColumn(Properties.GRADE_LEVEL, "INT NOT NULL") };
		
		TableColumn[] coursesTableColumns = new TableColumn[] {
				new TableColumn(Properties.ID, "INT NOT NULL UNIQUE"),
				new TableColumn(Properties.NAME, "VARCHAR(20) NOT NULL")};

		TableManager.addTable(new Table(database.Properties.STUDENTS_TABLE_NAME, studentsTableColumns));
		TableManager.addTable(new Table(database.Properties.COURSES_TABLE_NAME, coursesTableColumns));
	}
	
	private static void showPasswordField() {
		PasswordField passTest = new PasswordField();
		passTest.setToOpen();
	}

}
