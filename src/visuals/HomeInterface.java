package visuals;

import grading.GradingScaleInterface;
import utilities.ConsolePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class HomeInterface extends JPanel implements ActionListener {

    private HashMap<String, JPanel> interfaces;
    private Interface mainInterface;
    private JPanel contentPanel;

    public HomeInterface(Interface mainInterface) {
        initInterfaceHashMap();
        initPanel();
        initVisuals();

        this.mainInterface = mainInterface;
    }

    private void initPanel() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        add(contentPanel);
    }

    private void initInterfaceHashMap() {
        interfaces = new HashMap<String, JPanel>(){{
            put("Create Class", new CreateClassInterface());
            put("Create Assignment", new CreateAssignmentInterface());
            put("View Tables", new TableInterface());
            put("Console", new ConsolePanel());
            put("Grading Scale", new GradingScaleInterface());
            put("View Class Grades", new GradesInterface());
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
            wrapInJPanel(button);
        }
    }

    private void wrapInJPanel(JComponent component) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        contentPanel.add(panel);
        panel.add(component);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = ((JButton) e.getSource()).getText();

        mainInterface.showInterface(interfaces.get(name));
    }
}
