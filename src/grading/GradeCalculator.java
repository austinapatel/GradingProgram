package grading;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DataTypeManager;
import database.DatabaseManager;
import database.Table;
import database.TableManager;
import database.TableProperties;

public class GradeCalculator 
{

	private static ArrayList<GradingScale> scales = new ArrayList();
	
	public static ArrayList<GradingScale> getScales() {
		
//		for (int i = 0; i < scales.size(); i++)
//		{
//			scales.remove(i);
//		}
		scales.clear();
		//.scalesscales = new ArrayList<>();
		
		System.out.println(scales.size());
		TableManager.getTable(TableProperties.SCALE_TABLE_NAME).update();
		ArrayList<String> scaleStrings = DataTypeManager.toStringArrayList(TableManager.getTable(TableProperties.SCALE_TABLE_NAME).getAllFromColumn(TableProperties.SCALE_DATA));
		ArrayList<String> scaleNames = DataTypeManager.toStringArrayList(TableManager.getTable(TableProperties.SCALE_TABLE_NAME).getAllFromColumn(TableProperties.SCALE_DESCRIPTION));

		for (int i = 0; i < scaleStrings.size(); i++)
			scales.add(new GradingScale(scaleStrings.get(i), scaleNames.get(i)));
	

//		for (GradingScale scale : scales)
//			System.out.println(scale.getString());
		
		return scales;
	}
	
	public static void deleteScale(String name)
	{	
		
		Table table = TableManager.getTable(TableProperties.SCALE_TABLE_NAME);
		System.out.println(table.deleteRow(name, table.getColumnIndex(TableProperties.SCALE_DESCRIPTION) + 1));

	}
	

	
	
	
	
	
	
	
	private static boolean idExists(ArrayList<StudentGrade> grades, int id)
	{
		
		
		return false;
	}
	
	public static void getStudentGrades(String courseId)
	{
		ResultSet rs = getGrades(courseId, TableProperties.STUDENTS_TABLE_NAME + "." + TableProperties.STUDENT_ID);
		
		
		
		
		
		
		
	}
	
	
	
	
	public static ResultSet getGrades(String courseId, String groupBy)
	{	
//		return  DatabaseManager.getJoinedTable(TableProperties.GRADES_TABLE_NAME, 
//				TableProperties.ASSIGNMENTS_TABLE_NAME, new String[] {TableProperties.GRADES_TABLE_NAME + "." + 
//		TableProperties.STUDENT_ID, TableProperties.GRADES_TABLE_NAME + "." + TableProperties.GRADE_VALUE,
//		TableProperties.ASSIGNMENTS_TABLE_NAME + "." + TableProperties.ASSIGNMENTS_VALUE}, 
//				TableProperties.ASSIGNMENT_ID, TableProperties.ASSIGNMENT_ID, 
//				TableProperties.ASSIGNMENTS_TABLE_NAME + "." + TableProperties.COURSE_ID, courseId);

		return DatabaseManager.getTripleJoinedTable(TableProperties.GRADES_TABLE_NAME, 
				TableProperties.STUDENTS_TABLE_NAME, TableProperties.ASSIGNMENTS_TABLE_NAME, 
				new String[][] {{TableProperties.STUDENTS_TABLE_NAME, TableProperties.FIRST_NAME, 
					TableProperties.LAST_NAME},{TableProperties.GRADES_TABLE_NAME, TableProperties.STUDENT_ID, 
						TableProperties.GRADE_VALUE} , {TableProperties.ASSIGNMENTS_TABLE_NAME, TableProperties.ASSIGNMENTS_VALUE}},
				TableProperties.STUDENT_ID, TableProperties.STUDENT_ID, TableProperties.ASSIGNMENT_ID, TableProperties.ASSIGNMENT_ID,
				TableProperties.ASSIGNMENTS_TABLE_NAME + "." + TableProperties.COURSE_ID, courseId, groupBy);
		
		
		
		
		
		
	}
	
}
