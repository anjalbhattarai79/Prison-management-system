package view;

import java.awt.*;
import javax.swing.*;
import controller.PrisonController;
import model.PrisonerModel;

/**
 * Custom cell editor for handling View, Edit and Delete button clicks in table
 * @author Anjal Bhattarai
 */
public class TableButtonEditor extends DefaultCellEditor {
    private JPanel panel;
    private JButton viewButton;
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
        
        // Create View button
        viewButton = new JButton("View");
        viewButton.setPreferredSize(new Dimension(70, 25));
        viewButton.setBackground(new Color(46, 213, 115)); // Green (#2ED573)
        viewButton.setForeground(Color.WHITE);
        viewButton.setFocusPainted(false);
        viewButton.setBorderPainted(false);
        
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
        
        // View button action
        viewButton.addActionListener(e -> {
            fireEditingStopped();
            int row = currentRow;
            SwingUtilities.invokeLater(() -> {
                try {
                    Object idValue = table.getValueAt(row, 0);
                    if (idValue == null) {
                        JOptionPane.showMessageDialog(parentFrame,
                            "Cannot view: Prisoner ID not found",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    int prisonerId = (int) idValue;
                    PrisonerModel prisoner = controller.getPrisonerById(prisonerId);
                    if (prisoner != null) {
                        new ViewDetailsDialog(parentFrame, prisoner, controller, table).setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(parentFrame,
                            "Prisoner not found in system",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(parentFrame,
                        "Error viewing prisoner: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            });
        });
        
        // Edit button action
        editButton.addActionListener(e -> {
            System.out.println("[DEBUG] Edit button clicked on row: " + currentRow);
            fireEditingStopped();
            int row = currentRow;
            SwingUtilities.invokeLater(() -> {
                try {
                    System.out.println("[DEBUG] Getting prisoner ID from row: " + row);
                    Object idValue = table.getValueAt(row, 0);
                    System.out.println("[DEBUG] ID Value: " + idValue);
                    if (idValue == null) {
                        System.err.println("[DEBUG] ID Value is null!");
                        JOptionPane.showMessageDialog(parentFrame,
                            "Cannot edit: Prisoner ID not found",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    int prisonerId = (int) idValue;
                    System.out.println("[DEBUG] Fetching prisoner with ID: " + prisonerId);
                    PrisonerModel prisoner = controller.getPrisonerById(prisonerId);
                    if (prisoner != null) {
                        System.out.println("[DEBUG] Prisoner found: " + prisoner.getName());
                        System.out.println("[DEBUG] Calling showEditDialog...");
                        PrisonerDialogHelper.showEditDialog(prisoner, controller, parentFrame, table);
                        System.out.println("[DEBUG] showEditDialog returned");
                    } else {
                        System.err.println("[DEBUG] Prisoner not found with ID: " + prisonerId);
                        JOptionPane.showMessageDialog(parentFrame,
                            "Prisoner not found in system",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    System.err.println("[DEBUG] Exception in edit button: " + ex.getMessage());
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
                        // Update recent activities
                        if (parentFrame instanceof MainFrame) {
                            ((MainFrame) parentFrame).updateRecentActivities();
                        }
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(parentFrame,
                        "Error deleting prisoner: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            });
        });
        
        panel.add(viewButton);
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
