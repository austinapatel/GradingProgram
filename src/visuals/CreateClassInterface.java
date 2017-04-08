package visuals;

import database.DataTypeManager;
import database.Table;
import database.TableManager;
import database.TableProperties;
import table.Date;
import table.Student;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class CreateClassInterface extends JFrame implements KeyListener, WindowListener
{

	private JPanel contentPane, studentsPane, basePane;
	private JTextField txtClassName, txtStartYear, txtEndYear, txtFirstName, txtLastName, txtStudentID, txtMonth, txtDay, txtYear;
	private JComboBox<Character> genderComboBox;
	private JList listStudents;
	private JButton btnAddStudent, btnFinish;
	private TableInterface tableInterface;
	private JComboBox<String> counselorComboBox;
	private JTextField txtGradYear;
	private ArrayList<Student> students = new ArrayList<>();
	private JLabel lblStudentInfo;

	public static void main(String[] args) {
		new CreateClassInterface(null) {{
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		}};
	}

	public CreateClassInterface(TableInterface tableInterface)
	{
		this.tableInterface = tableInterface;
		if (tableInterface != null)
			tableInterface.setEnabled(false);

		initBasePanel();
		initMainContentPane();
		initStudentsPane();
		initClassName();
		initYearPicker();
		initStudentAdder();
		initFrameProperties();
	}

	private void initBasePanel()
	{
		basePane = new JPanel();
		basePane.setBorder(new EmptyBorder(5, 5, 5, 5));
		basePane.setLayout(new BorderLayout());
		setContentPane(basePane);
	}

	private void initStudentsPane()
	{
		studentsPane = new JPanel();
		studentsPane.setBorder(BorderFactory.createEtchedBorder());
		basePane.add(studentsPane);
		studentsPane.setLayout(new BorderLayout());
	}

	private void initMainContentPane()
	{
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		basePane.add(contentPane, BorderLayout.WEST);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
	}

	private void initFrameProperties()
	{
//		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		setResizable(false);

		setSize(400, 600);
		setLocationRelativeTo(null);
		setTitle("Create Class");
		setIconImage(new ImageIcon("icon.png").getImage());
		addWindowListener(this);

		setVisible(true);
	}

	private void wrapInJPanel(JComponent component) {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		contentPane.add(panel);
		panel.add(component);
	}

	private void initClassName()
	{
		JLabel lblClass = new JLabel("Class");
		lblClass.setFont(new Font("Tahoma", Font.PLAIN, 32));
		wrapInJPanel(lblClass);

		JLabel lblClassName = new JLabel("Name");
		wrapInJPanel(lblClassName);

		txtClassName = new JTextField();
		contentPane.add(txtClassName);
		txtClassName.setColumns(10);
		txtClassName.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (!txtClassName.getText().trim().equals(""))
					txtFirstName.requestFocus();
			}
		});
		txtClassName.addKeyListener(this);
	}

	private void initYearPicker()
	{
		JLabel lblStartYear = new JLabel("Start Year");
		wrapInJPanel(lblStartYear);

		int year = Calendar.getInstance().get(Calendar.YEAR);

		JCheckBox chckbxCustomYear = new JCheckBox("Custom Year");
		chckbxCustomYear.addKeyListener(this);

		wrapInJPanel(chckbxCustomYear);

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
		contentPane.add(panel);
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

		listStudents.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Student student = students.get(listStudents.getSelectedIndex());

				int counselorId = student.getCounselorId();

				String counselorName = TableManager.getTable(TableProperties.COUNSELORS_TABLE_NAME).getSomeFromColumn(TableProperties.NAME, TableProperties.COUNSELOR_ID, String.valueOf(counselorId)).get(0).toString();

				lblStudentInfo.setText(toHTML(student.toString() + "\nCounselor: " + counselorName));
			}
		});

		listStudents.addKeyListener(this);

		studentsPane.add(listStudents, BorderLayout.CENTER);

		lblStudentInfo = new JLabel();
		lblStudentInfo.setBorder(BorderFactory.createEtchedBorder());
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
		txtStudentID.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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

		txtDay = new JFormattedTextField(decimalFormat);
		txtDay.setColumns(2); //whatever size you wish to set

		txtMonth = new JFormattedTextField(decimalFormat);
		txtMonth.setColumns(2); //whatever size you wish to set

		txtYear = new JFormattedTextField(decimalFormat);
		txtYear.setColumns(2); //whatever size you wish to set


		txtDay.addKeyListener(this);
		txtMonth.addKeyListener(this);
		txtYear.addKeyListener(this);

		txtDay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isDateValid(txtDay.getText()))
					txtYear.requestFocus();
			}
		});

		txtMonth.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isDateValid(txtMonth.getText()))
					txtDay.requestFocus();
			}
		});

		txtYear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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

		DefaultComboBoxModel<Character> genderModel = new DefaultComboBoxModel<Character>(new Character[] {' ', 'M', 'F', 'O'});
		genderComboBox = new JComboBox<>(genderModel);
		genderComboBox.addKeyListener(this);

		contentPane.add(genderComboBox);

		// Counselor selector
		wrapInJPanel(new JLabel("Counselor"));
		DefaultComboBoxModel<String> counselorModel = new DefaultComboBoxModel<String>();

		Table counselorTable = TableManager.getTable(TableProperties.COUNSELORS_TABLE_NAME);
		ArrayList<String> counselorNames = DataTypeManager.toStringArrayList(counselorTable.getAllFromColumn(TableProperties.NAME));

		counselorModel.addElement("");
		for (String name : counselorNames)
			counselorModel.addElement(name);

		counselorComboBox = new JComboBox<String>(counselorModel);
		counselorComboBox.addKeyListener(this);
		contentPane.add(counselorComboBox);

		btnAddStudent = new JButton("Add Student");
		btnAddStudent.setEnabled(false);

		JFrame thisInterface = this;

		btnAddStudent.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try {
					Student student = new Student(txtFirstName.getText(), txtLastName.getText(), Integer.parseInt(txtStudentID.getText()));

					if (!isEmpty(txtMonth) && !isEmpty(txtDay) && !isEmpty(txtYear)) {
						int month = Integer.parseInt(txtMonth.getText());
						int day = Integer.parseInt(txtDay.getText());
						int year = Integer.parseInt(txtYear.getText());

						student.setBirthday(new Date(month, day, year));
					}

					student.setGender((Character) genderComboBox.getSelectedItem());

					if (!isEmpty(txtGradYear))
						student.setGraduationYear(Integer.parseInt(txtGradYear.getText()));

					if (counselorComboBox.getSelectedIndex() > 0) {
						String counselorName = counselorComboBox.getSelectedItem().toString();

						int counselorId = Integer.parseInt(TableManager.getTable(TableProperties.COUNSELORS_TABLE_NAME).getSomeFromColumn(TableProperties.COUNSELOR_ID, TableProperties.NAME, counselorName).get(0).toString());

						student.setCounselorId(counselorId);
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
				} catch (Exception e) {
					JOptionPane.showMessageDialog(thisInterface, "Failed to add student");
				}

				txtFirstName.requestFocus();
			}
		});

		btnAddStudent.addKeyListener(this);

		wrapInJPanel(btnAddStudent);

		btnFinish = new JButton("Finish");
		btnFinish.addKeyListener(this);
		btnFinish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnFinish.doClick();
			}
		});
		wrapInJPanel(btnFinish);
		btnFinish.setEnabled(false);


		btnFinish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String className = txtClassName.getText().trim();

				for (Student student : students) {
					String firstName = student.getFirstName();
					String lastName = student.getLastName();
					int studentID = student.getStudentId();
					char gender = student.getGender();
					int graduationYear = student.getGraduationYear();
					Date birthdate = student.getBirthday();
					int birthMonth = birthdate.getMonth();
					int birthDay = birthdate.getDay();
					int birthYear = birthdate.getYear();

					Table studentsTable = TableManager.getTable(TableProperties.STUDENTS_TABLE_NAME);

					HashMap<String, Object> newValues = new HashMap<String, Object>() {{
						put(TableProperties.STUDENT_REDWOOD_ID, studentID);
						put(TableProperties.FIRST_NAME, firstName);
						put(TableProperties.LAST_NAME, lastName);
						put(TableProperties.GENDER, gender);
						put(TableProperties.GRADUATION_YEAR, graduationYear);
						put(TableProperties.BIRTH_MONTH, birthMonth);
						put(TableProperties.BIRTH_DAY, birthDay);
						put(TableProperties.BIRTH_YEAR, birthYear);
					}};

					TableManager.insertValuesIntoNewRow(studentsTable, newValues);
				}

				closeJFrame();
			}
		});
	}

	private String toHTML(String text) {
		return "<html>" + text.replaceAll("\n", "<br>") + "</html>";
	}

	private boolean isEmpty(JTextField jTextField) {
		return jTextField.getText().trim().equals("");
	}

	private void closeJFrame() {
		if (tableInterface != null) {
			tableInterface.notifyExternalDataChange();
			tableInterface.setEnabled(true);
		}

		dispose();
	}

	private boolean isStudentIDValid() {
		String t = txtStudentID.getText();
		boolean isValid = true;
		try {
			Integer.parseInt(t);
		} catch (Exception exp) {
			isValid = false;
		}

		return t.length() == 6 && isValid;
	}

	private boolean isDateValid(String text) {
		if (text.equals(""))
			return false;

		try {
			Integer.parseInt(text);
		} catch (Exception e) {
			return false;
		}

		return text.length() == 2;
	}

	// Checks if a JTextField has any text in it
	private boolean isNotEmpty(JTextField jTextField) {
		return !jTextField.getText().trim().equals("");
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (isArrowKey(e)) {
			if (isOnLastTabSpot(e) && e.getSource() == counselorComboBox && !counselorComboBox.isPopupVisible()) {
				escape();
			}
			// Don't handle arrow key presses if the combobox is open
			if ((e.getSource() == genderComboBox && genderComboBox.isPopupVisible()) ||
					(e.getSource() == counselorComboBox && counselorComboBox.isPopupVisible()))
				return;
			if (e.getKeyCode() == KeyEvent.VK_DOWN && !isOnLastTabSpot(e))
				tab();
			if (e.getKeyCode() == KeyEvent.VK_UP && e.getSource() != txtClassName)
				shiftTab();
		} else if (e.getSource() == listStudents) {
			// Handle the deletion of students from the students list
			if (e.getKeyCode() == KeyEvent.VK_DELETE && listStudents.getSelectedIndex() != -1) {
				students.remove(listStudents.getSelectedIndex());

				((DefaultListModel) listStudents.getModel()).remove(listStudents.getSelectedIndex());
			}
		} else if (e.getSource() == btnAddStudent) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER)
				btnAddStudent.doClick();
		}
	}

	private boolean isOnLastTabSpot(KeyEvent e) {
		Object source = e.getSource();

		if (source == btnFinish)
			return true;
		if (source == btnAddStudent && !btnFinish.isEnabled())
			return true;
		if (source == counselorComboBox && !btnFinish.isEnabled() && !btnAddStudent.isEnabled())
			return true;

		return false;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// Determine if the add button should be enabled
		btnAddStudent.setEnabled(isStudentIDValid() && isNotEmpty(txtFirstName) && isNotEmpty(txtLastName));

		// Determine if the finish button should be enabled
		btnFinish.setEnabled(isNotEmpty(txtClassName));

		// Limit the date fields to two characters
		if (txtMonth.getText().length() > 2)
			txtMonth.setText(txtMonth.getText().substring(0, 2));
		if (txtDay.getText().length() > 2)
			txtDay.setText(txtDay.getText().substring(0, 2));
		if (txtYear.getText().length() > 2)
			txtYear.setText(txtYear.getText().substring(0, 2));
	}

	private void tab() {
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void shiftTab() {
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_SHIFT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void escape() {
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ESCAPE);
			robot.keyRelease(KeyEvent.VK_ESCAPE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean isArrowKey(KeyEvent e) {
		int k = e.getKeyCode();
		return k == KeyEvent.VK_UP || k == KeyEvent.VK_DOWN;
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		closeJFrame();
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	// first and last - required 1
	// student id - required 1
	// gender 1
	// birthdate 1
	// graduation year 1
	// counselor 1

}
