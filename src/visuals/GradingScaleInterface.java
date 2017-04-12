package visuals;

import grading.GradeCalculator;
import grading.GradingScale;
import grading.GradingScaleTableModel;
import visuals.Tab;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInput;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.json.JSONArray;
import org.json.JSONException;

import customBorders.TextBubbleBorder;

public class GradingScaleInterface extends JPanel implements TableModelListener, Tab, KeyListener {

	private final int rows = 13, cols = 5, rowHeight = 50, colWidth = 30;
	private static final Font STANDARD_FONT = new Font("Calibri", Font.BOLD, 24);
	private JTable letterTable;
	private JSplitPane splitPane;
	private Border blackline, raisedetched, loweredetched, raisedbevel, loweredbevel, empty, compound;
	private int[] disabled_columns = { 1, 2, 3 };
	private String[] disabled_labels = { "Below", "", "down to" };
	private int disabled_col = 2, cur_col = 0;
	private JLabel label1;
	private JScrollPane scrollPane1;
	private JPanel rightPanel, leftPanel, buttonPane;
	private JList scaleList;
	private DefaultListModel listModel;
	private ArrayList<GradingScale> scales = new ArrayList();
	private JButton newScaleButton, saveAsButton, addRowButton, deleteScale;
	private GradingScale openScale;
	private boolean open = false;
	private int currentRows = rows;
	private GradingScaleTableModel tableModel;
	private static int minCols = 5;
	private static Object[][] template = { { "A+", 99.9 }, { "A", 95 }, { "A-", 90 }, { "B+", 88 }, { "B", 83 },
			{ "B-", 80 }, { "C+", 78 }, { "C", 72 }, { "C-", 70 }, { "D+", 68 }, { "D", 63 }, { "D-", 60 },
			{ "F", 0 } };

	public GradingScaleInterface() {

		initButtons();
		initBorders();
		initList();
		initLabels();
		initTable();
		initPane(); // make sure this is last line in constructor

		letterTable.addKeyListener(this);
		this.setMinimumSize(getPreferredSize());

		letterTable.setShowHorizontalLines(true);
		letterTable.setShowVerticalLines(true);
		initScale();
	}
	

