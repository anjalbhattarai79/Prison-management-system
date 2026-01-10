package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * VisitRequest Model - Represents a family visit request
 * Used for tracking visit requests from family portal
 * @author Anjal Bhattarai
 */
public class VisitRequest {
    private int requestId;
    private int prisonerId;
    private String prisonerName;
    private String visitorName;
    private String relationship;
    private LocalDate preferredDate;
    private String purpose;
    private String status; // "Pending", "Approved", "Declined"
    private LocalDateTime requestDateTime;
    private String adminNotes;
    
    // Auto-increment counter for request IDs
    private static int nextRequestId = 1001;
    
    /**
     * Constructor for new visit request
     */
    public VisitRequest(int prisonerId, String prisonerName, String visitorName, 
                       String relationship, LocalDate preferredDate, String purpose) {
        this.requestId = nextRequestId++;
        this.prisonerId = prisonerId;
        this.prisonerName = prisonerName;
        this.visitorName = visitorName;
        this.relationship = relationship;
        this.preferredDate = preferredDate;
        this.purpose = purpose;
        this.status = "Pending";
        this.requestDateTime = LocalDateTime.now();
        this.adminNotes = "";
    }
    
    /**
     * Constructor with all fields (for loading existing data)
     */
    public VisitRequest(int requestId, int prisonerId, String prisonerName, String visitorName,
                       String relationship, LocalDate preferredDate, String purpose,
                       String status, LocalDateTime requestDateTime, String adminNotes) {
        this.requestId = requestId;
        this.prisonerId = prisonerId;
        this.prisonerName = prisonerName;
        this.visitorName = visitorName;
        this.relationship = relationship;
        this.preferredDate = preferredDate;
        this.purpose = purpose;
        this.status = status;
        this.requestDateTime = requestDateTime;
        this.adminNotes = adminNotes;
        
        // Update nextRequestId if needed
        if (requestId >= nextRequestId) {
            nextRequestId = requestId + 1;
        }
    }
    
    // Getters and Setters
    public int getRequestId() {
        return requestId;
    }
    
    public int getPrisonerId() {
        return prisonerId;
    }
    
    public void setPrisonerId(int prisonerId) {
        this.prisonerId = prisonerId;
    }
    
    public String getPrisonerName() {
        return prisonerName;
    }
    
    public void setPrisonerName(String prisonerName) {
        this.prisonerName = prisonerName;
    }
    
    public String getVisitorName() {
        return visitorName;
    }
    
    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }
    
    public String getRelationship() {
        return relationship;
    }
    
    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
    
    public LocalDate getPreferredDate() {
        return preferredDate;
    }
    
    public void setPreferredDate(LocalDate preferredDate) {
        this.preferredDate = preferredDate;
    }
    
    public String getPurpose() {
        return purpose;
    }
    
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDateTime getRequestDateTime() {
        return requestDateTime;
    }
    
    public String getAdminNotes() {
        return adminNotes;
    }
    
    public void setAdminNotes(String adminNotes) {
        this.adminNotes = adminNotes;
    }
    
    /**
     * Get formatted request date time
     */
    public String getFormattedRequestDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return requestDateTime.format(formatter);
    }
    
    /**
     * Get formatted preferred date
     */
    public String getFormattedPreferredDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return preferredDate.format(formatter);
    }
    
    /**
     * Get color-coded status for display
     */
    public String getStatusWithColor() {
        switch (status) {
            case "Approved":
                return "<html><span style='color: green; font-weight: bold;'>Approved</span></html>";
            case "Declined":
                return "<html><span style='color: red; font-weight: bold;'>Declined</span></html>";
            default:
                return "<html><span style='color: orange; font-weight: bold;'>Pending</span></html>";
        }
    }
    
    @Override
    public String toString() {
        return String.format("Visit Request #%d - %s wants to visit %s (ID: %d) on %s - Status: %s",
            requestId, visitorName, prisonerName, prisonerId, 
            getFormattedPreferredDate(), status);
    }
}
