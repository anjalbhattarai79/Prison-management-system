package controller;

import java.io.File;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
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
    private Queue<PrisonerModel> recentlyAddedQueue = new LinkedList<>();
    private Stack<PrisonerModel> trashBin = new Stack<>(); // Stack for deleted prisoners
    private Queue<Activity> recentActivities = new LinkedList<>(); // Activity tracking
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
        }
        return result.isSuccess();
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
    public Stack<PrisonerModel> getTrashBin() {
        return trashBin;
    }
    
    /**
     * Log an activity
     */
    private void logActivity(String action, String prisonerName, int prisonerId) {
        Activity activity = new Activity(action, prisonerName, prisonerId);
        recentActivities.offer(activity);
        
        // Keep only last MAX_ACTIVITIES
        while (recentActivities.size() > MAX_ACTIVITIES) {
            recentActivities.poll();
        }
        
        System.out.println("[ACTIVITY LOGGED] " + activity.toString());
    }
    
    /**
     * Get recent activities queue
     * @return Queue of recent activities
     */
    public Queue<Activity> getRecentActivitiesQueue() {
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
        
        // Sample 1 - Active, Good Health
        addPrisoner("Ram Bahadur Thapa", 32, "Male", "Tinkune-15, Kathmandu",
                    "Theft", "Shoplifting from local store", 
                    LocalDate.of(2024, 3, 15), 18, "Central Jail, Kathmandu", 
                    "FAM101", getAbsoluteImagePath("1.jpg"), "Active");
        getPrisonerById(101).setHealthStatus("Good");
        
        // Sample 2 - Active, Fair Health
        addPrisoner("Sita Maya Gurung", 28, "Female", "Lakeside-6, Pokhara, Kaski",
                    "Fraud", "Financial fraud in cooperative society",
                    LocalDate.of(2024, 5, 20), 24, "Pokhara Jail, Kaski",
                    "FAM102", getAbsoluteImagePath("2.jpg"), "Active");
        getPrisonerById(102).setHealthStatus("Fair");
        
        // Sample 3 - Released
        addPrisoner("Bikash Sharma Poudel", 35, "Male", "Dharan-12, Sunsari",
                    "Assault", "Physical assault during dispute",
                    LocalDate.of(2023, 1, 10), 18, "Biratnagar Jail, Morang",
                    "FAM103", getAbsoluteImagePath("3.jpg"), "Released");
        getPrisonerById(103).setHealthStatus("Good");
        
        // Sample 4 - Medical, Poor Health
        addPrisoner("Anita Kumari Rai", 26, "Female", "Birtamod-8, Jhapa",
                    "Embezzlement", "Misappropriation of office funds",
                    LocalDate.of(2024, 1, 8), 36, "Biratnagar Jail, Morang",
                    "FAM104", getAbsoluteImagePath("4.jpg"), "Medical");
        getPrisonerById(104).setHealthStatus("Poor");
        
        // Sample 5 - Active, Critical Health
        addPrisoner("Prakash Tamang", 42, "Male", "Bouddha-7, Kathmandu",
                    "Drug Possession", "Possession of illegal narcotics",
                    LocalDate.of(2023, 8, 22), 48, "Central Jail, Kathmandu",
                    "FAM105", getAbsoluteImagePath("5.jpg"), "Active");
        getPrisonerById(105).setHealthStatus("Critical");
        
        // Sample 6 - Transferred
        addPrisoner("Sunita Devi Chaudhary", 30, "Female", "Nepalgunj-3, Banke",
                    "Forgery", "Document forgery for land registration",
                    LocalDate.of(2024, 2, 14), 20, "Nepalgunj Jail, Banke",
                    "FAM106", getAbsoluteImagePath("default-prisoner.png"), "Transferred");
        getPrisonerById(106).setHealthStatus("Good");
        
        // Sample 7 - Solitary, Fair Health
        addPrisoner("Nirajan Karki Chhetri", 29, "Male", "Chitwan Bazaar-4, Chitwan",
                    "Cyber Crime", "Online fraud and identity theft",
                    LocalDate.of(2024, 6, 5), 28, "Bharatpur Jail, Chitwan",
                    "FAM107", getAbsoluteImagePath("default-prisoner.png"), "Solitary");
        getPrisonerById(107).setHealthStatus("Fair");
        
        // Sample 8 - Parole, Good Health
        addPrisoner("Gita Kumari Adhikari", 38, "Female", "Butwal-11, Rupandehi",
                    "Smuggling", "Smuggling goods across border",
                    LocalDate.of(2023, 6, 18), 30, "Bhairahawa Jail, Rupandehi",
                    "FAM108", getAbsoluteImagePath("default-prisoner.png"), "Parole");
        getPrisonerById(108).setHealthStatus("Good");
        
        // Sample 9 - Active, Fair Health
        addPrisoner("Dinesh Bahadur Magar", 45, "Male", "Hetauda-10, Makwanpur",
                    "Corruption", "Bribery and corruption in public office",
                    LocalDate.of(2024, 4, 12), 60, "Central Jail, Kathmandu",
                    "FAM109", getAbsoluteImagePath("default-prisoner.png"), "Active");
        getPrisonerById(109).setHealthStatus("Fair");
        
        // Sample 10 - Released
        addPrisoner("Krishna Kumari Shrestha", 33, "Female", "Bhaktapur Durbar-9, Bhaktapur",
                    "Robbery", "Armed robbery of jewelry shop",
                    LocalDate.of(2022, 9, 25), 24, "Central Jail, Kathmandu",
                    "FAM110", getAbsoluteImagePath("default-prisoner.png"), "Released");
        getPrisonerById(110).setHealthStatus("Good");
        
        System.out.println("✓ Successfully loaded 10 sample prisoner records");
        System.out.println("  - Names: Nepali local names from various ethnic groups");
        System.out.println("  - Addresses: Nepal cities and districts");
        System.out.println("  - Prisons: Actual Nepal prison locations");
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
