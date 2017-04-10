// Austin Patel
// 4/9/2017
// CreateAssignmentInterface.java

package visuals;

import database.DataTypeManager;
import database.TableManager;
import database.TableProperties;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class CreateAssignmentInterface extends JPanel implements Tab, KeyListener, ActionListener {

    private JTextField txtName, txtPointValue;
    private DatabaseJComboBox comboBoxClass, comboBoxCategory;

    public CreateAssignmentInterface() {
        initPanel();
        initInterface();
    }

    private void initPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    private void initInterface() {
        // Main title
        JLabel lblStudents = new JLabel("Create Assignment");
        lblStudents.setFont(new Font("Tahoma", Font.PLAIN, 32));
        wrapInJPanel(lblStudents);

        // Assignment properties
        wrapInJPanel(new JLabel("Name"));
        txtName = new JTextField();
        txtName.addActionListener(this);
        add(txtName);

        wrapInJPanel(new JLabel("Class"));
        comboBoxClass = new DatabaseJComboBox(TableProperties.COURSES_TABLE_NAME, TableProperties.NAME, TableProperties.PERIOD);
        comboBoxClass.addActionListener(this);
        add(comboBoxClass);

        wrapInJPanel(new JLabel("Point Value"));
        txtPointValue = new JTextField();
        txtPointValue.addActionListener(this);
        add(txtPointValue);

        wrapInJPanel(new JLabel("Category"));
        comboBoxCategory = new DatabaseJComboBox(TableProperties.CATEGORIES_TABLE_NAME, TableProperties.NAME);
        comboBoxCategory.addActionListener(this);
        add(comboBoxCategory);
    }

    private void wrapInJPanel(JComponent component) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        add(panel);
        panel.add(component);
    }

    @Override
    public String getTabName() {
        return "Create Assignment";
    }

    @Override
    public String getTabImage() {
        return "assignment_icon.png";
    }

    @Override
    public void onTabSelected() {

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

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
