// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 10/13/16
// Interface.java

package visuals;

import grading.GradingScaleInterface;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Interface for the program.
 */
@SuppressWarnings("serial")
public class Interface extends JFrame implements ActionListener {

    private static final String FRAME_TITLE = "Grading Program";

    private static final int WIDTH = 1200, HEIGHT = 800;

    private final String ACTION_ADD_ROW = "Add Row",
            ACTION_DELETE_ROW = "Delete Row",
            ACTION_CHANGE_CONNECTION = "Manage Database Connection",
            ACTION_SHOW_TABLE_INTERFACE_RIGHT = "Tables Right",
            ACTION_SHOW_CREATE_CLASS_INTERFACE_RIGHT = "Create Class Right",
            ACTION_SHOW_GRADE_SCALES_RIGHT = "Grade Scales Right",
            ACTION_SHOW_TABLE_INTERFACE = "Tables",
            ACTION_SHOW_CREATE_CLASS_INTERFACE = "Create Class",
            ACTION_SHOW_GRADE_SCALES = "Grade Scales";

    private JSplitPane splitPane;
    private JTabbedPane leftTabbedPane = new JTabbedPane(), rightTabbedPane = new JTabbedPane();

    private JMenuBar jMenuBar;

    private ArrayList<Tab> tabs;

    private enum TabSide {
        Left,
        Right
    }

    private CreateClassInterface createClassInterface = new CreateClassInterface();
    private GradingScaleInterface gradingScaleInterface = new GradingScaleInterface();
    private TableInterface tableInterface = new TableInterface();

    public Interface() {
        initTabbedPanes();
        initSplitPane();
        initMenu();
        initFrame();
    }

    private void initSplitPane() {
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftTabbedPane, rightTabbedPane);
        splitPane.setResizeWeight(0.5);
        add(splitPane);
    }

    public void addTab(Tab tab, TabSide tabSide) {
        JTabbedPane destination = leftTabbedPane;
        if (tabSide == TabSide.Right)
            destination = rightTabbedPane;

        destination.addTab(tab.getTabName(), new ImageIcon(tab.getTabImage()), (JPanel) tab);
        int index = destination.indexOfComponent((JPanel) tab);
        destination.setTabComponentAt(index, new ButtonTabComponent(destination));
        destination.setSelectedIndex(destination.getTabCount() - 1);
    }

    private void initTabbedPane(JTabbedPane tabbedPane) {
        TabReorderHandler.enableReordering(tabbedPane);

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

        tabs = new ArrayList<>();
        tabs.add(tableInterface);
        tabs.add(gradingScaleInterface);
        tabs.add(createClassInterface);

        addTab(tabs.get(0), TabSide.Left);
        addTab(tabs.get(1), TabSide.Right);
        addTab(tabs.get(2), TabSide.Right);

//        for (Tab tab : tabs)
//            addTab(tab, TabSide.Left);

        leftTabbedPane.setSelectedIndex(0);
    }

    private void initMenu() {
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
                add(new JMenu("Interface") {{
                    add(new JMenu("Show View") {{
                        add(new JMenu("On Left Tab") {{
                            add(new JMenuItem(ACTION_SHOW_TABLE_INTERFACE) {{
                                setActionCommand(ACTION_SHOW_TABLE_INTERFACE);
                                addActionListener(Interface.this);
                            }});
                            add(new JMenuItem(ACTION_SHOW_CREATE_CLASS_INTERFACE) {{
                                setActionCommand(ACTION_SHOW_CREATE_CLASS_INTERFACE);
                                addActionListener(Interface.this);
                            }});
                            add(new JMenuItem(ACTION_SHOW_GRADE_SCALES) {{
                                setActionCommand(ACTION_SHOW_GRADE_SCALES);
                                addActionListener(Interface.this);
                            }});
                        }});
                        add(new JMenu("On Right Tab") {{
                            add(new JMenuItem(ACTION_SHOW_TABLE_INTERFACE) {{
                                setActionCommand(ACTION_SHOW_TABLE_INTERFACE_RIGHT);
                                addActionListener(Interface.this);
                            }});
                            add(new JMenuItem(ACTION_SHOW_CREATE_CLASS_INTERFACE) {{
                                setActionCommand(ACTION_SHOW_CREATE_CLASS_INTERFACE_RIGHT);
                                addActionListener(Interface.this);
                            }});
                            add(new JMenuItem(ACTION_SHOW_GRADE_SCALES) {{
                                setActionCommand(ACTION_SHOW_GRADE_SCALES_RIGHT);
                                addActionListener(Interface.this);
                            }});
                        }});
                    }});
                }});
            }
        };

        add(jMenuBar, BorderLayout.NORTH);
    }

    /**
     * Initializes properties of the JFrame.
     */
    private void initFrame() {
        setIconImage(new ImageIcon("icon.png").getImage());
        setSize(WIDTH, HEIGHT);
        setTitle(Interface.FRAME_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String action = actionEvent.getActionCommand();

        switch (action) {
            case ACTION_ADD_ROW:
//				addRowButton.doClick();
                break;
            case ACTION_DELETE_ROW:
//				deleteRowButton.doClick();
                break;
            case ACTION_CHANGE_CONNECTION:
                new PasswordField();
                dispose();
                break;
            case ACTION_SHOW_CREATE_CLASS_INTERFACE:
                addTab(createClassInterface, TabSide.Left);
                break;
            case ACTION_SHOW_GRADE_SCALES:
                addTab(gradingScaleInterface, TabSide.Left);
                break;
            case ACTION_SHOW_TABLE_INTERFACE:
                addTab(tableInterface, TabSide.Left);
                break;
            case ACTION_SHOW_CREATE_CLASS_INTERFACE_RIGHT:
                addTab(createClassInterface, TabSide.Right);
                break;
            case ACTION_SHOW_GRADE_SCALES_RIGHT:
                addTab(gradingScaleInterface, TabSide.Right);
                break;
            case ACTION_SHOW_TABLE_INTERFACE_RIGHT:
                addTab(tableInterface, TabSide.Right);
                break;
        }
    }
}