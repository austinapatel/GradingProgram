package visuals;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Array;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import database.*;

public class GradeBook extends InterfacePanel
{	
	private JList classList;
	private DefaultListModel listModel;
	private DatabaseJTable table;

	public GradeBook ()
	{		
		initClassTable();
		initClassPicker();
	}

	private void initClassPicker() {
		add(table.getTableHeader());
		add(table, BorderLayout.NORTH);
	}

	@Override
	public void onLayoutOpened() {

	}

	public void initClassTable()
	{
		// Hashmap: first is student id, which gives hashmap that needs assignment id which gives the students grade on the assignment
//		HashMap<Integer, HashMap<Integer, Integer>> assignmentScores = new HashMap<>();

		table = new DatabaseJTable(TableProperties.COURSES_TABLE_NAME);
		table.setCellEditor(new DatabaseCellEditor());
		table.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent me) {
				if (me.getClickCount() == 2) {
					int courseId = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());



//					DefaultTableModel model = new DefaultTableModel(rowsFormatted, columnsFormatted);

					JTable gradesTable = new JTable(new GradeBookTableModel(courseId));

					add(gradesTable.getTableHeader());
					add(gradesTable);

					validate();
					repaint();
		        }
		    }
		});

		add(table.getTableHeader());
		add(table);
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
}