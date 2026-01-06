package model;

import java.time.LocalDate;

/**
 * Simple PrisonerModel Model Class
Represents a prisoner entity in the Prison Management System
 */
public class PrisonerModel {
    private int prisonerId;
    private String name;
    private int age;
    private String gender;
    private String address;
    private String crimeType;
    private String crimeDescription;
    private LocalDate admissionDate;
    private int sentenceDuration; // in months
    private LocalDate releaseDate;
    private String status; // "Active", "Released", "Transferred", "Court Hearing"
    private String prisonLocation;
    private String healthStatus;
    private String familyCode; // For family portal access
    private String photoPath; // Path to prisoner's photo

    // Constructor (auto-calculate status)
    public PrisonerModel(int prisonerId, String name, int age, String gender,
                   String address, String crimeType, String crimeDescription,
                   LocalDate admissionDate, int sentenceDuration,
                   String prisonLocation, String familyCode, String photoPath) {
        this.prisonerId = prisonerId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.crimeType = crimeType;
        this.crimeDescription = crimeDescription;
        this.admissionDate = admissionDate;
        this.sentenceDuration = sentenceDuration;
        this.releaseDate = admissionDate.plusMonths(sentenceDuration);
        this.prisonLocation = prisonLocation;
        this.familyCode = familyCode;
        this.photoPath = photoPath;
        this.status = "Active";
        this.healthStatus = "Good";
    }
    
    // Constructor (with manual status)
    public PrisonerModel(int prisonerId, String name, int age, String gender,
                   String address, String crimeType, String crimeDescription,
                   LocalDate admissionDate, int sentenceDuration,
                   String prisonLocation, String familyCode, String photoPath, String status) {
        this.prisonerId = prisonerId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.crimeType = crimeType;
        this.crimeDescription = crimeDescription;
        this.admissionDate = admissionDate;
        this.sentenceDuration = sentenceDuration;
        this.releaseDate = admissionDate.plusMonths(sentenceDuration);
        this.prisonLocation = prisonLocation;
        this.familyCode = familyCode;
        this.photoPath = photoPath;
        this.status = status;  // Use provided status
        this.healthStatus = "Good";
    }

    // Getters and Setters
    public int getPrisonerId() {
        return prisonerId;
    }

    public void setPrisonerId(int prisonerId) {
        this.prisonerId = prisonerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCrimeType() {
        return crimeType;
    }

    public void setCrimeType(String crimeType) {
        this.crimeType = crimeType;
    }

    public String getCrimeDescription() {
        return crimeDescription;
    }

    public void setCrimeDescription(String crimeDescription) {
        this.crimeDescription = crimeDescription;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(LocalDate admissionDate) {
        this.admissionDate = admissionDate;
        // Recalculate release date when admission date changes
        this.releaseDate = admissionDate.plusMonths(sentenceDuration);
    }

    public int getSentenceDuration() {
        return sentenceDuration;
    }

    public void setSentenceDuration(int sentenceDuration) {
        this.sentenceDuration = sentenceDuration;
        // Recalculate release date when sentence duration changes
        this.releaseDate = admissionDate.plusMonths(sentenceDuration);
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrisonLocation() {
        return prisonLocation;
    }

    public void setPrisonLocation(String prisonLocation) {
        this.prisonLocation = prisonLocation;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public String getFamilyCode() {
        return familyCode;
    }

    public void setFamilyCode(String familyCode) {
        this.familyCode = familyCode;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    // Main method for testing
    public static void main(String[] args) {
        // Example usage
        PrisonerModel prisoner1 = new PrisonerModel(101, "John Doe", 35, "Male",
                "123 Main St", "Robbery", "Bank robbery with weapons",
                LocalDate.of(2023, 5, 15), 60, "Central Prison", "FAM12345");
        
        System.out.println("Prisoner Name: " + prisoner1.getName());
        System.out.println("Crime Type: " + prisoner1.getCrimeType());
        System.out.println("Release Date: " + prisoner1.getReleaseDate());
        System.out.println("Status: " + prisoner1.getStatus());
    }
}