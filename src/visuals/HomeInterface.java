package visuals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class HomeInterface extends InterfacePanel implements ActionListener {

    private static int BUTTON_BORDER = 30;

    private HashMap<String, InterfacePanel> interfaces;
    private BaseInterface mainInterface;
    private KeyboardGridPanel gridPanel;

    public HomeInterface(BaseInterface mainInterface) {
        initInterfaceHashMap();
        initPanel();
        initVisuals();

        this.mainInterface = mainInterface;
    }

    private void initPanel() {
        setLayout(new GridBagLayout());

        gridPanel = new KeyboardGridPanel(4,2);
        add(gridPanel);
    }

    private void initInterfaceHashMap() {
        interfaces = new HashMap<String, InterfacePanel>(){{
            put("Create Class", new CreateClassInterface());
            put("Grade Book", new GradeBook());
            put("Create Assignment", new CreateAssignmentInterface());
            put("View Tables", new TableInterface());
            put("Console", new ConsolePanel());
            put("Grading Scale", new GradingScaleInterface());
            put("View Class Grades", new GradesInterface());
            put("Add Student", new StudentInterface());
        }};
    }

    private void initVisuals() {
        ArrayList<JButton> buttons = new ArrayList<>();

        String[] keys = new String[0];
        keys = interfaces.keySet().toArray(keys);

        for (String key : keys)
            buttons.add(new JButton(key));

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
}
