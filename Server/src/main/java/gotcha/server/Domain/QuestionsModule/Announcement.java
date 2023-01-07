package gotcha.server.Domain.QuestionsModule;

public class Announcement {
    private String message;
    private String sender;

    public Announcement(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }

    public String get_message() {
        return this.message;
    }

    public String get_sender() {
        return this.sender;
    }
}
