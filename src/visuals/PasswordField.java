
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 10/13/16
// JPasswordFieldTest.java

package visuals;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**Password entry prompt for database credentials.*/
public class PasswordField extends JFrame
{
	private final int WIDTH = 800, HEIGHT = 350;
	
	private JButton submit = new JButton("Submit");
	
	public PasswordField()
	{
		setLayout(null);
		setIconImage(new ImageIcon("icon.png").getImage());
		setResizable(false);
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Enter Credentials");
		setLayout(null);
		
		JLabel label = new JLabel("User Name:");
		JLabel label2 = new JLabel("Password:");
		
		label.setHorizontalAlignment(JLabel.CENTER);
		label2.setHorizontalAlignment(JLabel.CENTER);
		
		JTextField userNameField = new JTextField(20);
		JPasswordField passwordField = new JPasswordField();
		
		Box box = Box.createVerticalBox();    // vertical box
			box.add(label);
		    box.add(userNameField);
		    box.add(label2);
		    box.add(passwordField);
		box.setSize(WIDTH, 200);
		add(box);  
		
		submit.setSize(WIDTH, 75);
		submit.setLocation(0, 220);
		add(submit);
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void setToOpen() {
		submit.addActionListener(new ActionListener() {         
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			    new Interface();
			}
		}); 
	}
}