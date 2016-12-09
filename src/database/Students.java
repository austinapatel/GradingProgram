
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 12/2/16
// Students.java

package database;

import java.sql.PreparedStatement;

/** Holds data for the Students table in the database. */
public class Students extends Table
{

	private static final String TABLE_NAME = "Students", PRIMARY_KEY = "id";
	public static final String[][] COLUMN_INFO = new String[][] {{Student.PROPERTY_ID, "INT NOT NULL UNIQUE"},
				{Student.PROPERTY_FIRST_NAME, "VARCHAR(20) NOT NULL"}, {Student.PROPERTY_LAST_NAME, "VARCHAR(20) NOT NULL"},
				{Student.PROPERTY_NOTES, "VARCHAR(255)"}, {Student.PROPERTY_GENDER, "CHAR(1)"},
				{Student.PROPERTY_GRADE_LEVEL, "INT NOT NULL"}};

	public Students()
	{
		super(TABLE_NAME, PRIMARY_KEY, COLUMN_INFO);
	}

	/** Returns a Student given a primaryKey. */
	public Student getRow(int primaryKey)
	{
		return new Student(super.getRowData(primaryKey));
	}

	/**Adds a student to the students table.*/
	public void addStudent(Student student)
	{
		PreparedStatement statement = super.addRow();

		try
		{
			statement.setInt(1, student.getIntProperty(Student.PROPERTY_ID));
			statement.setString(2, student.getStringProperty(Student.PROPERTY_FIRST_NAME));
			statement.setString(3, student.getStringProperty(Student.PROPERTY_LAST_NAME));
			statement.setString(4, student.getStringProperty(Student.PROPERTY_NOTES));
			statement.setString(5, student.getStringProperty(Student.PROPERTY_GENDER));
			statement.setInt(6, student.getIntProperty(Student.PROPERTY_GRADE_LEVEL));

			statement.executeUpdate();
		}
		catch (Exception error)
		{
			System.out.println("Failed to insert student into table.");
		}
	}

}
