// Austin Patel
// 4/8/2017
// TableInterface.java

package visuals;

import database.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.print.PrinterException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;

public class TableInterface extends InterfacePanel {

    public static final int ROW_HEIGHT = 17;

    private JTable jTable;
    private JPanel bottomContainer, tableComponents;
    private JList<String> tableList;
    private Table table;
    private DatabaseTableModel databaseTableModel;

    public TableInterface() {
        initPanel();
        initBottomContainer();
        initBottomButtons();
        initTablePicker();
        initTable();
    }

    @Override
    public void onLayoutOpened() {
        for (Table curTable : TableManager.getAllTables())
            curTable.refresh();

        // Refreshes the current table
        databaseTableModel.refresh();
    }

    private void initPanel() {
        tableComponents = new JPanel();
        tableComponents.setLayout(new BorderLayout());
        add(tableComponents);
    }

    private void initBottomContainer() {
        bottomContainer = new JPanel();
        bottomContainer.setLayout(new GridLayout());
        add(bottomContainer);
    }

    public void addRow() {
        table.addRow();

        databaseTableModel.fireTableDataChanged();

        jTable.requestFocus();
        jTable.changeSelection(
                databaseTableModel.getRowCount() - 1, 1, false,
                false);
    }

    public void deleteRow() {
        ResultSet resultSet = table.getResultSet();

        int[] selectedRows = jTable.getSelectedRows();

        // Reverse the rows so they can be removed without shifting
        int[] selectedRowsReverse = new int[selectedRows.length];

        for (int i = selectedRows.length - 1; i >= 0; i--)
            selectedRowsReverse[selectedRows.length - 1 - i] = selectedRows[i];

        for (int row : selectedRowsReverse) {
            try {
                resultSet.absolute(row + 1);
                resultSet.deleteRow();

                if (databaseTableModel.getRowCount() > 0)
                    databaseTableModel.fireTableRowsDeleted(row,
                            row);
                else
                    databaseTableModel.fireTableDataChanged();
            } catch (SQLException e1) {
                System.out.println(
                        "Failed to delete row from database. bitch keke");
            }
        }

        if (databaseTableModel.getRowCount() == 0)
            tableList.requestFocus();
    }

    private void initBottomButtons() {
        JPanel buttonPanel = new JPanel();

        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        buttonPanel.add(new JButton() {
            {
                setText("Add Row");
                addActionListener(e -> addRow());
            }
        });

        buttonPanel.add(new JButton() {
            {
                setText("PrintTable");
                addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            MessageFormat header = new MessageFormat(
                                    jTable.getTableHeader()
                                            + " Page {0,number,integer}");
                            jTable.print(JTable.PrintMode.FIT_WIDTH, header,
                                    null);
                            JOptionPane.showMessageDialog(null,
                                    "Printing Succesful");

                        } catch (PrinterException e1) {
                            JOptionPane.showMessageDialog(null,
                                    "Unable To Print");
                            e1.printStackTrace();
                        }
                    }
                });
            }
        });

        buttonPanel.add(new JButton() {
            {
                setText("Delete Row(s)");
                addActionListener(e -> deleteRow());
            }
        });

        add(buttonPanel);
    }

    /**
     * Displays the table picker on the frame.
     */
    private void initTablePicker() {
        Table[] tables = TableManager.getAllTables();
        String[] tableNames = new String[tables.length];

        for (int i = 0; i < tables.length; i++)
            tableNames[i] = tables[i].getName();

        tableList = new JList<String>(tableNames) {
            {
                setLayoutOrientation(JList.HORIZONTAL_WRAP);
                setVisibleRowCount(tables.length);
                setBackground(bottomContainer.getBackground());
                setSelectedIndex(0);
                setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

                addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {

                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_RIGHT
                                || e.getKeyCode() == KeyEvent.VK_KP_RIGHT) {
                            if (databaseTableModel.getRowCount() > 0) {
                                jTable.requestFocus();
                                jTable.changeSelection(0, 1, false, false);
                            }
                        }
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {

                    }
                });

                addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        setTable(TableManager.getTable(getSelectedValue()));
                    }
                });
            }
        };

        tableComponents.add(tableList, BorderLayout.WEST);
    }

    /**
     * Initializes a JTable (and its container) and the table model.
     */
    private void initTable() {
        jTable = new JTable() {
            public TableCellEditor getCellEditor(int row, int column) {
                return new DatabaseCellEditor();
            }

            {
                setAutoCreateRowSorter(true);
                setRowHeight(ROW_HEIGHT);
                getTableHeader().setReorderingAllowed(false);

                addKeyListener(new KeyListener() {

                    @Override
                    public void keyTyped(KeyEvent e) {
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        if ((e.getKeyCode() == KeyEvent.VK_LEFT
                                || e.getKeyCode() == KeyEvent.VK_KP_LEFT)
                                && jTable.getSelectedColumn() == 1)
                            tableList.requestFocus();
                    }
                });
            }
        };

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(jTable.getTableHeader(), BorderLayout.NORTH);
        tablePanel.add(jTable, BorderLayout.CENTER);
        jTable.setBackground(getBackground());

        tableComponents.add(tablePanel, BorderLayout.CENTER);

        databaseTableModel = new DatabaseTableModel();

        setTable(TableManager.getTable(tableList.getSelectedValue()));
    }

    public void setTable(Table table) {
        this.table = table;

        databaseTableModel.setTable(table);
        databaseTableModel.fireTableStructureChanged();

        jTable.setModel(databaseTableModel);

        // Change the cell color to gray
        for (int i = 0; i < jTable.getColumnCount(); i++)
            jTable.getColumnModel().getColumn(i).setCellRenderer(new ColoredTableCellRenderer());

        // Hide the ID column
//        jTable.getColumnModel().getColumn(0).setMinWidth(0);
//        jTable.getColumnModel().getColumn(0).setMaxWidth(0);
//        jTable.getColumnModel().getColumn(0).setWidth(0);
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

