// Austin Patel
// 4/10/2017
// DatabaseComboBoxContent.java

package visuals;


import database.DataTypeManager;
import database.TableManager;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.util.ArrayList;

/**
 * Contains information regarding the content of a JComboBox that refers to data
 * in that database to facilitate updating its content to respond to changes that
 * occur to the data in the database.
 */
public class DatabaseJComboBox extends JComboBox<String> implements PopupMenuListener {

    private String tableName;
    private String[] columns;

    public DatabaseJComboBox(String tableName, String... columns) {
        this.tableName = tableName;
        this.columns = columns;

        addPopupMenuListener(this);
    }

    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) getModel();
        model.removeAllElements();

        ArrayList<String> combined = new ArrayList<>();

        for (String column : columns) {
            ArrayList<String> values = DataTypeManager.toStringArrayList(TableManager.getTable(tableName).getAllFromColumn(column));

            while (values.size() > combined.size())
                combined.add("");

            for (int i = 0; i < combined.size(); i++)
                combined.set(i, combined.get(i) + " " + values.get(i));
        }

        for (String value : combined)
            model.addElement(value);
    }

    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

    }

    @Override
    public void popupMenuCanceled(PopupMenuEvent e) {

    }
}
