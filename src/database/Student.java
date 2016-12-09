
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 11/18/16
// Student.java

package database;

import java.sql.ResultSet;

/** Data encapsulation class for "Student" table in database. */
public class Student
{

	public final static String PROPERTY_ID = "id";
	
	private String lastName, firstName, notes;
	private char gender;
	private int student_id, gradeLevel;
	private ResultSet resultSet;

	public Student(Object[] objects)
	{
		this.student_id = ((Integer) objects[0]).intValue();
		this.firstName = (String) objects[1];
		this.lastName = (String) objects[2];

		this.notes = (String) objects[3];
		this.gender = ((Character) objects[4]).charValue();
		this.gradeLevel = ((Integer) objects[5]).intValue();
	}
	
	public Student(ResultSet resultSet)
	{
		this.resultSet = resultSet;
	}

	public Student(int student_id, String first, String last, String notes, char gender, int gradeLevel)
	{
		this.student_id = student_id;
		this.firstName = first;
		this.lastName = last;
		this.notes = notes;
		this.gender = gender;
		this.gradeLevel = gradeLevel;
	}
	
}
