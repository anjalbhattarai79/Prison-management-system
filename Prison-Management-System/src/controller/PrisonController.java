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
    private int nextPrisonerId = 106; // Start after your sample data
    
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
        prisonDetails.clear();
        
        // Add sample prisoners
        prisonDetails.add(new PrisonerModel(101, "John Doe", 35, "Male",
                "123 Main St", "Robbery", "Bank robbery with weapons",
                LocalDate.of(2023, 5, 15), 60, "Central Prison", "FAM12345"));
        
        prisonDetails.add(new PrisonerModel(102, "Jane Smith", 28, "Female",
                "456 Oak Ave", "Fraud", "Credit card fraud",
                LocalDate.of(2022, 8, 20), 48, "Women's Prison", "FAM67890"));
        
        prisonDetails.add(new PrisonerModel(103, "Robert Johnson", 42, "Male",
                "789 Pine Rd", "Assault", "Bar fight resulting in injury",
                LocalDate.of(2024, 1, 10), 36, "County Jail", "FAM11223"));
        
        prisonDetails.add(new PrisonerModel(104, "Michael Brown", 31, "Male",
                "101 Maple St", "Drug Possession", "Possession of illegal substances",
                LocalDate.of(2023, 11, 5), 24, "Central Prison", "FAM44556"));
        
        prisonDetails.add(new PrisonerModel(105, "Sarah Wilson", 26, "Female",
                "202 Cedar Ln", "Theft", "Shoplifting at department store",
                LocalDate.of(2024, 3, 18), 12, "Women's Prison", "FAM77889"));
        
        // Initialize next ID
        nextPrisonerId = 106;
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
}