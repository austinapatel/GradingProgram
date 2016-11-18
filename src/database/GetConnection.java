
// Austin Patel & Jason Morris
// APCS
// Redwood High School
// 10/13/16

package database;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.Scanner;

public class GetConnection
{
	private static String url;
	private static String username;
	private static String passwd;
	
	public Connection getConnection()
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