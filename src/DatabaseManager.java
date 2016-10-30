import java.sql.Connection;
import java.sql.DriverManager;

// Austin Patel & Jason Morris
// APCS
// Redwood High School
// 10/13/16
// DatabaseManager.java

public class DatabaseManager
{

	public static void main(String[] args) throws Exception
	{
		getConnection();
		init();
	}
	
	public static void init()
	{

	}
	
	public static Connection getConnection() throws Exception
	{
		try
		{
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://db4free.net:3306/gradingprogram?autoReconnect=true&useSSL=false";
			String username = "cheetahgod";
			String password = "Prince1";
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

}
