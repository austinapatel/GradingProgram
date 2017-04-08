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
	
	public static void getScales() {
		ArrayList<String> scaleStrings = DataTypeManager.toStringArrayList(TableManager.getTable(TableProperties.SCALE_TABLE_NAME).getAllFromColumn(TableProperties.SCALE_DATA));

		for (String s : scaleStrings)
			scales.add(new GradingScale(s));

		for (GradingScale scale : scales)
			System.out.println(scale.getString());
	}
}
