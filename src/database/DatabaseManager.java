
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 10/13/16
// DatabaseManager.java
// mySQL data setup help from mysql tutorial by Steven Byrne

package database;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;

/**Abstracts mySQL database management operations.*/
public class DatabaseManager
{
	private static Connection con;
	private static String url;
	private static String username;
	private static String passwd;

	public static void main(String[] args) throws Exception
	{
		con = getConnection();
		createDB();

		String[][] names = {{"Austin", "K"}, {"Zach", "J"}, {"Frank", "B"}, {"Ken", "Mark"}};

		for (String[] name : names)
		{
			addStudent(false, name[0], name[1], "No notes", 12345);
		}

		System.out.println(Arrays.toString(getStudent("Austin").toArray()));

	}

	public static void getPassword()
	{
		Scanner input = new Scanner(System.in);
		System.out.print("Enter database password:   ");
		//password = input.nextLine();
		input.close();

	}

	private static Connection getConnection()
	{

		WritePropertiesFile.write();

		Properties props = new Properties();
		FileInputStream in = null;

		try
		{
			in = new FileInputStream("db.properties");
			props.load(in);

		}
		catch (Exception e)
		{

		}

		url = props.getProperty("db.url");
		username = props.getProperty("db.user");
		passwd = props.getProperty("db.passwd");

		Connection conn = null;

		try
		{
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, passwd);
			System.out.println("Connected");
		}
		catch (Exception e)
		{
			System.out.print(e);
		}

		return conn;
	}

	private static ArrayList<String> getStudent(String var1) throws Exception
	{
		try
		{
			//PreparedStatement statement = con.prepareStatement("SELECT first,lastname FROM tablename LIMIT 1"); 
			/// SELECT * FROM tablename, table2 WHERE tablename.first = table2.first AND ... OR
			//PreparedStatement statement = con.prepareStatement("SELECT * FROM tablename, table2 ORDER BY lastname ASC");
			PreparedStatement statement = con
						.prepareStatement("SELECT * FROM tablename WHERE first = '" + var1 + "' LIMIT 1");

			ResultSet result = statement.executeQuery();

			ArrayList<String> array = new ArrayList<String>();
			while (result.next())
			{
				System.out.print(result.getString("first"));
				System.out.print(" ");
				System.out.println(result.getString("lastname"));
				array.add(result.getString("lastname"));
			}
			System.out.println("All records have been selected");
			return array;
		}
		catch (Exception e)
		{
			System.out.println(e);
		}

		return null;
	}

	private static void createDB() throws Exception
	{
		try
		{
			PreparedStatement create = con.prepareStatement(
						"CREATE TABLE IF NOT EXISTS tablename(id int NOT NULL AUTO_INCREMENT, first varchar(255), lastname varchar(255), PRIMARY KEY (id))");
			create.executeUpdate();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			System.out.println("Function completed");
		}
	}

	private static void addStudent(boolean gender, String last, String first, String notes, int student_id)
	{
		try
		{
			PreparedStatement posted = con
						.prepareStatement("INSERT INTO tablename (first, lastname) VALUES ('" + first + "', '" + last + "')");
			posted.executeUpdate();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			System.out.println("Insert Completed");
		}

	}

}