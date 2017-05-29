// Austin Patel & Jason Morris
// APCS
// Redwood High School
// 12/2/16
// Table.java

package database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Lets tables utilize the operations in the DatabaseManager class and adds
 * common methods for all tables.
 */
public class Table {

    private String name, primaryKey;
    private TableColumn[] tableColumns;
    private ResultSet resultSet;

    public Table(String name, TableColumn[] tableColumns) {
        this.name = name;
        init(tableColumns, DatabaseManager.getTable(this));

        if (TableManager.createTable)
            createTable();
    }

    public Table(ResultSet resultSet) {
        TableColumn[] tableColumns = null;

        try {
            ResultSetMetaData metaData = resultSet.getMetaData();

            tableColumns = new TableColumn[metaData.getColumnCount()];
            // TODO: Fix weird for loop below
            tableColumns[0] = new TableColumn(metaData.getColumnName(1), metaData.getColumnTypeName(1), null);
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                tableColumns[i - 1] = new TableColumn(metaData.getColumnName(i), metaData.getColumnTypeName(i), null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        init(tableColumns, resultSet);
    }

    private void init(TableColumn[] tableColumns, ResultSet resultSet) {
        this.tableColumns = tableColumns;
        this.primaryKey = tableColumns[0].getName();
        this.resultSet = resultSet;
    }

    /**
     * Creates the table.
     */
    private void createTable() {
        DatabaseManager.createTable(this);
    }

    public void refresh() {
        resultSet = DatabaseManager.getTable(this);
    }

    public String getName() {
        return name;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public TableColumn[] getTableColumns() {
        return tableColumns;
    }

    /**
     * Determines the number of rows in the table.
     */
    public int getRowCount() {
        int rows = -1;

        try {
            resultSet.last();
            rows = resultSet.getRow();
        } catch (SQLException e) {
            System.out.println("Failed to determine the number of rows in " + name);
        }

        return rows;
    }

    /**
     * Adds a new row to the table with given values.
     */
    public void addRow() {
        try {
            int desiredRowID = getInsertID();

            resultSet.moveToInsertRow();

            setValueAt(-1, 0, desiredRowID);

            for (int i = 1; i < tableColumns.length; i++)
                setValueAt(-1, i, null);

            DatabaseManager.endRowInsert(this);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to insertValueIntoNewRow");
        }
    }

    public void addRow(HashMap<String, Object> values) {
        addRow();
        setValuesAt(getRowCount(), values);
    }

    public void setValuesAt(int rowIndex, HashMap<String, Object> values) {
        Object[] keySetObjects = values.keySet().toArray();
        String[] keys = new String[keySetObjects.length];

        for (int i = 0; i < keySetObjects.length; i++)
            keys[i] = keySetObjects[i].toString();

        for (int i = 0; i < keys.length; i++) {
            int columnIndex = getColumnIndex(keys[i]);
            String currentKey = keys[i];
            Object value = values.get(currentKey);

            setValueAt(rowIndex, columnIndex, value);

            try {
                resultSet.updateRow();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Adds a value of the correct type to a table row ResultSet.  If rowIndex is -1 then row will not change
     */
    public void setValueAt(int rowIndex, int columnIndex, Object value) {
        TableColumn.DataType type = DatabaseManager.getSQLType(tableColumns[columnIndex].getType());

        columnIndex++; // columnIndex starts at 1, not 0

        try {
            if (rowIndex != -1)
                resultSet.absolute(rowIndex);

            if (type == TableColumn.DataType.String) {
                if (value == null)
                    value = "";

                resultSet.updateString(columnIndex, value.toString());
            } else if (type == TableColumn.DataType.Integer) {
                if (value == null)
                    value = 0;

                Integer newValue = Integer.parseInt(value.toString());

                resultSet.updateInt(columnIndex, newValue);
            } else if (type == TableColumn.DataType.Double) {
                if (value == null)
                    value = 0d;

                resultSet.updateDouble(columnIndex, Double.class.cast(value));
            } else if (type == TableColumn.DataType.Date) {
                if (value == null)
                    value = 0;
                resultSet.updateDate(columnIndex, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            }


        } catch (Exception e) {
            System.out.println("Unable to add value " + value + " to " + name + " Column index: " + columnIndex + " Column Name: " + tableColumns[columnIndex].getName() + ".");
        }

    }

    private int getInsertID() {
        ArrayList<Integer> currentIDs = DataTypeManager.toIntegerArrayList(getAllFromColumn(tableColumns[0].getName()));

        //		resultSet.first();

        int largest = 0;
        for (int i : currentIDs)
            largest = (i > largest) ? i : largest;

        return largest + 1;
    }

    /**
     * Removes a row from the table given a value and the column that value is
     * in. Returns the index of the row that was deleted.
     */
    public int deleteRow(Object value, int column) {
        try {
            resultSet.beforeFirst();

            while (resultSet.next()) {
                if (resultSet.getObject(column).toString().equals(value.toString())) {
                    resultSet.deleteRow();
                    return resultSet.getRow();
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to delete row.");
        }

        return -1;
    }

    public int getColumnIndex(String columnName) {
        for (int i = 0; i < tableColumns.length; i++)
            if (tableColumns[i].getName().equals(columnName))
                return i;

        return -1;
    }

    public ArrayList<Object> getAllFromColumn(String columnName) {
        return getAllFromColumn(getColumnIndex(columnName), resultSet);
    }

    private static ArrayList<Object> getAllFromColumn(int columnIndex, ResultSet resultSet) {
        ArrayList<Object> data = new ArrayList<>();

        columnIndex++;

        try {
            resultSet.beforeFirst();

            if (!resultSet.next()) { // Checks if ResultSet is empty
                resultSet.beforeFirst();
                return data;
            }

            resultSet.first();

            while (!resultSet.isAfterLast()) {
                Object currentValue = resultSet.getObject(columnIndex);
                data.add(currentValue);

                resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    public ArrayList<Object> getSomeFromColumn(String returnColumnName, Search... searches) {
        return Table.getAllFromColumn(0, DatabaseManager.select(name, new Selection(new ColumnIdentifier(returnColumnName)), new Filter(searches)));
    }

    public ResultSet select(Selection selection, Filter filter) {
        return DatabaseManager.select(name, selection, filter);
    }

    public ResultSet select(Filter filter) {
        return DatabaseManager.select(name, new Selection(), filter);
    }
}
