import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PrisonManagementSystem extends JFrame {
    private JTabbedPane tabbedPane;
    private DefaultTableModel prisonerModel, staffModel, cellModel;
    private JTable prisonerTable, staffTable, cellTable;
    
    public PrisonManagementSystem() {
        setTitle("Prison Management System");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        
        // Add tabs
        tabbedPane.addTab("Dashboard", createDashboardPanel());
        tabbedPane.addTab("Prisoners", createPrisonerPanel());
        tabbedPane.addTab("Staff", createStaffPanel());
        tabbedPane.addTab("Cells", createCellPanel());
        
        add(tabbedPane);
    }
    
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel titleLabel = new JLabel("Prison Management Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Statistics panel
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        statsPanel.add(createStatCard("Total Prisoners", "0", new Color(52, 152, 219)));
        statsPanel.add(createStatCard("Total Staff", "0", new Color(46, 204, 113)));
        statsPanel.add(createStatCard("Occupied Cells", "0", new Color(231, 76, 60)));
        statsPanel.add(createStatCard("Available Cells", "0", new Color(241, 196, 15)));
        
        panel.add(statsPanel, BorderLayout.CENTER);
        
        // Date and time
        JLabel dateLabel = new JLabel("System Date: " + new SimpleDateFormat("EEEE, MMMM dd, yyyy").format(new Date()));
        dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(dateLabel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(color);
        card.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        
        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 36));
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createPrisonerPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Top panel with title and buttons
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Prisoner Management");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(title, BorderLayout.WEST);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addBtn = new JButton("Add Prisoner");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");
        JButton searchBtn = new JButton("Search");
        
        addBtn.addActionListener(e -> showAddPrisonerDialog());
        deleteBtn.addActionListener(e -> deleteSelectedRow(prisonerTable, prisonerModel));
        
        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(searchBtn);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        
        panel.add(topPanel, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"ID", "Name", "Age", "Gender", "Crime", "Sentence", "Cell No", "Admission Date", "Release Date"};
        prisonerModel = new DefaultTableModel(columns, 0);
        prisonerTable = new JTable(prisonerModel);
        JScrollPane scrollPane = new JScrollPane(prisonerTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createStaffPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Staff Management");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(title, BorderLayout.WEST);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addBtn = new JButton("Add Staff");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");
        
        addBtn.addActionListener(e -> showAddStaffDialog());
        deleteBtn.addActionListener(e -> deleteSelectedRow(staffTable, staffModel));
        
        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        
        panel.add(topPanel, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"ID", "Name", "Position", "Shift", "Phone", "Email", "Hire Date"};
        staffModel = new DefaultTableModel(columns, 0);
        staffTable = new JTable(staffModel);
        JScrollPane scrollPane = new JScrollPane(staffTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createCellPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Cell Management");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(title, BorderLayout.WEST);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addBtn = new JButton("Add Cell");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");
        
        addBtn.addActionListener(e -> showAddCellDialog());
        deleteBtn.addActionListener(e -> deleteSelectedRow(cellTable, cellModel));
        
        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        
        panel.add(topPanel, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"Cell No", "Block", "Capacity", "Current Occupancy", "Status", "Security Level"};
        cellModel = new DefaultTableModel(columns, 0);
        cellTable = new JTable(cellModel);
        JScrollPane scrollPane = new JScrollPane(cellTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void showAddPrisonerDialog() {
        JDialog dialog = new JDialog(this, "Add Prisoner", true);
        dialog.setSize(400, 500);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridLayout(10, 2, 5, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JComboBox<String> genderBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        JTextField crimeField = new JTextField();
        JTextField sentenceField = new JTextField();
        JTextField cellField = new JTextField();
        JTextField admissionField = new JTextField();
        JTextField releaseField = new JTextField();
        
        panel.add(new JLabel("Prisoner ID:"));
        panel.add(idField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Age:"));
        panel.add(ageField);
        panel.add(new JLabel("Gender:"));
        panel.add(genderBox);
        panel.add(new JLabel("Crime:"));
        panel.add(crimeField);
        panel.add(new JLabel("Sentence (years):"));
        panel.add(sentenceField);
        panel.add(new JLabel("Cell No:"));
        panel.add(cellField);
        panel.add(new JLabel("Admission Date:"));
        panel.add(admissionField);
        panel.add(new JLabel("Release Date:"));
        panel.add(releaseField);
        
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");
        
        saveBtn.addActionListener(e -> {
            Object[] row = {
                idField.getText(),
                nameField.getText(),
                ageField.getText(),
                genderBox.getSelectedItem(),
                crimeField.getText(),
                sentenceField.getText(),
                cellField.getText(),
                admissionField.getText(),
                releaseField.getText()
            };
            prisonerModel.addRow(row);
            dialog.dispose();
        });
        
        cancelBtn.addActionListener(e -> dialog.dispose());
        
        panel.add(saveBtn);
        panel.add(cancelBtn);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private void showAddStaffDialog() {
        JDialog dialog = new JDialog(this, "Add Staff", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridLayout(8, 2, 5, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JComboBox<String> positionBox = new JComboBox<>(new String[]{"Guard", "Warden", "Administrator", "Medical Staff", "Counselor"});
        JComboBox<String> shiftBox = new JComboBox<>(new String[]{"Morning", "Evening", "Night"});
        JTextField phoneField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField hireDateField = new JTextField();
        
        panel.add(new JLabel("Staff ID:"));
        panel.add(idField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Position:"));
        panel.add(positionBox);
        panel.add(new JLabel("Shift:"));
        panel.add(shiftBox);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Hire Date:"));
        panel.add(hireDateField);
        
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");
        
        saveBtn.addActionListener(e -> {
            Object[] row = {
                idField.getText(),
                nameField.getText(),
                positionBox.getSelectedItem(),
                shiftBox.getSelectedItem(),
                phoneField.getText(),
                emailField.getText(),
                hireDateField.getText()
            };
            staffModel.addRow(row);
            dialog.dispose();
        });
        
        cancelBtn.addActionListener(e -> dialog.dispose());
        
        panel.add(saveBtn);
        panel.add(cancelBtn);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private void showAddCellDialog() {
        JDialog dialog = new JDialog(this, "Add Cell", true);
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTextField cellNoField = new JTextField();
        JComboBox<String> blockBox = new JComboBox<>(new String[]{"A", "B", "C", "D"});
        JTextField capacityField = new JTextField();
        JTextField occupancyField = new JTextField();
        JComboBox<String> statusBox = new JComboBox<>(new String[]{"Available", "Occupied", "Maintenance"});
        JComboBox<String> securityBox = new JComboBox<>(new String[]{"Low", "Medium", "High", "Maximum"});
        
        panel.add(new JLabel("Cell No:"));
        panel.add(cellNoField);
        panel.add(new JLabel("Block:"));
        panel.add(blockBox);
        panel.add(new JLabel("Capacity:"));
        panel.add(capacityField);
        panel.add(new JLabel("Current Occupancy:"));
        panel.add(occupancyField);
        panel.add(new JLabel("Status:"));
        panel.add(statusBox);
        panel.add(new JLabel("Security Level:"));
        panel.add(securityBox);
        
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");
        
        saveBtn.addActionListener(e -> {
            Object[] row = {
                cellNoField.getText(),
                blockBox.getSelectedItem(),
                capacityField.getText(),
                occupancyField.getText(),
                statusBox.getSelectedItem(),
                securityBox.getSelectedItem()
            };
            cellModel.addRow(row);
            dialog.dispose();
        });
        
        cancelBtn.addActionListener(e -> dialog.dispose());
        
        panel.add(saveBtn);
        panel.add(cancelBtn);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private void deleteSelectedRow(JTable table, DefaultTableModel model) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete this record?", 
                "Confirm Delete", 
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                model.removeRow(selectedRow);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.");
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new PrisonManagementSystem().setVisible(true);
        });
    }
}