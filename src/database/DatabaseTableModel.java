
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

	public DatabaseTableModel(Table table) {
		this.table = table;
		this.resultSet = table.getResultSet();
	}
	
	/**Changes the table of the model.*/
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
		int rows = 0;

		try {
			resultSet.first();

			while (resultSet.next()) {
				if (rows == 0)
					rows++;
				rows++;
			}

			resultSet.first();
		} catch (SQLException e) {
			System.out.println("Failed to determine the number of rows in "
					+ table.getName());
		}

		return rows;
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

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		DataType dataType = DatabaseManager
				.getSQLType(table.getTableColumns()[columnIndex].getType());

		columnIndex++;
		rowIndex++;
		
		Thread updateRowThread = new Thread(new UpdateDatabaseItemRunnable(columnIndex, rowIndex,
				value, dataType, resultSet));
		
//		updateRowThread.
		
		updateRowThread.start();
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return true;
	}

	@Override
	public String getColumnName(int col) {
		return table.getTableColumns()[col].getName();
	}
}
