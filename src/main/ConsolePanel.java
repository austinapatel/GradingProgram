package main;

import javax.swing.JPanel;

import visuals.Tab;

public class ConsolePanel extends JPanel implements Tab
{

	@Override
	public String getTabName() {
		// TODO Auto-generated method stub
		return "Console";
	}

	@Override
	public String getTabImage() {
		// TODO Auto-generated method stub
		return "grading.png";
	}

	@Override
	public void onTabSelected() {
		// TODO Auto-generated method stub
		
	}

}
