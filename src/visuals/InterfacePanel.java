package visuals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

/**
 * Tools for making the layout of a JPanel centered with correctly sized items.
 */
public abstract class InterfacePanel extends JPanel implements KeyListener {

    private GridBagConstraints gridBagConstraints;

    public InterfacePanel() {
        gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.gridx = 0;

        setLayout(new GridBagLayout());
    }

    @Override
    public Component add(Component component) {
        super.add(component, gridBagConstraints);

        return component;
    }

    public abstract void onLayoutOpened();

}
