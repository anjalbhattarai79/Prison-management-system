package view;

import java.awt.*;
import java.time.LocalDate;
import javax.swing.*;
import controller.PrisonController;
import model.PrisonerModel;

/**
 * Helper class for showing Add and Edit prisoner dialogs
 * Keeps MainFrame clean by handling all dialog logic here
 * @author Anjal Bhattarai
 */
public class PrisonerDialogHelper {
    
    /**
     * Show Add Prisoner dialog
     */
    public static void showAddDialog(PrisonController controller, JFrame parent, JTable table) {
        // Create form fields
        JTextField nameField = new JTextField(20);
        JSpinner ageSpinner = new JSpinner(new SpinnerNumberModel(25, 18, 120, 1));
        JComboBox<String> genderCombo = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        JTextField addressField = new JTextField(20);
        JTextField crimeTypeField = new JTextField(20);
        JTextArea crimeDescArea = new JTextArea(3, 20);
        crimeDescArea.setLineWrap(true);
        crimeDescArea.setWrapStyleWord(true);
        JScrollPane crimeDescScroll = new JScrollPane(crimeDescArea);
        
        // Date picker for admission date
        SpinnerDateModel dateModel = new SpinnerDateModel();
        JSpinner admissionDateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(admissionDateSpinner, "yyyy-MM-dd");
        admissionDateSpinner.setEditor(dateEditor);
        admissionDateSpinner.setValue(new java.util.Date());
        
        JSpinner sentenceSpinner = new JSpinner(new SpinnerNumberModel(12, 1, 600, 1));
        JTextField locationField = new JTextField(20);
        
        // Status dropdown
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{
            "Active", "Released", "Transferred", "Medical", "Solitary", "Parole"
        });
        
        // Auto-generate ID and family code
        int nextId = controller.getNextAvailableId();
        String familyCode = "FAM" + nextId;
        JTextField familyCodeField = new JTextField(familyCode);
        
        // Create panel with GridBagLayout
        JPanel panel = createFormPanel(nameField, ageSpinner, genderCombo, addressField,
                crimeTypeField, crimeDescScroll, admissionDateSpinner, sentenceSpinner,
                locationField, statusCombo, familyCodeField);
        
