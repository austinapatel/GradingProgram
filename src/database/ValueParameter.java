
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 12/24/16
// ValueParameter.java

package database;

/** Dictates attributes that a value must have to be valid input. */
public class ValueParameter {
	
	private boolean hasSetLength, hasSetValue;
	private int minValueLength, maxValueLength, minValue, maxValue;

	public ValueParameter() {
		hasSetLength = hasSetValue = false;
		minValueLength = maxValueLength = minValue = maxValue = 0;
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
		this.hasSetLength = true;
		
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	public boolean isSetValue() {
		return hasSetValue;
	}
	
}
