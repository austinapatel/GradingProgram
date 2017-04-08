
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 12/19/16
// TableManager.java

package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/** Holds all of the tables for easy accessibility. */
public class TableManager
{

	private static HashMap<String, Table> tables;

	public TableManager()
	{

	}

	public static void addTable(Table table)
	{
		if (TableManager.tables == null)
			TableManager.tables = new HashMap<String, Table>();

		TableManager.tables.put(table.getName(), table);
	}

	/**Returns a table given a name.*/
	public static Table getTable(String tableName)
	{
		return TableManager.tables.get(tableName);
	}

	/**Returns an array containing all of the tables.*/
	public static Table[] getAllTables()
	{
		ArrayList<Table> tablesArray = new ArrayList<Table>();

		Iterator<String> iter = tables.keySet().iterator();

		while (iter.hasNext())
			tablesArray.add(tables.get(iter.next()));

		Table[] finalTables = new Table[tablesArray.size()];
		for (int i = 0; i < tablesArray.size(); i++)
			finalTables[i] = tablesArray.get(i);

		return finalTables;
	}

	public static void insertValuesIntoNewRow(Table table, HashMap<String, Object> values) {
		try {
			table.addRow();

			ResultSet rs = table.getResultSet();
			if (rs.last()) {
				System.out.println("moved to last row");
			}
			
			
			//DatabaseManager.addToRow(table, values., columnIndex);
			
			
			
			
			
			
			
			Object[] keySetObjects = values.keySet().toArray();
			String[] keys = new String[keySetObjects.length];

			for (int i = 0; i < keySetObjects.length; i++)
				keys[i] = keySetObjects[i].toString();

			for (int i = 0; i < keys.length; i++) {
				int columnIndex = table.getColumnIndex(keys[i]);
				String currentKey = keys[i];
				Object value = values.get(currentKey);

				DatabaseManager.addToRow(table, value, columnIndex);
//				rs.updateObject(columnIndex, value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
