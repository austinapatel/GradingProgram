// Austin Patel & Jason Morris
// APCS
// Redwood High School
// 10/13/16
// DatabaseManager.java

package database;

import database.TableColumn.DataType;

import java.sql.*;

/**
 * Abstracts mySQL database management operations. "init() must be called before
 * use."
 */
public class DatabaseManager {

    private static Connection connection;

    public DatabaseManager() {

    }

    public static void init(String secretKey) {
        DatabaseManager.connectToRemote(secretKey);
    }

    public static void init(String url, String username, String password) {
        DatabaseManager.connectToRemote(url, username, password);
    }

    /**
     * Deletes a table.
     */
    public static void deleteTable(String tableName) {
        try {
            DatabaseManager.getSQLStatement("DROP TABLE " + tableName).executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to delete table \"" + tableName + "\".");
        }
    }

    /**
     * Returns the ResultSet for a given table.
     */
    public static ResultSet getTable(Table table) {
        try {
            String sql = "SELECT * FROM " + table.getName();
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(sql);

            return rs;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public static ResultSet getFilterdTable(Table table, String filter, String filterValue) {

        // SELECT * FROM GRADES WHERE GRADES_STUDENT_ID = value

        try {
            String sql = "SELECT * FROM " + table.getName() + " WHERE " + filter + " = " + '\"' + filterValue + '\"';
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(sql);
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static ResultSet executeSqlStatement(String sql) {
        try {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(sql);
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ResultSet getJoinedTable(String table1Name, String table2Name, String[] tableAndColumnNames, String table1JoinColumn, String table2JoinColumn, String tableNameAndFilter, String filterValue) {
        //table1        table2         table1.column       table2.column               table2 filterSQL name  filterSQL value

        String selection = "";
        if (tableAndColumnNames.length > 1) {
            for (int i = 0; i < tableAndColumnNames.length - 1; i++) {
                selection += tableAndColumnNames[i] + ", ";
            }
            selection += tableAndColumnNames[tableAndColumnNames.length - 1];
        } else
            selection = tableAndColumnNames[0];

        // SELECT * FROM Students JOIN Enrollments ON Students.studentId = Enrollments.studentId WHERE Enrollments.courseId = "1"
        try {
            String sql = "SELECT " + selection + " FROM " + table1Name + " JOIN "
                    + table2Name + " ON " + table1Name + "." + table1JoinColumn + " = "
                    + table2Name + "." + table2JoinColumn + " WHERE " + tableNameAndFilter + " = " + '\"' + filterValue + '\"';

            System.out.println(sql);

            return DatabaseManager.getSQLStatement(sql).executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ResultSet select(String tableName, Selection selection, Filter filter) {
        selection.setTableName(tableName);
        filter.setTableName(tableName);

        return DatabaseManager.executeSqlStatement(selection + "" + filter);
    }

    public static ResultSet getTripleJoinedTable(String table1Name, String table2Name, String table3Name, String[][] tableAndColumnNames, String table1JoinColumn, String table2JoinColumn, String table1SecondJoinColumn, String table3JoinColumn, String tableNameAndFilter, String filterValue, String groupByTableNameAndColumn) {

        //SELECT Grades.studentId, Grades.points, Assignments.value, Students.firstname, Students.lastname From Grades
        //Join Students On Grades.studentId = Students.studentId
        //Join Assignments On Grades.assignmentId = Assignments.assignmentId
        //WHERE Assignments.courseId = "1"

        String selection = "";
        if (tableAndColumnNames.length > 1) {
            for (int i = 0; i < tableAndColumnNames.length; i++) {

                for (int j = 1; j < tableAndColumnNames[i].length; j++) {
                    selection += tableAndColumnNames[i][0] + "." + tableAndColumnNames[i][j];

                    if (i != tableAndColumnNames.length - 1) {
                        selection += ", ";
                    } else if (j != tableAndColumnNames[i].length - 1) {
                        selection += ", ";
                    }
                }

                selection += " ";
            }
        } else
            selection = tableAndColumnNames[0][0] + "." + tableAndColumnNames[0][1];

        try {
            String sql = "SELECT " + selection + " FROM " + table1Name + " JOIN "
                    + table2Name + " ON " + table1Name + "." + table1JoinColumn + " = "
                    + table2Name + "." + table2JoinColumn + " JOIN " + table3Name + " ON "
                    + table1Name + "." + table1SecondJoinColumn + " = " + table3Name + "." + table3JoinColumn + " WHERE " + tableNameAndFilter + " = " + '\"' + filterValue + '\"';


            if (groupByTableNameAndColumn != null) {
                sql += " GROUP BY " + groupByTableNameAndColumn;
            }
            System.out.println(sql);

            return DatabaseManager.getSQLStatement(sql).executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Finishes the insert process for a row into a table.
     */
    public static void endRowInsert(Table table) {
        ResultSet resultSet = table.getResultSet();

        try {
            resultSet.insertRow();
            resultSet.moveToCurrentRow();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(
                    "Insert row already added. Change values that need to be unique in row before adding a new row.");
        }
    }

    /**
     * Returns the name of the data type from a String containing a mySQL data
     * type.
     */
    public static DataType getSQLType(String name) {
        if (name.contains("VARCHAR") || name.contains("CHAR"))
            return DataType.String;
        else if (name.contains("INT"))
            return DataType.Integer;
        else if (name.contains("DOUBLE"))
            return DataType.Double;
        else if (name.contains("DATE"))
            return DataType.Date;
        else
            return null;
    }

    /**
     * Opens the remote connection to the database.
     */
    private static void connectToRemote(String secretString) {
        String url = DatabasePropertiesManager.read(secretString, "db", "url");
        String username = DatabasePropertiesManager.read(secretString, "db", "username");
        String password = DatabasePropertiesManager.read(secretString, "db", "password");

        System.out.println(url);

        try {
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);

        } catch (Exception e) {
            System.out.print(e);
        }
    }

    private static void connectToRemote(String url, String username, String password) {
        try {
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test a connection to a database.
     */
    public static boolean testConnection(String url, String username, String password) {
        try {

            //System.out.println(DatabasePropertiesManager.read(new String("db", "url").equals(url));
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);

            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("Successfully connected to database.");
            con.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * Returns the mySQL prepared table given a command.
     */
    public static PreparedStatement getSQLStatement(String mySQLCommand) {
        System.out.println(mySQLCommand);
        try {
            return connection.prepareStatement(mySQLCommand);
        } catch (SQLException e) {
            System.out.println("The operation " + mySQLCommand + " failed.");
            e.printStackTrace();

            return null;
        }
    }

    /**
     * Creates a single table.
     */
    public static void createTable(Table table) {

        String columnInfo = "";

        for (TableColumn column : table.getTableColumns())
            columnInfo += column.getName() + ' ' + column.getType() + ", ";

        columnInfo += "PRIMARY KEY (" + table.getPrimaryKey() + ")";

        try {

            String sql = "CREATE TABLE IF NOT EXISTS " + table.getName() + "(" + columnInfo + ")";
            DatabaseManager.getSQLStatement(sql).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}