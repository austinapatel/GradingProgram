package grading;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import database.DatabaseManager;
import database.Table;
import database.TableColumn.DataType;
import database.TableManager;
import database.TableProperties;
import database.UpdateDatabaseItemRunnable;

public class GradingScale 
{
	private JSONArray letterGrades;
	private String name;
	public GradingScale(String jsonText, String name)
	{
		
		this.name = name;
		try {
			letterGrades = new JSONArray(jsonText);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public GradingScale(String name, Object[][] data)
	{
		this.name = name;
		letterGrades = objectToJSON(data);
		Table table = TableManager.getTable(TableProperties.SCALE_TABLE_NAME);
		
		HashMap<String, Object> newValues = new HashMap<String, Object>() {{
			put(TableProperties.SCALE_DATA, letterGrades.toString());
			put(TableProperties.SCALE_DESCRIPTION, name);
			
		}};
		
		
		TableManager.insertValuesIntoNewRow(table, newValues);
	}
	
	
	
	public JSONArray objectToJSON(Object[][] data)
	{
		
		if (data!= null)
		{	
		JSONArray array = new JSONArray();
		for (int i = 0; i < data.length; i++)
		{
			JSONObject obj = new JSONObject();
			try {
				


				if (data[i][0] != null && data[i][1] != null)
				{
					obj.put(data[i][0].toString(), data[i][1].toString());
					array.put(obj);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return array;
		}
		return null;
		
	}
	
	public void update(Object[][] data)
	{
		letterGrades = objectToJSON(data);
		UpdateDatabaseItemRunnable test = new UpdateDatabaseItemRunnable(2, 1, (Object) letterGrades.toString(),DatabaseManager.getFilterdTable(TableManager.getTable(TableProperties.SCALE_TABLE_NAME), TableProperties.SCALE_DESCRIPTION, name), DataType.String);
		new Thread(test).start();
	}
	public String getString()
	{
		return letterGrades.toString();
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getGradeLetter(double percentage)
	{
		for (int i = 0; i < letterGrades.length(); i++)
		{
			try {
				if (percentage >= letterGrades.getJSONObject(i).getDouble(letterGrades.getJSONObject(i).keys().next().toString()))
					return letterGrades.getJSONObject(i).keys().next().toString(); 
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
		
	}
	
	public JSONArray getData()
	{
		return letterGrades;
	}
	
	
	
}