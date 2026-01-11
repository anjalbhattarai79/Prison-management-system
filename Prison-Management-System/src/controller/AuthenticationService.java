package controller;

import model.PrisonerModel;

/**
 * AuthenticationService - Handles user authentication logic
 * Centralizes authentication to eliminate security logic from view layer
 * Provides type-safe authentication results without UI dependencies
 * 
 * Responsibilities:
 * - Validate admin credentials
 * - Validate family portal access (prisoner ID + family code)
 * - Return structured authentication results
 * - Delegate to PrisonController for prisoner data access
 * 
 * Note: In production, credentials would be stored securely (hashed, database)
 * This implementation uses hardcoded credentials for coursework demonstration
 * 
 * @author Anjal Bhattarai
 */
public class AuthenticationService {
    
    // Hardcoded admin credentials (for coursework demonstration only)
    // In production: use secure password hashing and database storage
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
    
    private final PrisonController prisonController;
    
    /**
     * Constructor with dependency injection
     * @param prisonController controller for accessing prisoner data
     */
    public AuthenticationService(PrisonController prisonController) {
        this.prisonController = prisonController;
    }
    
    public OperationResult<Boolean> authenticateAdmin(String username, String password) {
        // Validate inputs
        if (username == null || username.trim().isEmpty()) {
            return OperationResult.failure("Username cannot be empty");
        }
        
        if (password == null || password.trim().isEmpty()) {
            return OperationResult.failure("Password cannot be empty");
        }
        
        // Check credentials
        if (ADMIN_USERNAME.equals(username.trim()) && ADMIN_PASSWORD.equals(password)) {
            System.out.println("[AuthenticationService] Admin authentication successful");
            return OperationResult.success(true, "Login successful! Welcome, Administrator.");
        } else {
            System.out.println("[AuthenticationService] Admin authentication failed");
            return OperationResult.failure(
                "Invalid Credentials",
                "The username or password you entered is incorrect.\nPlease try again."
            );
        }
    }
    
    public OperationResult<PrisonerModel> authenticateFamilyPortal(int prisonerId, String familyCode) {
        // Validate family code input
        if (familyCode == null || familyCode.trim().isEmpty()) {
            return OperationResult.failure("Family code cannot be empty");
        }
        
        // Retrieve prisoner by ID
        PrisonerModel prisoner = prisonController.getPrisonerById(prisonerId);
        
        if (prisoner == null) {
            System.out.println("[AuthenticationService] Family login failed - Prisoner not found: " + prisonerId);
            return OperationResult.failure(
                "Prisoner Not Found",
                "No prisoner found with ID: " + prisonerId + "\n" +
                "Please verify the prisoner ID and try again."
            );
        }
        
        // Validate family code
        if (!prisoner.getFamilyCode().equals(familyCode.trim())) {
            System.out.println("[AuthenticationService] Family login failed - Invalid family code");
            return OperationResult.failure(
                "Invalid Family Code",
                "The family code you entered is incorrect.\n" +
                "Please contact the prison administration for assistance."
            );
        }
        
        // Authentication successful
        System.out.println("[AuthenticationService] Family authentication successful for prisoner ID: " + prisonerId);
        return OperationResult.success(
            prisoner,
            "Welcome! You are now viewing information for " + prisoner.getName()
        );
    }
    
    public OperationResult<Integer> validatePrisonerId(String prisonerIdStr) {
        if (prisonerIdStr == null || prisonerIdStr.trim().isEmpty()) {
            return OperationResult.failure("Prisoner ID cannot be empty");
        }
        
        try {
            int prisonerId = Integer.parseInt(prisonerIdStr.trim());
            
            if (prisonerId <= 0) {
                return OperationResult.failure(
                    "Invalid Prisoner ID",
                    "Prisoner ID must be a positive number."
                );
            }
            
            return OperationResult.success(prisonerId, "Valid prisoner ID");
            
        } catch (NumberFormatException e) {
            return OperationResult.failure(
                "Invalid Format",
                "Prisoner ID must be a valid number.\n" +
                "Please enter numbers only (e.g., 101, 102)."
            );
        }
    }
    
    /**
     * Get admin username for reference (public information)
     * Password should never be exposed
     */
    public String getAdminUsername() {
        return ADMIN_USERNAME;
    }
}
