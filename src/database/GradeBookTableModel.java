package database;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.util.ArrayList;

public class GradeBookTableModel extends AbstractTableModel
{

	private Table studentsTable, assignmentsTable, enrollmentsTable, gradesTable;

	private int courseId;

	private String[][] data;
	private String[] columnHeaders;

	private ArrayList<Integer> studentIds, assignmentIds;

	public GradeBookTableModel(int courseId)
	{
		this.courseId = courseId;

		loadTables();
		refresh();
	}

	/**
	  * Loads all of the data into the table.
	  */
	public void refresh()
	{
		studentIds = DataTypeManager.toIntegerArrayList(enrollmentsTable.getSomeFromColumn(TableProperties.STUDENT_ID,
					new Search(TableProperties.COURSE_ID, courseId)));
		assignmentIds = DataTypeManager.toIntegerArrayList(assignmentsTable
					.getSomeFromColumn(TableProperties.ASSIGNMENT_ID, new Search(TableProperties.COURSE_ID, courseId)));

		System.out.println("student ids:");
		for (int studentID : studentIds)
		{
			System.out.println(studentID);
		}

		System.out.println("assignment ids");
		for (int assignmentId : assignmentIds)
		{
			System.out.println(assignmentId);
		}

		System.out.println("NUMBER OF ASSIGNMENTS: " + assignmentIds.size());

		// Get the data and put it into the row
		ArrayList<ArrayList<String>> rowsList = new ArrayList<>();

		for (int i = 0; i < studentIds.size(); i++)
		{
			int studentId = studentIds.get(i);

			ArrayList<String> currentRow = new ArrayList<>();
			// Convert a student id to a name
			String firstName = DataTypeManager.toStringArrayList(studentsTable
						.getSomeFromColumn(TableProperties.FIRST_NAME, new Search(TableProperties.STUDENT_ID, studentId)))
						.get(0);
			String lastName = DataTypeManager.toStringArrayList(studentsTable.getSomeFromColumn(TableProperties.LAST_NAME,
						new Search(TableProperties.STUDENT_ID, studentId + ""))).get(0);

			currentRow.add(firstName + " " + lastName);

			// Now add each of the student's scores to the row
			for (int j = 0; j < assignmentIds.size(); j++)
			{
				int assignmentId = assignmentIds.get(j);
				ArrayList<Double> grades = DataTypeManager.toDoubleArrayList(gradesTable.getSomeFromColumn(
							TableProperties.GRADE_VALUE, new Search(TableProperties.STUDENT_ID, studentId),
							new Search(TableProperties.ASSIGNMENT_ID, assignmentId)));

				if (grades.size() > 0)
					if (grades.get(0) == -1)
						currentRow.add("Excused");
					else
						currentRow.add(grades.get(0) + "");
				else
					currentRow.add("Missing");
			}

			rowsList.add(currentRow);
		}

		data = new String[rowsList.size()][assignmentIds.size() + 1];

		for (int x = 0; x < data.length; x++)
		{
			for (int y = 0; y < data[x].length; y++)
			{
				data[x][y] = rowsList.get(x).get(y);
			}
		}

		ArrayList<String> columnsList = new ArrayList<>();
		columnsList.add("Student Name");

		for (int i = 0; i < assignmentIds.size(); i++)
		{
			String assignmentName = DataTypeManager
						.toStringArrayList(assignmentsTable.getSomeFromColumn(TableProperties.NAME,
									new Search(TableProperties.ASSIGNMENT_ID, assignmentIds.get(i))))
						.get(0);
			int assignmentValue = DataTypeManager
						.toIntegerArrayList(assignmentsTable.getSomeFromColumn(TableProperties.ASSIGNMENTS_VALUE,
									new Search(TableProperties.ASSIGNMENT_ID, assignmentIds.get(i))))
						.get(0);

			columnsList.add(assignmentName + " (" + assignmentValue + ")");
		}

		columnHeaders = new String[columnsList.size()];

		for (int i = 0; i < columnHeaders.length; i++)
			columnHeaders[i] = columnsList.get(i);
	}

	private void loadTables()
	{
		studentsTable = TableManager.getTable(TableProperties.STUDENTS_TABLE_NAME);
		assignmentsTable = TableManager.getTable(TableProperties.ASSIGNMENTS_TABLE_NAME);
		enrollmentsTable = TableManager.getTable(TableProperties.ENROLLMENTS_TABLE_NAME);
		gradesTable = TableManager.getTable(TableProperties.GRADES_TABLE_NAME);
	}

	@Override
	public int getRowCount()
	{
		return data.length;
	}

	@Override
	public int getColumnCount()
	{
		return columnHeaders.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		//        if (columnIndex == 0) {
		//            String first =  DataTypeManager.toStringArrayList(studentsTable.getSomeFromColumn(TableProperties.FIRST_NAME, new Search(TableProperties.STUDENT_ID, studentIds.get(rowIndex)))).get(0);
		//            String last =  DataTypeManager.toStringArrayList(studentsTable.getSomeFromColumn(TableProperties.LAST_NAME, new Search(TableProperties.STUDENT_ID, studentIds.get(rowIndex)))).get(0);
		//
		//            return first + " " + last;
		//        }
		//
		//        int studentId = studentIds.get(rowIndex);
		//        int assignmentId = assignmentIds.get(columnIndex - 1);
		//
		//        ResultSet resultSet = gradesTable.select(new Filter(new Search(TableProperties.STUDENT_ID, studentId), new Search(TableProperties.ASSIGNMENT_ID, assignmentId)));
		//
		//        try {
		//            resultSet.absolute(1);
		//
		//            return resultSet.getDouble(TableProperties.GRADE_VALUE);
		//        } catch (SQLException e) {
		//            return "Missing";
		////            e.printStackTrace();
		//        }

		return data[rowIndex][columnIndex];
	}

	@Override
	public void setValueAt(Object rawValue, int rowIndex, int columnIndex)
	{
		int studentId = studentIds.get(rowIndex);
		int assignmentId = assignmentIds.get(columnIndex - 1);

		ResultSet resultSet = gradesTable.select(new Filter(new Search(TableProperties.STUDENT_ID, studentId),
					new Search(TableProperties.ASSIGNMENT_ID, assignmentId)));

		try
		{
			if(rawValue.equals("ex")){
				rawValue = -1;
			}
			double value = Double.parseDouble(rawValue.toString());

			resultSet.absolute(1);
			resultSet.updateDouble(TableProperties.GRADE_VALUE, value);
			resultSet.updateRow();

			data[rowIndex][columnIndex] = resultSet.getDouble(TableProperties.GRADE_VALUE) + "";
		}
		catch (NumberFormatException e)
		{
			// Ignore non-double values
			return;
		}
		catch (Exception e)
		{
			System.out.println(rawValue);
			e.printStackTrace();
		}

		//        try {
		//            resultSet.last();
		//            int rowCount = resultSet.getRow();
		//            System.out.println("Return row count: " + rowCount);
		//        } catch (SQLException e) {
		//            e.printStackTrace();
		//        }
		
		if (Double.parseDouble(data[rowIndex][columnIndex]) == -1) {
			data[rowIndex][columnIndex] = "Excused";
		}
	}

	@Override
	public boolean isCellEditable(int row, int col)
	{
		return col != 0;
	}

	@Override
	public String getColumnName(int col)
	{
		return columnHeaders[col];
	}
}
