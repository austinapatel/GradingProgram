
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 12/25/16
// DatabaseCellEditor.java

package database;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.AbstractCellEditor;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.table.TableCellEditor;

/** Cell editor for JTable to create custom input methods. */
@SuppressWarnings("serial")
public class DatabaseCellEditor extends AbstractCellEditor
		implements TableCellEditor {

	private JComboBox<String> list;
	private JTextField textField;
	private ValueParameter valueParameter;

	public DatabaseCellEditor() {
		list = null;
		textField = null;
		valueParameter = null;
	}

	@Override
	public Object getCellEditorValue() {
		if (list != null) {
			// Link the selected item to the correct value from the specific
			// database table.
			Table selectorTable = TableManager
					.getTable(valueParameter.getSelectorTable());
			ResultSet selectorResultSet = selectorTable.getResultSet();
			TableColumn[] selectorTableColumns = selectorTable
					.getTableColumns();

			// Move the row of the item selected
			try {
				selectorResultSet.absolute(list.getSelectedIndex() + 1);
			} catch (SQLException e1) {
				System.out.println(
						"Failed to move to the row of the selector item in selector.");
				e1.printStackTrace();
			}

			// Get the correct value from the linked column.
			String linkColumnName = valueParameter.getSelectorLinkColumn();
			int linkColumnIndex = 0;

			for (int i = 0; i < selectorTableColumns.length; i++)
				if (selectorTableColumns[i].getName().equals(linkColumnName)) {
					linkColumnIndex = i + 1;
					break;
				}

			String linkedValue = null;
			try {
				linkedValue = selectorResultSet.getObject(linkColumnIndex)
						.toString();
			} catch (SQLException e) {
				System.out.println("Failed to get linked value with selector.");
				e.printStackTrace();
			}

			list = null;

			return linkedValue;
		} else {
			String value = textField.getText();
			textField = null;

			return value;
		}
	}

	/**
	 * Provides the correct input method based on the row and column for the
	 * database cell when editing.
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		if (value != null) {
			Border border = BorderFactory.createMatteBorder(1, 1, 1, 1,
					table.getSelectionBackground());

			// Get the data type of the column
			DatabaseTableModel model = (DatabaseTableModel) table.getModel();
			int databaseColumnIndex = table.getColumnModel().getColumn(column)
					.getModelIndex();
			TableColumn tableColumn = model.getTable()
					.getTableColumns()[databaseColumnIndex];
			valueParameter = tableColumn.getValueParameter();

			// Only use JComboBox for values that have an associated selector
			if ((valueParameter != null && !valueParameter.hasSelector())
					|| valueParameter == null) {
				textField = new JTextField();
				textField.setBorder(border);
				String text = model.getValueAt(row, column).toString();
				text = text.equals("0") ? "" : text;
				textField.setText(text);

				return textField;
			}

			// Set up selector
			Table selectorTable = TableManager
					.getTable(valueParameter.getSelectorTable());
			ResultSet selectorResultSet = selectorTable.getResultSet();
			String[] selectorValues = new String[selectorTable.getRowCount()];
			TableColumn[] selectorTableColumns = selectorTable
					.getTableColumns();
			String[] selectorOutputColumns = valueParameter
					.getSelectorOutputColumns();
			ArrayList<Integer> selecterColumnIndices = new ArrayList<Integer>();

			// Find indices of selector outputs in the table columns
			for (int k = 0; k < selectorOutputColumns.length; k++) {
				for (int i = 0; i < selectorTableColumns.length; i++) {
					TableColumn cur = selectorTableColumns[i];

					if (cur.getName().equals(selectorOutputColumns[k])) {
						selecterColumnIndices.add(i + 1);
						break;
					}
				}
				// if the current selecter table column is not actually a column
				// (could be a formattaing String)
				selecterColumnIndices.add(-k - 1); // Having the negative value
													// will tell the rest of the
													// program that this is a
													// formatting point
			}

			// Add all the values in the rows in the specific column to the list
			try {
				selectorResultSet.beforeFirst();
				int i = 0;
				while (selectorResultSet.next()) {
					selectorValues[i] = "";

					for (int index : selecterColumnIndices) {
						if (index < 0) { // This must be a formatting String
							selectorValues[i] = selectorValues[i].trim(); // Allow freedom in formatting
							
							int actualIndex = -(index + 1);
							selectorValues[i] += selectorOutputColumns[actualIndex];
						} else
							selectorValues[i] += selectorResultSet
									.getObject(index).toString() + " ";						
					}

					selectorValues[i] = selectorValues[i].substring(0,
							selectorValues[i].length() - 1);

					i++;
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			list = new JComboBox<String>(selectorValues);

			// When the space key action occurs, show the popup
			String ACTION_KEY = "spacePress";
			Action actionListener = new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent actionEvent) {
					@SuppressWarnings("unchecked")
					JComboBox<String> source = (JComboBox<String>) actionEvent
							.getSource();
					source.showPopup();
					source.requestFocus();
				}
			};

			// Simulate space key press to open popup menu when JComboBox
			// focused.
			KeyStroke spacePress = KeyStroke.getKeyStroke(' ');
			InputMap inputMap = list
					.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
			inputMap.put(spacePress, ACTION_KEY);
			ActionMap actionMap = list.getActionMap();
			actionMap.put(ACTION_KEY, actionListener);
			list.setActionMap(actionMap);

			list.addKeyListener(new KeyListener() {

				@Override
				public void keyTyped(KeyEvent e) {
				}

				@Override
				public void keyReleased(KeyEvent e) {
				}

				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						list.hidePopup();
						table.requestFocus();
					}
				}
			});

			return list;
		} else
			return null;
	}

}
