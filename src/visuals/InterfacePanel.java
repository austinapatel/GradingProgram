package visuals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

/**Tools for making the layout of a JPanel centered with correctly sized items.*/
public abstract class InterfacePanel extends JPanel implements KeyListener {

    private JPanel contentPanel;

    public InterfacePanel() {
        contentPanel = new JPanel();
        init();
        super.add(contentPanel);
    }

    /**Initializes the base layout of a JPanel.*/
    public void init() {
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
    }

    /**Wraps a component in its own JPanel, so that it will center align and resize
     * correctly and then adds it to a frame.*/
    @Override
    public Component add(Component toAdd) {
        JPanel basePanel = new JPanel();
        basePanel.setLayout(new BorderLayout());
        basePanel.add(toAdd);

        return contentPanel.add(basePanel);
    }
}
