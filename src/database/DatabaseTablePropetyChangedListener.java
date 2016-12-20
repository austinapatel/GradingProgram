
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 12/20/16
// Main.java

package database;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Handles changes of the database data from a JTable and updates the value into
 * the table.
 */
public class DatabaseTablePropetyChangedListener
		implements PropertyChangeListener {

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		System.out.println("New value: " + e.getNewValue());
	}

}
