import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class JPasswordFieldDemo {

	public static void main(String[] argv) {
		final JFrame frame = new JFrame("JPassword Usage Demo");
		JLabel jlbPassword = new JLabel("Enter the password: ");
		JPasswordField jpwName = new JPasswordField(10);
		jpwName.setEchoChar('*');
		jpwName.addActionListener(new ActionListener() 
		{

			public void actionPerformed(ActionEvent e)
			{
				JPasswordField input = (JPasswordField) e.getSource();
				char[] password = input.getPassword();
				
					JOptionPane.showMessageDialog(frame, "Correct  password.");
				
			}
		});
		JPanel jplContentPane = new JPanel(new BorderLayout());
		jplContentPane.setBorder(BorderFactory.createEmptyBorder(20, 20,
				20, 20));
		jplContentPane.add(jlbPassword, BorderLayout.WEST);
		jplContentPane.add(jpwName, BorderLayout.CENTER);
		frame.setContentPane(jplContentPane);
		frame.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.pack();
		frame.setVisible(true);
	}

}