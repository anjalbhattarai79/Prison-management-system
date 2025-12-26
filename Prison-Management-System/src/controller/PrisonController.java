package controller;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.PrisonerModel;

/**
 * PrisonController - Manages prisoner operations
 */
public class PrisonController {
    
    private LinkedList<PrisonerModel> prisonDetails = new LinkedList<>();
    private Queue<PrisonerModel> recentlyAddedQueue = new LinkedList<>();
    private Stack<PrisonerModel> trashBin = new Stack<>(); // Stack for deleted prisoners
    private int nextPrisonerId = 101; // Start at 101
    
    // Constructor - load sample data
    public PrisonController() {
        loadSampleNepalData();
    }
    
    /**
     * Add a new prisoner (CREATE operation)
     * Delegates to CRUD.addPrisoner
     */
    public boolean addPrisoner(String name, int age, String gender, String address,
                              String crimeType, String crimeDescription,
                              LocalDate admissionDate, int sentenceDuration,
                              String prisonLocation, String familyCode, String status) {
        Object[] result = CRUD.addPrisoner(prisonDetails, recentlyAddedQueue, nextPrisonerId,
                                           name, age, gender, address, crimeType, crimeDescription,
                                           admissionDate, sentenceDuration, prisonLocation, familyCode, status);
        
        boolean success = (Boolean) result[0];
        if (success) {
            nextPrisonerId = (Integer) result[1]; // Update next ID
        }
        return success;
    }
    
    /**
     * Get next available prisoner ID (public for UI preview)
     * Delegates to CRUD.getNextAvailableId
     */
    public int getNextAvailableId() {
        return CRUD.getNextAvailableId(prisonDetails, nextPrisonerId);
    }
    
    /**
     * Get recent activities for display
     * Delegates to CRUD.getRecentActivities
     */
    public String getRecentActivities() {
        return CRUD.getRecentActivities(recentlyAddedQueue);
    }
    
    /**
     * Get prisoner by ID
     * Delegates to CRUD.getPrisonerById
     */
    public PrisonerModel getPrisonerById(int id) {
        return CRUD.getPrisonerById(prisonDetails, id);
    }
    
    /**
     * Get all prisoners
     */
    public LinkedList<PrisonerModel> getAllPrisoners() {
        return prisonDetails;
    }
    
    /**
     * UPDATE - Update existing prisoner information
     * Delegates to CRUD.updatePrisoner
     * @return true if update successful, false otherwise
     */
    public boolean updatePrisoner(int prisonerId, String name, int age, String gender,
                                  String address, String crimeType, String crimeDescription,
                                  LocalDate admissionDate, int sentenceDuration,
                                  String prisonLocation, String familyCode) {
        return CRUD.updatePrisoner(prisonDetails, prisonerId, name, age, gender,
                                   address, crimeType, crimeDescription, admissionDate,
                                   sentenceDuration, prisonLocation, familyCode);
    }
    
    /**
     * DELETE - Remove prisoner from system and move to trash bin
     * Delegates to CRUD.deletePrisoner
     * @return true if deletion successful, false otherwise
     */
    public boolean deletePrisoner(int prisonerId) {
        return CRUD.deletePrisoner(prisonDetails, trashBin, prisonerId);
    }
    
    /**
     * RESTORE - Restore most recently deleted prisoner from trash bin (Stack pop)
     * Delegates to TrashBinOperation.popFromTrash
     * @return The restored prisoner, or null if trash is empty
     */
    public PrisonerModel restorePrisoner() {
        return TrashBinOperation.popFromTrash(trashBin, prisonDetails);
    }
    
    /**
     * View trash bin contents
     * Delegates to TrashBinOperation.viewTrashContents
     * @return HTML formatted string of trash contents
     */
    public String getTrashContents() {
        return TrashBinOperation.viewTrashContents(trashBin);
    }
    
    /**
     * Get number of prisoners in trash
     * @return trash bin size
     */
    public int getTrashSize() {
        return TrashBinOperation.getTrashSize(trashBin);
    }
    
