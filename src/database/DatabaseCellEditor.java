
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 12/25/16
// DatabaseCellEditor.java

package database;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/** Cell editor for JTable to create custom error trapping and cell colors. */
@SuppressWarnings("serial")
public class DatabaseCellEditor extends AbstractCellEditor
		implements TableCellRenderer, TableCellEditor {

	@Override
	public Object getCellEditorValue() {
		return null;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		return null;
	}

	@Override
	public Component getTableCellRendererComponent(JTable arg0, Object arg1,
			boolean arg2, boolean arg3, int arg4, int arg5) {
		return null;
	}

}
