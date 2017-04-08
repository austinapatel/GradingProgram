package grading;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import database.Table;
import database.TableManager;
import database.TableProperties;

public class GradingScale 
{
	private String[] letters = {"A+", "A", "A-","B+", "B", "B-", "C+", "C", "C-", "D+", "D", "D-"};	
	private JSONArray letterGrades = new JSONArray();
	public GradingScale(String jsonText)
	{
		try {
			letterGrades = new JSONArray(jsonText);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public GradingScale(double[] values)
	{
		for (int i = 0; i < letters.length; i++)
		{
			JSONObject obj = new JSONObject();
			try {
				obj.put(letters[i], values[i]);
				letterGrades.put(obj);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Table table = TableManager.getTable(TableProperties.SCALE_TABLE_NAME);
		System.out.println(letterGrades.toString());
		HashMap<String, Object> newValues = new HashMap<String, Object>() {{
			put(TableProperties.SCALE_DATA, letterGrades.toString());
			
		}};
		
		TableManager.insertValuesIntoNewRow(table, newValues);
	}
	public String getString()
	{
		return letterGrades.toString();
	}
	public String getLetterGrade(double percentage)
	{
		for (int i = 0; i < letterGrades.length(); i++)
		{
			try {
					
					double val = letterGrades.getJSONObject(i).getDouble(letters[i]);
					
					if (percentage >= val)
						return letterGrades.getJSONObject(i).keys().next().toString();
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
		return null;
	}
}