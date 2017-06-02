package grading;

import database.*;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class GradeCalculator {

    private static Table assignmentsTable, gradesTable;

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

    public static void init() {
        assignmentsTable = TableManager.getTable(TableProperties.ASSIGNMENTS_TABLE_NAME);
        gradesTable = TableManager.getTable(TableProperties.GRADES_TABLE_NAME);
    }

    public static Grade getGrade(int studentId, int courseId) {
        int totalPoints = GradeCalculator.getTotalPoints(courseId);

        // Get the students point total
        Table joined = gradesTable.join(assignmentsTable, new Selection(new ColumnIdentifier(TableProperties.GRADES_TABLE_NAME, TableProperties.GRADE_VALUE)), TableProperties.ASSIGNMENT_ID, TableProperties.ASSIGNMENT_ID, new Filter(new Search(TableProperties.GRADES_TABLE_NAME, TableProperties.STUDENT_ID, studentId), new Search(TableProperties.ASSIGNMENTS_TABLE_NAME, TableProperties.COURSE_ID, courseId)));

        ArrayList<Double> studentPoints = DataTypeManager.toDoubleArrayList(joined.getAllFromColumn(TableProperties.GRADE_VALUE));

        int studentPointSum = 0;

        for (double d : studentPoints)
            studentPointSum += d;

        return new Grade(totalPoints, studentPointSum);
    }

    private static int getTotalPoints(int courseId) {
        ArrayList<Integer> pointValues = DataTypeManager.toIntegerArrayList(assignmentsTable.getSomeFromColumn(TableProperties.ASSIGNMENTS_VALUE, new Search(TableProperties.COURSE_ID, courseId)));

        int sum = 0;

        for (int i : pointValues)
            sum += i;

        return sum;
    }

}
