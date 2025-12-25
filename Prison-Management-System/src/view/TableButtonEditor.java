package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import controller.PrisonController;
import model.PrisonerModel;

/**
 * Custom cell editor for handling Edit and Delete button clicks in table
 * @author Anjal Bhattarai
 */
public class TableButtonEditor extends DefaultCellEditor {
    private JPanel panel;
    private JButton editButton;
    private JButton deleteButton;
    private int currentRow;
    private JTable table;
    private PrisonController controller;
    private JFrame parentFrame;
    
    public TableButtonEditor(JCheckBox checkBox, JTable table, 
                            PrisonController controller, JFrame parentFrame) {
        super(checkBox);
        this.table = table;
        this.controller = controller;
        this.parentFrame = parentFrame;
        
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        
        // Create Edit button
        editButton = new JButton("Edit");
        editButton.setPreferredSize(new Dimension(70, 25));
        editButton.setBackground(new Color(52, 152, 219)); // Blue
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        editButton.setBorderPainted(false);
        
        // Create Delete button
        deleteButton = new JButton("Delete");
        deleteButton.setPreferredSize(new Dimension(70, 25));
        deleteButton.setBackground(new Color(231, 76, 60)); // Red
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.setBorderPainted(false);
        
        // Edit button action
        editButton.addActionListener(e -> {
            fireEditingStopped();
            int row = currentRow;
            SwingUtilities.invokeLater(() -> {
                try {
                    Object idValue = table.getValueAt(row, 0);
                    if (idValue == null) {
                        JOptionPane.showMessageDialog(parentFrame,
                            "Cannot edit: Prisoner ID not found",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    int prisonerId = (int) idValue;
                    PrisonerModel prisoner = controller.getPrisonerById(prisonerId);
                    if (prisoner != null) {
                        PrisonerDialogHelper.showEditDialog(prisoner, controller, parentFrame, table);
                    } else {
                        JOptionPane.showMessageDialog(parentFrame,
                            "Prisoner not found in system",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(parentFrame,
                        "Error editing prisoner: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            });
        });
        
        // Delete button action
        deleteButton.addActionListener(e -> {
            fireEditingStopped();
            int row = currentRow;
            SwingUtilities.invokeLater(() -> {
                try {
                    Object idValue = table.getValueAt(row, 0);
                    if (idValue == null) {
                        JOptionPane.showMessageDialog(parentFrame,
                            "Cannot delete: Prisoner ID not found",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    int prisonerId = (int) idValue;
                    if (controller.deletePrisoner(prisonerId)) {
                        controller.loadPrisonerToTable(table);
                        setupTableButtons(); // Re-setup buttons after refresh
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(parentFrame,
                        "Error deleting prisoner: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            });
        });
        
        panel.add(editButton);
        panel.add(deleteButton);
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        currentRow = row;
        return panel;
    }
    
    @Override
    public Object getCellEditorValue() {
        return "";
    }
    
    @Override
    public boolean stopCellEditing() {
        return super.stopCellEditing();
    }
    
    /**
     * Re-setup table buttons after data refresh
     */
    private void setupTableButtons() {
        table.getColumnModel().getColumn(10).setCellRenderer(new TableButtonRenderer());
        table.getColumnModel().getColumn(10).setCellEditor(
            new TableButtonEditor(new JCheckBox(), table, controller, parentFrame));
    }
}
