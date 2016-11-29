
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 11/22/16
// Table.java

package database;

/** Holds information regarding a specific table in the database. */
public class Table {
	private String name, primaryKey;
	private TableColumn[] columns;

	public Table(String name, String primaryKey, TableColumn[] columns) {
		this.name = name;
		this.columns = columns;
		this.primaryKey = primaryKey;
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
