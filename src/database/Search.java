package database;

/**Holds a query with a table column and a search value*/
public class Search {

    private String columnName;
    private String searchValue;

//    private String tableName;

    public Search(String columnName, String searchValue) {
        this.columnName = columnName;
        this.searchValue = searchValue;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

//    public String getTableName() {
//        return tableName;
//    }

//    public void setTableName(String tableName) {
//        this.tableName = tableName;
//    }

}
