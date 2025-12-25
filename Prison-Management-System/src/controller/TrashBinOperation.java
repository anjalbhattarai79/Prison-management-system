/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.LinkedList;
import java.util.Stack;
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
    public static void pushToTrash(Stack<PrisonerModel> trashBin, PrisonerModel deletedPrisoner) {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║           TRASH BIN - PUSH OPERATION (STACK)              ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println("Data Structure: Stack (LIFO - Last In First Out)");
        
        System.out.println("\n--- Before Push ---");
        System.out.println("Stack size: " + trashBin.size());
        
        if (!trashBin.isEmpty()) {
            PrisonerModel topPrisoner = trashBin.peek();
            System.out.println("Current top of stack: " + topPrisoner.getName() + " (ID: " + topPrisoner.getPrisonerId() + ")");
        } else {
            System.out.println("Stack is empty");
        }
        
        // Push operation
        System.out.println("\n--- Pushing to Stack ---");
        System.out.println("Pushing prisoner: " + deletedPrisoner.getName() + " (ID: " + deletedPrisoner.getPrisonerId() + ")");
        trashBin.push(deletedPrisoner);
        
        System.out.println("\n--- After Push ---");
        System.out.println("Stack size: " + trashBin.size());
        System.out.println("New top of stack: " + deletedPrisoner.getName() + " (ID: " + deletedPrisoner.getPrisonerId() + ")");
        
        System.out.println("\n✓ Successfully pushed to trash bin");
        System.out.println("This prisoner can now be restored using pop operation");
    }
    
    /**
     * Pop (restore) most recently deleted prisoner from trash
     * Demonstrates Stack.pop() operation
     * 
     * @param trashBin Stack containing deleted prisoners
     * @param prisonDetails Main list to restore prisoner to
     * @return The restored prisoner, or null if trash is empty
     */
    public static PrisonerModel popFromTrash(Stack<PrisonerModel> trashBin, LinkedList<PrisonerModel> prisonDetails) {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║            TRASH BIN - POP OPERATION (STACK)              ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        
        // Check if trash is empty
        if (trashBin.isEmpty()) {
            System.out.println("✗ Trash bin is empty - nothing to restore");
            JOptionPane.showMessageDialog(null,
                "Trash bin is empty!\nNo prisoners to restore.",
                "Trash Empty",
                JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
        
        System.out.println("Stack size before pop: " + trashBin.size());
        
        // Peek at top element before popping
        PrisonerModel topPrisoner = trashBin.peek();
        System.out.println("\n--- Prisoner at Top of Stack ---");
        System.out.println("ID: " + topPrisoner.getPrisonerId());
        System.out.println("Name: " + topPrisoner.getName());
        System.out.println("Crime: " + topPrisoner.getCrimeType());
        System.out.println("Status: " + topPrisoner.getStatus());
        
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
            System.out.println("✗ User cancelled restore operation");
            return null;
        }
        
        // Pop from stack
        System.out.println("\n--- Popping from Stack ---");
        PrisonerModel restoredPrisoner = trashBin.pop();
        System.out.println("✓ Popped: " + restoredPrisoner.getName() + " (ID: " + restoredPrisoner.getPrisonerId() + ")");
        
        // Add back to main list
        System.out.println("\n--- Restoring to Main List ---");
        prisonDetails.add(restoredPrisoner);
        System.out.println("✓ Added back to LinkedList");
        
        System.out.println("\n--- After Pop ---");
        System.out.println("Stack size: " + trashBin.size());
        
        if (!trashBin.isEmpty()) {
            PrisonerModel newTop = trashBin.peek();
            System.out.println("New top of stack: " + newTop.getName() + " (ID: " + newTop.getPrisonerId() + ")");
        } else {
            System.out.println("Stack is now empty");
        }
        
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║              RESTORE OPERATION SUCCESSFUL                  ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println("Restored: " + restoredPrisoner.getName() + " (ID: " + restoredPrisoner.getPrisonerId() + ")");
        System.out.println("Total prisoners in system: " + prisonDetails.size());
        System.out.println("Prisoners remaining in trash: " + trashBin.size());
        
        JOptionPane.showMessageDialog(null,
            "Prisoner restored successfully!\n\n" +
            "Name: " + restoredPrisoner.getName() + "\n" +
            "ID: " + restoredPrisoner.getPrisonerId(),
            "Restore Success",
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
    public static String viewTrashContents(Stack<PrisonerModel> trashBin) {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║              TRASH BIN - VIEW CONTENTS                     ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println("Stack size: " + trashBin.size());
        
        if (trashBin.isEmpty()) {
            System.out.println("Stack is empty");
            return "<html><b>Trash Bin (Stack):</b><br>No deleted prisoners</html>";
        }
        
        StringBuilder contents = new StringBuilder("<html><b>Trash Bin (Stack - LIFO):</b><br>");
        contents.append("<i>Most recent deletion at top</i><br><br>");
        
        System.out.println("\n--- Stack Contents (Top to Bottom) ---");
        
        // Iterate through stack (doesn't modify it)
        int position = trashBin.size();
        for (PrisonerModel p : trashBin) {
            String positionLabel = (position == trashBin.size()) ? " ← TOP" : "";
            
            contents.append(position).append(". ")
                   .append("<b>").append(p.getName()).append("</b>")
                   .append(" (ID: ").append(p.getPrisonerId()).append(")")
                   .append(positionLabel)
                   .append("<br>");
            
            System.out.println("  Position " + position + ": " + p.getName() + 
                             " (ID: " + p.getPrisonerId() + ")" + positionLabel);
            
            position--;
        }
        
        contents.append("<br><i>Total in trash: ").append(trashBin.size()).append("</i>");
        contents.append("</html>");
        
        System.out.println("\nStack peek (top element): " + trashBin.peek().getName());
        
        return contents.toString();
    }
    
    /**
     * Get trash bin size
     * 
     * @param trashBin Stack containing deleted prisoners
     * @return Number of prisoners in trash
     */
    public static int getTrashSize(Stack<PrisonerModel> trashBin) {
        return trashBin.size();
    }
    
    /**
     * Check if trash bin is empty
     * 
     * @param trashBin Stack containing deleted prisoners
     * @return true if trash is empty
     */
    public static boolean isTrashEmpty(Stack<PrisonerModel> trashBin) {
        return trashBin.isEmpty();
    }
    
    /**
     * Clear entire trash bin (permanent deletion)
     * Demonstrates Stack.clear() operation
     * 
     * @param trashBin Stack containing deleted prisoners
     */
    public static void emptyTrash(Stack<PrisonerModel> trashBin) {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║          TRASH BIN - EMPTY OPERATION (CLEAR)              ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        
        if (trashBin.isEmpty()) {
            System.out.println("Trash bin is already empty");
            JOptionPane.showMessageDialog(null,
                "Trash bin is already empty!",
                "Empty Trash",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int count = trashBin.size();
        System.out.println("Prisoners in trash before clear: " + count);
        
        // Ask for confirmation
        int confirm = JOptionPane.showConfirmDialog(null,
            "Permanently delete all " + count + " prisoners from trash?\n\n" +
            "This action cannot be undone!\n" +
            "All prisoners in trash will be permanently removed.",
            "Confirm Empty Trash",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm != JOptionPane.YES_OPTION) {
            System.out.println("✗ User cancelled empty trash operation");
            return;
        }
        
        // Clear the stack
        System.out.println("\n--- Clearing Stack ---");
        trashBin.clear();
        System.out.println("✓ Stack cleared");
        System.out.println("Stack size after clear: " + trashBin.size());
        
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║            EMPTY TRASH OPERATION SUCCESSFUL                ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println("Permanently deleted " + count + " prisoners");
        
        JOptionPane.showMessageDialog(null,
            "Trash bin emptied!\n" +
            "Permanently deleted " + count + " prisoner(s).",
            "Empty Trash Success",
            JOptionPane.INFORMATION_MESSAGE);
    }
}
