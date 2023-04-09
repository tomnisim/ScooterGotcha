package gotcha.server.Domain.UserModule;

import java.time.LocalDate;
/**
 * this DAO is for RIDER & ADMIN applications.
 */
public class RiderDAO {
    private double rating;
    private String scooterType;
    private LocalDate licenceIssueDate;
    private String raspberryPiSerialNumber;
    private String userEmail;
    private String phoneNumber;
    private String gender;
    private LocalDate birthDay;
    private String name;
    private String lastName;
    private String loggedIn;

    public RiderDAO(){}

    public RiderDAO(Rider rider){
        this.rating = rider.getRating();
        this.gender = rider.get_gender();
        this.userEmail = rider.get_email();
        this.name = rider.getName();
        this.lastName = rider.getLastName();
        this.licenceIssueDate = rider.getLicenceIssueDate();
        this.raspberryPiSerialNumber = rider.getRaspberryPiSerialNumber();
        this.phoneNumber = rider.get_phone_number();
        this.birthDay = rider.get_birth_day();
        this.scooterType = rider.getScooterType();
        if (rider.is_logged_in())
            this.loggedIn = "V";
        else
            this.loggedIn = "X";
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getScooterType() {
        return scooterType;
    }

    public void setScooterType(String scooterType) {
        this.scooterType = scooterType;
    }

    public LocalDate getLicenceIssueDate() {
        return licenceIssueDate;
    }

    public void setLicenceIssueDate(LocalDate licenceIssueDate) {
        this.licenceIssueDate = licenceIssueDate;
    }

    public String getRaspberryPiSerialNumber() {
        return raspberryPiSerialNumber;
    }

    public void setRaspberryPiSerialNumber(String raspberryPiSerialNumber) {
        this.raspberryPiSerialNumber = raspberryPiSerialNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
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

    public String getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(String loggedIn) {
        this.loggedIn = loggedIn;
    }
}
