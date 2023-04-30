package gotcha.server.Domain.QuestionsModule;

/**
 * this DAO is for RIDER application.
 */
public class QuestionDAO {
    private String message_date;
    private String answer_date;

    private String message;
    private String answer;

    public QuestionDAO(){}
    public QuestionDAO(Question question){
        this.answer = question.getAnswer();
        this.message = question.getMessage();
        this.message_date = question.getMessage_date().toString();
        this.answer_date = question.getAnswer_date().toString();

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
}
