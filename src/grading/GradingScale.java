package grading;

import org.json.JSONArray;

public class GradingScale 
{
	private Object[][] letterGrades = { {"A+", 100} , {"A", 93}, {"A-", 90} ,
			{"B+", 87} , {"B", 83} , {"B-", 80} ,
			{"C+", 77} , {"C", 73} , {"C-", 70} ,
			{"D+", 67} , {"D", 63} , {"D-", 60} ,
			{"F", 0}}; 
	
	JSONArray myArray = new JSONArray();
	
	public GradingScale(double[] values)
	{
		if (values != null)
		{
		for (int i = 0; i < letterGrades.length; i++)
			letterGrades[i][1] = values[i];
		}	
		
		
	}



	public String getLetterGrade(double percentGrade){
		for(int i = 0; i < letterGrades.length; i++){
			if(percentGrade >= (int)letterGrades[i][1])
				return (String) letterGrades[i][0];
		}
		return "F";
	}
	
}