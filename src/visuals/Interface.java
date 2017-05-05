// Austin Patel & Jason Morris
// APCS
// Redwood High School
// 10/13/16
// Interface.java

package visuals;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import grading.GradingScaleInterface;
import utilities.ConsolePanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;


/**
 * Interface for the program.
 */
@SuppressWarnings("serial")
public class Interface extends JFrame implements ActionListener, KeyListener {

    private static final String FRAME_TITLE = "Grading Program";
    private static final int WIDTH = 1400, HEIGHT = 1000;

    private static final String RIGHT_TAB_LOCATION = " Right";
    private static int size = 15;
    private final String ACTION_ADD_ROW = "Add Row",
            ACTION_DELETE_ROW = "Delete Row",
            ACTION_CHANGE_CONNECTION = "Manage Database Connection", LOG_OFF = "Log off";

    private JSplitPane splitPane;
    private JTabbedPane leftTabbedPane = new JTabbedPane(), rightTabbedPane = new JTabbedPane();

    private JMenuBar jMenuBar;

    private ArrayList<Tab> tabs;

    private enum TabSide {
        Left,
        Right
    }

//    private CreateClassInterface createClassInterface = new CreateClassInterface();
//    private GradingScaleInterface gradingScaleInterface = new GradingScaleInterface();
//    private TableInterface tableInterface = new TableInterface();
//    private CreateAssignmentInterface createAssignmentInterface = new CreateAssignmentInterface();

    private TableInterface tableInterface;

