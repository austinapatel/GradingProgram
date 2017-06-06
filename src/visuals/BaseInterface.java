// Austin Patel & Jason Morris
// APCS
// Redwood High School
// 10/13/16
// BaseInterface.java

package visuals;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
public class BaseInterface extends JFrame implements KeyListener
{

	private static final String FRAME_TITLE = "Grading Program", ESCAPE = "escape";
	private static final int WIDTH = 1200, HEIGHT = 900;
	private static final Dimension SIZE = new Dimension(WIDTH, HEIGHT);
	private JList themeList;

	private ArrayList<InterfacePanel> interfaces;

	private JPanel contentPanel;

	private JScrollPane scrollPane;

	public static Dimension getFrameSize()
	{
		return SIZE;
	}

	public static void setDefaultFontSize(int size)
	{
		System.setProperty("awt.useSystemAAFontSettings", "on");
		System.setProperty("swing.aatext", "true");

		Set<Object> keySet = UIManager.getLookAndFeelDefaults().keySet();
		Object[] keys = keySet.toArray(new Object[keySet.size()]);

		for (Object key : keys)
		{
			if (key != null && key.toString().toLowerCase().contains("font"))
			{
				Font font = UIManager.getDefaults().getFont(key);
				if (font != null)
				{
					font = font.deriveFont((float) size);
					UIManager.put(key, font);
				}
			}
		}
	}

	public BaseInterface() {
		interfaces = new ArrayList<>();

		contentPanel = new JPanel();
		scrollPane = new JScrollPane(contentPanel);

//		scrollPane.add(contentPanel);

		add(scrollPane);
		contentPanel.setLayout(new BorderLayout());

		setDefaultFontSize(15);
		initContent();
		initFrame();
	}

	public void initTheme()
	{
		themeList = new JList();
		themeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		if (!System.getProperty("user.name").contains("Austin"))
		{
			try
			{
				themeList.setListData(com.jtattoo.plaf.graphite.GraphiteLookAndFeel.getThemes().toArray());

				UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
			}
			catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e)
			{
				e.printStackTrace();
			}
		}

		ListSelectionListener themeListener = new ListSelectionListener()
		{

			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				SwingUtilities.invokeLater(new Runnable()
				{

					@Override
					public void run()
					{
						com.jtattoo.plaf.graphite.GraphiteLookAndFeel.setTheme((String) themeList.getSelectedValue());
						try
						{
							UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
							System.out.println("hey");
							Window windows[] = Window.getWindows();
							for (Window window : windows)
							{
								if (window.isDisplayable())
								{
									SwingUtilities.updateComponentTreeUI(window);
								}
							}
						}
						catch (ClassNotFoundException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						catch (InstantiationException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						catch (IllegalAccessException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						catch (UnsupportedLookAndFeelException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

			}
		};
		themeList.addListSelectionListener(themeListener);

	}

	public void initContent()
	{
		HomeInterface homeInterface = new HomeInterface(this);
		interfaces.add(homeInterface);
		contentPanel.add(homeInterface, BorderLayout.PAGE_START);
	}

	public void showInterface(InterfacePanel panel)
	{
		if (interfaces.size() > 0)
			contentPanel.remove(interfaces.get(interfaces.size() - 1));

		interfaces.add(panel);

		contentPanel.add(panel, BorderLayout.PAGE_START);

		panel.onLayoutOpened();

		getContentPane().revalidate();
		getContentPane().repaint();
	}

	public void backAnInterface()
	{
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
	private void initFrame()
	{

		try
		{

			if (System.getProperty("user.name").equals("jason.morris")
						|| System.getProperty("user.name").equals("achinthya.poduval"))
			{
				setIconImage(new ImageIcon(getClass().getClassLoader().getResource("icon.png")).getImage());
			}
			else
				setIconImage(new ImageIcon(getClass().getClassLoader().getResource("icon2.png")).getImage());
		}
		catch (Exception e)
		{

		}

		setSize(WIDTH, HEIGHT);
		setTitle(BaseInterface.FRAME_TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

		JPanel panel = (JPanel) getContentPane();

		panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), ESCAPE);
		panel.getActionMap().put(ESCAPE, new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				backAnInterface();
			}
		});
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		interfaces.get(interfaces.size() - 1).keyTyped(e);
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		interfaces.get(interfaces.size() - 1).keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		interfaces.get(interfaces.size() - 1).keyReleased(e);
	}
}