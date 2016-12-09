
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 12/2/16
// Table.java

package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

/**
 * Lets tables utilize the operations in the DatabaseManager class and adds
 * common methods for all tables.
 */
public abstract class Table
{

	private String name, primaryKey;
	private TableColumn[] columns;

	/** Initializes variables. */
	public Table(String name, String primaryKey, String[][] columnInfo)
	{
		this.name = name;
		this.columns = initColumns(columnInfo);
		this.primaryKey = primaryKey;

		createTable();
	}

	/** Initializes the table columns. */
	private TableColumn[] initColumns(String[][] columnInfo)
	{
		TableColumn[] tableColumns = new TableColumn[columnInfo.length];

		for (int i = 0; i < tableColumns.length; i++)
			tableColumns[i] = new TableColumn(columnInfo[i][0], columnInfo[i][1]);

		return tableColumns;
	}

	/** Gets raw data for a row from the table. */
	protected ResultSet getRowData(int primaryKey)
	{

		return DatabaseManager.getRow(this, primaryKey);
	}

	protected ResultSet getTable()
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

	public TableColumn[] getColumns()
	{
		return columns;
	}

	public String getPrimaryKey()
	{
		return primaryKey;
	}

	/**Returns the row with the PreparedStatement that needs to have values added to it.*/
	protected PreparedStatement addRow()
	{
		String columnNames = "";
		String values = "";

		for (TableColumn column : columns)
		{
			columnNames += column.getName() + ',';
			values += "?,";
		}

		columnNames = columnNames.substring(0, columnNames.length() - 1);
		values = values.substring(0, values.length() - 1);

		String sql = "INSERT INTO " + name + " (" + columnNames + ") VALUES(" + values + ");";

		return DatabaseManager.getSQLStatement(sql);
	}

}
