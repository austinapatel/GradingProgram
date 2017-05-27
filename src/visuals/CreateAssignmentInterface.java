// Austin Patel
// 4/9/2017
// CreateAssignmentInterface.java

package visuals;

import database.Table;
import database.TableManager;
import database.TableProperties;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class CreateAssignmentInterface extends InterfacePanel implements ActionListener, MouseListener {

    private JTextField txtName, txtPointValue;
    private JPanel contentPanel;
    private JButton createButton;
    private DatabaseJTable coursesJTable;
    private UtilDateModel dateModel;
    private JDatePanelImpl datePanel;
    private JDatePickerImpl datePicker;

    public CreateAssignmentInterface() {
        initPanel();
        initInterface();
    }

    @Override
    public void onLayoutOpened() {

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
        txtName.addKeyListener(this);
        contentPanel.add(txtName);

      
        wrapInJPanel(new JLabel("Class"));
        coursesJTable = new DatabaseJTable(TableProperties.COURSES_TABLE_NAME);
        coursesJTable.setPreferredScrollableViewportSize(coursesJTable.getPreferredSize());
        coursesJTable.addMouseListener(this);
        JScrollPane coursesScrollPane = new JScrollPane(coursesJTable);
        contentPanel.add(coursesScrollPane);

        wrapInJPanel(new JLabel("Point Value"));
        txtPointValue = new JTextField();
        txtPointValue.addActionListener(this);
        txtPointValue.addKeyListener(this);
        contentPanel.add(txtPointValue);

        dateModel = new UtilDateModel();
        datePanel = new JDatePanelImpl(dateModel);
        datePanel.setPreferredSize(contentPanel.getPreferredSize());
        datePicker = new JDatePickerImpl(datePanel);
        contentPanel.add(datePicker);
        datePanel.addActionListener(this);

        createButton = new JButton();
        createButton.setText("Create");
        createButton.setEnabled(false);
        createButton.addActionListener(this);
        wrapInJPanel(createButton);
    }

    private void wrapInJPanel(JComponent component) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        contentPanel.add(panel);
        panel.add(component);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        determineIfCreateEnabled();
    }

    private void determineIfCreateEnabled() {
        // Determine whether the create button should be enabled
        boolean isEnabled = !txtName.getText().equals("") && !txtPointValue.getText().equals("") && !txtPointValue.getText().equals("") && coursesJTable.getSelectedRow() >= 0 && !datePicker.getJFormattedTextField().getText().equals("");

        try {
            Integer.parseInt(txtPointValue.getText());
        } catch(Exception exception) {
            isEnabled = false;
        }

        createButton.setEnabled(isEnabled);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == datePanel)
            determineIfCreateEnabled();
        if (e.getSource() == createButton) {
            Table assignmentsTable = TableManager.getTable(TableProperties.ASSIGNMENTS_TABLE_NAME);

            String courseId = coursesJTable.getValueAt(coursesJTable.getSelectedRow(), 0).toString();
            String date = dateModel.getYear() + "-" + dateModel.getMonth() + "-" + dateModel.getDay();

            // course id, date, value, name
            HashMap<String, Object> values = new HashMap<String, Object>() {{
                put(TableProperties.COURSE_ID, courseId);
                put(TableProperties.ASSIGNMENT_DATE, date);
                put(TableProperties.ASSIGNMENTS_VALUE, txtPointValue.getText());
                put(TableProperties.NAME, txtName.getText());
            }};

            assignmentsTable.addRow(values);
            
            txtName.setText("");
            txtPointValue.setText("");
            dateModel.setSelected(false);
            coursesJTable.getSelectionModel().clearSelection();
            
            determineIfCreateEnabled();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == coursesJTable)
            determineIfCreateEnabled();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
