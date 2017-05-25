package visuals;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class TestInterface extends InterfacePanel {

    public TestInterface() {
        add(new JButton("test"));
        add(new JTextField("test"));
        add(new JLabel("test"));
        add(new JCheckBox("test"));
    }

    @Override
    public void onLayoutOpened() {

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
