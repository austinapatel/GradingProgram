package visuals;

import database.TableProperties;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Created by austin.patel on 5/15/2017.
 */
public class EnrollmentsInterface extends InterfacePanel {

    private JButton enrollButton;
    private DatabaseJTable studentsTable, coursesTable;

    public EnrollmentsInterface() {
        initTables();
    }

    private void initTables() {
        studentsTable = new DatabaseJTable(TableProperties.STUDENTS_TABLE_NAME);
        coursesTable = new DatabaseJTable(TableProperties.COURSES_TABLE_NAME);

        add(studentsTable.getTableHeader());
        add(studentsTable);

        add(coursesTable.getTableHeader());
        add(coursesTable);

        add(enrollButton = new JButton("Enroll Student"));
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
