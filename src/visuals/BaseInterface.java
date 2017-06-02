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
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Set;

/**
 * BaseInterface for the program.
 */
@SuppressWarnings("serial")
public class BaseInterface extends JFrame implements KeyListener {

    private static final String FRAME_TITLE = "Grading Program", ESCAPE = "escape";
    private static final int WIDTH = 1200, HEIGHT = 900;
    private static final Dimension SIZE = new Dimension(WIDTH, HEIGHT);

    private ArrayList<InterfacePanel> interfaces;

    private JPanel contentPanel;

    public static Dimension getFrameSize() {
        return SIZE;
    }

    public static void setDefaultFontSize(int size) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

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
        contentPanel.setLayout(new BorderLayout());

        setDefaultFontSize(15);
        initContent();
        initFrame();
    }

    public void initContent() {
        HomeInterface homeInterface = new HomeInterface(this);
        interfaces.add(homeInterface);
        contentPanel.add(homeInterface, BorderLayout.PAGE_START);
    }

    public void showInterface(InterfacePanel panel) {
        if (interfaces.size() > 0)
            contentPanel.remove(interfaces.get(interfaces.size() - 1));

        interfaces.add(panel);

        contentPanel.add(panel, BorderLayout.PAGE_START);

        panel.onLayoutOpened();

        getContentPane().revalidate();
        getContentPane().repaint();
    }

    public void backAnInterface() {
        if (interfaces.size() == 1)
            return;


        JPanel toRemove = interfaces.remove(interfaces.size() - 1);
        contentPanel.remove(toRemove);

        contentPanel.add(interfaces.get(interfaces.size() - 1), BorderLayout.PAGE_START);

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

    @Override
    public void keyTyped(KeyEvent e) {
        interfaces.get(interfaces.size() - 1).keyTyped(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        interfaces.get(interfaces.size() - 1).keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        interfaces.get(interfaces.size() - 1).keyReleased(e);
    }
}