package database;

public class SqlBuilder
{	
	public static String getJoinString(String JoinTable, String PriorTable, String joinColumn)
	{
		return "JOIN " + JoinTable + " ON " + PriorTable + "." + joinColumn + " = " + JoinTable + "."  + joinColumn;	
	}
		
	public static String Filter(String TableName, String TableColumn, String filterValue)
	{
		return "WHERE " + TableName + "." + TableColumn + " = " + filterValue;
	}
	
	public static String Selection(String[][] Selection, String[] Tables)
	{
		String selection = "";
		if (Selection.length > 1)
		{
		for (int i = 0; i < Selection.length; i++)
		{
			
			for (int j = 1; j < Selection[i].length; j++)
			{
				selection += Selection[i][0] + "." + Selection[i][j];
				
				if (i != Selection.length -1)
				{
					selection += ", ";
				}
				else if (j != Selection[i].length - 1)
				{
					selection += ", ";
				}
			}	
			selection += " ";
		}
		}
		else
			selection = Selection[0][0] + "." + Selection[0][1];
		
		String tableNames = "";
		if (Tables.length > 1)
		{
			for (int i =0; i < Tables.length -1; i++)
			{
				tableNames += Tables[i] + ", ";
			}
			tableNames += Tables[Tables.length -1];
		}	
		else
		{
			tableNames += Tables[0];
		}
		return selection + " FROM " + tableNames;
	}
}