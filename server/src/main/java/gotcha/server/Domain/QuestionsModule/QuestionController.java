package gotcha.server.Domain.QuestionsModule;

import gotcha.server.DAL.HibernateUtils;
import gotcha.server.Domain.UserModule.Admin;
import gotcha.server.Domain.UserModule.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class QuestionController implements iQuestionController {

    private Map<Integer, Question> open_questions;
    private Map<String, List<Question>> users_questions;
    private AtomicInteger question_ids_counter;



    private static class SingletonHolder{
        private static QuestionController instance = new QuestionController();
    }
    public static QuestionController getInstance(){
        return QuestionController.SingletonHolder.instance;
    }

    public QuestionController() {
        this.open_questions = new ConcurrentHashMap<>();
        this.users_questions = new ConcurrentHashMap<>();
        this.question_ids_counter = new AtomicInteger(1);
    }

    // TODO: 14/12/2022 implement
    public void load(){
//        this.questionsMap = HibernateUtils.get_questions();
        this.question_ids_counter = new AtomicInteger(HibernateUtils.get_max_question_id());
    }


    /**
     * this method for a user who add question to admins, the method notify admins and added the question to the
     * open questions.
     * @param message
     * @param sender
     */
    @Override
    public void add_user_question(String message, User sender){
        int question_id = this.question_ids_counter.getAndIncrement();
        Question question_to_add = new Question(question_id, message, sender);
        String sender_email = sender.get_email();

        this.open_questions.put(question_id, question_to_add);
        if (this.users_questions.containsKey(sender_email))
            this.users_questions.get(sender_email).add(question_to_add);
        else{
            ArrayList<Question> list = new ArrayList<>();
            list.add(question_to_add);
            this.users_questions.put(sender_email, list);
        }
        this.notify_admins("there is a new question");
    }



    /**
     * this method for an admin who answer user question
     * the method will notify user, and remove the question from the open questions.
     * @param question_id
     * @param answer
     * @param admin
     * @throws Exception
     */
    @Override
    public void answer_user_question(int question_id, String answer, Admin admin) throws Exception {
        if (!this.open_questions.containsKey(question_id))
        {
            throw new Exception("Question does not exist");
        }
        Question question = this.open_questions.get(question_id);
        question.set_answer(answer, admin);
        question.getSender().add_notification("Your question got answered");
        this.open_questions.remove(question_id);

    }



    /**
     * this method is for a user who want to see his questions.
     * @param user_email
     * @return open & close questions of the user
     */
    @Override
    public List<String> get_all_user_questions(String user_email) {
        ArrayList<String> answer = new ArrayList<>();
        List<Question> user_questions = this.users_questions.get(user_email);
        for (Question question : user_questions){
            answer.add(question.toString_for_user());
        }
        return answer;
    }

    /**
     * this method is for an admin who want to see all the open questions.
     * @return all the open questions.
     */
    @Override
    public List<String> get_all_open_questions(){
        ArrayList<String> answer = new ArrayList<>();
        for (Question question : this.open_questions.values()){
            answer.add(question.toString_for_admin());
        }
        return answer;
    }

    public int getQuestion_ids_counter() {
        return question_ids_counter.get();
    }


    private void notify_admins(String message) {
    }
}



