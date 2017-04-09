package grading;

import visuals.Tab;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class GradingScaleInterface extends JPanel implements TableModelListener, Tab
{

	private int rows = 13, cols = 5, rowHeight = 30, colWidth = 30;
	private static final Font STANDARD_FONT = new Font("Arial", Font.PLAIN, 30);
	private JTable letterTable;
	private JSplitPane splitPane;
	private Border blackline, raisedetched, loweredetched, raisedbevel, loweredbevel, empty;
	
	private int[] disabled_columns = {1, 3};
	private String[] disabled_labels = {"Below", "down to"};	
	private int disabled_col = 2, cur_col = 0;
	private JLabel label1;
	private JScrollPane scrollPane1, scrollPane2;
	
	
	public GradingScaleInterface()
	{
		initBorders();
		initLabels();
		initTable();
		initPane(); //make sure this is last line in constructor
	}
	
	
	private void initBorders()
	{
		blackline = BorderFactory.createLineBorder(Color.black);
		raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		raisedbevel = BorderFactory.createRaisedBevelBorder();
		loweredbevel = BorderFactory.createLoweredBevelBorder();
		empty = BorderFactory.createEmptyBorder();

	}
	
	private void initLabels()
	{
		label1 = new JLabel("", JLabel.CENTER);
		label1.setBorder(BorderFactory.createCompoundBorder(raisedbevel, loweredbevel));
		//label1.setLocation(10, 10);
		label1.setFont(STANDARD_FONT);
		label1.setText("Scale Description");
		label1.setOpaque(true);
		//label1.setBackground(Color.BLACK);
		//label1.setForeground(Color.WHITE);
		label1.setSize(label1.getPreferredSize());
		label1.setVisible(true);
		
	}

	private void initPane() {
		setLayout(new BorderLayout());
		
		scrollPane1 = new JScrollPane();
		scrollPane1.setColumnHeaderView(label1);
		
		scrollPane2 = new JScrollPane(letterTable);
		//scrollPane1.setLayout(new BorderLayout());
		//scrollPane2.setLayout(new BorderLayout());
		
		
	
		//scrollPane1.add(label1, ScrollPaneLayout.);
		//scrollPane2.add(letterTable);
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane1, scrollPane2);
		splitPane.setOneTouchExpandable(true);
	//	splitPane.setDividerLocation(150);
		
		
		
		splitPane.setResizeWeight(0.5);
		splitPane.setDividerSize(0);
		splitPane.setBorder(null);
		add(splitPane, BorderLayout.CENTER);
		
		 GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    Font[] fonts = e.getAllFonts(); // Get the fonts
		    for (Font f : fonts) {
		      System.out.println(f.getFontName());
		    }
		
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
		letterTable.setRowHeight(rowHeight);
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
