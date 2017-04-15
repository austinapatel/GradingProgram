package utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import visuals.Tab;


public class ConsolePanel extends JPanel implements Tab
{
	private JTextPane textComponent;
	JLabel label;
	public ConsolePanel()
	{
		
		
		
		textComponent = new JTextPane();
		textComponent.setFont(new Font("Terminal", Font.PLAIN, 14));
		textComponent.setBackground(Color.BLACK);
		//textComponent.setLineWrap(true);

		//textComponen
        //t.setLineWrap(true);
		
		this.setLayout(new BorderLayout());
		JScrollPane pane;
		
		 pane = new JScrollPane(textComponent,
			        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
			        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		
		add(pane, BorderLayout.CENTER);
		
		
		
		
		//add(new JScrollPane(textComponent));
		MessageConsole mc = new MessageConsole(textComponent);
		//mc.redirectOut();
		mc.redirectOut(Color.GREEN, System.out);
		mc.redirectErr(Color.RED, null);
		mc.setMessageLines(100);
		System.out.println("test");
		
		
	}

	@Override
	public String getTabName() {
		// TODO Auto-generated method stub
		return "Console";
	}

	@Override
	public String getTabImage() {
		// TODO Auto-generated method stub
		return "console.png";
	}

	@Override
	public void onTabSelected() {
		// TODO Auto-generated method stub
		
	}

}
