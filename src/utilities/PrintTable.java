package utilities;

import java.text.MessageFormat;

import javax.swing.JTable;

public class PrintTable
{
	public PrintTable(JTable table)
	{
   	MessageFormat header = new MessageFormat("Page {0,number,integer}");
   	
   	JTable table2 = new JTable();
   	table2 = table;
   	
   	try 
   	{
   	    table2.print(JTable.PrintMode.FIT_WIDTH, header, null);
   	} 
   	catch (java.awt.print.PrinterException e) 
   	{
   	    System.err.format("Cannot print %s%n", e.getMessage());
   	}
   }
}
