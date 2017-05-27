package database;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class GradeBookTableModel extends AbstractTableModel {

    private Table studentsTable, assignmentsTable, enrollmentsTable, gradesTable;

    private int courseId;

    private String[][] rows;
    private String[] columns;

    public GradeBookTableModel(int courseId) {
        this.courseId = courseId;

        loadTables();
        loadData();
    }

    private void loadData() {
        ArrayList<Integer> studentIds = DataTypeManager.toIntegerArrayList(enrollmentsTable.getSomeFromColumn(TableProperties.STUDENT_ID, new Search(TableProperties.COURSE_ID, courseId)));
        ArrayList<Integer> assignmentIds = DataTypeManager.toIntegerArrayList(assignmentsTable.getSomeFromColumn(TableProperties.ASSIGNMENT_ID, new Search(TableProperties.COURSE_ID, courseId)));

        System.out.println("student ids:");
        for (int studentID : studentIds) {
            System.out.println(studentID);
        }

        System.out.println("assignment ids");
        for (int assignmentId : assignmentIds) {
            System.out.println(assignmentId);
        }

        System.out.println("NUMBER OF ASSIGNMENTS: " + assignmentIds.size());

        // Get the data and put it into the row
        ArrayList<ArrayList<String>> rowsList = new ArrayList<>();

        for (int i = 0; i < studentIds.size(); i++) {
            int studentId = studentIds.get(i);

            ArrayList<String> currentRow = new ArrayList<>();
            // Convert a student id to a name
            String firstName = DataTypeManager.toStringArrayList(studentsTable.getSomeFromColumn(TableProperties.FIRST_NAME, new Search(TableProperties.STUDENT_ID, studentId))).get(0);
            String lastName = DataTypeManager.toStringArrayList(studentsTable.getSomeFromColumn(TableProperties.LAST_NAME, new Search(TableProperties.STUDENT_ID, studentId + ""))).get(0);

            currentRow.add(firstName + " " + lastName);

            // Now add each of the student's scores to the row
            for (int j = 0; j < assignmentIds.size(); j++) {
                int assignmentId = assignmentIds.get(j);
                ArrayList<Double> grades = DataTypeManager.toDoubleArrayList(gradesTable.getSomeFromColumn(TableProperties.GRADE_VALUE, new Search(TableProperties.STUDENT_ID, studentId), new Search(TableProperties.ASSIGNMENT_ID, assignmentId)));

                if (grades.size() > 0)
                    currentRow.add(grades.get(0) + "");
                else
                    currentRow.add("Missing");
            }

            rowsList.add(currentRow);
        }

        rows = new String[rowsList.size()][assignmentIds.size() + 1];

        for (int x = 0; x < rows.length; x++) {
            for (int y = 0; y < rows[x].length; y++) {
                rows[x][y] = rowsList.get(x).get(y);
            }
        }

        ArrayList<String> columnsList = new ArrayList<>();
        columnsList.add("Student Name");

        for (int i = 0; i < assignmentIds.size(); i++) {
            String assignmentName = DataTypeManager.toStringArrayList(assignmentsTable.getSomeFromColumn(TableProperties.NAME, new Search(TableProperties.ASSIGNMENT_ID, assignmentIds.get(i)))).get(0);
            int assignmentValue = DataTypeManager.toIntegerArrayList(assignmentsTable.getSomeFromColumn(TableProperties.ASSIGNMENTS_VALUE, new Search(TableProperties.ASSIGNMENT_ID, assignmentIds.get(i)))).get(0);

            columnsList.add(assignmentName + " (" + assignmentValue + ")");
        }

        columns = new String[columnsList.size()];

        for (int i = 0; i < columns.length; i++)
            columns[i] = columnsList.get(i);
    }

    private void loadTables() {
        studentsTable = TableManager.getTable(TableProperties.STUDENTS_TABLE_NAME);
        assignmentsTable = TableManager.getTable(TableProperties.ASSIGNMENTS_TABLE_NAME);
        enrollmentsTable = TableManager.getTable(TableProperties.ENROLLMENTS_TABLE_NAME);
        gradesTable = TableManager.getTable(TableProperties.GRADES_TABLE_NAME);
    }

    @Override
    public int getRowCount() {
        return rows.length;
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return rows[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {

    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col != 0;
    }

    @Override
    public String getColumnName(int col) {
        return columns[col];
    }
}
