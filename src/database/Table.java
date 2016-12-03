
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 12/2/16
// Table.java

package database;

import java.util.HashMap;

/**
 * Lets tables utilize the operations in the DatabaseManager class and adds
 * common methods for all tables.
 */
public abstract class Table {

	private String name, primaryKey;
	private TableColumn[] columns;

	/** Initializes variables. */
	public Table(String name, String primaryKey,
			String[][] columnInfo) {
		this.name = name;
		this.columns = initColumns(columnInfo);

		createTable();
	}

	/** Initializes the table columns. */
	private TableColumn[] initColumns(String[][] columnInfo) {
		TableColumn[] tableColumns = new TableColumn[columnInfo.length];

		for (int i = 0; i < tableColumns.length; i++)
			tableColumns[i] = new TableColumn(columnInfo[i][0],
					columnInfo[i][1]);

		return tableColumns;
	}

	/** Gets raw data for a row from the table. */
	protected HashMap<String, Object> getRowData(int primaryKey) {
		return DatabaseManager.getRow(this, primaryKey);
	}
	
	/** Creates the table. */
	private void createTable() {
		DatabaseManager.createTable(this);
	}

	public String getName() {
		return name;
	}

	public TableColumn[] getColumns() {
		return columns;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}
	
	

}
