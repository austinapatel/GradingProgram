// Austin Patel
// 4/9/2017
// GradingScaleTableModel.java

package grading;

import javax.swing.table.DefaultTableModel;

/**
 * Table Model for the grading scale interface
 */
public class GradingScaleTableModel extends DefaultTableModel {

    private int rows, columns;
    private int[] disabledColumns;

    public GradingScaleTableModel(int rows, int columns, int[] disabledColumns) {
        super(rows, columns);
        this.rows = rows;
        this.columns = columns;
        this.disabledColumns = disabledColumns;
    }

//    @Override
//    public int getRowCount() {
//        return super.getRowCount();
//    }
//
//    @Override
//    public int getColumnCount() {
//        return super.getColumnCount();
//    }

    @Override
    public boolean isCellEditable(int row, int column) {
        boolean edit = true;

        for (int i = 0; i < disabledColumns.length; i++)
            if (disabledColumns[i] == column)
                edit = false;

        return edit;
    }
}
