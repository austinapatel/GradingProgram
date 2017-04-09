
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 12/2/16
// Table.java

package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Lets tables utilize the operations in the DatabaseManager class and adds
 * common methods for all tables.
 */
public class Table
{

	private String name, primaryKey;
	private TableColumn[] tableColumns;
	private ResultSet resultSet;

	public Table(Object name, TableColumn[] tableColumns)
	{
		this.tableColumns = tableColumns;

		this.name = name.toString();
		this.primaryKey = tableColumns[0].getName();

		createTable();

		resultSet = DatabaseManager.getTable(this);
	}

	/** Creates the table. */
	private void createTable()
	{
		DatabaseManager.createTable(this);
	}

	public String getName()
	{
		return name;
	}

	public String getPrimaryKey()
	{
		return primaryKey;
	}

	public ResultSet getResultSet()
	{
		return resultSet;
	}

	public TableColumn[] getTableColumns()
	{
		return tableColumns;
	}

	/**Determines the number of rows in the table.*/
	public int getRowCount() {
		int rows = 0;

		try
		{
			//			resultSet.beforeFirst();
			//
			//			while (resultSet.next())
			//				rows++;
			//
			//			resultSet.first();

			resultSet.last();
			rows = resultSet.getRow();
		}
		catch (SQLException e)
		{
			System.out.println("Failed to determine the number of rows in " + name);
		}

		return rows;
	}

	/**
	 * Adds a row to the table with blank data.
	 */
	public void addRow() {
		int numRows = getRowCount();

		DatabaseManager.beginRowInsert(this);

		DatabaseManager.addToRow(this, numRows + 1, 0);

		for (int i = 1; i < tableColumns.length; i++)
			DatabaseManager.addToRow(this, null, i);

		DatabaseManager.endRowInsert(this);
	}

	/**
	 * Removes a row from the table given a value and the column that value is
	 * in. Returns the index of the row that was deleted.
	 */
	public int deleteRow(Object value, int column) {
		try
		{
			resultSet.beforeFirst();

			while (resultSet.next())
				if (resultSet.getObject(column).toString().equals(value.toString()))
				{
					resultSet.deleteRow();
					return resultSet.getRow();
				}
		}
		catch (SQLException e)
		{
			System.out.println("Failed to delete row.");
		}

		return -1;
	}

	public int getColumnIndex(String columnName) {
		for (int i = 0; i < tableColumns.length; i++)
			if (tableColumns[i].getName().equals(columnName))
				return i;

		return -1;
	}

	public ArrayList<Object> getAllFromColumn(String columnName) {
		return getAllFromColumn(columnName, resultSet);
	}

	private ArrayList<Object> getAllFromColumn(String columnName, ResultSet resultSet) {
		ArrayList<Object> data = new ArrayList<Object>();
		int columnIndex = getColumnIndex(columnName) + 1;

		try {
			resultSet.first();

			if (!resultSet.next()) // Checks if ResultSet is empty
				return data;

			while(!resultSet.isAfterLast())
			{
				Object currentValue = resultSet.getObject(columnIndex);
				data.add(currentValue);

				resultSet.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return data;
	}

	public ArrayList<Object> getSomeFromColumn(String returnColumnName, String searchColumnName, String searchQuery) {
		ResultSet filter = DatabaseManager.getFilterdTable(this, searchColumnName, searchQuery);
		return getAllFromColumn(returnColumnName, filter);
	}
}
