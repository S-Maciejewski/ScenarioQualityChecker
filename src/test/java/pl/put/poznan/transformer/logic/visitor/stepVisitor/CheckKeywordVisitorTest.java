package pl.put.poznan.transformer.logic.visitor.stepVisitor;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import pl.put.poznan.transformer.logic.Step;

public class CheckKeywordVisitorTest {

    public CheckKeywordVisitorTest() {
    }

    /**
     * Test of startsWithKeyword, of class CheckKeywordVisitor.
     * Testuje poprawność działania funkcji sprawdzającej, czy krok zawiera słowo kluczowe.
     * Krok nie zawiera słowa kluczowego.
     */
    @Test
    public void test_1_startsWithKeyword() {
        Step step = new Step("AktorSys1 Test substep 1", null);
        CheckKeywordVisitor visitor = new CheckKeywordVisitor();
        step.accept(visitor);
        boolean result = visitor.startsWithKeyword();
        assertFalse(result);
    }

    /**
     * Test of startsWithKeyword, of class CheckKeywordVisitor.
     * Testuje poprawność działania funkcji sprawdzającej, czy krok zawiera słowo kluczowe.
     * Krok zawiera słowo kluczowe.
     */
    @Test
    public void test2_startsWithKeyword() {
        Step step = new Step("FOR EACH: Test substep 1", null);
        CheckKeywordVisitor visitor = new CheckKeywordVisitor();
        step.accept(visitor);
        boolean result = visitor.startsWithKeyword();
        assertTrue(result);
    }
}
