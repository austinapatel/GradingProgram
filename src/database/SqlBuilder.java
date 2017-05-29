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

}