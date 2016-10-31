import java.io.Console;

// Austin Patel & Jason Morris 
// APCS
// Redwood High School
// 10/13/16
// Main.java

public class Main
{

	public static void main(String[] args)
	{
		
		Console console = System.console();

      String enteredPassword =
          new String(console.readPassword("Please enter your password: "));
      
      System.out.println(enteredPassword);
      
//		new Interface();
	}

}
