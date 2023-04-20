package gotcha.server.Service.Communication.Requests;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

/**
 * UserRegistrationRequest is a data transfer object (DTO) that represents
 * the user registration request payload containing user information
 * and the Raspberry Pi's serial number.
 */

public class RegisterRequest {
    private String email;
    private String password;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String gender;
    private String raspberrySerialNumber;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")

    private LocalDate licenseIssueDate;

    private String scooterType;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    private LocalDate birthDate;

    public LocalDate getLicenseIssueDate() {
        return licenseIssueDate;
    }

    public void setLicenseIssueDate(LocalDate licenseIssueDate) {
        this.licenseIssueDate = licenseIssueDate;
    }

    public String getScooterType() {
        return scooterType;
    }

    public void setScooterType(String scooterType) {
        this.scooterType = scooterType;
    }

    // Default constructor (required for deserialization)
    public RegisterRequest() {
    }

    // Custom constructor
    public RegisterRequest(String email, String password, String name, String lastName, String phoneNumber, String gender, String raspberrySerialNumber, LocalDate birthDate) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.raspberrySerialNumber = raspberrySerialNumber;
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRaspberrySerialNumber() {
        return raspberrySerialNumber;
    }

    public void setRaspberrySerialNumber(String raspberrySerialNumber) {
        this.raspberrySerialNumber = raspberrySerialNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
