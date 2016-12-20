
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 12/19/16
// TypeConverter.java

package database;

/**Converts objects to their actual types.*/
public class TypeConverter {
	
	public enum DataType {
		Integer,
		String
	}
	
	public TypeConverter() {
		
	}
	
	public static int toInt(Object o)
	{
		return Integer.class.cast(o).intValue();
	}
	
	public static String toString(Object o)
	{
		return String.class.cast(o);
	}

}
