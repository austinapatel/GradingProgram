//package database;
//
//public class example
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//public class JDBCUpdatableRS {
//
//    private static final String DBURL = 
//               "jdbc:mysql://localhost:3306/mydb?user=usr&password=sql" +
//              "&useUnicode=true&characterEncoding=UTF-8";
//    private static final String DBDRIVER = "org.gjt.mm.mysql.Driver";
//  
//    static {
//        try {
//            Class.forName(DBDRIVER).newInstance();
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    private static Connection getConnection() 
//    {
//        Connection connection = null;
//        try {
//            connection = DriverManager.getConnection(DBURL);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        return connection;
//    }
//
//    public static void createEmployees()
//    {
//        Connection con = getConnection();
//        Statement stmt =null;
//        String createString;
//        createString = "CREATE TABLE  `mydb`.`employees` ("+
//         "`EmployeeID` int(10) unsigned NOT NULL default '0',"+
//         "`Name` varchar(45) collate utf8_unicode_ci NOT NULL default '',"+
//        "`Office` varchar(10) collate utf8_unicode_ci NOT NULL default '',"+
//        "`CreateTime` timestamp NOT NULL default CURRENT_TIMESTAMP,"+
//        "PRIMARY KEY  (`EmployeeID`)"+
//       ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;";            
//        try {
//            stmt = con.createStatement();
//               stmt.executeUpdate(createString);
//        } catch(SQLException ex) {
//            System.err.println("SQLException: " + ex.getMessage());
//        }
//        finally {
//            if (stmt != null) {
//                try {
//                    stmt.close();
//                } catch (SQLException e) {
//                    System.err.println("SQLException: " + e.getMessage());
//                }
//            }
//            if (con != null) {
//                try {
//                    con.close();
//                } catch (SQLException e) {
//                    System.err.println("SQLException: " + e.getMessage());
//                }
//            }
//        }
//    }
//    private static void dropEmployees()
//    {
//        Connection con = getConnection();
//        Statement stmt =null;
//        String createString;
//        createString = "DROP TABLE IF EXISTS `mydb`.`employees`;";            
//        try {
//            stmt = con.createStatement();
//            stmt.executeUpdate(createString);
//        } catch(SQLException ex) {
//            System.err.println("SQLException: " + ex.getMessage());
//        }
//        finally {
//            if (stmt != null) {
//                try {
//                    stmt.close();
//                } catch (SQLException e) {
//                    System.err.println("SQLException: " + e.getMessage());
//                }
//            }
//            if (con != null) {
//                try {
//                    con.close();
//                } catch (SQLException e) {
//                    System.err.println("SQLException: " + e.getMessage());
//                }
//            }
//        }
//    }
//    
//    public static void showEmployee() {
//        Connection con = getConnection();
//        Statement stmt =null;
//        try {
//            stmt = con.createStatement();
//               ResultSet rs = stmt.executeQuery("Select * from employees " 
//                       + where EmployeeID=1001");
//               if (rs.next()) {
//                   System.out.println("EmployeeID : " + 
//                            rs.getInt("EmployeeID"));
//                   System.out.println("Name : " + rs.getString("Name"));
//                   System.out.println("Office : " + rs.getString("Office"));
//               }
//               else {
//                   System.out.println("No Specified Record.");
//               }
//               rs.close();
//        } catch(SQLException ex) {
//            System.err.println("SQLException: " + ex.getMessage());
//        }
//        finally {
//            if (stmt != null) {
//                try {
//                    stmt.close();
//                } catch (SQLException e) {
//                    System.err.println("SQLException: " + e.getMessage());
//                }
//            }
//            if (con != null) {
//                try {
//                    con.close();
//                } catch (SQLException e) {
//                    System.err.println("SQLException: " + e.getMessage());
//                }
//            }
//        }
//        
//    }
//    public static void insertEmployee() {
//        Connection con = getConnection();
//        Statement stmt =null;
//        String sqlString = "SELECT EmployeeID, Name, " + 
//             " Office FROM employees;";
//        try {
//            stmt = con.createStatement(
//                    ResultSet.TYPE_SCROLL_SENSITIVE, 
//                    ResultSet.CONCUR_UPDATABLE);
//            ResultSet rs = stmt.executeQuery(sqlString);
//               
//            //Check the result set is an updatable result set
//            int concurrency = rs.getConcurrency();
//            if (concurrency == ResultSet.CONCUR_UPDATABLE) {
//                rs.moveToInsertRow(); 
//                rs.updateInt(1, 1001);
//                rs.updateString(2, "Divad Walker"); 
//                rs.updateString(3, "HQ101"); 
//                rs.insertRow();
//                rs.moveToCurrentRow();                
//            } else {
//            System.out.println("ResultSet is not an updatable result set.");
//            }
//            rs.close();
//        } catch(SQLException ex) {
//            System.err.println("SQLException: " + ex.getMessage());
//        }
//        finally {
//            if (stmt != null) {
//                try {
//                    stmt.close();
//                } catch (SQLException e) {
//                    System.err.println("SQLException: " + e.getMessage());
//                }
//            }
//            if (con != null) {
//                try {
//                    con.close();
//                } catch (SQLException e) {
//                    System.err.println("SQLException: " + e.getMessage());
//                }
//            }
//        }
//    }
//
//    public static void updateEmployee(){
//        Connection con = getConnection();
//        Statement stmt =null;
//        String sqlString = "SELECT EmployeeID, Name, Office " + 
//            " FROM employees WHERE EmployeeID=1001";
//        try {
//            stmt = con.createStatement(
//                    ResultSet.TYPE_SCROLL_SENSITIVE, 
//                    ResultSet.CONCUR_UPDATABLE);
//            ResultSet rs = stmt.executeQuery(sqlString);
//               
//            //Check the result set is an updatable result set
//            int concurrency = rs.getConcurrency();
//            if (concurrency == ResultSet.CONCUR_UPDATABLE) {
//                rs.first();
//                rs.updateString("Office", "HQ222");
//                rs.updateRow();
//            } else {
//            System.out.println("ResultSet is not an updatable result set.");
//            }
//            rs.close();
//        } catch(SQLException ex) {
//            System.err.println("SQLException: " + ex.getMessage());
//        }
//        finally {
//            if (stmt != null) {
//                try {
//                    stmt.close();
//                } catch (SQLException e) {
//                    System.err.println("SQLException: " + e.getMessage());
//                }
//            }
//            if (con != null) {
//                try {
//                    con.close();
//                } catch (SQLException e) {
//                    System.err.println("SQLException: " + e.getMessage());
//                }
//            }
//        }
//    }
//    
//    public static void deleteEmployee(){
//        Connection con = getConnection();
//        Statement stmt =null;
//        String sqlString = "SELECT EmployeeID, Name, Office " + 
//              " FROM employees WHERE EmployeeID=1001";
//        try {
//            stmt = con.createStatement(
//                    ResultSet.TYPE_SCROLL_SENSITIVE, 
//                    ResultSet.CONCUR_UPDATABLE);
//            ResultSet rs = stmt.executeQuery(sqlString);
//               
//            //Check the result set is an updatable result set
//            int concurrency = rs.getConcurrency();
//            if (concurrency == ResultSet.CONCUR_UPDATABLE) {
//                rs.first();
//                rs.deleteRow();
//            } else {
//            System.out.println("ResultSet is not an updatable result set.");
//            }
//            rs.close();
//        } catch(SQLException ex) {
//            System.err.println("SQLException: " + ex.getMessage());
//        }
//        finally {
//            if (stmt != null) {
//                try {
//                    stmt.close();
//                } catch (SQLException e) {
//                    System.err.println("SQLException: " + e.getMessage());
//                }
//            }
//            if (con != null) {
//                try {
//                    con.close();
//                } catch (SQLException e) {
//                    System.err.println("SQLException: " + e.getMessage());
//                }
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        dropEmployees();
//        createEmployees();
//        insertEmployee();
//        System.out.println("\nAfter inserting a Record ...");
//        showEmployee();
//        updateEmployee();
//        System.out.println("\nAfter updating a Record ...");
//        showEmployee();
//        deleteEmployee();
//        System.out.println("\nAfter deleting a Record ...");
//        showEmployee();
//    }
//
//}