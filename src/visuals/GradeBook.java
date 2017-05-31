package visuals;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.Printable;
import java.text.MessageFormat;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;



import database.DatabaseCellEditor;
import database.GradeBookTableModel;
import database.TableProperties;
import utilities.ColumnsAutoSizer;
import utilities.PrintTable;

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
                    ColumnsAutoSizer.sizeColumnsToFit(gradesTable);
                    validate();
                    repaint();
                }
            }
        });

        add(classTable.getTableHeader());
        add(classTable);
    }

 
    
    @Override
    public void keyTyped(KeyEvent e)
    {
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
			new PrintTable(gradesTable);
			
		}
	}
}