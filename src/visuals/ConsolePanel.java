package visuals;

import utilities.MessageConsole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class ConsolePanel extends InterfacePanel {
    private JTextPane textComponent;

    public ConsolePanel() {
        textComponent = new JTextPane();
        textComponent.setFont(new Font("Terminal", Font.PLAIN, 14));
        textComponent.setBackground(getBackground());

        this.setLayout(new BorderLayout());
        JScrollPane pane = new JScrollPane(textComponent, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(pane, BorderLayout.CENTER);

        MessageConsole mc = new MessageConsole(textComponent);
        mc.redirectOut(Color.BLACK, System.out);
        mc.redirectErr(Color.RED, System.out);
        mc.setMessageLines(100);
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
