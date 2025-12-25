/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.LinkedList;
import javax.swing.JOptionPane;
import model.PrisonerModel;

/**
 * SortOperation class - Contains all sorting algorithm implementations
 * Demonstrates various sorting algorithms with detailed console logging
 * to show understanding of how sorting works
 * 
 * @author Anjal Bhattarai
 */
public class SortOperation {
    
    /**
     * Bubble Sort Algorithm
     * Time Complexity: O(n²)
     * Space Complexity: O(1)
     * Stable: Yes
     * 
     * Repeatedly steps through the list, compares adjacent elements and swaps them if they are in wrong order.
     * The pass through the list is repeated until the list is sorted.
     */
    private static <T extends Comparable<T>> void bubbleSort(LinkedList<PrisonerModel> list, 
            java.util.function.Function<PrisonerModel, T> keyExtractor, boolean ascending) {
        
        int n = list.size();
        System.out.println("\n=== BUBBLE SORT STARTED ===");
        System.out.println("Initial list size: " + n);
        
        int totalComparisons = 0;
        int totalSwaps = 0;
        
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            System.out.println("\n--- Pass " + (i + 1) + " ---");
            
            for (int j = 0; j < n - i - 1; j++) {
                PrisonerModel current = list.get(j);
                PrisonerModel next = list.get(j + 1);
                
                T currentKey = keyExtractor.apply(current);
                T nextKey = keyExtractor.apply(next);
                
                totalComparisons++;
                int comparison = currentKey.compareTo(nextKey);
                
                // Determine if swap is needed based on sort order
                boolean shouldSwap = ascending ? comparison > 0 : comparison < 0;
                
                if (shouldSwap) {
                    // Swap elements
                    list.set(j, next);
                    list.set(j + 1, current);
                    swapped = true;
                    totalSwaps++;
                    System.out.println("  Swap: Position " + j + " (" + currentKey + ") <-> Position " + (j + 1) + " (" + nextKey + ")");
                }
            }
            
            if (!swapped) {
                System.out.println("  No swaps in this pass - List is sorted!");
                break;
            }
        }
        
