package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import controller.PrisonController;
import controller.SimpleStack;
import model.PrisonerModel;

/**
 * Dialog for viewing and managing trash bin contents in tabular format
 * Maintains Stack (LIFO) functionality for deleted prisoners
 * @author Anjal Bhattarai
 */
public class TrashBinDialog extends JDialog {
    private PrisonController controller;
    private JTable trashTable;
    private DefaultTableModel tableModel;
    private JLabel statusLabel;
    private JFrame parentFrame;
    private JTable mainTable;
    
    public TrashBinDialog(JFrame parent, PrisonController controller, JTable mainTable) {
        super(parent, "Trash Bin (Stack - LIFO)", true);
        this.parentFrame = parent;
        this.controller = controller;
        this.mainTable = mainTable;
        
        initComponents();
        loadTrashData();
        setLocationRelativeTo(parent);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setSize(900, 600);
        
        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(231, 76, 60)); // Red for trash
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel titleLabel = new JLabel("🗑️ Trash Bin - Deleted Prisoners");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        
        statusLabel = new JLabel();
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        statusLabel.setForeground(Color.WHITE);
        
        JLabel infoLabel = new JLabel("Stack (LIFO): Most recently deleted appears at top");
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        infoLabel.setForeground(Color.WHITE);
        
        JPanel headerTextPanel = new JPanel(new GridLayout(3, 1));
        headerTextPanel.setBackground(new Color(231, 76, 60));
        headerTextPanel.add(titleLabel);
        headerTextPanel.add(statusLabel);
        headerTextPanel.add(infoLabel);
        
        headerPanel.add(headerTextPanel, BorderLayout.CENTER);
        
        // Table Panel
        String[] columnNames = {"Position", "ID", "Name", "Age", "Gender", "Crime Type", 
                               "Admission Date", "Sentence (months)", "Location", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        
        trashTable = new JTable(tableModel);
        trashTable.setRowHeight(30);
        trashTable.setFont(new Font("Arial", Font.PLAIN, 12));
        trashTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        trashTable.getTableHeader().setBackground(new Color(52, 73, 94));
        trashTable.getTableHeader().setForeground(Color.WHITE);
        
        // Set column widths
        trashTable.getColumnModel().getColumn(0).setPreferredWidth(70);  // Position
        trashTable.getColumnModel().getColumn(1).setPreferredWidth(50);  // ID
        trashTable.getColumnModel().getColumn(2).setPreferredWidth(120); // Name
        trashTable.getColumnModel().getColumn(3).setPreferredWidth(50);  // Age
        trashTable.getColumnModel().getColumn(4).setPreferredWidth(70);  // Gender
        trashTable.getColumnModel().getColumn(5).setPreferredWidth(110); // Crime Type
        trashTable.getColumnModel().getColumn(6).setPreferredWidth(100); // Admission Date
        trashTable.getColumnModel().getColumn(7).setPreferredWidth(120); // Sentence
        trashTable.getColumnModel().getColumn(8).setPreferredWidth(100); // Location
        trashTable.getColumnModel().getColumn(9).setPreferredWidth(90);  // Status
        
        JScrollPane scrollPane = new JScrollPane(trashTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton restoreTopButton = new JButton("Restore Top (Pop)");
        restoreTopButton.setPreferredSize(new Dimension(180, 40));
        restoreTopButton.setBackground(new Color(46, 213, 115)); // Green
        restoreTopButton.setForeground(Color.WHITE);
        restoreTopButton.setFont(new Font("Arial", Font.BOLD, 13));
        restoreTopButton.setFocusPainted(false);
        restoreTopButton.setToolTipText("Restore the most recently deleted prisoner (Stack Pop)");
        restoreTopButton.addActionListener(e -> restoreTopPrisoner());
        
        JButton emptyTrashButton = new JButton("Empty All");
        emptyTrashButton.setPreferredSize(new Dimension(150, 40));
        emptyTrashButton.setBackground(new Color(231, 76, 60)); // Red
        emptyTrashButton.setForeground(Color.WHITE);
        emptyTrashButton.setFont(new Font("Arial", Font.BOLD, 13));
        emptyTrashButton.setFocusPainted(false);
        emptyTrashButton.setToolTipText("Permanently delete all prisoners from trash");
        emptyTrashButton.addActionListener(e -> emptyAllTrash());
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setPreferredSize(new Dimension(120, 40));
        refreshButton.setBackground(new Color(52, 152, 219)); // Blue
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFont(new Font("Arial", Font.BOLD, 13));
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e -> loadTrashData());
        
        JButton closeButton = new JButton("Close");
        closeButton.setPreferredSize(new Dimension(120, 40));
        closeButton.setBackground(new Color(149, 165, 166)); // Gray
        closeButton.setForeground(Color.WHITE);
        closeButton.setFont(new Font("Arial", Font.BOLD, 13));
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> dispose());
        
