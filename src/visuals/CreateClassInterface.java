package visuals;

import database.DataTypeManager;
import database.Table;
import database.TableManager;
import database.TableProperties;
import swingmaterial.MaterialButton;
import swingmaterial.MaterialComboBox;
import swingmaterial.MaterialTextField;
import table.Date;
import table.Student;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.security.Key;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class CreateClassInterface extends JPanel implements KeyListener, Tab {

    private JSplitPane splitPane;
    private JPanel contentPane, studentsPane, baseContentPane;
    private MaterialTextField txtClassName, txtStartYear, txtEndYear, txtFirstName, txtLastName, txtStudentID, txtMonth, txtDay, txtYear;
    private MaterialComboBox<Character> genderComboBox;
    private JList listStudents;
    private MaterialButton btnAddStudent, btnCreateClass;
    private JComboBox<String> counselorComboBox;
    private JTextField txtGradYear;
    private ArrayList<Student> students = new ArrayList<>();
    private JComboBox<Integer> periodComboBox;
    private JLabel lblStudentInfo;
    private JCheckBox chckbxCustomYear;

    public CreateClassInterface() {
        initBasePanel();
        initMainContentPane();
        initStudentsPane();
        initSplitPane();
        initClassInterface();
        initYearPicker();
        initStudentAdder();
    }

    private void initSplitPane() {
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, baseContentPane, studentsPane);
        splitPane.setResizeWeight(0.5);
        add(splitPane);
    }

    private void initBasePanel() {
        setLayout(new BorderLayout());
    }

    private void initStudentsPane() {
        studentsPane = new JPanel();
        studentsPane.setBorder(BorderFactory.createEtchedBorder());
        studentsPane.setLayout(new BorderLayout());
    }

    private void initMainContentPane() {
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        baseContentPane = new JPanel();
        baseContentPane.add(contentPane);
    }

    private void wrapInJPanel(JComponent component) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        contentPane.add(panel);
        panel.add(component);
    }

    private void initClassInterface() {
        JLabel lblClass = new JLabel("Class                  ");
        lblClass.setFont(new Font("Tahoma", Font.PLAIN, 32));
        wrapInJPanel(lblClass);

        JLabel lblClassName = new JLabel("Name");
        wrapInJPanel(lblClassName);

        txtClassName = new MaterialTextField();
        contentPane.add(txtClassName);
        txtClassName.setColumns(10);
        txtClassName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!txtClassName.getText().trim().equals(""))
                    txtFirstName.requestFocus();
            }
        });

        txtClassName.addKeyListener(this);

        wrapInJPanel(new JLabel("Period"));

        periodComboBox = new JComboBox<Integer>(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        periodComboBox.addKeyListener(this);
        contentPane.add(periodComboBox);
    }

    private void initYearPicker() {
        JLabel lblStartYear = new JLabel("Start Year");
        wrapInJPanel(lblStartYear);

        int year = Calendar.getInstance().get(Calendar.YEAR);

        chckbxCustomYear = new JCheckBox("Custom Year");
        chckbxCustomYear.addKeyListener(this);

        wrapInJPanel(chckbxCustomYear);

        chckbxCustomYear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtStartYear.setEnabled(!txtStartYear.isEnabled());
                txtEndYear.setEnabled(!txtEndYear.isEnabled());

                txtStartYear.setText(String.valueOf(year));
                txtEndYear.setText(String.valueOf(year + 1));
            }
        });

        JPanel panel = new JPanel();
        contentPane.add(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        txtStartYear = new MaterialTextField();
        panel.add(txtStartYear);
        txtStartYear.setText(String.valueOf(year));
        txtStartYear.setEnabled(false);
        txtStartYear.addKeyListener(this);

        txtEndYear = new MaterialTextField();
        panel.add(txtEndYear);
        txtEndYear.setText(String.valueOf(year + 1));
        txtEndYear.setEnabled(false);
        txtEndYear.addKeyListener(this);
    }

    private void initStudentAdder() {
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
                if (listStudents.getSelectedIndex() == -1)
                    return;

                Student student = students.get(listStudents.getSelectedIndex());

                int counselorId = student.getCounselorId();

                String counselorName = "Not specified";

                if (counselorId != 0)
                    counselorName = TableManager.getTable(TableProperties.COUNSELORS_TABLE_NAME).getSomeFromColumn(TableProperties.NAME, TableProperties.COUNSELOR_ID, String.valueOf(counselorId)).get(0).toString();

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

        txtFirstName = new MaterialTextField();
        contentPane.add(txtFirstName);
        txtFirstName.setColumns(10);

        txtFirstName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!txtFirstName.getText().trim().equals(""))
                    txtLastName.requestFocus();
            }
        });

        txtFirstName.addKeyListener(this);

        wrapInJPanel(new JLabel("*Last Name"));

        txtLastName = new MaterialTextField();
        wrapInJPanel(txtLastName);

        txtLastName.setColumns(10);
        txtLastName.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!txtLastName.getText().trim().equals(""))
                    txtStudentID.requestFocus();
            }
        });

        txtLastName.addKeyListener(this);

        // Student id
        wrapInJPanel(new JLabel("*Student ID"));
        txtStudentID = new MaterialTextField();
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
        txtDay = new MaterialTextField();
        txtMonth = new MaterialTextField();
        txtYear = new MaterialTextField();

        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
        decimalFormat.setGroupingUsed(false);

        txtDay = new MaterialTextField();
        txtDay.setColumns(2); //whatever size you wish to set

        txtMonth = new MaterialTextField();
        txtMonth.setColumns(2); //whatever size you wish to set

        txtYear = new MaterialTextField();
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

        DefaultComboBoxModel<Character> genderModel = new DefaultComboBoxModel<Character>(new Character[]{' ', 'M', 'F', 'O'});
        genderComboBox = new MaterialComboBox<>();
        genderComboBox.setModel(genderModel);
        genderComboBox.addKeyListener(this);

        contentPane.add(genderComboBox);

        // Counselor selector
        wrapInJPanel(new JLabel("Counselor"));
        DefaultComboBoxModel<String> counselorModel = new DefaultComboBoxModel<String>();

        Table counselorTable = TableManager.getTable(TableProperties.COUNSELORS_TABLE_NAME);
        ArrayList<String> counselorNames = DataTypeManager.toStringArrayList(counselorTable.getAllFromColumn(TableProperties.NAME));

        System.out.println("Counselor Names: " + counselorNames.toString());

        counselorModel.addElement("");
        for (String name : counselorNames)
            counselorModel.addElement(name);

        counselorComboBox = new JComboBox<String>(counselorModel);
        counselorComboBox.addKeyListener(this);
        contentPane.add(counselorComboBox);

        btnAddStudent = new MaterialButton();
        btnAddStudent.setText("Add Student");
        btnAddStudent.setEnabled(false);

        JPanel thisPanel = this;

        btnAddStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
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

                        if (!counselorName.equals("")) {
                            ArrayList<Integer> counselorSearch = DataTypeManager.toIntegerArrayList(
                                    TableManager.getTable(TableProperties.COUNSELORS_TABLE_NAME)
                                            .getSomeFromColumn(TableProperties.COUNSELOR_ID, TableProperties.NAME, counselorName));
                            if (counselorSearch.size() == 1) {
                                int counselorId = counselorSearch.get(0);

                                student.setCounselorId(counselorId);
                            } else
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
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(thisPanel, "Failed to add student");
                }

                txtFirstName.requestFocus();
            }
        });

        btnAddStudent.addKeyListener(this);

        wrapInJPanel(btnAddStudent);

        btnCreateClass = new MaterialButton();
        btnCreateClass.setText("Create Class");
        btnCreateClass.addKeyListener(this);

        wrapInJPanel(btnCreateClass);
        btnCreateClass.setEnabled(false);

        btnCreateClass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String className = txtClassName.getText();
                    int classPeriod = Integer.parseInt(periodComboBox.getSelectedItem().toString());
                    int startYear = Integer.parseInt(txtStartYear.getText());
                    int endYear = Integer.parseInt(txtEndYear.getText());

                    // Add all the students
                    for (Student student : students) {
                        String firstName = student.getFirstName();
                        String lastName = student.getLastName();
                        int studentID = student.getStudentId();
                        char gender = student.getGender();
                        int graduationYear = student.getGraduationYear();
                        Date birthdate = student.getBirthday();

                        int birthMonth = 0;
                        int birthDay = 0;
                        int birthYear = 0;

                        if (birthdate != null) {
                            birthMonth = birthdate.getMonth();
                            birthDay = birthdate.getDay();
                            birthYear = birthdate.getYear();
                        }

                        final int monthFinal = birthMonth;
                        final int dayFinal = birthDay;
                        final int yearFinal = birthYear;

                        int counselorId = student.getCounselorId();

                        Table studentsTable = TableManager.getTable(TableProperties.STUDENTS_TABLE_NAME);

                        HashMap<String, Object> newValues = new HashMap<String, Object>() {{
                            put(TableProperties.STUDENT_REDWOOD_ID, studentID);
                            put(TableProperties.FIRST_NAME, firstName);
                            put(TableProperties.LAST_NAME, lastName);
                            put(TableProperties.GENDER, gender);
                            put(TableProperties.GRADUATION_YEAR, graduationYear);
                            put(TableProperties.BIRTH_MONTH, monthFinal);
                            put(TableProperties.BIRTH_DAY, dayFinal);
                            put(TableProperties.BIRTH_YEAR, yearFinal);
                            put(TableProperties.COUNSELOR_ID, counselorId);
                        }};

                        TableManager.insertValuesIntoNewRow(studentsTable, newValues);
                    }

                    // Create the class
                    Table coursesTable = TableManager.getTable(TableProperties.COURSES_TABLE_NAME);
                    HashMap<String, Object> newValues = new HashMap<String, Object>() {{
                        put(TableProperties.NAME, className);
                        put(TableProperties.PERIOD, classPeriod);
                        put(TableProperties.START_YEAR, startYear);
                        put(TableProperties.END_YEAR, endYear);
                    }};
                    TableManager.insertValuesIntoNewRow(coursesTable, newValues);

                    // Clear all fields
                    btnCreateClass.setEnabled(false);
                    students.removeAll(students);
                    listStudentsModel.removeAllElements();
                    txtClassName.setText("");
                    chckbxCustomYear.setEnabled(false);
                    periodComboBox.setSelectedIndex(0);
                    txtClassName.requestFocus();
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(thisPanel, "Failed to create class");
                }
            }
        });
    }

    private String toHTML(String text) {
        return "<html>" + text.replaceAll("\n", "<br>") + "</html>";
    }

    private boolean isEmpty(JTextField jTextField) {
        return jTextField.getText().trim().equals("");
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

        return text.length() > 0;
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
        String sourceClassName = e.getSource().getClass().getSimpleName();
        if (isArrowKey(e) && !sourceClassName.equals("JList") && !sourceClassName.equals("JComboBox")) {
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
        } else if (sourceClassName.equals("JComboBox")) {
            JComboBox jComboBox = (JComboBox) e.getSource();
            if (!jComboBox.isPopupVisible()) {


                if (isArrowKey(e)) {
                    escape();
                    if (e.getKeyCode() == KeyEvent.VK_UP)
                        shiftTab();
                    else if (e.getKeyCode() == KeyEvent.VK_DOWN)
                        tab();
                }
            }
        } else if (e.getSource() == btnAddStudent) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER)
                btnAddStudent.doClick();
        } else if (e.getSource() == btnCreateClass) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER)
                btnCreateClass.doClick();
        }
    }

    private boolean isOnLastTabSpot(KeyEvent e) {
        Object source = e.getSource();

        if (source == btnCreateClass)
            return true;
        if (source == btnAddStudent && !btnCreateClass.isEnabled())
            return true;
        if (source == counselorComboBox && !btnCreateClass.isEnabled() && !btnAddStudent.isEnabled())
            return true;

        return false;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Determine if the add button should be enabled
        btnAddStudent.setEnabled(isStudentIDValid() && isNotEmpty(txtFirstName) && isNotEmpty(txtLastName));

        // Determine if the finish button should be enabled
        btnCreateClass.setEnabled(isNotEmpty(txtClassName));

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
    public String getTabName() {
        return "Create Class";
    }

    @Override
    public String getTabImage() {
        return "class.png";
    }

    @Override
    public void onTabSelected() {

    }

}