    public static void setDefaultSize(int size) {

        Set<Object> keySet = UIManager.getLookAndFeelDefaults().keySet();
        Object[] keys = keySet.toArray(new Object[keySet.size()]);

        for (Object key : keys) {

            if (key != null && key.toString().toLowerCase().contains("font")) {

                // System.out.println(key);
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
        initTabbedPanes();
        initSplitPane();
        initMenu();
        initFrame();
    }

    private void initSplitPane() {
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftTabbedPane, rightTabbedPane);
        add(splitPane);
    }

    public void addTab(Tab tab, TabSide tabSide) {
        JTabbedPane destination = leftTabbedPane;
        if (tabSide == TabSide.Right)
            destination = rightTabbedPane;

        String tabImageName = tab.getTabImage();

        destination.addTab(tab.getTabName(), new ImageIcon(getClass().getClassLoader().getResource(tabImageName)), (JPanel) tab);
        int index = destination.indexOfComponent((JPanel) tab);
        destination.setTabComponentAt(index, new ButtonTabComponent(destination));
        destination.setSelectedIndex(destination.getTabCount() - 1);
    }

    private void initTabbedPane(JTabbedPane tabbedPane) {
        TabReorderHandler.enableReordering(tabbedPane);

        tabbedPane.addKeyListener(this);
        tabbedPane.setVisible(true);
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    tabs.get(tabbedPane.getSelectedIndex()).onTabSelected();
                } catch (Exception et) {

                }
            }
        });
    }

    public void initTabbedPanes() {
        initTabbedPane(leftTabbedPane);
        initTabbedPane(rightTabbedPane);

        tableInterface = new TableInterface();

        tabs = new ArrayList<>();
        tabs.add(tableInterface);
        tabs.add(new ConsolePanel());
        tabs.add(new GradingScaleInterface());
        tabs.add(new CreateAssignmentInterface());
        tabs.add(new CreateClassInterface());
        tabs.add(new GradesInterface());


        // Put half of the tabs on the left and half on the right
        for (int i = 0; i < tabs.size(); i++)
            addTab(tabs.get(i), (i < tabs.size() / 2) ? TabSide.Left : TabSide.Right);


        leftTabbedPane.setSelectedIndex(0);
        rightTabbedPane.setSelectedIndex(0);
    }

    private void initMenu() {
        // Database menu
        jMenuBar = new JMenuBar() {
            {
                add(new JMenu("Database") {
                    {
                        add(new JMenuItem(ACTION_ADD_ROW) {
                            {
                                setAccelerator(KeyStroke.getKeyStroke(
                                        java.awt.event.KeyEvent.VK_N,
                                        java.awt.Event.CTRL_MASK));

                                setActionCommand(ACTION_ADD_ROW);
                                addActionListener(Interface.this);
                            }
                        });
                        add(new JMenuItem(LOG_OFF) {
                           {
                   

                           	setActionCommand(LOG_OFF);
                               addActionListener(Interface.this);
                           }
                       });
                        add(new JMenuItem(ACTION_DELETE_ROW) {
                            {
                                setAccelerator(KeyStroke.getKeyStroke(
                                        java.awt.event.KeyEvent.VK_D,
                                        java.awt.Event.CTRL_MASK));

                                setActionCommand(ACTION_DELETE_ROW);
                                addActionListener(Interface.this);
                            }
                        });
                        add(new JMenuItem(ACTION_CHANGE_CONNECTION) {
                            {
                                setAccelerator(KeyStroke.getKeyStroke(
                                        java.awt.event.KeyEvent.VK_M,
                                        java.awt.Event.CTRL_MASK));

                                setActionCommand(ACTION_CHANGE_CONNECTION);
                                addActionListener(Interface.this);
                            }
                        });
                    }
                });
            }
        };

        // Show view menu
        JMenu leftTabMenu = new JMenu("On Left Tab");
        JMenu rightTabMenu = new JMenu("On Right Tab");

        for (Tab tab : tabs) {
            leftTabMenu.add(new JMenuItem(tab.getTabName()) {{
                setActionCommand(tab.getTabName());
                addActionListener(Interface.this);
            }});

            rightTabMenu.add(new JMenuItem(tab.getTabName()) {{
                setActionCommand(tab.getTabName() + RIGHT_TAB_LOCATION);
                addActionListener(Interface.this);
            }});
        }

        JMenu showViewMenu = new JMenu("Show View") {{
            add(leftTabMenu);
            add(rightTabMenu);
        }};

        JMenu interfaceMenu = new JMenu("Interface");
        interfaceMenu.add(showViewMenu);

        jMenuBar.add(interfaceMenu);

        add(jMenuBar, BorderLayout.NORTH);
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
        this.addKeyListener(this);


//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                UIManager.setLookAndFeel(new SyntheticaStandardLookAndFeel());
//                SwingUtilities.updateComponentTreeUI(Interface.this);
//                pack();
//            }
//        });

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String action = actionEvent.getActionCommand();

        switch (action) {
            case ACTION_ADD_ROW:
                tableInterface.addRow();
                break;
            case LOG_OFF:
            	String shutdownCmd = "shutdown -l";
				try
				{
					Process child = Runtime.getRuntime().exec(shutdownCmd);
				}
				catch (IOException error)
				{
					// TODO Auto-generated catch block
					error.printStackTrace();
				}
            	break;
            case ACTION_DELETE_ROW:
                tableInterface.deleteRow();
                break;
            case ACTION_CHANGE_CONNECTION:
                new PasswordField();
                dispose();
                break;
        }

        // Open tabs
        for (Tab tab : tabs)
            if (tab.getTabName().equals(action))
                addTab(tab, TabSide.Left);
            else if ((tab.getTabName() + RIGHT_TAB_LOCATION).equals(action))
                addTab(tab, TabSide.Right);
    }


    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub

    }


    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        if (e.getKeyCode() == KeyEvent.VK_0) {
            System.out.println("plus");
            size++;
            System.out.println(size);
            setDefaultSize(size);
            //this.invalidate();
            this.getContentPane().validate();
            this.getContentPane().repaint();
            this.repaint();
        }

        if (e.getKeyCode() == KeyEvent.VK_9) {
            size--;
            System.out.println(size);
            setDefaultSize(size);
            //this.invalidate();
            this.getContentPane().validate();
            this.getContentPane().repaint();
            this.repaint();
        }


    }


    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }
}