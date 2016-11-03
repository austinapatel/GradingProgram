
// Austin Patel & Jason Morris
// APCS
// Redwood High School
// 10/13/16
// DatabaseManager.java

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public class DatabaseManager
{
	private static String password = "";
	private static Connection connection;
	
	public static void main(String[] args) throws Exception
	{
		password = getPassword();
		connection = getConnection();
		
		createDB();
		
		String[][] names = {{"Carl", "K"}, {"Zach", "J"}, {"Frank" , "B"}, {"Ken", "Mark"}};	
		
		// Put all the names in the database
		for (String[] name: names)
			post(name[0], name[1]);
		
		System.out.println(getStudent("Carl"));
	}
	
	private static String getPassword()
	{
		System.out.print("Enter password:  ");
		
		Scanner scanner = new Scanner(System.in);
		return scanner.nextLine();
	}
	
	private static ArrayList<String> getStudent(String var1) throws Exception
	{
		try
		{
			//PreparedStatement statement = con.prepareStatement("SELECT first,lastname FROM tablename LIMIT 1"); 
			/// SELECT * FROM tablename, table2 WHERE tablename.first = table2.first AND ... OR
			//PreparedStatement statement = con.prepareStatement("SELECT * FROM tablename, table2 ORDER BY lastname ASC");
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM tablename WHERE first = '"+var1+"' LIMIT 1");
			
			ResultSet result = statement.executeQuery();
			
			ArrayList<String> array = new ArrayList<String>();
			while(result.next())
			{
				System.out.print(result.getString("first"));
				System.out.print(" ");
				System.out.println(result.getString("lastname"));
				array.add(result.getString("lastname"));
			}
			System.out.println("All records have been selected");
			return array; 
		}catch (Exception e)
		{
			System.out.println(e);
		}
		return null;
	}

	
	private static Connection getConnection() throws Exception
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
	
	private static void createDB() throws Exception
	{
		try
		{
			PreparedStatement create = connection.prepareStatement("CREATE TABLE IF NOT EXISTS tablename(id int NOT NULL AUTO_INCREMENT, first varchar(255), lastname varchar(255), PRIMARY KEY (id))");
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
	
	/**Executes a mySQL command.*/
	private static void execute()
	{
		
	}
	
	private static void post(String var1, String var2) throws Exception
	{
		//final String var1 = "John";
		//final String var2 = "Miller";
		
		try
		{
			PreparedStatement posted = connection.prepareStatement("INSERT INTO tablename (first, lastname) VALUES ('"+var1+"', '"+var2+"')");
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