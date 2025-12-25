/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Queue;
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
                                       String prisonLocation, String familyCode, String status) {
        try {
            System.out.println("\n╔════════════════════════════════════════════════════════════╗");
            System.out.println("║              CREATE OPERATION - ADD PRISONER               ║");
            System.out.println("╚════════════════════════════════════════════════════════════╝");
            System.out.println("Attempting to add: " + name);
            
            // ========== VALIDATION PHASE ==========
            System.out.println("\n--- Validation Phase ---");
            
            if (name == null || name.trim().isEmpty()) {
                System.err.println("[VALIDATION FAILED] Name is empty");
                throw new IllegalArgumentException("Prisoner name cannot be empty");
            }
            System.out.println("✓ Name validation passed: " + name.trim());
            
            if (age <= 0 || age > 120) {
                System.err.println("[VALIDATION FAILED] Age out of range: " + age);
                throw new IllegalArgumentException("Age must be between 1 and 120");
            }
            System.out.println("✓ Age validation passed: " + age);
            
            if (sentenceDuration <= 0) {
                System.err.println("[VALIDATION FAILED] Invalid sentence duration: " + sentenceDuration);
                throw new IllegalArgumentException("Sentence duration must be positive");
            }
            System.out.println("✓ Sentence duration validation passed: " + sentenceDuration + " months");
            
            if (admissionDate.isAfter(LocalDate.now())) {
                System.err.println("[VALIDATION FAILED] Admission date in future: " + admissionDate);
                throw new IllegalArgumentException("Admission date cannot be in future");
            }
            System.out.println("✓ Admission date validation passed: " + admissionDate);
            
            // Check for duplicate names
            System.out.println("\n--- Duplicate Check ---");
            for (PrisonerModel p : prisonDetails) {
                if (p.getName().equalsIgnoreCase(name.trim())) {
                    System.err.println("[DUPLICATE FOUND] Prisoner already exists: " + p.getName() + " (ID: " + p.getPrisonerId() + ")");
                    throw new IllegalArgumentException("Prisoner with this name already exists");
                }
            }
            System.out.println("✓ No duplicates found");
            
            // ========== ID GENERATION ==========
            System.out.println("\n--- ID Generation ---");
            int prisonerId = getNextAvailableId(prisonDetails, nextPrisonerId);
            System.out.println("Generated Prisoner ID: " + prisonerId);
            
            // ========== OBJECT CREATION ==========
            System.out.println("\n--- Creating Prisoner Object ---");
            PrisonerModel newPrisoner = new PrisonerModel(prisonerId, 
                name.trim(), age, gender, address.trim(), crimeType, 
                crimeDescription, admissionDate, sentenceDuration,
                prisonLocation, familyCode, status);
            System.out.println("✓ Prisoner object created");
            
            // ========== ADD TO MAIN LIST ==========
            System.out.println("\n--- Adding to Main List ---");
            prisonDetails.add(newPrisoner);
            System.out.println("✓ Added to LinkedList (New size: " + prisonDetails.size() + ")");
            
            // ========== UPDATE RECENT QUEUE ==========
            System.out.println("\n--- Updating Recent Activities Queue ---");
            if (recentlyAddedQueue.size() >= MAX_RECENT) {
                PrisonerModel removed = recentlyAddedQueue.poll();
                System.out.println("  Queue full, removed oldest: " + removed.getName());
            }
            recentlyAddedQueue.offer(newPrisoner);
            System.out.println("✓ Added to recent queue (Current size: " + recentlyAddedQueue.size() + ")");
            
            // ========== SUCCESS ==========
            System.out.println("\n╔════════════════════════════════════════════════════════════╗");
            System.out.println("║                 ADD OPERATION SUCCESSFUL                   ║");
            System.out.println("╚════════════════════════════════════════════════════════════╝");
            System.out.println("Prisoner: " + name + " (ID: " + prisonerId + ")");
            System.out.println("Status: " + status);
            System.out.println("Total prisoners in system: " + prisonDetails.size());
            
            return new Object[]{true, prisonerId + 1}; // Return success and new nextId
            
        } catch (IllegalArgumentException e) {
            System.err.println("\n[ADD OPERATION FAILED] " + e.getMessage());
            JOptionPane.showMessageDialog(null, 
                "Validation Error: " + e.getMessage(), 
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
            throw e;
        } catch (Exception e) {
            System.err.println("\n[SYSTEM ERROR] " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error adding prisoner: " + e.getMessage(), 
                "System Error", 
                JOptionPane.ERROR_MESSAGE);
            return new Object[]{false, nextPrisonerId}; // Return failure, keep same nextId
        }
    }
    
    /**
     * READ - Get prisoner by ID
     */
    public static PrisonerModel getPrisonerById(LinkedList<PrisonerModel> prisonDetails, int id) {
        System.out.println("\n[READ] Searching for prisoner with ID: " + id);
        for (PrisonerModel p : prisonDetails) {
            if (p.getPrisonerId() == id) {
                System.out.println("✓ Found: " + p.getName());
                return p;
            }
        }
        System.out.println("✗ Prisoner not found");
        return null;
    }
    
    /**
     * UPDATE - Update existing prisoner information
     */
    public static boolean updatePrisoner(LinkedList<PrisonerModel> prisonDetails,
                                         int prisonerId, String name, int age, String gender,
                                         String address, String crimeType, String crimeDescription,
                                         LocalDate admissionDate, int sentenceDuration,
                                         String prisonLocation, String familyCode) {
        try {
            System.out.println("\n╔════════════════════════════════════════════════════════════╗");
            System.out.println("║             UPDATE OPERATION - EDIT PRISONER               ║");
            System.out.println("╚════════════════════════════════════════════════════════════╝");
            System.out.println("Target Prisoner ID: " + prisonerId);
            
            // Find prisoner
            PrisonerModel prisoner = getPrisonerById(prisonDetails, prisonerId);
            if (prisoner == null) {
                System.err.println("[UPDATE FAILED] Prisoner not found");
                throw new IllegalArgumentException("Prisoner with ID " + prisonerId + " not found");
            }
            
            System.out.println("Found prisoner: " + prisoner.getName());
            
            // ========== VALIDATION PHASE ==========
            System.out.println("\n--- Validation Phase ---");
            
            if (name == null || name.trim().isEmpty()) {
                System.err.println("[VALIDATION FAILED] Name is empty");
                throw new IllegalArgumentException("Prisoner name cannot be empty");
            }
            System.out.println("✓ Name validation passed: " + name.trim());
            
            if (age <= 0 || age > 120) {
                System.err.println("[VALIDATION FAILED] Age out of range: " + age);
                throw new IllegalArgumentException("Age must be between 1 and 120");
            }
            System.out.println("✓ Age validation passed: " + age);
            
            if (sentenceDuration <= 0) {
                System.err.println("[VALIDATION FAILED] Invalid sentence duration: " + sentenceDuration);
                throw new IllegalArgumentException("Sentence duration must be positive");
            }
            System.out.println("✓ Sentence duration validation passed: " + sentenceDuration + " months");
            
            if (admissionDate.isAfter(LocalDate.now())) {
                System.err.println("[VALIDATION FAILED] Admission date in future: " + admissionDate);
                throw new IllegalArgumentException("Admission date cannot be in future");
            }
            System.out.println("✓ Admission date validation passed: " + admissionDate);
            
            // Check for duplicate names (excluding current prisoner)
            System.out.println("\n--- Duplicate Check ---");
            for (PrisonerModel p : prisonDetails) {
                if (p.getPrisonerId() != prisonerId && 
                    p.getName().equalsIgnoreCase(name.trim())) {
                    System.err.println("[DUPLICATE FOUND] Name conflict with ID: " + p.getPrisonerId());
                    throw new IllegalArgumentException("Another prisoner with this name already exists");
                }
            }
            System.out.println("✓ No name conflicts");
            
            // ========== UPDATE FIELDS ==========
            System.out.println("\n--- Updating Fields ---");
            System.out.println("Before: " + prisoner.getName() + ", Age: " + prisoner.getAge() + ", Sentence: " + prisoner.getSentenceDuration() + " months");
            
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
            
            System.out.println("After:  " + prisoner.getName() + ", Age: " + prisoner.getAge() + ", Sentence: " + prisoner.getSentenceDuration() + " months");
            
            System.out.println("\n╔════════════════════════════════════════════════════════════╗");
            System.out.println("║               UPDATE OPERATION SUCCESSFUL                  ║");
            System.out.println("╚════════════════════════════════════════════════════════════╝");
            System.out.println("Updated: " + name + " (ID: " + prisonerId + ")");
            
            return true;
            
        } catch (IllegalArgumentException e) {
            System.err.println("\n[UPDATE OPERATION FAILED] " + e.getMessage());
            JOptionPane.showMessageDialog(null, 
                "Update Error: " + e.getMessage(), 
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
            throw e;
        } catch (Exception e) {
            System.err.println("\n[SYSTEM ERROR] " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error updating prisoner: " + e.getMessage(), 
                "System Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * DELETE - Remove prisoner from system
     */
    public static boolean deletePrisoner(LinkedList<PrisonerModel> prisonDetails, int prisonerId) {
        try {
            System.out.println("\n╔════════════════════════════════════════════════════════════╗");
            System.out.println("║            DELETE OPERATION - REMOVE PRISONER              ║");
            System.out.println("╚════════════════════════════════════════════════════════════╝");
            System.out.println("Target Prisoner ID: " + prisonerId);
            
            PrisonerModel prisoner = getPrisonerById(prisonDetails, prisonerId);
            if (prisoner == null) {
                System.err.println("[DELETE FAILED] Prisoner not found");
                throw new IllegalArgumentException("Prisoner with ID " + prisonerId + " not found");
            }
            
            System.out.println("Found prisoner: " + prisoner.getName());
            System.out.println("Crime: " + prisoner.getCrimeType());
            
            // Confirm deletion
            System.out.println("\n--- Requesting User Confirmation ---");
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
                System.out.println("✗ User cancelled deletion");
                return false;
            }
            
            System.out.println("✓ User confirmed deletion");
            
            // Remove from list
            System.out.println("\n--- Removing from LinkedList ---");
            int sizeBefore = prisonDetails.size();
            boolean removed = prisonDetails.remove(prisoner);
            int sizeAfter = prisonDetails.size();
            
            if (removed) {
                System.out.println("✓ Successfully removed from list");
                System.out.println("List size: " + sizeBefore + " → " + sizeAfter);
                
                System.out.println("\n╔════════════════════════════════════════════════════════════╗");
                System.out.println("║               DELETE OPERATION SUCCESSFUL                  ║");
                System.out.println("╚════════════════════════════════════════════════════════════╝");
                System.out.println("Deleted: " + prisoner.getName() + " (ID: " + prisonerId + ")");
                System.out.println("Remaining prisoners: " + sizeAfter);
                
                JOptionPane.showMessageDialog(null,
                    "Prisoner deleted successfully!",
                    "Success",
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
