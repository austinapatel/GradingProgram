package visuals;


import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import java.util.Calendar;

public class CreateClassInterface extends InterfacePanel
{
	private JTextField txtClassName, txtStartYear, txtEndYear;
	private JComboBox<Integer> periodComboBox;
	private JCheckBox chckbxCustomYear;
	private JButton btnCreateClass;
	private StudentInterface studentInterface;

	public CreateClassInterface()
	{
		initClassInterface();
	}

	private void initClassInterface()
	{
		txtStartYear = new JTextField();
		txtEndYear = new JTextField();

		JLabel lblClass = new JLabel("Class                  ");
		lblClass.setFont(new Font("Tahoma", Font.PLAIN, 32));
		add(lblClass);

		JLabel lblClassName = new JLabel("Name");
		add(lblClassName);

		txtClassName = new JTextField();
		add(txtClassName);
		txtClassName.setColumns(10);
//		txtClassName.addActionListener(new ActionListener()
//		{
//			@Override
//			public void actionPerformed(ActionEvent e)
//			{
//				if (!txtClassName.getText().trim().equals(""))
//					txtFirstName.requestFocus();
//			}
//		});

		txtClassName.addKeyListener(this);

		add(new JLabel("Period"));

		periodComboBox = new JComboBox<Integer>(new Integer[] {1, 2, 3, 4, 5, 6, 7});
		periodComboBox.addKeyListener(this);
		add(periodComboBox);
		
		initYearPicker();
		
		btnCreateClass = new JButton();
		btnCreateClass.setText("Create Class");
		btnCreateClass.addKeyListener(this);

		add(btnCreateClass);
		btnCreateClass.setEnabled(false);
		
		studentInterface = new StudentInterface();
		add(studentInterface);		

//		btnCreateClass.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				try {
//					String className = txtClassName.getText();
//					int classPeriod = Integer.parseInt(periodComboBox.getSelectedItem().toString());
//					int startYear = Integer.parseInt(txtStartYear.getText());
//					int endYear = Integer.parseInt(txtEndYear.getText());
//
//					// Create the class
//					Table coursesTable = TableManager.getTable(TableProperties.COURSES_TABLE_NAME);
//					HashMap<String, Object> coursesVals = new HashMap<String, Object>() {
//						{
//							put(TableProperties.NAME, className);
//							put(TableProperties.PERIOD, classPeriod);
//							put(TableProperties.START_YEAR, startYear);
//							put(TableProperties.END_YEAR, endYear);
//						}
//					};
//					System.out.println(className + classPeriod + startYear + endYear);
//					TableManager.insertValuesIntoNewRow(coursesTable, coursesVals);
//
//					ArrayList<Integer> courseIds = DataTypeManager.toIntegerArrayList(coursesTable.getAllFromColumn(TableProperties.COURSE_ID));
//
//					int courseId = courseIds.get(courseIds.size() - 1);
//
//					// Add all the students
//					for (Student student : students) {
//						String firstName = student.getFirstName();
//						String lastName = student.getLastName();
//						int studentID = student.getStudentId();
//						char gender = student.getGender();
//						int graduationYear = student.getGraduationYear();
//						Date birthdate = student.getBirthday();
//
//						int birthMonth = 0;
//						int birthDay = 0;
//						int birthYear = 0;
//
//						if (birthdate != null) {
//							birthMonth = birthdate.getMonth();
//							birthDay = birthdate.getDay();
//							birthYear = birthdate.getYear();
//						}
//
//						final int monthFinal = birthMonth;
//						final int dayFinal = birthDay;
//						final int yearFinal = birthYear;
//
//						int counselorId = student.getCounselorId();
//
//						//adds new Student
//						Table studentsTable = TableManager.getTable(TableProperties.STUDENTS_TABLE_NAME);
//
//						ArrayList<Integer> redwoodIds = DataTypeManager.toIntegerArrayList(studentsTable.getAllFromColumn(TableProperties.STUDENT_REDWOOD_ID));
//
//						if (!redwoodIds.contains(studentID)) {
//							HashMap<String, Object> studentVals = new HashMap<String, Object>() {
//								{
//									put(TableProperties.STUDENT_REDWOOD_ID, studentID);
//									put(TableProperties.FIRST_NAME, firstName);
//									put(TableProperties.LAST_NAME, lastName);
//									put(TableProperties.GENDER, gender);
//									put(TableProperties.GRADUATION_YEAR, graduationYear);
//									put(TableProperties.BIRTH_MONTH, monthFinal);
//									put(TableProperties.BIRTH_DAY, dayFinal);
//									put(TableProperties.BIRTH_YEAR, yearFinal);
//									put(TableProperties.COUNSELOR_ID, counselorId);
//								}
//							};
//
//							System.out.println("Added a student");
//							TableManager.insertValuesIntoNewRow(studentsTable, studentVals);
//						}
//
//						int curStudentDatabaseID = DataTypeManager.toIntegerArrayList(studentsTable.getSomeFromColumn(TableProperties.STUDENT_ID, TableProperties.STUDENT_REDWOOD_ID, "" + studentID)).get(0);
//
//						//Adds new Enrollment
//						Table enrollmentsTable = TableManager.getTable(TableProperties.ENROLLMENTS_TABLE_NAME);
//
//						HashMap<String, Object> enrollmentsVals = new HashMap<String, Object>() {
//							{
//								put(TableProperties.STUDENT_ID, curStudentDatabaseID);
//								put(TableProperties.COURSE_ID, courseId);
//							}
//						};
//
//						TableManager.insertValuesIntoNewRow(enrollmentsTable, enrollmentsVals);
//					}
//
//					students.removeAll(students);
//					listStudentsModel.removeAllElements();
//				} catch (Exception e1) {
//					JOptionPane.showMessageDialog(thisPanel, "Failed to create class");
//				}
//			}
//		});
	}

	private void initYearPicker()
	{
		JLabel lblStartYear = new JLabel("Start Year");
		add(lblStartYear);

		int year = Calendar.getInstance().get(Calendar.YEAR);

		chckbxCustomYear = new JCheckBox("Custom Year");
		chckbxCustomYear.addKeyListener(this);

		add(chckbxCustomYear);

		chckbxCustomYear.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				txtStartYear.setEnabled(!txtStartYear.isEnabled());
				txtEndYear.setEnabled(!txtEndYear.isEnabled());

				txtStartYear.setText(String.valueOf(year));
				txtEndYear.setText(String.valueOf(year + 1));
			}
		});

		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		txtStartYear = new JTextField();
		panel.add(txtStartYear);
		txtStartYear.setText(String.valueOf(year));
		txtStartYear.setEnabled(false);
		txtStartYear.addKeyListener(this);

		txtEndYear = new JTextField();
		panel.add(txtEndYear);
		txtEndYear.setText(String.valueOf(year + 1));
		txtEndYear.setEnabled(false);
		txtEndYear.addKeyListener(this);
	}

	@Override
	public void keyTyped(KeyEvent e)
	{

	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		// Determine if the finish button should be enabled
        btnCreateClass.setEnabled(!txtClassName.getText().equals(""));
	}

	@Override
	public void keyPressed(KeyEvent e)
	{

	}

}
