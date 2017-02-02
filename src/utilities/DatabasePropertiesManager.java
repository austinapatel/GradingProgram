
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 10/13/16
// WriteProperties.java

package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/** Stores the database credential information to a file. */
public class DatabasePropertiesManager
{

	/** Writes an array of keys and values to a properties file. */
	public static void write(String fileName, String[] keys, String[] values)
	{
		fileName += ".properties";

		try
		{
			Properties properties = new Properties();
			FileInputStream in = new FileInputStream("db.properties");
			properties.load(in);

			for (int i = 0; i < keys.length; i++)
				properties.setProperty(keys[i], values[i]);

			FileOutputStream fileOut = new FileOutputStream(new File(fileName));
			properties.store(fileOut, fileName);

			fileOut.close();
		}
		catch (Exception e)
		{
			System.out.println("Failed to read from file");
			e.printStackTrace();
		}

	}

	/** Reads a values in a with a given key and property file name. */
	public static String read(String secretKey, String fileName, String key)
	{
		fileName += ".properties";

		try
		{
			Properties properties = new Properties();
			FileInputStream in = new FileInputStream(fileName);
			properties.load(in);
			return 	AES.decrypt(properties.getProperty(key), secretKey);
		}
		catch (Exception e)
		{
			System.out.println("Failed to read from to properties file.");
			e.printStackTrace();

			return null;
		}
	}
}
