
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 11/18/16
// Student.java

package database;

import java.sql.ResultSet;

/** Data encapsulation class for "Student" table in database. */
public class Student extends TableRow
{
	public final static String PROPERTY_ID = "id", PROPERTY_FIRST_NAME = "first", PROPERTY_LAST_NAME = "last",
				PROPERTY_NOTES = "notes", PROPERTY_GENDER = "gender", PROPERTY_GRADE_LEVEL = "gradeLevel";

	public Student(ResultSet resultSet)
	{
		super(resultSet);
	}
}
