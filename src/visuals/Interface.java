// Austin Patel & Jason Morris
// APCS
// Redwood High School
// 10/13/16
// Interface.java

package visuals;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Set;

/**
 * Interface for the program.
 */
@SuppressWarnings("serial")
public class Interface extends JFrame {

    private static final String FRAME_TITLE = "Grading Program";
    private static final int WIDTH = 1200, HEIGHT = 900;

    private static int size = 15;

    private ArrayList<JPanel> interfaces;
    private HomeInterface homeInterface;

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

    public Interface() {
        setDefaultSize(size);
        initContent();
        initFrame();

        interfaces = new ArrayList<JPanel>();
    }

    public void initContent() {
        homeInterface = new HomeInterface(this);
        add(homeInterface);
    }


    public void showInterface(JPanel panel) {
        System.out.println("showing new interface");

        interfaces.add(panel);

        remove(homeInterface);

        add(panel);

        getContentPane().revalidate();
        getContentPane().repaint();
    }

    /**
     * Initializes properties of the JFrame.
     */
    private void initFrame() {
        setIconImage(new ImageIcon(getClass().getClassLoader().getResource("icon.png")).getImage());
        setSize(WIDTH, HEIGHT);
        setTitle(Interface.FRAME_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}