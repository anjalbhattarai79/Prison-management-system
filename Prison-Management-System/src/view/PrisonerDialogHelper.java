package view;

import java.awt.*;
import java.io.File;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
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
        JSpinner.NumberEditor ageEditor = new JSpinner.NumberEditor(ageSpinner);
        ageSpinner.setEditor(ageEditor);
        ageEditor.getTextField().setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField tf = (JTextField) input;
                try {
                    int value = Integer.parseInt(tf.getText());
                    if (value < 18) {
                        JOptionPane.showMessageDialog(parent,
                            "Age must be at least 18 years.",
                            "Invalid Input",
                            JOptionPane.WARNING_MESSAGE);
                        tf.setText("18");
                        return false;
                    }
                    if (value > 120) {
                        JOptionPane.showMessageDialog(parent,
                            "Age must be 120 years or less.",
                            "Invalid Input",
                            JOptionPane.WARNING_MESSAGE);
                        tf.setText("120");
                        return false;
                    }
                    return true;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(parent,
                        "Please enter a valid number for age.",
                        "Invalid Input",
                        JOptionPane.WARNING_MESSAGE);
                    tf.setText("25");
                    return false;
                }
            }
        });
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
        dateEditor.getTextField().setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField tf = (JTextField) input;
                try {
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                    sdf.setLenient(false); // Strict date parsing - rejects invalid dates
                    java.util.Date parsedDate = sdf.parse(tf.getText());
                    
                    // Check if date is in the future
                    if (parsedDate.after(new java.util.Date())) {
                        JOptionPane.showMessageDialog(parent,
                            "Admission date cannot be in the future.\n" +
                            "Please enter a valid past or present date.",
                            "Invalid Date",
                            JOptionPane.WARNING_MESSAGE);
                        admissionDateSpinner.setValue(new java.util.Date());
                        return false;
                    }
                    return true;
                } catch (java.text.ParseException e) {
                    JOptionPane.showMessageDialog(parent,
                        "Invalid date format.\n" +
                        "Please use format: yyyy-MM-dd (e.g., 2024-12-25)\n" +
                        "Note: Days must be 1-31, months must be 1-12.",
                        "Invalid Date Format",
                        JOptionPane.WARNING_MESSAGE);
                    admissionDateSpinner.setValue(new java.util.Date());
                    return false;
                }
            }
        });
        
        JSpinner sentenceSpinner = new JSpinner(new SpinnerNumberModel(12, 1, 600, 1));
        JSpinner.NumberEditor sentenceEditor = new JSpinner.NumberEditor(sentenceSpinner);
        sentenceSpinner.setEditor(sentenceEditor);
        sentenceEditor.getTextField().setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField tf = (JTextField) input;
                try {
                    int value = Integer.parseInt(tf.getText());
                    if (value < 1) {
                        JOptionPane.showMessageDialog(parent,
                            "Sentence duration must be at least 1 month.",
                            "Invalid Input",
                            JOptionPane.WARNING_MESSAGE);
                        tf.setText("1");
                        return false;
                    }
                    if (value > 600) {
                        JOptionPane.showMessageDialog(parent,
                            "Sentence duration must be 600 months or less.",
                            "Invalid Input",
                            JOptionPane.WARNING_MESSAGE);
                        tf.setText("600");
                        return false;
                    }
                    return true;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(parent,
                        "Please enter a valid number for sentence duration.",
                        "Invalid Input",
                        JOptionPane.WARNING_MESSAGE);
                    tf.setText("12");
                    return false;
                }
            }
        });
        JTextField locationField = new JTextField(20);
        
        // Status dropdown
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{
            "Active", "Released", "Transferred", "Medical", "Solitary", "Parole"
        });
        
        // Auto-generate ID and family code
        int nextId = controller.getNextAvailableId();
        String familyCode = "FAM" + nextId;
        JTextField familyCodeField = new JTextField(familyCode);
        
        // Photo upload components
        JLabel photoPreviewLabel = new JLabel();
        photoPreviewLabel.setPreferredSize(new Dimension(150, 150));
        photoPreviewLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        photoPreviewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Automatically assign photo based on prisoner ID (cycle through 1.jpg to 5.jpg)
        int photoNumber = ((nextId - 101) % 5) + 1; // Cycles: 1,2,3,4,5,1,2,3...
        
        // Build absolute path from working directory
        String workingDir = System.getProperty("user.dir");
        System.out.println("[AddPrisoner Dialog] Working directory: " + workingDir);
        
        // Construct the full absolute path
        File photoFile = new File(workingDir, "Prison-Management-System/images/" + photoNumber + ".jpg");
        
        // If that doesn't exist, try without the Prison-Management-System prefix
        if (!photoFile.exists()) {
            photoFile = new File(workingDir, "images/" + photoNumber + ".jpg");
        }
        
        // Convert to absolute path
        String defaultPhoto = photoFile.getAbsolutePath();
        String[] selectedPhotoPath = {defaultPhoto};
        
        System.out.println("[AddPrisoner Dialog] Trying photo path: " + defaultPhoto);
        System.out.println("[AddPrisoner Dialog] Photo file exists: " + photoFile.exists());
        
        // Display default photo preview
        try {
            if (photoFile.exists()) {
                ImageIcon defaultIcon = new ImageIcon(defaultPhoto);
                if (defaultIcon.getIconWidth() > 0) {
                    Image scaledImage = defaultIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                    photoPreviewLabel.setIcon(new ImageIcon(scaledImage));
                    photoPreviewLabel.setText("");
                    System.out.println("[AddPrisoner Dialog] Photo preview loaded successfully");
                } else {
                    photoPreviewLabel.setText("Photo " + photoNumber);
                    System.out.println("[AddPrisoner Dialog] Photo icon has invalid dimensions");
                }
            } else {
                photoPreviewLabel.setText("Photo " + photoNumber);
                System.out.println("[AddPrisoner Dialog] Photo file does not exist at: " + defaultPhoto);
            }
        } catch (Exception ex) {
            photoPreviewLabel.setText("Photo " + photoNumber);
            System.out.println("[AddPrisoner Dialog] Exception: " + ex.getMessage());
        }
        
        JButton choosePhotoBtn = new JButton("Choose Photo");
        choosePhotoBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("Prison-Management-System/images"));
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Image Files (*.jpg, *.png)", "jpg", "jpeg", "png");
            fileChooser.setFileFilter(filter);
            
            int result = fileChooser.showOpenDialog(parent);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                selectedPhotoPath[0] = selectedFile.getPath();
                
                // Display preview
                ImageIcon icon = new ImageIcon(selectedPhotoPath[0]);
                Image scaledImage = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                photoPreviewLabel.setIcon(new ImageIcon(scaledImage));
                photoPreviewLabel.setText("");
            }
        });
        
        // Create panel with GridBagLayout
        JPanel panel = createFormPanel(nameField, ageSpinner, genderCombo, addressField,
                crimeTypeField, crimeDescScroll, admissionDateSpinner, sentenceSpinner,
                locationField, statusCombo, familyCodeField, photoPreviewLabel, choosePhotoBtn);
        
        // Show dialog
        int result = JOptionPane.showConfirmDialog(parent, panel,
                "Add New Prisoner - ID: " + nextId,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                // ========== INPUT VALIDATION ==========
                String name = nameField.getText().trim();
                String address = addressField.getText().trim();
                String crimeType = crimeTypeField.getText().trim();
                String crimeDesc = crimeDescArea.getText().trim();
                String location = locationField.getText().trim();
                familyCode = familyCodeField.getText().trim();
                
                // Validate required fields
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(parent,
                        "Name is required and cannot be empty.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validate name format (letters, spaces, hyphens, apostrophes only)
                if (!name.matches("^[a-zA-Z\\s'-]+$")) {
                    JOptionPane.showMessageDialog(parent,
                        "Name can only contain letters, spaces, hyphens, and apostrophes.\n" +
                        "No numbers or special characters allowed.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validate name length
                if (name.length() > 100) {
                    JOptionPane.showMessageDialog(parent,
                        "Name is too long. Maximum 100 characters allowed.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (crimeType.isEmpty()) {
                    JOptionPane.showMessageDialog(parent,
                        "Crime Type is required and cannot be empty.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (location.isEmpty()) {
                    JOptionPane.showMessageDialog(parent,
                        "Prison Location is required and cannot be empty.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validate age (prevent negative or zero values)
                int age = (int) ageSpinner.getValue();
                if (age <= 0) {
                    JOptionPane.showMessageDialog(parent,
                        "Age must be a positive number.\n" +
                        "Age cannot be zero or negative.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (age < 18) {
                    JOptionPane.showMessageDialog(parent,
                        "Age must be at least 18 years.\n" +
                        "Minors cannot be admitted to prison.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (age > 150) {
                    JOptionPane.showMessageDialog(parent,
                        "Age must be 150 years or less.\n" +
                        "Please enter a valid age.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validate sentence duration (prevent negative values)
                int sentenceDuration = (int) sentenceSpinner.getValue();
                if (sentenceDuration <= 0) {
                    JOptionPane.showMessageDialog(parent,
                        "Sentence duration must be a positive number.\n" +
                        "Cannot be zero or negative.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Get admission date
                java.util.Date utilDate = (java.util.Date) admissionDateSpinner.getValue();
                LocalDate admissionDate = utilDate.toInstant()
                    .atZone(java.time.ZoneId.systemDefault())
                    .toLocalDate();
                
                // Debug: Print photo path being used
                System.out.println("[AddPrisoner] Using photo path: " + selectedPhotoPath[0]);
                File photoFileCheck = new File(selectedPhotoPath[0]);
                System.out.println("[AddPrisoner] Photo file exists: " + photoFileCheck.exists());
                System.out.println("[AddPrisoner] Photo file absolute path: " + photoFileCheck.getAbsolutePath());
                
                boolean success = controller.addPrisoner(
                    name,
                    age,
                    (String) genderCombo.getSelectedItem(),
                    address,
                    crimeType,
                    crimeDesc,
                    admissionDate,
                    sentenceDuration,
                    location,
                    familyCode,
                    selectedPhotoPath[0],
                    (String) statusCombo.getSelectedItem()
                );
                
                if (success) {
                    controller.loadPrisonerToTable(table);
                    setupTableButtons(table, controller, parent);
                    // Update recent activities
                    if (parent instanceof MainFrame) {
                        ((MainFrame) parent).updateRecentActivities();
                    }
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
        System.out.println("[DEBUG] showEditDialog called for prisoner: " + prisoner.getName() + " (ID: " + prisoner.getPrisonerId() + ")");
        // Create form fields pre-filled with prisoner data
        JTextField nameField = new JTextField(prisoner.getName(), 20);
        JSpinner ageSpinner = new JSpinner(new SpinnerNumberModel(prisoner.getAge(), 18, 120, 1));
        JSpinner.NumberEditor ageEditor = new JSpinner.NumberEditor(ageSpinner);
        ageSpinner.setEditor(ageEditor);
        ageEditor.getTextField().setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField tf = (JTextField) input;
                try {
                    int value = Integer.parseInt(tf.getText());
                    if (value < 18) {
                        JOptionPane.showMessageDialog(parent,
                            "Age must be at least 18 years.",
                            "Invalid Input",
                            JOptionPane.WARNING_MESSAGE);
                        tf.setText("18");
                        return false;
                    }
                    if (value > 120) {
                        JOptionPane.showMessageDialog(parent,
                            "Age must be 120 years or less.",
                            "Invalid Input",
                            JOptionPane.WARNING_MESSAGE);
                        tf.setText("120");
                        return false;
                    }
                    return true;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(parent,
                        "Please enter a valid number for age.",
                        "Invalid Input",
                        JOptionPane.WARNING_MESSAGE);
                    tf.setText(String.valueOf(prisoner.getAge()));
                    return false;
                }
            }
        });
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
        dateEditor.getTextField().setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField tf = (JTextField) input;
                try {
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                    sdf.setLenient(false); // Strict date parsing - rejects invalid dates
                    java.util.Date parsedDate = sdf.parse(tf.getText());
                    
                    // Check if date is in the future
                    if (parsedDate.after(new java.util.Date())) {
                        JOptionPane.showMessageDialog(parent,
                            "Admission date cannot be in the future.\n" +
                            "Please enter a valid past or present date.",
                            "Invalid Date",
                            JOptionPane.WARNING_MESSAGE);
                        admissionDateSpinner.setValue(java.sql.Date.valueOf(prisoner.getAdmissionDate()));
                        return false;
                    }
                    return true;
                } catch (java.text.ParseException e) {
                    JOptionPane.showMessageDialog(parent,
                        "Invalid date format.\n" +
                        "Please use format: yyyy-MM-dd (e.g., 2024-12-25)\n" +
                        "Note: Days must be 1-31, months must be 1-12.",
                        "Invalid Date Format",
                        JOptionPane.WARNING_MESSAGE);
                    admissionDateSpinner.setValue(java.sql.Date.valueOf(prisoner.getAdmissionDate()));
                    return false;
                }
            }
        });
        
        JSpinner sentenceSpinner = new JSpinner(new SpinnerNumberModel(
            prisoner.getSentenceDuration(), 1, 600, 1));
        JSpinner.NumberEditor sentenceEditor = new JSpinner.NumberEditor(sentenceSpinner);
        sentenceSpinner.setEditor(sentenceEditor);
        sentenceEditor.getTextField().setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField tf = (JTextField) input;
                try {
                    int value = Integer.parseInt(tf.getText());
                    if (value < 1) {
                        JOptionPane.showMessageDialog(parent,
                            "Sentence duration must be at least 1 month.",
                            "Invalid Input",
                            JOptionPane.WARNING_MESSAGE);
                        tf.setText("1");
                        return false;
                    }
                    if (value > 600) {
                        JOptionPane.showMessageDialog(parent,
                            "Sentence duration must be 600 months or less.",
                            "Invalid Input",
                            JOptionPane.WARNING_MESSAGE);
                        tf.setText("600");
                        return false;
                    }
                    return true;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(parent,
                        "Please enter a valid number for sentence duration.",
                        "Invalid Input",
                        JOptionPane.WARNING_MESSAGE);
                    tf.setText(String.valueOf(prisoner.getSentenceDuration()));
                    return false;
                }
            }
        });
        JTextField locationField = new JTextField(prisoner.getPrisonLocation(), 20);
        
        // Status dropdown
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{
            "Active", "Released", "Transferred", "Medical", "Solitary", "Parole"
        });
        statusCombo.setSelectedItem(prisoner.getStatus());
        
        JTextField familyCodeField = new JTextField(prisoner.getFamilyCode(), 20);
        
        // Photo upload components with existing photo
        JLabel photoPreviewLabel = new JLabel();
        photoPreviewLabel.setPreferredSize(new Dimension(150, 150));
        photoPreviewLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        photoPreviewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        String[] selectedPhotoPath = {prisoner.getPhotoPath() != null ? prisoner.getPhotoPath() : "Prison-Management-System/images/default-prisoner.png"};
        
        // Display existing photo
        try {
            ImageIcon icon = new ImageIcon(selectedPhotoPath[0]);
            Image scaledImage = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            photoPreviewLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception ex) {
            photoPreviewLabel.setText("No Photo");
        }
        
        JButton choosePhotoBtn = new JButton("Choose Photo");
        choosePhotoBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("Prison-Management-System/images"));
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Image Files (*.jpg, *.png)", "jpg", "jpeg", "png");
            fileChooser.setFileFilter(filter);
            
            int result = fileChooser.showOpenDialog(parent);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                selectedPhotoPath[0] = selectedFile.getPath();
                
                // Display preview
                ImageIcon icon = new ImageIcon(selectedPhotoPath[0]);
                Image scaledImage = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                photoPreviewLabel.setIcon(new ImageIcon(scaledImage));
                photoPreviewLabel.setText("");
            }
        });
        
        // Create panel
        JPanel panel = createFormPanel(nameField, ageSpinner, genderCombo, addressField,
                crimeTypeField, crimeDescScroll, admissionDateSpinner, sentenceSpinner,
                locationField, statusCombo, familyCodeField, photoPreviewLabel, choosePhotoBtn);
        
        // Show dialog
        int result = JOptionPane.showConfirmDialog(parent, panel,
                "Edit Prisoner - ID: " + prisoner.getPrisonerId(),
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                // ========== INPUT VALIDATION ==========
                String name = nameField.getText().trim();
                String address = addressField.getText().trim();
                String crimeType = crimeTypeField.getText().trim();
                String crimeDesc = crimeDescArea.getText().trim();
                String location = locationField.getText().trim();
                String familyCode = familyCodeField.getText().trim();
                
                // Validate required fields
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(parent,
                        "Name is required and cannot be empty.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validate name format (letters, spaces, hyphens, apostrophes only)
                if (!name.matches("^[a-zA-Z\\s'-]+$")) {
                    JOptionPane.showMessageDialog(parent,
                        "Name can only contain letters, spaces, hyphens, and apostrophes.\n" +
                        "No numbers or special characters allowed.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validate name length
                if (name.length() > 100) {
                    JOptionPane.showMessageDialog(parent,
                        "Name is too long. Maximum 100 characters allowed.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (crimeType.isEmpty()) {
                    JOptionPane.showMessageDialog(parent,
                        "Crime Type is required and cannot be empty.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (location.isEmpty()) {
                    JOptionPane.showMessageDialog(parent,
                        "Prison Location is required and cannot be empty.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validate age (prevent negative or zero values)
                int age = (int) ageSpinner.getValue();
                if (age <= 0) {
                    JOptionPane.showMessageDialog(parent,
                        "Age must be a positive number.\n" +
                        "Age cannot be zero or negative.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (age < 18) {
                    JOptionPane.showMessageDialog(parent,
                        "Age must be at least 18 years.\n" +
                        "Minors cannot be admitted to prison.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (age > 150) {
                    JOptionPane.showMessageDialog(parent,
                        "Age must be 150 years or less.\n" +
                        "Please enter a valid age.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validate sentence duration (prevent negative values)
                int sentenceDuration = (int) sentenceSpinner.getValue();
                if (sentenceDuration <= 0) {
                    JOptionPane.showMessageDialog(parent,
                        "Sentence duration must be a positive number.\n" +
                        "Cannot be zero or negative.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Get admission date
                LocalDate admissionDate;
                try {
                    java.util.Date utilDate = (java.util.Date) admissionDateSpinner.getValue();
                    admissionDate = utilDate.toInstant()
                        .atZone(java.time.ZoneId.systemDefault())
                        .toLocalDate();
                    
                    // Additional validation: Check if date is in the future
                    if (admissionDate.isAfter(LocalDate.now())) {
                        JOptionPane.showMessageDialog(parent,
                            "Admission date cannot be in the future.\n" +
                            "Please enter a valid past or present date.",
                            "Validation Error",
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(parent,
                        "Invalid admission date.\n" +
                        "Error: " + e.getMessage(),
                        "Date Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                boolean success = controller.updatePrisoner(
                    prisoner.getPrisonerId(),
                    name,
                    age,
                    (String) genderCombo.getSelectedItem(),
                    address,
                    crimeType,
                    crimeDesc,
                    admissionDate,
                    sentenceDuration,
                    location,
                    familyCode,
                    selectedPhotoPath[0]
                );
                
                if (success) {
                    // Update status separately
                    prisoner.setStatus((String) statusCombo.getSelectedItem());
                    
                    controller.loadPrisonerToTable(table);
                    setupTableButtons(table, controller, parent);
                    // Update recent activities
                    if (parent instanceof MainFrame) {
                        ((MainFrame) parent).updateRecentActivities();
                    }
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
            JTextField locationField, JComboBox<String> statusCombo, JTextField familyCodeField,
            JLabel photoPreviewLabel, JButton choosePhotoBtn) {
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        // Photo (spans both columns)
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        panel.add(new JLabel("Photo:"), gbc);
        gbc.gridx = 1;
        JPanel photoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        photoPanel.add(photoPreviewLabel);
        photoPanel.add(choosePhotoBtn);
        panel.add(photoPanel, gbc);
        row++;
        
        // Reset gridwidth
        gbc.gridwidth = 1;
        
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
        // Set row height for better button visibility
        table.setRowHeight(32);  // Increased from default ~16px to 32px
        
        // Set column widths for better UX
        table.getColumnModel().getColumn(0).setPreferredWidth(50);   // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(120);  // Name
        table.getColumnModel().getColumn(2).setPreferredWidth(50);   // Age
        table.getColumnModel().getColumn(3).setPreferredWidth(70);   // Gender
        table.getColumnModel().getColumn(4).setPreferredWidth(100);  // Admission Date
        table.getColumnModel().getColumn(5).setPreferredWidth(110);  // Crime Type
        table.getColumnModel().getColumn(6).setPreferredWidth(100);  // Release Date
        table.getColumnModel().getColumn(7).setPreferredWidth(80);   // Sentence
        table.getColumnModel().getColumn(8).setPreferredWidth(100);  // Location
        table.getColumnModel().getColumn(9).setPreferredWidth(90);   // Status
        table.getColumnModel().getColumn(10).setPreferredWidth(240); // Actions (View, Edit, Delete)
        
        // Setup button renderer and editor
        table.getColumnModel().getColumn(10).setCellRenderer(new TableButtonRenderer());
        table.getColumnModel().getColumn(10).setCellEditor(
            new TableButtonEditor(new JCheckBox(), table, controller, parent));
    }
}
