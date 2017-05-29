package visuals;

import database.DatabaseCellEditor;
import database.GradeBookTableModel;
import database.TableProperties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GradeBook extends InterfacePanel {

    private DatabaseJTable classTable;
    private JTable gradesTable;
    private GradeBookTableModel gradesTableModel;

    public GradeBook() {
        initClassTable();
        initClassPicker();
    }

    private void initClassPicker() {
        add(classTable.getTableHeader());
        add(classTable);
    }

    @Override
    public void onLayoutOpened() {
        if (gradesTableModel != null) {
            gradesTableModel.refresh();
            gradesTableModel.fireTableDataChanged();
        }
    }

    public void initClassTable() {
        classTable = new DatabaseJTable(TableProperties.COURSES_TABLE_NAME);
        classTable.setCellEditor(new DatabaseCellEditor());

        classTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    // Remove the previous grades table
                    if (gradesTable != null) {
                        remove(gradesTable.getTableHeader());
                        remove(gradesTable);
                    }

                    // Add the new grades table
                    int courseId = Integer.parseInt(classTable.getValueAt(classTable.getSelectedRow(), 0).toString());

                    gradesTable = new JTable(gradesTableModel = new GradeBookTableModel(courseId));

                    add(gradesTable.getTableHeader());
                    add(gradesTable);

                    validate();
                    repaint();
                }
            }
        });

        add(classTable.getTableHeader());
        add(classTable);
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