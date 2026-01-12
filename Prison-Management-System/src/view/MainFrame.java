/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import java.awt.Color;
import java.util.LinkedList;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import model.PrisonerModel;
import controller.PrisonController;

/**
 * MainFrame - Main Application Window
 * Professional Blue/Gray Theme for Government System
 * @author Anjal Bhattarai
 */
public class MainFrame extends javax.swing.JFrame {
 
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainFrame.class.getName());
    
    // Panel names as constants - matching CardLayout card names in initComponents
    public static final String HOME_PANEL = "card3";
    public static final String ADMIN_LOGIN_PANEL = "card4";
    public static final String ADMIN_DASHBOARD_PANEL = "card2";
    public static final String FAMILY_LOGIN_PANEL = "card5";
    public static final String FAMILY_DASHBOARD_PANEL = "card6";
    
    // Color Scheme - Professional Blue/Gray
    public static final Color PRIMARY_COLOR = new Color(41, 98, 255);      // Royal Blue
    public static final Color SECONDARY_COLOR = new Color(52, 73, 94);     // Dark Blue-Gray
    public static final Color ACCENT_COLOR = new Color(46, 213, 115);      // Success Green
    public static final Color DANGER_COLOR = new Color(255, 71, 87);       // Alert Red
    public static final Color WARNING_COLOR = new Color(255, 165, 2);      // Warning Orange
    public static final Color BACKGROUND_COLOR = new Color(236, 240, 241); // Light Gray
    public static final Color CARD_COLOR = Color.WHITE;
    public static final Color TEXT_PRIMARY = new Color(44, 62, 80);
    public static final Color TEXT_SECONDARY = new Color(127, 140, 141);

    // Shared controller instance
    private PrisonController controller;

    // Current prisoner ID for family dashboard
    private int currentFamilyPrisonerId = 0;

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        setupCustomComponents();
        
        // Initialize controller with initial data
        controller = new PrisonController();
        controller.prepareInitialData();
        
        showHomePanel();
    }
    
    /**
     * Load family dashboard with prisoner details
     */
    private void loadFamilyDashboard(PrisonerModel prisoner) {
        
        
        // Fix null release date
        String releaseDate = prisoner.getReleaseDate() != null 
            ? prisoner.getReleaseDate().toString() 
            : "Not determined";
        
        // Update value labels (jLabel25-30) with prisoner's actual data
        jLabel25.setText("<html>" + prisoner.getName() + "</html>");
        jLabel26.setText("<html>" + prisoner.getStatus() + "</html>");
        jLabel27.setText("<html>" + prisoner.getAdmissionDate().toString() + "</html>");
        jLabel28.setText("<html>" + prisoner.getHealthStatus() + "</html>");
        jLabel29.setText("<html>" + prisoner.getPrisonLocation() + "</html>");
        jLabel30.setText("<html>" + releaseDate + "</html>");
        
        // Set relationship dropdown options
        jComboBox1.removeAllItems();
        jComboBox1.addItem("Parent");
        jComboBox1.addItem("Spouse");
        jComboBox1.addItem("Sibling");
        jComboBox1.addItem("Child");
        jComboBox1.addItem("Friend");
        jComboBox1.addItem("Lawyer");
        jComboBox1.addItem("Other");
        
        // Clear visit request form
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        
        // Remove existing focus listeners to prevent duplicates
        for (java.awt.event.FocusListener fl : jTextField3.getFocusListeners()) {
            jTextField3.removeFocusListener(fl);
        }
        for (java.awt.event.FocusListener fl : jTextField4.getFocusListeners()) {
            jTextField4.removeFocusListener(fl);
        }
        for (java.awt.event.FocusListener fl : jTextField5.getFocusListeners()) {
            jTextField5.removeFocusListener(fl);
        }
        
        // Set placeholder text
        jTextField3.setForeground(java.awt.Color.GRAY);
        jTextField3.setText("Enter your full name");
        jTextField3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (jTextField3.getText().equals("Enter your full name")) {
                    jTextField3.setText("");
                    jTextField3.setForeground(java.awt.Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (jTextField3.getText().isEmpty()) {
                    jTextField3.setForeground(java.awt.Color.GRAY);
                    jTextField3.setText("Enter your full name");
                }
            }
        });
        
        jTextField4.setForeground(java.awt.Color.GRAY);
        jTextField4.setText("YYYY-MM-DD");
        jTextField4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (jTextField4.getText().equals("YYYY-MM-DD")) {
                    jTextField4.setText("");
                    jTextField4.setForeground(java.awt.Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (jTextField4.getText().isEmpty()) {
                    jTextField4.setForeground(java.awt.Color.GRAY);
                    jTextField4.setText("YYYY-MM-DD");
                }
            }
        });
        
        jTextField5.setForeground(java.awt.Color.GRAY);
        jTextField5.setText("Purpose of visit");
        jTextField5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (jTextField5.getText().equals("Purpose of visit")) {
                    jTextField5.setText("");
                    jTextField5.setForeground(java.awt.Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (jTextField5.getText().isEmpty()) {
                    jTextField5.setForeground(java.awt.Color.GRAY);
                    jTextField5.setText("Purpose of visit");
                }
            }
        });
        
        // Load visit requests for this prisoner
        loadVisitRequestsForPrisoner(prisoner.getPrisonerId());
        
        // Force UI refresh to ensure labels display properly
        jLabel13.revalidate();
        jLabel14.revalidate();
        jLabel15.revalidate();
        jLabel16.revalidate();
        jLabel17.revalidate();
        jLabel18.revalidate();
        jPanel6.revalidate();
        jPanel6.repaint();
    }
    
    /**
     * Load visit requests into the family dashboard table
     */
    private void loadVisitRequestsForPrisoner(int prisonerId) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        
        java.util.LinkedList<model.VisitRequest> requests = controller.getVisitRequestsForPrisoner(prisonerId);
        
        for (model.VisitRequest request : requests) {
            Object[] row = {
                request.getRequestId(),
                request.getVisitorName(),
                request.getRelationship(),
                request.getFormattedPreferredDate(),
                request.getStatus(),
                request.getPurpose()
            };
            model.addRow(row);
        }
        
        // If no requests, show message
        if (requests.isEmpty()) {
            Object[] row = {"-", "No visit requests yet", "-", "-", "-", "Submit a request above to get started"};
            model.addRow(row);
        }
    }
    
    /**
     * Clear family dashboard
     */
    private void clearFamilyDashboard() {
        jLabel13.setText("Name: -");
        jLabel14.setText("Status: -");
        jLabel15.setText("Admitted: -");
        jLabel16.setText("Location: -");
        jLabel17.setText("Health: -");
        jLabel18.setText("Expected Release: -");
        
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
    }
    
    /**
     * Show family dashboard panel
     */
    private void showFamilyDashboardPanel() {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, FAMILY_DASHBOARD_PANEL);
    }
    
    /**
     * Setup placeholder text for a text field
     */
    private void setupPlaceholderText(JTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);
        
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }
            
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().trim().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });
    }
    
    /**
     * Setup custom components like mouse listeners for labels
     */
    private void setupCustomComponents() {
        // Setup placeholder text for family login fields
        setupPlaceholderText(jTextField1, "Enter prisoner ID");
        setupPlaceholderText(jTextField2, "Enter family code");
        
        // Setup Search button
        SearchButton.addActionListener(evt -> {
            String searchType = (String) SearchTypeComboBox.getSelectedItem();
            String searchTerm = SearchTextField.getText().trim();
            
            if (searchTerm.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Please enter a search term",
                    "Search Error",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Call controller's search method
            LinkedList<PrisonerModel> results = controller.searchPrisoners(searchType, searchTerm);
            
            // Display results
            if (!results.isEmpty()) {
                controller.loadPrisonerListToTable(PrisonerRecordTable, results);
                PrisonerDialogHelper.setupTableButtons(PrisonerRecordTable, controller, this);
                
                JOptionPane.showMessageDialog(this,
                    "Search complete!\nFound " + results.size() + " result(s)\n\n" +
                    "Check console output to see the search algorithm in action.",
                    "Search Success",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        // Setup Refresh button
        RefreshButton.addActionListener(evt -> {
            controller.loadPrisonerToTable(PrisonerRecordTable);
            PrisonerDialogHelper.setupTableButtons(PrisonerRecordTable, controller, this);
            SearchTextField.setText(""); // Clear search field
            JOptionPane.showMessageDialog(this,
                "Table refreshed!\nShowing all " + controller.getPrisonerCount() + " prisoners",
                "Refresh Complete",
                JOptionPane.INFORMATION_MESSAGE);
        });
        
        // Setup Sort button
        SortButton.addActionListener(evt -> {
            String sortBy = (String) SortByComboBox.getSelectedItem();
            String sortBasis = (String) SortBasisComboBox.getSelectedItem();
            String sortType = (String) SortTypeComboBox.getSelectedItem();
            
            if (sortBy == null || sortBasis == null || sortType == null) {
                JOptionPane.showMessageDialog(this,
                    "Please select sort options",
                    "Sort Error",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            boolean ascending = "Ascending".equals(sortBasis);
            
            // Call controller's sort with explicit algorithm from UI
            LinkedList<PrisonerModel> sortedList = controller.sortPrisoners(sortBy, ascending, sortType);
            
            // Display sorted results
            controller.loadPrisonerListToTable(PrisonerRecordTable, sortedList);
            PrisonerDialogHelper.setupTableButtons(PrisonerRecordTable, controller, this);
            
            System.out.println("\nTable updated with sorted results. Check console above for sorting algorithm details.");
        });
        
        // Setup Visit Requests button
        VisitRequestsButton.addActionListener(evt -> {
            VisitRequestsDialog dialog = new VisitRequestsDialog(this, controller);
            dialog.setVisible(true);
        });
        
        // Setup Back to Home button in Admin Login (jButton4)
        jButton4.addActionListener(evt -> {
            showHomePanel();
        });
        
        // Setup Back to Home button in Family Login (jButton3)
        jButton3.addActionListener(evt -> {
            showHomePanel();
        });
        
    }     
    
    /**
     * Update Recent Activity Panel with latest activities
     */
    public void updateRecentActivities() {
        String activitiesHTML = controller.getFormattedActivities();
        ActivityLabel.setText(activitiesHTML);
        ActivityLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        System.out.println("[MainFrame] Recent activities updated");
    }
     
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        AdminLoginPanel = new javax.swing.JPanel();
        AdminLoginHeaderPanel = new javax.swing.JPanel();
        TitleLabel = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        AdminLoginBodyPanel = new javax.swing.JPanel();
        AdminCredentialEntryPanel = new javax.swing.JPanel();
        AdminAcess = new javax.swing.JLabel();
        EnterCredentials = new javax.swing.JLabel();
        UsernameTextField = new javax.swing.JTextField();
        UsernameLabel = new javax.swing.JLabel();
        PasswordLabel = new javax.swing.JLabel();
        AdminLogin = new javax.swing.JButton();
        PasswordField = new javax.swing.JPasswordField();
        AdminDashboardPanel = new javax.swing.JPanel();
        AdminDashboardHeaderPanel = new javax.swing.JPanel();
        DashboardTItleLabel = new javax.swing.JLabel();
        AdminLogOutButton = new javax.swing.JButton();
        AdminDashboardBodyPanel = new javax.swing.JPanel();
        OperationalPanel = new javax.swing.JPanel();
        SearchLabel = new javax.swing.JLabel();
        SortByLabel = new javax.swing.JLabel();
        SearchTextField = new javax.swing.JTextField();
        SearchTypeComboBox = new javax.swing.JComboBox<>();
        SearchButton = new javax.swing.JButton();
        SortByComboBox = new javax.swing.JComboBox<>();
        SortBasisComboBox = new javax.swing.JComboBox<>();
        SortButton = new javax.swing.JButton();
        VisitRequestsButton = new javax.swing.JButton();
        TrashBinButton = new javax.swing.JButton();
        SortTypeComboBox = new javax.swing.JComboBox<>();
        AddPrisonerButton = new javax.swing.JButton();
        RefreshButton = new javax.swing.JButton();
        PrisonerRecordPanel = new javax.swing.JPanel();
        PrisonerRecordLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        PrisonerRecordTable = new javax.swing.JTable();
        RecentActivityPanel = new javax.swing.JPanel();
        ActivityLabel = new javax.swing.JLabel();
        FamilyLoginPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        FamilyPortalLoginButton = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        HomePanel = new javax.swing.JPanel();
        HomeHeader = new javax.swing.JPanel();
        Header = new javax.swing.JLabel();
        Slogan = new javax.swing.JLabel();
        HomeBody = new javax.swing.JPanel();
        AdminLoginButton = new javax.swing.JButton();
        FamilyLoginButton = new javax.swing.JButton();
        FamilyDashboardPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPanel.setLayout(new java.awt.CardLayout());

        AdminLoginHeaderPanel.setBackground(new java.awt.Color(0, 204, 255));

        TitleLabel.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        TitleLabel.setForeground(new java.awt.Color(255, 255, 255));
        TitleLabel.setText("Admin Login");

        jButton4.setBackground(new java.awt.Color(102, 102, 255));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Back to Home");

        javax.swing.GroupLayout AdminLoginHeaderPanelLayout = new javax.swing.GroupLayout(AdminLoginHeaderPanel);
        AdminLoginHeaderPanel.setLayout(AdminLoginHeaderPanelLayout);
        AdminLoginHeaderPanelLayout.setHorizontalGroup(
            AdminLoginHeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminLoginHeaderPanelLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jButton4)
                .addGap(407, 407, 407)
                .addComponent(TitleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(369, Short.MAX_VALUE))
        );
        AdminLoginHeaderPanelLayout.setVerticalGroup(
            AdminLoginHeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminLoginHeaderPanelLayout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(jButton4)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AdminLoginHeaderPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(TitleLabel)
                .addGap(34, 34, 34))
        );

        AdminLoginBodyPanel.setBackground(new java.awt.Color(204, 255, 255));

        AdminCredentialEntryPanel.setBackground(new java.awt.Color(255, 255, 255));

        AdminAcess.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        AdminAcess.setText("Admin Access");

        EnterCredentials.setText("Enter your credentials to continue");

        UsernameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UsernameTextFieldActionPerformed(evt);
            }
        });

        UsernameLabel.setText("Username");

        PasswordLabel.setText("Password");

        AdminLogin.setBackground(new java.awt.Color(51, 102, 255));
        AdminLogin.setForeground(new java.awt.Color(255, 255, 255));
        AdminLogin.setText("Login");
        AdminLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AdminLoginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AdminCredentialEntryPanelLayout = new javax.swing.GroupLayout(AdminCredentialEntryPanel);
        AdminCredentialEntryPanel.setLayout(AdminCredentialEntryPanelLayout);
        AdminCredentialEntryPanelLayout.setHorizontalGroup(
            AdminCredentialEntryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminCredentialEntryPanelLayout.createSequentialGroup()
                .addGroup(AdminCredentialEntryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AdminCredentialEntryPanelLayout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(EnterCredentials))
                    .addGroup(AdminCredentialEntryPanelLayout.createSequentialGroup()
                        .addGap(137, 137, 137)
                        .addComponent(UsernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AdminCredentialEntryPanelLayout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(AdminAcess))
                    .addGroup(AdminCredentialEntryPanelLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(AdminCredentialEntryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(PasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(UsernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(51, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AdminCredentialEntryPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(AdminCredentialEntryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AdminCredentialEntryPanelLayout.createSequentialGroup()
                        .addComponent(AdminLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(113, 113, 113))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AdminCredentialEntryPanelLayout.createSequentialGroup()
                        .addComponent(PasswordLabel)
                        .addGap(141, 141, 141))))
        );
        AdminCredentialEntryPanelLayout.setVerticalGroup(
            AdminCredentialEntryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminCredentialEntryPanelLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(AdminAcess)
                .addGap(18, 18, 18)
                .addComponent(EnterCredentials)
                .addGap(56, 56, 56)
                .addComponent(UsernameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(UsernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PasswordLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(AdminLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout AdminLoginBodyPanelLayout = new javax.swing.GroupLayout(AdminLoginBodyPanel);
        AdminLoginBodyPanel.setLayout(AdminLoginBodyPanelLayout);
        AdminLoginBodyPanelLayout.setHorizontalGroup(
            AdminLoginBodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminLoginBodyPanelLayout.createSequentialGroup()
                .addGap(135, 135, 135)
                .addComponent(AdminCredentialEntryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(207, Short.MAX_VALUE))
        );
        AdminLoginBodyPanelLayout.setVerticalGroup(
            AdminLoginBodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminLoginBodyPanelLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(AdminCredentialEntryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(88, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout AdminLoginPanelLayout = new javax.swing.GroupLayout(AdminLoginPanel);
        AdminLoginPanel.setLayout(AdminLoginPanelLayout);
        AdminLoginPanelLayout.setHorizontalGroup(
            AdminLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminLoginPanelLayout.createSequentialGroup()
                .addGroup(AdminLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AdminLoginPanelLayout.createSequentialGroup()
                        .addGap(487, 487, 487)
                        .addComponent(AdminLoginBodyPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AdminLoginPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(AdminLoginHeaderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        AdminLoginPanelLayout.setVerticalGroup(
            AdminLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminLoginPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AdminLoginHeaderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(AdminLoginBodyPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        mainPanel.add(AdminLoginPanel, "card4");

        AdminDashboardHeaderPanel.setBackground(new java.awt.Color(0, 153, 255));

        DashboardTItleLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        DashboardTItleLabel.setForeground(new java.awt.Color(255, 255, 255));
        DashboardTItleLabel.setText("Admin Dashboard");

        AdminLogOutButton.setBackground(new java.awt.Color(255, 51, 51));
        AdminLogOutButton.setForeground(new java.awt.Color(255, 255, 255));
        AdminLogOutButton.setText("LogOut");
        AdminLogOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AdminLogOutButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AdminDashboardHeaderPanelLayout = new javax.swing.GroupLayout(AdminDashboardHeaderPanel);
        AdminDashboardHeaderPanel.setLayout(AdminDashboardHeaderPanelLayout);
        AdminDashboardHeaderPanelLayout.setHorizontalGroup(
            AdminDashboardHeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminDashboardHeaderPanelLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(DashboardTItleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(AdminLogOutButton)
                .addGap(36, 36, 36))
        );
        AdminDashboardHeaderPanelLayout.setVerticalGroup(
            AdminDashboardHeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminDashboardHeaderPanelLayout.createSequentialGroup()
                .addGroup(AdminDashboardHeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AdminDashboardHeaderPanelLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(DashboardTItleLabel))
                    .addGroup(AdminDashboardHeaderPanelLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(AdminLogOutButton)))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        OperationalPanel.setBackground(new java.awt.Color(255, 255, 255));

        SearchLabel.setText("Search:");

        SortByLabel.setText("SortBy:");

        SearchTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchTextFieldActionPerformed(evt);
            }
        });

        SearchTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Name/Crime [Linear Search]", "ID [ Binary Search ]" }));
        SearchTypeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchTypeComboBoxActionPerformed(evt);
            }
        });

        SearchButton.setBackground(new java.awt.Color(102, 102, 255));
        SearchButton.setForeground(new java.awt.Color(255, 255, 255));
        SearchButton.setText("Search");

        SortByComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admission Date", "Sentence Duration", "Prisoner ID", "Name" }));

        SortBasisComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ascending", "Descending" }));

        SortButton.setBackground(new java.awt.Color(0, 102, 102));
        SortButton.setForeground(new java.awt.Color(255, 255, 255));
        SortButton.setText("Sort");

        VisitRequestsButton.setBackground(new java.awt.Color(255, 167, 33));
        VisitRequestsButton.setForeground(new java.awt.Color(255, 255, 255));
        VisitRequestsButton.setText("Visit Requests");
        VisitRequestsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VisitRequestsButtonActionPerformed(evt);
            }
        });

        TrashBinButton.setBackground(new java.awt.Color(25, 9, 80));
        TrashBinButton.setForeground(new java.awt.Color(255, 255, 255));
        TrashBinButton.setText("Trash Bin");
        TrashBinButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TrashBinButtonActionPerformed(evt);
            }
        });

        SortTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "InsertionSort", "SelectionSort", "MergeSort" }));

        AddPrisonerButton.setBackground(new java.awt.Color(51, 153, 0));
        AddPrisonerButton.setForeground(new java.awt.Color(255, 255, 255));
        AddPrisonerButton.setText("+ Add Prisoner");
        AddPrisonerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddPrisonerButtonActionPerformed(evt);
            }
        });

        RefreshButton.setBackground(new java.awt.Color(255, 255, 0));
        RefreshButton.setForeground(new java.awt.Color(51, 51, 51));
        RefreshButton.setText("Refresh");

        javax.swing.GroupLayout OperationalPanelLayout = new javax.swing.GroupLayout(OperationalPanel);
        OperationalPanel.setLayout(OperationalPanelLayout);
        OperationalPanelLayout.setHorizontalGroup(
            OperationalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OperationalPanelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(OperationalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(OperationalPanelLayout.createSequentialGroup()
                        .addComponent(SortByLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(SortByComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SortTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SortBasisComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(OperationalPanelLayout.createSequentialGroup()
                        .addComponent(SearchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SearchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(SearchTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(46, 46, 46)
                .addGroup(OperationalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SearchButton, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                    .addComponent(SortButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(269, 269, 269)
                .addGroup(OperationalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(RefreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(VisitRequestsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(63, 63, 63)
                .addGroup(OperationalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(TrashBinButton, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AddPrisonerButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        OperationalPanelLayout.setVerticalGroup(
            OperationalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OperationalPanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(OperationalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SearchLabel)
                    .addComponent(SearchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SearchTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SearchButton)
                    .addComponent(RefreshButton)
                    .addComponent(AddPrisonerButton))
                .addGap(25, 25, 25)
                .addGroup(OperationalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SortByLabel)
                    .addComponent(SortByComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SortTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SortBasisComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SortButton)
                    .addComponent(VisitRequestsButton)
                    .addComponent(TrashBinButton))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        PrisonerRecordPanel.setBackground(new java.awt.Color(255, 255, 255));

        PrisonerRecordLabel.setText("Prisoner Records");

        PrisonerRecordTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Age", "Gender", "Admission Date", "Crime Type", "Release Date", "Sentence (in Month)", "Location", "Status", "Action"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(PrisonerRecordTable);

        javax.swing.GroupLayout PrisonerRecordPanelLayout = new javax.swing.GroupLayout(PrisonerRecordPanel);
        PrisonerRecordPanel.setLayout(PrisonerRecordPanelLayout);
        PrisonerRecordPanelLayout.setHorizontalGroup(
            PrisonerRecordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PrisonerRecordPanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(PrisonerRecordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PrisonerRecordLabel)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1088, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(908, Short.MAX_VALUE))
        );
        PrisonerRecordPanelLayout.setVerticalGroup(
            PrisonerRecordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PrisonerRecordPanelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(PrisonerRecordLabel)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(223, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout AdminDashboardBodyPanelLayout = new javax.swing.GroupLayout(AdminDashboardBodyPanel);
        AdminDashboardBodyPanel.setLayout(AdminDashboardBodyPanelLayout);
        AdminDashboardBodyPanelLayout.setHorizontalGroup(
            AdminDashboardBodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AdminDashboardBodyPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(AdminDashboardBodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(OperationalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PrisonerRecordPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(95, 95, 95))
        );
        AdminDashboardBodyPanelLayout.setVerticalGroup(
            AdminDashboardBodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminDashboardBodyPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(OperationalPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(PrisonerRecordPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        RecentActivityPanel.setBackground(new java.awt.Color(255, 255, 255));

        ActivityLabel.setText("Recent Activities");

        javax.swing.GroupLayout RecentActivityPanelLayout = new javax.swing.GroupLayout(RecentActivityPanel);
        RecentActivityPanel.setLayout(RecentActivityPanelLayout);
        RecentActivityPanelLayout.setHorizontalGroup(
            RecentActivityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RecentActivityPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(ActivityLabel)
                .addContainerGap(216, Short.MAX_VALUE))
        );
        RecentActivityPanelLayout.setVerticalGroup(
            RecentActivityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RecentActivityPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(ActivityLabel)
                .addContainerGap(768, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout AdminDashboardPanelLayout = new javax.swing.GroupLayout(AdminDashboardPanel);
        AdminDashboardPanel.setLayout(AdminDashboardPanelLayout);
        AdminDashboardPanelLayout.setHorizontalGroup(
            AdminDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminDashboardPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(AdminDashboardBodyPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(RecentActivityPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(AdminDashboardHeaderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        AdminDashboardPanelLayout.setVerticalGroup(
            AdminDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminDashboardPanelLayout.createSequentialGroup()
                .addComponent(AdminDashboardHeaderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(AdminDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AdminDashboardPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(AdminDashboardBodyPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 811, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AdminDashboardPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(RecentActivityPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainPanel.add(AdminDashboardPanel, "card2");

        jPanel1.setBackground(new java.awt.Color(153, 255, 51));

        jLabel2.setFont(new java.awt.Font("Candara", 1, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Family Login Portal");

        jButton3.setBackground(new java.awt.Color(102, 102, 255));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Back to Home");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jButton3)
                .addGap(401, 401, 401)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(510, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jButton3))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel3.setText("Family Portal Access");

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 14)); // NOI18N
        jLabel4.setText("Connect with your loved ones");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Prisoner ID");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Family Code");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        FamilyPortalLoginButton.setBackground(new java.awt.Color(51, 255, 51));
        FamilyPortalLoginButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        FamilyPortalLoginButton.setForeground(new java.awt.Color(255, 255, 255));
        FamilyPortalLoginButton.setText("Access Portal");
        FamilyPortalLoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FamilyPortalLoginButtonActionPerformed(evt);
            }
        });

        jPanel8.setBackground(new java.awt.Color(204, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 255, 51)));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 51, 255));
        jLabel5.setText("How to access");

        jLabel6.setText("- Enter the prisoner id provided by prison");

        jLabel7.setText("- Enter your registered family code ");

        jLabel8.setText("- Contact administrator if you need any help");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(131, 131, 131))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(230, 230, 230))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(121, 121, 121)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(46, 46, 46))
                    .addComponent(FamilyPortalLoginButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(77, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addGap(46, 46, 46)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(FamilyPortalLoginButton)
                .addGap(51, 51, 51))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(396, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(259, 259, 259))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );

        javax.swing.GroupLayout FamilyLoginPanelLayout = new javax.swing.GroupLayout(FamilyLoginPanel);
        FamilyLoginPanel.setLayout(FamilyLoginPanelLayout);
        FamilyLoginPanelLayout.setHorizontalGroup(
            FamilyLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FamilyLoginPanelLayout.createSequentialGroup()
                .addGroup(FamilyLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FamilyLoginPanelLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(FamilyLoginPanelLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        FamilyLoginPanelLayout.setVerticalGroup(
            FamilyLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FamilyLoginPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );

        mainPanel.add(FamilyLoginPanel, "card5");

        HomeHeader.setBackground(new java.awt.Color(51, 51, 255));

        Header.setForeground(new java.awt.Color(255, 255, 255));
        Header.setText("Prison Management System");

        Slogan.setForeground(new java.awt.Color(255, 255, 255));
        Slogan.setText("Nepal Correctional Facilities - Digital Management System");

        javax.swing.GroupLayout HomeHeaderLayout = new javax.swing.GroupLayout(HomeHeader);
        HomeHeader.setLayout(HomeHeaderLayout);
        HomeHeaderLayout.setHorizontalGroup(
            HomeHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomeHeaderLayout.createSequentialGroup()
                .addContainerGap(653, Short.MAX_VALUE)
                .addGroup(HomeHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HomeHeaderLayout.createSequentialGroup()
                        .addComponent(Slogan)
                        .addGap(560, 560, 560))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HomeHeaderLayout.createSequentialGroup()
                        .addComponent(Header, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(636, 636, 636))))
        );
        HomeHeaderLayout.setVerticalGroup(
            HomeHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HomeHeaderLayout.createSequentialGroup()
                .addContainerGap(74, Short.MAX_VALUE)
                .addComponent(Header)
                .addGap(50, 50, 50)
                .addComponent(Slogan)
                .addGap(43, 43, 43))
        );

        HomeBody.setBackground(new java.awt.Color(204, 255, 255));
        HomeBody.setForeground(new java.awt.Color(204, 255, 255));

        AdminLoginButton.setBackground(new java.awt.Color(102, 102, 255));
        AdminLoginButton.setForeground(new java.awt.Color(255, 255, 255));
        AdminLoginButton.setText("Admin Login");
        AdminLoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AdminLoginButtonActionPerformed(evt);
            }
        });

        FamilyLoginButton.setBackground(new java.awt.Color(0, 255, 0));
        FamilyLoginButton.setForeground(new java.awt.Color(255, 255, 255));
        FamilyLoginButton.setText("Family Portal");
        FamilyLoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FamilyLoginButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout HomeBodyLayout = new javax.swing.GroupLayout(HomeBody);
        HomeBody.setLayout(HomeBodyLayout);
        HomeBodyLayout.setHorizontalGroup(
            HomeBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomeBodyLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(AdminLoginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(103, 103, 103)
                .addComponent(FamilyLoginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(543, 543, 543))
        );
        HomeBodyLayout.setVerticalGroup(
            HomeBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomeBodyLayout.createSequentialGroup()
                .addContainerGap(368, Short.MAX_VALUE)
                .addGroup(HomeBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FamilyLoginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AdminLoginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(169, 169, 169))
        );

        javax.swing.GroupLayout HomePanelLayout = new javax.swing.GroupLayout(HomePanel);
        HomePanel.setLayout(HomePanelLayout);
        HomePanelLayout.setHorizontalGroup(
            HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(HomeBody, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(HomeHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        HomePanelLayout.setVerticalGroup(
            HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomePanelLayout.createSequentialGroup()
                .addComponent(HomeHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(HomeBody, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainPanel.add(HomePanel, "card3");

        jPanel4.setBackground(new java.awt.Color(102, 255, 51));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Family Portal Dashboard");

        jButton1.setBackground(new java.awt.Color(102, 102, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Log Out");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jButton1)
                .addGap(383, 383, 383)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(35, 35, 35))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 0)));

        jLabel19.setText("Request a Visit");

        jLabel20.setText("Your name:");

        jLabel21.setText("Relationship:");

        jLabel22.setText("Preferred Date:");

        jLabel23.setText("Purpose");

        jButton2.setText("Submit Request");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 255));
        jLabel1.setText("Request a Visit");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jLabel22)
                .addGap(27, 27, 27)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addGap(221, 221, 221))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(jButton2))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 204, 0)));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(102, 102, 255));
        jLabel12.setText("Prisoner Information");

        jLabel13.setText("Name:");

        jLabel25.setText("<Prisoner Name>");

        jLabel14.setText("Status:");

        jLabel26.setText("<Prisoner status>");

        jLabel15.setText("Admitted:");

        jLabel27.setText("<Prisoner Admisson Date>");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel26))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel15))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jLabel17.setText("Health:");

        jLabel16.setText("Prison Location:");

        jLabel18.setText("Expected Release:");

        jLabel28.setText("<Prisoner Health>");

        jLabel29.setText("<Prisoner Location>");

        jLabel30.setText("<Prisoner Expected Release Date>");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addGap(39, 39, 39)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel29))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel30))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(218, 218, 218))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 0)));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(102, 102, 255));
        jLabel24.setText("Your Visit History");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Visit ID", "Visitor Name", "Relationship", "Visit Date", "Status", "Purpose"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout FamilyDashboardPanelLayout = new javax.swing.GroupLayout(FamilyDashboardPanel);
        FamilyDashboardPanel.setLayout(FamilyDashboardPanelLayout);
        FamilyDashboardPanelLayout.setHorizontalGroup(
            FamilyDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FamilyDashboardPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(FamilyDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        FamilyDashboardPanelLayout.setVerticalGroup(
            FamilyDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FamilyDashboardPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainPanel.add(FamilyDashboardPanel, "card6");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AdminLoginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdminLoginButtonActionPerformed
        showAdminLoginPanel();
    }//GEN-LAST:event_AdminLoginButtonActionPerformed

    private void FamilyLoginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FamilyLoginButtonActionPerformed
       showFamilyLoginPanel();
    }//GEN-LAST:event_FamilyLoginButtonActionPerformed

    private void UsernameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UsernameTextFieldActionPerformed
        // When Enter is pressed in Username field, move to Password field
        PasswordField.requestFocus();
    }//GEN-LAST:event_UsernameTextFieldActionPerformed

    private void AdminLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdminLoginActionPerformed
        // Get credentials
        String username = UsernameTextField.getText().trim();
        String password = new String(PasswordField.getPassword()).trim();
        
        // Validate inputs
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter both username and password",
                "Login Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Simple authentication (username: admin, password: admin123)
        if (username.equals("admin") && password.equals("admin123")) {
            // Successful login
            // Load data to table
            controller.loadPrisonerToTable(PrisonerRecordTable);
            // Setup action buttons in table
            PrisonerDialogHelper.setupTableButtons(PrisonerRecordTable, controller, this);
            // Initialize Recent Activities panel
            updateRecentActivities();
            showAdminDashboardPanel();
            
            JOptionPane.showMessageDialog(this,
                "Welcome, Administrator!",
                "Login Successful",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                "Invalid username or password.\n\n" +
                "Default credentials:\n" +
                "Username: admin\n" +
                "Password: admin123",
                "Login Failed",
                JOptionPane.ERROR_MESSAGE);
            
            // Clear password field for security
            PasswordField.setText("");
        }
    }//GEN-LAST:event_AdminLoginActionPerformed

    private void AdminLogOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdminLogOutButtonActionPerformed
        // Clear any user data and return to home
        UsernameTextField.setText("");
        PasswordField.setText("");
        showHomePanel();
    }//GEN-LAST:event_AdminLogOutButtonActionPerformed

    private void SearchTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchTextFieldActionPerformed
        
    }//GEN-LAST:event_SearchTextFieldActionPerformed

    private void AddPrisonerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddPrisonerButtonActionPerformed
        // Use PrisonerDialogHelper for Add dialog
        PrisonerDialogHelper.showAddDialog(controller, this, PrisonerRecordTable);
    }//GEN-LAST:event_AddPrisonerButtonActionPerformed

    private void SearchTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchTypeComboBoxActionPerformed
        // No action needed - just for display/selection
    }//GEN-LAST:event_SearchTypeComboBoxActionPerformed

    private void VisitRequestsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VisitRequestsButtonActionPerformed
        // Open Visit Requests Dialog
        VisitRequestsDialog dialog = new VisitRequestsDialog(this, controller);
        dialog.setVisible(true);
    }//GEN-LAST:event_VisitRequestsButtonActionPerformed

    private void TrashBinButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TrashBinButtonActionPerformed
        // Open TrashBinDialog to view trash in tabular format
        TrashBinDialog trashDialog = new TrashBinDialog(this, controller, PrisonerRecordTable);
        trashDialog.setVisible(true);
    }//GEN-LAST:event_TrashBinButtonActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // When Enter is pressed in Prisoner ID field, move to Family Code field
        jTextField2.requestFocus();
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void FamilyPortalLoginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FamilyPortalLoginButtonActionPerformed
        // Get input values
        String prisonerIdText = jTextField1.getText().trim();
        String familyCode = jTextField2.getText().trim();
        
        // Check if placeholder text is still present
        if (prisonerIdText.equals("Enter prisoner ID")) {
            prisonerIdText = "";
        }
        if (familyCode.equals("Enter family code")) {
            familyCode = "";
        }
        
        // Validate inputs
        if (prisonerIdText.isEmpty() || familyCode.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter both Prisoner ID and Family Code",
                "Login Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Parse prisoner ID
        int prisonerId;
        try {
            prisonerId = Integer.parseInt(prisonerIdText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Prisoner ID must be a valid number",
                "Invalid Input",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validate credentials
        PrisonerModel prisoner = controller.validateFamilyLogin(prisonerId, familyCode);
        
        if (prisoner != null) {
            // Successful login
            currentFamilyPrisonerId = prisonerId;
            
            // Clear login fields
            jTextField1.setText("");
            jTextField2.setText("");
            
            // Load prisoner details into dashboard
            loadFamilyDashboard(prisoner);
            
            // Show family dashboard
            showFamilyDashboardPanel();
            
            JOptionPane.showMessageDialog(this,
                "Welcome! You are now viewing information for " + prisoner.getName(),
                "Login Successful",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                "Invalid Prisoner ID or Family Code.\n" +
                "Please verify your credentials and try again.\n\n" +
                "Contact the prison administrator if you need assistance.",
                "Login Failed",
                JOptionPane.ERROR_MESSAGE);
            
            // Clear password field for security
            jTextField2.setText("");
        }
    }//GEN-LAST:event_FamilyPortalLoginButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Family Dashboard Logout
        int choice = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to log out?",
            "Confirm Logout",
            JOptionPane.YES_NO_OPTION);
        
        if (choice == JOptionPane.YES_OPTION) {
            currentFamilyPrisonerId = 0;
            clearFamilyDashboard();
            showHomePanel();
            
            JOptionPane.showMessageDialog(this,
                "You have been logged out successfully.",
                "Logged Out",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // Submit Visit Request
        if (currentFamilyPrisonerId == 0) {
            JOptionPane.showMessageDialog(this,
                "Please log in first to submit a visit request.",
                "Not Logged In",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Get form values
        String visitorName = jTextField3.getText().trim();
        String relationship = (String) jComboBox1.getSelectedItem();
        String dateText = jTextField4.getText().trim();
        String purpose = jTextField5.getText().trim();
        
        // Validate inputs
        if (visitorName.isEmpty() || dateText.isEmpty() || purpose.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill in all fields:\n" +
                "- Your Name\n" +
                "- Relationship\n" +
                "- Preferred Date (YYYY-MM-DD)\n" +
                "- Purpose",
                "Incomplete Form",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Parse date
        java.time.LocalDate preferredDate;
        try {
            preferredDate = java.time.LocalDate.parse(dateText);
            
            // Check if date is in the future
            if (preferredDate.isBefore(java.time.LocalDate.now())) {
                JOptionPane.showMessageDialog(this,
                    "Preferred date must be today or in the future.",
                    "Invalid Date",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Invalid date format.\n" +
                "Please use YYYY-MM-DD format (e.g., 2026-02-15)",
                "Date Format Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Get prisoner details
        PrisonerModel prisoner = controller.getPrisonerById(currentFamilyPrisonerId);
        if (prisoner == null) {
            JOptionPane.showMessageDialog(this,
                "Error: Prisoner information not found.",
                "System Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Submit request
        boolean success = controller.addVisitRequest(
            currentFamilyPrisonerId,
            prisoner.getName(),
            visitorName,
            relationship,
            preferredDate,
            purpose
        );
        
        if (success) {
            JOptionPane.showMessageDialog(this,
                "Your visit request has been submitted successfully!\n\n" +
                "Prisoner: " + prisoner.getName() + "\n" +
                "Visitor: " + visitorName + "\n" +
                "Date: " + dateText + "\n\n" +
                "Status: Pending approval\n" +
                "You will be notified once the request is reviewed.",
                "Request Submitted",
                JOptionPane.INFORMATION_MESSAGE);
            
            // Clear form
            jTextField3.setText("");
            jTextField4.setText("");
            jTextField5.setText("");
            jComboBox1.setSelectedIndex(0);
            
            // Refresh visit history table
            loadVisitRequestsForPrisoner(currentFamilyPrisonerId);
        } else {
            JOptionPane.showMessageDialog(this,
                "Failed to submit visit request.\n" +
                "Please try again or contact administrator.",
                "Submission Failed",
                JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed
  
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new MainFrame().setVisible(true));
    }

    
    // Panel visibility methods
    
    public void showHomePanel() {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, HOME_PANEL);
    }

    private void showAdminLoginPanel() {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, ADMIN_LOGIN_PANEL);
    }

    private void showAdminDashboardPanel() {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, ADMIN_DASHBOARD_PANEL);
    }

    /**
     * Show family login panel
     */
    private void showFamilyLoginPanel() {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, FAMILY_LOGIN_PANEL);
    }

    /**
     * Show family dashboard panel with prisoner ID
     */
    public void showFamilyDashboardPanel(int prisonerId) {
        currentFamilyPrisonerId = prisonerId;
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, FAMILY_DASHBOARD_PANEL);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ActivityLabel;
    private javax.swing.JButton AddPrisonerButton;
    private javax.swing.JLabel AdminAcess;
    private javax.swing.JPanel AdminCredentialEntryPanel;
    private javax.swing.JPanel AdminDashboardBodyPanel;
    private javax.swing.JPanel AdminDashboardHeaderPanel;
    private javax.swing.JPanel AdminDashboardPanel;
    private javax.swing.JButton AdminLogOutButton;
    private javax.swing.JButton AdminLogin;
    private javax.swing.JPanel AdminLoginBodyPanel;
    private javax.swing.JButton AdminLoginButton;
    private javax.swing.JPanel AdminLoginHeaderPanel;
    private javax.swing.JPanel AdminLoginPanel;
    private javax.swing.JLabel DashboardTItleLabel;
    private javax.swing.JLabel EnterCredentials;
    private javax.swing.JPanel FamilyDashboardPanel;
    private javax.swing.JButton FamilyLoginButton;
    private javax.swing.JPanel FamilyLoginPanel;
    private javax.swing.JButton FamilyPortalLoginButton;
    private javax.swing.JLabel Header;
    private javax.swing.JPanel HomeBody;
    private javax.swing.JPanel HomeHeader;
    private javax.swing.JPanel HomePanel;
    private javax.swing.JPanel OperationalPanel;
    private javax.swing.JPasswordField PasswordField;
    private javax.swing.JLabel PasswordLabel;
    private javax.swing.JLabel PrisonerRecordLabel;
    private javax.swing.JPanel PrisonerRecordPanel;
    private javax.swing.JTable PrisonerRecordTable;
    private javax.swing.JPanel RecentActivityPanel;
    private javax.swing.JButton RefreshButton;
    private javax.swing.JButton SearchButton;
    private javax.swing.JLabel SearchLabel;
    private javax.swing.JTextField SearchTextField;
    private javax.swing.JComboBox<String> SearchTypeComboBox;
    private javax.swing.JLabel Slogan;
    private javax.swing.JComboBox<String> SortBasisComboBox;
    private javax.swing.JButton SortButton;
    private javax.swing.JComboBox<String> SortByComboBox;
    private javax.swing.JLabel SortByLabel;
    private javax.swing.JComboBox<String> SortTypeComboBox;
    private javax.swing.JLabel TitleLabel;
    private javax.swing.JButton TrashBinButton;
    private javax.swing.JLabel UsernameLabel;
    private javax.swing.JTextField UsernameTextField;
    private javax.swing.JButton VisitRequestsButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JPanel mainPanel;
    // End of variables declaration//GEN-END:variables
}
