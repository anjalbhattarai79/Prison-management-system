package controller;

import java.io.File;
import java.time.LocalDate;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Activity;
import model.PrisonerModel;
import model.VisitRequest;

/**
 * PrisonController - Manages prisoner operations
 */
public class PrisonController {
    
    private LinkedList<PrisonerModel> prisonDetails = new LinkedList<>();
    private SimpleQueue recentlyAddedQueue = new SimpleQueue();
    private SimpleStack trashBin = new SimpleStack(); // Custom Stack for deleted prisoners
    private SimpleQueue recentActivities = new SimpleQueue(); // Activity tracking
    private LinkedList<VisitRequest> visitRequests = new LinkedList<>(); // Visit requests
    private static final int MAX_ACTIVITIES = 10; // Maximum activities to track
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
                              String prisonLocation, String familyCode, String photoPath, String status) {
        OperationResult<Integer> result = CRUD.addPrisoner(prisonDetails, recentlyAddedQueue, nextPrisonerId,
                                           name, age, gender, address, crimeType, crimeDescription,
                                           admissionDate, sentenceDuration, prisonLocation, familyCode, photoPath, status);
        
        if (result.isSuccess()) {
            int newId = result.getData();
            // Log activity
            logActivity("ADDED", name, newId);
            nextPrisonerId = newId + 1; // Update next ID for next prisoner
        }
        return result.isSuccess();
    }
    
    /**
     * Get next available prisoner ID (public for UI preview)
     * Delegates to CRUD.getNextAvailableId
     */
    public int getNextAvailableId() {
        return CRUD.getNextAvailableId(prisonDetails, nextPrisonerId);
    }
    
    /**
     * Get recently added prisoners for display
     * Delegates to CRUD.getRecentActivities
     */
    public String getRecentlyAdded() {
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
                                  String prisonLocation, String familyCode, String photoPath) {
        OperationResult<Boolean> result = CRUD.updatePrisoner(prisonDetails, prisonerId, name, age, gender,
                                   address, crimeType, crimeDescription, admissionDate,
                                   sentenceDuration, prisonLocation, familyCode, photoPath);
        if (result.isSuccess()) {
            logActivity("UPDATED", name, prisonerId);
        }
        return result.isSuccess();
    }
    
    /**
     * DELETE - Remove prisoner from system and move to trash bin
     * Delegates to CRUD.deletePrisoner
     * @return true if deletion successful, false otherwise
     */
    public boolean deletePrisoner(int prisonerId) {
        OperationResult<PrisonerModel> result = CRUD.deletePrisoner(prisonDetails, trashBin, prisonerId);
        if (result.isSuccess()) {
            PrisonerModel prisoner = result.getData();
            logActivity("DELETED", prisoner.getName(), prisonerId);
            return true;
        } else {
            // Show overflow or other error details to the user
            JOptionPane.showMessageDialog(null,
                result.getFullErrorMessage(),
                "Delete Failed",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * RESTORE - Restore most recently deleted prisoner from trash bin (Stack pop)
     * Delegates to TrashBinOperation.popFromTrash
     * @return The restored prisoner, or null if trash is empty
     */
    public PrisonerModel restorePrisoner() {
        PrisonerModel restored = TrashBinOperation.popFromTrash(trashBin, prisonDetails);
        if (restored != null) {
            logActivity("RESTORED", restored.getName(), restored.getPrisonerId());
        }
        return restored;
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
        int count = trashBin.size();
        TrashBinOperation.emptyTrash(trashBin);
        if (count > 0) {
            logActivity("EMPTIED TRASH", count + " prisoner(s)", 0);
        }
    }
    
    /**
     * Get the trash bin Stack for viewing
     * @return Stack of deleted prisoners
     */
    public SimpleStack getTrashBin() {
        return trashBin;
    }
    
    /**
     * Log an activity
     */
    private void logActivity(String action, String prisonerName, int prisonerId) {
        Activity activity = new Activity(action, prisonerName, prisonerId);
        recentActivities.enqueue(activity);
        
        // Keep only last MAX_ACTIVITIES
        while (recentActivities.size() > MAX_ACTIVITIES) {
            recentActivities.dequeue();
        }
        
        System.out.println("[ACTIVITY LOGGED] " + activity.toString());
    }
    
    /**
     * Get recent activities queue
     * @return Queue of recent activities
     */
    public SimpleQueue getRecentActivitiesQueue() {
        return recentActivities;
    }
    
    /**
     * Get formatted recent activities string for display
     */
    public String getFormattedActivities() {
        if (recentActivities.isEmpty()) {
            return "<html><b>Recent Activities:</b><br>No recent activities</html>";
        }
        
        StringBuilder sb = new StringBuilder("<html><b>Recent Activities:</b><br>");
        // Convert to array and reverse to show newest first
        Activity[] activities = recentActivities.toArray(new Activity[0]);
        for (int i = activities.length - 1; i >= 0; i--) {
            Activity activity = activities[i];
            String color = getColorForAction(activity.getAction());
            sb.append("<font color='").append(color).append("'>• ");
            sb.append(activity.getAction()).append("</font>: ");
            sb.append(activity.getPrisonerName());
            sb.append(" (ID: ").append(activity.getPrisonerId()).append(") ");
            sb.append("<font color='gray'><i>").append(activity.getFormattedTime()).append("</i></font><br>");
        }
        sb.append("</html>");
        return sb.toString();
    }
    
    /**
     * Get color for activity action
     */
    private String getColorForAction(String action) {
        switch (action) {
            case "ADDED": return "#2ED573"; // Green
            case "UPDATED": return "#3498DB"; // Blue
            case "DELETED": return "#E74C3C"; // Red
            case "RESTORED": return "#F39C12"; // Orange
            case "EMPTIED TRASH": return "#8E44AD"; // Purple
            case "VIEWED": return "#9B59B6"; // Purple
            default: return "#34495E"; // Dark gray
        }
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
     * SORT (explicit algorithm) - Sort by field and order using chosen algorithm
     * @param sortBy Field to sort
     * @param ascending Asc/Desc order
     * @param algorithm One of: InsertionSort, SelectionSort, MergeSort
     */
    public LinkedList<PrisonerModel> sortPrisoners(String sortBy, boolean ascending, String algorithm) {
        return SortOperation.sortPrisoners(prisonDetails, sortBy, ascending, algorithm);
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
     * Helper method to get absolute path for image files
     */
    private String getAbsoluteImagePath(String imageFileName) {
        String workingDir = System.getProperty("user.dir");
        File imageFile = new File(workingDir, "Prison-Management-System/images/" + imageFileName);
        
        // If not found, try without the Prison-Management-System prefix
        if (!imageFile.exists()) {
            imageFile = new File(workingDir, "images/" + imageFileName);
        }
        
        return imageFile.getAbsolutePath();
    }
    
    /**
     * Load sample prisoner data with Nepali context
     * Demonstrates variety in all fields including status and health
     */
    private void loadSampleNepalData() {
        System.out.println("Loading sample prisoner data...");
        
        // Age distribution: Under 25 (15 prisoners), 25-45 (20 prisoners), 45+ (10 prisoners)
        // Gender distribution: ~30 Male, ~15 Female
        
        // Under 25 - Males (10)
        addPrisoner("Aditya Shrestha", 19, "Male", "Tinkune-15, Kathmandu",
                    "Theft", "Shoplifting from local store", 
                    LocalDate.of(2024, 3, 15), 18, "Central Jail, Kathmandu", 
                    "FAM101", getAbsoluteImagePath("1.jpg"), "Active");
        getPrisonerById(101).setHealthStatus("Good");
        
        addPrisoner("Rohan Tamang", 22, "Male", "Boudha-7, Kathmandu",
                    "Drug Possession", "Possession of marijuana",
                    LocalDate.of(2024, 8, 10), 12, "Central Jail, Kathmandu",
                    "FAM102", getAbsoluteImagePath("default-prisoner.png"), "Active");
        getPrisonerById(102).setHealthStatus("Good");
        
        addPrisoner("Suraj Karki", 20, "Male", "Dharan-12, Sunsari",
                    "Assault", "Minor physical assault",
                    LocalDate.of(2024, 9, 5), 6, "Biratnagar Jail, Morang",
                    "FAM103", getAbsoluteImagePath("3.jpg"), "Active");
        getPrisonerById(103).setHealthStatus("Fair");
        
        addPrisoner("Bibek Rai", 23, "Male", "Birtamod-8, Jhapa",
                    "Vandalism", "Property damage",
                    LocalDate.of(2024, 7, 20), 9, "Biratnagar Jail, Morang",
                    "FAM104", getAbsoluteImagePath("default-prisoner.png"), "Active");
        getPrisonerById(104).setHealthStatus("Good");
        
        addPrisoner("Manish Gurung", 21, "Male", "Pokhara-6, Kaski",
                    "Theft", "Vehicle theft",
                    LocalDate.of(2024, 6, 15), 15, "Pokhara Jail, Kaski",
                    "FAM105", getAbsoluteImagePath("default-prisoner.png"), "Active");
        getPrisonerById(105).setHealthStatus("Good");
        
        addPrisoner("Kiran Thapa", 24, "Male", "Chitwan-4, Chitwan",
                    "Fraud", "Credit card fraud",
                    LocalDate.of(2024, 5, 1), 18, "Bharatpur Jail, Chitwan",
                    "FAM106", getAbsoluteImagePath("default-prisoner.png"), "Active");
        getPrisonerById(106).setHealthStatus("Fair");
        
        addPrisoner("Sanjay Magar", 22, "Male", "Butwal-11, Rupandehi",
                    "Trespassing", "Illegal entry",
                    LocalDate.of(2024, 10, 10), 6, "Bhairahawa Jail, Rupandehi",
                    "FAM107", getAbsoluteImagePath("default-prisoner.png"), "Active");
        getPrisonerById(107).setHealthStatus("Good");
        
        addPrisoner("Dipesh Limbu", 20, "Male", "Dhankuta-9, Dhankuta",
                    "Theft", "Shoplifting",
                    LocalDate.of(2024, 4, 25), 12, "Biratnagar Jail, Morang",
                    "FAM108", getAbsoluteImagePath("default-prisoner.png"), "Released");
        getPrisonerById(108).setHealthStatus("Good");
        
        addPrisoner("Ashish Chaudhary", 23, "Male", "Nepalgunj-3, Banke",
                    "Public Disorder", "Public intoxication and disturbance",
                    LocalDate.of(2024, 11, 1), 3, "Nepalgunj Jail, Banke",
                    "FAM109", getAbsoluteImagePath("default-prisoner.png"), "Active");
        getPrisonerById(109).setHealthStatus("Good");
        
        addPrisoner("Prakash Thakuri", 24, "Male", "Hetauda-10, Makwanpur",
                    "Burglary", "Breaking and entering",
                    LocalDate.of(2024, 2, 14), 20, "Central Jail, Kathmandu",
                    "FAM110", getAbsoluteImagePath("default-prisoner.png"), "Active");
        getPrisonerById(110).setHealthStatus("Fair");
        
        // Under 25 - Females (5)
        addPrisoner("Anita Rai", 21, "Female", "Lakeside-6, Pokhara",
                    "Fraud", "Financial fraud",
                    LocalDate.of(2024, 5, 20), 24, "Pokhara Jail, Kaski",
                    "FAM111", getAbsoluteImagePath("2.jpg"), "Active");
        getPrisonerById(111).setHealthStatus("Fair");
        
        addPrisoner("Kritika Adhikari", 23, "Female", "Bhaktapur-9, Bhaktapur",
                    "Theft", "Jewelry theft",
                    LocalDate.of(2024, 6, 5), 15, "Central Jail, Kathmandu",
                    "FAM112", getAbsoluteImagePath("default-prisoner.png"), "Active");
        getPrisonerById(112).setHealthStatus("Good");
        
        addPrisoner("Ritu Sharma", 22, "Female", "Biratnagar-8, Morang",
                    "Embezzlement", "Misappropriation of funds",
                    LocalDate.of(2024, 7, 18), 18, "Biratnagar Jail, Morang",
                    "FAM113", getAbsoluteImagePath("default-prisoner.png"), "Active");
        getPrisonerById(113).setHealthStatus("Good");
        
        addPrisoner("Sunita Karki", 24, "Female", "Butwal-5, Rupandehi",
                    "Forgery", "Document forgery",
                    LocalDate.of(2024, 3, 10), 12, "Bhairahawa Jail, Rupandehi",
                    "FAM114", getAbsoluteImagePath("default-prisoner.png"), "Released");
        getPrisonerById(114).setHealthStatus("Good");
        
        addPrisoner("Sapana Thapa", 20, "Female", "Chitwan-11, Chitwan",
                    "Cyber Crime", "Social media fraud",
                    LocalDate.of(2024, 9, 8), 15, "Bharatpur Jail, Chitwan",
                    "FAM115", getAbsoluteImagePath("default-prisoner.png"), "Active");
        getPrisonerById(115).setHealthStatus("Fair");
        
        // 25-45 Age Group - Males (14)
        addPrisoner("Ram Bahadur Thapa", 32, "Male", "Tinkune-15, Kathmandu",
                    "Theft", "Grand larceny", 
                    LocalDate.of(2024, 1, 10), 24, "Central Jail, Kathmandu", 
                    "FAM116", getAbsoluteImagePath("default-prisoner.png"), "Active");
        getPrisonerById(116).setHealthStatus("Good");
        
        addPrisoner("Prakash Tamang", 42, "Male", "Bouddha-7, Kathmandu",
                    "Drug Trafficking", "Distribution of illegal narcotics",
                    LocalDate.of(2023, 8, 22), 60, "Central Jail, Kathmandu",
                    "FAM117", getAbsoluteImagePath("5.jpg"), "Active");
        getPrisonerById(117).setHealthStatus("Critical");
        
        addPrisoner("Bikash Poudel", 35, "Male", "Dharan-12, Sunsari",
                    "Assault", "Aggravated assault",
                    LocalDate.of(2023, 1, 10), 36, "Biratnagar Jail, Morang",
                    "FAM118", getAbsoluteImagePath("default-prisoner.png"), "Parole");
        getPrisonerById(118).setHealthStatus("Good");
        
        addPrisoner("Nirajan Chhetri", 29, "Male", "Chitwan Bazaar-4, Chitwan",
                    "Cyber Crime", "Hacking and identity theft",
                    LocalDate.of(2024, 6, 5), 36, "Bharatpur Jail, Chitwan",
                    "FAM119", getAbsoluteImagePath("default-prisoner.png"), "Active");
        getPrisonerById(119).setHealthStatus("Fair");
        
        addPrisoner("Dinesh Magar", 38, "Male", "Hetauda-10, Makwanpur",
                    "Corruption", "Bribery and corruption",
                    LocalDate.of(2024, 4, 12), 60, "Central Jail, Kathmandu",
                    "FAM120", getAbsoluteImagePath("default-prisoner.png"), "Active");
        getPrisonerById(120).setHealthStatus("Fair");
        
        addPrisoner("Suresh Rana", 40, "Male", "Pokhara-10, Kaski",
                    "Human Trafficking", "Trafficking of minors",
                    LocalDate.of(2023, 5, 15), 120, "Pokhara Jail, Kaski",
                    "FAM121", getAbsoluteImagePath("default-prisoner.png"), "Active");
        getPrisonerById(121).setHealthStatus("Fair");
        
        addPrisoner("Rajesh Basnet", 34, "Male", "Butwal-7, Rupandehi",
                    "Robbery", "Armed robbery",
                    LocalDate.of(2023, 11, 20), 48, "Bhairahawa Jail, Rupandehi",
                    "FAM122", getAbsoluteImagePath("default-prisoner.png"), "Active");
        getPrisonerById(122).setHealthStatus("Good");
        
        addPrisoner("Gopal Adhikari", 36, "Male", "Biratnagar-5, Morang",
                    "Murder", "Manslaughter during altercation",
                    LocalDate.of(2022, 3, 8), 180, "Biratnagar Jail, Morang",
                    "FAM123", getAbsoluteImagePath("default-prisoner.png"), "Active");
        getPrisonerById(123).setHealthStatus("Poor");
        
        addPrisoner("Naresh Bhattarai", 33, "Male", "Kathmandu-28, Kathmandu",
                    "Extortion", "Threatening and extortion",
                    LocalDate.of(2024, 2, 22), 30, "Central Jail, Kathmandu",
                    "FAM124", getAbsoluteImagePath("default-prisoner.png"), "Active");
        getPrisonerById(124).setHealthStatus("Good");
        
        addPrisoner("Kamal Dahal", 41, "Male", "Dhankuta-3, Dhankuta",
                    "Smuggling", "Cross-border smuggling",
                    LocalDate.of(2023, 12, 5), 48, "Biratnagar Jail, Morang",
                    "FAM125", getAbsoluteImagePath("default-prisoner.png"), "Active");
        getPrisonerById(125).setHealthStatus("Good");
        
        addPrisoner("Ramesh Gurung", 37, "Male", "Chitwan-15, Chitwan",
                    "Kidnapping", "Abduction for ransom",
                    LocalDate.of(2023, 7, 10), 96, "Bharatpur Jail, Chitwan",
                    "FAM126", getAbsoluteImagePath("default-prisoner.png"), "Active");
        getPrisonerById(126).setHealthStatus("Fair");
        
        addPrisoner("Umesh Karki", 30, "Male", "Nepalgunj-8, Banke",
                    "Drug Possession", "Large quantity narcotics possession",
                    LocalDate.of(2024, 8, 15), 36, "Nepalgunj Jail, Banke",
                    "FAM127", getAbsoluteImagePath("default-prisoner.png"), "Active");
        getPrisonerById(127).setHealthStatus("Good");
        
        addPrisoner("Bikram Thapa", 31, "Male", "Lalitpur-3, Lalitpur",
                    "Assault", "Domestic violence",
                    LocalDate.of(2024, 9, 20), 18, "Central Jail, Kathmandu",
                    "FAM128", getAbsoluteImagePath("default-prisoner.png"), "Active");
        getPrisonerById(128).setHealthStatus("Fair");
        
        addPrisoner("Sanjib Rai", 44, "Male", "Jhapa-10, Jhapa",
                    "Fraud", "Insurance fraud",
                    LocalDate.of(2024, 1, 30), 24, "Biratnagar Jail, Morang",
                    "FAM129", getAbsoluteImagePath("default-prisoner.png"), "Active");
        getPrisonerById(129).setHealthStatus("Good");
        
        // 25-45 Age Group - Females (6)
        addPrisoner("Sita Gurung", 28, "Female", "Lakeside-6, Pokhara",
                    "Fraud", "Cooperative society fraud",
                    LocalDate.of(2024, 5, 20), 24, "Pokhara Jail, Kaski",
                    "FAM130", getAbsoluteImagePath("default-prisoner.png"), "Active");
        getPrisonerById(130).setHealthStatus("Fair");
        
        addPrisoner("Sunita Chaudhary", 30, "Female", "Nepalgunj-3, Banke",
                    "Forgery", "Land document forgery",
                    LocalDate.of(2024, 2, 14), 24, "Nepalgunj Jail, Banke",
                    "FAM131", getAbsoluteImagePath("default-prisoner.png"), "Transferred");
        getPrisonerById(131).setHealthStatus("Good");
        
        addPrisoner("Gita Adhikari", 38, "Female", "Butwal-11, Rupandehi",
                    "Smuggling", "Gold smuggling",
                    LocalDate.of(2023, 6, 18), 36, "Bhairahawa Jail, Rupandehi",
                    "FAM132", getAbsoluteImagePath("default-prisoner.png"), "Parole");
        getPrisonerById(132).setHealthStatus("Good");
        
        addPrisoner("Krishna Shrestha", 33, "Female", "Bhaktapur-9, Bhaktapur",
                    "Robbery", "Jewelry shop robbery",
                    LocalDate.of(2022, 9, 25), 48, "Central Jail, Kathmandu",
                    "FAM133", getAbsoluteImagePath("default-prisoner.png"), "Active");
        getPrisonerById(133).setHealthStatus("Good");
        
        addPrisoner("Puja Tamang", 27, "Female", "Kathmandu-16, Kathmandu",
                    "Drug Trafficking", "Heroin trafficking",
                    LocalDate.of(2023, 10, 5), 72, "Central Jail, Kathmandu",
                    "FAM134", getAbsoluteImagePath("default-prisoner.png"), "Active");
        getPrisonerById(134).setHealthStatus("Poor");
        
        addPrisoner("Maya Limbu", 35, "Female", "Dharan-8, Sunsari",
                    "Murder", "Conspiracy to commit murder",
                    LocalDate.of(2021, 4, 12), 180, "Biratnagar Jail, Morang",
                    "FAM135", getAbsoluteImagePath("default-prisoner.png"), "Active");
        getPrisonerById(135).setHealthStatus("Fair");
        
        // 45+ Age Group - Males (7)
        addPrisoner("Shyam Prasad Sharma", 52, "Male", "Kathmandu-20, Kathmandu",
                    "Corruption", "Government official corruption",
                    LocalDate.of(2023, 3, 15), 84, "Central Jail, Kathmandu",
                    "FAM136", getAbsoluteImagePath("default-prisoner.png"), "Active");
        getPrisonerById(136).setHealthStatus("Fair");
        
        addPrisoner("Hari Bahadur KC", 48, "Male", "Pokhara-12, Kaski",
                    "Murder", "Premeditated murder",
                    LocalDate.of(2020, 8, 20), 240, "Pokhara Jail, Kaski",
                    "FAM137", getAbsoluteImagePath("default-prisoner.png"), "Active");
        getPrisonerById(137).setHealthStatus("Poor");
        
        addPrisoner("Mohan Khadka", 55, "Male", "Biratnagar-10, Morang",
                    "Fraud", "Land fraud and forgery",
                    LocalDate.of(2022, 5, 10), 72, "Biratnagar Jail, Morang",
                    "FAM138", getAbsoluteImagePath("default-prisoner.png"), "Active");
        getPrisonerById(138).setHealthStatus("Good");
        
        addPrisoner("Bishnu Dhakal", 50, "Male", "Chitwan-7, Chitwan",
                    "Human Trafficking", "International trafficking ring",
                    LocalDate.of(2021, 11, 25), 180, "Bharatpur Jail, Chitwan",
                    "FAM139", getAbsoluteImagePath("default-prisoner.png"), "Active");
        getPrisonerById(139).setHealthStatus("Fair");
        
        addPrisoner("Ganesh Paudel", 46, "Male", "Butwal-3, Rupandehi",
                    "Extortion", "Organized extortion racket",
                    LocalDate.of(2023, 2, 8), 96, "Bhairahawa Jail, Rupandehi",
                    "FAM140", getAbsoluteImagePath("default-prisoner.png"), "Active");
        getPrisonerById(140).setHealthStatus("Good");
        
        addPrisoner("Keshav Oli", 49, "Male", "Dhankuta-5, Dhankuta",
                    "Assault", "Attempted murder",
                    LocalDate.of(2022, 7, 14), 120, "Biratnagar Jail, Morang",
                    "FAM141", getAbsoluteImagePath("default-prisoner.png"), "Active");
        getPrisonerById(141).setHealthStatus("Fair");
        
        addPrisoner("Mahesh Pandey", 53, "Male", "Nepalgunj-7, Banke",
                    "Smuggling", "Arms smuggling",
                    LocalDate.of(2021, 9, 30), 144, "Nepalgunj Jail, Banke",
                    "FAM142", getAbsoluteImagePath("default-prisoner.png"), "Active");
        getPrisonerById(142).setHealthStatus("Poor");
        
        // 45+ Age Group - Females (3)
        addPrisoner("Radha Devi Joshi", 47, "Female", "Lalitpur-8, Lalitpur",
                    "Murder", "Murder of spouse",
                    LocalDate.of(2022, 1, 18), 180, "Central Jail, Kathmandu",
                    "FAM143", getAbsoluteImagePath("default-prisoner.png"), "Active");
        getPrisonerById(143).setHealthStatus("Good");
        
        addPrisoner("Saraswati Thapa", 51, "Female", "Pokhara-4, Kaski",
                    "Corruption", "Embezzlement of public funds",
                    LocalDate.of(2023, 4, 22), 60, "Pokhara Jail, Kaski",
                    "FAM144", getAbsoluteImagePath("4.jpg"), "Medical");
        getPrisonerById(144).setHealthStatus("Critical");
        
        addPrisoner("Laxmi Devkota", 49, "Female", "Bhaktapur-3, Bhaktapur",
                    "Fraud", "Banking and financial fraud",
                    LocalDate.of(2023, 8, 5), 48, "Central Jail, Kathmandu",
                    "FAM145", getAbsoluteImagePath("default-prisoner.png"), "Active");
        getPrisonerById(145).setHealthStatus("Fair");
        
        System.out.println("✓ Successfully loaded 45 sample prisoner records");
        System.out.println("  - Age Distribution: Under 25 (15), 25-45 (20), 45+ (10)");
        System.out.println("  - Gender Distribution: Male (31), Female (14)");
        System.out.println("  - Status variety: Active, Released, Medical, Transferred, Solitary, Parole");
        System.out.println("  - Health variety: Good, Fair, Poor, Critical");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
    }
    
    // ========== VISIT REQUEST MANAGEMENT ==========
    
    /**
     * Validate family login credentials
     * @param prisonerId - Prisoner ID
     * @param familyCode - Family code
     * @return PrisonerModel if valid, null otherwise
     */
    public PrisonerModel validateFamilyLogin(int prisonerId, String familyCode) {
        PrisonerModel prisoner = getPrisonerById(prisonerId);
        if (prisoner != null && prisoner.getFamilyCode().equals(familyCode)) {
            logActivity("FAMILY LOGIN", prisoner.getName(), prisonerId);
            return prisoner;
        }
        return null;
    }
    
    /**
     * Add a new visit request
     * @return true if successful
     */
    public boolean addVisitRequest(int prisonerId, String prisonerName, String visitorName,
                                   String relationship, LocalDate preferredDate, String purpose) {
        try {
            VisitRequest request = new VisitRequest(prisonerId, prisonerName, visitorName,
                                                    relationship, preferredDate, purpose);
            visitRequests.add(request);
            System.out.println("[VISIT REQUEST] New request #" + request.getRequestId() + 
                             " from " + visitorName + " for prisoner " + prisonerName);
            return true;
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to create visit request: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get all visit requests
     */
    public LinkedList<VisitRequest> getAllVisitRequests() {
        return visitRequests;
    }
    
    /**
     * Get visit requests for a specific prisoner
     */
    public LinkedList<VisitRequest> getVisitRequestsForPrisoner(int prisonerId) {
        LinkedList<VisitRequest> filtered = new LinkedList<>();
        for (VisitRequest request : visitRequests) {
            if (request.getPrisonerId() == prisonerId) {
                filtered.add(request);
            }
        }
        return filtered;
    }
    
    /**
     * Get pending visit requests count
     */
    public int getPendingVisitRequestsCount() {
        int count = 0;
        for (VisitRequest request : visitRequests) {
            if ("Pending".equals(request.getStatus())) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Update visit request status
     */
    public boolean updateVisitRequestStatus(int requestId, String newStatus, String adminNotes) {
        for (VisitRequest request : visitRequests) {
            if (request.getRequestId() == requestId) {
                request.setStatus(newStatus);
                request.setAdminNotes(adminNotes);
                System.out.println("[VISIT REQUEST] Updated request #" + requestId + 
                                 " status to: " + newStatus);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Get visit request by ID
     */
    public VisitRequest getVisitRequestById(int requestId) {
        for (VisitRequest request : visitRequests) {
            if (request.getRequestId() == requestId) {
                return request;
            }
        }
        return null;
    }
    
    /**
     * Load visit requests to table
     */
    public void loadVisitRequestsToTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        for (VisitRequest request : visitRequests) {
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
                "Actions" // Button column
            };
            model.addRow(row);
        }
    }
    }
