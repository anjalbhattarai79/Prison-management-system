package view;

import controller.PrisonController;
import model.VisitRequest;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

/**
 * VisitRequestsDialog - Manage family visit requests
 * Admin can approve or decline visit requests
 * @author Anjal Bhattarai
 */
public class VisitRequestsDialog extends JDialog {
    
    private PrisonController controller;
    private JTable requestsTable;
    private DefaultTableModel tableModel;
    private JLabel statusLabel;
    
    // Color scheme matching MainFrame
    private static final Color PRIMARY_COLOR = new Color(41, 98, 255);
    private static final Color ACCENT_COLOR = new Color(46, 213, 115);
    private static final Color DANGER_COLOR = new Color(255, 71, 87);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Color CARD_COLOR = Color.WHITE;
    
    public VisitRequestsDialog(Frame parent, PrisonController controller) {
        super(parent, "Visit Requests Management", true);
        this.controller = controller;
        
        initComponents();
        loadVisitRequests();
        
        setSize(1200, 600);
        setLocationRelativeTo(parent);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(BACKGROUND_COLOR);
        
        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(getWidth(), 60));
        
        JLabel titleLabel = new JLabel("Family Visit Requests Management");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Main Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Status Panel
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBackground(CARD_COLOR);
        statusPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        statusLabel = new JLabel("Loading requests...");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        statusPanel.add(statusLabel);
        
        contentPanel.add(statusPanel, BorderLayout.NORTH);
        
