package gotcha.server.Domain.QuestionsModule;

import gotcha.server.Domain.UserModule.Admin;
import gotcha.server.Domain.UserModule.User;

import java.util.List;

public interface IQuestionController {
    void add_user_question(String message, User sender);
    void answer_user_question(int question_id, String answer, Admin admin) throws Exception;
    List<String> get_all_user_questions(String user_email);
    List<String> get_all_open_questions();
}
