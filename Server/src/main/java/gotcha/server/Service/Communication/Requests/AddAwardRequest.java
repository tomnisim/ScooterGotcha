package gotcha.server.Service.Communication.Requests;

import java.util.List;

public class AddAwardRequest {
    private List<String> emails;
    private String award;

    public AddAwardRequest(){}

    public AddAwardRequest(List<String> emails, String award) {
        this.emails = emails;
        this.award = award;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    @Override
    public String toString() {
        return "AddAwardRequest{" +
                "emails=" + emails +
                ", award='" + award + '\'' +
                '}';
    }
}
