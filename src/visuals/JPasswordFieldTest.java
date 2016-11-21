
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 10/13/16
// JPasswordFieldTest.java

package visuals;

import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**Password entry prompt for database credentials.*/
public class JPasswordFieldTest extends JFrame
{
	
	public JPasswordFieldTest()
	{
		setIconImage(new ImageIcon("icon.png").getImage());
		setResizable(false);
		setSize(180, 70);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Enter Credentials");
		
		setLayout(new GridLayout(2, 2));
		
		JLabel label = new JLabel("User Name:  ", SwingConstants.RIGHT);
		JLabel label2 = new JLabel("Password:  ", SwingConstants.RIGHT);
		JTextField userNameField = new JTextField(20);
		JPasswordField passwordField = new JPasswordField();
		
		add(label);
		add(userNameField);
		add(label2);
		add(passwordField);
	}

	public static void main(String[] args)
	{
		new JPasswordFieldTest();
	}
}