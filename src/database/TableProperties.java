
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 12/23/16
// TableProperties.java

package database;

/** Contains the names of properties of table columns for the database. */
public class TableProperties {

	public TableProperties() {
		
	}
	
	// All tables
	public static String ID = "id";
	
	// Students
	public static String STUDENTS_TABLE_NAME = "Students";
	public static String STUDENT_ID = "studentId";
	public static String FIRST_NAME = "firstName";
	public static String LAST_NAME = "lastName";
	public static String NOTES = "notes";
	public static String GENDER = "gender";
	public static String GRADE_LEVEL = "gradeLevel";
	
	// Courses
	public static String COURSES_TABLE_NAME = "Courses";
	public static String NAME = "name";
	public static String PERIOD = "period";
	
	// Assignments
	public static String ASSIGNMENTS_TABLE_NAME = "Assignments";
	public static String COURSE_ID = "classId";
	public static String CATEGORY_ID = "categoryId";
	public static String VALUE = "value";
	// Also uses same "name" field of the "Courses" table
	
	// Categories
	public static String CATEGORIES_TABLE_NAME = "Categories";
	// Also uses same "name" field of the "Courses" table
	// Also uses same "classID" as Assignments
	public static String WEIGHT = "weightPercent";
	
	// Enrollments
	public static String ENROLLMENTS_TABLE_NAME = "Enrollments";
	public static String YEAR = "year";
	// Also uses the "courseID" field of "Assignments" table
	public static String STUDENTS_ID = "studentId";
}
