package visuals;

import javax.swing.*;
import java.awt.event.KeyListener;

/**
 * Tools for making the layout of a JPanel centered with correctly sized items.
 */
public abstract class InterfacePanel extends JPanel implements KeyListener {

    public InterfacePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public abstract void onLayoutOpened();

}
