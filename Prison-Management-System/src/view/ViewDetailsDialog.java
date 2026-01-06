package view;

import java.awt.*;
import javax.swing.*;
import controller.PrisonController;
import model.PrisonerModel;

/**
 * Dialog for viewing complete prisoner details including photo
 * @author Anjal Bhattarai
 */
public class ViewDetailsDialog extends JDialog {
    private PrisonerModel prisoner;
    private PrisonController controller;
    private JTable table;
    private JFrame parentFrame;
    
    public ViewDetailsDialog(JFrame parent, PrisonerModel prisoner, 
                            PrisonController controller, JTable table) {
        super(parent, "Prisoner Details - ID: " + prisoner.getPrisonerId(), true);
        this.prisoner = prisoner;
        this.controller = controller;
        this.table = table;
        this.parentFrame = parent;
        
        initComponents();
        setLocationRelativeTo(parent);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setSize(600, 700);
        
        // Main panel with padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Photo panel at top
        JPanel photoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        photoPanel.setBackground(Color.WHITE);
        JLabel photoLabel = new JLabel();
        photoLabel.setPreferredSize(new Dimension(200, 200));
        photoLabel.setBorder(BorderFactory.createLineBorder(new Color(52, 73, 94), 3));
        photoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Load and display photo
        try {
            String photoPath = prisoner.getPhotoPath();
            if (photoPath != null && !photoPath.isEmpty()) {
                ImageIcon icon = new ImageIcon(photoPath);
                Image scaledImage = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                photoLabel.setIcon(new ImageIcon(scaledImage));
            } else {
                photoLabel.setText("No Photo Available");
                photoLabel.setFont(new Font("Arial", Font.BOLD, 14));
            }
        } catch (Exception ex) {
            photoLabel.setText("Photo Not Found");
            photoLabel.setFont(new Font("Arial", Font.BOLD, 14));
            ex.printStackTrace(); // Debug: print error to console
        }
        
        photoPanel.add(photoLabel);
        
        // Details panel with GridBagLayout
        JPanel detailsPanel = new JPanel(new GridBagLayout());
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            "Prisoner Information",
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new Font("Arial", Font.BOLD, 14),
            new Color(52, 152, 219)));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        // Helper method to add field
        addField(detailsPanel, gbc, row++, "Prisoner ID:", String.valueOf(prisoner.getPrisonerId()));
        addField(detailsPanel, gbc, row++, "Name:", prisoner.getName());
        addField(detailsPanel, gbc, row++, "Age:", String.valueOf(prisoner.getAge()) + " years");
        addField(detailsPanel, gbc, row++, "Gender:", prisoner.getGender());
        addField(detailsPanel, gbc, row++, "Address:", prisoner.getAddress());
        addField(detailsPanel, gbc, row++, "Crime Type:", prisoner.getCrimeType());
        
        // Crime Description (text area for long text)
        gbc.gridx = 0; gbc.gridy = row;
        JLabel crimeDescLabel = new JLabel("Crime Description:");
        crimeDescLabel.setFont(new Font("Arial", Font.BOLD, 12));
        detailsPanel.add(crimeDescLabel, gbc);
        
        gbc.gridx = 1;
        JTextArea crimeDescArea = new JTextArea(prisoner.getCrimeDescription(), 3, 30);
        crimeDescArea.setLineWrap(true);
        crimeDescArea.setWrapStyleWord(true);
        crimeDescArea.setEditable(false);
        crimeDescArea.setBackground(new Color(236, 240, 241));
        crimeDescArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane crimeDescScroll = new JScrollPane(crimeDescArea);
        crimeDescScroll.setPreferredSize(new Dimension(350, 60));
        detailsPanel.add(crimeDescScroll, gbc);
        row++;
        
        addField(detailsPanel, gbc, row++, "Admission Date:", prisoner.getAdmissionDate().toString());
        addField(detailsPanel, gbc, row++, "Sentence Duration:", prisoner.getSentenceDuration() + " months");
        addField(detailsPanel, gbc, row++, "Release Date:", prisoner.getReleaseDate().toString());
        addField(detailsPanel, gbc, row++, "Prison Location:", prisoner.getPrisonLocation());
        
        // Status with color coding
        gbc.gridx = 0; gbc.gridy = row;
        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 12));
        detailsPanel.add(statusLabel, gbc);
        
        gbc.gridx = 1;
        JLabel statusValue = new JLabel(prisoner.getStatus());
        statusValue.setFont(new Font("Arial", Font.PLAIN, 12));
        statusValue.setOpaque(true);
        statusValue.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        // Color code based on status
        switch (prisoner.getStatus()) {
            case "Active":
                statusValue.setBackground(new Color(46, 213, 115));
                statusValue.setForeground(Color.WHITE);
                break;
            case "Released":
                statusValue.setBackground(new Color(52, 152, 219));
                statusValue.setForeground(Color.WHITE);
                break;
            case "Medical":
                statusValue.setBackground(new Color(241, 196, 15));
                statusValue.setForeground(Color.BLACK);
                break;
            case "Solitary":
                statusValue.setBackground(new Color(231, 76, 60));
                statusValue.setForeground(Color.WHITE);
                break;
            default:
                statusValue.setBackground(new Color(149, 165, 166));
                statusValue.setForeground(Color.WHITE);
        }
        detailsPanel.add(statusValue, gbc);
        row++;
        
        addField(detailsPanel, gbc, row++, "Health Status:", prisoner.getHealthStatus());
        addField(detailsPanel, gbc, row++, "Family Code:", prisoner.getFamilyCode());
        
        // Buttons panel at bottom
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton editButton = new JButton("Edit Prisoner");
        editButton.setPreferredSize(new Dimension(150, 35));
        editButton.setBackground(new Color(52, 152, 219)); // Blue
        editButton.setForeground(Color.WHITE);
        editButton.setFont(new Font("Arial", Font.BOLD, 12));
        editButton.setFocusPainted(false);
        editButton.addActionListener(e -> {
            dispose();
            PrisonerDialogHelper.showEditDialog(prisoner, controller, parentFrame, table);
        });
        
        JButton closeButton = new JButton("Close");
        closeButton.setPreferredSize(new Dimension(150, 35));
        closeButton.setBackground(new Color(149, 165, 166)); // Gray
        closeButton.setForeground(Color.WHITE);
        closeButton.setFont(new Font("Arial", Font.BOLD, 12));
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> dispose());
        
        buttonPanel.add(editButton);
        buttonPanel.add(closeButton);
        
        // Add all panels to main panel
        mainPanel.add(photoPanel, BorderLayout.NORTH);
        
        // Wrap details panel in scroll pane
        JScrollPane detailsScroll = new JScrollPane(detailsPanel);
        detailsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        detailsScroll.setBorder(null);
        mainPanel.add(detailsScroll, BorderLayout.CENTER);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        mainPanel.setBackground(Color.WHITE);
    }
    
    /**
     * Helper method to add a labeled field to the details panel
     */
    private void addField(JPanel panel, GridBagConstraints gbc, int row, String label, String value) {
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel fieldLabel = new JLabel(label);
        fieldLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(fieldLabel, gbc);
        
        gbc.gridx = 1;
        JLabel fieldValue = new JLabel(value);
        fieldValue.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(fieldValue, gbc);
    }
}
