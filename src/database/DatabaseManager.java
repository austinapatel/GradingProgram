
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 10/13/16
// DatabaseManager.java

package database;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

/**Abstracts mySQL database management operations.*/
public class DatabaseManager {
	
	private static Connection connection;
	private static HashMap<String, String> tableColumns;

	/**Tests the class's various operation.*/
	public static void main(String[] args) {
		@SuppressWarnings("serial")
		final HashMap<String, String> tableColumns = new HashMap<String, String>() {
			{
				put("Students",
						"id INT NOT NULL UNIQUE, firstName VARCHAR(20) NOT NULL, lastName VARCHAR(20) NOT NULL,"
								+ " notes VARCHAR(255), gender VARCHAR(1), studentID INT NOT NULL,"
								+ "gradeLevel INT NOT NULL, PRIMARY KEY (id)");
				put("Classes",
						"id INT NOT NULL UNIQUE, name VARCHAR(20) NOT NULL, PRIMARY KEY (id)");
			}
		};

		DatabaseManager.init(tableColumns);
	}

	/**Initializes the tables and sets up the Database to be ready for use.*/
	public static void init(HashMap<String, String> tableColumns) {		
		DatabaseManager.tableColumns = tableColumns;
		
		DatabaseManager.connectToRemote();
		DatabaseManager.createTables();
	}

	/**Writes a value to a specific table, row and column.
	 * Returns true if operation was successful.*/
	public static boolean writeCell(String table, int primaryKey, String column,
			String value) {
		String statement = "UPDATE " + table + " SET " + column
				+ "='" + value + "' WHERE" + primaryKey + "= 7;";
		try {
			DatabaseManager.getSQLStatement(statement).executeUpdate();
			
			return true;
		} catch (SQLException e) {
			System.out.println("Failed to perform write operation.");
			e.printStackTrace();
			
			return false;
		}
	}

	/**Opens the remote connection to the database.*/
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

	/**Returns the mySQL prepared table given a command.*/
	private static PreparedStatement getSQLStatement(String mySQLCommand) {
		try {
			return connection.prepareStatement(mySQLCommand);
		} catch (SQLException e) {
			System.out.println("The operation " + mySQLCommand + " failed.");
			e.printStackTrace();

			return null;
		}
	}

	/**Creates a table for each table.*/
	private static void createTables() {
		try {
			// Go through each key is the "tableColumns" and
			// create the table with the necessary information
			for (String key : tableColumns.keySet())
				DatabaseManager.getSQLStatement("CREATE TABLE IF NOT EXISTS " + key
						+ "(" + tableColumns.get(key) + ")").executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			System.out.println("Database table creation completed.");
		}
	}

}