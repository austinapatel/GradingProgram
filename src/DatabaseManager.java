
// Austin Patel
// APCS
// Redwood High School
// 10/13/16
// DatabaseManager.java

import java.sql.*;

public class DatabaseManager
{

	public static void main(String[] args)
	{
		init();
	}
	
	public static void init()
	{
		Connection c = null;
		try
		{
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:test.db");
		}
		catch (Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Opened database successfully");
		System.out.println("test 2");
	}

}
