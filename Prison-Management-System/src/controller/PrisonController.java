package controller;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Queue;
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
    private int nextPrisonerId = 106; // Start after your sample data
    
    // Maximum number of recent activities to show
    private static final int MAX_RECENT = 5;
    
    /**
     * Add a new prisoner (CREATE operation)
     * Validates input and updates recently added queue
     */
    public boolean addPrisoner(String name, int age, String gender, String address,
                              String crimeType, String crimeDescription,
                              LocalDate admissionDate, int sentenceDuration,
                              String prisonLocation, String familyCode, String status) {
        try {
            // Validation
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Prisoner name cannot be empty");
            }
            if (age <= 0 || age > 120) {
                throw new IllegalArgumentException("Age must be between 1 and 120");
            }
            if (sentenceDuration <= 0) {
                throw new IllegalArgumentException("Sentence duration must be positive");
            }
            if (admissionDate.isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("Admission date cannot be in future");
            }
            
            // Check for duplicate names (case-insensitive)
            for (PrisonerModel p : prisonDetails) {
                if (p.getName().equalsIgnoreCase(name.trim())) {
                    throw new IllegalArgumentException("Prisoner with this name already exists");
                }
            }
            
            // Generate unique ID
            int prisonerId = getNextAvailableId();
            
            // Create new prisoner with manual status
            PrisonerModel newPrisoner = new PrisonerModel(prisonerId, 
                name.trim(), age, gender, address.trim(), crimeType, 
                crimeDescription, admissionDate, sentenceDuration,
                prisonLocation, familyCode, status);
            
            // Add to main list
            prisonDetails.add(newPrisoner);
            
            // Update recently added queue (maintain max 5)
            if (recentlyAddedQueue.size() >= MAX_RECENT) {
                recentlyAddedQueue.poll(); // Remove oldest
            }
            recentlyAddedQueue.offer(newPrisoner); // Add newest
            
            // Update next ID
            nextPrisonerId = prisonerId + 1;
            
            // Log to console
            System.out.println("Added prisoner: " + name + " (ID: " + prisonerId + ")");
            
            return true;
            
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, 
                "Validation Error: " + e.getMessage(), 
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
            throw e;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error adding prisoner: " + e.getMessage(), 
                "System Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Get next available prisoner ID (public for UI preview)
     */
    public int getNextAvailableId() {
        // Find max ID in current list
        int maxId = 0;
        for (PrisonerModel p : prisonDetails) {
            if (p.getPrisonerId() > maxId) {
                maxId = p.getPrisonerId();
            }
        }
        // Return next available ID
        return Math.max(maxId + 1, nextPrisonerId);
    }
    
    /**
     * Get recent activities for display
     */
    public String getRecentActivities() {
        StringBuilder activities = new StringBuilder("<html><b>Recent Activities:</b><br>");
        
        if (recentlyAddedQueue.isEmpty()) {
            activities.append("No recent activities<br>");
        } else {
            for (PrisonerModel p : recentlyAddedQueue) {
                activities.append("• Added: ").append(p.getName())
                         .append(" (ID: ").append(p.getPrisonerId()).append(")<br>");
            }
        }
        
        // Add some sample activities if queue is empty
        if (recentlyAddedQueue.isEmpty()) {
            activities.append("• Login: Admin logged in<br>");
            activities.append("• View: Prisoner records loaded<br>");
        }
        
        activities.append("</html>");
        return activities.toString();
    }
    
    /**
     * Get prisoner by ID
     */
    public PrisonerModel getPrisonerById(int id) {
        for (PrisonerModel p : prisonDetails) {
            if (p.getPrisonerId() == id) {
                return p;
            }
        }
        return null;
    }
    
    /**
     * Get all prisoners
     */
    public LinkedList<PrisonerModel> getAllPrisoners() {
        return prisonDetails;
    }
    
    /**
     * UPDATE - Update existing prisoner information
     * @return true if update successful, false otherwise
     */
    public boolean updatePrisoner(int prisonerId, String name, int age, String gender,
                                  String address, String crimeType, String crimeDescription,
                                  LocalDate admissionDate, int sentenceDuration,
                                  String prisonLocation, String familyCode) {
        try {
            // Find prisoner
            PrisonerModel prisoner = getPrisonerById(prisonerId);
            if (prisoner == null) {
                throw new IllegalArgumentException("Prisoner with ID " + prisonerId + " not found");
            }
            
            // Validation
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Prisoner name cannot be empty");
            }
            if (age <= 0 || age > 120) {
                throw new IllegalArgumentException("Age must be between 1 and 120");
            }
            if (sentenceDuration <= 0) {
                throw new IllegalArgumentException("Sentence duration must be positive");
            }
            if (admissionDate.isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("Admission date cannot be in future");
            }
            
            // Check for duplicate names (excluding current prisoner)
            for (PrisonerModel p : prisonDetails) {
                if (p.getPrisonerId() != prisonerId && 
                    p.getName().equalsIgnoreCase(name.trim())) {
                    throw new IllegalArgumentException("Another prisoner with this name already exists");
                }
            }
            
            // Update prisoner details
            prisoner.setName(name.trim());
            prisoner.setAge(age);
            prisoner.setGender(gender);
            prisoner.setAddress(address.trim());
            prisoner.setCrimeType(crimeType);
            prisoner.setCrimeDescription(crimeDescription);
            prisoner.setAdmissionDate(admissionDate);
            prisoner.setSentenceDuration(sentenceDuration);
            prisoner.setPrisonLocation(prisonLocation);
            prisoner.setFamilyCode(familyCode);
            
            System.out.println("Updated prisoner: " + name + " (ID: " + prisonerId + ")");
            return true;
            
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, 
                "Update Error: " + e.getMessage(), 
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
            throw e;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error updating prisoner: " + e.getMessage(), 
                "System Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * DELETE - Remove prisoner from system
     * @return true if deletion successful, false otherwise
     */
    public boolean deletePrisoner(int prisonerId) {
        try {
            PrisonerModel prisoner = getPrisonerById(prisonerId);
            if (prisoner == null) {
                throw new IllegalArgumentException("Prisoner with ID " + prisonerId + " not found");
            }
            
            // Confirm deletion
            int confirm = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to delete prisoner:\n" +
                "ID: " + prisoner.getPrisonerId() + "\n" +
                "Name: " + prisoner.getName() + "\n" +
                "Crime: " + prisoner.getCrimeType() + "\n\n" +
                "This action cannot be undone!",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            
            if (confirm != JOptionPane.YES_OPTION) {
                return false;
            }
            
            // Remove from list
            boolean removed = prisonDetails.remove(prisoner);
            
            if (removed) {
                System.out.println("Deleted prisoner: " + prisoner.getName() + " (ID: " + prisonerId + ")");
                JOptionPane.showMessageDialog(null,
                    "Prisoner deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
            return removed;
            
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, 
                "Delete Error: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error deleting prisoner: " + e.getMessage(), 
                "System Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
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