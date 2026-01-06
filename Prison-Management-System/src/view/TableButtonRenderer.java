package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

/**
 * Custom cell renderer for displaying View, Edit and Delete buttons in table
 * @author Anjal Bhattarai
 */
public class TableButtonRenderer extends JPanel implements TableCellRenderer {
    private JButton viewButton;
    private JButton editButton;
    private JButton deleteButton;
    
    public TableButtonRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        
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
        
        add(viewButton);
        add(editButton);
        add(deleteButton);
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        
        // Change background if row is selected
        if (isSelected) {
            setBackground(table.getSelectionBackground());
        } else {
            setBackground(table.getBackground());
        }
        
        return this;
    }
}
