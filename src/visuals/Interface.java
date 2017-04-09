// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 10/13/16
// Interface.java

package visuals;

import grading.GradingScaleInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/** Interface for the program. */
@SuppressWarnings("serial")
public class Interface extends JFrame implements ActionListener {

	private static final String FRAME_TITLE = "Grading Program";

	private static final int WIDTH = 1200, HEIGHT = 800;
	private final String ACTION_ADD_ROW = "Add Row",
			ACTION_DELETE_ROW = "Delete Row",
			ACTION_CHANGE_CONNECTION = "Manage Database Connection",
			ACTION_SHOW_TABLE_INTERFACE = "Tables",
			ACTION_SHOW_CREATE_CLASS_INTERFACE = "Create Class",
			ACTION_SHOW_GRADE_SCALES = "Grade Scales";

	private JTabbedPane tabbedPane = new  JTabbedPane();

	private JMenuBar jMenuBar;

	private ArrayList<Tab> tabs;

	public Interface() {
		
		
		TabReorderHandler.enableReordering(tabbedPane);
		initFrame();
		initTabbedPane();
		initMenu();
		add(tabbedPane);
		setVisible(true);
	}
	
	public void addTab(String tabName, Icon icon, Component tab) {
		tabbedPane.addTab(tabName, icon, tab);
		tabbedPane.setTabComponentAt(tabbedPane.indexOfComponent(tab),new ButtonTabComponent(tabbedPane, icon));
		tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
	}

	public void initTabbedPane()
	{
		tabs = new ArrayList<>();
		tabs.add(new TableInterface());
		tabs.add(new GradingScaleInterface());
		tabs.add(new CreateClassInterface());

		for (Tab tab : tabs)
			addTab(tab.getTabName(), new ImageIcon(tab.getTabImage()), (JPanel) tab);

		tabbedPane.setSelectedIndex(0);
		tabbedPane.setVisible(true);
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
						add(new JMenuItem(ACTION_SHOW_TABLE_INTERFACE){{
							setActionCommand(ACTION_SHOW_TABLE_INTERFACE);
							addActionListener(Interface.this);
						}});
						add(new JMenuItem(ACTION_SHOW_CREATE_CLASS_INTERFACE){{
							setActionCommand(ACTION_SHOW_CREATE_CLASS_INTERFACE);
							addActionListener(Interface.this);
						}});
						add(new JMenuItem(ACTION_SHOW_GRADE_SCALES){{
							setActionCommand(ACTION_SHOW_GRADE_SCALES);
							addActionListener(Interface.this);
						}});
					}});
				}});
			}
		};

		add(jMenuBar, BorderLayout.NORTH);
	}

	/** Initializes properties of the JFrame. */
	private void initFrame() {
		setIconImage(new ImageIcon("icon.png").getImage());
		setSize(WIDTH, HEIGHT);
		setTitle(Interface.FRAME_TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
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
				addTab("Create class", new ImageIcon("class.png"), new CreateClassInterface());
				break;
			case ACTION_SHOW_GRADE_SCALES:
				addTab("Grading Scale", new ImageIcon("grading.png"), new GradingScaleInterface());
				break;
			case ACTION_SHOW_TABLE_INTERFACE:
				addTab("Table", new ImageIcon("table.png"), new TableInterface());
				break;
		}
	}
}