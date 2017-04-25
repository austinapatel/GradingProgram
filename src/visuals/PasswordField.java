
// Austin Patel & Jason Morris
// APCS
// Redwood High School
// 10/13/16
// JPasswordFieldTest.java

package visuals;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import database.DatabaseManager;
import database.DatabasePropertiesManager;
import main.Main;
import utilities.AES;

/** Password entry prompt for database credentials. */
public class PasswordField extends JFrame implements ActionListener {
	private final String PROPERTIES_FILE = "db";

	private JTextField userNameField;
	private JTextField databaseIpField;
	private JTextField databasePortField;
	private JTextField databaseNameField;
	
	private JPasswordField passwordField;
	
	private JCheckBox checkbox1;
	
	private JButton testConnectionButton, usePreviousLoginButton, submitButton;
	
	private Box box;

	public static void main(String[] args) {
		PasswordField field = new PasswordField();
	}

	private final int WIDTH = 255, HEIGHT = 350;

	public PasswordField() {
		// DatabasePropertiesManager.deleteFile(PROPERTIES_FILE);
		setLayout(null);
		setIconImage(new ImageIcon(getClass().getClassLoader().getResource("icon.png")).getImage());
		setResizable(false);
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Enter Credentials");
		setLocationRelativeTo(null);
		setLayout(null);

		JLabel label = new JLabel("User Name:");
		JLabel label2 = new JLabel("Password:");
		JLabel label3 = new JLabel("Database Ip:");
		JLabel label4 = new JLabel("Database Port (default 3306):");
		JLabel label5 = new JLabel("Database Name:");

		testConnectionButton = new JButton();
		testConnectionButton.setText("Test Connection");
		testConnectionButton.setForeground(Color.BLACK);
		testConnectionButton.setSize(160, 30);
		testConnectionButton.setLocation(WIDTH / 2 - 80, 200);
		testConnectionButton.setFocusable(false); // Don't let the button be
													// pressed via
		// ENTER or SPACE
		testConnectionButton.setVisible(true);
		testConnectionButton.addActionListener(this);
		add(testConnectionButton);

		usePreviousLoginButton = new JButton();
		usePreviousLoginButton.setText("Use Previous Login");
		usePreviousLoginButton.setForeground(Color.BLACK);
		usePreviousLoginButton.setSize(160, 30);
		usePreviousLoginButton.setLocation(WIDTH / 2 - 80, 270);
		usePreviousLoginButton.setFocusable(false); // Don't let the button be
													// pressed via

		String filename = PROPERTIES_FILE;

		File f = new File(filename);
		if (f.exists())
			usePreviousLoginButton.setVisible(true);
		else
			usePreviousLoginButton.setVisible(false);

		usePreviousLoginButton.setVisible(true);
		usePreviousLoginButton.addActionListener(this);
		add(usePreviousLoginButton);
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
		box = Box.createVerticalBox(); // vertical box
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

		submitButton = new JButton("Submit");
		submitButton.setSize(subSize, 30);
		submitButton.setLocation(WIDTH / 2 - subSize / 2, 235);
		add(submitButton);
		submitButton.addActionListener(this);

		setLocationRelativeTo(null);
		setVisible(true);
	}

	private String convertUrl() {

		return "jdbc:mysql://" + databaseIpField.getText() + ":"
				+ databasePortField.getText() + "/"
				+ databaseNameField.getText()
				+ "?autoReconnect=true&useSSL=false";
	}

	private boolean testConnection() {
		if (DatabaseManager.testConnection(convertUrl(),
				userNameField.getText(),
				new String(passwordField.getPassword()))) {
			JOptionPane.showMessageDialog(null,
					"Successfully connected to database.");
			return true;

		} else {
			JOptionPane.showMessageDialog(null,
					"Could not connect to database.");
			return false;
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(usePreviousLoginButton)) {

			JPasswordField pf = new JPasswordField();
			int okCxl = JOptionPane.showConfirmDialog(null, pf,
					"Enter Password", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);

			if (okCxl == JOptionPane.OK_OPTION) {
				String password = new String(pf.getPassword());

				if (!password.trim().equals("")) {
					String url = DatabasePropertiesManager.read(password,
							PROPERTIES_FILE, "url");
					String username = DatabasePropertiesManager.read(password,
							PROPERTIES_FILE, "username");
					String pass = DatabasePropertiesManager.read(password,
							PROPERTIES_FILE, "password");

					if (url != null && username != null && pass != null
							&& DatabaseManager.testConnection(url, username,
									password)) {

						Main.main(new String[] { password });
						dispose();

					}
				}
			}

		}

		if (e.getSource().equals(testConnectionButton))
			testConnection();

		if (e.getSource().equals(submitButton)) {
			if (testConnection()) {
				final String secretKey = new String(
						passwordField.getPassword());

				if (checkbox1.isSelected()) {
					DatabasePropertiesManager.write(PROPERTIES_FILE,
							new String[] { "password", "url", "username" },
							new String[] { AES.encrypt(secretKey, secretKey),
									AES.encrypt(convertUrl(), secretKey),
									AES.encrypt(userNameField.getText(),
											secretKey) });
					Main.main(new String[] { secretKey });
					dispose();
				} else {
					DatabasePropertiesManager.deleteFile(PROPERTIES_FILE);
					Main.main(new String[] { convertUrl(),
							userNameField.getText(),
							new String(passwordField.getPassword()) });
					dispose();
				}
			} else
				JOptionPane.showMessageDialog(null,
						"Could not connect to database.");
		}
	}
}
