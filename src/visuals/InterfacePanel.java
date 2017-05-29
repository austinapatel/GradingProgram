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
//        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.gridx = 0;

        setLayout(new GridBagLayout());
    }

    @Override
    public Component add(Component component) {
        // Set the maximum size to be as large as possible as BoxLayout takes into account
        // maximum, preferred and minimum size.  To set the size of a component, later use
        // the setPreferredSize function.  This allows setPreferredSize to actually change
        // the size of the component.
//        component.setMaximumSize(new Dimension(Integer.MAX_VALUE, component.getMinimumSize().height));

//        fitWidth(component);
        super.add(component, gridBagConstraints);

        return component;
//        return super.add(component);
    }

    public void fitWidth(Component component) {
        Dimension size = new Dimension((int) BaseInterface.getFrameSize().getWidth(), 100);
        component.setPreferredSize(size);
        component.setMaximumSize(size);
        component.setMinimumSize(size);
    }

    public abstract void onLayoutOpened();

}
