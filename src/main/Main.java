
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 10/13/16
// Main.java

package main;

import database.DatabaseManager;
import database.TableProperties;
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
				new TableColumn(TableProperties.ID, "INT NOT NULL UNIQUE"),
				new TableColumn(TableProperties.FIRST_NAME, "VARCHAR(20) NOT NULL"),
				new TableColumn(TableProperties.LAST_NAME, "VARCHAR(20) NOT NULL"),
				new TableColumn(TableProperties.NOTES, "VARCHAR(255)"),
				new TableColumn(TableProperties.GENDER, "CHAR(1)"),
				new TableColumn(TableProperties.GRADE_LEVEL, "INT NOT NULL") };
		
		TableColumn[] coursesTableColumns = new TableColumn[] {
				new TableColumn(TableProperties.ID, "INT NOT NULL UNIQUE"),
				new TableColumn(TableProperties.NAME, "VARCHAR(20) NOT NULL")};

		TableManager.addTable(new Table(database.TableProperties.STUDENTS_TABLE_NAME, studentsTableColumns));
		TableManager.addTable(new Table(database.TableProperties.COURSES_TABLE_NAME, coursesTableColumns));
	}
	
	private static void showPasswordField() {
		PasswordField passTest = new PasswordField();
		passTest.setToOpen();
	}

}