	private void initScale()
	{
		scaleList.setSelectedIndex(0);
		if (!scaleList.isSelectionEmpty())
		{
			clearTable();
			openScale();
		}
	}
		
	
	private void initButtons() {
		newScaleButton = new JButton("Create new scale");
		newScaleButton.setFont(new Font("Helvetica", Font.BOLD, 14));
		// newScaleButton.setForeground(Color.BLUE);
		newScaleButton.setFocusable(false);
		newScaleButton.setVisible(true);
		newScaleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String name = JOptionPane.showInputDialog("Enter the name for the new scale:   ");
				if (name != null && !name.trim().equals("")) {

					Object[][] obj = { { "A+", 99.9 }, { "A", 95 }, { "A-", 90 }, { "B+", 88 }, { "B", 83 },
							{ "B-", 80 }, { "C+", 78 }, { "C", 72 }, { "C-", 70 }, { "D+", 68 }, { "D", 63 },
							{ "D-", 60 }, { "F", 0 } };

					new GradingScale(name, obj);
					scales = GradeCalculator.getScales();
					updateList();
					
					scaleList.setSelectedIndex(scaleList.getLastVisibleIndex());
					clearTable();
					openScale();
					
				}
			}
		});

		deleteScale = new JButton("Delete Scale");
		deleteScale.setFont(new Font("Helvetica", Font.BOLD, 14));
		// deleteScale.setForeground(Color.BLUE);
		deleteScale.setFocusable(false);
		deleteScale.setVisible(true);
		deleteScale.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String temp = "";
				
				try
				{
				if (scaleList.getSelectedValue().toString() != null)
					temp = scaleList.getSelectedValue().toString();
				}
				catch (Exception eo)
				{
					
				}
				
				String name = JOptionPane.showInputDialog("Enter the name of the scale you wish to delete:   ", temp);
				if (name != null && !name.trim().equals("")) {
					GradeCalculator.deleteScale(name);
				}
				scales = GradeCalculator.getScales();
				updateList();
			}
		});

		addRowButton = new JButton("Add Row");
		addRowButton.setFont(new Font("Helvetica", Font.BOLD, 14));
		addRowButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {

					if (letterTable.getRowCount() < rows) {
						{
						if (letterTable.getSelectedRow() != -1 && letterTable.getSelectedRow() != 0)
							tableModel.insertRow(letterTable.getSelectedRow(), new String[] { "0", "0", "0", "0", "0" });
					
						}

					}
						
				} catch (Exception ep) {

				}
			}

		});

		buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout());
		buttonPane.add(newScaleButton);
		buttonPane.add(deleteScale);
		buttonPane.add(addRowButton);
	}

	private int getLetterGradeIndex(String grade) {

		for (int i = 0; i < template.length; i++) {
			if (template[i][0].toString().equals(grade))
				return i;
		}
		return -1;
	}

	private Object[][] formatData(Object[][] data) {

		for (int i = 0; i < data.length; i++) {
			int index = getLetterGradeIndex(data[i][0].toString());
			if (index == -1) {
				if (i == 0) {
					data[i][0] = template[0][0];// A+
				} else {

					try {

						int pindex = getLetterGradeIndex(data[i - 1][0].toString());
						data[i][0] = template[pindex + 1][0];
					} catch (Exception e) {

					}

				}

			} else if (i != 0 && data[i][0].toString().equals(data[i - 1][0].toString())) {

				try {
					int pindex = getLetterGradeIndex(data[i - 1][0].toString());
					data[i][0] = template[pindex + 1][0];
				} catch (Exception e) {

				}
			} else if (i != 0 && i < data.length - 1) {

				int pastIndex = getLetterGradeIndex(data[i - 1][0].toString());
				try {
					int currIndex = getLetterGradeIndex(data[i][0].toString());

					if (currIndex <= pastIndex) {
						data[i][0] = template[pastIndex + 1][0];
					}
				} catch (Exception e) {
					data[i][0] = template[pastIndex][0];
					data[i - 1][0] = template[pastIndex - 1][0];
					 formatData(data);

				}
			}
		}

		while (data[data.length - 2][0].toString().equals("F")) {
			data = Arrays.copyOf(data, data.length - 1);
			
			
		}
		return formatNumbers(data);

	}
	
	
	private Object[][] formatNumbers(Object[][] data)
	{
		for (int i =0; i < data.length; i++)
		{
			
			try
			{
				double number = Double.parseDouble(data[i][1].toString());
				
				if (number <= 0 && i < data.length -1)
					data[i][1] = template[getLetterGradeIndex(data[i][0].toString())][1];
//				else if (number > 100)
//					data[i][1] = 100;


			}
			catch (Exception e)
			{
				data[i][1] = template[getLetterGradeIndex(data[i][0].toString())][1];
			}
		}
		
		for (int i =0; i < data.length; i++)
		{
			try
			{
				double number = Double.parseDouble(data[i][1].toString());
				
				if (i != 0 && number >= Double.parseDouble(data[i -1][1].toString()))
				{	
					data[i][1] = (Double.parseDouble(data[i -1][1].toString()) - 1);
					
					
					
					
				}
				
				if (Double.parseDouble(data[i][1].toString()) < 0)
				{
					while((Double.parseDouble(data[i][1].toString()) < 0))
					{
					for (int j = 0; j <= i; j++)
					{
						try
						{
							data[j][1]= Double.parseDouble(data[j][1].toString()) +1;
						}
						catch(Exception e)
						{
							
						}
					}
					}
				}

			}
			catch (Exception e)
			{
				data[i][1] = template[getLetterGradeIndex(data[i][0].toString())][1];
			}
		}
		return data;
	}
	
	private void initList() {
		listModel = new DefaultListModel();
		scaleList = new JList(listModel);
		scaleList.setBackground(getBackground());
		// scaleList.setBorder(compound);
		scales = GradeCalculator.getScales();
		scaleList.addKeyListener(this);
		scaleList.setFont(new Font("Helvetica", Font.BOLD, 15));

		for (GradingScale scale : scales) {
			listModel.addElement(scale.getName());
		}

		scaleList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JList list = (JList) evt.getSource();
				if (evt.getClickCount() == 2) {

					clearTable();
					openScale();

				} else if (evt.getClickCount() == 3) {

					int index = list.locationToIndex(evt.getPoint());
				}
			}
		});
	}

	private void initBorders() {
		blackline = BorderFactory.createLineBorder(Color.black);
		raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		raisedbevel = BorderFactory.createRaisedBevelBorder();
		loweredbevel = BorderFactory.createLoweredBevelBorder();
		empty = BorderFactory.createEmptyBorder();
		compound = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);

	}

	private void initLabels() {
		label1 = new JLabel("", JLabel.CENTER);
		label1.setFont(STANDARD_FONT);
		label1.setText("Scale Description");
		label1.setOpaque(true);
		label1.setVisible(true);

	}

	private void initPane() {
		setLayout(new BorderLayout());
		scrollPane1 = new JScrollPane(scaleList);

		leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
		leftPanel.add(label1);

		leftPanel.add(scrollPane1);

		rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
		buttonPane.setMaximumSize(buttonPane.getPreferredSize());
		rightPanel.add(buttonPane);

		JScrollPane tablePanel = new JScrollPane(letterTable);
		rightPanel.add(tablePanel);

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);

		this.addKeyListener(this);
		add(splitPane, BorderLayout.CENTER);

		splitPane.setResizeWeight(0.5);
		splitPane.setDividerSize(0);
		splitPane.setBorder(null);
		splitPane.setOneTouchExpandable(true);
	}

	private void initTable() {
		tableModel = new GradingScaleTableModel(rows, cols, disabled_columns);
		letterTable = new JTable(tableModel);
		tableModel.addTableModelListener(this);
		letterTable.setFont(new Font("Helvetica", Font.PLAIN, 20));
		letterTable.setRowHeight(rowHeight);
		letterTable.setBorder(compound);
		letterTable.setBackground(getBackground());
		letterTable.getTableHeader().setReorderingAllowed(false);

		for (int i = 0; i < letterTable.getColumnCount(); i++) {
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(JLabel.CENTER);
			letterTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
			letterTable.setRowSelectionAllowed(false);
			final ListSelectionModel sel = letterTable.getColumnModel().getSelectionModel();
			sel.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					// If the column is disabled, reselect previous column

					for (int i = 0; i < disabled_columns.length; i++) {

						if (sel.isSelectedIndex(disabled_columns[i])) {
							cur_col += 2;
							sel.setSelectionInterval(cur_col, cur_col);
							return;
						}
					}
					// Set current selection
					cur_col = sel.getMaxSelectionIndex();
				}
			});

			initValues();
			
		}
	}

	private void openScale() {
		scales = GradeCalculator.getScales();
		open = false;
		String name = (String) listModel.getElementAt(scaleList.getSelectedIndex());
		JSONArray data = null;
		for (GradingScale scale : scales) {
			if (scale.getName().equals(name)) {
				data = scale.getData();
				openScale = scale;
				break;
			}
		}

		clearTable();
		createRows(data.length());
		initValues();

		for (int row = 0; row < letterTable.getRowCount(); row++) {
			try {
				letterTable.setValueAt(data.getJSONObject(row).keys().next(), row, 0);
				letterTable.setValueAt(
						data.getJSONObject(row).getDouble(data.getJSONObject(row).keys().next().toString()), row, 4);
				letterTable.setValueAt(
						data.getJSONObject(row - 1).getDouble(data.getJSONObject(row - 1).keys().next().toString()),
						row, 2);

			} catch (JSONException e) {
				// TODO Auto-generated catch block

			}
		}
		letterTable.setValueAt("0", letterTable.getRowCount() - 1, letterTable.getColumnCount() - 1);
		open = true;
		// updateList();
	}

	private void initValues() {

		letterTable.setValueAt("From", 0, disabled_columns[0]);
		letterTable.setValueAt(disabled_labels[2], 0, disabled_columns[2]);
		letterTable.setValueAt(Character.toString('\u221E'), 0, disabled_columns[1]);
		letterTable.setValueAt("0", letterTable.getRowCount() - 1, letterTable.getColumnCount() - 1);

		for (int row = 1; row < letterTable.getRowCount(); row++) {
			for (int col = 0; col < letterTable.getColumnCount(); col++) {
				if (letterTable.isCellEditable(row, col) == false) {

					letterTable.setValueAt(disabled_labels[getUneditableIndex(col)], row, col);
				}
			}
		}
	}

	public int getUneditableIndex(int col) {

		if (letterTable.isCellEditable(0, col) == false) {

			for (int i = 0; i < disabled_columns.length; i++) {
				if (disabled_columns[i] == col)
					return i;
			}

		}
		return -1;
	}

	private boolean isDouble(String text) {

		try {
			Double.parseDouble(text);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void tableChanged(TableModelEvent arg0) {
		if (open) {

			int rowNum = 0;
			int rowNum2 = 0;
			for (int i = 0; i < letterTable.getRowCount(); i++) {

				if (letterTable.getValueAt(i, letterTable.getColumnCount() - 1) != null) {
					String text = letterTable.getValueAt(i, letterTable.getColumnCount() - 1).toString();
					if (isDouble(text))
						rowNum++;
				}
				if (letterTable.getValueAt(i, 0) != null) {
					rowNum2++;
				}
			}
			rowNum = Math.min(rowNum, rowNum2);

			Object[][] data = new Object[rowNum][2];
			for (int row = 0; row < rowNum; row++) {
				data[row][0] = letterTable.getValueAt(row, 0);
				data[row][1] = letterTable.getValueAt(row, letterTable.getColumnCount() - 1);
			}
			data = formatData(data);

			openScale.update(data);
			// System.out.println("Table was opened!!!!!!!!!");
			openScale();
		}
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

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	private void createRows(int row) {
		for (int i = 0; i < row; i++) {
			tableModel.addRow(new String[] { "", "", "", "", "" });
		}
	}

	public void clearTable() {
		open = false;
		DefaultTableModel dtm = (DefaultTableModel) letterTable.getModel();
		dtm.setRowCount(0);

	}

	private void updateList() {

		listModel.removeAllElements();
		for (GradingScale scale : scales) {
			{
				if (!listModel.contains(scale.getName()))
					listModel.addElement(scale.getName());
			}
		}
	}

	public void deleteRow() {

		if (letterTable.getSelectedRow() != -1 && letterTable.getRowCount() > minCols) {
			int selectedRow = letterTable.getSelectedRow();
			int selectedColumn = letterTable.getSelectedColumn();

			tableModel.removeRow(letterTable.getSelectedRow());

			if (selectedRow == 0)
				selectedRow = 1;

			letterTable.changeSelection(selectedRow - 1, selectedColumn, false, false);
		}
	}

	public void deleteRow(int index) {
		tableModel.removeRow(index);
	}

	@Override
	public void keyReleased(KeyEvent key) {
		// TODO Auto-generated method stub

		if (key.getKeyCode() == KeyEvent.VK_CONTROL && open) {

			deleteRow();
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}