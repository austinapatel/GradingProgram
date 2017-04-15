
// Austin Patel & Jason Morris
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
	public static String STUDENTS_TABLE_NAME = "Student";
	public static String STUDENT_ID = "studentId";
	public static String STUDENT_REDWOOD_ID = "studentRedwoodId";
	public static String FIRST_NAME = "firstName";
	public static String LAST_NAME = "lastName";
	public static String GENDER = "gender";
	public static String GRADUATION_YEAR = "graduationYear";
	public static String BIRTH_MONTH = "birthMonth";
	public static String BIRTH_DAY = "birthDay";
	public static String BIRTH_YEAR = "birthYear";
	// also uses "counselorId"

	// Courses
	public static String COURSES_TABLE_NAME = "Course";
	public static String COURSE_ID = "courseId";
	public static String NAME = "name";
	public static String PERIOD = "period";
	public static String START_YEAR = "startYear";
	public static String END_YEAR = "endYear";

	// Assignments
	public static String ASSIGNMENTS_TABLE_NAME = "Assignment";
	public static String ASSIGNMENT_ID = "assignmentId";
	public static String ASSIGNMENTS_VALUE = "value";
	public static String ASSIGNMENT_DATE = "assignmentDate";
	// Also uses same "name" field of the "Courses" table

	// Categories
	public static String CATEGORIES_TABLE_NAME = "Category";
	public static String CATEGORY_ID = "categoryId";
	// Also uses same "name" field of the "Courses" table
	// Also uses same "classID" as Assignments
	public static String WEIGHT = "weightPercent";

	// Enrollments
	public static String ENROLLMENTS_TABLE_NAME = "Enrollment";
	public static String ENROLLMENT_ID = "enrollmentId";
	// Also uses the "courseID" field of "Assignments" table
	// Also uses the "start year" field of "Courses" table
	// Also uses the "end year" field of "Courses" table

	// Grades
	public static String GRADES_TABLE_NAME = "Grade";
	public static String GRADE_ID = "gradeId";
	// Also uses studentId field of "Enrollments" table
	public static String GRADE_VALUE = "points";

	// Counselors
	public static String COUNSELORS_TABLE_NAME = "Counselor";
	public static String COUNSELOR_ID = "counselorId";
	// Also uses "name"

	// Grading Scales
	public static String SCALE_TABLE_NAME = "Scale";
	public static String SCALE_ID = "scaleId";
	public static String SCALE_DATA = "scaleData";
	public static String SCALE_DESCRIPTION = "scaleDescription";
}