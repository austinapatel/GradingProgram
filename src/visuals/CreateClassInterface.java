package visuals;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JList;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Calendar;

public class CreateClassInterface extends JFrame
{

	private JPanel contentPane, studentsPane, basePane;
	private JTextField txtClassName, txtStartYear, txtEndYear, txtFirstName, txtLastName;
	private JComboBox<Character> jComboBoxGender;
	private JList listStudents;
	private JButton btnAddStudent;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					CreateClassInterface frame = new CreateClassInterface();
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CreateClassInterface()
	{
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
		setBounds(100, 100, 481, 673);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);

		setSize(300, 375);
		setLocationRelativeTo(null);
		setTitle("Create Class");
		setIconImage(new ImageIcon("icon.png").getImage());

		setVisible(true);
	}

	private void initClassName()
	{
		JLabel lblClass = new JLabel("Class");
		lblClass.setFont(new Font("Tahoma", Font.PLAIN, 32));
		contentPane.add(lblClass);

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
				txtFirstName.requestFocus();
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
		contentPane.add(lblStudents);

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
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == 127 && listStudents.getSelectedIndex() != -1)
				{
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
				btnAddStudent.doClick();
				txtFirstName.requestFocus();
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
		
		DefaultComboBoxModel<Character> genderModel = new DefaultComboBoxModel<Character>(new Character[] {'>', '<', '?'});
		
		JLabel genderLabel = new JLabel("Gender:");
		contentPane.add(genderLabel);
		
		jComboBoxGender = new JComboBox<>(genderModel);
		contentPane.add(jComboBoxGender);

		btnAddStudent = new JButton("Add Student");
		contentPane.add(btnAddStudent);
		btnAddStudent.setEnabled(false);

		btnAddStudent.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				model.addElement(txtFirstName.getText() + " " + txtLastName.getText());

				txtFirstName.setText("");
				txtLastName.setText("");
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
