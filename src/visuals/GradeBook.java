package visuals;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import database.DatabaseCellEditor;
import database.TableProperties;

public class GradeBook extends JPanel implements Tab
{	
	private JList classList;
	private DefaultListModel listModel;
	private DatabaseJTable table;
	private JScrollPane tablePane;

	public GradeBook ()
	{		
		add(table);
	}
	
	public void initClassTable()
	{
		table = new DatabaseJTable(TableProperties.COURSES_TABLE_NAME);
		table.setCellEditor(new DatabaseCellEditor());
		 table.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent me) {
		    	DatabaseJTable table2 =(DatabaseJTable) me.getSource();
		        Point p = me.getPoint();
		        int row = table2.rowAtPoint(p);
		        if (me.getClickCount() == 2)
		        {
		        	{	        	
		        		
		        	}
		        }
		    }
		});
		tablePane = new JScrollPane(table);
	}
	private void initList() 
	{
		listModel = new DefaultListModel();
		listModel.addElement("hello");
		classList = new JList(listModel);
		classList.setBackground(getBackground());
		classList.setFont(new Font("Helvetica", Font.BOLD, 15));
		classList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JList list = (JList) evt.getSource();
				if (evt.getClickCount() == 2) 
				{

				} else if (evt.getClickCount() == 3)
				{

					int index = list.locationToIndex(evt.getPoint());
				}
			}
		});
	}
	
	@Override
	public String getTabName()
	{
		// TODO Auto-generated method stub
		return "GradeBook";
	}

	@Override
	public String getTabImage()
	{
		// TODO Auto-generated method stub
		return "grading_tab_icon.png";
	}

	@Override
	public void onTabSelected()
	{
		// TODO Auto-generated method stub
		
	}	
}
