
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 11/22/16
// TableColumn.java

package database;

/** Holds information for a specific column in a database table. */
public class TableColumn {

	private String name, type;
	private boolean isText;

	public TableColumn(String name, String type) {
		this.name = name;
		this.type = type;
		
		isText = type.contains("VARCHAR") || type.contains("CHAR");
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}
	
	public boolean isText() {
		return isText;
	}

}
