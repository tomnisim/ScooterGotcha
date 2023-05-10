package gotcha.server.Domain.QuestionsModule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class QuestionControllerTest {
    private QuestionController questionController;
    @Mock
    private QuestionsRepository questionsRepository;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.questionController = new QuestionController(questionsRepository);
    }

    @ParameterizedTest
    @ValueSource(strings = {"hello, here a question", "עברית"})
    void add_user_question_valid_message(String message) {
        String email = "email@gmail.com";
        boolean flag = false;
        assertDoesNotThrow(() -> questionController.add_user_question(message,email,null));
        try{
            verify(questionsRepository, times(1)).addQuestion(any());
        }
        catch (Exception e){
            fail("shouldn't fail: "+ e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"","meesage with <>"})
    void add_user_question_invalid_message(String message) {
        String email = "email11@gmail.com";
        boolean flag = true;
        assertThrows(Exception.class, () -> questionController.add_user_question(message,email,null));
        try{
            verify(questionsRepository, times(0)).addQuestion(any());
        }
        catch (Exception e){
            fail("shouldn't fail: "+ e.getMessage());
        }
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