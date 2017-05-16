package visuals;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;

import database.DataTypeManager;
import database.Table;
import database.TableManager;
import database.TableProperties;

/**
 * Created by austin.patel on 5/15/2017.
 */
public class EnrollmentsInterface extends InterfacePanel
{

	private JButton enrollButton;
	private DatabaseJTable studentsJTable, coursesJTable;

	public EnrollmentsInterface()
	{
		initTables();
	}

	private void initTables()
	{
		studentsJTable = new DatabaseJTable(TableProperties.STUDENTS_TABLE_NAME);
		coursesJTable = new DatabaseJTable(TableProperties.COURSES_TABLE_NAME);

		add(studentsJTable.getTableHeader());
		add(studentsJTable);

		add(coursesJTable.getTableHeader());
		add(coursesJTable);

		add(enrollButton = new JButton("Enroll Student"));
		enrollButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				Table studentsTable = TableManager.getTable(TableProperties.STUDENTS_TABLE_NAME);
				ArrayList<Integer> studentIds = DataTypeManager.toIntegerArrayList(studentsTable.getAllFromColumn(TableProperties.STUDENT_ID));
				
				int studentId = studentIds.get(studentsJTable.getSelectedRow());
				
				Table coursesTable = TableManager.getTable(TableProperties.COURSES_TABLE_NAME);
				ArrayList<Integer> courseIds = DataTypeManager.toIntegerArrayList(coursesTable.getAllFromColumn(TableProperties.COURSE_ID));

				int courseId = courseIds.get(coursesJTable.getSelectedRow());
				
				Table enrollmentsTable = TableManager.getTable(TableProperties.ENROLLMENTS_TABLE_NAME);

				HashMap<String, Object> enrollmentsVals = new HashMap<String, Object>()
				{
					{
						put(TableProperties.STUDENT_ID, studentId);
						put(TableProperties.COURSE_ID, courseId);
					}
				};

				TableManager.insertValuesIntoNewRow(enrollmentsTable, enrollmentsVals);
			}
		});
	}

	@Override
	public void keyTyped(KeyEvent e)
	{

	}

	@Override
	public void keyPressed(KeyEvent e)
	{

	}

	@Override
	public void keyReleased(KeyEvent e)
	{

	}
}
