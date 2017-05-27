package database;

import java.sql.ResultSet;
import java.util.ArrayList;

public class SqlBuilder {
    public enum JoinType {
        JOIN("JOIN"), INNER_JOIN("INNER JOIN"), LEFT_JOIN("LEFT JOIN"), RIGHT_JOIN("RIGHT JOIN"), FULL_OUTER_JOIN("FULL OUTER JOIN");

        private String joinText;

        private JoinType(String joinText) {
            this.joinText = joinText;
        }

        public String getJoinText() {
            return joinText;
        }
    }


    public enum Operator {
        OR("OR"), AND("AND");

        private String operatorText;

        private Operator(String operatorText) {
            this.operatorText = operatorText;
        }

        public String getOperatorType() {
            return operatorText;
        }
    }


    public static String getOperatorJoin(Operator operator, JoinType type, String JoinTable, String PriorTable, String joinColumn) {
        return " " + operator.getOperatorType() + " " + PriorTable + "." + joinColumn + " = " + JoinTable + "." + joinColumn;
    }


    public static String getJoinString(JoinType type, String JoinTable, String PriorTable, String joinColumn) {
        return " JOIN " + JoinTable + " ON " + PriorTable + "." + joinColumn + " = " + JoinTable + "." + joinColumn;
    }

    public static String filterSQL(Search... searches) {
        String filter = " WHERE ";

        for (int i = 0; i < searches.length; i++) {
            Search search = searches[i];

            filter += search.getTableName() + "." + search.getColumnName() + " = \"" + search.getSearchValue() + "\" ";

            if (i < searches.length - 1)
                filter += "AND ";
        }

        return filter;
    }

    public static String selectionSQL(String[][] selection) {
        String sql = "SELECT ";
        if (selection.length > 1) {
            for (int i = 0; i < selection.length; i++) {

                for (int j = 1; j < selection[i].length; j++) {
                    sql += selection[i][0] + "." + selection[i][j];

                    if (i != selection.length - 1) {
                        sql += ", ";
                    } else if (j != selection[i].length - 1) {
                        sql += ", ";
                    }
                }
                sql += " ";
            }
        } else
            sql += selection[0][0] + "." + selection[0][1];

        String tableNames = "";
        for (int x = 0; x < selection.length - 1; x++)
            tableNames += selection[x][0] + ", ";

        tableNames += selection[selection.length - 1][0];

        return sql + " FROM " + tableNames;
    }

}