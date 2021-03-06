package visuals;

import database.DatabaseCellEditor;
import database.GradeBookTableModel;
import database.TableProperties;
import utilities.PrintTable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.text.MessageFormat;

public class GradeBook extends InterfacePanel implements ActionListener  {

    private DatabaseJTable classTable;
    private JTable gradesTable;
    private GradeBookTableModel gradesTableModel;
    private JButton printTable;
    private int courseId = -1;

    public GradeBook() {
        printTable = new JButton("Print Table");
        printTable.addActionListener(this);
        
        initClassTable();
    }

    @Override
    public void onLayoutOpened() {
        classTable.refreshTableContent();

        if (gradesTableModel != null) {
            gradesTableModel.refresh();
            gradesTableModel.fireTableDataChanged();

            showCurrentTable();
        }
    }

    public void initClassTable() {
        classTable = new DatabaseJTable(TableProperties.COURSES_TABLE_NAME);
        classTable.setCellEditor(new DatabaseCellEditor());

        classTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    // Add the new grades table
                    courseId = Integer.parseInt(classTable.getValueAt(classTable.getSelectedRow(), 0).toString());
                    showCurrentTable();
                }
            }
        });

        add(classTable.getTableHeader());
        add(classTable);
    }

    private void showCurrentTable() {
        // Remove the previous grades table
        if (gradesTable != null) {
            remove(gradesTable.getTableHeader());
            remove(gradesTable);
        }

        gradesTable = new JTable(gradesTableModel = new GradeBookTableModel(courseId));

        add(gradesTable.getTableHeader());
        add(gradesTable);
        add(printTable);

        validate();
        repaint();
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
			try {
				gradesTable.print();
			} catch (PrinterException e1) {
				e1.printStackTrace();
			}
			new PrintTable(gradesTable);

			printTable(gradesTable);
		}
	}
}