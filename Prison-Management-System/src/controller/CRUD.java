/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.time.LocalDate;
import java.util.LinkedList;
import model.PrisonerModel;

/**
 * CRUD - Contains all Create, Read, Update, Delete operations
 * UI-agnostic business logic layer - no Swing dependencies
 * Returns OperationResult for type-safe success/failure handling
 * Separates business logic from controller, following Single Responsibility Principle
 * 
 * @author Anjal Bhattarai
 */
public class CRUD {
    
    // Maximum number of recent activities to track
    private static final int MAX_RECENT = 5;
    
    /**
     * CREATE - Add a new prisoner to the system
     * Returns OperationResult with success/failure status
     * No UI dependencies - view layer handles user feedback
     * 
     * @param prisonDetails Main prisoner list
     * @param recentlyAddedQueue Queue tracking recent additions
     * @param nextPrisonerId Current next available ID
     * @param name Prisoner name
     * @param age Prisoner age
     * @param gender Gender
     * @param address Address
     * @param crimeType Type of crime
     * @param crimeDescription Crime description
     * @param admissionDate Date of admission
     * @param sentenceDuration Sentence duration in months
     * @param prisonLocation Prison location
     * @param familyCode Family portal access code
     * @param status Prisoner status
     * @return OperationResult with prisoner ID on success, error message on failure
     */
    public static OperationResult<Integer> addPrisoner(LinkedList<PrisonerModel> prisonDetails,
                                       SimpleQueue<PrisonerModel> recentlyAddedQueue,
                                       int nextPrisonerId,
                                       String name, int age, String gender, String address,
                                       String crimeType, String crimeDescription,
                                       LocalDate admissionDate, int sentenceDuration,
                                       String prisonLocation, String familyCode, String photoPath, String status) {
        try {
            // Validate name
            if (name == null || name.trim().isEmpty()) {
                return OperationResult.failure("Prisoner name cannot be empty");
            }
            
            if (!name.trim().matches("^[a-zA-Z\\s'-]+$")) {
                return OperationResult.failure("Name can only contain letters, spaces, hyphens, and apostrophes");
            }
            
            if (name.trim().length() > 100) {
                return OperationResult.failure("Name cannot exceed 100 characters");
            }
            
            if (age <= 0 || age > 120) {
                return OperationResult.failure("Age must be between 1 and 120");
            }
            
            if (gender == null || (!gender.equals("Male") && !gender.equals("Female") && !gender.equals("Other"))) {
                return OperationResult.failure("Gender must be Male, Female, or Other");
            }
            
            if (address == null || address.trim().isEmpty()) {
                return OperationResult.failure("Address cannot be empty");
            }
            if (address.trim().length() > 200) {
                return OperationResult.failure("Address cannot exceed 200 characters");
            }
            
            if (crimeType == null || crimeType.trim().isEmpty()) {
                return OperationResult.failure("Crime type cannot be empty");
            }
            
            if (prisonLocation == null || prisonLocation.trim().isEmpty()) {
                return OperationResult.failure("Prison location cannot be empty");
            }
            
            if (sentenceDuration <= 0) {
                return OperationResult.failure("Sentence duration must be positive");
            }
            
            if (admissionDate.isAfter(LocalDate.now())) {
                return OperationResult.failure("Admission date cannot be in future");
            }
            
            // Check for duplicate names
            for (PrisonerModel p : prisonDetails) {
                if (p.getName().equalsIgnoreCase(name.trim())) {
                    return OperationResult.failure("Prisoner with this name already exists");
                }
            }
            
            // Generate ID
            int prisonerId = getNextAvailableId(prisonDetails, nextPrisonerId);
            
            // Create prisoner object
            PrisonerModel newPrisoner = new PrisonerModel(prisonerId, 
                name.trim(), age, gender, address.trim(), crimeType, 
                crimeDescription, admissionDate, sentenceDuration,
                prisonLocation, familyCode, photoPath, status);
            
            // Add to main list
            prisonDetails.add(newPrisoner);
            
            // Update recent queue
            if (recentlyAddedQueue.size() >= MAX_RECENT) {
                recentlyAddedQueue.dequeue();
            }
            recentlyAddedQueue.enqueue(newPrisoner);
            
            System.out.println("[CRUD] Successfully added prisoner: " + name + " (ID: " + prisonerId + ")");
            return OperationResult.success(prisonerId, 
                "Prisoner added successfully!\nName: " + name + "\nID: " + prisonerId);
            
        } catch (Exception e) {
            System.err.println("[CRUD] Error adding prisoner: " + e.getMessage());
            return OperationResult.failure("Error adding prisoner", e.getMessage());
        }
    }
    
    /**
     * READ - Get prisoner by ID
     */
    public static PrisonerModel getPrisonerById(LinkedList<PrisonerModel> prisonDetails, int id) {
        for (PrisonerModel p : prisonDetails) {
            if (p.getPrisonerId() == id) {
                return p;
            }
        }
        return null;
    }
    
