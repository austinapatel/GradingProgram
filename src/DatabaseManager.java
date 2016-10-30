
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


// Austin Patel & Jason Morris
// APCS
// Redwood High School
// 10/13/16
// DatabaseManager.java
//mysql data setup help from mysql tutorial by Steven Byrne


public class DatabaseManager
{
	private static String password = "";
	public static void main(String[] args) throws Exception
	{
		getPassword();
		createTable();
		
		String[][] names = {{"Austin", "P"}, {"Jason", "M"}, {"fred" , "jones"}, {"Ben", "Dover"}};	
		
		
		for (String[] name: names)
		{
			post(name[0], name[1]);
		}
		
		System.out.println(Arrays.toString(getStudent("Austin").toArray()));
		
	}
	
	public static void getPassword()
	{
		Scanner input = new Scanner(System.in);
		System.out.print("Enter database password:   ");
		password = input.nextLine();
		input.close();

	}
	
	public static ArrayList<String> getStudent(String var1) throws Exception
	{
		try
		{
			Connection con = getConnection();
			//PreparedStatement statement = con.prepareStatement("SELECT first,lastname FROM tablename LIMIT 1"); 
			/// SELECT * FROM tablename, table2 WHERE tablename.first = table2.first AND ... OR
			//PreparedStatement statement = con.prepareStatement("SELECT * FROM tablename, table2 ORDER BY lastname ASC");
			PreparedStatement statement = con.prepareStatement("SELECT * FROM tablename WHERE first = '"+var1+"' LIMIT 1");
			
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
	
	
	
	public static void post(String var1, String var2) throws Exception
	{
		//final String var1 = "John";
		//final String var2 = "Miller";
		
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