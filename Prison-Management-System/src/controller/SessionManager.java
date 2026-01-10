package controller;

/**
 * SessionManager - Manages user authentication and session state
 * Centralizes session management to eliminate session state in view layer
 * Provides thread-safe session handling (though current application is single-threaded)
 * 
 * Responsibilities:
 * - Track admin login status
 * - Track family portal login status with associated prisoner ID
 * - Provide session validation
 * - Enable clean logout functionality
 * 
 * @author Anjal Bhattarai
 */
public class SessionManager implements ISessionManager {
    
    private boolean adminLoggedIn;
    private boolean familyLoggedIn;
    private int currentFamilyPrisonerId;
    
    /**
     * Constructor - initializes with no active session
     */
    public SessionManager() {
        clearSession();
    }
    
    @Override
    public boolean isAdminLoggedIn() {
        return adminLoggedIn;
    }
    
    @Override
    public boolean isFamilyLoggedIn() {
        return familyLoggedIn;
    }
    
    @Override
    public int getCurrentFamilyPrisonerId() {
        return currentFamilyPrisonerId;
    }
    
    @Override
    public void setAdminLoggedIn(boolean loggedIn) {
        this.adminLoggedIn = loggedIn;
        if (loggedIn) {
            // Clear family session when admin logs in
            this.familyLoggedIn = false;
            this.currentFamilyPrisonerId = 0;
        }
        System.out.println("[SessionManager] Admin login status: " + loggedIn);
    }
    
    @Override
    public void setFamilyLoggedIn(boolean loggedIn, int prisonerId) {
        this.familyLoggedIn = loggedIn;
        this.currentFamilyPrisonerId = loggedIn ? prisonerId : 0;
        if (loggedIn) {
            // Clear admin session when family logs in
            this.adminLoggedIn = false;
        }
        System.out.println("[SessionManager] Family login status: " + loggedIn + 
                         (loggedIn ? " (Prisoner ID: " + prisonerId + ")" : ""));
    }
    
    @Override
    public void clearSession() {
        this.adminLoggedIn = false;
        this.familyLoggedIn = false;
        this.currentFamilyPrisonerId = 0;
        System.out.println("[SessionManager] Session cleared");
    }
    
    @Override
    public String getSessionType() {
        if (adminLoggedIn) {
            return "ADMIN";
        } else if (familyLoggedIn) {
            return "FAMILY";
        } else {
            return "NONE";
        }
    }
    
    /**
     * Get session information as string for debugging
     */
    @Override
    public String toString() {
        return "SessionManager{" +
               "type=" + getSessionType() +
               ", adminLoggedIn=" + adminLoggedIn +
               ", familyLoggedIn=" + familyLoggedIn +
               ", currentFamilyPrisonerId=" + currentFamilyPrisonerId +
               '}';
    }
}
