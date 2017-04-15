
// Austin Patel & Jason Morris
// APCS
// Redwood High School
// 12/24/16
// ValueParameter.java

package database;

/** Dictates attributes that a value must have to be valid input. */
public class ValueParameter
{

	private boolean hasSetLength, hasSetValue, hasSelector;
	private int minValueLength, maxValueLength, minValue, maxValue;
	private String selectorTable, selectorLinkColumn;
	private String[] selectorOutputColumns;

	public ValueParameter()
	{
		hasSetLength = hasSetValue = hasSelector = false;
		minValueLength = maxValueLength = minValue = maxValue = 0;
		selectorTable = selectorLinkColumn = "";
		selectorOutputColumns = null;
	}

	public int getMinValueLength()
	{
		return minValueLength;
	}

	public int getMaxValueLength()
	{
		return maxValueLength;
	}

	public void setValueLengthRange(int minValueLength, int maxValueLength)
	{
		this.hasSetLength = true;

		this.minValueLength = minValueLength;
		this.maxValueLength = maxValueLength;
	}

	public boolean isSetLength()
	{
		return hasSetLength;
	}

	public int getMinValue()
	{
		return minValue;
	}

	public int getMaxValue()
	{
		return maxValue;
	}

	public void setValueRange(int minValue, int maxValue)
	{
		this.hasSetValue = true;

		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	public boolean isSetValue()
	{
		return hasSetValue;
	}

	public boolean hasSelector()
	{
		return hasSelector;
	}


	/**Sets the field to be have a JComboBox to pick input*/
	public void addSelector(String tableName, String... visualColumnNames)
	{
		selectorTable = tableName;
		selectorOutputColumns = visualColumnNames;
		selectorLinkColumn = tableName.toLowerCase() + "Id";
		hasSelector = true;
	}

	public String getSelectorTable()
	{
		return selectorTable;
	}

	public String[] getSelectorOutputColumns()
	{
		return selectorOutputColumns;
	}

	public String getSelectorLinkColumn()
	{
		return selectorLinkColumn;
	}

}
