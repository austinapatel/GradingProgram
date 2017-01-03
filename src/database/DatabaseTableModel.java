
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

/** Relates a mySQL database to a JTable TableModel. */
@SuppressWarnings("serial")
public class DatabaseTableModel extends AbstractTableModel {

	private Table table;
	private ResultSet resultSet;

	public DatabaseTableModel() {
		
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

			return resultSet.getObject(column + 1);
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

		if (success)
			new Thread(new UpdateDatabaseItemRunnable(columnIndex, rowIndex,
					value, resultSet, dataType)).start();
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return true;
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
