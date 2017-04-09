package grading;

import visuals.Tab;

import java.awt.BorderLayout;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class GradingScaleInterface extends JPanel implements TableModelListener, Tab
{

	private int rows = 13, cols = 2, rowHeight = 30, colWidth = 30;
	
	private JTable letterTable;
	private JTabbedPane window;
	
	public GradingScaleInterface()
	{
		initPane();
		initTable();


	}

	private void initPane() {
		setLayout(new BorderLayout());
	}

	private void initTable() {
		letterTable = new JTable(rows, cols);
		letterTable.getModel().addTableModelListener(this);
		letterTable.setRowHeight(30);
		letterTable.setBackground(getBackground());
		
		
		
		
		letterTable.setSelectionModel(new DefaultListSelectionModel() {
			@Override
			public boolean isSelectedIndex(final int index) {
			boolean isSelected;
			if (index == 2) {
			isSelected = false;
			} else {
			isSelected = super.isSelectedIndex(index);
			}
			return isSelected;
			}
			});
		

		
		
		letterTable.getColumnModel().getColumn(0).setPreferredWidth(colWidth);
		add(letterTable, BorderLayout.WEST);
	}

	@Override
	public void tableChanged(TableModelEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getTabName() {
		return "Grading Scale";
	}

	@Override
	public String getTabImage() {
		return "grading.png";
	}
}
