
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 10/13/16
// DatabaseManager.java

package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

/** Abstracts mySQL database management operations. */
public class DatabaseManager {

	private static Connection connection;
	private static Table[] tables;

	/** Tests the class's various operation. */
	public static void main(String[] args) {
		final Table[] tables = new Table[] {
				new Table("Students", "studentID", new TableColumn[] {
						new TableColumn("studentID", "INT NOT NULL UNIQUE"),
						new TableColumn("firstName", "VARCHAR(20) NOT NULL"),
						new TableColumn("lastName", "VARCHAR(20) NOT NULL"),
						new TableColumn("notes", "VARCHAR(255)"),
						new TableColumn("gender", "CHAR(1)"),
						new TableColumn("gradeLevel", "INT NOT NULL") }),
				new Table("Classes", "id", new TableColumn[] {
						new TableColumn("id", "INT NOT NULL UNIQUE"),
						new TableColumn("name", "VARCHAR(20) NOT NULL"), }) };
		
		

		DatabaseManager.init(tables);
	}
	
	public static void DeleteTable(String tableName) {
		try {
			DatabaseManager.getSQLStatement("DROP TABLE " + tableName).executeUpdate();
		} catch (SQLException e) {
			System.out.println("Failed to delete table \"" + tableName + "\".");
		} finally {
			System.out.println("Sucessfully deleted table \"" + tableName + "\".");
		}
	}

	/** Initializes the tables and sets up the Database to be ready for use. */
	public static void init(Table[] tables) {
		DatabaseManager.tables = tables;

		DatabaseManager.connectToRemote();
		DatabaseManager.createTables();
	}

	/**
	 * Writes a value to a specific table, row and column. Returns true if
	 * operation was successful.
	 */
	public static boolean writeCell(String table, int primaryKey, String column,
			String value) {
		String statement = "UPDATE " + table + " SET " + column + "='" + value
				+ "' WHERE" + primaryKey + "= 7;";
		try {
			DatabaseManager.getSQLStatement(statement).executeUpdate();

			return true;
		} catch (SQLException e) {
			System.out.println("Failed to perform write operation.");
			e.printStackTrace();

			return false;
		}
	}

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

	/** Creates a table for each table. */
	private static void createTables() {
		try {
			for (Table table : tables) {
				String columnInfo = "";
				TableColumn[] columns = table.getColumns();

				for (TableColumn column : columns)
					columnInfo += column.getName() + ' ' + column.getType() + ", ";

				columnInfo += "PRIMARY KEY(" + table.getPrimaryKey() + ")";

				DatabaseManager
						.getSQLStatement("CREATE TABLE IF NOT EXISTS "
								+ table.getName() + "(" + columnInfo + ")")
						.executeUpdate();
				
				System.out.println("Sucessfully created table \"" + table.getName() + "\".");
			}
		} catch (Exception e) {
			System.out.println("Failed to create table.");
		}
	}

}