package visuals;

import javax.swing.*;

import customBorders.RoundedCornerBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.TreeMap;

public class HomeInterface extends InterfacePanel implements ActionListener {

    private static int BUTTON_BORDER = 30;

    private TreeMap<String, InterfacePanel> interfaces;
    private BaseInterface mainInterface;
    private KeyboardGridPanel gridPanel;
    private ArrayList<JButton> buttons;
    private boolean color = false;

    public HomeInterface(BaseInterface mainInterface) {
        initInterfaceHashMap();
        initPanel();
        initVisuals();

        this.mainInterface = mainInterface;
    }

    private void initPanel() {        
        gridPanel = new KeyboardGridPanel(5, 2);
        add(gridPanel);
    }

    private void initInterfaceHashMap() {
        interfaces = new TreeMap<String, InterfacePanel>() {{
            put("Create Class", new CreateClassInterface());
            put("Grade Book", new GradeBook());
            put("Create Assignment", new CreateAssignmentInterface());
            put("View Tables", new TableInterface());
            put("Console", new ConsolePanel());
            put("Grading Scale", new GradingScaleInterface());
            put("View Class Grades", new GradesInterface());
            put("Add Students", new StudentInterface());
            put("Enroll Student", new EnrollmentsInterface());
            put("Test Interface", new TestInterface());
        }};
    }

    private void initVisuals() {
        buttons = new ArrayList<>();

        String[] keys = new String[0];
        keys = interfaces.keySet().toArray(keys);

        for (String key : keys)
        {     
        	
        	JButton newbutton = new JButton(key);
        	newbutton.setFont( new Font ("Arial", Font.BOLD , 32));
        	newbutton.setBorder(new RoundedCornerBorder());
        	buttons.add(newbutton);
        }

        for (JButton button : buttons) {
            button.addActionListener(this);
            JPanel border = new JPanel();
            border.setLayout(new BorderLayout());
            border.add(button, BorderLayout.CENTER);
            border.setBorder(BorderFactory.createEmptyBorder(BUTTON_BORDER, BUTTON_BORDER, BUTTON_BORDER, BUTTON_BORDER));

            gridPanel.add(border);

        }
        buttons.get(0).requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = ((JButton) e.getSource()).getText();
        mainInterface.showInterface(interfaces.get(name));
    }

    @Override
    public void keyTyped(KeyEvent e) {
        gridPanel.keyTyped(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("key pressed");
        gridPanel.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        gridPanel.keyReleased(e);
    }

    @Override
    public void onLayoutOpened() {

    }
}
