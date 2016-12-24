
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 12/22/16
// UpdateDatabaseItemThread.java

package database;

import java.sql.ResultSet;

import database.TableColumn.DataType;

/**
 * Adds an item to a row given a column and value. Using a Runnable increases
 * performance and does not block UI thread.
 */
public class UpdateDatabaseItemRunnable implements Runnable {

	private int rowIndex, columnIndex;
	private Object newValue;
	private DataType dataType;
	private ResultSet resultSet;

	public UpdateDatabaseItemRunnable(int columnIndex, int rowIndex, Object newValue,
			DataType dataType, ResultSet resultSet) {
		this.columnIndex = columnIndex;
		this.newValue = newValue;
		this.dataType = dataType;
		this.resultSet = resultSet;
		this.rowIndex = rowIndex;
	}

	@Override
	public void run() {
		try {
			resultSet.absolute(rowIndex);
			
			if (dataType == DataType.Integer) {
				resultSet.updateInt(columnIndex, Integer.parseInt(newValue.toString()));
			} else if (dataType == DataType.String)
				resultSet.updateString(columnIndex, newValue.toString());

			resultSet.updateRow();
		} catch (Exception e) {
		}
	}

}
