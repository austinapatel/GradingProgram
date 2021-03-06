// Austin Patel & Jason Morris
// APCS
// Redwood High School
// 11/22/16
// TableColumn.java

package database;

/**
 * Holds information for a specific column in a database table.
 */
public class TableColumn {

    private String name, type;
    private ValueParameter valueParameter;

    public enum DataType {
        String, Integer, Double, Date
    }

    public TableColumn(String name, String type, ValueParameter valueParameter) {
        this.name = name;
        this.type = type;
        this.valueParameter = valueParameter;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public ValueParameter getValueParameter() {
        return valueParameter;
    }

}
