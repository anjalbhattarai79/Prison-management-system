/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import java.awt.Color;
import java.awt.Cursor;
import java.time.LocalDate;
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
    
    // Panel names as constants
    public static final String HOME_PANEL = "HOME";
    public static final String ADMIN_LOGIN_PANEL = "ADMIN_LOGIN";
    public static final String ADMIN_DASHBOARD_PANEL = "ADMIN_DASHBOARD";
    public static final String FAMILY_LOGIN_PANEL = "FAMILY_LOGIN";
    public static final String FAMILY_DASHBOARD_PANEL = "FAMILY_DASHBOARD";
    
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
     * Setup custom components like mouse listeners for labels
     */
    private void setupCustomComponents() {
        // Make BackLabel clickable
        BackLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        BackLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BackLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BackLabel.setText("<html><u><- Back to Home</u></html>");
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BackLabel.setText("<- Back to Home");
            }
        });
        
   
        
        
    }     
     
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        AdminLoginPanel = new javax.swing.JPanel();
        AdminLoginHeaderPanel = new javax.swing.JPanel();
        TitleLabel = new javax.swing.JLabel();
        BackLabel = new javax.swing.JLabel();
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
        HomePanel = new javax.swing.JPanel();
        HomeHeader = new javax.swing.JPanel();
        Header = new javax.swing.JLabel();
        Slogan = new javax.swing.JLabel();
        HomeBody = new javax.swing.JPanel();
        AdminLoginButton = new javax.swing.JButton();
        FamilyLoginButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPanel.setLayout(new java.awt.CardLayout());

        AdminLoginHeaderPanel.setBackground(new java.awt.Color(0, 204, 255));

        TitleLabel.setForeground(new java.awt.Color(255, 255, 255));
        TitleLabel.setText("Admin Login");

        BackLabel.setForeground(new java.awt.Color(255, 255, 255));
        BackLabel.setText("<- Back to Home");

        javax.swing.GroupLayout AdminLoginHeaderPanelLayout = new javax.swing.GroupLayout(AdminLoginHeaderPanel);
        AdminLoginHeaderPanel.setLayout(AdminLoginHeaderPanelLayout);
        AdminLoginHeaderPanelLayout.setHorizontalGroup(
            AdminLoginHeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminLoginHeaderPanelLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(BackLabel)
                .addGap(556, 556, 556)
                .addComponent(TitleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        AdminLoginHeaderPanelLayout.setVerticalGroup(
            AdminLoginHeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminLoginHeaderPanelLayout.createSequentialGroup()
                .addGroup(AdminLoginHeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AdminLoginHeaderPanelLayout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(BackLabel))
                    .addGroup(AdminLoginHeaderPanelLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(TitleLabel)))
                .addContainerGap(40, Short.MAX_VALUE))
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AdminLoginBodyPanelLayout.createSequentialGroup()
                .addContainerGap(637, Short.MAX_VALUE)
                .addComponent(AdminCredentialEntryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(572, 572, 572))
        );
        AdminLoginBodyPanelLayout.setVerticalGroup(
            AdminLoginBodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminLoginBodyPanelLayout.createSequentialGroup()
                .addGap(153, 153, 153)
                .addComponent(AdminCredentialEntryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(384, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout AdminLoginPanelLayout = new javax.swing.GroupLayout(AdminLoginPanel);
        AdminLoginPanel.setLayout(AdminLoginPanelLayout);
        AdminLoginPanelLayout.setHorizontalGroup(
            AdminLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(AdminLoginHeaderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(AdminLoginBodyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        AdminLoginPanelLayout.setVerticalGroup(
            AdminLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminLoginPanelLayout.createSequentialGroup()
                .addComponent(AdminLoginHeaderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AdminLoginBodyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1211, Short.MAX_VALUE)
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
                        .addComponent(SortBasisComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(SortButton))
                    .addGroup(OperationalPanelLayout.createSequentialGroup()
                        .addComponent(SearchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SearchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(SearchTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(SearchButton)))
                .addGap(160, 160, 160)
                .addGroup(OperationalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(VisitRequestsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(AddPrisonerButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(OperationalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RefreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TrashBinButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 266, Short.MAX_VALUE))
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
                    .addComponent(AddPrisonerButton)
                    .addComponent(RefreshButton))
                .addGroup(OperationalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(OperationalPanelLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(OperationalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(SortByLabel)
                            .addComponent(SortByComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SortTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SortBasisComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SortButton)))
                    .addGroup(OperationalPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(OperationalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(VisitRequestsButton)
                            .addComponent(TrashBinButton))))
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1067, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(72, Short.MAX_VALUE))
        );
        PrisonerRecordPanelLayout.setVerticalGroup(
            PrisonerRecordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PrisonerRecordPanelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(PrisonerRecordLabel)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(368, Short.MAX_VALUE))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        RecentActivityPanelLayout.setVerticalGroup(
            RecentActivityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RecentActivityPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(ActivityLabel)
                .addContainerGap(843, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout AdminDashboardPanelLayout = new javax.swing.GroupLayout(AdminDashboardPanel);
        AdminDashboardPanel.setLayout(AdminDashboardPanelLayout);
        AdminDashboardPanelLayout.setHorizontalGroup(
            AdminDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminDashboardPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AdminDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(AdminDashboardHeaderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(AdminDashboardPanelLayout.createSequentialGroup()
                        .addComponent(AdminDashboardBodyPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(RecentActivityPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        AdminDashboardPanelLayout.setVerticalGroup(
            AdminDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminDashboardPanelLayout.createSequentialGroup()
                .addComponent(AdminDashboardHeaderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(AdminDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AdminDashboardPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(AdminDashboardBodyPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AdminDashboardPanelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(RecentActivityPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        mainPanel.add(AdminDashboardPanel, "card2");

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
            .addGroup(HomeHeaderLayout.createSequentialGroup()
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HomeBodyLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(HomeBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(FamilyLoginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AdminLoginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(669, 669, 669))
        );
        HomeBodyLayout.setVerticalGroup(
            HomeBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomeBodyLayout.createSequentialGroup()
                .addContainerGap(357, Short.MAX_VALUE)
                .addComponent(AdminLoginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(FamilyLoginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(332, 332, 332))
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
                .addContainerGap(15, Short.MAX_VALUE))
        );
        HomePanelLayout.setVerticalGroup(
            HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomePanelLayout.createSequentialGroup()
                .addComponent(HomeHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(HomeBody, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainPanel.add(HomePanel, "card3");

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
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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
        // TODO add your handling code here:
    }//GEN-LAST:event_UsernameTextFieldActionPerformed

    private void AdminLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdminLoginActionPerformed
        // For now, just navigate to dashboard (authentication will be added later)
        // Load data to table
        controller.loadPrisonerToTable(PrisonerRecordTable);
        // Setup action buttons in table
        PrisonerDialogHelper.setupTableButtons(PrisonerRecordTable, controller, this);
        showAdminDashboardPanel();
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
        JOptionPane.showMessageDialog(this,
            "Visit Requests feature coming soon!",
            "Feature Not Available",
            JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_SearchTypeComboBoxActionPerformed

    private void VisitRequestsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VisitRequestsButtonActionPerformed
        JOptionPane.showMessageDialog(this,
            "Visit Requests feature coming soon!",
            "Feature Not Available",
            JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_VisitRequestsButtonActionPerformed

    private void TrashBinButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TrashBinButtonActionPerformed
        JOptionPane.showMessageDialog(this,
            "Undo/Stack functionality will be implemented here!",
            "Feature Coming Soon",
            JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_TrashBinButtonActionPerformed
  
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

    /**
     * Handle BackLabel click to return to home
     */
    private void BackLabelMouseClicked(java.awt.event.MouseEvent evt) {
        showHomePanel();
    }
    
    // Panel visibility methods
    
    public void showHomePanel() {
        HomePanel.setVisible(true);
        AdminLoginPanel.setVisible(false);
        AdminDashboardPanel.setVisible(false);
        // Family panels will be added via NetBeans Form Editor
    }

    private void showAdminLoginPanel() {
        HomePanel.setVisible(false);
        AdminLoginPanel.setVisible(true);
        AdminDashboardPanel.setVisible(false);
    }

    private void showAdminDashboardPanel() {
        HomePanel.setVisible(false);
        AdminLoginPanel.setVisible(false);
        AdminDashboardPanel.setVisible(true);
    }

    /**
     * Show family login panel - will be implemented after adding FamilyLoginPanel via Form Editor
     */
    private void showFamilyLoginPanel() {
        // TODO: Hide other panels and show FamilyLoginPanel
        // FamilyLoginPanel.setVisible(true);
    }

    /**
     * Show family dashboard panel with prisoner ID - will be implemented after adding via Form Editor
     */
    public void showFamilyDashboardPanel(int prisonerId) {
        currentFamilyPrisonerId = prisonerId;
        // TODO: Hide other panels and show FamilyDashboardPanel
        // FamilyDashboardPanel.setVisible(true);
        // Update labels with prisoner info
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
    private javax.swing.JLabel BackLabel;
    private javax.swing.JLabel DashboardTItleLabel;
    private javax.swing.JLabel EnterCredentials;
    private javax.swing.JButton FamilyLoginButton;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainPanel;
    // End of variables declaration//GEN-END:variables
}
