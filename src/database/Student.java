
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 11/18/16
// Student.java

package database;

/**Data encapsulation class for "Student" table in database.*/
public class Student extends Table
{
	private String last, first, notes, gender;
	private int student_id;
	
	public Student(String gender, String last, String first, String notes, Integer student_id)
	{
		this.gender = gender;
		this.last = last;
		this.first = first; 
		this.notes = notes;
		this.student_id = student_id;
	}

	public String getLast()
	{
		return last;
	}

	public void setLast(String last)
	{
		this.last = last;
	}

	public String getFirst()
	{
		return first;
	}
	
	public String getGender()
	{
		return gender;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}

	public void setFirst(String first)
	{
		this.first = first;
	}

	public String getNotes()
	{
		return notes;
	}

	public void setNotes(String notes)
	{
		this.notes = notes;
	}

	public int getStudent_id()
	{
		return student_id;
	}

	public void setStudent_id(int student_id)
	{
		this.student_id = student_id;
	}
}
