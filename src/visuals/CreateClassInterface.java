package visuals;


import database.DataTypeManager;
import database.Table;
import database.TableManager;
import database.TableProperties;
import table.Date;
import table.Student;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

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

		CreateClassInterface thisInterface = this;

		btnCreateClass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String className = txtClassName.getText();
					int classPeriod = Integer.parseInt(periodComboBox.getSelectedItem().toString());
					int startYear = Integer.parseInt(txtStartYear.getText());
					int endYear = Integer.parseInt(txtEndYear.getText());

					// Create the class
					Table coursesTable = TableManager.getTable(TableProperties.COURSES_TABLE_NAME);
					HashMap<String, Object> coursesVals = new HashMap<String, Object>() {
						{
							put(TableProperties.NAME, className);
							put(TableProperties.PERIOD, classPeriod);
							put(TableProperties.START_YEAR, startYear);
							put(TableProperties.END_YEAR, endYear);
						}
					};
					System.out.println(className + classPeriod + startYear + endYear);
					TableManager.insertValuesIntoNewRow(coursesTable, coursesVals);
				} catch (Exception e1) {
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
