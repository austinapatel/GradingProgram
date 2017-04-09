// Austin Patel
// 4/8/2017
// TableInterface.java

package visuals;

import database.DatabaseCellEditor;
import database.DatabaseTableModel;
import database.Table;
import database.TableManager;

import javax.swing.*;
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

public class TableInterface extends JPanel implements Tab {

    private JTable jTable;
    private JPanel bottomContainer;
    private JButton addRowButton, deleteRowButton, printButton, selectButton;
    private JList<String> tableList;
    private Table table;
    private JScrollPane tableScrollPane;
    private DatabaseTableModel databaseTableModel;
    private int callingTableIndex;

    // Locked means that the TableInterface is being used as a selector for a
    // table row
    private boolean isLocked;

    public TableInterface() {
        initPanel();
        initBottomContainer();
        initBottomButtons();
        initTablePicker();
        initTable();
    }

    private void initPanel() {
        setLayout(new BorderLayout());
    }

    private void initBottomContainer() {
        bottomContainer = new JPanel();
        bottomContainer.setLayout(new GridLayout());
        add(bottomContainer, BorderLayout.SOUTH);
    }

    private void initBottomButtons() {
        addRowButton = new JButton() {
            {
                setText("Add Row");
                addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        table.addRow();

                        databaseTableModel.fireTableDataChanged();

                        jTable.requestFocus();
                        jTable.changeSelection(
                                databaseTableModel.getRowCount() - 1, 1, false,
                                false);
                    }
                });
            }
        };

        printButton = new JButton() {
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
        };

        deleteRowButton = new JButton() {
            {
                setText("Delete Row(s)");
                addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ResultSet resultSet = table.getResultSet();

                        int[] selectedRows = jTable.getSelectedRows();
                        int[] selectedRowsReverse = new int[selectedRows.length];

                        for (int i = selectedRows.length - 1; i >= 0; i--)
                            selectedRowsReverse[selectedRows.length - 1
                                    - i] = selectedRows[i];

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
                                        "Failed to delete row from database.");
                            }
                        }

                        if (databaseTableModel.getRowCount() == 0)
                            tableList.requestFocus();
                    }
                });
            }
        };

        selectButton = new JButton("Select Row");
        selectButton.setEnabled(false);

        bottomContainer.add(addRowButton);
        bottomContainer.add(deleteRowButton);
        bottomContainer.add(printButton);
        bottomContainer.add(selectButton);
    }

    /** Displays the table picker on the frame. */
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

        add(tableList, BorderLayout.WEST);
    }

    // Locks the TableInterface into a specific table so that it can be used as
    // a selector
    public void lockInto(int tableIndexLock, int rowToChange,
                         int columnToChange) {
        this.callingTableIndex = tableList.getSelectedIndex();
        isLocked = true;

        tableList.setSelectedIndex(tableIndexLock);

         selectButton = new JButton("Select Row");
		selectButton.setEnabled(true);
		selectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				unlock(jTable.getSelectedRow(), rowToChange, columnToChange);
				jTable.requestFocus();
				jTable.changeSelection(0, 1, false, false);
			}
		});

         bottomContainer.remove(printButton);
         bottomContainer.add(selectButton);

         remove(bottomContainer);

        tableList.setEnabled(false);

        jTable.requestFocus();
        jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable.changeSelection(0, 1, false, false);
    }

    /** Unlocks a table after it was locked into picking a row. */
    private void unlock(int selectedRow, int rowToChange, int columnToChange) {
        // Reset the TableInterface to how it was
        isLocked = false;

        int idValue = Integer.class
                .cast(databaseTableModel.getValueAt(selectedRow, 0)).intValue();

        tableList.setEnabled(true);
        tableList.requestFocus();
        // tableList.setSelectedIndex(callingTableIndex);
        tableList.setSelectedIndex(callingTableIndex);
        setTable(TableManager.getAllTables()[callingTableIndex]);

		selectButton.setEnabled(false);

        // bottomContainer.removeAll();
        // initBottomButtons();

        databaseTableModel.setValueAt(idValue, rowToChange, columnToChange);

        jTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }

    /** Initializes a JTable (and its container) and the table model. */
    private void initTable() {
        TableInterface thisTableInterface = this;
        jTable = new JTable() {

            public TableCellEditor getCellEditor(int row, int column) {
                return new DatabaseCellEditor(thisTableInterface);
            }

            {
                setAutoCreateRowSorter(true);
                setRowHeight(17);
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
                                && jTable.getSelectedColumn() == 1 && !isLocked)
                            tableList.requestFocus();
                    }
                });
            }
        };

        tableScrollPane = new JScrollPane(jTable);
        add(tableScrollPane, BorderLayout.CENTER);

        databaseTableModel = new DatabaseTableModel(this);

        setTable(TableManager.getTable(tableList.getSelectedValue()));
    }

    public void setTable(Table table) {
        this.table = table;

        databaseTableModel.setTable(table);
        databaseTableModel.fireTableStructureChanged();

        jTable.setModel(databaseTableModel);

        // Hide the ID column
//        jTable.getColumnModel().getColumn(0).setMinWidth(0);
//        jTable.getColumnModel().getColumn(0).setMaxWidth(0);
//        jTable.getColumnModel().getColumn(0).setWidth(0);
    }

    public boolean isLocked() {
        return isLocked;
    }

    @Override
    public String getTabName() {
        return "Table";
    }

    @Override
    public String getTabImage() {
        return "table.png";
    }

    @Override
    public void onTabSelected() {
        databaseTableModel.fireTableDataChanged();
    }
}