    /**
     * UPDATE - Update existing prisoner information
     * Returns OperationResult with success/failure status
     * No UI dependencies - view layer handles user feedback
     */
    public static OperationResult<Boolean> updatePrisoner(LinkedList<PrisonerModel> prisonDetails,
                                         int prisonerId, String name, int age, String gender,
                                         String address, String crimeType, String crimeDescription,
                                         LocalDate admissionDate, int sentenceDuration,
                                         String prisonLocation, String familyCode, String photoPath) {
        try {
            // Find prisoner
            PrisonerModel prisoner = getPrisonerById(prisonDetails, prisonerId);
            if (prisoner == null) {
                return OperationResult.failure("Prisoner with ID " + prisonerId + " not found");
            }
            
            // Validate
            if (name == null || name.trim().isEmpty()) {
                return OperationResult.failure("Prisoner name cannot be empty");
            }
            
            if (!name.trim().matches("^[a-zA-Z\\s'-]+$")) {
                return OperationResult.failure("Name can only contain letters, spaces, hyphens, and apostrophes");
            }
            
            if (name.trim().length() > 100) {
                return OperationResult.failure("Name cannot exceed 100 characters");
            }
            
            if (age <= 0 || age > 120) {
                return OperationResult.failure("Age must be between 1 and 120");
            }
            
            if (gender == null || (!gender.equals("Male") && !gender.equals("Female") && !gender.equals("Other"))) {
                return OperationResult.failure("Gender must be Male, Female, or Other");
            }
            
            if (address == null || address.trim().isEmpty()) {
                return OperationResult.failure("Address cannot be empty");
            }
            if (address.trim().length() > 200) {
                return OperationResult.failure("Address cannot exceed 200 characters");
            }
            
            if (crimeType == null || crimeType.trim().isEmpty()) {
                return OperationResult.failure("Crime type cannot be empty");
            }
            
            if (prisonLocation == null || prisonLocation.trim().isEmpty()) {
                return OperationResult.failure("Prison location cannot be empty");
            }
            
            if (sentenceDuration <= 0) {
                return OperationResult.failure("Sentence duration must be positive");
            }
            
            if (admissionDate.isAfter(LocalDate.now())) {
                return OperationResult.failure("Admission date cannot be in future");
            }
            
            // Check for duplicate names (excluding current prisoner)
            for (PrisonerModel p : prisonDetails) {
                if (p.getPrisonerId() != prisonerId && 
                    p.getName().equalsIgnoreCase(name.trim())) {
                    return OperationResult.failure("Another prisoner with this name already exists");
                }
            }
            
            // Update fields
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
            prisoner.setPhotoPath(photoPath);
            
            System.out.println("[CRUD] Successfully updated prisoner: " + name + " (ID: " + prisonerId + ")");
            return OperationResult.success(true, 
                "Prisoner updated successfully!\nName: " + name + "\nID: " + prisonerId);
            
        } catch (Exception e) {
            System.err.println("[CRUD] Error updating prisoner: " + e.getMessage());
            return OperationResult.failure("Error updating prisoner", e.getMessage());
        }
    }
    
    /**
     * DELETE - Remove prisoner from system and push to trash bin (Stack)
     * Returns OperationResult with prisoner data on success
     * View layer should confirm deletion before calling this method
     */
    public static OperationResult<PrisonerModel> deletePrisoner(LinkedList<PrisonerModel> prisonDetails, 
                                         SimpleStack<PrisonerModel> trashBin, 
                                         int prisonerId) {
        try {
            PrisonerModel prisoner = getPrisonerById(prisonDetails, prisonerId);
            if (prisoner == null) {
                return OperationResult.failure("Prisoner with ID " + prisonerId + " not found");
            }
            
            // Remove from list
            boolean removed = prisonDetails.remove(prisoner);
            
            if (removed) {
                // Push to trash bin (custom stack)
                TrashBinOperation.pushToTrash(trashBin, prisoner);
                
                System.out.println("[CRUD] Prisoner moved to trash: " + prisoner.getName() + " (ID: " + prisonerId + ")");
                return OperationResult.success(prisoner,
                    "Prisoner moved to Trash Bin!\n\n" +
                    "Name: " + prisoner.getName() + "\n" +
                    "ID: " + prisoner.getPrisonerId() + "\n\n" +
                    "Prisoners in trash: " + trashBin.size() + "\n" +
                    "You can restore using the Restore button.");
            } else {
                System.err.println("[CRUD] Failed to remove prisoner from list");
                return OperationResult.failure("Failed to remove prisoner from list");
            }
            
        } catch (Exception e) {
            System.err.println("[CRUD] Error deleting prisoner: " + e.getMessage());
            e.printStackTrace();
            return OperationResult.failure("Error deleting prisoner", e.getMessage());
        }
    }
    
    /**
     * Helper: Get next available prisoner ID
     */
    public static int getNextAvailableId(LinkedList<PrisonerModel> prisonDetails, int nextPrisonerId) {
        // Find max ID in current list
        int maxId = 0;
        for (PrisonerModel p : prisonDetails) {
            if (p.getPrisonerId() > maxId) {
                maxId = p.getPrisonerId();
            }
        }
        // Return next available ID
        int generatedId = Math.max(maxId + 1, nextPrisonerId);
        System.out.println("Max ID in list: " + maxId + ", Next ID tracker: " + nextPrisonerId + " → Generated: " + generatedId);
        return generatedId;
    }
    
    /**
     * Helper: Get recent activities string for display
     */
    public static String getRecentActivities(SimpleQueue<PrisonerModel> recentlyAddedQueue) {
        StringBuilder activities = new StringBuilder("<html><b>Recent Activities:</b><br>");
        
        if (recentlyAddedQueue.isEmpty()) {
            activities.append("No recent activities<br>");
            // Add some sample activities if queue is empty
            activities.append("• Login: Admin logged in<br>");
            activities.append("• View: Prisoner records loaded<br>");
        } else {
            PrisonerModel[] arr = recentlyAddedQueue.toArray(new PrisonerModel[0]);
            for (PrisonerModel p : arr) {
                activities.append("• Added: ").append(p.getName())
                         .append(" (ID: ").append(p.getPrisonerId()).append(")<br>");
            }
        }
        
        activities.append("</html>");
        return activities.toString();
    }
}
