
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
	private Table table;
	private DatabaseTableModel model;

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
						table.addRow(null);
						
						setTable(table);
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
							selectedRowsReverse[selectedRows.length - 1 - i] = selectedRows[i];
																		
						for (int row : selectedRowsReverse) {
							try {
								resultSet.absolute(row + 1);							
								resultSet.deleteRow();
							} catch (SQLException e1) {
								System.out.println("Failed to delete row from database.");
							}
						}
						
						setTable(table);
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

		add(new JList<String>(tableNames) {
			{
				setSelectionMode(
						ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
				setLayoutOrientation(JList.HORIZONTAL_WRAP);
				setVisibleRowCount(tables.length);
				setBackground(tableContainer.getBackground());

				addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						setTable(TableManager.getTable(getSelectedValue()));
					}
				});
			}
		}, BorderLayout.WEST);
	}

	private void initTable() {
		jTable = new JTable();
		tableScrollPane = new JScrollPane(jTable);

		tableContainer.add(tableScrollPane, BorderLayout.CENTER);

		setTable(TableManager.getAllTables()[0]);
	}

	/**Updates the JTable to display a specific table.*/
	private void setTable(Table table) {
		this.table = table;
		this.model = new DatabaseTableModel(table);
		
		jTable.setModel(model);
	}

}
