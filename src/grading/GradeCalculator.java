package grading;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.TableManager;
import database.TableProperties;

public class GradeCalculator 
{

	private static ArrayList<GradingScale> scales = new ArrayList();
	
	public static void getScales()
	{
	
		ResultSet data = TableManager.getTable(TableProperties.SCALE_TABLE_NAME).getResultSet();
		try {
			data.first();
			while(!data.isAfterLast())
			{
				scales.add(new GradingScale(data.getString(2)));
				data.next();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		for (GradingScale temp: scales)
		{
			System.out.println(temp.getString());
		}
	}
}
