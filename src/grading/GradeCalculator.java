package grading;

import database.*;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class GradeCalculator {

    private static ArrayList<GradingScale> scales = new ArrayList<>();

    public static ArrayList<GradingScale> getScales() {
        scales.clear();

        TableManager.getTable(TableProperties.SCALE_TABLE_NAME).refresh();
        ArrayList<String> scaleStrings = DataTypeManager.toStringArrayList(TableManager.getTable(TableProperties.SCALE_TABLE_NAME).getAllFromColumn(TableProperties.SCALE_DATA));
        ArrayList<String> scaleNames = DataTypeManager.toStringArrayList(TableManager.getTable(TableProperties.SCALE_TABLE_NAME).getAllFromColumn(TableProperties.SCALE_DESCRIPTION));

        for (int i = 0; i < scaleStrings.size(); i++)
            scales.add(new GradingScale(scaleStrings.get(i), scaleNames.get(i)));

        return scales;
    }

    public static void deleteScale(String name) {
        Table table = TableManager.getTable(TableProperties.SCALE_TABLE_NAME);
        table.deleteRow(name, table.getColumnIndex(TableProperties.SCALE_DESCRIPTION) + 1);
    }

    public static ArrayList<Grade> getStudentGrades(int courseId) {
        ResultSet rs = getGrades(courseId, null);
        ArrayList<Grade> studentGrades = new ArrayList();
        // Get assignment scores from table
        Table table = new Table(rs);
        Table enrollmentsTable = TableManager.getTable(TableProperties.ENROLLMENTS_TABLE_NAME);

        // ArrayList<Integer> studentIds = DataTypeManager.toIntegerArrayList(enrollmentsTable.getSomeFromColumn(TableProperties.STUDENT_ID, TableProperties.COURSE_ID, String.valueOf(courseId)));
        ArrayList<Double> pointValues = DataTypeManager.toDoubleArrayList(table.getAllFromColumn(TableProperties.GRADE_VALUE));
        ArrayList<Integer> studentIds = DataTypeManager.toIntegerArrayList(table.getAllFromColumn(TableProperties.STUDENT_ID));

        // Initialize hashmap with blank values
        HashMap<Integer, Double> grades = new HashMap<>();

        for (int i : studentIds)
            grades.put(i, 0.0);

        // Tally up each student's scores
        for (int i = 0; i < studentIds.size(); i++)
            grades.put(studentIds.get(i), grades.get(studentIds.get(i)) + pointValues.get(i));

        // Calculate the total number of possible points
        Table assignmentsTable = TableManager.getTable(TableProperties.ASSIGNMENTS_TABLE_NAME);
        ArrayList<Double> assignmentPoints = DataTypeManager.toDoubleArrayList(assignmentsTable.getSomeFromColumn(TableProperties.ASSIGNMENTS_VALUE, new Search(TableProperties.COURSE_ID, courseId)));
        double totalPoints = 0;
        for (double d : assignmentPoints)
            totalPoints += d;

        Iterator<Integer> uniqueStudentIds = grades.keySet().iterator();

        while (uniqueStudentIds.hasNext()) {
            int id = uniqueStudentIds.next();
            System.out.println("Student id: " + id + " got a sum of " + grades.get(id) + " a percent grade of " + (grades.get(id) * 100 / totalPoints) + "%");

            studentGrades.add(new Grade(totalPoints, grades.get(id), id));
        }

        return studentGrades;
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
