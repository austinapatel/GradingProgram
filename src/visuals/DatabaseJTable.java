// Austin Patel
// 4/10/2017
// DatabaseJTable.java

package visuals;

import database.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**A JTable containing information in a database table.*/
public class DatabaseJTable extends JTable {

    private SelectableTableModel model;
    private Table table;
    private String[] columnNames;

    /**columnNames parameter are for specifically stating which columns should be shown
     * Defaults to all columns shown if no columnNames are passed in.*/
    public DatabaseJTable(String tableName, String... columnNames) {
        super(new SelectableTableModel());

        model = (SelectableTableModel) getModel();
        table = TableManager.getTable(tableName);
        this.columnNames = columnNames;

        refreshTableContent();

        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void refreshTableContent() {
        if (columnNames.length == 0) {
            TableColumn[] tableColumns = table.getTableColumns();

            columnNames = new String[tableColumns.length];

            for (int i = 0; i < tableColumns.length; i++)
                columnNames[i] = tableColumns[i].getName();
        }

        ArrayList<ArrayList<String>> tableContent = new ArrayList<>();

        model.setColumnNames(columnNames);

        for (String columnName : columnNames)
            tableContent.add(DataTypeManager.toStringArrayList(table.getAllFromColumn(columnName)));

        model.setRowCount(tableContent.get(0).size());
        model.setColumnCount(tableContent.size());

        for (int col = 0; col < model.getColumnCount(); col++)
            for (int row = 0; row < model.getRowCount(); row++)
                model.setValueAt(tableContent.get(col).get(row), row, col);
    }
}

class SelectableTableModel extends DefaultTableModel {

    private String[] columnNames;

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public String getColumnName(int index) {
        String rawName = columnNames[index];
        String columnName = "";

        // Format the column name for readability
        for (Character c : rawName.toCharArray()) {
            if (Character.isUpperCase(c))
                columnName += ' ';
            columnName += Character.toUpperCase(c);
        }

        return columnName;
    }

}
