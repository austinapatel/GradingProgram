package visuals;

import database.*;
import table.Date;
import table.Student;
import utilities.KeyboardTools;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import static utilities.KeyboardTools.tab;

/**
 * Interface for selecting an existing student or creating a new one.
 */
public class StudentInterface extends InterfacePanel {

    private final static int STUDENT_ID_LENGTH = 6;

    private JTextField txtStudentID, txtMonth, txtDay, txtYear, txtFirstName, txtLastName;

    private JComboBox<Character> genderComboBox;
    private JList<String> listStudents;
    private JButton btnAddStudent;
    private JComboBox<String> counselorComboBox;
    private JTextField txtGradYear;
    private JPanel studentsPane;
    private JLabel lblStudentInfo;
    private ArrayList<Student> students = new ArrayList<>();

    public StudentInterface() {
        initStudentsPane();
        initComponents();
    }

    @Override
    public void onLayoutOpened() {

    }
    
    private void initStudentsPane() {
        studentsPane = new JPanel();
        studentsPane.setBorder(BorderFactory.createEtchedBorder());
        studentsPane.setLayout(new BorderLayout());
    }

    /**
     * Converts a String into HTML format for use in JLabels.
     */
    private static String toHTML(String text) {
        return "<html>" + text.replaceAll("\n", "<br>") + "</html>";
    }

    private void initComponents() {
        JLabel lblStudents = new JLabel("Add Students");
        lblStudents.setFont(new Font("Tahoma", Font.PLAIN, 32));
        add(lblStudents);

        listStudents = new JList<>();
        listStudents.setAlignmentX(Component.LEFT_ALIGNMENT);
        listStudents.setBackground(getBackground());
        listStudents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listStudents.setModel(new DefaultListModel<>());

        DefaultListModel<String> listStudentsModel = (DefaultListModel<String>) listStudents.getModel();

        listStudents.addListSelectionListener((ListSelectionEvent l) -> {
            if (listStudents.getSelectedIndex() == -1)
                return;

            Student student = students.get(listStudents.getSelectedIndex());

            int counselorId = student.getCounselorId();

            String counselorName = "Not specified";

            if (counselorId != 0)
                counselorName = TableManager.getTable(TableProperties.COUNSELORS_TABLE_NAME)
                        .getSomeFromColumn(TableProperties.NAME, new Search(TableProperties.COUNSELOR_ID, String.valueOf(counselorId)))
                        .get(0).toString();

            lblStudentInfo.setText(toHTML(student.toString() + "\nCounselor: " + counselorName));
            //            }
        });

        listStudents.addKeyListener(this);

        studentsPane.add(listStudents, BorderLayout.CENTER);

        lblStudentInfo = new JLabel();
        studentsPane.add(lblStudentInfo, BorderLayout.SOUTH);

        JLabel lblFirstName = new JLabel("*First Name");
        add(lblFirstName);

        txtFirstName = new JTextField();
        add(txtFirstName);
        txtFirstName.setColumns(10);

        txtFirstName.addActionListener(e -> {
            if (!txtFirstName.getText().trim().equals(""))
                txtLastName.requestFocus();
        });

        txtFirstName.addKeyListener(this);

        add(new JLabel("*Last Name"));

        txtLastName = new JTextField();
        add(txtLastName);

        txtLastName.setColumns(10);
        txtLastName.addActionListener(e -> {
            if (!txtLastName.getText().trim().equals(""))
                txtStudentID.requestFocus();
        });

        txtLastName.addKeyListener(this);

        // Student id
        add(new JLabel("*Student ID"));
        txtStudentID = new JTextField();
        txtStudentID.addActionListener(e -> {
            if (isStudentIDValid())
                if (btnAddStudent.isEnabled())
                    btnAddStudent.requestFocus();
                else
                    KeyboardTools.tab();
        });
        txtStudentID.addKeyListener(this);

        add(txtStudentID);

        // Graduation year
        add(new JLabel("Graduation Year"));
        txtGradYear = new JTextField();
        txtGradYear.addKeyListener(this);
        add(txtGradYear);

        // Birth date
        add(new JLabel("MM/DD/YY"));

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

        txtDay.addActionListener(e -> {
            if (isDateValid(txtDay.getText()))
                txtYear.requestFocus();
        });

        txtMonth.addActionListener(e -> {
            if (isDateValid(txtMonth.getText()))
                txtDay.requestFocus();
        });

        txtYear.addActionListener(e -> {
            if (isDateValid(txtYear.getText()))
                if (btnAddStudent.isEnabled())
                    btnAddStudent.requestFocus();
                else
                    tab();
        });

        birthdatePanel.add(txtMonth);
        birthdatePanel.add(txtDay);
        birthdatePanel.add(txtYear);
        add(birthdatePanel);

        // Gender selector
        add(new JLabel("Gender:"));

        DefaultComboBoxModel<Character> genderModel = new DefaultComboBoxModel<Character>(
                new Character[]{' ', 'M', 'F', 'O'});
        genderComboBox = new JComboBox<>();
        genderComboBox.setModel(genderModel);
        genderComboBox.addKeyListener(this);

        add(genderComboBox);

        // Counselor selector
        add(new JLabel("Counselor"));
        DefaultComboBoxModel<String> counselorModel = new DefaultComboBoxModel<String>();

        Table counselorTable = TableManager.getTable(TableProperties.COUNSELORS_TABLE_NAME);
        ArrayList<String> counselorNames = DataTypeManager
                .toStringArrayList(counselorTable.getAllFromColumn(TableProperties.NAME));

        counselorModel.addElement("");
        for (String name : counselorNames)
            counselorModel.addElement(name);

        counselorComboBox = new JComboBox<String>(counselorModel);
        counselorComboBox.addKeyListener(this);
        add(counselorComboBox);

        btnAddStudent = new JButton();
        btnAddStudent.setText("Add Student");
        btnAddStudent.setEnabled(false);

        JPanel thisPanel = this;

        btnAddStudent.addActionListener(arg0 -> {
            try {
                Student student = new Student(txtFirstName.getText(), txtLastName.getText(),
                        Integer.parseInt(txtStudentID.getText()));

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
                                TableManager.getTable(TableProperties.COUNSELORS_TABLE_NAME).getSomeFromColumn(
                                        TableProperties.COUNSELOR_ID, new Search(TableProperties.NAME, counselorName)));
                        if (counselorSearch.size() == 1) {
                            int counselorId = counselorSearch.get(0);

                            student.setCounselorId(counselorId);
                        } else
                            System.out.println("Failed to find counselor with name of: " + counselorName);
                    }
                }

                students.add(student);

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

                //adds new Student
                Table studentsTable = TableManager.getTable(TableProperties.STUDENTS_TABLE_NAME);

                ArrayList<Integer> redwoodIds = DataTypeManager
                        .toIntegerArrayList(studentsTable.getAllFromColumn(TableProperties.STUDENT_REDWOOD_ID));

                if (!redwoodIds.contains(studentID)) {
                    HashMap<String, Object> studentVals = new HashMap<String, Object>() {
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

                    studentsTable.addRow(studentVals);
                }

                int curStudentDatabaseID = DataTypeManager
                        .toIntegerArrayList(studentsTable.getSomeFromColumn(TableProperties.STUDENT_ID,
                                new Search(TableProperties.STUDENT_REDWOOD_ID, studentID)))
                        .get(0);

                //Adds new Enrollment
                Table enrollmentsTable = TableManager.getTable(TableProperties.ENROLLMENTS_TABLE_NAME);

                Table coursesTable = TableManager.getTable(TableProperties.COURSES_TABLE_NAME);
                ArrayList<Integer> courseIds = DataTypeManager
                        .toIntegerArrayList(coursesTable.getAllFromColumn(TableProperties.COURSE_ID));

                int courseId = courseIds.get(courseIds.size() - 1);

                HashMap<String, Object> enrollmentsVals = new HashMap<String, Object>() {
                    {
                        put(TableProperties.STUDENT_ID, curStudentDatabaseID);
                        put(TableProperties.COURSE_ID, courseId);
                    }
                };

                enrollmentsTable.addRow(enrollmentsVals);
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
        });

