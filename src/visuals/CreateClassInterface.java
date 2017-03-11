package visuals;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import java.awt.Component;
import java.awt.Font;

public class CreateClassInterface extends JFrame {

	private JPanel contentPane;
	private JTextField txtClassName;
	private JTextField txtStartYear;
	private JTextField txtEndYear;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateClassInterface frame = new CreateClassInterface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CreateClassInterface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 481, 484);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JLabel lblClass = new JLabel("Class");
		lblClass.setFont(new Font("Tahoma", Font.PLAIN, 32));
		contentPane.add(lblClass);
		
		JLabel lblClassName = new JLabel("Class Name:");
		contentPane.add(lblClassName);
		
		txtClassName = new JTextField();
		txtClassName.setText("Class Name");
		contentPane.add(txtClassName);
		txtClassName.setColumns(10);
		
		JLabel lblStartYear = new JLabel("Start Year");
		contentPane.add(lblStartYear);
		
		JCheckBox chckbxCustomYear = new JCheckBox("Custom Year");
		contentPane.add(chckbxCustomYear);
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		txtStartYear = new JTextField();
		txtStartYear.setText("Start Year");
		panel.add(txtStartYear);
		txtStartYear.setColumns(10);
		
		txtEndYear = new JTextField();
		txtEndYear.setText("End Year");
		panel.add(txtEndYear);
		txtEndYear.setColumns(10);
		
		JLabel lblStudents = new JLabel("Students:");
		contentPane.add(lblStudents);
		
		JList listStudents = new JList();
		listStudents.setAlignmentX(Component.LEFT_ALIGNMENT);
		listStudents.setModel(new AbstractListModel() {
			String[] values = new String[] {"Test 1", "Test 2", "Test 3\t"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		contentPane.add(listStudents);
		
		JLabel lblAddStudent = new JLabel("Add Student:");
		contentPane.add(lblAddStudent);
	}

}
