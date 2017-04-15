package grading;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import database.*;

public class GradeCalculator {

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

    public static void deleteScale(String name) {

        Table table = TableManager.getTable(TableProperties.SCALE_TABLE_NAME);
        System.out.println(table.deleteRow(name, table.getColumnIndex(TableProperties.SCALE_DESCRIPTION) + 1));

    }


    private static boolean idExists(ArrayList<StudentGrade> grades, int id) {


        return false;
    }

    public static void getStudentGrades(int courseId) {
        ResultSet rs = getGrades(courseId, null);

//        try {
//            int primaryid = rs.findColumn(TableProperties.STUDENT_ID);
//        } catch (SQLException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }
//
//        Object[][] data = DatabaseManager.ResultSetToObjectArray(rs);
//
//        ArrayList<StudentGrade> grades = new ArrayList();
//
//        for (int j = 0; j < data[0].length; j++) {
//            Object[] row = new Object[data.length];
//
//            for (int i = 0; i < data.length; i++) {
//                row[i] = data[i][j];
//            }
//
//            grades.add(new StudentGrade(row));
//
//
//        }
        // Get assignment scores from table
        Table table = new Table("Grade Calculator", rs);
        Table enrollmentsTable = TableManager.getTable(TableProperties.ENROLLMENTS_TABLE_NAME);

        ArrayList<Integer> studentIds = DataTypeManager.toIntegerArrayList(enrollmentsTable.getSomeFromColumn(TableProperties.STUDENT_ID, TableProperties.COURSE_ID, String.valueOf(courseId)));
        ArrayList<Double> pointValues = DataTypeManager.toDoubleArrayList(table.getAllFromColumn(TableProperties.GRADE_VALUE));

        System.out.println("student ids: " + studentIds);
        System.out.println("point values: " + pointValues);

        // Initialize hashmap with blank values
        HashMap<Integer, Double> grades = new HashMap<>();

        for (int i : studentIds)
            grades.put(i, 0.0);

        // Tally up each student's scores
        for (int i = 0; i < studentIds.size(); i++) {
            System.out.println("before: " + grades.get(studentIds.get(i)));
            grades.put(studentIds.get(i), grades.get(studentIds.get(i)) + pointValues.get(i));
            System.out.println("after: " + grades.get(studentIds.get(i)));

        }

        // Calculate the total number of possible points
        Table assignmentsTable = TableManager.getTable(TableProperties.ASSIGNMENTS_TABLE_NAME);
        ArrayList<Double> assignmentPoints = DataTypeManager.toDoubleArrayList(assignmentsTable.getSomeFromColumn(TableProperties.ASSIGNMENTS_VALUE, TableProperties.COURSE_ID, String.valueOf(courseId)));
        double totalPoints = 0;
        for (double d : assignmentPoints)
            totalPoints += d;

        System.out.println("sum of assignment points values: " + totalPoints);

        Iterator<Integer> uniqueStudentIds = grades.keySet().iterator();
        while (uniqueStudentIds.hasNext()) {
            int id = uniqueStudentIds.next();
            System.out.println("Student id: " + id + " got a sum of " + grades.get(id) + " a percent grade of " + (grades.get(id) * 100 / totalPoints) + "%");
        }
    }


    public static ResultSet getGrades(int courseId, String groupBy) {
//		return  DatabaseManager.getJoinedTable(TableProperties.GRADES_TABLE_NAME, 
//				TableProperties.ASSIGNMENTS_TABLE_NAME, new String[] {TableProperties.GRADES_TABLE_NAME + "." + 
//		TableProperties.STUDENT_ID, TableProperties.GRADES_TABLE_NAME + "." + TableProperties.GRADE_VALUE,
//		TableProperties.ASSIGNMENTS_TABLE_NAME + "." + TableProperties.ASSIGNMENTS_VALUE}, 
//				TableProperties.ASSIGNMENT_ID, TableProperties.ASSIGNMENT_ID, 
//				TableProperties.ASSIGNMENTS_TABLE_NAME + "." + TableProperties.COURSE_ID, courseId);

        return DatabaseManager.getTripleJoinedTable(TableProperties.GRADES_TABLE_NAME,
                TableProperties.STUDENTS_TABLE_NAME, TableProperties.ASSIGNMENTS_TABLE_NAME,
                new String[][]{{TableProperties.STUDENTS_TABLE_NAME, TableProperties.FIRST_NAME,
                        TableProperties.LAST_NAME}, {TableProperties.GRADES_TABLE_NAME, TableProperties.STUDENT_ID,
                        TableProperties.GRADE_VALUE}, {TableProperties.ASSIGNMENTS_TABLE_NAME, TableProperties.ASSIGNMENTS_VALUE}},
                TableProperties.STUDENT_ID, TableProperties.STUDENT_ID, TableProperties.ASSIGNMENT_ID, TableProperties.ASSIGNMENT_ID,
                TableProperties.ASSIGNMENTS_TABLE_NAME + "." + TableProperties.COURSE_ID, String.valueOf(courseId), groupBy);


    }

}
