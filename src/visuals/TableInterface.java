
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import database.DatabaseTableModel;
import database.Table;
import database.TableManager;

/** Interface for the program. */
@SuppressWarnings("serial")
public class TableInterface extends JFrame {

	private static final int WIDTH = 800, HEIGHT = 600;
	private JTable jTable;
	private JPanel tableContainer, bottomContainer;
	private JScrollPane tableScrollPane;
	private JList<String> tableList;
	private Table table;
	private DatabaseTableModel databaseTableModel;

	public TableInterface() {
		initFrame();

		initTopContainer();
		initBottomContainer();
		initTablePicker();
		initTable();
		initBottomButtons();

		setVisible(true);
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
		bottomContainer.add(new JButton() {
			{
				setText("Add Row");
				addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int deletedRow = table.deleteRow(0, 1);
						databaseTableModel.fireTableRowsDeleted(deletedRow, deletedRow);
						
						table.addRow(null);
						int numRows = databaseTableModel.getRowCount();
						databaseTableModel.fireTableRowsInserted(numRows, numRows);

						jTable.requestFocus();
						jTable.changeSelection(
								databaseTableModel.getRowCount() - 1, 0, false,
								false);
					}
				});
			}
		});

		bottomContainer.add(new JButton() {
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
								
								databaseTableModel.fireTableRowsDeleted(row, row);
							} catch (SQLException e1) {
								System.out.println(
										"Failed to delete row from database.");
							}
						}
					}
				});
			}
		});
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
				setSelectionMode(
						ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
				setLayoutOrientation(JList.HORIZONTAL_WRAP);
				setVisibleRowCount(tables.length);
				setBackground(tableContainer.getBackground());
				setSelectedIndex(0);

				addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
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
		jTable = new JTable();
		tableScrollPane = new JScrollPane(jTable);
		tableContainer.add(tableScrollPane, BorderLayout.CENTER);

		this.table = TableManager.getTable(tableList.getSelectedValue());
		this.databaseTableModel = new DatabaseTableModel(table);
		jTable.setModel(databaseTableModel);
	}
}
