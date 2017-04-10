package grading;

import visuals.Tab;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.json.JSONArray;
import org.json.JSONException;

import customBorders.CurvedBorder;
import customBorders.RoundedCornerBorder;
import customBorders.TextBubbleBorder;

public class GradingScaleInterface extends JPanel implements TableModelListener, Tab, KeyListener {

    private final int rows = 13, cols = 5, rowHeight = 50, colWidth = 30;
    private static final Font STANDARD_FONT = new Font("Arial", Font.PLAIN, 20);
    private JTable letterTable;
    private JSplitPane splitPane;
    private Border blackline, raisedetched, loweredetched, raisedbevel, loweredbevel, empty;
    private int[] disabled_columns = {1, 2, 3};
    private String[] disabled_labels = {"Below", "", "down to"};
    private int disabled_col = 2, cur_col = 0;
    private JLabel label1;
    private JScrollPane scrollPane1;
    private JPanel rightPanel, panel1, panel2, bpanel1, bpanel2;
    private JList scaleList;
    private DefaultListModel listModel;
    private ArrayList<GradingScale> scales = new ArrayList();
    private JButton saveButton, saveAsButton, addRowButton;
    private GradingScale openScale;
    private boolean open = false;
    private int currentRows = rows;
    private GradingScaleTableModel tableModel;

    public GradingScaleInterface() {

        initButtons();
        initBorders();
        initList();
        initLabels();
        initTable();
        initPane(); //make sure this is last line in constructor
        letterTable.setBorder(new TextBubbleBorder(Color.GRAY, 2, 8));
        letterTable.addKeyListener(this);
    }

    private void initButtons() {
        saveButton = new JButton("Save");
        saveButton.setFont(new Font("Arial", Font.BOLD, 28));
        saveButton.setForeground(Color.BLUE);
        //saveButton.setSize(30, 30);

        saveButton.setFocusable(false); // Don't let the button be pressed via ENTER or SPACE
        saveButton.setVisible(true);


        saveAsButton = new JButton("Save As");
        saveAsButton.setFont(new Font("Arial", Font.BOLD, 28));
        saveAsButton.setForeground(Color.BLUE);
        //	saveAsButton.setSize(30, 30);

        saveAsButton.setFocusable(false); // Don't let the button be pressed via ENTER or SPACE
        saveAsButton.setVisible(true);

        addRowButton = new JButton("Add Row");
        addRowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.addRow(new String[] {"1", "2", "3", "4", "5"});
            }
        });
    }

    private void initList() {
        listModel = new DefaultListModel();
        scaleList = new JList(listModel);
        scaleList.setBackground(getBackground());
        scales = GradeCalculator.getScales();
        scaleList.addKeyListener(this);

        for (GradingScale scale : scales) {
            listModel.addElement(scale.getName());
        }


    }

    private void openScale() {

        scales = GradeCalculator.getScales();
        open = false;
        initValues();
        String name = (String) listModel.getElementAt(scaleList.getSelectedIndex());
        JSONArray data = null;
        for (GradingScale scale : scales) {
            if (scale.getName().equals(name)) {
                data = scale.getData();
                openScale = scale;
                break;
            }
        }

        for (int row = 0; row < letterTable.getRowCount(); row++) {
            try {
                letterTable.setValueAt(data.getJSONObject(row).keys().next(), row, 0);
                letterTable.setValueAt(data.getJSONObject(row).getDouble(data.getJSONObject(row).keys().next().toString()), row, 4);
                letterTable.setValueAt(data.getJSONObject(row - 1).getDouble(data.getJSONObject(row - 1).keys().next().toString()), row, 2);


            } catch (JSONException e) {
                // TODO Auto-generated catch block

            }

        }
        open = true;
    }

    private void initBorders() {
        blackline = BorderFactory.createLineBorder(Color.black);
        raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        raisedbevel = BorderFactory.createRaisedBevelBorder();
        loweredbevel = BorderFactory.createLoweredBevelBorder();
        empty = BorderFactory.createEmptyBorder();

    }

    private void initLabels() {
        label1 = new JLabel("", JLabel.CENTER);
        //label1.setBorder(BorderFactory.createCompoundBorder( new RoundedCornerBorder(), raisedbevel));
        //label1.setBorder(new RoundedCornerBorder());
        // label1.setBorder(new TextBubbleBorder(Color.DARK_GRAY, 1, 8));
        //label1.setLocation(10, 10);
        label1.setFont(STANDARD_FONT);
        label1.setText("Scale Description");
        label1.setOpaque(true);
        //label1.setBackground(Color.BLACK);
        //label1.setForeground(Color.WHITE);
        //label1.setSize(label1.getPreferredSize());
        label1.setVisible(true);


    }

    private void initPane() {
        setLayout(new BorderLayout());

        scrollPane1 = new JScrollPane(scaleList);
        scrollPane1.setColumnHeaderView(label1);

        rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        rightPanel.add(letterTable, BorderLayout.CENTER);
        rightPanel.add(saveButton);
        rightPanel.add(addRowButton);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane1, rightPanel);
        splitPane.setOneTouchExpandable(true);

        splitPane.setResizeWeight(0.5);
        splitPane.setDividerSize(0);
        splitPane.setBorder(null);
        this.addKeyListener(this);
        add(splitPane, BorderLayout.CENTER);

