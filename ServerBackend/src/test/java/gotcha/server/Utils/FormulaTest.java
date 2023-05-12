package gotcha.server.Utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FormulaTest {

    @BeforeEach
    void setUp() {

    }

    @Test
    void evaluate_no_var() {
        Formula formula = new Formula("5+5*5");
        double answer = formula.evaluate(5);
        assertEquals(answer, 5+5*5);
    }
    @Test
    void evaluate_with_var() {
        Formula formula = new Formula("5+5*5+w");
        double answer = formula.evaluate(10);
        assertEquals(answer, 40);
    }
}