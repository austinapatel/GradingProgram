
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 10/13/16
// GetConnection.java

package database;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**Opens the connection to the remote mySQL database.*/
public class GetConnection
{
	private static String url;
	private static String username;
	private static String passwd;
	
	private GetConnection() throws Exception
	{
		throw new Exception("Do not instantiate this class.");
	}
	
	public static Connection getConnection()
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
			
		try
		{
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, username, passwd);
			System.out.println("Connected");
			return conn;

		}
		catch (Exception e)
		{
			System.out.print(e);
		}

		return null;
	}

}