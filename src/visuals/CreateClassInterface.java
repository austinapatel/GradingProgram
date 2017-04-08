package visuals;

import database.DataTypeManager;
import database.Table;
import database.TableManager;
import database.TableProperties;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CreateClassInterface extends JFrame implements KeyListener, WindowListener
{

	private JPanel contentPane, studentsPane, basePane;
	private JTextField txtClassName, txtStartYear, txtEndYear, txtFirstName, txtLastName, txtStudentID, txtMonth, txtDay, txtYear;
	private JComboBox<Character> genderComboBox;
	private JList listStudents;
	private JButton btnAddStudent, btnFinish;
	private ArrayList<Character> genders;
	private ArrayList<String> firstNames, lastNames;
	private ArrayList<ArrayList> studentProperties;
	private TableInterface tableInterface;
	private JComboBox<String> counselorComboBox;

	public static void main(String[] args) {
		new CreateClassInterface(null) {{
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		}};
	}

	/**
	 * Create the frame.
	 */
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
		initStudentData();
		initFrameProperties();
	}

	private void initStudentData() {
		genders = new ArrayList<Character>();
		firstNames = new ArrayList<String>();
		lastNames = new ArrayList<String>();
		studentProperties = new ArrayList<ArrayList>(){{
			add(genders);
			add(firstNames);
			add(lastNames);
		}};
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
		studentsPane.setLayout(new BoxLayout(studentsPane, BoxLayout.Y_AXIS));
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

		txtEndYear = new JTextField();
		panel.add(txtEndYear);
		txtEndYear.setText(String.valueOf(year + 1));
		txtEndYear.setEnabled(false);
	}

	private void initStudentAdder()
	{
		JLabel lblStudents = new JLabel("Students");
		lblStudents.setFont(new Font("Tahoma", Font.PLAIN, 32));
		wrapInJPanel(lblStudents);

		listStudents = new JList();
		listStudents.setAlignmentX(Component.LEFT_ALIGNMENT);
		listStudents.setBackground(contentPane.getBackground());
		listStudents.setModel(new DefaultListModel<String>());

		DefaultListModel<String> model = (DefaultListModel<String>) listStudents.getModel();

		listStudents.addKeyListener(this);

		studentsPane.add(listStudents);

		JLabel lblFirstName = new JLabel("First Name:");
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

		wrapInJPanel(new JLabel("Last Name:"));

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
		wrapInJPanel(new JLabel("Student ID"));
		txtStudentID = new JTextField();
		txtStudentID.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isStudentIDValid())
					btnAddStudent.requestFocus();
			}
		});
		txtStudentID.addKeyListener(this);

		wrapInJPanel(txtStudentID);

		// Birth date
		wrapInJPanel(new JLabel("MM/DD/YY"));

		JPanel birthdatePanel = new JPanel();
		birthdatePanel.setLayout(new BoxLayout(birthdatePanel, BoxLayout.X_AXIS));
		txtDay = new JTextField();
		txtMonth = new JTextField();
		txtYear = new JTextField();

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
					btnAddStudent.requestFocus();
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
		contentPane.add(genderComboBox);

		// Counselor selector
		wrapInJPanel(new JLabel("Counselor"));
		DefaultComboBoxModel<String> counselorModel = new DefaultComboBoxModel<String>();

		Table counselorTable = TableManager.getTable(TableProperties.COUNSELORS_TABLE_NAME);
		ArrayList<String> counselorNames = DataTypeManager.toStringArrayList(counselorTable.getAllFromColumn(TableProperties.NAME));

		for (String name : counselorNames)
			counselorModel.addElement(name);

		counselorComboBox = new JComboBox<String>(counselorModel);
		contentPane.add(counselorComboBox);

		btnAddStudent = new JButton("Add Student");
		btnAddStudent.setEnabled(false);

		btnAddStudent.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				model.addElement(txtFirstName.getText() + " " + txtLastName.getText());

				genders.add((Character) genderComboBox.getSelectedItem());
				firstNames.add(txtFirstName.getText());
				lastNames.add(txtLastName.getText());

				txtFirstName.setText("");
				txtLastName.setText("");
				txtStudentID.setText("");
				txtDay.setText("");
				txtMonth.setText("");
				txtYear.setText("");
				genderComboBox.setSelectedIndex(0);

				txtFirstName.requestFocus();
			}
		});

		btnAddStudent.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					btnAddStudent.doClick();
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		});

		wrapInJPanel(btnAddStudent);

		btnFinish = new JButton("Finish");
		wrapInJPanel(btnFinish);
		btnFinish.setEnabled(false);

		CreateClassInterface thisInterface = this;

		btnFinish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String className = txtClassName.getText().trim();

				for (int i = 0; i < firstNames.size(); i++) {
					char gender = genders.get(i);
					String firstName = firstNames.get(i);
					String lastName = lastNames.get(i);

					Table studentsTable = TableManager.getTable(TableProperties.STUDENTS_TABLE_NAME);

					HashMap<String, Object> newValues = new HashMap<String, Object>() {{
						put(TableProperties.GENDER, gender);
						put(TableProperties.FIRST_NAME, firstName);
						put(TableProperties.LAST_NAME, lastName);
					}};

					TableManager.insertValuesIntoNewRow(studentsTable, newValues);
				}

				closeJFrame();
			}
		});
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
			return true;

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

	}

	@Override
	public void keyReleased(KeyEvent e) {
		String source = e.getSource().getClass().getSimpleName() ;

		if (source.equals("JList")) {
			// Handle the deletion of students from the students list
			if (e.getKeyCode() == 127 && listStudents.getSelectedIndex() != -1) {
				for (ArrayList arrayList : studentProperties)
					arrayList.remove(listStudents.getSelectedIndex());

				((DefaultListModel) listStudents.getModel()).remove(listStudents.getSelectedIndex());
			}
		} else if (isArrowKey(e)) {
			Robot robot = null;
			try {
				robot = new Robot();
				if (isForwardKey(e)) {
					robot.keyPress(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_TAB);
				} else {
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_SHIFT);
				}
			} catch (AWTException e1) {
				e1.printStackTrace();
			}
		} else {
			// Determine if the add button should be enabled
			btnAddStudent.setEnabled(isStudentIDValid() && isNotEmpty(txtFirstName) && isNotEmpty(txtLastName));

			// Determine if the finish button should be enabled
			btnFinish.setEnabled(isNotEmpty(txtClassName));
		}
	}

	private boolean isArrowKey(KeyEvent e) {
		int k = e.getKeyCode();
		return k == KeyEvent.VK_UP || k == KeyEvent.VK_DOWN || k == KeyEvent.VK_LEFT || k == KeyEvent.VK_RIGHT;
	}

	private boolean isForwardKey(KeyEvent e) {
		int k = e.getKeyCode();
		return k == KeyEvent.VK_DOWN || k == KeyEvent.VK_RIGHT;
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
	// birthdate
	// graduation year
	// counselor

}
