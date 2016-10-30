
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;


// Austin Patel & Jason Morris
// APCS
// Redwood High School
// 10/13/16
// DatabaseManager.java
//mysql data setup help from mysql tutorial by Steven Byrne

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class DatabaseManager
{
	private static String password = "";
	public static void main(String[] args) throws Exception
	{
		getPassword();
		createTable();
		post();
	}
	
	public static void getPassword()
	{
		Scanner input = new Scanner(System.in);
		System.out.print("Enter database password:   ");
		password = input.nextLine();
		input.close();

	}
	
	public static Connection getConnection() throws Exception
	{
		try
		{
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://db4free.net:3306/gradingprogram?autoReconnect=true&useSSL=false";
			String username = "cheetahgod";
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, username, password);
			System.out.println("Connected");
			return conn;
		
		}catch (Exception e)
		{
			System.out.print(e);
		}
		
		return null;
	}
	
	public static void createTable() throws Exception
	{
		try
		{
			Connection con = getConnection();
			PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS tablename(id int NOT NULL AUTO_INCREMENT, first varchar(255), lastname varchar(255), PRIMARY KEY (id))");
			create.executeUpdate();
		}catch (Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			System.out.println("Function completed");
		}
	}
	
	public static void post() throws Exception
	{
		final String var1 = "John";
		final String var2 = "Miller";
		
		try
		{
			Connection con = getConnection();
			PreparedStatement posted = con.prepareStatement("INSERT INTO tablename (first, lastname) VALUES ('"+var1+"', '"+var2+"')");
			posted.executeUpdate();
		}catch (Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			System.out.println("Insert Completed");
		}
		
	}

}