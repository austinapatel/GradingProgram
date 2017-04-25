package visuals;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import database.DataTypeManager;
import database.DatabaseManager;
import database.Table;
import database.TableColumn;
import database.TableManager;
import database.TableProperties;
import grading.Grade;
import grading.GradeCalculator;
import grading.GradingScale;


public class GradeInterface extends JPanel implements Tab {

    private JButton backButton;
    private DatabaseJTable table;
    private JScrollPane tablePane, tablePane2;
    private int currentCourse = 0;


    public GradeInterface() {
        initInstuctionsPane();
        initButton();
        initTable();
        initPanel();
    }

    private void initInstuctionsPane() {
        JLabel instuctionsLabel = new JLabel("Try double clicking one of the rows (APCS) to view student grades");
        add(instuctionsLabel);
    }

    private void initButton() {
        backButton = new JButton("Back");
        backButton.setFont(new Font("Helvetica", Font.BOLD, 14));
        // backButton.setForeground(Color.BLUE);
        backButton.setFocusable(false);
        backButton.setVisible(true);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                remove(tablePane2);
                add(tablePane);
                remove(backButton);
            }

        });
    }

    private void initTable() {

        table = new DatabaseJTable(TableProperties.COURSES_TABLE_NAME);

        tablePane = new JScrollPane(table);
        tablePane.setSize(table.getPreferredSize());
        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                DatabaseJTable table2 = (DatabaseJTable) me.getSource();
                Point p = me.getPoint();
                int row = table2.rowAtPoint(p);
                if (me.getClickCount() == 2) {
                    {
                        currentCourse = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
                        displayClass(currentCourse);

                    }
                }
            }
        });

    }

    private Grade hasStudent(ArrayList<Grade> grades, int studentId) {

        for (Grade grade : grades) {
            if (grade.getStudentId() == studentId)
                return grade;
        }
        return null;
    }

    private void displayClass(int id) {

        ResultSet joinedResultSet = DatabaseManager.getJoinedTable(TableProperties.STUDENTS_TABLE_NAME, TableProperties.ENROLLMENTS_TABLE_NAME, new String[]{TableProperties.STUDENTS_TABLE_NAME + "." + TableProperties.STUDENT_ID, TableProperties.STUDENTS_TABLE_NAME + "." + TableProperties.FIRST_NAME, TableProperties.STUDENTS_TABLE_NAME + "." + TableProperties.LAST_NAME}, TableProperties.STUDENT_ID, TableProperties.STUDENT_ID, TableProperties.ENROLLMENTS_TABLE_NAME + "." + TableProperties.COURSE_ID, String.valueOf(id));
        Table table2 = new Table("test join table", joinedResultSet);

        DefaultTableModel model = new DefaultTableModel();
        JTable jTable = new JTable(model);


        ArrayList<Grade> grades = GradeCalculator.getStudentGrades(id);

        TableColumn[] tableColumns = table2.getTableColumns();
        ArrayList<ArrayList<String>> tableContent = new ArrayList<>();
        String[] columnNames = new String[tableColumns.length];

        for (int i = 0; i < tableColumns.length; i++) {
            columnNames[i] = tableColumns[i].getName();
        }
        model.setColumnIdentifiers(columnNames);

        for (TableColumn tableColumn : tableColumns)
            tableContent.add(DataTypeManager.toStringArrayList(table2.getAllFromColumn(tableColumn.getName())));

        model.setRowCount(tableContent.get(0).size());
        model.setColumnCount(tableContent.size()); // jason got rid of minus 1

        for (int col = 0; col < model.getColumnCount(); col++)
            for (int row = 0; row < model.getRowCount(); row++)
                model.setValueAt(tableContent.get(col).get(row), row, col);


        model.addColumn("Percentage");
        model.addColumn("Letter Grade");
        model.addColumn("Points");
        model.addColumn("Total Points");

        int index = table2.getColumnIndex(TableProperties.STUDENT_ID);
        for (int row = 0; row < jTable.getRowCount(); row++) {
            Grade grade = hasStudent(grades, Integer.parseInt(jTable.getValueAt(row, index).toString()));

            if (grade != null) {
                jTable.setValueAt(grade.getPercentage(), row, jTable.getColumnCount() - 4);
                jTable.setValueAt(grade.getLetterGrade(GradeCalculator.getScales().get(0)), row, jTable.getColumnCount() - 3);
                jTable.setValueAt(grade.getPoints(), row, jTable.getColumnCount() - 2);
                jTable.setValueAt(grade.getTotalPoints(), row, jTable.getColumnCount() - 1);
            }

        }


        jTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                DatabaseJTable table2 = (DatabaseJTable) me.getSource();
                Point p = me.getPoint();
                int row = table2.rowAtPoint(p);
                if (me.getClickCount() == 2) {
                    {
                        //get all grads
                    }
                }
            }
        });


        remove(tablePane);
        this.revalidate();
        add(backButton);
        tablePane2 = new JScrollPane(jTable);
        add(tablePane2);
        this.repaint();


        ///SELECT * FROM Students JOIN Enrollments ON Students.studentId = Enrollments.studentId WHERE Enrollments.courseId = "1"

        //SELECT * FROM Enrollments JOIN Students ON Enrollments.studentId = Students.StudentId WHERE Enrollments.courseId = "1"


    }

    private void initPanel() {

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.add(tablePane);
        this.setVisible(true);

    }

    @Override
    public String getTabName() {
        return "Grades";
    }

    @Override
    public String getTabImage() {
        return "grading_tab_icon.png";
    }

    @Override
    public void onTabSelected() {
    }


}
