
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 10/13/16
// DatabaseManager.java

package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/** Abstracts mySQL database management operations. */
public class DatabaseManager {

	private static Connection connection;
	private static Table[] tables;

	/** Tests the class's various operation. */
	public static void main(String[] args) {
		
		// TableData studentsTable = new TableData("Student", "id",
		// new TableColumn[] {
		// new TableColumn("id", "INT NOT NULL UNIQUE"),
		// new TableColumn("firstName", "VARCHAR(20) NOT NULL"),
		// new TableColumn("lastName", "VARCHAR(20) NOT NULL"),
		// new TableColumn("notes", "VARCHAR(255)"),
		// new TableColumn("gender", "CHAR(1)"),
		// new TableColumn("gradeLevel", "INT NOT NULL") });
		//
		// TableData coursesTable = new TableData("Course", "id",
		// new TableColumn[] {
		// new TableColumn("id", "INT NOT NULL UNIQUE"),
		// new TableColumn("name", "VARCHAR(20) NOT NULL") });
		//
		// TableData assignmentsTable = new TableData("Assignment", "id",
		// new TableColumn[] {
		// new TableColumn("id", "INT NOT NULL UNIQUE"),
		// new TableColumn("name", "VARCHAR(20) NOT NULL"),
		// new TableColumn("courseid", "INT NOT NULL"),
		// new TableColumn("value", "DOUBLE NOT NULL"),
		// new TableColumn("categoryid", "DOUBLE NOT NULL") });
		//
		// TableData categoryTable = new TableData("Category", "id",
		// new TableColumn[] {
		// new TableColumn("id", "INT NOT NULL UNIQUE"),
		// new TableColumn("name", "VARCHAR(20) NOT NULL"),
		// new TableColumn("weight", "DOUBLE NOT NULL") });
		//
		// TableData enrollmentTable = new TableData("Enrollment", "id",
		// new TableColumn[] {
		// new TableColumn("id", "INT NOT NULL AUTO_INCREMENT"),
		// new TableColumn("studentid", "INT NOT NULL"),
		// new TableColumn("classid", "INT NOT NULL"),
		// new TableColumn("year", "DATE NOT NULL") });
		//
		// TableData gradeTable = new TableData("Grade", "id",
		// new TableColumn[] {
		// new TableColumn("id", "INT NOT NULL AUTO_INCREMENT"),
		// new TableColumn("studentid", "INT NOT NULL"),
		// new TableColumn("assignmentid", "INT NOT NULL"),
		// new TableColumn("value", "DOUBLE NOT NULL") });
		//
		// final TableData[] tables = new TableData[] { studentsTable,
		// coursesTable, assignmentsTable, categoryTable, enrollmentTable,
		// gradeTable };

		Table[] tables = new Table[] { new Students() };

		DatabaseManager.init(tables);
		
		Students students = (Students) tables[0];
		
//		students.add
	}

	public static void deleteTable(String tableName) {
		try {
			DatabaseManager.getSQLStatement("DROP TABLE " + tableName)
					.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Failed to delete table \"" + tableName + "\".");
		} finally {
			System.out.println(
					"Sucessfully deleted table \"" + tableName + "\".");
		}
	}

	/** Initializes the tables and sets up the Database to be ready for use. */
	public static void init(Table[] tables) {
		DatabaseManager.tables = tables;

		DatabaseManager.connectToRemote();
		// DatabaseManager.createTables();
	}

	/**
	 * Writes a value to a specific table, row and column. Returns true if
	 * operation was successful.
	 */
//	public static boolean writeCell(TableData table, int primaryKey,
//			TableColumn column, String value) {
//		String statement = "UPDATE " + table.getName() + " SET "
//				+ column.getName() + "='" + value + "' WHERE" + primaryKey
//				+ "= 7;";
//		try {
//			DatabaseManager.getSQLStatement(statement).executeUpdate();
//
//			return true;
//		} catch (SQLException e) {
//			System.out.println("Failed to perform write operation.");
//			e.printStackTrace();
//
//			return false;
//		}
//	}

	/** Returns a row from a given table and a primary key id. */
	public static HashMap<String, Object> getRow(Table table, int id) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		ResultSet resultSet = null;

		try {
			resultSet = DatabaseManager.getSQLStatement("SELECT * FROM"
					+ table.getName() + "WHERE id = '" + id + "' LIMIT 1")
					.executeQuery();
		} catch (SQLException e) {
			System.out.println("Failed to read a row from the table: "
					+ table.getName() + '.');

			return null;
		}

		try {
			for (TableColumn column : table.getColumns())
				result.put(column.getName(),
						resultSet.getObject(column.getName()));
		} catch (SQLException e) {
			System.out.println("Failed to get objects from column in table: "
					+ table.getName());
		}

		return result;
	}

//	/** Adds a row to a table. */
//	public static void addRow(TableData table, Object... values) {
//		String data = "";
//		TableColumn[] columns = table.getColumns();
//		TableColumn curColumn;
//
//		for (int i = 0; i < values.length; i++) {
//			curColumn = columns[i];
//
//			String quoteChar = (curColumn.isText()) ? "\'" : "";
//			data += quoteChar + String.valueOf(values[i]) + quoteChar + ',';
//		}
//
//		// Removes extra ',' from end
//		data = data.substring(0, data.length() - 1);
//
//		DatabaseManager.getSQLStatement(
//				"INSERT INTO" + table.getName() + "VALUES(" + values + ")");
//	}

	// /** Returns the correct result from a ResultSet given a type. */
	// private static Object getDataFromResult(ResultSet resultSet,
	// TableColumn column, String type) {
	// // int char string double
	//
	// try {
	// if (type.contains("INT"))
	// return resultSet.getInt(column.getName());
	// // else if (type.contains("CHAR"))
	// // return resultSet.getOb
	// } catch (SQLException e) {
	// System.out.println("Failed to get value from ResultSet.");
	// }
	//
	// return null;
	// }

	/** Opens the remote connection to the database. */
	private static void connectToRemote() {
		String url = PropertiesManager.read("db", "url");
		String username = PropertiesManager.read("db", "username");
		String password = PropertiesManager.read("db", "password");

		try {
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver);
			connection = DriverManager.getConnection(url, username, password);
			System.out.println("Successfully connected to database.");
		} catch (Exception e) {
			System.out.print(e);
		}
	}

	/** Returns the mySQL prepared table given a command. */
	private static PreparedStatement getSQLStatement(String mySQLCommand) {
		try {
			return connection.prepareStatement(mySQLCommand);
		} catch (SQLException e) {
			System.out.println("The operation " + mySQLCommand + " failed.");
			e.printStackTrace();

			return null;
		}
	}

	/** Creates a single table. */
	public static void createTable(Table table) {

		String columnInfo = "";

		for (TableColumn column : table.getColumns())
			columnInfo += column.getName() + ' ' + column.getType() + ", ";

		columnInfo += "PRIMARY KEY(" + table.getPrimaryKey() + ")";

		try {
			DatabaseManager
					.getSQLStatement("CREATE TABLE IF NOT EXISTS "
							+ table.getName() + "(" + columnInfo + ")")
					.executeUpdate();

			System.out.println(
					"Sucessfully created table \"" + table.getName() + "\".");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}