    /**
     * Empty trash bin permanently
     * Delegates to TrashBinOperation.emptyTrash
     */
    public void emptyTrash() {
        TrashBinOperation.emptyTrash(trashBin);
    }
    
    /**
     * SEARCH - Search prisoners by different criteria
     * @param searchType: "ID", "Name", "Crime Type", "Location", "Status"
     * @param searchTerm: search keyword
     * @return filtered list of prisoners
     */
    /**
     * Search for prisoners - delegates to SearchOperation
     * @param searchType - Type from ComboBox
     * @param searchTerm - The search query
     * @return LinkedList of matching prisoners
     */
    public LinkedList<PrisonerModel> searchPrisoners(String searchType, String searchTerm) {
        return SearchOperation.searchPrisoners(prisonDetails, searchType, searchTerm);
    }
    
    /**
     * SORT - Sort prisoners by different criteria
     * @param sortBy: "Name", "Age", "ID", "Admission Date", "Release Date"
     * @param ascending: true for ascending, false for descending
     * @return sorted list of prisoners
     */
    public LinkedList<PrisonerModel> sortPrisoners(String sortBy, boolean ascending) {
        return SortOperation.sortPrisoners(prisonDetails, sortBy, ascending);
    }
    
    /**
     * Load filtered/sorted prisoner list to table
     */
    public void loadPrisonerListToTable(JTable prisonerTable, LinkedList<PrisonerModel> prisoners) {
        DefaultTableModel model = (DefaultTableModel) prisonerTable.getModel();
        model.setRowCount(0); // clear previous rows

        for (PrisonerModel prisoner : prisoners) {
            Object[] row = {
                prisoner.getPrisonerId(),
                prisoner.getName(),
                prisoner.getAge(),
                prisoner.getGender(),
                prisoner.getAdmissionDate(),
                prisoner.getCrimeType(),
                prisoner.getReleaseDate(),
                prisoner.getSentenceDuration(),
                prisoner.getPrisonLocation(),
                prisoner.getStatus(),
                "View/Edit" // Actions column
            };
            model.addRow(row);
        }
    }
    
    /**
     * Get prisoner count
     */
    public int getPrisonerCount() {
        return prisonDetails.size();
    }
    
    /**
     * Check if prisoner exists
     */
    public boolean prisonerExists(int prisonerId) {
        return getPrisonerById(prisonerId) != null;
    }
    
    // Your existing methods remain the same...
    public void prepareInitialData() {
        // Sample data already loaded in constructor via loadSampleNepalData()
        // This method kept for backwards compatibility but does nothing
        System.out.println("prepareInitialData() called - data already loaded in constructor");
    }
    
    public void loadPrisonerToTable(JTable prisonerTable) {
        DefaultTableModel model = (DefaultTableModel) prisonerTable.getModel();
        model.setRowCount(0); // clear previous rows

        for (PrisonerModel prisoner : prisonDetails) {
            Object[] row = {
                prisoner.getPrisonerId(),
                prisoner.getName(),
                prisoner.getAge(),
                prisoner.getGender(),
                prisoner.getAdmissionDate(),
                prisoner.getCrimeType(),
                prisoner.getReleaseDate(),
                prisoner.getSentenceDuration(),
                prisoner.getPrisonLocation(),
                prisoner.getStatus(),
                "View/Edit" // Actions column
            };
            model.addRow(row);
        }
    }
    
    /**
     * Load sample prisoner data with Nepali context
     * Demonstrates variety in all fields including status and health
     */
    private void loadSampleNepalData() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║          LOADING SAMPLE NEPAL PRISONER DATA                ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        
        // Sample 1 - Active, Good Health
        addPrisoner("Ram Bahadur Thapa", 32, "Male", "Tinkune-15, Kathmandu",
                    "Theft", "Shoplifting from local store", 
                    LocalDate.of(2024, 3, 15), 18, "Central Jail, Kathmandu", 
                    "FAM101", "Active");
        getPrisonerById(101).setHealthStatus("Good");
        
