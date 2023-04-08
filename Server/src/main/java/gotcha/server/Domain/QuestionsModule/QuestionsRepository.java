package gotcha.server.Domain.QuestionsModule;

import gotcha.server.Domain.AdvertiseModule.Advertise;
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


    public void removeQuestion(int questionId) throws Exception {
        var result = open_questions.remove(questionId);
        if (result == null)
            throw new Exception("question with id:" + questionId + " not found");
        users_questions.get(result.getSenderEmail()).remove(questionId);
        questionsJpaRepository.delete(result);
    }

    public Question getQuestion(int questionId) throws Exception {
        var result = open_questions.get(questionId);
        if (result == null) {
            return getAdvertiseFromDb(questionId);
        }
        return result;
    }

    private Question getAdvertiseFromDb(int questionId) throws Exception {
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
            open_questions.put(question.getQuestion_id(), question);
            users_questions.computeIfAbsent(question.getSenderEmail(), k -> new HashMap<>()).putIfAbsent(question.getQuestion_id(), question);
        }
    }
}
