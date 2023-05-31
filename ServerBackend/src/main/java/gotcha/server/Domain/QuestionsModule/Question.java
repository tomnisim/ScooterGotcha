package gotcha.server.Domain.QuestionsModule;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int question_id = 0;

    @Column(name="messageDate", nullable = false)
    protected LocalDateTime message_date;
    @Column(name="answerDate")
    protected LocalDateTime answer_date;

    @Column(name="message", columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci", nullable = false)
    protected String message;

    @Column(name="answer", columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")

    protected String answer;

    @Column(name="hasAnswer", nullable = false)
    protected boolean has_answer;

    @Column(name="senderEmail", nullable = false)
    protected String senderEmail;

    @Column(name="responderEmail")

    protected String responderEmail;

    // ---------------------- Constructor ---------------------------------------
    public Question() {
    }

    /**
     * constructor for new question
     * @param message
     * @param senderEmail
     */
    public Question(String message, String senderEmail) {
        this.message = message;
        this.has_answer = false;
        this.senderEmail = senderEmail;
        this.message_date = LocalDateTime.now();
        this.answer_date = LocalDateTime.MAX;
    }

    /**
     * constructor for load questions from DB
     * @param message_date
     * @param answer_date
     * @param message
     * @param answer
     * @param has_answer
     * @param senderEmail
     * @param responderEmail
     */
    public Question(LocalDateTime message_date, LocalDateTime answer_date, String message, String answer, boolean has_answer, String senderEmail, String responderEmail) {
        this.message_date = message_date;
        this.answer_date = answer_date;
        this.message = message;
        this.answer = answer;
        this.has_answer = has_answer;
        this.senderEmail = senderEmail;
        this.responderEmail = responderEmail;
    }



    public void set_answer(String answer, String responderEmail){
        this.has_answer = true;
        this.responderEmail = responderEmail;
        this.answer = answer;
        this.answer_date = LocalDateTime.now();
    }


    // question to string for user and admin
    public String toString_for_admin() {
        String ans = has_answer ? answer : "No Answer Yet";
        String answer_date1 = has_answer? answer_date.toString() : "";
        String has_ans = has_answer ? "Yes" : "No";
        return "UserQuestion{" +
                ", question_id=" + question_id +
                ", message_date=" + message_date +
                ", answer_date=" + answer_date1 +
                ", message=" + message +
                ", answer=" + ans +
                ", has_answer=" + has_ans +
                '}';
    }
    public String toString_for_user() {
        String ans = has_answer ? answer : "No Answer Yet";
        String answer_date1 = has_answer? answer_date.toString() : "";
        String has_ans = has_answer ? "Yes" : "No";
        return "Question to admin :" +
                ", message_date=" + message_date +
                ", answer_date=" + answer_date1 +
                ", message=" + message +
                ", answer=" + ans +
                '}';
    }

    // getters & setters


    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public LocalDateTime getMessage_date() {
        return message_date;
    }

    public void setMessage_date(LocalDateTime message_date) {
        this.message_date = message_date;
    }

    public LocalDateTime getAnswer_date() {
        return answer_date;
    }

    public void setAnswer_date(LocalDateTime answer_date) {
        this.answer_date = answer_date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isHas_answer() {
        return has_answer;
    }

    public void setHas_answer(boolean has_answer) {
        this.has_answer = has_answer;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSender(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getResponderEmail() {
        return responderEmail;
    }

    public void setResponder(String responderEmail) {
        this.responderEmail = responderEmail;
    }
}
