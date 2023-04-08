package gotcha.server.Domain.QuestionsModule;

import gotcha.server.Domain.UserModule.Admin;
import gotcha.server.Domain.UserModule.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class Question {

    protected int question_id;
    protected String message_date;
    protected String answer_date;

    protected String message;
    protected String answer;
    protected boolean has_answer;
    protected String senderEmail;
    protected String responderEmail;

    // ---------------------- Constructor ---------------------------------------
    public Question() {
    }

    /**
     * constructor for new question
     * @param message
     * @param senderEmail
     */
    public Question(int question_id, String message, String senderEmail) {
        this.question_id = question_id;
        this.message = message;
        this.has_answer = false;
        this.senderEmail = senderEmail;
        this.message_date = LocalDate.now().toString();
    }

    /**
     * constructor for load questions from DB
     * @param question_id
     * @param message_date
     * @param answer_date
     * @param message
     * @param answer
     * @param has_answer
     * @param senderEmail
     * @param responderEmail
     */
    public Question(int question_id, String message_date, String answer_date, String message, String answer, boolean has_answer, String senderEmail, String responderEmail) {
        this.question_id = question_id;
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
        this.answer_date = LocalDate.now().toString();
    }


    // question to string for user and admin
    public String toString_for_admin() {
        String ans = has_answer ? answer : "No Answer Yet";
        String has_ans = has_answer ? "Yes" : "No";
        return "UserQuestion{" +
                ", question_id=" + question_id +
                ", message_date=" + message_date +
                ", answer_date=" + answer_date +
                ", message=" + message +
                ", answer=" + ans +
                ", has_answer=" + has_ans +
                '}';
    }
    public String toString_for_user() {
        return "Question to admin :" +
                ", message_date=" + message_date +
                ", answer_date=" + answer_date +
                ", message=" + message +
                ", answer=" + answer +
                '}';
    }

    // getters & setters


    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getMessage_date() {
        return message_date;
    }

    public void setMessage_date(String message_date) {
        this.message_date = message_date;
    }

    public String getAnswer_date() {
        return answer_date;
    }

    public void setAnswer_date(String answer_date) {
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
