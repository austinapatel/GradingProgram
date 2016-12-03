
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 12/2/16
// Students.java

package database;

import java.util.HashMap;

/** Holds data for the Students table in the database. */
public class Students extends Table {
	
	public static void main(String[] args) {
		Object object = new Integer(3);
		System.out.println(object instanceof String);
	}

	private static final String TABLE_NAME = "Students", PRIMARY_KEY = "id";
	private static final String[][] COLUMN_INFO = new String[][] {
			{ "id", "INT NOT NULL UNIQUE" },
			{ "firstName", "VARCHAR(20) NOT NULL" },
			{ "lastName", "VARCHAR(20) NOT NULL" }, { "notes", "VARCHAR(255)" },
			{ "gender", "CHAR(1)" }, { "gradeLevel", "INT NOT NULL" } };

	public Students() {
		super(TABLE_NAME, PRIMARY_KEY, COLUMN_INFO);
	}

	/** Returns a Student given a primaryKey. */
	public Student getRow(int primaryKey) {
		HashMap<String, Object> rawRowData = super.getRowData(primaryKey);
		
		return new Student((String) rawRowData.get("firstName"),
				(String) rawRowData.get("lastName"),
				(String) rawRowData.get("notes"),
				((Character) rawRowData.get("gender")),
				((Integer) rawRowData.get("student_id")).intValue(),
				((Integer) rawRowData.get("gradeLevel")).intValue());
	}
	
	/**Adds a student to the students table.*/
	public void addStudent(Student student) {
		
	}

}
