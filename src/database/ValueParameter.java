
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 12/24/16
// ValueParameter.java

package database;

/** Dictates attributes that a value must have to be valid input. */
public class ValueParameter {
	
	private boolean hasSetLength, hasSetValue, hasSelector;
	private int minValueLength, maxValueLength, minValue, maxValue;
	private String selectorTable, selectorOutputColumn, selectorLinkColumn;

	public ValueParameter() {
		hasSetLength = hasSetValue = hasSelector = false;
		minValueLength = maxValueLength = minValue = maxValue = 0;
		selectorTable = selectorOutputColumn = selectorLinkColumn = "";
	}
	
	public int getMinValueLength() {
		return minValueLength;
	}
	
	public int getMaxValueLength() {
		return maxValueLength;
	}
	
	public void setValueLengthRange(int minValueLength, int maxValueLength) {
		this.hasSetLength = true;
		
		this.minValueLength = minValueLength;
		this.maxValueLength = maxValueLength;
	}	
	
	public boolean isSetLength() {
		return hasSetLength;
	}
	
	public int getMinValue() {
		return minValue;
	}
	
	public int getMaxValue() {
		return maxValue;
	}

	public void setValueRange(int minValue, int maxValue) {
		this.hasSetValue = true;
		
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	public boolean isSetValue() {
		return hasSetValue;
	}

	public boolean hasSelector() {
		return hasSelector;
	}
	
	/**Sets the field to be have a JComboBox to pick input*/
	public void addSelector(String tableName, String visualColumnName, String linkColumnName) {
		selectorTable = tableName;
		selectorOutputColumn = visualColumnName;
		selectorLinkColumn = linkColumnName;
		hasSelector = true;
	}

	public String getSelectorTable() {
		return selectorTable;
	}

	public String getSelectorOutputColumn() {
		return selectorOutputColumn;
	}

	public String getSelectorLinkColumn() {
		return selectorLinkColumn;
	}
	
}
