
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
			//String url = "jdbc:mysql://db4free.net:3306/gradingprogram?autoReconnect=true&useSSL=false";
			//String url2 = "jdbc:mysql://98.248.155.100:3306/gradingprogram?autoReconnect=true&useSSL=false";
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