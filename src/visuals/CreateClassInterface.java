package visuals;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import database.DataTypeManager;
import database.Search;
import database.Table;
import database.TableManager;
import database.TableProperties;

public class CreateClassInterface extends InterfacePanel {

	private static final int STUDENTS_TABLE_HEIGHT = 200;

	private JTextField txtClassName, txtStartYear, txtEndYear;
	private JComboBox<Integer> periodComboBox;
	private JCheckBox chckbxCustomYear;
	private JButton btnCreateClass, enrollButton;
	private StudentInterface studentInterface;
	private DatabaseJTable studentsJTable;
	private int courseId;

	public CreateClassInterface()
	{
		initClassInterface();

		Dimension studentSize = new Dimension((int) getPreferredSize().getWidth(),
					(int) studentInterface.getPreferredSize().getHeight());
		studentInterface.setMinimumSize(studentSize);
	}

	@Override
	public void onLayoutOpened()
	{

	}

	private void initClassInterface()
	{
		txtStartYear = new JTextField();
		txtEndYear = new JTextField();

		JLabel lblClass = new JLabel("Class");
		lblClass.setFont(new Font("Tahoma", Font.PLAIN, 32));
		add(lblClass);

		JLabel lblClassName = new JLabel("Name");
		add(lblClassName);

		txtClassName = new JTextField();
		add(txtClassName);
		txtClassName.setColumns(10);

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
		studentInterface.setStudentButtonEnabled(false);
		studentInterface.setBorder(new EmptyBorder(new Insets(0,-100,0,-100)));
		add(studentInterface);
		
		JLabel existingStudentsLabel = new JLabel("Add Existing Students");
		existingStudentsLabel.setFont(new Font("Tahoma", Font.PLAIN, 32));
		add(existingStudentsLabel);

		studentsJTable = new DatabaseJTable(TableProperties.STUDENTS_TABLE_NAME, TableProperties.STUDENT_REDWOOD_ID,
					TableProperties.FIRST_NAME, TableProperties.LAST_NAME);

		add(studentsJTable.getTableHeader());
		JScrollPane studentsScroll = new JScrollPane(studentsJTable);
		studentsScroll.setPreferredSize(new Dimension((int) studentsJTable.getPreferredScrollableViewportSize().getWidth(), STUDENTS_TABLE_HEIGHT));
		studentsScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS); 
		studentsScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(studentsScroll);

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

				Table enrollmentsTable = TableManager.getTable(TableProperties.ENROLLMENTS_TABLE_NAME);

				HashMap<String, Object> enrollmentsVals = new HashMap<String, Object>()
				{
					{
						put(TableProperties.STUDENT_ID, studentId);
						put(TableProperties.COURSE_ID, courseId);
					}
				};

				enrollmentsTable.addRow(enrollmentsVals);
				enrollmentsTable.addRow(enrollmentsVals);
			}
		});
		CreateClassInterface thisInterface = this;

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
					coursesTable.addRow(coursesVals);
					courseId = (int) TableManager.getTable(TableProperties.COURSES_TABLE_NAME)
								.getSomeFromColumn(TableProperties.COURSE_ID, new Search(TableProperties.NAME, className))
								.get(0);
					enrollButton.setEnabled(true);
					studentInterface.setStudentButtonEnabled(true);

					txtClassName.setText("");
					periodComboBox.setSelectedIndex(0);

				}
				catch (Exception e1)
				{
					JOptionPane.showMessageDialog(thisInterface, "Failed to create class");
				}
			}
		});
	}

	private void initYearPicker()
	{
		JLabel lblStartYear = new JLabel("Start Year");
		add(lblStartYear);

		int year = Calendar.getInstance().get(Calendar.YEAR);

		chckbxCustomYear = new JCheckBox("Custom Year");
		chckbxCustomYear.addKeyListener(this);

		add(chckbxCustomYear);

		chckbxCustomYear.addActionListener(e -> {
			txtStartYear.setEnabled(!txtStartYear.isEnabled());
			txtEndYear.setEnabled(!txtEndYear.isEnabled());

			txtStartYear.setText(String.valueOf(year));
			txtEndYear.setText(String.valueOf(year + 1));
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

	private String toHTML(String text)
	{
		return "<html>" + text.replaceAll("\n", "<br>") + "</html>";
	}

	private boolean isEmpty(JTextField jTextField)
	{
		return jTextField.getText().trim().equals("");
	}

	//	private boolean isStudentIDValid()
	//	{
	//		String t = txtStudentID.getText();
	//
	//		if (t.equals(""))
	//			return false;
	//
	//		boolean isValid = true;
	//		try
	//		{
	//			Integer.parseInt(t);
	//		}
	//		catch (Exception exp)
	//		{
	//			isValid = false;
	//		}
	//
	//		return t.length() == 6 && isValid;
	//	}

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
