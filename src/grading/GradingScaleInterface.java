package grading;


import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import visuals.TableInterface;






public class GradingScaleInterface extends JPanel implements TableModelListener
{

	private int rows = 13, cols = 2, rowHeight = 30, colWidth = 30;
	
	private  JTable letterTable;
	private JTabbedPane window;
	
	public GradingScaleInterface(TableInterface tableInterface)
	{
		this.setLayout(new BorderLayout());
		letterTable = new JTable(rows, cols);
		letterTable.getModel().addTableModelListener(this);
		letterTable.setRowHeight(30);
		letterTable.getColumnModel().getColumn(0).setPreferredWidth(colWidth);
		this.add(letterTable, BorderLayout.WEST);
		tableInterface.addTab("Grading Scale", new ImageIcon("grading.png"), this);
	}

	@Override
	public void tableChanged(TableModelEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
