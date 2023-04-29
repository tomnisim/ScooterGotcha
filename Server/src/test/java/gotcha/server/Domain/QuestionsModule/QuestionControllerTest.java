package gotcha.server.Domain.QuestionsModule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class QuestionControllerTest {
    private QuestionController questionController;
    @BeforeEach
    void setUp() {
        this.questionController = new QuestionController();

    }

    @ParameterizedTest
    @ValueSource(strings = {"hello, here a question", "עברית"})
    void add_user_question_valid_message(String message) {
        String email = "email@gmail.com";
        boolean flag = false;
        try{
            questionController.add_user_question(message, email, null);
        }
        catch (Exception e){}
        for (QuestionDAO questionDAO :questionController.get_all_user_questions(email))
        {
            if (questionDAO.getMessage().equals(message)){
                flag = true;
            }
        }
        assertTrue(flag);
    }

    @ParameterizedTest
    @ValueSource(strings = {"","meesage with <>"})
    void add_user_question_invalid_message(String message) {
        String email = "email11@gmail.com";
        boolean flag = true;
        try{
            questionController.add_user_question(message, email, null);
        }
        catch (Exception e){}
        for (QuestionDAO questionDAO :questionController.get_all_user_questions(email))
        {
            if (questionDAO.getMessage().equals(message)){
                flag = false;
            }
        }
        assertTrue(flag);
    }

    @Test
    void answer_user_question() {
    }

    @Test
    void get_question() {
    }

    @Test
    void get_all_user_questions() {
    }

    @Test
    void get_all_open_questions() {
    }
}