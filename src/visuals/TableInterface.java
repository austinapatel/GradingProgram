
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 10/13/16
// Interface.java

package visuals;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import database.DatabaseCellEditor;
import database.DatabaseTableModel;
import database.Table;
import database.TableManager;
import javafx.scene.input.KeyCode;

/** Interface for the program. */
@SuppressWarnings("serial")
public class TableInterface extends JFrame implements ActionListener {

	private static final int WIDTH = 800, HEIGHT = 600;
	private final String ACTION_ADD_ROW = "Add Row",
			ACTION_DELETE_ROW = "Delete Row";

	private JTable jTable;
	private JPanel tableContainer, bottomContainer;
	private JScrollPane tableScrollPane;
	private JList<String> tableList;
	private Table table;
	private DatabaseTableModel databaseTableModel;
	private JButton addRowButton, deleteRowButton;

	public TableInterface() {
		initFrame();

		initTopContainer();
		initBottomContainer();
		initTablePicker();
		initTable();
		initBottomButtons();
		initMenu();

		setVisible(true);
	}

	private void initMenu() {
		add(new JMenuBar() {
			{
				add(new JMenu("Database") {
					{
						add(new JMenuItem("Add row") {
							{
								setAccelerator(KeyStroke.getKeyStroke(
										java.awt.event.KeyEvent.VK_N,
										java.awt.Event.CTRL_MASK));

								setActionCommand(ACTION_ADD_ROW);
								addActionListener(TableInterface.this);
							}
						});
						add(new JMenuItem("Delete row") {
							{
								setAccelerator(KeyStroke.getKeyStroke(
										java.awt.event.KeyEvent.VK_D,
										java.awt.Event.CTRL_MASK));

								setActionCommand(ACTION_DELETE_ROW);
								addActionListener(TableInterface.this);
							}
						});
					}
				});
			}
		}, BorderLayout.NORTH);
	}

	private void initBottomContainer() {
		bottomContainer = new JPanel();
		bottomContainer.setLayout(new GridLayout());
		tableContainer.add(bottomContainer, BorderLayout.SOUTH);
	}

	/** Initializes properties of the JFrame. */
	private void initFrame() {
		setIconImage(new ImageIcon("icon.png").getImage());
		setSize(WIDTH, HEIGHT);
		setTitle("Grading Program");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void initBottomButtons() {
		addRowButton = new JButton() {
			{
				setText("Add Row");
				addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (databaseTableModel.getRowCount() > 0) {
							int deletedRow = table.deleteRow(0, 1);
							
							databaseTableModel.fireTableRowsDeleted(deletedRow,
									deletedRow);
						}

						table.addRow(null);
						int numRows = databaseTableModel.getRowCount();
						
						if (numRows > 0)
							databaseTableModel.fireTableRowsInserted(numRows - 1,
									numRows - 1);
						else
							databaseTableModel.fireTableRowsInserted(1, 1);

						jTable.requestFocus();
						jTable.changeSelection(
								databaseTableModel.getRowCount() - 1, 0, false,
								false);
					}
				});
			}
		};

		deleteRowButton = new JButton() {
			{
				setText("Delete Row(s)");
				addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						ResultSet resultSet = table.getResultSet();

						int[] selectedRows = jTable.getSelectedRows();
						int[] selectedRowsReverse = new int[selectedRows.length];

						for (int i = selectedRows.length - 1; i >= 0; i--)
							selectedRowsReverse[selectedRows.length - 1
									- i] = selectedRows[i];

						for (int row : selectedRowsReverse) {
							try {
								resultSet.absolute(row + 1);
								resultSet.deleteRow();

								if (databaseTableModel.getRowCount() > 0)
									databaseTableModel.fireTableRowsDeleted(row,
										row);
								else
									databaseTableModel.fireTableDataChanged();
							} catch (SQLException e1) {
								System.out.println(
										"Failed to delete row from database.");
							}
						}
					}
				});
			}
		};

		bottomContainer.add(addRowButton);
		bottomContainer.add(deleteRowButton);
	}

	/** Sets up the top container. */
	private void initTopContainer() {
		tableContainer = new JPanel();
		tableContainer.setLayout(new BorderLayout());
		add(tableContainer, BorderLayout.CENTER);
	}

	/** Displays the table picker on the frame. */
	private void initTablePicker() {
		Table[] tables = TableManager.getAllTables();
		String[] tableNames = new String[tables.length];

		for (int i = 0; i < tables.length; i++)
			tableNames[i] = tables[i].getName();

		tableList = new JList<String>(tableNames) {
			{
				setLayoutOrientation(JList.HORIZONTAL_WRAP);
				setVisibleRowCount(tables.length);
				setBackground(tableContainer.getBackground());
				setSelectedIndex(0);
				setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

				addKeyListener(new KeyListener() {
					@Override
					public void keyTyped(KeyEvent e) {
						
					}

					@Override
					public void keyReleased(KeyEvent e) {
						if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_KP_RIGHT) {
							if (databaseTableModel.getRowCount() > 0) {
								jTable.requestFocus();
								jTable.changeSelection(0, 0, false, false);
							}
						}
					}

					@Override
					public void keyPressed(KeyEvent e) {
					}
				});
				
				addListSelectionListener(new ListSelectionListener() {
					
					@Override
					public void valueChanged(ListSelectionEvent e) {
						table = TableManager.getTable(getSelectedValue());
						databaseTableModel.setTable(table);

						databaseTableModel.fireTableStructureChanged();
						
					}
				});
			}
		};

		add(tableList, BorderLayout.WEST);
	}

	/** Initializes a JTable (and its container) and the table model. */
	private void initTable() {
		jTable = new JTable() {
			{
				setAutoCreateRowSorter(true);
				setCellEditor(new DatabaseCellEditor());
				addKeyListener(new KeyListener() {
					
					@Override
					public void keyTyped(KeyEvent e) {						
					}
					
					@Override
					public void keyReleased(KeyEvent e) {
						
					}
					
					@Override
					public void keyPressed(KeyEvent e) {
						if ((e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_KP_LEFT) && jTable.getSelectedColumn() == 0)
							tableList.requestFocus();
					}
				});
			}
		};

		tableScrollPane = new JScrollPane(jTable);
		tableContainer.add(tableScrollPane, BorderLayout.CENTER);

		this.table = TableManager.getTable(tableList.getSelectedValue());
		this.databaseTableModel = new DatabaseTableModel(table);
		jTable.setModel(databaseTableModel);
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		String action = actionEvent.getActionCommand();

		if (action.equals(ACTION_ADD_ROW))
			addRowButton.doClick();
		else if (action.equals(ACTION_DELETE_ROW))
			deleteRowButton.doClick();
	}
}
