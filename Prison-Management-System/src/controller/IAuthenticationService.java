package controller;

import model.PrisonerModel;

/**
 * IAuthenticationService - Interface for authentication operations
 * Defines contract for user authentication without UI dependencies
 * Enables loose coupling and testability
 * 
 * @author Anjal Bhattarai
 */
public interface IAuthenticationService {
    
    /**
     * Authenticate admin user with credentials
     * @param username the admin username
     * @param password the admin password
     * @return OperationResult with success/failure status and message
     */
    OperationResult<Boolean> authenticateAdmin(String username, String password);
    
    /**
     * Authenticate family user with prisoner ID and family code
     * @param prisonerId the prisoner ID
     * @param familyCode the family access code
     * @return OperationResult with prisoner data on success, error message on failure
     */
    OperationResult<PrisonerModel> authenticateFamilyPortal(int prisonerId, String familyCode);
    
    /**
     * Validate prisoner ID format
     * @param prisonerIdStr the prisoner ID as string (from user input)
     * @return OperationResult with parsed integer on success, error on failure
     */
    OperationResult<Integer> validatePrisonerId(String prisonerIdStr);
}