        System.out.println("\n=== BUBBLE SORT COMPLETED ===");
        System.out.println("Total Comparisons: " + totalComparisons);
        System.out.println("Total Swaps: " + totalSwaps);
    }
    
    /**
     * Insertion Sort Algorithm
     * Time Complexity: O(n²)
     * Space Complexity: O(1)
     * Stable: Yes
     * 
     * Builds the final sorted list one item at a time.
     * Takes each element and inserts it into its correct position in the sorted portion.
     */
    private static <T extends Comparable<T>> void insertionSort(LinkedList<PrisonerModel> list, 
            java.util.function.Function<PrisonerModel, T> keyExtractor, boolean ascending) {
        
        int n = list.size();
        System.out.println("\n=== INSERTION SORT STARTED ===");
        System.out.println("Initial list size: " + n);
        
        int totalComparisons = 0;
        int totalInsertions = 0;
        
        for (int i = 1; i < n; i++) {
            PrisonerModel key = list.get(i);
            T keyValue = keyExtractor.apply(key);
            int j = i - 1;
            
            System.out.println("\n--- Inserting element at position " + i + " (Key: " + keyValue + ") ---");
            
            // Move elements greater than key one position ahead
            while (j >= 0) {
                PrisonerModel compared = list.get(j);
                T comparedValue = keyExtractor.apply(compared);
                
                totalComparisons++;
                int comparison = keyValue.compareTo(comparedValue);
                
                boolean shouldMove = ascending ? comparison < 0 : comparison > 0;
                
                if (shouldMove) {
                    list.set(j + 1, compared);
                    System.out.println("  Shifting: " + comparedValue + " from position " + j + " to " + (j + 1));
                    j--;
                } else {
                    break;
                }
            }
            
            // Insert key at correct position
            if (j + 1 != i) {
                list.set(j + 1, key);
                totalInsertions++;
                System.out.println("  Inserted: " + keyValue + " at position " + (j + 1));
            } else {
                System.out.println("  Element already in correct position");
            }
        }
        
        System.out.println("\n=== INSERTION SORT COMPLETED ===");
        System.out.println("Total Comparisons: " + totalComparisons);
        System.out.println("Total Insertions: " + totalInsertions);
    }
    
    /**
     * Main sorting method - Entry point for all sorting operations
     * Delegates to appropriate sorting algorithm based on list size and configuration
     * 
     * @param prisonDetails The list of prisoners to sort
     * @param sortBy The field to sort by (Name, Prisoner ID, Admission Date, Sentence Duration)
     * @param ascending True for ascending order, false for descending
     * @return A new sorted LinkedList
     */
    public static LinkedList<PrisonerModel> sortPrisoners(LinkedList<PrisonerModel> prisonDetails, 
            String sortBy, boolean ascending) {
        
        // Validation
        if (prisonDetails == null || prisonDetails.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                "No prisoner data available to sort.",
                "Sort Error",
                JOptionPane.WARNING_MESSAGE);
            return new LinkedList<>();
        }
        
        // Create a copy to avoid modifying original list
        LinkedList<PrisonerModel> sorted = new LinkedList<>(prisonDetails);
        
        try {
            System.out.println("\n╔════════════════════════════════════════════════════════════╗");
            System.out.println("║           SORTING PRISONERS - ALGORITHM DEMONSTRATION       ║");
            System.out.println("╚════════════════════════════════════════════════════════════╝");
            System.out.println("Sort Field: " + sortBy);
            System.out.println("Sort Order: " + (ascending ? "Ascending" : "Descending"));
            System.out.println("Number of Records: " + sorted.size());
            
            // Choose sorting algorithm based on list size
            // For demonstration purposes: use Bubble Sort for smaller lists, Insertion Sort for larger
            boolean useBubbleSort = sorted.size() <= 10;
            
            System.out.println("Selected Algorithm: " + (useBubbleSort ? "Bubble Sort" : "Insertion Sort"));
            System.out.println("Reason: " + (useBubbleSort 
                ? "Small dataset (≤10 records) - Bubble Sort suitable for demonstration" 
                : "Larger dataset (>10 records) - Insertion Sort more efficient"));
            
            // Determine the key extractor based on sortBy field
            java.util.function.Function<PrisonerModel, ? extends Comparable> keyExtractor;
            
            switch (sortBy) {
                case "Name":
                    keyExtractor = p -> p.getName();
                    break;
                    
                case "Prisoner ID":
                    keyExtractor = p -> p.getPrisonerId();
                    break;
                    
                case "Admission Date":
                    keyExtractor = p -> p.getAdmissionDate();
                    break;
                    
                case "Sentence Duration":
                    keyExtractor = p -> {
                        try {
                            // Extract numeric value from sentence (e.g., "5 years" -> 5)
                            String sentence = p.getSentenceDuration();
                            if (sentence == null || sentence.isEmpty()) {
                                return 0;
                            }
                            // Split and parse the first number
                            String[] parts = sentence.trim().split("\\s+");
                            return Integer.parseInt(parts[0]);
                        } catch (Exception e) {
                            return 0;
                        }
                    };
                    break;
                    
                default:
                    // Default to Prisoner ID
                    keyExtractor = p -> p.getPrisonerId();
                    System.out.println("Unknown sort field, defaulting to Prisoner ID");
            }
            
            // Execute the appropriate sorting algorithm
            long startTime = System.currentTimeMillis();
            
            if (useBubbleSort) {
                bubbleSort(sorted, (java.util.function.Function<PrisonerModel, Comparable>) keyExtractor, ascending);
            } else {
                insertionSort(sorted, (java.util.function.Function<PrisonerModel, Comparable>) keyExtractor, ascending);
            }
            
            long endTime = System.currentTimeMillis();
            
            System.out.println("\n╔════════════════════════════════════════════════════════════╗");
            System.out.println("║                    SORTING COMPLETED                        ║");
            System.out.println("╚════════════════════════════════════════════════════════════╝");
            System.out.println("Execution Time: " + (endTime - startTime) + " ms");
            System.out.println("Final List Order: " + (ascending ? "Ascending" : "Descending") + " by " + sortBy);
            
            // Display first few results for verification
            System.out.println("\nFirst 5 sorted results:");
            for (int i = 0; i < Math.min(5, sorted.size()); i++) {
                PrisonerModel p = sorted.get(i);
                String keyValue = keyExtractor.apply(p).toString();
                System.out.println("  " + (i + 1) + ". ID: " + p.getPrisonerId() + 
                                 ", Name: " + p.getName() + 
                                 ", Sort Key (" + sortBy + "): " + keyValue);
            }
            
            // Show success message to user
            JOptionPane.showMessageDialog(null,
                "Successfully sorted " + sorted.size() + " prisoners by " + sortBy + 
                "\n(" + (ascending ? "Ascending" : "Descending") + " order)",
                "Sort Successful",
                JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            System.err.println("\n[ERROR] Sorting failed: " + e.getMessage());
            e.printStackTrace();
            
            JOptionPane.showMessageDialog(null,
                "Error during sorting: " + e.getMessage(),
                "Sort Error",
                JOptionPane.ERROR_MESSAGE);
            
            return new LinkedList<>(prisonDetails); // Return original list on error
        }
        
        return sorted;
    }
}
