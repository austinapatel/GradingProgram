
// Austin Patel & Jason Morris 
// APCS
// Redwood High School
// 11/18/16
// ButtonPane.java

package visuals;

import javax.swing.JComponent;

public class ComponentPane extends JComponent
{
	
	protected JComponent[] components;
	
	public ComponentPane(int width, int height, JComponent[] components)
	{
		this.components = components;
		
		setSize(width, height);
	}
	
}
