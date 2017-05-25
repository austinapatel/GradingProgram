package visuals;

import database.TableProperties;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import database.DataTypeManager;
import database.Table;
import database.TableManager;

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

	@Override
	public void onLayoutOpened() {

	}

	private void initTables()
	{
		studentsJTable = new DatabaseJTable(TableProperties.STUDENTS_TABLE_NAME);
		coursesJTable = new DatabaseJTable(TableProperties.COURSES_TABLE_NAME);

		add(studentsJTable.getTableHeader());
		add(studentsJTable);
		
		studentsJTable.getSelectionModel().addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				// TODO Auto-generated method stub
				if(studentsJTable.getSelectedRow() >= 0 && coursesJTable.getSelectedRow() >= 0)
					enrollButton.setEnabled(true);
			}
		});
		
		add(coursesJTable.getTableHeader());
		add(coursesJTable);
		coursesJTable.getSelectionModel().addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				// TODO Auto-generated method stub
				if(studentsJTable.getSelectedRow() >= 0 && coursesJTable.getSelectedRow() >= 0)
					enrollButton.setEnabled(true);
			}
		});

		add(enrollButton = new JButton("Enroll Student"));
		enrollButton.setEnabled(false);
		enrollButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				Table studentsTable = TableManager.getTable(TableProperties.STUDENTS_TABLE_NAME);
				ArrayList<Integer> studentIds = DataTypeManager
							.toIntegerArrayList(studentsTable.getAllFromColumn(TableProperties.STUDENT_ID));

				int studentId = studentIds.get(studentsJTable.getSelectedRow());

				Table coursesTable = TableManager.getTable(TableProperties.COURSES_TABLE_NAME);
				ArrayList<Integer> courseIds = DataTypeManager
							.toIntegerArrayList(coursesTable.getAllFromColumn(TableProperties.COURSE_ID));

				int courseId = courseIds.get(coursesJTable.getSelectedRow());

				Table enrollmentsTable = TableManager.getTable(TableProperties.ENROLLMENTS_TABLE_NAME);
				
				courseIds = DataTypeManager.toIntegerArrayList(enrollmentsTable.getAllFromColumn(TableProperties.COURSE_ID));
				studentIds = DataTypeManager.toIntegerArrayList(enrollmentsTable.getAllFromColumn(TableProperties.STUDENT_ID));
				
				boolean flag = false;
				for(int i = 0; i < courseIds.size(); i++){
					if(courseIds.get(i) == courseId && studentIds.get(i) == studentId)
						flag = true;
				}
				
				if(flag == false)
				{
					HashMap<String, Object> enrollmentsVals = new HashMap<String, Object>()
					{
						{
							put(TableProperties.STUDENT_ID, studentId);
							put(TableProperties.COURSE_ID, courseId);
						}
					};
					TableManager.insertValuesIntoNewRow(enrollmentsTable, enrollmentsVals);
				};
			}
		});
	}
	
//	void addActionListener(ActionEvent e){
//		if(studentsJTable.getSelectedRow() != -1 && coursesJTable.getSelectedRow() != -1){
//			enrollButton.setEnabled(true);
//		}
//		else
//			enrollButton.setEnabled(false);
//	}
	
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