        btnAddStudent.addKeyListener(this);

        add(btnAddStudent);
    }

    private boolean isStudentIDValid() {
        String t = txtStudentID.getText();

        if (t.equals(""))
            return false;

        boolean isValid = true;
        try {
            Integer.parseInt(t);
        } catch (Exception exp) {
            isValid = false;
        }

        return t.length() == STUDENT_ID_LENGTH && isValid;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        String sourceClassName = e.getSource().getClass().getSimpleName();
        if (KeyboardTools.isArrowKey(e) && !sourceClassName.equals("JList") && !sourceClassName.equals("JComboBox")) {
            if (e.getKeyCode() == KeyEvent.VK_DOWN && !isOnLastTabSpot(e))
                tab();
        } else if (e.getSource() == listStudents) {
            // Handle the deletion of students from the students list
            if (e.getKeyCode() == KeyEvent.VK_DELETE && listStudents.getSelectedIndex() != -1) {
                students.remove(listStudents.getSelectedIndex());

                ((DefaultListModel) listStudents.getModel()).remove(listStudents.getSelectedIndex());
            }
        } else if (sourceClassName.equals("JComboBox")) {
            JComboBox jComboBox = (JComboBox) e.getSource();
            if (!jComboBox.isPopupVisible()) {

                if (KeyboardTools.isArrowKey(e)) {
                    KeyboardTools.escape();
                    if (e.getKeyCode() == KeyEvent.VK_UP)
                        KeyboardTools.shiftTab();
                    else if (e.getKeyCode() == KeyEvent.VK_DOWN)
                        tab();
                }
            }
        } else if (e.getSource() == btnAddStudent) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER)
                btnAddStudent.doClick();
        }
    }

    private boolean isOnLastTabSpot(KeyEvent e) {
        Object source = e.getSource();

        return source == btnAddStudent || (source == counselorComboBox && !btnAddStudent.isEnabled());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Determine if the add button should be enabled
        btnAddStudent.setEnabled(isStudentIDValid() && !isEmpty(txtFirstName) && !isEmpty(txtLastName));

        // Limit the date fields to two characters
        if (txtMonth.getText().length() > 2)
            txtMonth.setText(txtMonth.getText().substring(0, 2));
        if (txtDay.getText().length() > 2)
            txtDay.setText(txtDay.getText().substring(0, 2));
        if (txtYear.getText().length() > 2)
            txtYear.setText(txtYear.getText().substring(0, 2));
    }

    private boolean isEmpty(JTextField jTextField) {
        return jTextField.getText().trim().equals("");
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

    public void setStudentButtonEnabled(boolean enabled) {
        btnAddStudent.setEnabled(enabled);
    }
}
