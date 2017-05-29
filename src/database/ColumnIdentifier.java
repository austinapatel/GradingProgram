package database;

/**
 * Encapsulates data for a table name and a column name.
 */
public class ColumnIdentifier {

    private String tableName = null, columnName;

    public ColumnIdentifier(String columnName) {
        this.columnName = columnName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String toString() {
        if (tableName == null) {
            System.out.println("ERROR: forgot to set the table name in the ColumnIdentifier");

            return "Error in ColumnIdentifier";
        }

        return tableName + '.' + columnName;
    }
}
