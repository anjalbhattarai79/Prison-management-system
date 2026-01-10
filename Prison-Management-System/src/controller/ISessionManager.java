package controller;

/**
 * ISessionManager - Interface for user session management
 * Defines contract for managing user authentication state and session data
 * Enables loose coupling between views and session management logic
 * 
 * @author Anjal Bhattarai
 */
public interface ISessionManager {
    
    /**
     * Check if admin user is currently logged in
     * @return true if admin is authenticated, false otherwise
     */
    boolean isAdminLoggedIn();
    
    /**
     * Check if family user is currently logged in
     * @return true if family user is authenticated, false otherwise
     */
    boolean isFamilyLoggedIn();
    
    /**
     * Get the currently authenticated family's prisoner ID
     * @return prisoner ID or 0 if no family is logged in
     */
    int getCurrentFamilyPrisonerId();
    
    /**
     * Set admin login status
     * @param loggedIn true to mark admin as logged in, false to log out
     */
    void setAdminLoggedIn(boolean loggedIn);
    
    /**
     * Set family login status with associated prisoner ID
     * @param loggedIn true to mark family as logged in, false to log out
     * @param prisonerId the prisoner ID associated with this family session
     */
    void setFamilyLoggedIn(boolean loggedIn, int prisonerId);
    
    /**
     * Clear all session data (logout all users)
     */
    void clearSession();
    
    /**
     * Get session type indicator
     * @return "ADMIN", "FAMILY", or "NONE"
     */
    String getSessionType();
}
