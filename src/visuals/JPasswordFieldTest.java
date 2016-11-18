
// Austin Patel & Jason Morris
// APCS
// Redwood High School
// 10/13/16
// Interface.java

package visuals;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class JPasswordFieldTest extends JFrame
{

	public static void main(String[] args)
	{
		//JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("JTextField Test");
		frame.setLayout(new GridLayout(2, 2));
		JLabel label = new JLabel("User Name:", SwingConstants.RIGHT);
		JLabel label2 = new JLabel("Password:", SwingConstants.RIGHT);
		JTextField userNameField = new JTextField(20);
		JPasswordField passwordField = new JPasswordField();
		frame.add(label);
		frame.add(userNameField);
		frame.add(label2);
		frame.add(passwordField);
		frame.setResizable(false);
		frame.setSize(200, 70);
		frame.setVisible(true);
	}
}