        // Table Panel
        String[] columnNames = {
            "Request ID", "Prisoner ID", "Prisoner Name", "Visitor Name",
            "Relationship", "Preferred Date", "Purpose", "Status", 
            "Request Date", "Actions"
        };
        
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 9; // Only actions column is editable
            }
        };
        
        requestsTable = new JTable(tableModel);
        requestsTable.setRowHeight(35);
        requestsTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        requestsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        requestsTable.getTableHeader().setBackground(PRIMARY_COLOR);
        requestsTable.getTableHeader().setForeground(Color.WHITE);
        requestsTable.setSelectionBackground(new Color(173, 216, 230));
        
        // Set column widths
        requestsTable.getColumnModel().getColumn(0).setPreferredWidth(80);  // Request ID
        requestsTable.getColumnModel().getColumn(1).setPreferredWidth(80);  // Prisoner ID
        requestsTable.getColumnModel().getColumn(2).setPreferredWidth(120); // Prisoner Name
        requestsTable.getColumnModel().getColumn(3).setPreferredWidth(120); // Visitor Name
        requestsTable.getColumnModel().getColumn(4).setPreferredWidth(100); // Relationship
        requestsTable.getColumnModel().getColumn(5).setPreferredWidth(100); // Preferred Date
        requestsTable.getColumnModel().getColumn(6).setPreferredWidth(150); // Purpose
        requestsTable.getColumnModel().getColumn(7).setPreferredWidth(80);  // Status
        requestsTable.getColumnModel().getColumn(8).setPreferredWidth(120); // Request Date
        requestsTable.getColumnModel().getColumn(9).setPreferredWidth(150); // Actions
        
        // Custom renderer for Status column
        requestsTable.getColumnModel().getColumn(7).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String status = value.toString();
                setHorizontalAlignment(CENTER);
                setFont(new Font("Segoe UI", Font.BOLD, 11));
                
                if ("Approved".equals(status)) {
                    c.setForeground(ACCENT_COLOR);
                } else if ("Declined".equals(status)) {
                    c.setForeground(DANGER_COLOR);
                } else {
                    c.setForeground(new Color(255, 165, 2)); // Orange for Pending
                }
                
                return c;
            }
        });
        
        // Setup action buttons in table
        setupActionButtons();
        
        JScrollPane scrollPane = new JScrollPane(requestsTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 1));
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(contentPanel, BorderLayout.CENTER);
        
        // Bottom Panel with buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton refreshButton = createStyledButton("Refresh", PRIMARY_COLOR);
        refreshButton.addActionListener(e -> loadVisitRequests());
        
        JButton closeButton = createStyledButton("Close", new Color(108, 117, 125));
        closeButton.addActionListener(e -> dispose());
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void setupActionButtons() {
        requestsTable.getColumnModel().getColumn(9).setCellRenderer(new ButtonRenderer());
        requestsTable.getColumnModel().getColumn(9).setCellEditor(
            new ButtonEditor(new JCheckBox(), controller, this)
        );
    }
    
    private void loadVisitRequests() {
        tableModel.setRowCount(0);
        LinkedList<VisitRequest> requests = controller.getAllVisitRequests();
        
        for (VisitRequest request : requests) {
            Object[] row = {
                request.getRequestId(),
                request.getPrisonerId(),
                request.getPrisonerName(),
                request.getVisitorName(),
                request.getRelationship(),
                request.getFormattedPreferredDate(),
                request.getPurpose(),
                request.getStatus(),
                request.getFormattedRequestDateTime(),
                "Actions"
            };
            tableModel.addRow(row);
        }
        
        int total = requests.size();
        int pending = controller.getPendingVisitRequestsCount();
        statusLabel.setText(String.format(
            "Total Requests: %d  |  Pending: %d  |  Processed: %d",
            total, pending, total - pending
        ));
    }
    
    public void refreshTable() {
        loadVisitRequests();
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(120, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
    
    // Button Renderer for Actions column
    class ButtonRenderer extends JPanel implements TableCellRenderer {
        private JButton approveButton;
        private JButton declineButton;
        
        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 2));
            setOpaque(true);
            
            approveButton = new JButton("Approve");
            approveButton.setFont(new Font("Segoe UI", Font.BOLD, 10));
            approveButton.setBackground(ACCENT_COLOR);
            approveButton.setForeground(Color.WHITE);
            approveButton.setFocusPainted(false);
            approveButton.setBorderPainted(false);
            approveButton.setPreferredSize(new Dimension(70, 25));
            
            declineButton = new JButton("Decline");
            declineButton.setFont(new Font("Segoe UI", Font.BOLD, 10));
            declineButton.setBackground(DANGER_COLOR);
            declineButton.setForeground(Color.WHITE);
            declineButton.setFocusPainted(false);
            declineButton.setBorderPainted(false);
            declineButton.setPreferredSize(new Dimension(70, 25));
            
            add(approveButton);
            add(declineButton);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            String status = table.getValueAt(row, 7).toString();
            
            if ("Pending".equals(status)) {
                approveButton.setEnabled(true);
                declineButton.setEnabled(true);
            } else {
                approveButton.setEnabled(false);
                declineButton.setEnabled(false);
            }
            
            return this;
        }
    }
    
    // Button Editor for Actions column
    class ButtonEditor extends DefaultCellEditor {
        private JPanel panel;
        private JButton approveButton;
        private JButton declineButton;
        private int currentRow;
        private PrisonController controller;
        private VisitRequestsDialog parentDialog;
        
        public ButtonEditor(JCheckBox checkBox, PrisonController controller, VisitRequestsDialog parentDialog) {
            super(checkBox);
            this.controller = controller;
            this.parentDialog = parentDialog;
            
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
            
            approveButton = new JButton("Approve");
            approveButton.setFont(new Font("Segoe UI", Font.BOLD, 10));
            approveButton.setBackground(ACCENT_COLOR);
            approveButton.setForeground(Color.WHITE);
            approveButton.setFocusPainted(false);
            approveButton.setBorderPainted(false);
            approveButton.setPreferredSize(new Dimension(70, 25));
            
            declineButton = new JButton("Decline");
            declineButton.setFont(new Font("Segoe UI", Font.BOLD, 10));
            declineButton.setBackground(DANGER_COLOR);
            declineButton.setForeground(Color.WHITE);
            declineButton.setFocusPainted(false);
            declineButton.setBorderPainted(false);
            declineButton.setPreferredSize(new Dimension(70, 25));
            
            approveButton.addActionListener(e -> {
                handleApprove();
                fireEditingStopped();
            });
            
            declineButton.addActionListener(e -> {
                handleDecline();
                fireEditingStopped();
            });
            
            panel.add(approveButton);
            panel.add(declineButton);
        }
        
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            currentRow = row;
            
            String status = table.getValueAt(row, 7).toString();
            approveButton.setEnabled("Pending".equals(status));
            declineButton.setEnabled("Pending".equals(status));
            
            return panel;
        }
        
        @Override
        public Object getCellEditorValue() {
            return "Actions";
        }
        
        private void handleApprove() {
            int requestId = (int) requestsTable.getValueAt(currentRow, 0);
            String visitorName = requestsTable.getValueAt(currentRow, 3).toString();
            String prisonerName = requestsTable.getValueAt(currentRow, 2).toString();
            
            String notes = JOptionPane.showInputDialog(
                parentDialog,
                "Approving visit request for " + visitorName + " to visit " + prisonerName + "\n\n" +
                "Enter any notes or instructions (optional):",
                "Approve Visit Request",
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (notes != null) { // User clicked OK (even if empty)
                if (notes.trim().isEmpty()) {
                    notes = "Approved by administrator";
                }
                
                controller.updateVisitRequestStatus(requestId, "Approved", notes);
                
                JOptionPane.showMessageDialog(
                    parentDialog,
                    "Visit request #" + requestId + " has been approved!\n" +
                    "The family will be notified.",
                    "Request Approved",
                    JOptionPane.INFORMATION_MESSAGE
                );
                
                parentDialog.refreshTable();
            }
        }
        
        private void handleDecline() {
            int requestId = (int) requestsTable.getValueAt(currentRow, 0);
            String visitorName = requestsTable.getValueAt(currentRow, 3).toString();
            String prisonerName = requestsTable.getValueAt(currentRow, 2).toString();
            
            String reason = JOptionPane.showInputDialog(
                parentDialog,
                "Declining visit request for " + visitorName + " to visit " + prisonerName + "\n\n" +
                "Please enter the reason for declining this request:",
                "Decline Visit Request",
                JOptionPane.WARNING_MESSAGE
            );
            
            if (reason != null && !reason.trim().isEmpty()) {
                controller.updateVisitRequestStatus(requestId, "Declined", reason);
                
                JOptionPane.showMessageDialog(
                    parentDialog,
                    "Visit request #" + requestId + " has been declined.\n" +
                    "Reason: " + reason,
                    "Request Declined",
                    JOptionPane.INFORMATION_MESSAGE
                );
                
                parentDialog.refreshTable();
            } else if (reason != null) {
                JOptionPane.showMessageDialog(
                    parentDialog,
                    "Please provide a reason for declining the request.",
                    "Reason Required",
                    JOptionPane.WARNING_MESSAGE
                );
            }
        }
    }
}
