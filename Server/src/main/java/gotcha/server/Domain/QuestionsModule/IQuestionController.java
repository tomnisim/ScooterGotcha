package gotcha.server.Domain.QuestionsModule;
import java.util.List;
import java.util.function.BiConsumer;


public interface IQuestionController {
    void add_user_question(String message, String senderEmail, BiConsumer<String,Integer> update_function);
    String answer_user_question(int question_id, String answer, String adminEmail) throws Exception;

    Question get_question(int question_id) throws Exception;
    List<String> get_all_user_questions(String user_email);
    List<Question> get_all_open_questions();
}
