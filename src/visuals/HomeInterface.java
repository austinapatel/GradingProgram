package visuals;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class HomeInterface extends JPanel implements ActionListener {

    private HashMap<String, JPanel> interfaces;
    private Interface mainInterface;

    public HomeInterface(Interface mainInterface) {
        initInterfaceHashMap();
        initVisuals();

        this.mainInterface = mainInterface;
    }

    private void initInterfaceHashMap() {
        interfaces = new HashMap<String, JPanel>(){{
            put("Create Class", new CreateClassInterface());
            put("Create Assignment", new CreateAssignmentInterface());
        }};
    }

    private void initVisuals() {
        ArrayList<JButton> buttons = new ArrayList<>();

        String[] keys = new String[0];
        keys = interfaces.keySet().toArray(keys);

        for (String key : keys)
            buttons.add(new JButton(key));

        add(new JLabel("Grading Program"));

        for (JButton button : buttons) {
            button.addActionListener(this);
            add(button);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = ((JButton) e.getSource()).getText();

        mainInterface.showInterface(interfaces.get(name));
    }
}
