package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * PrisonerValidator - Centralized validation logic for prisoner data
 * Eliminates code duplication across dialog helper classes
 * Enforces business rules consistently across the application
 * 
 * Responsibilities:
 * - Validate prisoner personal information (name, age, gender)
 * - Validate crime-related data (type, description)
 * - Validate dates (admission, release)
 * - Validate sentence duration
 * - Validate prison location and family codes
 * 
 * @author Anjal Bhattarai
 */
public class PrisonerValidator {
    
    // Constants for validation rules
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 150;
    private static final int MAX_NAME_LENGTH = 100;
    private static final int MIN_SENTENCE_DURATION = 1;
    private static final int MAX_SENTENCE_DURATION = 999;
    private static final String NAME_PATTERN = "^[a-zA-Z\\s'-]+$";
    
    /**
     * Validate all prisoner data at once
     * Returns a comprehensive validation result with all errors
     */
    public ValidationResult validatePrisonerData(
            String name, 
            int age, 
            String gender, 
            String address,
            String crimeType, 
            String crimeDescription,
            LocalDate admissionDate,
            int sentenceDuration,
            String location,
            String familyCode) {
        
        List<String> errors = new ArrayList<>();
        
        // Validate name
        ValidationResult nameResult = validateName(name);
        if (nameResult.isInvalid()) {
            errors.addAll(nameResult.getErrors());
        }
        
        // Validate age
        ValidationResult ageResult = validateAge(age);
        if (ageResult.isInvalid()) {
            errors.addAll(ageResult.getErrors());
        }
        
        // Validate gender
        ValidationResult genderResult = validateGender(gender);
        if (genderResult.isInvalid()) {
            errors.addAll(genderResult.getErrors());
        }
        
        // Validate crime type
        ValidationResult crimeTypeResult = validateCrimeType(crimeType);
        if (crimeTypeResult.isInvalid()) {
            errors.addAll(crimeTypeResult.getErrors());
        }
        
        // Validate sentence duration
        ValidationResult sentenceResult = validateSentenceDuration(sentenceDuration);
        if (sentenceResult.isInvalid()) {
            errors.addAll(sentenceResult.getErrors());
        }
        
        // Validate location
        ValidationResult locationResult = validateLocation(location);
        if (locationResult.isInvalid()) {
            errors.addAll(locationResult.getErrors());
        }
        
        // Validate admission date
        ValidationResult admissionResult = validateAdmissionDate(admissionDate);
        if (admissionResult.isInvalid()) {
            errors.addAll(admissionResult.getErrors());
        }
        
        if (errors.isEmpty()) {
            return ValidationResult.success();
        }
        
        return ValidationResult.failure(errors);
    }
    
