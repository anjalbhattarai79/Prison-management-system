/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.LinkedList;
import javax.swing.JOptionPane;
import model.PrisonerModel;

/**
 * TrashBinOperation - Manages deleted prisoners using Stack data structure
 * Implements LIFO (Last In First Out) pattern for undo/restore functionality
 * Demonstrates Stack operations: push, pop, peek, isEmpty, size
 * 
 * @author Anjal Bhattarai
 */
public class TrashBinOperation {
    
    /**
     * Push deleted prisoner to trash bin (Stack)
     * Demonstrates Stack.push() operation
     * 
     * @param trashBin Stack containing deleted prisoners
     * @param deletedPrisoner The prisoner to add to trash
     */
    public static void pushToTrash(SimpleStack<PrisonerModel> trashBin, PrisonerModel deletedPrisoner) {
        trashBin.push(deletedPrisoner);
        System.out.println("\n[STACK] PUSH: " + deletedPrisoner.getName() + " → Moved to trash (Size: " + trashBin.size() + ")");
        System.out.println("        (LIFO - Last In, First Out: This prisoner will be restored first)\n");
    }
    
    /**
     * Pop (restore) most recently deleted prisoner from trash
     * Demonstrates Stack.pop() operation
     * 
     * @param trashBin Stack containing deleted prisoners
     * @param prisonDetails Main list to restore prisoner to
     * @return The restored prisoner, or null if trash is empty
     */
    public static PrisonerModel popFromTrash(SimpleStack<PrisonerModel> trashBin, LinkedList<PrisonerModel> prisonDetails) {
        // Check if trash is empty
        if (trashBin.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                "Trash bin is empty!\nNo prisoners to restore.",
                "Trash Empty",
                JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
        
        // Peek at top element before popping
        PrisonerModel topPrisoner = trashBin.peek();
        
        // Ask for confirmation
        int confirm = JOptionPane.showConfirmDialog(null,
            "Restore this prisoner from trash?\n\n" +
            "ID: " + topPrisoner.getPrisonerId() + "\n" +
            "Name: " + topPrisoner.getName() + "\n" +
            "Crime: " + topPrisoner.getCrimeType() + "\n\n" +
            "This will remove them from trash and restore to active list.",
            "Confirm Restore",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirm != JOptionPane.YES_OPTION) {
            return null;
        }
        
        // Pop from stack
        PrisonerModel restoredPrisoner = trashBin.pop();
        System.out.println("\n[STACK] POP: " + restoredPrisoner.getName() + " ← Restored from trash (Size: " + trashBin.size() + ")");
        System.out.println("       (LIFO demonstrated: Most recently deleted prisoner restored first)\n");
        
        // Add back to main list
        prisonDetails.add(restoredPrisoner);
        
        JOptionPane.showMessageDialog(null,
            "Prisoner restored successfully!\n\n" +
            "Name: " + restoredPrisoner.getName() + "\n" +
            "ID: " + restoredPrisoner.getPrisonerId() + "\n\n" +
            "They have been added back to the active prisoner list.",
            "Restore Successful",
            JOptionPane.INFORMATION_MESSAGE);
        
        return restoredPrisoner;
    }
    
    /**
     * View trash bin contents without modifying stack
     * Demonstrates Stack.peek() and iteration
     * 
     * @param trashBin Stack containing deleted prisoners
     * @return HTML formatted string of trash contents
     */
    public static String viewTrashContents(SimpleStack<PrisonerModel> trashBin) {
        if (trashBin.isEmpty()) {
            return "<html><b>Trash Bin (Stack):</b><br>No deleted prisoners</html>";
        }
        
        StringBuilder contents = new StringBuilder("<html><b>Trash Bin (Stack - LIFO):</b><br>");
        contents.append("<i>Most recent deletion at top</i><br><br>");
        
        // Iterate through stack via array (keeps it simple)
        int position = trashBin.size();
        PrisonerModel[] arr = trashBin.toArray(new PrisonerModel[0]);
        for (PrisonerModel p : arr) {
            String positionLabel = (position == trashBin.size()) ? " ← TOP" : "";
            
            contents.append(position).append(". ")
                   .append("<b>").append(p.getName()).append("</b>")
                   .append(" (ID: ").append(p.getPrisonerId()).append(")")
                   .append(positionLabel)
                   .append("<br>");
            
            position--;
        }
        
        contents.append("<br><i>Total in trash: ").append(trashBin.size()).append("</i>");
        contents.append("</html>");
        
        PrisonerModel top = trashBin.peek();
        System.out.println("Trash contains " + trashBin.size() + " prisoner(s) [Top: " + (top != null ? top.getName() : "None") + "]");
        
        return contents.toString();
    }
    
    /**
     * Get trash bin size
     * 
     * @param trashBin Stack containing deleted prisoners
     * @return Number of prisoners in trash
     */
    public static int getTrashSize(SimpleStack<PrisonerModel> trashBin) {
        return trashBin.size();
    }
    
    /**
     * Check if trash bin is empty
     * 
     * @param trashBin Stack containing deleted prisoners
     * @return true if trash is empty
     */
    public static boolean isTrashEmpty(SimpleStack<PrisonerModel> trashBin) {
        return trashBin.isEmpty();
    }
    
    /**
     * Clear entire trash bin (permanent deletion)
     * Demonstrates Stack.clear() operation
     * 
     * @param trashBin Stack containing deleted prisoners
     */
    public static void emptyTrash(SimpleStack<PrisonerModel> trashBin) {
        if (trashBin.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                "Trash bin is already empty!",
                "Empty Trash",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int count = trashBin.size();
        
        // Ask for confirmation
        int confirm = JOptionPane.showConfirmDialog(null,
            "Permanently delete all " + count + " prisoners from trash?\n\n" +
            "This action cannot be undone!\n" +
            "All prisoners in trash will be permanently removed.",
            "Confirm Empty Trash",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        
        // Clear the stack
        trashBin.clear();
        System.out.println("\n[STACK] CLEAR: All " + count + " prisoner(s) permanently deleted");
        System.out.println("        (Stack now empty - cannot restore any prisoners)\n");
        
        JOptionPane.showMessageDialog(null,
            "Trash bin emptied!\n" +
            "Permanently deleted " + count + " prisoner(s).",
            "Empty Trash Success",
            JOptionPane.INFORMATION_MESSAGE);
    }
}
