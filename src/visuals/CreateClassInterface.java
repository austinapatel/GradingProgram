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

	public CreateClassInterface()
	{
		initClassInterface();
		initYearPicker();
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

		btnCreateClass = new JButton();
		btnCreateClass.setText("Create Class");
		btnCreateClass.addKeyListener(this);

		add(btnCreateClass);
		btnCreateClass.setEnabled(false);

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

<<<<<<< Updated upstream
=======
	private void initStudentAdder()
	{
		JLabel lblStudents = new JLabel("Students");
		lblStudents.setFont(new Font("Tahoma", Font.PLAIN, 32));
		wrapInJPanel(lblStudents);

		listStudents = new JList();
		listStudents.setAlignmentX(Component.LEFT_ALIGNMENT);
		listStudents.setBackground(contentPane.getBackground());
		listStudents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listStudents.setModel(new DefaultListModel<String>());

		DefaultListModel<String> listStudentsModel = (DefaultListModel<String>) listStudents.getModel();

		listStudents.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				if (listStudents.getSelectedIndex() == -1)
					return;

				Student student = students.get(listStudents.getSelectedIndex());

				int counselorId = student.getCounselorId();

				String counselorName = "Not specified";

				if (counselorId != 0)
					counselorName = TableManager.getTable(TableProperties.COUNSELORS_TABLE_NAME)
								.getSomeFromColumn(TableProperties.NAME, TableProperties.COUNSELOR_ID,
											String.valueOf(counselorId))
								.get(0).toString();

				lblStudentInfo.setText(toHTML(student.toString() + "\nCounselor: " + counselorName));
			}
		});

		listStudents.addKeyListener(this);

		studentsPane.add(listStudents, BorderLayout.CENTER);

		lblStudentInfo = new JLabel();
		studentsPane.add(lblStudentInfo, BorderLayout.SOUTH);

		JLabel lblFirstName = new JLabel("*First Name");
		wrapInJPanel(lblFirstName);

		txtFirstName = new JTextField();
		contentPane.add(txtFirstName);
		txtFirstName.setColumns(10);

		txtFirstName.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (!txtFirstName.getText().trim().equals(""))
					txtLastName.requestFocus();
			}
		});

		txtFirstName.addKeyListener(this);

		wrapInJPanel(new JLabel("*Last Name"));

		txtLastName = new JTextField();
		wrapInJPanel(txtLastName);

		txtLastName.setColumns(10);
		txtLastName.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (!txtLastName.getText().trim().equals(""))
					txtStudentID.requestFocus();
			}
		});

		txtLastName.addKeyListener(this);

		// Student id
		wrapInJPanel(new JLabel("*Student ID"));
		txtStudentID = new JTextField();
		txtStudentID.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (isStudentIDValid())
					if (btnAddStudent.isEnabled())
						btnAddStudent.requestFocus();
					else
						tab();
			}
		});
		txtStudentID.addKeyListener(this);

		wrapInJPanel(txtStudentID);

		// Graduation year
		wrapInJPanel(new JLabel("Graduation Year"));
		txtGradYear = new JTextField();
		txtGradYear.addKeyListener(this);
		contentPane.add(txtGradYear);

		// Birth date
		wrapInJPanel(new JLabel("MM/DD/YY"));

		JPanel birthdatePanel = new JPanel();
		birthdatePanel.setLayout(new BoxLayout(birthdatePanel, BoxLayout.X_AXIS));
		txtDay = new JTextField();
		txtMonth = new JTextField();
		txtYear = new JTextField();

		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
		DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
		decimalFormat.setGroupingUsed(false);

		txtDay = new JTextField();
		txtDay.setColumns(2); //whatever size you wish to set

		txtMonth = new JTextField();
		txtMonth.setColumns(2); //whatever size you wish to set

		txtYear = new JTextField();
		txtYear.setColumns(2); //whatever size you wish to set

		txtDay.addKeyListener(this);
		txtMonth.addKeyListener(this);
		txtYear.addKeyListener(this);

		txtDay.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (isDateValid(txtDay.getText()))
					txtYear.requestFocus();
			}
		});

		txtMonth.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (isDateValid(txtMonth.getText()))
					txtDay.requestFocus();
			}
		});

		txtYear.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (isDateValid(txtYear.getText()))
					if (btnAddStudent.isEnabled())
						btnAddStudent.requestFocus();
					else
						tab();
			}
		});

		birthdatePanel.add(txtMonth);
		birthdatePanel.add(txtDay);
		birthdatePanel.add(txtYear);
		contentPane.add(birthdatePanel);

		// Gender selector
		wrapInJPanel(new JLabel("Gender:"));

		DefaultComboBoxModel<Character> genderModel = new DefaultComboBoxModel<Character>(
					new Character[] {' ', 'M', 'F', 'O'});
		genderComboBox = new JComboBox<>();
		genderComboBox.setModel(genderModel);
		genderComboBox.addKeyListener(this);

		contentPane.add(genderComboBox);

		// Counselor selector
		wrapInJPanel(new JLabel("Counselor"));
		DefaultComboBoxModel<String> counselorModel = new DefaultComboBoxModel<String>();

		Table counselorTable = TableManager.getTable(TableProperties.COUNSELORS_TABLE_NAME);
		ArrayList<String> counselorNames = DataTypeManager
					.toStringArrayList(counselorTable.getAllFromColumn(TableProperties.NAME));

		counselorModel.addElement("");
		for (String name : counselorNames)
			counselorModel.addElement(name);

		counselorComboBox = new JComboBox<String>(counselorModel);
		counselorComboBox.addKeyListener(this);
		contentPane.add(counselorComboBox);

		btnAddStudent = new JButton();
		btnAddStudent.setText("Add Student");
		btnAddStudent.setEnabled(false);

		JPanel thisPanel = this;

		btnAddStudent.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					Student student = new Student(txtFirstName.getText(), txtLastName.getText(),
								Integer.parseInt(txtStudentID.getText()));

					if (!isEmpty(txtMonth) && !isEmpty(txtDay) && !isEmpty(txtYear))
					{
						int month = Integer.parseInt(txtMonth.getText());
						int day = Integer.parseInt(txtDay.getText());
						int year = Integer.parseInt(txtYear.getText());

						student.setBirthday(new Date(month, day, year));
					}

					student.setGender((Character) genderComboBox.getSelectedItem());

					if (!isEmpty(txtGradYear))
						student.setGraduationYear(Integer.parseInt(txtGradYear.getText()));

					if (counselorComboBox.getSelectedIndex() > 0)
					{
						String counselorName = counselorComboBox.getSelectedItem().toString();

						if (!counselorName.equals(""))
						{
							ArrayList<Integer> counselorSearch = DataTypeManager.toIntegerArrayList(
										TableManager.getTable(TableProperties.COUNSELORS_TABLE_NAME).getSomeFromColumn(
													TableProperties.COUNSELOR_ID, TableProperties.NAME, counselorName));
							if (counselorSearch.size() == 1)
							{
								int counselorId = counselorSearch.get(0);

								student.setCounselorId(counselorId);
							}
							else
								System.out.println("Failed to find counselor with name of: " + counselorName);
						}
					}

					students.add(student);
					listStudentsModel.addElement(student.getFirstName() + " " + student.getLastName());

					txtFirstName.setText("");
					txtLastName.setText("");
					txtStudentID.setText("");
					txtGradYear.setText("");
					txtDay.setText("");
					txtMonth.setText("");
					txtYear.setText("");
					counselorComboBox.setSelectedIndex(0);
					genderComboBox.setSelectedIndex(0);

					btnAddStudent.setEnabled(false);
					listStudents.setSelectedIndex(listStudentsModel.getSize() - 1);
				}
				catch (Exception e)
				{
					JOptionPane.showMessageDialog(thisPanel, "Failed to add student");
				}

				txtFirstName.requestFocus();
			}
		});

		wrapInJPanel(new JLabel("Select existing students"));

		JTable jTable = new DatabaseJTable(TableProperties.STUDENTS_TABLE_NAME);

		wrapInJPanel(jTable.getTableHeader());
		wrapInJPanel(jTable);

		btnAddStudent.addKeyListener(this);

		wrapInJPanel(btnAddStudent);

		btnCreateClass = new JButton();
		btnCreateClass.setText("Create Class");
		btnCreateClass.addKeyListener(this);

		wrapInJPanel(btnCreateClass);
		btnCreateClass.setEnabled(false);

		btnCreateClass.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					String className = txtClassName.getText();
					int classPeriod = Integer.parseInt(periodComboBox.getSelectedItem().toString());
					int startYear = Integer.parseInt(txtStartYear.getText());
					int endYear = Integer.parseInt(txtEndYear.getText());

					// Create the class
					Table coursesTable = TableManager.getTable(TableProperties.COURSES_TABLE_NAME);
					HashMap<String, Object> coursesVals = new HashMap<String, Object>()
					{
						{
							put(TableProperties.NAME, className);
							put(TableProperties.PERIOD, classPeriod);
							put(TableProperties.START_YEAR, startYear);
							put(TableProperties.END_YEAR, endYear);
						}
					};
					System.out.println(className + classPeriod + startYear + endYear);
					TableManager.insertValuesIntoNewRow(coursesTable, coursesVals);
					
					ArrayList<Integer> courseIds = DataTypeManager.toIntegerArrayList(coursesTable.getAllFromColumn(TableProperties.COURSE_ID));

					int courseId = courseIds.get(courseIds.size() - 1);
					
					// Add all the students
					for (Student student : students)
					{
						String firstName = student.getFirstName();
						String lastName = student.getLastName();
						int studentID = student.getStudentId();
						char gender = student.getGender();
						int graduationYear = student.getGraduationYear();
						Date birthdate = student.getBirthday();

						int birthMonth = 0;
						int birthDay = 0;
						int birthYear = 0;

						if (birthdate != null)
						{
							birthMonth = birthdate.getMonth();
							birthDay = birthdate.getDay();
							birthYear = birthdate.getYear();
						}

						final int monthFinal = birthMonth;
						final int dayFinal = birthDay;
						final int yearFinal = birthYear;

						int counselorId = student.getCounselorId();

						//adds new Student
						Table studentsTable = TableManager.getTable(TableProperties.STUDENTS_TABLE_NAME);

						ArrayList<Integer> redwoodIds = DataTypeManager.toIntegerArrayList(studentsTable.getAllFromColumn(TableProperties.STUDENT_REDWOOD_ID));

						if (!redwoodIds.contains(studentID))
						{
							HashMap<String, Object> studentVals = new HashMap<String, Object>()
							{
								{
									put(TableProperties.STUDENT_REDWOOD_ID, studentID);
									put(TableProperties.FIRST_NAME, firstName);
									put(TableProperties.LAST_NAME, lastName);
									put(TableProperties.GENDER, gender);
									put(TableProperties.GRADUATION_YEAR, graduationYear);
									put(TableProperties.BIRTH_MONTH, monthFinal);
									put(TableProperties.BIRTH_DAY, dayFinal);
									put(TableProperties.BIRTH_YEAR, yearFinal);
									put(TableProperties.COUNSELOR_ID, counselorId);
								}
							};

							System.out.println("Added a student");
							TableManager.insertValuesIntoNewRow(studentsTable, studentVals);
						}
						
						int curStudentDatabaseID = DataTypeManager.toIntegerArrayList(studentsTable.getSomeFromColumn(TableProperties.STUDENT_ID, TableProperties.STUDENT_REDWOOD_ID, "" + studentID)).get(0);
						
						//Adds new Enrollment
						Table enrollmentsTable = TableManager.getTable(TableProperties.ENROLLMENTS_TABLE_NAME);

						HashMap<String, Object> enrollmentsVals = new HashMap<String, Object>()
						{
							{
								put(TableProperties.STUDENT_ID, curStudentDatabaseID);
								put(TableProperties.COURSE_ID, courseId);
							}
						};

						TableManager.insertValuesIntoNewRow(enrollmentsTable, enrollmentsVals);
					}
					
					// Clear all fields
					btnCreateClass.setEnabled(false);
					students.removeAll(students);
					listStudentsModel.removeAllElements();
					txtClassName.setText("");
					chckbxCustomYear.setEnabled(false);
					periodComboBox.setSelectedIndex(0);
					txtClassName.requestFocus();	
				}
				catch (Exception e1)
				{
					JOptionPane.showMessageDialog(thisPanel, "Failed to create class");
				}
			}
		});
	}

	private String toHTML(String text)
	{
		return "<html>" + text.replaceAll("\n", "<br>") + "</html>";
	}

	private boolean isEmpty(JTextField jTextField)
	{
		return jTextField.getText().trim().equals("");
	}

	private boolean isStudentIDValid()
	{
		String t = txtStudentID.getText();
		
		if (t.equals(""))
			return false;
		
		boolean isValid = true;
		try
		{
			Integer.parseInt(t);
		}
		catch (Exception exp)
		{
			isValid = false;
		}

		return t.length() == 6 && isValid;
	}

	private boolean isDateValid(String text)
	{
		if (text.equals(""))
			return false;

		try
		{
			Integer.parseInt(text);
		}
		catch (Exception e)
		{
			return false;
		}

		return text.length() > 0;
	}

	// Checks if a JTextField has any text in it
	private boolean isNotEmpty(JTextField jTextField)
	{
		return !jTextField.getText().trim().equals("");
	}

>>>>>>> Stashed changes
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
