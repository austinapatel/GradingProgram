package database;

/**
 * Filter for an SQL statement -- the WHERE clause
 */
public class Filter {

    private Search[] searches;

    public Filter(Search... searches) {
        this.searches = searches;
    }

    public void setTableName(String tableName) {
        for (Search search : searches)
            search.setTableName(tableName);
    }

    public String toString() {
        String filter = " WHERE ";

        for (int i = 0; i < searches.length; i++) {
            Search search = searches[i];

            filter += search.getTableName() + "." + search.getColumnName() + " = \"" + search.getSearchValue() + "\" ";

            if (i < searches.length - 1)
                filter += "AND ";
        }

        return filter;
    }

}
