
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

	private String lastName, firstName, notes;
	private char gender;
	private int student_id, gradeLevel;
	
	private ResultSet data;


	public Student(int student_id, String first, String last, String notes, char gender, int gradeLevel)
	{
		this.student_id = student_id;
		this.firstName = first;
		this.lastName = last;
		this.notes = notes;
		this.gender = gender;
		this.gradeLevel = gradeLevel;
	}
	
	public Student(ResultSet data)
	{
		this.data = data;
	}
}
