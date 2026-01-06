package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Activity Model - Represents a system activity/event
 * Used for tracking recent activities in the system
 * @author Anjal Bhattarai
 */
public class Activity {
    private String action;      // "ADDED", "UPDATED", "DELETED", "RESTORED", "VIEWED"
    private String prisonerName;
    private int prisonerId;
    private LocalDateTime timestamp;
    
    public Activity(String action, String prisonerName, int prisonerId) {
        this.action = action;
        this.prisonerName = prisonerName;
        this.prisonerId = prisonerId;
        this.timestamp = LocalDateTime.now();
    }
    
    public String getAction() {
        return action;
    }
    
    public String getPrisonerName() {
        return prisonerName;
    }
    
    public int getPrisonerId() {
        return prisonerId;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    /**
     * Get formatted time string
     */
    public String getFormattedTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return timestamp.format(formatter);
    }
    
    /**
     * Get display string for activity
     */
    @Override
    public String toString() {
        return String.format("[%s] %s: %s (ID: %d)", 
            getFormattedTime(), action, prisonerName, prisonerId);
    }
}
