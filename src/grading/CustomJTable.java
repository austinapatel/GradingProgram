package grading;

import database.DataTypeManager;
import database.Table;
import database.TableColumn;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.print.Printable;
import java.text.MessageFormat;
import java.util.ArrayList;

public class CustomJTable extends JTable {
   
    public CustomJTable(Table table2, DefaultTableModel model) {
        super(model);
        TableColumn[] tableColumns = table2.getTableColumns();
        ArrayList<ArrayList<String>> tableContent = new ArrayList<>();
        String[] columnNames = new String[tableColumns.length];

        for (int i = 0; i < tableColumns.length; i++) {
            columnNames[i] = tableColumns[i].getName();
        }
        model.setColumnIdentifiers(columnNames);

        for (TableColumn tableColumn : tableColumns)
            tableContent.add(DataTypeManager.toStringArrayList(table2.getAllFromColumn(tableColumn.getName())));

        model.setRowCount(tableContent.get(0).size());
        model.setColumnCount(tableContent.size()); // jason got rid of minus 1

        for (int col = 0; col < model.getColumnCount(); col++)
            for (int row = 0; row < model.getRowCount(); row++)
                model.setValueAt(tableContent.get(col).get(row), row, col);
    }


    public int getColumnIndex(String columnName) {
        for (int i = 0; i < this.getColumnCount(); i++) {
            if (this.getColumnName(i).equals(columnName))
                return i;
        }
        return -1;
    }
}