        // Sample 2 - Active, Fair Health
        addPrisoner("Sita Maya Gurung", 28, "Female", "Lakeside-6, Pokhara, Kaski",
                    "Fraud", "Financial fraud in cooperative society",
                    LocalDate.of(2024, 5, 20), 24, "Pokhara Jail, Kaski",
                    "FAM102", "Active");
        getPrisonerById(102).setHealthStatus("Fair");
        
        // Sample 3 - Released
        addPrisoner("Bikash Sharma Poudel", 35, "Male", "Dharan-12, Sunsari",
                    "Assault", "Physical assault during dispute",
                    LocalDate.of(2023, 1, 10), 18, "Biratnagar Jail, Morang",
                    "FAM103", "Released");
        getPrisonerById(103).setHealthStatus("Good");
        
        // Sample 4 - Medical, Poor Health
        addPrisoner("Anita Kumari Rai", 26, "Female", "Birtamod-8, Jhapa",
                    "Embezzlement", "Misappropriation of office funds",
                    LocalDate.of(2024, 1, 8), 36, "Biratnagar Jail, Morang",
                    "FAM104", "Medical");
        getPrisonerById(104).setHealthStatus("Poor");
        
        // Sample 5 - Active, Critical Health
        addPrisoner("Prakash Tamang", 42, "Male", "Bouddha-7, Kathmandu",
                    "Drug Possession", "Possession of illegal narcotics",
                    LocalDate.of(2023, 8, 22), 48, "Central Jail, Kathmandu",
                    "FAM105", "Active");
        getPrisonerById(105).setHealthStatus("Critical");
        
        // Sample 6 - Transferred
        addPrisoner("Sunita Devi Chaudhary", 30, "Female", "Nepalgunj-3, Banke",
                    "Forgery", "Document forgery for land registration",
                    LocalDate.of(2024, 2, 14), 20, "Nepalgunj Jail, Banke",
                    "FAM106", "Transferred");
        getPrisonerById(106).setHealthStatus("Good");
        
        // Sample 7 - Solitary, Fair Health
        addPrisoner("Nirajan Karki Chhetri", 29, "Male", "Chitwan Bazaar-4, Chitwan",
                    "Cyber Crime", "Online fraud and identity theft",
                    LocalDate.of(2024, 6, 5), 28, "Bharatpur Jail, Chitwan",
                    "FAM107", "Solitary");
        getPrisonerById(107).setHealthStatus("Fair");
        
        // Sample 8 - Parole, Good Health
        addPrisoner("Gita Kumari Adhikari", 38, "Female", "Butwal-11, Rupandehi",
                    "Smuggling", "Smuggling goods across border",
                    LocalDate.of(2023, 6, 18), 30, "Bhairahawa Jail, Rupandehi",
                    "FAM108", "Parole");
        getPrisonerById(108).setHealthStatus("Good");
        
        // Sample 9 - Active, Fair Health
        addPrisoner("Dinesh Bahadur Magar", 45, "Male", "Hetauda-10, Makwanpur",
                    "Corruption", "Bribery and corruption in public office",
                    LocalDate.of(2024, 4, 12), 60, "Central Jail, Kathmandu",
                    "FAM109", "Active");
        getPrisonerById(109).setHealthStatus("Fair");
        
        // Sample 10 - Released
        addPrisoner("Krishna Kumari Shrestha", 33, "Female", "Bhaktapur Durbar-9, Bhaktapur",
                    "Robbery", "Armed robbery of jewelry shop",
                    LocalDate.of(2022, 9, 25), 24, "Central Jail, Kathmandu",
                    "FAM110", "Released");
        getPrisonerById(110).setHealthStatus("Good");
        
        System.out.println("✓ Successfully loaded 10 sample prisoner records");
        System.out.println("  - Names: Nepali local names from various ethnic groups");
        System.out.println("  - Addresses: Nepal cities and districts");
        System.out.println("  - Prisons: Actual Nepal prison locations");
        System.out.println("  - Status variety: Active, Released, Medical, Transferred, Solitary, Parole");
        System.out.println("  - Health variety: Good, Fair, Poor, Critical");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
    }
}