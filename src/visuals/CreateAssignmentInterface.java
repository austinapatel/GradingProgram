// Austin Patel
// 4/9/2017
// CreateAssignmentInterface.java

package visuals;

import database.TableProperties;
import swingmaterial.MaterialButton;
import swingmaterial.MaterialPanel;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CreateAssignmentInterface extends JPanel implements Tab, KeyListener, ActionListener {

    private JTextField txtName, txtPointValue;
    private JPanel contentPanel;
    private JButton createButton;

    public CreateAssignmentInterface() {
        initPanel();
        initInterface();
    }

    private void initPanel() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        add(contentPanel);
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
        contentPanel.add(txtName);

        wrapInJPanel(new JLabel("Class"));
        JTable coursesJTable = new DatabaseJTable(TableProperties.COURSES_TABLE_NAME);
        coursesJTable.setPreferredScrollableViewportSize(coursesJTable.getPreferredSize());
        JScrollPane coursesScrollPane = new JScrollPane(coursesJTable);
        contentPanel.add(coursesScrollPane);

        wrapInJPanel(new JLabel("Point Value"));
        txtPointValue = new JTextField();
        txtPointValue.addActionListener(this);
        contentPanel.add(txtPointValue);

        wrapInJPanel(new JLabel("Category"));
        JTable categoryJTable = new DatabaseJTable(TableProperties.CATEGORIES_TABLE_NAME);
        categoryJTable.setPreferredScrollableViewportSize(categoryJTable.getPreferredSize());
        JScrollPane categoryScrollPane = new JScrollPane(categoryJTable);
        contentPanel.add(categoryScrollPane);

        createButton = new JButton();
        createButton.setText("Create");
        wrapInJPanel(createButton);
    }

    private void wrapInJPanel(JComponent component) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        contentPanel.add(panel);
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
