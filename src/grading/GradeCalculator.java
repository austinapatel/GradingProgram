package grading;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DataTypeManager;
import database.TableManager;
import database.TableProperties;

public class GradeCalculator 
{

	private static ArrayList<GradingScale> scales = new ArrayList<>();
	
	public static ArrayList<GradingScale> getScales() {
		ArrayList<String> scaleStrings = DataTypeManager.toStringArrayList(TableManager.getTable(TableProperties.SCALE_TABLE_NAME).getAllFromColumn(TableProperties.SCALE_DATA));
		ArrayList<String> scaleNames = DataTypeManager.toStringArrayList(TableManager.getTable(TableProperties.SCALE_TABLE_NAME).getAllFromColumn(TableProperties.SCALE_DESCRIPTION));

		for (int i = 0; i < scaleStrings.size(); i++)
			scales.add(new GradingScale(scaleStrings.get(i), scaleNames.get(i)));
	

		for (GradingScale scale : scales)
			System.out.println(scale.getString());
		
		return scales;
	}
}
