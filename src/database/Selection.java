package database;

import java.util.ArrayList;

/**
 * Holds information for a SQL selection
 */
public class Selection {

    private boolean selectAll;

    private String tableName = null;
    private ColumnIdentifier[] columnIdentifiers;

    /**
     * Defaults to selecting all columns.
     */
    public Selection() {
        selectAll = true;
    }

    /**
     * Takes specific columns.
     */
    public Selection(ColumnIdentifier... columnIdentifiers) {
        this.columnIdentifiers = columnIdentifiers;
    }

    public boolean isSelectAll() {
        return selectAll;
    }

    public void setSelectAll(boolean selectAll) {
        this.selectAll = selectAll;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;

        if (!selectAll)
            for (ColumnIdentifier columnIdentifier : columnIdentifiers)
                columnIdentifier.setTableName(tableName);
    }

    public ColumnIdentifier[] getColumnIdentifiers() {
        return columnIdentifiers;
    }

    public void setColumnIdentifiers(ColumnIdentifier[] columnIdentifiers) {
        this.columnIdentifiers = columnIdentifiers;
    }

    public String toString() {
        if (tableName == null) {
            System.out.println("ERROR: Forgot the set table name in Selection.java");

            return "Error in Selection.java";
        }

        String result = "SELECT ";

        if (selectAll)
            result += '*';
        else {
            for (int i = 0; i < columnIdentifiers.length; i++) {
                ColumnIdentifier columnIdentifier = columnIdentifiers[i];

                result += columnIdentifier.getTableName() + '.' + columnIdentifier.getColumnName();

                if (i < columnIdentifiers.length - 1)
                    result += ", ";
            }
        }

        result += " FROM ";

        if (selectAll)
            result += tableName;
        else {
            ArrayList<String> uniqueTableNames = new ArrayList<>();

            for (ColumnIdentifier columnIdentifier : columnIdentifiers)
                if (!uniqueTableNames.contains(columnIdentifier.getTableName()))
                    uniqueTableNames.add(columnIdentifier.getTableName());

            for (int i = 0; i < uniqueTableNames.size(); i++) {
                String uniqueTableName = uniqueTableNames.get(i);

                result += uniqueTableName;

                if (i < uniqueTableNames.size() - 1)
                    result += ", ";
            }

        }

        return result;
    }
}
