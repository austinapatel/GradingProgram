
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 12/22/16
// DatabaseTableModel.java

package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.AbstractTableModel;
import database.TableColumn.DataType;
import visuals.TableInterface;

/** Relates a mySQL database to a JTable TableModel. */
@SuppressWarnings("serial")
public class DatabaseTableModel extends AbstractTableModel {

	private Table table;
	private ResultSet resultSet;
	private TableInterface tableInterface;

	public DatabaseTableModel(TableInterface tableInterface) {
		this.tableInterface = tableInterface;
	}

	/** Changes the table of the model. */
	public void setTable(Table table) {
		this.table = table;
		this.resultSet = table.getResultSet();
	}

	@Override
	public int getColumnCount() {
		return table.getTableColumns().length;
	}

	@Override
	public int getRowCount() {
		return table.getRowCount();
	}

	@Override
	public Object getValueAt(int row, int column) {
		try {
			resultSet.absolute(row + 1);

			Object value = resultSet.getObject(column + 1);

			// Convert this value to its non-linked value when displaying to the
			// user in the JTable if the current column uses a selector
			TableColumn tableColumn = table.getTableColumns()[column];
			ValueParameter valueParameter = tableColumn.getValueParameter();
			
			if (valueParameter != null && valueParameter.hasSelector()) {
				int id = resultSet.getInt(column + 1);
				// Get the value of the selector item from the other table that is linked
				Table otherTable = TableManager.getTable(valueParameter.getSelectorTable());
				ResultSet otherResultSet = otherTable.getResultSet();
				otherResultSet.absolute(id + 1);
				
				// Add together the values from each of the visual columns
				String newValue = "";

				for (String columnName : valueParameter.getSelectorOutputColumns()) {
					// Check if columnName is actually a column (could be a formatting String)
					boolean isColumn = false;
					for (TableColumn tableColumn2 : otherTable.getTableColumns())
						if (tableColumn2.getName().equals(columnName)) {
							isColumn = true;
							break;
						}
					
					if (isColumn)
						newValue += otherResultSet.getString(columnName) + " ";
					else {
						newValue = newValue.trim();
						newValue += columnName;
					}
				}
				
				newValue = newValue.substring(0, newValue.length() - 1);
				
				value = newValue;
			}

			return value;
		} catch (SQLException e) {
			System.out.println(
					"Failed to get value from table " + table.getName() + ".");
		}

		return null;
	}

	public Table getTable() {
		return table;
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		DataType dataType = DatabaseManager
				.getSQLType(table.getTableColumns()[columnIndex].getType());

		TableColumn[] columns = table.getTableColumns();
		TableColumn column = columns[columnIndex];
		boolean success = true;
		ValueParameter valueParameter = column.getValueParameter();

		try {
			if (valueParameter != null) {
				if (valueParameter.isSetValue()) {
					if (dataType == DataType.Integer) {
						if (value.toString().length() > 0)
							if (value.toString().charAt(0) == '0')
								value = value.toString().substring(1);

						int intVal = Integer.parseInt(value.toString());

						if (intVal < valueParameter.getMinValue()
								|| intVal > valueParameter.getMaxValue())
							success = false;
					} else
						System.out.println(
								"Non-Integer values should not have set value.");
				}
				if (valueParameter.isSetLength()) {
					int length = value.toString().length();

					if (length < valueParameter.getMinValueLength()
							|| length > valueParameter.getMaxValueLength())
						success = false;
				}
			}
		} catch (Exception e) {
			success = false;
		}

		columnIndex++;
		rowIndex++;

		if (success) {
			resultSet = table.getResultSet();

			new Thread(new UpdateDatabaseItemRunnable(columnIndex, rowIndex,
					value, resultSet, dataType)).start();
		}
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		if (col == 0)
			return false;

		return !tableInterface.isLocked();
	}

	@Override
	public String getColumnName(int col) {
		String rawColumnName = table.getTableColumns()[col].getName();
		String columnName = "";

		for (Character c : rawColumnName.toCharArray()) {
			if (Character.isUpperCase(c))
				columnName += ' ';
			columnName += Character.toUpperCase(c);
		}

		return columnName;
	}
}
