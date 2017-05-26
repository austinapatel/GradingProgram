
// Austin Patel & Jason Morris
// APCS
// Redwood High School
// 12/2/16
// Table.java

package database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Lets tables utilize the operations in the DatabaseManager class and adds
 * common methods for all tables.
 */
public class Table
{

	private String name, primaryKey;
	private TableColumn[] tableColumns;
	private ResultSet resultSet;

	public Table(String name, TableColumn[] tableColumns)
	{
		this.name = name;
		init(name, tableColumns, DatabaseManager.getTable(this));
		if (TableManager.createTable)
			createTable();
	}

	public Table(String tableName, ResultSet resultSet)
	{
		TableColumn[] tableColumns = null;

		try
		{
			ResultSetMetaData metaData = resultSet.getMetaData();

			tableColumns = new TableColumn[metaData.getColumnCount()];
			tableColumns[0] = new TableColumn(metaData.getColumnName(1), metaData.getColumnTypeName(1), null);
			for (int i = 1; i <= metaData.getColumnCount(); i++)
			{
				tableColumns[i - 1] = new TableColumn(metaData.getColumnName(i), metaData.getColumnTypeName(i), null);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		init(tableName, tableColumns, resultSet);
	}

	private void init(String name, TableColumn[] tableColumns, ResultSet resultSet)
	{
		this.tableColumns = tableColumns;

		this.name = name;
		this.primaryKey = tableColumns[0].getName();

		this.resultSet = resultSet;
	}

	/** Creates the table. */
	private void createTable()
	{
		DatabaseManager.createTable(this);
	}

	public void update()
	{
		resultSet = DatabaseManager.getTable(this);
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
	public int getRowCount()
	{
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
	 * Adds a row to the table with blank data. Do not call this function if you want to add a row.
	 * Use TableManager.insertValuesIntoNewRow();
	 */
	public void startRowCreation()
	{

		int desiredRowID = getInsertID();
		DatabaseManager.beginRowInsert(this);

		DatabaseManager.addToRow(this, desiredRowID, 0);

		for (int i = 1; i < tableColumns.length; i++)
			DatabaseManager.addToRow(this, null, i);

		DatabaseManager.endRowInsert(this);
	}

	private int getInsertID()
	{
		ArrayList<Integer> currentIDs = DataTypeManager.toIntegerArrayList(getAllFromColumn(tableColumns[0].getName()));

		//		resultSet.first();

		int largest = 0;
		for (int i : currentIDs)
			largest = (i > largest) ? i : largest;

		return largest + 1;
	}

	/**
	 * Removes a row from the table given a value and the column that value is
	 * in. Returns the index of the row that was deleted.
	 */
	public int deleteRow(Object value, int column)
	{
		try
		{
			resultSet.beforeFirst();

			while (resultSet.next())
			{
				if (resultSet.getObject(column).toString().equals(value.toString()))
				{
					resultSet.deleteRow();
					return resultSet.getRow();
				}
			}
		}
		catch (SQLException e)
		{
			System.out.println("Failed to delete row.");
		}

		return -1;
	}

	public int getColumnIndex(String columnName)
	{
		for (int i = 0; i < tableColumns.length; i++)
			if (tableColumns[i].getName().equals(columnName))
				return i;

		return -1;
	}

	public ArrayList<Object> getAllFromColumn(String columnName)
	{
		return getAllFromColumn(columnName, resultSet);
	}

	private ArrayList<Object> getAllFromColumn(String columnName, ResultSet resultSet)
	{
		ArrayList<Object> data = new ArrayList<>();
		int columnIndex = getColumnIndex(columnName) + 1;

		try
		{
			resultSet.beforeFirst();

			if (!resultSet.next())
			{ // Checks if ResultSet is empty
				resultSet.beforeFirst();
				return data;
			}

			resultSet.first();

			while (!resultSet.isAfterLast())
			{
				Object currentValue = resultSet.getObject(columnIndex);
				data.add(currentValue);

				resultSet.next();
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return data;
	}

	public ArrayList<Object> getSomeFromColumn(String returnColumnName, String searchColumnName, String searchQuery)
	{
		ResultSet filter = DatabaseManager.getFilterdTable(this, searchColumnName, searchQuery);
		return getAllFromColumn(returnColumnName, filter);
	}

	public ArrayList<Object> getSomeFromColummn(String returnColumnName, Search... searches) {
		String sql = "";
		String select = SqlBuilder.selection(new String[][] {{name, returnColumnName}}, new String[] {name});

		sql += select;
		System.out.println("Selection: " + select);

		String[][] filters = new String[searches.length][2];

		for (int i = 0; i < searches.length; i++) {
			Search search = searches[i];

			sql += " " + SqlBuilder.filter(name, search.getColumnName(), search.getSearchValue() + "");

			if (i < searches.length - 1)
				sql += " AND ";
		}

//		String filter = SqlBuilder.filter(TableProperties.GRADES_TABLE_NAME, TableProperties.STUDENT_ID, studentId + "");
//		String filter2 = SqlBuilder.filter(TableProperties.GRADES_TABLE_NAME, TableProperties.ASSIGNMENT_ID, assignmentId + "");
//
//		String sql = select + " " + filter + " AND " + filter2;

		System.out.println("Austin:" + sql);
		return new ArrayList<>();

//		ResultSet set = DatabaseManager.executeSqlStatement(sql);
//		Table table = new Table("Table", set);
//		ArrayList<Object> result = table.getAllFromColumn(TableProperties.GRADE_ID);

	}
}
