package gotcha.server.Service.Communication.Requests;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

/**
 * UpdateInformationRequest is a data transfer object (DTO) that represents
 * the user update information request payload containing user information
 */

public class UpdateInformationRequest {
    private String email;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String gender;

    private String scooterType;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    private LocalDate birthDate;



    public String getScooterType() {
        return scooterType;
    }

    public void setScooterType(String scooterType) {
        this.scooterType = scooterType;
    }

    // Default constructor (required for deserialization)
    public UpdateInformationRequest() {
    }

    // Custom constructor
    public UpdateInformationRequest(String email, String name, String lastName, String phoneNumber, String gender,  LocalDate birthDate) {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birthDate = birthDate;
    }




    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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


    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "UpdateInformationRequest{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", scooterType='" + scooterType + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
