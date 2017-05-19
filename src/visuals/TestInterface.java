package visuals;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class TestInterface extends InterfacePanel {

    public TestInterface() {
//        System.out.println("Base interface type: " + getla);

        JPanel panel = new JPanel();


        panel.add(new JButton("test"));
        panel.add(new JTextField("test"));
        panel.add(new JLabel("test"));
        panel.add(new JCheckBox("test"));

        add(panel);
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
