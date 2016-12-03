package database;

public class Student
{
	private boolean gender;
	private String last, first, notes;
	private int student_id;
	
	public Student(boolean gender, String last, String first, String notes, Integer student_id)
	{
		this.gender = gender;
		this.last = last;
		this.first = first; 
		this.notes = notes;
		this.student_id = student_id;
	}

	public boolean isGender()
	{
		return gender;
	}

	public void setGender(boolean gender)
	{
		this.gender = gender;
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