        // Show dialog
        int result = JOptionPane.showConfirmDialog(parent, panel,
                "Add New Prisoner - ID: " + nextId,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                // Get admission date
                java.util.Date utilDate = (java.util.Date) admissionDateSpinner.getValue();
                LocalDate admissionDate = utilDate.toInstant()
                    .atZone(java.time.ZoneId.systemDefault())
                    .toLocalDate();
                
                boolean success = controller.addPrisoner(
                    nameField.getText(),
                    (int) ageSpinner.getValue(),
                    (String) genderCombo.getSelectedItem(),
                    addressField.getText(),
                    crimeTypeField.getText(),
                    crimeDescArea.getText(),
                    admissionDate,
                    (int) sentenceSpinner.getValue(),
                    locationField.getText(),
                    familyCodeField.getText(),
                    (String) statusCombo.getSelectedItem()
                );
                
                if (success) {
                    controller.loadPrisonerToTable(table);
                    setupTableButtons(table, controller, parent);
                    JOptionPane.showMessageDialog(parent,
                        "Prisoner added successfully!\nPrisoner ID: " + nextId +
                        "\nFamily Code: " + familyCodeField.getText(),
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent,
                    "Error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Show Edit Prisoner dialog
     */
    public static void showEditDialog(PrisonerModel prisoner, PrisonController controller,
                                      JFrame parent, JTable table) {
        // Create form fields pre-filled with prisoner data
        JTextField nameField = new JTextField(prisoner.getName(), 20);
        JSpinner ageSpinner = new JSpinner(new SpinnerNumberModel(prisoner.getAge(), 18, 120, 1));
        JComboBox<String> genderCombo = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        genderCombo.setSelectedItem(prisoner.getGender());
        JTextField addressField = new JTextField(prisoner.getAddress(), 20);
        JTextField crimeTypeField = new JTextField(prisoner.getCrimeType(), 20);
        JTextArea crimeDescArea = new JTextArea(prisoner.getCrimeDescription(), 3, 20);
        crimeDescArea.setLineWrap(true);
        crimeDescArea.setWrapStyleWord(true);
        JScrollPane crimeDescScroll = new JScrollPane(crimeDescArea);
        
        // Date picker with prisoner's admission date
        SpinnerDateModel dateModel = new SpinnerDateModel();
        JSpinner admissionDateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(admissionDateSpinner, "yyyy-MM-dd");
        admissionDateSpinner.setEditor(dateEditor);
        admissionDateSpinner.setValue(java.sql.Date.valueOf(prisoner.getAdmissionDate()));
        
        JSpinner sentenceSpinner = new JSpinner(new SpinnerNumberModel(
            prisoner.getSentenceDuration(), 1, 600, 1));
        JTextField locationField = new JTextField(prisoner.getPrisonLocation(), 20);
        
        // Status dropdown
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{
            "Active", "Released", "Transferred", "Medical", "Solitary", "Parole"
        });
        statusCombo.setSelectedItem(prisoner.getStatus());
        
        JTextField familyCodeField = new JTextField(prisoner.getFamilyCode(), 20);
        
        // Create panel
        JPanel panel = createFormPanel(nameField, ageSpinner, genderCombo, addressField,
                crimeTypeField, crimeDescScroll, admissionDateSpinner, sentenceSpinner,
                locationField, statusCombo, familyCodeField);
        
        // Show dialog
        int result = JOptionPane.showConfirmDialog(parent, panel,
                "Edit Prisoner - ID: " + prisoner.getPrisonerId(),
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                // Get admission date
                java.util.Date utilDate = (java.util.Date) admissionDateSpinner.getValue();
                LocalDate admissionDate = utilDate.toInstant()
                    .atZone(java.time.ZoneId.systemDefault())
                    .toLocalDate();
                
                boolean success = controller.updatePrisoner(
                    prisoner.getPrisonerId(),
                    nameField.getText(),
                    (int) ageSpinner.getValue(),
                    (String) genderCombo.getSelectedItem(),
                    addressField.getText(),
                    crimeTypeField.getText(),
                    crimeDescArea.getText(),
                    admissionDate,
                    (int) sentenceSpinner.getValue(),
                    locationField.getText(),
                    familyCodeField.getText()
                );
                
                if (success) {
                    // Update status separately
                    prisoner.setStatus((String) statusCombo.getSelectedItem());
                    
                    controller.loadPrisonerToTable(table);
                    setupTableButtons(table, controller, parent);
                    JOptionPane.showMessageDialog(parent,
                        "Prisoner updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent,
                    "Error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Create form panel with GridBagLayout
     */
    private static JPanel createFormPanel(JTextField nameField, JSpinner ageSpinner,
            JComboBox<String> genderCombo, JTextField addressField, JTextField crimeTypeField,
            JScrollPane crimeDescScroll, JSpinner admissionDateSpinner, JSpinner sentenceSpinner,
            JTextField locationField, JComboBox<String> statusCombo, JTextField familyCodeField) {
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        // Name
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Name:*"), gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);
        
        // Age
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Age:*"), gbc);
        gbc.gridx = 1;
        panel.add(ageSpinner, gbc);
        
        // Gender
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Gender:*"), gbc);
        gbc.gridx = 1;
        panel.add(genderCombo, gbc);
        
        // Address
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1;
        panel.add(addressField, gbc);
        
        // Crime Type
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Crime Type:*"), gbc);
        gbc.gridx = 1;
        panel.add(crimeTypeField, gbc);
        
        // Crime Description
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panel.add(new JLabel("Crime Description:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(crimeDescScroll, gbc);
        
        // Admission Date
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new JLabel("Admission Date:*"), gbc);
        gbc.gridx = 1;
        panel.add(admissionDateSpinner, gbc);
        
        // Sentence Duration
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Sentence (months):*"), gbc);
        gbc.gridx = 1;
        panel.add(sentenceSpinner, gbc);
        
        // Prison Location
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Prison Location:*"), gbc);
        gbc.gridx = 1;
        panel.add(locationField, gbc);
        
        // Status
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Status:*"), gbc);
        gbc.gridx = 1;
        panel.add(statusCombo, gbc);
        
        // Family Code
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Family Code:"), gbc);
        gbc.gridx = 1;
        panel.add(familyCodeField, gbc);
        
        return panel;
    }
    
    /**
     * Setup table action buttons (public static so MainFrame can call it)
     */
    public static void setupTableButtons(JTable table, PrisonController controller, JFrame parent) {
        table.getColumnModel().getColumn(10).setCellRenderer(new TableButtonRenderer());
        table.getColumnModel().getColumn(10).setCellEditor(
            new TableButtonEditor(new JCheckBox(), table, controller, parent));
    }
}
