package visuals;

import grading.GradeCalculator;
import grading.GradingScale;
import grading.GradingScaleTableModel;
import grading.GradingScaleCellEditor;
import grading.GradingScaleCellEditor.EditorType;
import org.json.JSONArray;
import org.json.JSONException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GradingScaleInterface extends InterfacePanel implements TableModelListener {

    private final int rows = 13, cols = 5, rowHeight = 50;
    private JTable letterTable;
    private JSplitPane splitPane;
    private Border blackline, raisedetched, loweredetched, raisedbevel, loweredbevel, empty, compound;
    private int[] disabled_columns = {1, 2, 3};
    private String[] disabled_labels = {"Below", "", "down to"};
    private int cur_col = 0;
    private JLabel label1;
    private JScrollPane scrollPane1;
    private JPanel rightPanel, leftPanel, buttonPane;
    private JList scaleList;
    private DefaultListModel listModel;
    private ArrayList<GradingScale> scales = new ArrayList();
    private JButton newScaleButton, addRowButton, deleteScale;
    private GradingScale openScale;
    private boolean open = false;
    private GradingScaleTableModel tableModel;
    private static int minCols = 5;
    private static Object[][] template = {{"A+", 99.9}, {"A", 95}, {"A-", 90}, {"B+", 88}, {"B", 83},
            {"B-", 80}, {"C+", 78}, {"C", 72}, {"C-", 70}, {"D+", 68}, {"D", 63}, {"D-", 60},
            {"F", 0}};

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

    @Override
    public void onLayoutOpened() {

    }

    private void initScale() {
        scaleList.setSelectedIndex(0);
        if (!scaleList.isSelectionEmpty()) {
            clearTable();
            openScale();
        }
    }

    private void initButtons() {
        newScaleButton = new JButton("Create new scale");
        newScaleButton.setFont(new Font("Helvetica", Font.BOLD, 14));
        newScaleButton.setFocusable(false);
        newScaleButton.setVisible(true);
        newScaleButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter the name for the new scale:   ");
            if (name != null && !name.trim().equals("")) {

                Object[][] obj = {{"A+", 99.9}, {"A", 95}, {"A-", 90}, {"B+", 88}, {"B", 83},
                        {"B-", 80}, {"C+", 78}, {"C", 72}, {"C-", 70}, {"D+", 68}, {"D", 63},
                        {"D-", 60}, {"F", 0}};

                new GradingScale(name, obj);
                scales = GradeCalculator.getScales();
                updateList();

                scaleList.setSelectedIndex(scaleList.getLastVisibleIndex());
                clearTable();
                openScale();
            }
        });

        deleteScale = new JButton("Delete Scale");
        deleteScale.setFocusable(false);
        deleteScale.setVisible(true);
        deleteScale.addActionListener(e -> {
            String temp = "";

            try {
                if (scaleList.getSelectedValue().toString() != null)
                    temp = scaleList.getSelectedValue().toString();
            } catch (Exception eo) {

            }

            String name = JOptionPane.showInputDialog("Enter the name of the scale you wish to delete:   ", temp);
            if (name != null && !name.trim().equals("")) {
                GradeCalculator.deleteScale(name);
            }
            scales = GradeCalculator.getScales();
            updateList();
            scaleList.setSelectedIndex(0);
            clearTable();
            openScale();
        });

        addRowButton = new JButton("Add Row");
        addRowButton.addActionListener(e -> {
            try {
                if (letterTable.getRowCount() < rows) {
                    if (letterTable.getSelectedRow() != -1) {
                        open = false;

                        if (letterTable.getSelectedRow() == 0)
                            letterTable.changeSelection(1, letterTable.getSelectedColumn(), false, false);

                        tableModel.insertRow(letterTable.getSelectedRow(), new String[]{"::::", "0", "0", "0", letterTable.getValueAt(letterTable.getSelectedRow(), letterTable.getColumnCount() - 1).toString()});
                        open = true;
                        letterTable.setValueAt("0", letterTable.getRowCount() - 1, letterTable.getColumnCount() - 1);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout());
        buttonPane.add(newScaleButton);
        buttonPane.add(deleteScale);
        buttonPane.add(addRowButton);
    }

    private int getLetterGradeIndex(String grade) {

        for (int i = 0; i < template.length; i++)
            if (template[i][0].toString().equals(grade))
                return i;

        return -1;
    }

    private void initList() {
        listModel = new DefaultListModel();
        scaleList = new JList(listModel);
        scaleList.setBackground(getBackground());
        scales = GradeCalculator.getScales();
        scaleList.addKeyListener(this);

        for (GradingScale scale : scales)
            listModel.addElement(scale.getName());

        scaleList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
            if (evt.getClickCount() == 2) {
                clearTable();
                openScale();
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

        rightPanel.add(letterTable);

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
        letterTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        letterTable.setRowHeight(TableInterface.ROW_HEIGHT);
        letterTable.setRowHeight(TableInterface.ROW_HEIGHT);
        letterTable.setBorder(blackline);
        letterTable.setBackground(getBackground());
        letterTable.getTableHeader().setReorderingAllowed(false);
        letterTable.getColumnModel().getColumn(0).setCellEditor(new GradingScaleCellEditor(new JTextField(), letterTable, EditorType.LetterGrade));
        letterTable.getColumnModel().getColumn(letterTable.getColumnCount() - 1).setCellEditor(new GradingScaleCellEditor(new JTextField(), letterTable, EditorType.PercentGrade));

        for (int i = 0; i < letterTable.getColumnCount(); i++) {
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);

            letterTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            letterTable.setRowSelectionAllowed(false);

            final ListSelectionModel sel = letterTable.getColumnModel().getSelectionModel();
            sel.addListSelectionListener(e -> {
                // If the column is disabled, reselect previous column

                for (int i1 = 0; i1 < disabled_columns.length; i1++)
                    if (sel.isSelectedIndex(disabled_columns[i1])) {
                        cur_col += 2;
                        sel.setSelectionInterval(cur_col, cur_col);
                        return;
                    }

                // Set current selectionSQL
                cur_col = sel.getMaxSelectionIndex();
            });

            initValues();
        }
    }

    private void openScale() {
        letterTable.clearSelection();
        if (scaleList.isSelectionEmpty()) {
            clearTable();
            return;
        }

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

            }
        }
        letterTable.setValueAt("0", letterTable.getRowCount() - 1, letterTable.getColumnCount() - 1);
        open = true;
    }

    private void initValues() {
        letterTable.setValueAt("From", 0, disabled_columns[0]);
        letterTable.setValueAt(disabled_labels[2], 0, disabled_columns[2]);
        letterTable.setValueAt(Character.toString('\u221E'), 0, disabled_columns[1]);
        letterTable.setValueAt("0", letterTable.getRowCount() - 1, letterTable.getColumnCount() - 1);

        for (int row = 1; row < letterTable.getRowCount(); row++)
            for (int col = 0; col < letterTable.getColumnCount(); col++)
                if (!letterTable.isCellEditable(row, col))
                    letterTable.setValueAt(disabled_labels[getUneditableIndex(col)], row, col);
    }

    public int getUneditableIndex(int col) {
        if (!letterTable.isCellEditable(0, col))
            for (int i = 0; i < disabled_columns.length; i++)
                if (disabled_columns[i] == col)
                    return i;

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

                if (letterTable.getValueAt(i, 0) != null)
                    rowNum2++;
            }

            rowNum = Math.min(rowNum, rowNum2);
            Object[][] data = new Object[rowNum][2];

            for (int row = 0; row < rowNum; row++) {
                data[row][0] = letterTable.getValueAt(row, 0);
                data[row][1] = letterTable.getValueAt(row, letterTable.getColumnCount() - 1);
            }
            openScale.update(data);
            openScale();
        }
    }

    private void createRows(int row) {
        for (int i = 0; i < row; i++) {
            tableModel.addRow(new String[]{"", "", "", "", ""});
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
            if (!listModel.contains(scale.getName()))
                listModel.addElement(scale.getName());
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

    @Override
    public void keyPressed(KeyEvent arg0) {

    }

    @Override
    public void keyReleased(KeyEvent key) {
        if (key.getKeyCode() == KeyEvent.VK_CONTROL && open)
            deleteRow();
    }

    @Override
    public void keyTyped(KeyEvent arg0) {

    }
}