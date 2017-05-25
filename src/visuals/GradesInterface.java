package visuals;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import database.DatabaseManager;
import database.Table;
import database.TableProperties;
import grading.CustomJTable;
import grading.Grade;
import grading.GradeCalculator;
import grading.GradingScale;

public class GradesInterface extends InterfacePanel {

	private JButton backButton, printButton;
	private DatabaseJTable table;
	private JComboBox scaleList;
	private JScrollPane tablePane, tablePane2;
	private int currentCourse = 0;
	private String percentage = "Percentage", totalPoints = "Total Points", letterGrade = "Letter Grade", points = "Points";
	private CustomJTable jTable;
	
	public GradesInterface()
	{
		initButton();
		initTable();
		initPanel();
	}

	@Override
	public void onLayoutOpened() {

	}

	private void initButton()
	{
		backButton = new JButton("Back");
		backButton.setFont(new Font("Helvetica", Font.PLAIN, 18));
		// backButton.setForeground(Color.BLUE);
		backButton.setFocusable(false);
		backButton.setVisible(true);
		backButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				
			remove(tablePane2);
			remove(scaleList);
			add(tablePane);
			remove(backButton);
			remove(printButton);
			}
			
		});
		
		
		printButton = new JButton("Print Table");
		printButton.setFont(new Font("Helvetica", Font.PLAIN, 18));
		printButton.setFocusable(false);
		printButton.setVisible(true);
		printButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				
				PrinterJob pj = PrinterJob.getPrinterJob();
				pj.setCopies(1);
				pj.setJobName("test");
      // if (pageFormat == null) {
				    PageFormat pageFormat = pj.defaultPage();
      // }
				Printable printable = jTable.getPrintable(JTable.PrintMode.FIT_WIDTH, new MessageFormat("tes"), new MessageFormat("Test"));
				pj.setPrintable(printable, pageFormat);
				
				 if (pj.printDialog()) {
			            try {
			                pj.print();
			            } catch (PrinterException printErr) {
			                printErr.printStackTrace(System.err);
			            }
			        }

			}
			
		});
	}
	
	private void initTable()
	{	
	 table = new DatabaseJTable(TableProperties.COURSES_TABLE_NAME);
	 tablePane = new JScrollPane(table);
	 table.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent me) {
		    	DatabaseJTable table2 =(DatabaseJTable) me.getSource();
		        Point p = me.getPoint();
		        int row = table2.rowAtPoint(p);
		        if (me.getClickCount() == 2)
		        {
		        	{	        	
		        		currentCourse = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
		        		displayClass(currentCourse);
		        	
		        	}
		        }
		    }
		});
	}
	
	private Grade hasStudent(ArrayList<Grade> grades, int studentId)
	{
		
		for (Grade grade: grades)
		{
			if (grade.getStudentId() == studentId)
				return grade;
		}
		return null;
	}
	
	private void displayClass(int id)
	{
		ResultSet joinedResultSet = DatabaseManager.getJoinedTable(TableProperties.STUDENTS_TABLE_NAME, TableProperties.ENROLLMENTS_TABLE_NAME, new String[] {TableProperties.STUDENTS_TABLE_NAME +  "." + TableProperties.STUDENT_ID, TableProperties.STUDENTS_TABLE_NAME + "." + TableProperties.FIRST_NAME, TableProperties.STUDENTS_TABLE_NAME + "." + TableProperties.LAST_NAME}, TableProperties.STUDENT_ID, TableProperties.STUDENT_ID, TableProperties.ENROLLMENTS_TABLE_NAME + "." + TableProperties.COURSE_ID, String.valueOf(id));
		Table table2 = new Table("test join table", joinedResultSet);
		
		DefaultTableModel model = new DefaultTableModel();
		
		jTable = new CustomJTable(table2, model);

		
		
		ArrayList<GradingScale> scales = GradeCalculator.getScales();
		String[] scaleNames = new String[scales.size()];
		for (int i = 0; i < scales.size(); i++)
		{
			scaleNames[i] = scales.get(i).getName();
		}
		scaleList = new JComboBox(scaleNames);
		scaleList.setMaximumSize((new Dimension( (int)this.getPreferredSize().getWidth(), 10)));
		
		scaleList.addItemListener(new ItemListener() {
	        public void itemStateChanged(ItemEvent arg0) {
	            updateGrades(table2, jTable, id);
	        }
	    });

	        model.addColumn("Percentage");
	        model.addColumn("Letter Grade");
	        model.addColumn("Points");
	        model.addColumn("Total Points");
	        
	        updateGrades(table2, jTable, id);
		jTable.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent me) {
		    	JTable table3 =(JTable) me.getSource();
		        Point p = me.getPoint();
		        int row = table3.rowAtPoint(p);
		        if (me.getClickCount() == 2)
		        {
		        	{	        	
///
		        	
		        	}
		        }
		    }
		});

		//jTable.getColumnModel().removeColumn(jTable.getColumnModel().getColumn(jTable.getColumnIndex(TableProperties.STUDENT_ID)));
		jTable.setMinimumSize(jTable.getPreferredSize());
		remove(tablePane);
		this.revalidate();
		add(backButton);
		add(printButton);
		add(scaleList);
		tablePane2  = new JScrollPane(jTable);
		add(tablePane2);
		this.repaint();	
		///SELECT * FROM Students JOIN Enrollments ON Students.studentId = Enrollments.studentId WHERE Enrollments.courseId = "1"	
		//SELECT * FROM Enrollments JOIN Students ON Enrollments.studentId = Students.StudentId WHERE Enrollments.courseId = "1'	
		}

	private void updateGrades(Table table2, CustomJTable jTable, int id)
	{
		
		ArrayList<Grade> grades = GradeCalculator.getStudentGrades(id);
		int index = table2.getColumnIndex(TableProperties.STUDENT_ID);
		System.out.println("here is the index" + index);
		for (int row = 0; row < jTable.getRowCount(); row++)
		{
			Grade grade = hasStudent(grades, Integer.parseInt(jTable.getValueAt(row, index).toString()));
			
			if (grade != null)
			{
				jTable.setValueAt(grade.getPercentage(), row, jTable.getColumnIndex(percentage));
				jTable.setValueAt(grade.getLetterGrade(GradeCalculator.getScales().get(scaleList.getSelectedIndex())), row, jTable.getColumnIndex(letterGrade));
				jTable.setValueAt(grade.getPoints(), row, jTable.getColumnIndex(points));
				jTable.setValueAt(grade.getTotalPoints(), row, jTable.getColumnIndex(totalPoints));
			}
		}
	}
	private void initPanel() 
	{
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(tablePane);
		this.setVisible(true);
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
