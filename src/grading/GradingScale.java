package grading;

import java.util.ArrayList;
import java.util.HashMap;

import database.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import database.TableColumn.DataType;

public class GradingScale 
{
	private JSONArray letterGrades;
	private String name;
	public GradingScale(String jsonText, String name)
	{
		this.name = name;
		try {
			if (!jsonText.equals(""))
			{	
				letterGrades = new JSONArray(jsonText);
			}
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

		ArrayList<String> scaleNames = DataTypeManager.toStringArrayList(table.getAllFromColumn(TableProperties.SCALE_DESCRIPTION));

		for (int i = 0; i < scaleNames.size(); i++)
			if (scaleNames.get(i).equals(name))
				table.deleteRow(name, table.getColumnIndex(TableProperties.SCALE_DESCRIPTION));
		
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
		
		letterGrades = new JSONArray();
		letterGrades = objectToJSON(data);
		//System.out.println(letterGrades.toString());
		UpdateDatabaseItemRunnable test = new UpdateDatabaseItemRunnable(2, 1, (Object) letterGrades.toString(),DatabaseManager.getFilterdTable(TableManager.getTable(TableProperties.SCALE_TABLE_NAME), TableProperties.SCALE_DESCRIPTION, name), DataType.String);
		Thread myThread = new Thread(test);
		myThread.start();
		try {
			myThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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