    /**
     * Validate prisoner name
     * Rules:
     * - Cannot be empty
     * - Can only contain letters, spaces, hyphens, and apostrophes
     * - Maximum 100 characters
     */
    public ValidationResult validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return ValidationResult.failure("Name is required and cannot be empty.");
        }
        
        String trimmedName = name.trim();
        
        if (!trimmedName.matches(NAME_PATTERN)) {
            return ValidationResult.failure(
                "Name can only contain letters, spaces, hyphens, and apostrophes.\n" +
                "No numbers or special characters allowed."
            );
        }
        
        if (trimmedName.length() > MAX_NAME_LENGTH) {
            return ValidationResult.failure(
                "Name is too long. Maximum " + MAX_NAME_LENGTH + " characters allowed."
            );
        }
        
        return ValidationResult.success();
    }
    
    /**
     * Validate prisoner age
     * Rules:
     * - Must be at least 18 years (legal adult)
     * - Cannot exceed 150 years (realistic limit)
     * - Cannot be zero or negative
     */
    public ValidationResult validateAge(int age) {
        if (age <= 0) {
            return ValidationResult.failure(
                "Age must be a positive number.\nAge cannot be zero or negative."
            );
        }
        
        if (age < MIN_AGE) {
            return ValidationResult.failure(
                "Age must be at least " + MIN_AGE + " years.\n" +
                "Minors cannot be admitted to prison."
            );
        }
        
        if (age > MAX_AGE) {
            return ValidationResult.failure(
                "Age must be " + MAX_AGE + " years or less.\n" +
                "Please enter a valid age."
            );
        }
        
        return ValidationResult.success();
    }
    
    /**
     * Validate gender selection
     * Rules:
     * - Cannot be empty or null
     */
    public ValidationResult validateGender(String gender) {
        if (gender == null || gender.trim().isEmpty()) {
            return ValidationResult.failure("Gender is required. Please select a gender.");
        }
        
        return ValidationResult.success();
    }
    
    /**
     * Validate crime type
     * Rules:
     * - Cannot be empty
     */
    public ValidationResult validateCrimeType(String crimeType) {
        if (crimeType == null || crimeType.trim().isEmpty()) {
            return ValidationResult.failure("Crime Type is required and cannot be empty.");
        }
        
        return ValidationResult.success();
    }
    
    /**
     * Validate sentence duration
     * Rules:
     * - Must be at least 1 month
     * - Cannot exceed 999 months (reasonable maximum)
     */
    public ValidationResult validateSentenceDuration(int sentenceDuration) {
        if (sentenceDuration < MIN_SENTENCE_DURATION) {
            return ValidationResult.failure(
                "Sentence duration must be at least " + MIN_SENTENCE_DURATION + " month.\n" +
                "Cannot be zero or negative."
            );
        }
        
        if (sentenceDuration > MAX_SENTENCE_DURATION) {
            return ValidationResult.failure(
                "Sentence duration cannot exceed " + MAX_SENTENCE_DURATION + " months.\n" +
                "Please enter a valid sentence duration."
            );
        }
        
        return ValidationResult.success();
    }
    
    /**
     * Validate prison location
     * Rules:
     * - Cannot be empty
     */
    public ValidationResult validateLocation(String location) {
        if (location == null || location.trim().isEmpty()) {
            return ValidationResult.failure("Prison Location is required and cannot be empty.");
        }
        
        return ValidationResult.success();
    }
    
    /**
     * Validate admission date
     * Rules:
     * - Cannot be null
     * - Cannot be in the future
     */
    public ValidationResult validateAdmissionDate(LocalDate admissionDate) {
        if (admissionDate == null) {
            return ValidationResult.failure("Admission Date is required.");
        }
        
        if (admissionDate.isAfter(LocalDate.now())) {
            return ValidationResult.failure(
                "Admission Date cannot be in the future.\n" +
                "Please select a valid date."
            );
        }
        
        return ValidationResult.success();
    }
    
    /**
     * Validate family code format
     * Rules:
     * - Cannot be empty
     * - Must be alphanumeric
     */
    public ValidationResult validateFamilyCode(String familyCode) {
        if (familyCode == null || familyCode.trim().isEmpty()) {
            return ValidationResult.failure("Family Code is required and cannot be empty.");
        }
        
        String trimmedCode = familyCode.trim();
        
        if (!trimmedCode.matches("^[a-zA-Z0-9]+$")) {
            return ValidationResult.failure(
                "Family Code must be alphanumeric (letters and numbers only).\n" +
                "No spaces or special characters allowed."
            );
        }
        
        return ValidationResult.success();
    }
    
    /**
     * Validate visit request data
     */
    public ValidationResult validateVisitRequest(
            String visitorName,
            String relationship,
            LocalDate preferredDate,
            String purpose) {
        
        List<String> errors = new ArrayList<>();
        
        // Validate visitor name
        if (visitorName == null || visitorName.trim().isEmpty()) {
            errors.add("Visitor name is required.");
        } else if (!visitorName.matches(NAME_PATTERN)) {
            errors.add("Visitor name can only contain letters, spaces, hyphens, and apostrophes.");
        }
        
        // Validate relationship
        if (relationship == null || relationship.trim().isEmpty()) {
            errors.add("Relationship is required.");
        }
        
        // Validate preferred date
        if (preferredDate == null) {
            errors.add("Preferred visit date is required.");
        } else if (preferredDate.isBefore(LocalDate.now())) {
            errors.add("Preferred visit date cannot be in the past.");
        }
        
        // Validate purpose
        if (purpose == null || purpose.trim().isEmpty()) {
            errors.add("Purpose of visit is required.");
        }
        
        if (errors.isEmpty()) {
            return ValidationResult.success();
        }
        
        return ValidationResult.failure(errors);
    }
}
