package controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Validation result container
 * Holds validation status and detailed error messages
 * Supports multiple validation errors for comprehensive feedback
 * 
 * @author Anjal Bhattarai
 */
public class ValidationResult {
    
    private final boolean valid;
    private final List<String> errors;
    
    private ValidationResult(boolean valid, List<String> errors) {
        this.valid = valid;
        this.errors = errors != null ? errors : new ArrayList<>();
    }
    
    /**
     * Create a successful validation result
     */
    public static ValidationResult success() {
        return new ValidationResult(true, new ArrayList<>());
    }
    
    /**
     * Create a failed validation result with a single error
     */
    public static ValidationResult failure(String error) {
        List<String> errors = new ArrayList<>();
        errors.add(error);
        return new ValidationResult(false, errors);
    }
    
    /**
     * Create a failed validation result with multiple errors
     */
    public static ValidationResult failure(List<String> errors) {
        return new ValidationResult(false, errors);
    }
    
    public boolean isValid() {
        return valid;
    }
    
    public boolean isInvalid() {
        return !valid;
    }
    
    public List<String> getErrors() {
        return new ArrayList<>(errors);
    }
    
    public String getFirstError() {
        return errors.isEmpty() ? "" : errors.get(0);
    }
    
    /**
     * Get all errors as a single formatted string
     */
    public String getAllErrorsAsString() {
        if (errors.isEmpty()) {
            return "";
        }
        if (errors.size() == 1) {
            return errors.get(0);
        }
        
        StringBuilder sb = new StringBuilder("Validation errors:\n");
        for (int i = 0; i < errors.size(); i++) {
            sb.append((i + 1)).append(". ").append(errors.get(i)).append("\n");
        }
        return sb.toString().trim();
    }
    
    @Override
    public String toString() {
        if (valid) {
            return "Valid";
        }
        return "Invalid: " + getAllErrorsAsString();
    }
}
