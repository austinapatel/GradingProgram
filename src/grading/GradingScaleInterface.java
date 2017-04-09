package grading;

import visuals.Tab;

import java.awt.BorderLayout;
import java.util.HashMap;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class GradingScaleInterface extends JPanel implements TableModelListener, Tab
{

	private int rows = 13, cols = 5, rowHeight = 30, colWidth = 30;
	
	private JTable letterTable;
	private JTabbedPane window;
	
	
	private int[] disabled_columns = {1, 3};
	private String[] disabled_labels = {"Below", "down to"};	
	private int disabled_col = 2, cur_col = 0;
	public GradingScaleInterface()
	{
		
		
		
		initPane();
		initTable();


	}

	private void initPane() {
		setLayout(new BorderLayout());
	}
	
	

	private void initTable() {
		DefaultTableModel tableModel = new DefaultTableModel(rows, cols) {

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       boolean edit = true;
		    	for (int i = 0; i < disabled_columns.length; i++)
		    		if (disabled_columns[i] == column)
		    			edit = false;
		    	return edit; 
		    }
		};
		
		
		letterTable = new JTable(tableModel);
		letterTable.getModel().addTableModelListener(this);
		letterTable.setRowHeight(30);
		letterTable.getColumnModel().getColumn(0).setPreferredWidth(colWidth);
		letterTable.setBackground(getBackground());
		
		//letterTable.setSelectionModel(new CustomModel(letterTable.getSelectionModel()));
		
		
		final ListSelectionModel sel = letterTable.getColumnModel().getSelectionModel();
        sel.addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //If the column is disabled, reselect previous column
                
            	for (int i = 0; i < disabled_columns.length; i++)
            	{
            	
	            	if (sel.isSelectedIndex(disabled_columns[i]))
	                {
	                	cur_col+=2;
	                	sel.setSelectionInterval(cur_col,cur_col);
	                	return;
	                }
            	}
                //Set current selection
                cur_col = sel.getMaxSelectionIndex();
            }
        });

       
		letterTable.setValueAt("From", 0, disabled_columns[0] );
		letterTable.setValueAt(disabled_labels[1], 0, disabled_columns[1] );
		
		for (int row = 1; row < letterTable.getRowCount(); row++)
		{
			for (int col = 0; col < letterTable.getColumnCount(); col++)
			{
				if (letterTable.isCellEditable(row, col) == false)
				{
					
					letterTable.setValueAt(disabled_labels[getUneditableIndex(col)], row, col);
				}
			}
		}
		
		add(letterTable, BorderLayout.WEST);
	}
	
	public int getUneditableIndex(int col)
	{
		
			if (letterTable.isCellEditable(0, col) == false)
			{
				
				for (int i =0; i < disabled_columns.length; i++)
				{
					if (disabled_columns[i] == col)
						return i;
				}
					
			}
			return 0;
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

	@Override
	public void onTabSelected() {

	}
}
