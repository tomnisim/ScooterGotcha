package gotcha.server.Domain.UserModule;

import gotcha.server.Domain.RatingModule.UserRateCalculator;
import gotcha.server.Domain.RidesModule.Ride;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("Rider")
public class Rider extends User{

    @Column(name="rating")
    private double rating;

    @Column(name="scooterType")
    private String scooterType;



    @Column(name= "licenseIssueDate")
    private LocalDate licenseIssueDate;

    @Column(name="rpSerialNumber")

    private String raspberryPiSerialNumber;

    public Rider(String userEmail, String userPassword,String name, String lastName, String phoneNumber, LocalDate birthDay, String gender, String scooterType, LocalDate licenceIssueDate, String raspberryPiSerialNumber) {
        super(userEmail,name, lastName, userPassword, phoneNumber, birthDay, gender);
        this.licenseIssueDate = licenceIssueDate;
        this.scooterType = scooterType;
        this.rating = 0.0;
        this.raspberryPiSerialNumber = raspberryPiSerialNumber;
    }

    public Rider(){super();}

    public void update_rating(Ride ride, int number_of_rides,UserRateCalculator calculator ) {
        calculator.update_user_rating(this, ride, number_of_rides);
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

    public LocalDate getLicenseIssueDate() {
        return licenseIssueDate;
    }
    public void setLicenseIssueDate(LocalDate licenseIssueDate) {
        this.licenseIssueDate = licenseIssueDate;
    }
    public String getRaspberryPiSerialNumber() {
        return raspberryPiSerialNumber;
    }
    public void setRaspberryPiSerialNumber(String raspberryPiSerialNumber) {
        this.raspberryPiSerialNumber = raspberryPiSerialNumber;
    }

}
