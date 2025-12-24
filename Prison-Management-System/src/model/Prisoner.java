/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Anjal Bhattarai
 */


/**
 * Prisoner Model Class
 * Represents a prisoner entity in the Prison Management System
 */
public class Prisoner implements Comparable<Prisoner> {
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
    
    // Constructor
    public Prisoner(int prisonerId, String name, int age, String gender, 
                   String address, String crimeType, String crimeDescription,
                   LocalDate admissionDate, int sentenceDuration, 
                   String prisonLocation, String familyCode) {
        this.prisonerId = prisonerId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.crimeType = crimeType;
        this.crimeDescription = crimeDescription;
        this.admissionDate = admissionDate;
        this.sentenceDuration = sentenceDuration;
        this.prisonLocation = prisonLocation;
        this.status = "Active";
        this.healthStatus = "Good";
        this.familyCode = familyCode;
        
        // Calculate release date
        this.releaseDate = admissionDate.plusMonths(sentenceDuration);
    }
    
    // Getters and Setters
    public int getPrisonerId() { return prisonerId; }
    public void setPrisonerId(int prisonerId) { this.prisonerId = prisonerId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getCrimeType() { return crimeType; }
    public void setCrimeType(String crimeType) { this.crimeType = crimeType; }
    
    public String getCrimeDescription() { return crimeDescription; }
    public void setCrimeDescription(String crimeDescription) { 
        this.crimeDescription = crimeDescription; 
    }
    
    public LocalDate getAdmissionDate() { return admissionDate; }
    public void setAdmissionDate(LocalDate admissionDate) { 
        this.admissionDate = admissionDate; 
    }
    
    public int getSentenceDuration() { return sentenceDuration; }
    public void setSentenceDuration(int sentenceDuration) { 
        this.sentenceDuration = sentenceDuration;
        this.releaseDate = admissionDate.plusMonths(sentenceDuration);
    }
    
    public LocalDate getReleaseDate() { return releaseDate; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getPrisonLocation() { return prisonLocation; }
    public void setPrisonLocation(String prisonLocation) { 
        this.prisonLocation = prisonLocation; 
    }
    
    public String getHealthStatus() { return healthStatus; }
    public void setHealthStatus(String healthStatus) { 
        this.healthStatus = healthStatus; 
    }
    
    public String getFamilyCode() { return familyCode; }
    public void setFamilyCode(String familyCode) { this.familyCode = familyCode; }
    
    // Utility method to get formatted admission date
    public String getFormattedAdmissionDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return admissionDate.format(formatter);
    }
    
    // Utility method to get formatted release date
    public String getFormattedReleaseDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return releaseDate.format(formatter);
    }
    
    // For sorting by admission date
    @Override
    public int compareTo(Prisoner other) {
        return this.admissionDate.compareTo(other.admissionDate);
    }
    
    // For searching and display
    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s | Crime: %s | Status: %s | Location: %s",
                           prisonerId, name, crimeType, status, prisonLocation);
    }
    
    // Method to check if prisoner name contains search term (for linear search)
    public boolean containsSearchTerm(String searchTerm) {
        String lowerSearch = searchTerm.toLowerCase();
        return name.toLowerCase().contains(lowerSearch) ||
               crimeType.toLowerCase().contains(lowerSearch) ||
               String.valueOf(prisonerId).contains(searchTerm);
    }
}