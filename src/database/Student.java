
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 11/18/16
// Student.java

package database;

/**Data encapsulation class for "Student" table in database.*/
public class Student extends Table
{
	private String lastName, firstName, notes, gender;
	private int student_id, gradeLevel;
	
	public Student(String first, String last, String notes, String gender, int student_id, int gradeLevel)
	{
		this.gender = gender;
		this.lastName = last;
		this.firstName = first; 
		this.notes = notes;
		this.student_id = student_id;
		this.gradeLevel = gradeLevel;
	}
	
	public int getGradeLevel() {
		return gradeLevel;
	}

	public void setGradeLevel(int gradeLevel) {
		this.gradeLevel = gradeLevel;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String last)
	{
		this.lastName = last;
	}

	public String getFirstName()
	{
		return firstName;
	}
	
	public String getGender()
	{
		return gender;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}

	public void setFirstName(String first)
	{
		this.firstName = first;
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
