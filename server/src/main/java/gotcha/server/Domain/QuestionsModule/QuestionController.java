package gotcha.server.Domain.QuestionsModule;

import gotcha.server.DAL.HibernateUtils;
import gotcha.server.Domain.UserModule.Admin;
import gotcha.server.Domain.UserModule.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
@Component
public class QuestionController implements IQuestionController {
    private final QuestionsRepository questionsRepository;

    @Autowired
    public QuestionController(QuestionsRepository questionsRepository){
        this.questionsRepository = questionsRepository;
    }
    @Override
    public void add_user_question(String message, String senderEmail, BiConsumer<String, Integer> update_function) throws Exception {
        Question question = new Question(message, senderEmail);
        this.questionsRepository.addQuestion(question);
    }

    /**
     * this method for an admin who answer user question
     * the method will set the answer on the question and will return the sending user email
     * @param question_id
     * @param answer
     * @param adminEmail
     * @throws Exception
     */
    @Override
    public String answer_user_question(int question_id, String answer, String adminEmail) throws Exception {
        var question = this.questionsRepository.getOpenQuestion(question_id);
        question.set_answer(answer, adminEmail);
        questionsRepository.removeOpenQuestion(question_id);
        return question.getSenderEmail();
    }

    @Override
    public Question get_question(int question_id) throws Exception {
        return questionsRepository.getOpenQuestion(question_id);
    }


    /**
     * this method is for a user who want to see his questions.
     * @param user_email
     * @return open & close questions of the user
     */
    @Override
    public List<Question> get_all_user_questions(String user_email) {
        ArrayList<Question> answer = new ArrayList();
        for (Question question : this.questionsRepository.getUsersQuestions(user_email)){
            answer.add(question);
        }
        return answer;
    }

    /**
     * this method is for an admin who want to see all the open questions.
     * @return all the open questions.
     */
    @Override

    public List<Question> get_all_open_questions(){
        ArrayList<Question> answer = new ArrayList<Question>();
        for (Question question : this.questionsRepository.getAllOpenQuestions()){
            answer.add(question);
        }
        return answer;
    }
    private void notify_admins(String message) {
    }
}



