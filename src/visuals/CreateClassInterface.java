package visuals;

import database.Table;
import database.TableColumn;
import database.TableManager;
import database.TableProperties;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CreateClassInterface extends JFrame
{

	private JPanel contentPane, studentsPane, basePane;
	private JTextField txtClassName, txtStartYear, txtEndYear, txtFirstName, txtLastName;
	private JComboBox<Character> jComboBoxGender;
	private JList listStudents;
	private JButton btnAddStudent, btnFinish;
	private ArrayList<Character> genders;
	private ArrayList<String> firstNames, lastNames;
	private ArrayList<ArrayList> studentProperties;
	private TableInterface tableInterface;

	/**
	 * Create the frame.
	 */
	public CreateClassInterface(TableInterface tableInterface)
	{
		this.tableInterface = tableInterface;

		initBasePanel();
		initMainContentPane();
		initStudentsPane();
		initClassName();
		initYearPicker();
		initStudentAdder();
		initFrameProperties();
		initStudentData();
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);

		setSize(400, 500);
		setLocationRelativeTo(null);
		setTitle("Create Class");
		setIconImage(new ImageIcon("icon.png").getImage());

		setVisible(true);
	}

	private JPanel wrapInJPanel(JComponent component) {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		contentPane.add(panel);
		panel.add(component);

		return panel;
	}

	private void initClassName()
	{
		JLabel lblClass = new JLabel("Class");
		lblClass.setFont(new Font("Tahoma", Font.PLAIN, 32));
		wrapInJPanel(lblClass);

		JLabel lblClassName = new JLabel("Name");
		contentPane.add(lblClassName);

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
		txtClassName.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				btnFinish.setEnabled(true);
				if (txtClassName.getText().trim().equals(""))
					btnFinish.setEnabled(false);
			}
		});
	}

	private void initYearPicker()
	{
		JLabel lblStartYear = new JLabel("Start Year");
		contentPane.add(lblStartYear);

		int year = Calendar.getInstance().get(Calendar.YEAR);

		JCheckBox chckbxCustomYear = new JCheckBox("Custom Year");
		contentPane.add(chckbxCustomYear);
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

		listStudents.addKeyListener(new KeyListener()
		{

			@Override
			public void keyTyped(KeyEvent e)
			{

			}

			@Override
			public void keyReleased(KeyEvent e)
			{

			}

			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == 127 && listStudents.getSelectedIndex() != -1)
				{
					for (ArrayList arrayList : studentProperties)
						arrayList.remove(listStudents.getSelectedIndex());

					model.remove(listStudents.getSelectedIndex());
				}
			}
		});

		studentsPane.add(listStudents);

		JLabel lblFirstName = new JLabel("First Name:");
		contentPane.add(lblFirstName);

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

		txtFirstName.getDocument().addDocumentListener(new DocumentListener()
		{

			@Override
			public void removeUpdate(DocumentEvent e)
			{
				textChanged();
			}

			@Override
			public void insertUpdate(DocumentEvent e)
			{
				textChanged();
			}

			@Override
			public void changedUpdate(DocumentEvent e)
			{
				textChanged();
			}
		});

		JLabel lblLastName = new JLabel("Last Name:");
		contentPane.add(lblLastName);

		txtLastName = new JTextField();
		contentPane.add(txtLastName);
		txtLastName.setColumns(10);
		txtLastName.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (!txtLastName.getText().trim().equals(""))
					btnAddStudent.requestFocus();
			}
		});

		txtLastName.getDocument().addDocumentListener(new DocumentListener()
		{

			@Override
			public void removeUpdate(DocumentEvent e)
			{
				textChanged();
			}

			@Override
			public void insertUpdate(DocumentEvent e)
			{
				textChanged();
			}

			@Override
			public void changedUpdate(DocumentEvent e)
			{
				textChanged();
			}
		});
		
		DefaultComboBoxModel<Character> genderModel = new DefaultComboBoxModel<Character>(new Character[] {' ', 'M', 'F', 'O'});
		
		JLabel genderLabel = new JLabel("Gender:");
		contentPane.add(genderLabel);
		
		jComboBoxGender = new JComboBox<>(genderModel);
		contentPane.add(jComboBoxGender);

		btnAddStudent = new JButton("Add Student");
		btnAddStudent.setEnabled(false);

		btnAddStudent.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				model.addElement(txtFirstName.getText() + " " + txtLastName.getText());

//				private ArrayList<Character> genders;
//				private ArrayList<String> firstNames, lastNames;
//				private ArrayList<ArrayList> studentProperties;

				genders.add((Character) jComboBoxGender.getSelectedItem());
				firstNames.add(txtFirstName.getText());
				lastNames.add(txtLastName.getText());

				txtFirstName.setText("");
				txtLastName.setText("");

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

				tableInterface.notifyExternalDataChange();
				thisInterface.dispose();
			}
		});
	}

	private void textChanged()
	{
		// Make sure to not allow adding of student if name is blank
		btnAddStudent.setEnabled(!txtFirstName.getText().equals("") && !txtLastName.getText().equals(""));
	}

	// first and last - required
	// student id - required
	// gender
	// birthdate
	// graduation year
	// counselor

}
