/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.LinkedList;
import java.util.ArrayList;
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
     * Compare two prisoners based on sortBy and order.
     */
    private static int comparePrisoners(PrisonerModel a, PrisonerModel b, String sortBy, boolean ascending) {
        int c;
        switch (sortBy) {
            case "Name":
                c = a.getName().compareToIgnoreCase(b.getName());
                break;
            case "Prisoner ID":
            case "ID":
                c = Integer.compare(a.getPrisonerId(), b.getPrisonerId());
                break;
            case "Age":
                c = Integer.compare(a.getAge(), b.getAge());
                break;
            case "Admission Date":
                c = a.getAdmissionDate().compareTo(b.getAdmissionDate());
                break;
            case "Release Date": {
                java.time.LocalDate ra = a.getReleaseDate();
                java.time.LocalDate rb = b.getReleaseDate();
                if (ra == null && rb == null) c = 0;
                else if (ra == null) c = 1; // nulls last in ascending
                else if (rb == null) c = -1;
                else c = ra.compareTo(rb);
                break;
            }
            case "Sentence Duration":
                c = Integer.compare(a.getSentenceDuration(), b.getSentenceDuration());
                break;
            default:
                c = Integer.compare(a.getPrisonerId(), b.getPrisonerId());
        }
        return ascending ? c : -c;
    }

    /** Selection Sort (simple, O(n^2)) */
    private static void selectionSort(LinkedList<PrisonerModel> list, String sortBy, boolean ascending) {
        int n = list.size();
        System.out.println("\nSelection Sort: " + n + " records");
        for (int i = 0; i < n - 1; i++) {
            int best = i;
            for (int j = i + 1; j < n; j++) {
                if (comparePrisoners(list.get(j), list.get(best), sortBy, ascending) < 0) {
                    best = j;
                }
            }
            if (best != i) {
                PrisonerModel tmp = list.get(i);
                list.set(i, list.get(best));
                list.set(best, tmp);
            }
        }
        System.out.println("✓ Selection Sort complete");
    }

    /** Insertion Sort (stable, O(n^2)) */
    private static void insertionSort(LinkedList<PrisonerModel> list, String sortBy, boolean ascending) {
        int n = list.size();
        System.out.println("\nInsertion Sort: " + n + " records");
        for (int i = 1; i < n; i++) {
            PrisonerModel key = list.get(i);
            int j = i - 1;
            while (j >= 0 && comparePrisoners(key, list.get(j), sortBy, ascending) < 0) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
        System.out.println("✓ Insertion Sort complete");
    }

    /** Merge Sort (stable, O(n log n)) */
    private static void mergeSort(LinkedList<PrisonerModel> list, String sortBy, boolean ascending) {
        int n = list.size();
        if (n <= 1) return;
        int mid = n / 2;
        ArrayList<PrisonerModel> leftArr = new ArrayList<>(list.subList(0, mid));
        ArrayList<PrisonerModel> rightArr = new ArrayList<>(list.subList(mid, n));
        LinkedList<PrisonerModel> left = new LinkedList<>(leftArr);
        LinkedList<PrisonerModel> right = new LinkedList<>(rightArr);
        mergeSort(left, sortBy, ascending);
        mergeSort(right, sortBy, ascending);
        // Merge back into list
        list.clear();
        int i = 0, j = 0;
        while (i < left.size() && j < right.size()) {
            PrisonerModel a = left.get(i);
            PrisonerModel b = right.get(j);
            if (comparePrisoners(a, b, sortBy, ascending) <= 0) {
                list.add(a);
                i++;
            } else {
                list.add(b);
                j++;
            }
        }
        while (i < left.size()) { list.add(left.get(i++)); }
        while (j < right.size()) { list.add(right.get(j++)); }
    }
    
    // (Legacy verbose insertion sort removed to simplify coursework scope)
    
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
        return sortInternal(prisonDetails, sortBy, ascending, null);
    }

    /**
     * Overload: Sort with explicit algorithm selection from UI.
     * algorithm: "InsertionSort", "SelectionSort", "MergeSort"
     */
    public static LinkedList<PrisonerModel> sortPrisoners(LinkedList<PrisonerModel> prisonDetails,
            String sortBy, boolean ascending, String algorithm) {
        return sortInternal(prisonDetails, sortBy, ascending, algorithm);
    }

    /** Internal: single implementation for sorting with optional explicit algorithm. */
    private static LinkedList<PrisonerModel> sortInternal(LinkedList<PrisonerModel> prisonDetails,
            String sortBy, boolean ascending, String algorithm) {
        if (prisonDetails == null || prisonDetails.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                "No prisoner data available to sort.",
                "Sort Error",
                JOptionPane.WARNING_MESSAGE);
            return new LinkedList<>();
        }

        LinkedList<PrisonerModel> sorted = new LinkedList<>(prisonDetails);
        try {
            System.out.println("\n=== Sorting Prisoners ===");
            System.out.println("Sorting " + sorted.size() + " records by: " + sortBy);
            System.out.println("Order: " + (ascending ? "Ascending (smallest first)" : "Descending (largest first)"));

            String algo;
            if (algorithm == null || algorithm.isEmpty()) {
                int n = sorted.size();
                algo = (n <= 10) ? "Insertion" : (n <= 100) ? "Selection" : "Merge";
            } else {
                if ("InsertionSort".equalsIgnoreCase(algorithm) || "Insertion".equalsIgnoreCase(algorithm)) algo = "Insertion";
                else if ("SelectionSort".equalsIgnoreCase(algorithm) || "Selection".equalsIgnoreCase(algorithm)) algo = "Selection";
                else algo = "Merge";
            }

            System.out.println("\nAlgorithm selected: " + algo + " Sort");
            long startTime = System.currentTimeMillis();
            if ("Insertion".equals(algo)) insertionSort(sorted, sortBy, ascending);
            else if ("Selection".equals(algo)) selectionSort(sorted, sortBy, ascending);
            else mergeSort(sorted, sortBy, ascending);

            long endTime = System.currentTimeMillis();
            System.out.println("\n✓ Sorting complete!");
            System.out.println("  Time taken: " + (endTime - startTime) + "ms");
            System.out.println("  All " + sorted.size() + " records now sorted by " + sortBy + " (" + 
                             (ascending ? "Ascending" : "Descending") + ")\n");

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
            return new LinkedList<>(prisonDetails);
        }
        return sorted;
    }
}
