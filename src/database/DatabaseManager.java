
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
import java.sql.Statement;

import database.TableColumn.DataType;

/** Abstracts mySQL database management operations. "init() must be called before use."*/
public class DatabaseManager {

	private static Connection connection;
	
	public static void init() {
		DatabaseManager.connectToRemote();
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

	/** Returns a row from a given table and a primary key id. */
	public static ResultSet getRow(Table table, int id) {
		try {
			String sql = "SELECT * FROM " + table.getName() + " WHERE id = '"
					+ id + "'";
			Statement stmt = connection.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to read a row from the table: "
					+ table.getName() + '.');

			return null;
		}
	}

	/** Returns the ResultSet for a given table. */
	public static ResultSet getTable(Table table) {
		try {
			String sql = "SELECT * FROM " + table.getName();
			Statement stmt = connection.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery(sql);

			return rs;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return null;
	}

	/** Gets a table ready to be inserted into. */
	public static void beginRowInsert(Table table) {
		ResultSet resultSet = table.getResultSet();
		try {
			if (resultSet.getConcurrency() == ResultSet.CONCUR_UPDATABLE)
				resultSet.moveToInsertRow();
		} catch (SQLException e) {
			System.out
					.println("Unable to begin inserting a row into the table: "
							+ table.getName());
		}
	}

	/** Finishes the insert process for a row into a table. */
	public static void endRowInsert(Table table) {
		ResultSet resultSet = table.getResultSet();

		try {
			resultSet.insertRow();
			resultSet.moveToCurrentRow();
		} catch (SQLException e) {

		}
	}
	
	/** Adds a value of the correct type to a table row ResultSet. */
	public static void addToRow(Table table, Object value, int columnIndex) {

		DataType type = DatabaseManager
				.getSQLType(table.getTableColumns()[columnIndex].getType());
		ResultSet resultSet = table.getResultSet();

		columnIndex++; // columnIndex starts at 1, not 0
		
		try {
			if (type == DataType.String) {
				if (value == null)
					value = "";
				
				resultSet.updateString(columnIndex, value.toString());
			}
			else if (type == DataType.Integer) {
				if (value == null)
					value = 0;
				
				resultSet.updateInt(columnIndex, Integer.class.cast(value));
			}
		} catch (SQLException e) {
			System.out.println("Unable to add value " + value.toString()
					+ " to " + table.getName() + ".");
		}

	}

	/**
	 * Returns the name of the data type from a String containing a mySQL data
	 * type.
	 */
	public static DataType getSQLType(String name) {
		if (name.contains("VARCHAR") || name.contains("CHAR"))
			return DataType.String;
		else if (name.contains("INT"))
			return DataType.Integer;
		else
			return null;
	}

	/** Opens the remote connection to the database. */
	private static void connectToRemote() {
		String url = DatabasePropertiesManager.read("db", "url");
		String username = DatabasePropertiesManager.read("db", "username");
		String password = DatabasePropertiesManager.read("db", "password");

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
	public static PreparedStatement getSQLStatement(String mySQLCommand) {

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

		for (TableColumn column : table.getTableColumns())
			columnInfo += column.getName() + ' ' + column.getType() + ", ";

		columnInfo += "PRIMARY KEY (" + table.getPrimaryKey() + ")";

		try {

			String sql = "CREATE TABLE IF NOT EXISTS " + table.getName() + "("
					+ columnInfo + ")";
			DatabaseManager.getSQLStatement(sql).executeUpdate();

			System.out.println(
					"Sucessfully created table \"" + table.getName() + "\".");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}