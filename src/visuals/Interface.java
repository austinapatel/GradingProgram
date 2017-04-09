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

	private CreateClassInterface createClassInterface = new CreateClassInterface();
	private GradingScaleInterface gradingScaleInterface = new GradingScaleInterface();
	private TableInterface tableInterface = new TableInterface();

	public Interface() {
		TabReorderHandler.enableReordering(tabbedPane);
		initFrame();
		initTabbedPane();
		initMenu();
		add(tabbedPane);
		setVisible(true);
	}
	
	public void addTab(Tab tab) {
		tabbedPane.addTab(tab.getTabName(), new ImageIcon(tab.getTabImage()), (JPanel) tab);

		tabbedPane.setTabComponentAt(tabbedPane.indexOfComponent((JPanel) tab),new ButtonTabComponent(tabbedPane));

		tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
	}

	public void initTabbedPane()
	{
		tabs = new ArrayList<>();
		tabs.add(tableInterface);
		tabs.add(gradingScaleInterface);
		tabs.add(createClassInterface);

		for (Tab tab : tabs)
			addTab(tab);

		tabbedPane.setSelectedIndex(0);
		tabbedPane.setVisible(true);
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				try
				{
					
				
				tabs.get(tabbedPane.getSelectedIndex()).onTabSelected();
				}
				catch (Exception et)
				{
					
				}
			}
		});
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
		
		Toolkit toolkit =  Toolkit.getDefaultToolkit ();
		Dimension dim = toolkit.getScreenSize();
		setSize(dim.width,dim.height);
		setDefaultLookAndFeelDecorated(true);
		//setSize(WIDTH, HEIGHT);
		setTitle(Interface.FRAME_TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

//	private boolean doesTabExist(String tab) {
//		for (int i = 0; i < tabbedPane.getTabCount(); i++) {
//			Tab currentTab = (Tab) tabbedPane.getComponentAt(i);
//
//			if (currentTab.getTabName().equals(tab))
//				return true;
//		}
//
//		return false;
//	}

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
				addTab(createClassInterface);
				break;
			case ACTION_SHOW_GRADE_SCALES:
				addTab(gradingScaleInterface);
				break;
			case ACTION_SHOW_TABLE_INTERFACE:
				addTab(tableInterface);
				break;
		}
	}
}