package gotcha.server.Domain.UserModule;

import gotcha.server.Domain.RatingModule.UserRateCalculator;
import gotcha.server.Domain.RidesModule.Ride;

import java.time.LocalDate;

public class Rider extends User{
    private double rating;
    private String scooterType;
    private LocalDate licenceIssueDate;

    private String raspberryPiSerialNumber;

    public Rider(String userEmail, String userPassword,String name, String lastName, String phoneNumber, LocalDate birthDay, String gender, String scooterType, LocalDate licenceIssueDate, String raspberryPiSerialNumber) {
        super(userEmail,name, lastName, userPassword, phoneNumber, birthDay, gender);
        this.licenceIssueDate = licenceIssueDate;
        this.scooterType = scooterType;
        this.rating = 0.0;
        this.raspberryPiSerialNumber = raspberryPiSerialNumber;
    }

    public Rider(){}

    public void update_rating(Ride ride, int number_of_rides) {
        UserRateCalculator userRateCalculator = new UserRateCalculator();
        userRateCalculator.update_user_rating(this, ride, number_of_rides);
    }

    public void update_scooter_type(String scooterType) {
        this.scooterType = scooterType;
    }

    public void update_licence_issue_date(LocalDate licenceIssueDate) {
        this.licenceIssueDate = licenceIssueDate;
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

    public LocalDate getLicenceIssueDate() {
        return licenceIssueDate;
    }

    public String getRaspberryPiSerialNumber() {
        return raspberryPiSerialNumber;
    }
}
