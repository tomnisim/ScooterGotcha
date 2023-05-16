package gotcha.server.Domain.QuestionsModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class QuestionsRepository {
    private Map<Integer, Question> open_questions;
    private Map<String, HashMap<Integer,Question>> users_questions;
    private final IQuestionsRepository questionsJpaRepository;

    @Autowired
    public QuestionsRepository(IQuestionsRepository questionsJpaRepository) {
        this.open_questions = new ConcurrentHashMap<>();
        this.users_questions = new ConcurrentHashMap<>();
        this.questionsJpaRepository = questionsJpaRepository;
        LoadFromDB();
    }

    public void addQuestion(Question newQuestion) throws Exception {
        questionsJpaRepository.save(newQuestion);
        var addQuestionResult = this.open_questions.putIfAbsent(newQuestion.getQuestion_id(), newQuestion);
        if (addQuestionResult != null) {
            throw new Exception("Question already exists");
        }
        users_questions.computeIfAbsent(newQuestion.getSenderEmail(), k -> new HashMap<>()).putIfAbsent(newQuestion.getQuestion_id(), newQuestion);
    }

    public List<Question> getAllOpenQuestions() {
        return  new ArrayList<>(open_questions.values());
    }


    public void removeOpenQuestion(int questionId) throws Exception {
        var result = open_questions.remove(questionId);
        if (result == null)
            throw new Exception("question with id:" + questionId + " not found");
        // save new state
        questionsJpaRepository.delete(result);
    }

    public Question getOpenQuestion(int questionId) throws Exception {
        var result = open_questions.get(questionId);
        if (result == null) {
            return getQuestionFromDb(questionId);
        }
        return result;
    }

    public List<Question> getUsersQuestions(String userEmail) {
        HashMap<Integer,Question> users_questions_map = users_questions.get(userEmail);
        List<Question> questions = new ArrayList<>();
        questions.addAll(users_questions_map.values());
        return questions;
    }

    private Question getQuestionFromDb(int questionId) throws Exception {
        var result = questionsJpaRepository.findById(questionId);
        if (result.isPresent()) {
            return result.get();
        }
        else {
            throw new Exception("advertisement with id:" + questionId + " not found");
        }
    }

    private void LoadFromDB() {
        var questionsInDb = questionsJpaRepository.findAll();
        for(var question : questionsInDb) {
            users_questions.computeIfAbsent(question.getSenderEmail(), k -> new HashMap<>()).putIfAbsent(question.getQuestion_id(), question);
            if (!question.isHas_answer())
                open_questions.put(question.getQuestion_id(), question);
        }
    }

    public String answerOpenQuestion(int question_id, String answer, String adminEmail) throws Exception {
        var question = getOpenQuestion(question_id);
        question.set_answer(answer, adminEmail);
        removeOpenQuestion(question_id);
        questionsJpaRepository.save(question);
        return question.getSenderEmail();
    }
}
