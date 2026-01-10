package controller;

/**
 * Generic Result class for operation outcomes
 * Provides type-safe success/failure handling without exceptions
 * Eliminates need for UI components (JOptionPane) in controller layer
 * 
 * @param <T> The type of data returned on success
 * @author Anjal Bhattarai
 */
public class OperationResult<T> {
    
    private final boolean success;
    private final T data;
    private final String message;
    private final String errorDetails;
    
    /**
     * Private constructor - use static factory methods
     */
    private OperationResult(boolean success, T data, String message, String errorDetails) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.errorDetails = errorDetails;
    }
    
    /**
     * Create a successful result with data
     */
    public static <T> OperationResult<T> success(T data, String message) {
        return new OperationResult<>(true, data, message, null);
    }
    
    /**
     * Create a successful result without data
     */
    public static <T> OperationResult<T> success(String message) {
        return new OperationResult<>(true, null, message, null);
    }
    
    /**
     * Create a failed result with error message
     */
    public static <T> OperationResult<T> failure(String message) {
        return new OperationResult<>(false, null, message, null);
    }
    
    /**
     * Create a failed result with detailed error information
     */
    public static <T> OperationResult<T> failure(String message, String errorDetails) {
        return new OperationResult<>(false, null, message, errorDetails);
    }
    
    // Getters
    public boolean isSuccess() {
        return success;
    }
    
    public boolean isFailure() {
        return !success;
    }
    
    public T getData() {
        return data;
    }
    
    public String getMessage() {
        return message;
    }
    
    public String getErrorDetails() {
        return errorDetails;
    }
    
    /**
     * Get full error message including details
     */
    public String getFullErrorMessage() {
        if (errorDetails != null && !errorDetails.isEmpty()) {
            return message + "\n\nDetails: " + errorDetails;
        }
        return message;
    }
    
    @Override
    public String toString() {
        if (success) {
            return "Success: " + message + (data != null ? " [Data: " + data + "]" : "");
        } else {
            return "Failure: " + message + (errorDetails != null ? " [" + errorDetails + "]" : "");
        }
    }
}
