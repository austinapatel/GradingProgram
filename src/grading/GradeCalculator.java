package grading;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DataTypeManager;
import database.Table;
import database.TableManager;
import database.TableProperties;

public class GradeCalculator 
{

	private static ArrayList<GradingScale> scales = new ArrayList();
	
	public static ArrayList<GradingScale> getScales() {
		
//		for (int i = 0; i < scales.size(); i++)
//		{
//			scales.remove(i);
//		}
		scales.clear();
		//.scalesscales = new ArrayList<>();
		
		System.out.println(scales.size());
		TableManager.getTable(TableProperties.SCALE_TABLE_NAME).update();
		ArrayList<String> scaleStrings = DataTypeManager.toStringArrayList(TableManager.getTable(TableProperties.SCALE_TABLE_NAME).getAllFromColumn(TableProperties.SCALE_DATA));
		ArrayList<String> scaleNames = DataTypeManager.toStringArrayList(TableManager.getTable(TableProperties.SCALE_TABLE_NAME).getAllFromColumn(TableProperties.SCALE_DESCRIPTION));

		for (int i = 0; i < scaleStrings.size(); i++)
			scales.add(new GradingScale(scaleStrings.get(i), scaleNames.get(i)));
	

//		for (GradingScale scale : scales)
//			System.out.println(scale.getString());
		
		return scales;
	}
	
	public static void deleteScale(String name)
	{	
		
		Table table = TableManager.getTable(TableProperties.SCALE_TABLE_NAME);
		System.out.println(table.deleteRow(name, table.getColumnIndex(TableProperties.SCALE_DESCRIPTION) + 1));

	}
	
	
	
	
	public static void calculateGrades(int courseId, GradingScale scale, String dateRange)
	{
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
