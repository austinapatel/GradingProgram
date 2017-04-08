
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 12/22/16
// UpdateDatabaseItemThread.java

package database;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.TableColumn.DataType;

/**
 * Adds an item to a row given a column and value. Using a Runnable increases
 * performance and does not block UI thread.
 */
public class UpdateDatabaseItemRunnable implements Runnable
{

	private int rowIndex, columnIndex;
	private Object newValue;
	private DataType dataType;
	private ResultSet resultSet;

	public UpdateDatabaseItemRunnable(int columnIndex, int rowIndex, Object newValue, ResultSet resultSet,
				DataType dataType)
	{
		this.columnIndex = columnIndex;
		this.newValue = newValue;
		this.resultSet = resultSet;
		this.rowIndex = rowIndex;
		this.dataType = dataType;
	}

	@Override
	public void run()
	{
		try
		{
			resultSet.absolute(rowIndex);

			if (dataType == DataType.Integer)
			{
				int intVal = Integer.parseInt(newValue.toString());

				resultSet.updateInt(columnIndex, intVal);
			}
			else if (dataType == DataType.String)
				resultSet.updateString(columnIndex, newValue.toString());
			else if (dataType == DataType.Double)
			{
				double doubleVal = Double.parseDouble(newValue.toString());
				resultSet.updateDouble(columnIndex, doubleVal);
			}
			else
				throw new Exception("DataType not handled (UpdateDatabaseItemRunnabale.java)");

			resultSet.updateRow();
		}
		catch (Exception e) {
			System.out.println("Invalid new value into database row.");

			e.printStackTrace();
		}
	}
}
