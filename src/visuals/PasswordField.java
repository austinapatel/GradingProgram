
// Austin Patel & Jason Morris & Lex VonKlark
// APCS
// Redwood High School
// 10/13/16
// JPasswordFieldTest.java

package visuals;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/** Password entry prompt for database credentials. */
public class PasswordField extends JFrame implements ActionListener {
	private JTextField userNameField;
	private JPasswordField passwordField;
	private JTextField databaseIpField;
	private JTextField databasePortField;
	private JTextField databaseNameField;
	private JCheckBox checkbox1;
	private JButton button1;

	public static void main(String[] args) {	
		PasswordField field = new PasswordField();
	}

	private final int WIDTH = 300, HEIGHT = 300;

	private JButton submit = new JButton("Submit");

	public PasswordField() {
		setLayout(null);
		setIconImage(new ImageIcon("icon.png").getImage());
		setResizable(false);
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Enter Credentials");
		setLayout(null);

		JLabel label = new JLabel("User Name:");
		JLabel label2 = new JLabel("Password:");
		JLabel label3 = new JLabel("Database Ip:");
		JLabel label4 = new JLabel("Database Port (default 3306):");
		JLabel label5 = new JLabel("Database Name:");

		button1 = new JButton();
		button1.setText("Test Connection");
		button1.setForeground(Color.BLACK);
		button1.setSize(160, 30);
		button1.setLocation(WIDTH / 2 - 80, 200);
		button1.setFocusable(false); // Don't let the button be pressed via
										// ENTER or SPACE
		button1.setVisible(true);
		button1.addActionListener(this);
		add(button1);

		checkbox1 = new JCheckBox("<html>Remember Login (Encrypted)</html>");
		// checkbox1.setBackground(Color.GRAY);
		checkbox1.setForeground(Color.BLACK);
		checkbox1.setSize(150, 30);
		checkbox1.setFocusable(false); // Prevent a "border" around the text

		label.setHorizontalAlignment(JLabel.CENTER);
		label2.setHorizontalAlignment(JLabel.CENTER);

		userNameField = new JTextField(20);
		passwordField = new JPasswordField();
		databaseIpField = new JTextField(20);
		databasePortField = new JTextField(20);
		databaseNameField = new JTextField(20);

		Box box = Box.createVerticalBox(); // vertical box
		box.add(label);
		box.add(userNameField);

		box.add(label2);
		box.add(passwordField);

		box.add(label3);
		box.add(databaseIpField);

		box.add(label4);
		box.add(databasePortField);

		box.add(label5);
		box.add(databaseNameField);

		box.add(checkbox1);
		box.setSize(250, 200);
		add(box);

		int subSize = 200;
		submit.setSize(subSize, 30);
		submit.setLocation(WIDTH / 2 - subSize / 2, 235);
		add(submit);
		submit.addActionListener(this);

		setLocationRelativeTo(null);
		setVisible(true);
	}

	private String convertUrl(String url, String port, String dbName) {
		return "jdbc\\:mysql\\://" + url + "\\:" + port + "/" + dbName
				+ "?autoReconnect\\=true&useSSL\\=false";

	}

	public void actionPerformed(ActionEvent e) {

	}
}