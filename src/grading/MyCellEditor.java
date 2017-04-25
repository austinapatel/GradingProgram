package grading;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class MyCellEditor extends DefaultCellEditor 
{

	private static final long serialVersionUID = 1L;
	private static final Border red = new LineBorder(Color.red);
    private static final Border black = new LineBorder(Color.black);
    private JTextField textField;
    private EditorType type;
    private JTable table;
    private static Object[][] template = { { "A+", 99.9 }, { "A", 95 }, { "A-", 90 }, { "B+", 88 }, { "B", 83 },
			{ "B-", 80 }, { "C+", 78 }, { "C", 72 }, { "C-", 70 }, { "D+", 68 }, { "D", 63 }, { "D-", 60 },
			{ "F", 0 } };
    
    
    
    public enum EditorType
    {
    	LetterGrade,
    	PercentGrade;
    }
    

    public MyCellEditor(JTextField textField, JTable table, EditorType type) 
    {
        super(textField);
    	this.table = table;
    	this.type = type;
        this.textField = textField;
        this.textField.setHorizontalAlignment(JTextField.RIGHT);
    }
    
	private int getLetterGradeIndex(String grade) {

		for (int i = 0; i < template.length; i++) {
			if (template[i][0].toString().equals(grade))
				return i;
		}
		return -1;
	}

    

  
	
	
	
	@Override
    public boolean stopCellEditing() {	
    	try {
            
        	
        //textField.getText()
        	
        	
        	if (type == EditorType.LetterGrade)
        	{
                
        		int num = getLetterGradeIndex(textField.getText());
        		if (num == -1)
        		{
        		
        			throw new Exception();
        			
        		}
        	        				
        		if (table.getEditingRow() != 0 && num <= getLetterGradeIndex((table.getValueAt(table.getEditingRow() -1, table.getEditingColumn()).toString())) || table.getEditingRow() != table.getRowCount() && num >= getLetterGradeIndex((table.getValueAt(table.getEditingRow() + 1, table.getEditingColumn()).toString())) )		
        		{
        			throw new Exception();
        		}
            }
        	else if (type == EditorType.PercentGrade)
        	{
        		double num = Double.parseDouble(textField.getText());        		
        		if (table.getEditingRow() != 0 && num >= Double.parseDouble(table.getValueAt(table.getEditingRow() -1, table.getEditingColumn()).toString()) || table.getEditingRow() != table.getRowCount() -1 && num <= Double.parseDouble(table.getValueAt(table.getEditingRow() + 1, table.getEditingColumn()).toString()))
        		{
        			throw new Exception();
        		}
        	}
        } catch (Exception e) {
            textField.setBorder(red);
            return false;
        }
        return super.stopCellEditing();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table,
        Object value, boolean isSelected, int row, int column) {
        textField.setBorder(black);
        return super.getTableCellEditorComponent(
            table, value, isSelected, row, column);
    }
}