        buttonPanel.add(restoreTopButton);
        buttonPanel.add(emptyTrashButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);
        
        // Add panels to dialog
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Make background white
        getContentPane().setBackground(Color.WHITE);
    }
    
    /**
     * Load trash bin data into table
     * Displays from top of stack (most recent) to bottom
     */
    private void loadTrashData() {
        System.out.println("\n[TrashBinDialog] Loading trash data...");
        
        // Clear existing rows
        tableModel.setRowCount(0);
        
        SimpleStack trashBin = controller.getTrashBin();
        int trashSize = trashBin.size();
        
        statusLabel.setText("Prisoners in trash: " + trashSize);
        
        if (trashBin.isEmpty()) {
            System.out.println("[TrashBinDialog] Trash bin is empty");
            return;
        }
        
        // Convert Stack to array to display from top to bottom
        PrisonerModel[] trashArray = trashBin.toArray(new PrisonerModel[0]);
        
        // Display from top (most recent) to bottom (oldest)
        // Stack index: last element is top, first element is bottom
        for (int i = trashArray.length - 1; i >= 0; i--) {
            PrisonerModel prisoner = trashArray[i];
            int position = trashArray.length - i; // Position 1 is top
            
            String positionLabel = (position == 1) ? "1 (TOP)" : String.valueOf(position);
            
            Object[] row = {
                positionLabel,
                prisoner.getPrisonerId(),
                prisoner.getName(),
                prisoner.getAge(),
                prisoner.getGender(),
                prisoner.getCrimeType(),
                prisoner.getAdmissionDate(),
                prisoner.getSentenceDuration(),
                prisoner.getPrisonLocation(),
                prisoner.getStatus()
            };
            
            tableModel.addRow(row);
        }
        
        System.out.println("[TrashBinDialog] Loaded " + trashSize + " prisoners from trash");
    }
    
    /**
     * Restore top prisoner from trash (Stack Pop operation)
     */
    private void restoreTopPrisoner() {
        System.out.println("\n[TrashBinDialog] Restore top button clicked");
        
        if (controller.getTrashSize() == 0) {
            JOptionPane.showMessageDialog(this,
                "Trash bin is empty!\nNo prisoners to restore.",
                "Trash Empty",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Call controller's restore method which handles the pop operation
        PrisonerModel restored = controller.restorePrisoner();
        
        if (restored != null) {
            // Refresh trash table
            loadTrashData();
            
            // Refresh main table
            controller.loadPrisonerToTable(mainTable);
            PrisonerDialogHelper.setupTableButtons(mainTable, controller, parentFrame);
            
            // Update recent activities
            if (parentFrame instanceof MainFrame) {
                ((MainFrame) parentFrame).updateRecentActivities();
            }
            
            System.out.println("[TrashBinDialog] Prisoner restored successfully");
        }
    }
    
    /**
     * Empty entire trash bin (Clear operation)
     */
    private void emptyAllTrash() {
        System.out.println("\n[TrashBinDialog] Empty all button clicked");
        
        if (controller.getTrashSize() == 0) {
            JOptionPane.showMessageDialog(this,
                "Trash bin is already empty!",
                "Empty Trash",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Call controller's empty trash method
        controller.emptyTrash();
        
        // Refresh trash table
        loadTrashData();
        
        // Update recent activities
        if (parentFrame instanceof MainFrame) {
            ((MainFrame) parentFrame).updateRecentActivities();
        }
        
        System.out.println("[TrashBinDialog] Trash emptied");
    }
}
