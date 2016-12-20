
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 10/13/16
// Interface.java

package visuals;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import database.DatabaseManager;
import database.Properties;
import database.Table;
import database.TableManager;
import database.TypeConverter;
import database.TypeConverter.DataType;
import net.proteanit.sql.DbUtils;

/** Interface for the program. */
@SuppressWarnings("serial")
public class TableInterface extends JFrame {

	private static final int WIDTH = 800, HEIGHT = 600;
	private JTable jTable;
	private JPanel topContainer;
	private JScrollPane tableScrollPane;
	private Table currentTable;

	public TableInterface() {
		// Set up the frame's preferences
		setIconImage(new ImageIcon("icon.png").getImage());
		setSize(WIDTH, HEIGHT);
		setTitle("Grading Program");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setLayout(null);

		// add(new JButton("Apply Changes") {
		// {
		//// setLocation(150, 0);
		//// setSize(150, 50);
		// setVisible(true);
		// }
		// }, BorderLayout.PAGE_END);

		topContainer = new JPanel() {
			{
				// setSize(500,500);
				setLayout(new BorderLayout());
			}
		};

		currentTable = null;

		add(topContainer, BorderLayout.CENTER);

		initTablePicker();
		initTable();

		setVisible(true);
	}

	/** Displays the table picker on the frame. */
	private void initTablePicker() {
		Table[] tables = TableManager.getAllTables();
		String[] tableNames = new String[tables.length];

		for (int i = 0; i < tables.length; i++)
			tableNames[i] = tables[i].getName();

		topContainer.add(new JList<String>(tableNames) {
			{
				setSelectionMode(
						ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
				setLayoutOrientation(JList.HORIZONTAL_WRAP);
				setVisibleRowCount(tables.length);
				setBackground(topContainer.getBackground());
				// setLocation(0, 0);
				// setSize(50, 100);

				addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						setTable(TableManager.getTable(getSelectedValue()));
					}
				});

				setVisible(true);
			}
		}, BorderLayout.WEST);
	}

	private void initTable() {
		jTable = new JTable() {
			{
				// setLocation(100,100);
				// setSize(300,100);
				setVisible(true);
			}
		};

		tableScrollPane = new JScrollPane(jTable) {
			{
				// setLocation(0,0);
				// setSize(500,200);
				setVisible(true);
			}
		};

		topContainer.add(tableScrollPane, BorderLayout.CENTER);

		setTable(TableManager.getAllTables()[0]);
	}

	private void setTable(Table table) {
		currentTable = table;

		jTable.setModel(
				DbUtils.resultSetToTableModel(DatabaseManager.getTable(table)));

		jTable.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent evt) {

				int idColumnIndex = 0;

				for (int i = 0; i < jTable.getColumnCount(); i++)
					if (jTable.getColumnName(i).equals(Properties.ID))
						idColumnIndex = i;

				int id = TypeConverter.toInt(
						jTable.getValueAt(evt.getFirstRow(), idColumnIndex));
				System.out.println("id = " + id);

				DataType dataType = DatabaseManager.getSQLType(
						currentTable.getTableColumns()[evt.getColumn()]
								.getType());

				Object newValue = jTable.getValueAt(evt.getFirstRow(),
						evt.getColumn());

				System.out.println("New value in row: " + id + ": " + newValue
						+ " location: (" + evt.getFirstRow() + ", "
						+ evt.getColumn() + ")" + " data type: " + dataType);

				ResultSet rowResultSet = currentTable.getRow(id).getResultSet();
				System.out.println(
						jTable.getModel().getColumnName(evt.getColumn()));

				int columnIndex = jTable.getColumnModel().getColumnIndex(
						jTable.getColumn(jTable.getColumnName(evt.getColumn()))
								.getIdentifier()) + 1;

				try {
					if (dataType == DataType.Integer) {
						newValue = Integer.parseInt(TypeConverter.toString(newValue));
						rowResultSet.updateInt(columnIndex,
								TypeConverter.toInt(newValue));
					} else if (dataType == DataType.String)
						rowResultSet.updateString(columnIndex,
								TypeConverter.toString(newValue));
					
					rowResultSet.updateRow();
				} catch (SQLException e) {
					System.out.println("Failed to update value in database.");
				}

				// currentTable.getRow(id).getResultSet().update
			}
		});

		// jTable.setPreferredScrollableViewportSize(jTable.getPreferredSize());
		// jTable.setFillsViewportHeight(true);
	}

}
