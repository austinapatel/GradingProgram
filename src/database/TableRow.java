
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 12/3/16
// TableRow.java

package database;

import java.sql.ResultSet;
import java.sql.SQLException;

/** Superclass for a specific row in a database table. */
public class TableRow
{

	private ResultSet resultSet;

	public TableRow(ResultSet resultSet)
	{
		this.resultSet = resultSet;
	}

	/**Returns a String property from a table row.*/
	public String getStringProperty(String property)
	{
		try
		{
			return resultSet.getString(property);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("Failed to read String property: " + property);

			return null;
		}
	}

	/**Returns an Integer property from a table row.*/
	public int getIntProperty(String property)
	{
		try
		{
			return resultSet.getInt(property);
		}
		catch (SQLException error)
		{
			System.out.println("Failed to read Integer property: " + property);

			return -1;
		}
	}
	
	/**Returns the ResultSet for the current row.*/
	public ResultSet getResultSet() {
		return resultSet;
	}

}
