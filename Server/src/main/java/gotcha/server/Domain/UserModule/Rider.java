package gotcha.server.Domain.UserModule;

import gotcha.server.Domain.RatingModule.UserRateCalculator;
import gotcha.server.Domain.RidesModule.Ride;

import java.time.LocalDate;

public class Rider extends User{
    private double rating;
    private String scooterType;
    private LocalDate licenceIssueDate;

    public Rider(String userEmail, String userPassword, String phoneNumber, LocalDate birthDay, String gender, String scooterType, LocalDate licenceIssueDate) {
        super(userEmail, userPassword, phoneNumber, birthDay, gender);
        this.licenceIssueDate = licenceIssueDate;
        this.scooterType = scooterType;
        this.rating = 0.0;
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
}
