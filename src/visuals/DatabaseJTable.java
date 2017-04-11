// Austin Patel
// 4/10/2017
// DatabaseJTable.java

package visuals;

import database.DataTypeManager;
import database.Table;
import database.TableColumn;
import database.TableManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class DatabaseJTable extends JTable {

    private String tableName;
    private SelectableTableModel model;
    private Table table;

    public DatabaseJTable(String tableName) {
        super(new SelectableTableModel());

        this.tableName = tableName;
        model = (SelectableTableModel) getModel();
        table = TableManager.getTable(tableName);

        initTableContent();

        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void initTableContent() {
        TableColumn[] tableColumns = table.getTableColumns();
        ArrayList<ArrayList<String>> tableContent = new ArrayList<>();
        String[] columnNames = new String[tableColumns.length - 1];

        for (int i = 1; i < tableColumns.length; i++)
            columnNames[i - 1] = tableColumns[i].getName();

        model.setColumnNames(columnNames);

        for (TableColumn tableColumn : tableColumns)
            tableContent.add(DataTypeManager.toStringArrayList(table.getAllFromColumn(tableColumn.getName())));

        model.setRowCount(tableContent.get(0).size());
        model.setColumnCount(tableContent.size() - 1);

        for (int col = 0; col < model.getColumnCount(); col++)
            for (int row = 0; row < model.getRowCount(); row++)
                model.setValueAt(tableContent.get(col + 1).get(row), row, col);
    }
}

class SelectableTableModel extends DefaultTableModel {

    private String[] columnNames;

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public boolean isCellEditable(int row, int column){
        return false;
    }

    @Override
    public String getColumnName(int index) {
        String rawName = columnNames[index];
        String columnName = "";

        for (Character c : rawName.toCharArray()) {
            if (Character.isUpperCase(c))
                columnName += ' ';
            columnName += Character.toUpperCase(c);
        }

        return columnName;
    }

}
