
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
import java.util.HashMap;

/** Abstracts mySQL database management operations. */
public class DatabaseManager
{

	private static Connection connection;

	/** Tests the class's various operation. */
	public static void main(String[] args)
	{
		DatabaseManager.connectToRemote();

		Table[] tables = new Table[] {new Students()};
		Students students = (Students) tables[0];

		students.addStudent(new Student(110123, "Austin", "Patel", "Is very cool!", 'M', 10));

		students.addStudent(new Student(110124, "Austin2", "Patel", "Is very cool!", 'M', 10));
		students.addStudent(new Student(123456, "Dave", "Goldsmith", "Has two cats!", 'M', 50));
		students.addStudent(new Student(654321, "Drew", "Carlisle", "Is a man!", 'O', 12));

		Student student = students.getRow(654321);

	}

	public static void deleteTable(String tableName)
	{
		try
		{
			DatabaseManager.getSQLStatement("DROP TABLE " + tableName).executeUpdate();
		}
		catch (SQLException e)
		{
			System.out.println("Failed to delete table \"" + tableName + "\".");
		}
		finally
		{
			System.out.println("Sucessfully deleted table \"" + tableName + "\".");
		}
	}

	/** Returns a row from a given table and a primary key id. */
	public static ResultSet getRow(Table table, int id)
	{

		try
		{
			String sql = "SELECT * FROM " + table.getName() + " WHERE id = '" + id + "'";

			return DatabaseManager.getSQLStatement(sql).executeQuery();

		}
		catch (Exception e)
		{
			System.out.println("Failed to read a row from the table: " + table.getName() + '.');

			return null;
		}

	}

	public static ResultSet getTable(Table table)
	{

		String sql = "SELECT * FROM " + table.getName();
		try
		{
			return DatabaseManager.getSQLStatement(sql).executeQuery();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return null;

	}

	/** Opens the remote connection to the database. */
	private static void connectToRemote()
	{
		String url = PropertiesManager.read("db", "url");
		String username = PropertiesManager.read("db", "username");
		String password = PropertiesManager.read("db", "password");

		try
		{
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver);
			connection = DriverManager.getConnection(url, username, password);
			System.out.println("Successfully connected to database.");
		}
		catch (Exception e)
		{
			System.out.print(e);
		}
	}

	/** Returns the mySQL prepared table given a command. */
	public static PreparedStatement getSQLStatement(String mySQLCommand)
	{
		try
		{
			return connection.prepareStatement(mySQLCommand);
		}
		catch (SQLException e)
		{
			System.out.println("The operation " + mySQLCommand + " failed.");
			e.printStackTrace();

			return null;
		}
	}

	/** Creates a single table. */
	public static void createTable(Table table)
	{

		String columnInfo = "";

		for (TableColumn column : table.getColumns())
			columnInfo += column.getName() + ' ' + column.getType() + ", ";

		columnInfo += "PRIMARY KEY (" + table.getPrimaryKey() + ")";

		try
		{

			String sql = "CREATE TABLE IF NOT EXISTS " + table.getName() + "(" + columnInfo + ")";
			DatabaseManager.getSQLStatement(sql).executeUpdate();

			System.out.println("Sucessfully created table \"" + table.getName() + "\".");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

}