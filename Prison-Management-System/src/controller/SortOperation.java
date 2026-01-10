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
        int totalComparisons = 0;
        int totalSwaps = 0;
        
        System.out.println("\nBubble Sort - Step-by-step execution:");
        
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            int passSwaps = 0;
            
            System.out.println("  Pass " + (i + 1) + ": Comparing adjacent elements...");
            
            for (int j = 0; j < n - i - 1; j++) {
                PrisonerModel current = list.get(j);
                PrisonerModel next = list.get(j + 1);
                
                T currentKey = keyExtractor.apply(current);
                T nextKey = keyExtractor.apply(next);
                
                totalComparisons++;
                int comparison = currentKey.compareTo(nextKey);
                
                boolean shouldSwap = ascending ? comparison > 0 : comparison < 0;
                
                if (shouldSwap) {
                    list.set(j, next);
                    list.set(j + 1, current);
                    swapped = true;
                    totalSwaps++;
                    passSwaps++;
                }
            }
            
            System.out.println("     → " + passSwaps + " swap(s) made. Largest element \"bubbled\" to position " + (n - i - 1));
            
            if (!swapped) {
                System.out.println("     → No swaps needed - list is sorted!");
                break;
            }
        }
        
        System.out.println("\n  ✓ Bubble Sort completed: " + totalComparisons + " comparisons, " + totalSwaps + " total swaps");
        System.out.println("     (Each pass moves the largest unsorted element to its final position)");
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
        int totalComparisons = 0;
        int totalInsertions = 0;
        
        System.out.println("\nInsertion Sort - Step-by-step execution:");
        System.out.println("  (Building sorted portion from left to right)\n");
        
        for (int i = 1; i < n; i++) {
            PrisonerModel key = list.get(i);
            T keyValue = keyExtractor.apply(key);
            int originalPos = i;
            int j = i - 1;
            
            System.out.println("  Step " + i + ": Inserting element at position " + i + " (value: " + keyValue + ")");
            
            while (j >= 0) {
                PrisonerModel compared = list.get(j);
                T comparedValue = keyExtractor.apply(compared);
                
                totalComparisons++;
                int comparison = keyValue.compareTo(comparedValue);
                
                boolean shouldMove = ascending ? comparison < 0 : comparison > 0;
                
                if (shouldMove) {
                    list.set(j + 1, compared);
                    j--;
                } else {
                    break;
                }
            }
            
            int finalPos = j + 1;
            if (finalPos != i) {
                list.set(finalPos, key);
                totalInsertions++;
                System.out.println("     → Inserted at position " + finalPos + " (moved from " + originalPos + ")");
            } else {
                System.out.println("     → Already in correct position " + finalPos);
            }
        }
        
        System.out.println("\n  ✓ Insertion Sort completed: " + totalComparisons + " comparisons, " + totalInsertions + " insertions");
        System.out.println("     (Each element inserted into its correct position in the sorted portion)");
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
            System.out.println("\n=== Sorting Prisoners ===");
            System.out.println("Sorting " + sorted.size() + " records by: " + sortBy);
            System.out.println("Order: " + (ascending ? "Ascending (smallest first)" : "Descending (largest first)"));
            
            // Choose sorting algorithm based on list size
            boolean useBubbleSort = sorted.size() <= 10;
            
            System.out.println("\nAlgorithm selected: " + (useBubbleSort ? "Bubble Sort" : "Insertion Sort"));
            System.out.println("Why? " + (useBubbleSort 
                ? "Small dataset (≤10 records) - Bubble Sort is simple and effective" 
                : "Larger dataset (>10 records) - Insertion Sort is more efficient"));
            
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
                    keyExtractor = p -> p.getSentenceDuration();
                    break;
                default:
                    keyExtractor = p -> p.getPrisonerId();
            }
            
            // Execute the appropriate sorting algorithm
            long startTime = System.currentTimeMillis();
            
            if (useBubbleSort) {
                bubbleSort(sorted, (java.util.function.Function<PrisonerModel, Comparable>) keyExtractor, ascending);
            } else {
                insertionSort(sorted, (java.util.function.Function<PrisonerModel, Comparable>) keyExtractor, ascending);
            }
            
            long endTime = System.currentTimeMillis();
            
            System.out.println("\n✓ Sorting complete!");
            System.out.println("  Time taken: " + (endTime - startTime) + "ms");
            System.out.println("  All " + sorted.size() + " records now sorted by " + sortBy + " (" + 
                             (ascending ? "Ascending" : "Descending") + ")\n");
            
            // Show success message to user
            JOptionPane.showMessageDialog(null,
                "Successfully sorted " + sorted.size() + " prisoners by " + sortBy + 
                "\n(" + (ascending ? "Ascending" : "Descending") + " order)",
                "Sort Successful",
                JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Error during sorting: " + e.getMessage(),
                "Sort Error",
                JOptionPane.ERROR_MESSAGE);
            
            return new LinkedList<>(prisonDetails); // Return original list on error
        }
        
        return sorted;
    }
}
