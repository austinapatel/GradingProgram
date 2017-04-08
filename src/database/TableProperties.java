
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 12/23/16
// TableProperties.java

package database;

/** Contains the names of properties of table columns for the database. */
public class TableProperties
{

	public TableProperties()
	{

	}

	// All tables
	public static String ALL = "*";
	// Students
	public static String STUDENT_ID = "studentId";
	public static String STUDENTS_TABLE_NAME = "Students";
	public static String STUDENT_REDWOOD_ID = "studentRedwoodId";
	public static String FIRST_NAME = "firstName";
	public static String LAST_NAME = "lastName";
	public static String NOTES = "notes";
	public static String GENDER = "gender";
	public static String GRADE_LEVEL = "gradeLevel";

	// Courses
	public static String COURSE_ID = "courseId";
	public static String COURSES_TABLE_NAME = "Courses";
	public static String NAME = "name";
	public static String PERIOD = "period";
	public static String START_YEAR = "startYear";
	public static String END_YEAR = "endYear";

	// Assignments
	public static String ASSIGNMENT_ID = "assignmentId";
	public static String ASSIGNMENTS_TABLE_NAME = "Assignments";
	public static String VALUE = "value";
	// Also uses same "name" field of the "Courses" table

	// Categories
	public static String CATEGORY_ID = "categoryId";
	public static String CATEGORIES_TABLE_NAME = "Categories";
	// Also uses same "name" field of the "Courses" table
	// Also uses same "classID" as Assignments
	public static String WEIGHT = "weightPercent";

	// Enrollments
	public static String ENTROLLMENT_ID = "entrollmentId";
	public static String ENROLLMENTS_TABLE_NAME = "Enrollments";
	// Also uses the "courseID" field of "Assignments" table
	// Also uses the "start year" field of "Courses" table
	// Also uses the "end year" field of "Courses" table

	// Grades
	public static String GRADE_ID = "gradeId";
	public static String GRADES_TABLE_NAME = "Grades";
	// Also uses studentId field of "Enrollments" table
	public static String GRADE_VALUE = "points";
	///public static String COU
	
	
	public static String SCALE_ID = "scaleId";
	public static String SCALE_TABLE_NAME = "scale";
	
	
	
	
}