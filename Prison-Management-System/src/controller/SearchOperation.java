package controller;

import java.util.LinkedList;
import javax.swing.JOptionPane;
import model.PrisonerModel;

/**
 * SearchOperation - Implements search algorithms for prisoner data
 * Demonstrates Binary Search and Linear Search concepts
 * @author Anjal Bhattarai
 */
public class SearchOperation {
    
    /**
     * Binary Search for Prisoner by ID
     * Requires: Data sorted by ID (ascending order)
     * Time Complexity: O(log n)
     * @param prisonDetails - LinkedList of all prisoners
     * @param targetId - The prisoner ID to search for
     * @return PrisonerModel if found, null otherwise
     */
    public static PrisonerModel binarySearchById(LinkedList<PrisonerModel> prisonDetails, int targetId) {
        // Convert LinkedList to array for index-based access
        PrisonerModel[] prisoners = prisonDetails.toArray(new PrisonerModel[0]);
        
        int left = 0;
        int right = prisoners.length - 1;
        int comparisons = 0;
        
        System.out.println("=== Binary Search Started ===");
        System.out.println("Searching for ID: " + targetId);
        System.out.println("Array size: " + prisoners.length);
        
        while (left <= right) {
            comparisons++;
            int mid = left + (right - left) / 2; // Prevent overflow
            int midId = prisoners[mid].getPrisonerId();
            
            System.out.println("Comparison #" + comparisons + ": Checking index " + mid + " (ID: " + midId + ")");
            
            if (midId == targetId) {
                System.out.println("✓ Found at index " + mid + " after " + comparisons + " comparisons");
                return prisoners[mid];
            }
            
            if (midId < targetId) {
                System.out.println("  → Target is greater, searching right half");
                left = mid + 1;
            } else {
                System.out.println("  → Target is smaller, searching left half");
                right = mid - 1;
            }
        }
        
        System.out.println("✗ Not found after " + comparisons + " comparisons");
        return null;
    }
    
    /**
     * Linear Search for Prisoner by Name or Crime Type
     * Works on unsorted data, supports partial matches
     * Time Complexity: O(n)
     * @param prisonDetails - LinkedList of all prisoners
     * @param searchTerm - The term to search for (case-insensitive, partial match)
     * @return LinkedList of matching prisoners
     */
    public static LinkedList<PrisonerModel> linearSearchByNameOrCrime(LinkedList<PrisonerModel> prisonDetails, String searchTerm) {
        LinkedList<PrisonerModel> results = new LinkedList<>();
        String term = searchTerm.trim().toLowerCase();
        int comparisons = 0;
        
        System.out.println("=== Linear Search Started ===");
        System.out.println("Searching for: \"" + searchTerm + "\"");
        System.out.println("List size: " + prisonDetails.size());
        
        // Sequential search through entire list
        for (PrisonerModel prisoner : prisonDetails) {
            comparisons++;
            boolean nameMatch = prisoner.getName().toLowerCase().contains(term);
            boolean crimeMatch = prisoner.getCrimeType().toLowerCase().contains(term);
            
            System.out.println("Comparison #" + comparisons + ": ID=" + prisoner.getPrisonerId() + 
                             ", Name=\"" + prisoner.getName() + "\", Crime=\"" + prisoner.getCrimeType() + "\"");
            
            if (nameMatch || crimeMatch) {
                System.out.println("  ✓ Match found!");
                results.add(prisoner);
            }
        }
        
        System.out.println("=== Search Complete ===");
        System.out.println("Total comparisons: " + comparisons);
        System.out.println("Matches found: " + results.size());
        
        return results;
    }
    
    /**
     * Main search method - routes to appropriate search algorithm
     * Handles all search logic, validation, and user feedback
     * @param prisonDetails - LinkedList of all prisoners
     * @param searchType - Type from ComboBox ("Name/Crime [Linear Search]" or "ID [ Binary Search ]")
     * @param searchTerm - The search query
     * @return LinkedList of matching prisoners
     */
    public static LinkedList<PrisonerModel> searchPrisoners(LinkedList<PrisonerModel> prisonDetails, 
                                                            String searchType, String searchTerm) {
        LinkedList<PrisonerModel> results = new LinkedList<>();
        
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                "Please enter a search term",
                "Search Error",
                JOptionPane.WARNING_MESSAGE);
            return results;
        }
        
        try {
            // Determine which search algorithm to use based on combo box selection
            if (searchType.contains("Binary Search")) {
                // Binary Search for ID
                int searchId = Integer.parseInt(searchTerm.trim());
                PrisonerModel found = binarySearchById(prisonDetails, searchId);
                if (found != null) {
                    results.add(found);
                }
                
                if (results.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                        "No prisoner found with ID: " + searchId,
                        "Search Result",
                        JOptionPane.INFORMATION_MESSAGE);
                }
                
            } else if (searchType.contains("Linear Search")) {
                // Linear Search for Name/Crime
                results = linearSearchByNameOrCrime(prisonDetails, searchTerm);
                
                if (results.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                        "No prisoners found matching: \"" + searchTerm + "\"",
                        "Search Result",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                "For ID search, please enter a numeric value",
                "Invalid Input",
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Error during search: " + e.getMessage(),
                "Search Error",
                JOptionPane.ERROR_MESSAGE);
        }
        
        return results;
    }
}
