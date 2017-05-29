package visuals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class TestInterface extends InterfacePanel {

    public TestInterface() {
//        JButton button = new JButton("test");
//        Dimension size = new Dimension((int)BaseInterface.getFrameSize().getWidth(), (int) button.getMaximumSize().getHeight());
//        button.setPreferredSize(size);
//        button.setMaximumSize(size);
//        button.setMinimumSize(size);
//
////        fitWidth(button);
//        add(button);
//
        add(new JTextField("test"));
        add(new JLabel("test"));
        add(new JCheckBox("test"));






//        gridBagPanel.add(new JLabel("test"), cons);
//        gridBagPanel.add(new JTextField("test"), cons);
//
//        add(gridBagPanel);
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
