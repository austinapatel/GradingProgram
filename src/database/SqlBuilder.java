package database;
public class SqlBuilder
{	
	public enum JoinType
	{		
		JOIN("JOIN"), INNER_JOIN("INNER JOIN"), LEFT_JOIN("LEFT JOIN"), RIGHT_JOIN("RIGHT JOIN"), FULL_OUTER_JOIN("FULL OUTER JOIN");
		
		private String joinText;

		private JoinType(String joinText)
		{
		this.joinText = joinText;
		}
		
		public String getJoinText()
		{
			return joinText;
		}
	}
	
	
	public enum Operator
	{
		OR("OR"), AND("AND");
		
		private String operatorText;
		
		private Operator(String operatorText)
		{
			this.operatorText = operatorText;
		}
		public String getOperatorType()
		{
			return operatorText;
		}
	}
	
	
	public static String getOperatorJoin(Operator operator, JoinType type, String JoinTable, String PriorTable, String joinColumn)
	{
		return operator.getOperatorType() +  PriorTable + "." + joinColumn + " = " + JoinTable + "."  + joinColumn;	
	}
	
	
	public static String getJoinString(JoinType type, String JoinTable, String PriorTable, String joinColumn)
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