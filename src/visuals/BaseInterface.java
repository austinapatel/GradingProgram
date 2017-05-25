// Austin Patel & Jason Morris
// APCS
// Redwood High School
// 10/13/16
// BaseInterface.java

package visuals;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Set;

/**
 * BaseInterface for the program.
 */
@SuppressWarnings("serial")
public class BaseInterface extends JFrame {

    private static final String FRAME_TITLE = "Grading Program", ESCAPE = "escape";
    private static final int WIDTH = 1200, HEIGHT = 900;

    private static int size = 15;

    private ArrayList<JPanel> interfaces;
    private HomeInterface homeInterface;

    private JPanel contentPanel;

    public static void setDefaultSize(int size) {
        Set<Object> keySet = UIManager.getLookAndFeelDefaults().keySet();
        Object[] keys = keySet.toArray(new Object[keySet.size()]);

        for (Object key : keys) {
            if (key != null && key.toString().toLowerCase().contains("font")) {
                Font font = UIManager.getDefaults().getFont(key);
                if (font != null) {
                    font = font.deriveFont((float) size);
                    UIManager.put(key, font);
                }
            }
        }
    }

    public BaseInterface() {
        interfaces = new ArrayList<>();

        contentPanel = new JPanel();
        add(contentPanel);

        setDefaultSize(size);
        initContent();
        initFrame();
    }

    public void initContent() {
        homeInterface = new HomeInterface(this);
        interfaces.add(homeInterface);
        contentPanel.add(homeInterface);
    }


    public void showInterface(InterfacePanel panel) {
        if (interfaces.size() > 0)
            contentPanel.remove(interfaces.get(interfaces.size() - 1));

        interfaces.add(panel);

        contentPanel.add(panel);

        panel.onLayoutOpened();

        getContentPane().revalidate();
        getContentPane().repaint();
    }

    public void backAnInterface() {
        if (interfaces.size() == 1)
            return;


        JPanel toRemove = interfaces.remove(interfaces.size() - 1);
        contentPanel.remove(toRemove);

        contentPanel.add(interfaces.get(interfaces.size() - 1));

        getContentPane().revalidate();
        getContentPane().repaint();
    }

    /**
     * Initializes properties of the JFrame.
     */
    private void initFrame() {
        setIconImage(new ImageIcon(getClass().getClassLoader().getResource("icon.png")).getImage());
        setSize(WIDTH, HEIGHT);
        setTitle(BaseInterface.FRAME_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        JPanel panel = (JPanel) getContentPane();

        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), ESCAPE);
        panel.getActionMap().put(ESCAPE, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backAnInterface();
            }
        });
    }
}