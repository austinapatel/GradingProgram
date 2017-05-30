package visuals;

import database.DatabaseCellEditor;
import database.GradeBookTableModel;
import database.TableProperties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.MessageFormat;

public class GradeBook extends InterfacePanel implements ActionListener  {

    private DatabaseJTable classTable;
    private JTable gradesTable;
    private GradeBookTableModel gradesTableModel;
    private JButton printTable;

    public GradeBook()
    {
        printTable = new JButton("Print Table");
        printTable.addActionListener(this);
        
        initClassTable();
    }

    @Override
    public void onLayoutOpened() {
        if (gradesTableModel != null) {
            gradesTableModel.refresh();
            gradesTableModel.fireTableDataChanged();
        }
    }

    public void initClassTable() {
        classTable = new DatabaseJTable(TableProperties.COURSES_TABLE_NAME);
        classTable.setCellEditor(new DatabaseCellEditor());

        classTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    // Remove the previous grades table
                    if (gradesTable != null) {
                        remove(gradesTable.getTableHeader());
                        remove(gradesTable);
                    }

                    // Add the new grades table
                    int courseId = Integer.parseInt(classTable.getValueAt(classTable.getSelectedRow(), 0).toString());

                    gradesTable = new JTable(gradesTableModel = new GradeBookTableModel(courseId));

                    add(gradesTable.getTableHeader());
                    add(gradesTable);
                    add(printTable);
                    
                   
                    
                    
                    validate();
                    repaint();
                }
            }
        });

        add(classTable.getTableHeader());
        add(classTable);
    }

    private void printTable(JTable table)
    {
    	MessageFormat header = new MessageFormat("Page {0,number,integer}");
    	try {
    	    table.print(JTable.PrintMode.FIT_WIDTH, header, null);
    	} catch (java.awt.print.PrinterException e) {
    	    System.err.format("Cannot print %s%n", e.getMessage());
    	}
    }
    
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(printTable))
		{
			printTable(gradesTable);
		}
	}
}