/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import javax.swing.JOptionPane;
import model.PrisonerModel;

/**
 * CRUD - Contains all Create, Read, Update, Delete operations
 * Separates business logic from controller, following Single Responsibility Principle
 * Demonstrates clean architecture and separation of concerns
 * 
 * @author Anjal Bhattarai
 */
public class CRUD {
    
    // Maximum number of recent activities to track
    private static final int MAX_RECENT = 5;
    
    /**
     * CREATE - Add a new prisoner to the system
     * Includes comprehensive validation and queue management
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
     * @return Array containing [success (Boolean), newNextId (Integer)]
     */
    public static Object[] addPrisoner(LinkedList<PrisonerModel> prisonDetails,
                                       Queue<PrisonerModel> recentlyAddedQueue,
                                       int nextPrisonerId,
                                       String name, int age, String gender, String address,
                                       String crimeType, String crimeDescription,
                                       LocalDate admissionDate, int sentenceDuration,
                                       String prisonLocation, String familyCode, String photoPath, String status) {
        try {
            // Validate name
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Prisoner name cannot be empty");
            }
            
            if (!name.trim().matches("^[a-zA-Z\\s'-]+$")) {
                throw new IllegalArgumentException("Name can only contain letters, spaces, hyphens, and apostrophes");
            }
            
            if (name.trim().length() > 100) {
                throw new IllegalArgumentException("Name cannot exceed 100 characters");
            }
            
            if (age <= 0 || age > 120) {
                throw new IllegalArgumentException("Age must be between 1 and 120");
            }
            
            if (gender == null || (!gender.equals("Male") && !gender.equals("Female") && !gender.equals("Other"))) {
                throw new IllegalArgumentException("Gender must be Male, Female, or Other");
            }
            
            if (address == null || address.trim().isEmpty()) {
                throw new IllegalArgumentException("Address cannot be empty");
            }
            if (address.trim().length() > 200) {
                throw new IllegalArgumentException("Address cannot exceed 200 characters");
            }
            
            if (crimeType == null || crimeType.trim().isEmpty()) {
                throw new IllegalArgumentException("Crime type cannot be empty");
            }
            
            if (prisonLocation == null || prisonLocation.trim().isEmpty()) {
                throw new IllegalArgumentException("Prison location cannot be empty");
            }
            
            if (sentenceDuration <= 0) {
                throw new IllegalArgumentException("Sentence duration must be positive");
            }
            
            if (admissionDate.isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("Admission date cannot be in future");
            }
            
            // Check for duplicate names
            for (PrisonerModel p : prisonDetails) {
                if (p.getName().equalsIgnoreCase(name.trim())) {
                    throw new IllegalArgumentException("Prisoner with this name already exists");
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
                recentlyAddedQueue.poll();
            }
            recentlyAddedQueue.offer(newPrisoner);
            
            return new Object[]{true, prisonerId + 1};
            
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
            return new Object[]{false, nextPrisonerId};
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
     */
    public static boolean updatePrisoner(LinkedList<PrisonerModel> prisonDetails,
                                         int prisonerId, String name, int age, String gender,
                                         String address, String crimeType, String crimeDescription,
                                         LocalDate admissionDate, int sentenceDuration,
                                         String prisonLocation, String familyCode, String photoPath) {
        try {
            // Find prisoner
            PrisonerModel prisoner = getPrisonerById(prisonDetails, prisonerId);
            if (prisoner == null) {
                throw new IllegalArgumentException("Prisoner with ID " + prisonerId + " not found");
            }
            
            // Validate
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Prisoner name cannot be empty");
            }
            
            if (!name.trim().matches("^[a-zA-Z\\s'-]+$")) {
                throw new IllegalArgumentException("Name can only contain letters, spaces, hyphens, and apostrophes");
            }
            
            if (name.trim().length() > 100) {
                throw new IllegalArgumentException("Name cannot exceed 100 characters");
            }
            
            if (age <= 0 || age > 120) {
                throw new IllegalArgumentException("Age must be between 1 and 120");
            }
            
            if (gender == null || (!gender.equals("Male") && !gender.equals("Female") && !gender.equals("Other"))) {
                throw new IllegalArgumentException("Gender must be Male, Female, or Other");
            }
            
            if (address == null || address.trim().isEmpty()) {
                throw new IllegalArgumentException("Address cannot be empty");
            }
            if (address.trim().length() > 200) {
                throw new IllegalArgumentException("Address cannot exceed 200 characters");
            }
            
            if (crimeType == null || crimeType.trim().isEmpty()) {
                throw new IllegalArgumentException("Crime type cannot be empty");
            }
            
            if (prisonLocation == null || prisonLocation.trim().isEmpty()) {
                throw new IllegalArgumentException("Prison location cannot be empty");
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
     * DELETE - Remove prisoner from system and push to trash bin (Stack)
     */
    public static boolean deletePrisoner(LinkedList<PrisonerModel> prisonDetails, 
                                         Stack<PrisonerModel> trashBin, 
                                         int prisonerId) {
        try {
            PrisonerModel prisoner = getPrisonerById(prisonDetails, prisonerId);
            if (prisoner == null) {
                throw new IllegalArgumentException("Prisoner with ID " + prisonerId + " not found");
            }
            
            // Confirm deletion
            int confirm = JOptionPane.showConfirmDialog(null,
                "Delete prisoner and move to Trash Bin?\n\n" +
                "ID: " + prisoner.getPrisonerId() + "\n" +
                "Name: " + prisoner.getName() + "\n" +
                "Crime: " + prisoner.getCrimeType() + "\n\n" +
                "Prisoner will be moved to Trash (Stack).\n" +
                "You can restore them later using the Restore feature.",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            
            if (confirm != JOptionPane.YES_OPTION) {
                return false;
            }
            
            // Remove from list
            boolean removed = prisonDetails.remove(prisoner);
            
            if (removed) {
                // Push to trash bin (Stack)
                TrashBinOperation.pushToTrash(trashBin, prisoner);
                
                JOptionPane.showMessageDialog(null,
                    "Prisoner moved to Trash Bin!\n\n" +
                    "Name: " + prisoner.getName() + "\n" +
                    "ID: " + prisoner.getPrisonerId() + "\n\n" +
                    "Prisoners in trash: " + trashBin.size() + "\n" +
                    "You can restore using the Restore button.",
                    "Moved to Trash",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                System.err.println("✗ Failed to remove from list");
            }
            
            return removed;
            
        } catch (IllegalArgumentException e) {
            System.err.println("\n[DELETE OPERATION FAILED] " + e.getMessage());
            JOptionPane.showMessageDialog(null, 
                "Delete Error: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (Exception e) {
            System.err.println("\n[SYSTEM ERROR] " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error deleting prisoner: " + e.getMessage(), 
                "System Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
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
    public static String getRecentActivities(Queue<PrisonerModel> recentlyAddedQueue) {
        StringBuilder activities = new StringBuilder("<html><b>Recent Activities:</b><br>");
        
        if (recentlyAddedQueue.isEmpty()) {
            activities.append("No recent activities<br>");
            // Add some sample activities if queue is empty
            activities.append("• Login: Admin logged in<br>");
            activities.append("• View: Prisoner records loaded<br>");
        } else {
            for (PrisonerModel p : recentlyAddedQueue) {
                activities.append("• Added: ").append(p.getName())
                         .append(" (ID: ").append(p.getPrisonerId()).append(")<br>");
            }
        }
        
        activities.append("</html>");
        return activities.toString();
    }
}