//		 GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
//		    Font[] fonts = e.getAllFonts(); // Get the fonts
//		    for (Font f : fonts) {
//		      System.out.println(f.getFontName());
//		    }
//
    }


    private void initTable() {
        tableModel = new GradingScaleTableModel(rows, cols, disabled_columns);
        letterTable = new JTable(tableModel);
        tableModel.addTableModelListener(this);
        letterTable.setRowHeight(rowHeight);
        letterTable.setBackground(getBackground());
        letterTable.setFont(new Font("Arial", Font.PLAIN, 18));
        letterTable.getTableHeader().setReorderingAllowed(false);


        for (int i = 0; i < letterTable.getColumnCount(); i++) {
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            letterTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);


//            if (getUneditableIndex(i) == -1)
//                letterTable.getColumnModel().getColumn(i).setMaxWidth(50);
//            else
//            	letterTable.getColumnModel().getColumn(i).setMaxWidth(100);
        }


        //letterTable.setSelectionModel(new CustomModel(letterTable.getSelectionModel()));


        final ListSelectionModel sel = letterTable.getColumnModel().getSelectionModel();
        sel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //If the column is disabled, reselect previous column

                for (int i = 0; i < disabled_columns.length; i++) {

                    if (sel.isSelectedIndex(disabled_columns[i])) {
                        cur_col += 2;
                        sel.setSelectionInterval(cur_col, cur_col);
                        return;
                    }
                }
                //Set current selection
                cur_col = sel.getMaxSelectionIndex();
            }
        });


        initValues();


    }


    private void initValues() {
        letterTable.setValueAt("From", 0, disabled_columns[0]);
        letterTable.setValueAt(disabled_labels[2], 0, disabled_columns[2]);
        letterTable.setValueAt(Character.toString('\u221E'), 0, disabled_columns[1]);
        letterTable.setValueAt(Character.toString('\u2212') + Character.toString('\u221E'), letterTable.getRowCount() - 1, letterTable.getColumnCount() - 1);

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

    @Override
    public void tableChanged(TableModelEvent arg0) {


        if (open) {
            System.out.println("change");
            Object[][] data = new Object[13][2];
            for (int row = 0; row < letterTable.getRowCount(); row++) {
                data[row][0] = letterTable.getValueAt(row, 0);
                data[row][1] = letterTable.getValueAt(row, letterTable.getColumnCount() - 1);
            }
            openScale.update(data);
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

    @Override
    public void keyReleased(KeyEvent key) {
        // TODO Auto-generated method stub
        if (key.getKeyCode() == KeyEvent.VK_ENTER && scales.size() > 0) {
            {
                openScale();

            }

        }
        if (key.getKeyCode() == KeyEvent.VK_CONTROL) {
            tableModel.removeRow(letterTable.getSelectedRow());
            currentRows--;
        }


    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }
}