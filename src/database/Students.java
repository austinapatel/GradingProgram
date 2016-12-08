
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 12/2/16
// Students.java

package database;

import java.lang.reflect.Constructor;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/** Holds data for the Students table in the database. */
public class Students extends Table {

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
		ArrayList dit = new ArrayList();
		dit.add("");
		dit.add(0);
		
	
		
		HashMap<String, Object> rawRowData = super.getRowData(primaryKey);
		
		//System.out.println(Student.class.getDeclaredConstructors()[0].getParameterTypes()[0].getName());
		Class<?>[] types = Student.class.getDeclaredConstructors()[0].getParameterTypes();
		
		//System.out.println(types[0].getName());
		//System.out.println(types[0].cSast(rawRowData.get("lastName")));
		System.out.println(types[0].cast(rawRowData.get("lastName")).getClass());
		
		
		System.out.println(rawRowData.get("lastName") instanceof String);
		
		
//		String lastname = (rawRowData.get("lastName");
		
		
		
		
		
		
		Student student = new Student((String) rawRowData.get("firstName"),
				(String) rawRowData.get("lastName"),
				(String) rawRowData.get("notes"),
				((String) rawRowData.get("gender")).charAt(0),
				((Integer) rawRowData.get("id")).intValue(),
				((Integer) rawRowData.get("gradeLevel")).intValue());
	
		return student;
	}
	
	/**Adds a student to the students table.*/
	public void addStudent(Student student) {
		PreparedStatement statement = super.addRow();
		
		try
		{
			statement.setInt(1, student.getStudent_id());
			statement.setString(2, student.getFirstName());
			statement.setString(3, student.getLastName());
			statement.setString(4, student.getNotes());
			statement.setString(5, String.valueOf(student.getGender()));
			statement.setInt(6, student.getGradeLevel());
			
			statement.executeUpdate();
		}
		catch (Exception error)
		{
			System.out.println("Failed to insert student into table.");
		}
	}

}
