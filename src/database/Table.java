
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 12/2/16
// Table.java

package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Lets tables utilize the operations in the DatabaseManager class and adds
 * common methods for all tables.
 */
public class Table
{
	public final static String PROPERTY_ID = "id";

	private String name, primaryKey;
	private TableColumn[] tableColumns;
	private ResultSet resultSet;
	
	public Table(String name, TableColumn[] tableColumns)
	{
		this.tableColumns = tableColumns;
		
		this.name = name;
		this.primaryKey = Properties.ID;
		
		createTable();
		
		resultSet = DatabaseManager.getTable(this);
	}

	/** Gets raw data for a row from the table. */
	public TableRow getRow(int primaryKey)
	{
		return new TableRow(DatabaseManager.getRow(this, primaryKey));
	}

	private ResultSet getTable()
	{
		return DatabaseManager.getTable(this);
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
	
	/**
	 * Adds a row to the table. "data" parameter should have data in same order
	 * as "COLUMN_INFO" variable.
	 */
	public void add(Object[] data) {
		DatabaseManager.beginRowInsert(this);

		for (int i = 0; i < data.length; i++)
			DatabaseManager.addToRow(this, data[i], i);

		DatabaseManager.endRowInsert(this);
	}

}
