package gotcha.server.Domain.QuestionsModule;

import gotcha.server.DAL.HibernateUtils;
import gotcha.server.Domain.UserModule.Admin;
import gotcha.server.Domain.UserModule.User;
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

    private Map<Integer, Question> open_questions;
    private Map<String, List<Question>> users_questions;
    private AtomicInteger question_ids_counter;
    public QuestionController(){
        this.open_questions = new ConcurrentHashMap<>();
        this.users_questions = new ConcurrentHashMap<>();
        this.question_ids_counter = new AtomicInteger(0);
    }


    // TODO: 14/12/2022 Database
    public void load() {
//        this.questionsMap = HibernateUtils.get_questions();
        this.question_ids_counter = new AtomicInteger(HibernateUtils.get_max_question_id());
    }


    @Override
    public void add_user_question(String message, String senderEmail, BiConsumer<String, Integer> update_function) throws Exception {
        if (message.equals("")){
            throw new Exception("message cant be blank");
        }
        if (message.contains("<") || message.contains(">")){
            throw new Exception("no scripts allowed.");
        }
        int id = this.question_ids_counter.getAndIncrement();
        Question question = new Question(id, message, senderEmail);
        this.open_questions.put(id, question);
        List<Question> questionList = this.users_questions.getOrDefault(senderEmail, new LinkedList<>());
        questionList.add(question);
        this.users_questions.put(senderEmail, questionList);
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
        if (!this.open_questions.containsKey(question_id))
        {
            throw new Exception("Question does not exist");
        }
        Question question = this.open_questions.get(question_id);
        question.set_answer(answer, adminEmail);
        this.open_questions.remove(question_id);
        return question.getSenderEmail();
    }

    @Override
    public Question get_question(int question_id) throws Exception {
        if (!this.open_questions.containsKey(question_id))
        {
            throw new Exception("Question does not exist");
        }
        return this.open_questions.get(question_id);
    }


    /**
     * this method is for a user who want to see his questions.
     * @param user_email
     * @return open & close questions of the user
     */
    @Override
    public List<QuestionDAO> get_all_user_questions(String user_email) {
        ArrayList<QuestionDAO> answer = new ArrayList();
        List<Question> user_questions = this.users_questions.getOrDefault(user_email, new ArrayList<>());
        for (Question question : user_questions){
            answer.add(new QuestionDAO(question));
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
        for (Question question : this.open_questions.values()){
            answer.add(question);
        }
        return answer;
    }

    public int getQuestion_ids_counter() {
        return question_ids_counter.get();
    }


    private void notify_admins(String message) {
    }
}



