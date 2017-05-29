// Austin Patel
// 4/11/2017
// ColoredTableCellRenderer.java

package database;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ColoredTableCellRenderer extends DefaultTableCellRenderer {

    private static final Color desiredColor = new JPanel().getBackground();

    public ColoredTableCellRenderer() {

    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        //Cells are by default rendered as a JLabel.
        JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

        if (!isSelected)
            l.setBackground(desiredColor);

        //Return the JLabel which renders the cell.
        return l;
    }
}
